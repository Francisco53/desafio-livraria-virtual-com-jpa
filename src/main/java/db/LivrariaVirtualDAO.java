package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Eletronico;
import entities.Impresso;
import entities.Livro;
import entities.Venda;

public class LivrariaVirtualDAO {

	private static ConnectionFactory connectionFactory;

	public LivrariaVirtualDAO() {
		connectionFactory = new ConnectionFactory();
	}
	
	public void cadastrarLivroNoBanco(Livro livro) {
	    try (Connection connection = connectionFactory.recuperarConexao()) {
	        String sql = "INSERT INTO Livro (titulo, autores, editora, preco) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, livro.getTitulo());
	            preparedStatement.setString(2, livro.getAutores());
	            preparedStatement.setString(3, livro.getEditora());
	            preparedStatement.setFloat(4, livro.getPreco());
	            preparedStatement.executeUpdate();
	        }

	        if (livro instanceof Impresso) {
	            int livroId = recuperaUltimoIdInserido(connection);
	            sql = "INSERT INTO Impresso (id, frete, estoque) VALUES (?, ?, ?)";
	            try (PreparedStatement impressoStatement = connection.prepareStatement(sql)) {
	                impressoStatement.setInt(1, livroId);
	                impressoStatement.setFloat(2, ((Impresso) livro).getFrete());
	                impressoStatement.setInt(3, ((Impresso) livro).getEstoque());
	                impressoStatement.executeUpdate();
	            }
	        } else if (livro instanceof Eletronico) {
	            int livroId = recuperaUltimoIdInserido(connection);
	            sql = "INSERT INTO Eletronico (id, tamanho) VALUES (?, ?)";
	            try (PreparedStatement eletronicoStatement = connection.prepareStatement(sql)) {
	                eletronicoStatement.setInt(1, livroId);
	                eletronicoStatement.setInt(2, ((Eletronico) livro).getTamanho());
	                eletronicoStatement.executeUpdate();
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao cadastrar livro no banco de dados.", e);
	    }
	}

	private int recuperaUltimoIdInserido(Connection connection) throws SQLException {
	    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()")) {
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt(1);
	            } else {
	                throw new SQLException("Não foi possível obter o último ID inserido.");
	            }
	        }
	    }
	}

	public Livro[] consultarLivros() {
	    Livro[] livros = null;

	    try (Connection connection = connectionFactory.recuperarConexao()) {
	        String sql = "SELECT L.*, I.frete, I.estoque, E.tamanho FROM Livro L "
	                   + "LEFT JOIN Impresso I ON L.id = I.id "
	                   + "LEFT JOIN Eletronico E ON L.id = E.id";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                int count = 0;
	                while (resultSet.next()) {
	                    count++;
	                }
	                livros = new Livro[count];
	            }
	        }

	        sql = "SELECT L.*, I.frete, I.estoque, E.tamanho FROM Livro L "
	            + "LEFT JOIN Impresso I ON L.id = I.id "
	            + "LEFT JOIN Eletronico E ON L.id = E.id";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                int index = 0;
	                while (resultSet.next()) {
	                    String titulo = resultSet.getString("titulo");
	                    String autores = resultSet.getString("autores");
	                    String editora = resultSet.getString("editora");
	                    float preco = resultSet.getFloat("preco");

	                    Livro livro;
	                    if (resultSet.getFloat("frete") != 0) {
	                        float frete = resultSet.getFloat("frete");
	                        int estoque = resultSet.getInt("estoque");
	                        livro = new Impresso(titulo, autores, editora, preco, frete, estoque);
	                    } else if (resultSet.getInt("tamanho") != 0) {
	                        int tamanho = resultSet.getInt("tamanho");
	                        livro = new Eletronico(titulo, autores, editora, preco, tamanho);
	                    } else {
	                        throw new SQLException("Tipo de livro desconhecido.");
	                    }

	                    livros[index] = livro;
	                    index++;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao consultar livros no banco de dados.", e);
	    }

	    return livros;
	}
	
	public void cadastrarVendaNoBanco(Venda venda) {
	    try (Connection connection = connectionFactory.recuperarConexao()) {
	        String sql = "INSERT INTO Venda (cliente, valor) VALUES (?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            preparedStatement.setString(1, venda.getCliente());
	            preparedStatement.setFloat(2, venda.getValor());
	            preparedStatement.executeUpdate();

	            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int vendaNumero = generatedKeys.getInt(1);
	                    sql = "INSERT INTO VendaLivro (venda_numero, livro_id) VALUES (?, ?)";
	                    try (PreparedStatement vendaLivroStatement = connection.prepareStatement(sql)) {
	                        for (Livro livro : venda.getLivros()) {
	                            if (livro != null) {
	                                vendaLivroStatement.setInt(1, vendaNumero);
	                                vendaLivroStatement.setInt(2, recuperaLivroId(connection, livro));
	                                vendaLivroStatement.executeUpdate();
	                            }
	                        }
	                    }
	                } else {
	                    throw new SQLException("A inserção da venda falhou, não foi possível obter o número da venda.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao cadastrar venda no banco de dados.", e);
	    }
	}

	private int recuperaLivroId(Connection connection, Livro livro) throws SQLException {
	    String sql = "SELECT id FROM Livro WHERE titulo = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, livro.getTitulo());
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt("id");
	            } else {
	                throw new SQLException("O livro não foi encontrado no banco de dados.");
	            }
	        }
	    }
	}
	
	public Venda[] consultarVendas() {
	    Venda[] vendas = null;

	    try (Connection connection = connectionFactory.recuperarConexao()) {
	        String sql = "SELECT * FROM Venda";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                int count = 0;
	                while (resultSet.next()) {
	                    count++;
	                }
	                vendas = new Venda[count];
	            }
	        }

	        sql = "SELECT * FROM Venda";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                int index = 0;
	                while (resultSet.next()) {
	                    int numero = resultSet.getInt("numero");
	                    String cliente = resultSet.getString("cliente");
	                    float valor = resultSet.getFloat("valor");
	                    
	                    Venda venda = new Venda(cliente);
	                    venda.setNumero(numero);
	                    venda.setValor(valor);

	                    Livro[] livros = recuperarLivrosDaVenda(connection, numero);
	                    venda.setLivros(livros);

	                    vendas[index] = venda;
	                    index++;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao consultar vendas no banco de dados.", e);
	    }

	    return vendas;
	}

	private Livro[] recuperarLivrosDaVenda(Connection connection, int vendaNumero) throws SQLException {
	    String sql = "SELECT L.*, I.frete, I.estoque, E.tamanho FROM Livro L "
	               + "LEFT JOIN VendaLivro VL ON L.id = VL.livro_id "
	               + "LEFT JOIN Venda V ON VL.venda_numero = V.numero "
	               + "LEFT JOIN Impresso I ON L.id = I.id "
	               + "LEFT JOIN Eletronico E ON L.id = E.id "
	               + "WHERE V.numero = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, vendaNumero);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            List<Livro> livros = new ArrayList<>();
	            while (resultSet.next()) {
	                String titulo = resultSet.getString("titulo");
	                String autores = resultSet.getString("autores");
	                String editora = resultSet.getString("editora");
	                float preco = resultSet.getFloat("preco");
	                
	                Livro livro;
	                if (resultSet.getFloat("frete") != 0) {
	                    float frete = resultSet.getFloat("frete");
	                    int estoque = resultSet.getInt("estoque");
	                    livro = new Impresso(titulo, autores, editora, preco, frete, estoque);
	                } else if (resultSet.getInt("tamanho") != 0) {
	                    int tamanho = resultSet.getInt("tamanho");
	                    livro = new Eletronico(titulo, autores, editora, preco, tamanho);
	                } else {
	                    throw new SQLException("Tipo de livro desconhecido.");
	                }
	                
	                livros.add(livro);
	            }
	            
	            return livros.toArray(new Livro[0]);
	        }
	    }
	}
}
