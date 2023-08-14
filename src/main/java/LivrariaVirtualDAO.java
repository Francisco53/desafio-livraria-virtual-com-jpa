import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LivrariaVirtualDAO {
    // Resto do código da classe LivrariaVirtual

    private ConnectionFactory connectionFactory;

    public LivrariaVirtualDAO() {
        connectionFactory = new ConnectionFactory();
    }

    // Método para cadastrar livro no banco de dados
    public void cadastrarLivroNoBanco(Livro livro) {
        try (Connection connection = connectionFactory.recuperarConexao()) {
            String sql = "INSERT INTO livros (titulo, autores, editora, preco, frete, estoque, tamanho) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, livro.getTitulo());
                preparedStatement.setString(2, livro.getAutores());
                preparedStatement.setString(3, livro.getEditora());
                preparedStatement.setDouble(4, livro.getPreco());

                if (livro instanceof Impresso) {
                    preparedStatement.setDouble(5, ((Impresso) livro).getFrete());
                    preparedStatement.setInt(6, ((Impresso) livro).getEstoque());
                    preparedStatement.setNull(7, java.sql.Types.INTEGER); // Não se aplica a livros eletrônicos
                } else if (livro instanceof Eletronico) {
                    preparedStatement.setNull(5, java.sql.Types.DECIMAL); // Não se aplica a livros impressos
                    preparedStatement.setNull(6, java.sql.Types.INTEGER); // Não se aplica a livros impressos
                    preparedStatement.setInt(7, ((Eletronico) livro).getTamanho());
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar livro no banco de dados.", e);
        }
    }


    public void listarTodosLivros() {
        try (Connection connection = connectionFactory.recuperarConexao()) {
            String sql = "SELECT * FROM livros";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        if (resultSet.getObject("estoque") == null) {
                            Eletronico eletronico = new Eletronico();
                            eletronico.setTitulo(resultSet.getString("titulo"));
                            eletronico.setAutores(resultSet.getString("autores"));
                            eletronico.setEditora(resultSet.getString("editora"));
                            eletronico.setPreco(resultSet.getDouble("preco"));
                            eletronico.setTamanho(resultSet.getInt("tamanho"));
                            System.out.println("Tipo: Eletrônico");
                            System.out.println("Título: " + eletronico.getTitulo());
                            System.out.println("Autores: " + eletronico.getAutores());
                            System.out.println("Editora: " + eletronico.getEditora());
                            System.out.println("Preço: " + eletronico.getPreco());
                            System.out.println("Tamanho: " + eletronico.getTamanho() + " KB");
                            System.out.println("------------------------");
                        } else {
                            Impresso impresso = new Impresso();
                            impresso.setTitulo(resultSet.getString("titulo"));
                            impresso.setAutores(resultSet.getString("autores"));
                            impresso.setEditora(resultSet.getString("editora"));
                            impresso.setPreco(resultSet.getDouble("preco"));
                            impresso.setFrete(resultSet.getDouble("frete"));
                            impresso.setEstoque(resultSet.getInt("estoque"));
                            System.out.println("Tipo: Impresso");
                            System.out.println("Título: " + impresso.getTitulo());
                            System.out.println("Autores: " + impresso.getAutores());
                            System.out.println("Editora: " + impresso.getEditora());
                            System.out.println("Preço: " + impresso.getPreco());
                            System.out.println("Frete: " + impresso.getFrete());
                            System.out.println("Estoque: " + impresso.getEstoque());
                            System.out.println("------------------------");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros do banco de dados.", e);
        }
    }





    public void cadastrarLivroVendaNoBanco(Connection connection, int vendaId, Livro livro) throws SQLException {
        String sql = "INSERT INTO livros_venda (venda_id, livro_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vendaId);
            // Aqui você deve substituir o 1 pelo ID real do livro no banco
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
        }
    }

    // Resto do código da classe LivrariaVirtual
}
