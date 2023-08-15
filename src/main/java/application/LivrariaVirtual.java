package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import db.LivrariaVirtualDAO;
import entities.Eletronico;
import entities.Impresso;
import entities.Livro;
import entities.Venda;

public class LivrariaVirtual {

	public static Scanner sc;

	private static final int MAX_IMPRESSOS = 10;
	private static final int MAX_ELETRONICOS = 20;
	private static final int MAX_VENDAS = 50;

	private int numImpressos = 0;
	private int numEletronicos = 0;
	private int numVendas = 0;

	private Impresso[] impressos = new Impresso[MAX_IMPRESSOS];
	private Eletronico[] eletronicos = new Eletronico[MAX_ELETRONICOS];
	private Venda[] vendas = new Venda[MAX_VENDAS];

	public LivrariaVirtual() {
		sc = new Scanner(System.in);
	}

	public static int getMaxImpressos() {
		return MAX_IMPRESSOS;
	}

	public static int getMaxEletronicos() {
		return MAX_ELETRONICOS;
	}

	public static int getMaxVendas() {
		return MAX_VENDAS;
	}

	public static int getMaxLivros() {
		return MAX_IMPRESSOS + MAX_ELETRONICOS;
	}

	public int getNumImpressos() {
		return numImpressos;
	}

	public void setNumImpressos(int numImpressos) {
		if (numImpressos > 0 && numImpressos <= MAX_IMPRESSOS) {
			this.numImpressos = numImpressos;
		} else {
			throw new IllegalArgumentException("Quantidade inválida!");
		}
	}

	public int getNumEletronicos() {
		return numEletronicos;
	}

	public void setNumEletronicos(int numEletronicos) {
		if (numEletronicos > 0 && numEletronicos <= MAX_ELETRONICOS) {
			this.numEletronicos = numEletronicos;
		} else {
			throw new IllegalArgumentException("Quantidade inválida!");
		}
	}

	public int getNumVendas() {
		return numVendas;
	}

	public void setNumVendas(int numVendas) {
		if (numVendas > 0 && numVendas <= MAX_VENDAS) {
			this.numVendas = numVendas;
		} else {
			throw new IllegalArgumentException("Quantidade inválida!");
		}
	}

	public Impresso[] getImpressos() {
		return impressos;
	}

	public void adicionarLivroImpresso(Impresso livro) {
		if (livro != null && getNumImpressos() < MAX_IMPRESSOS) {
			getImpressos()[getNumImpressos()] = livro;
			setNumImpressos(getNumImpressos() + 1);
			System.out.println("Livro impresso cadastrado.");

			LivrariaVirtualDAO livrariaDAO = new LivrariaVirtualDAO();
			livrariaDAO.cadastrarLivroNoBanco(livro);
		} else {
			System.out.println("Erro ao cadastrar livro.");
		}
	}

	public Eletronico[] getEletronicos() {
		return eletronicos;
	}

	public void adicionarLivroEletronico(Eletronico livro) {
		if (livro != null && getNumEletronicos() < MAX_ELETRONICOS) {
			getEletronicos()[getNumEletronicos()] = livro;
			setNumEletronicos(getNumEletronicos() + 1);
			System.out.println("Livro eletrônico cadastrado.");

			LivrariaVirtualDAO livrariaDAO = new LivrariaVirtualDAO();
			livrariaDAO.cadastrarLivroNoBanco(livro);
		} else {
			System.out.println("Erro ao cadastrar livro eletrônico.");
		}
	}

	public Venda[] getVendas() {
		return vendas;
	}

	public void cadastrarLivro() {
		try {
			int opcao;
			System.out.println("Informa o tipo de livro a ser cadastrado:");
			System.out.println("[1] Impresso");
			System.out.println("[2] Eletrônico");
			System.out.println("[3] Ambos");
			System.out.println("[4] Voltar");
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1: {
				cadastrarLivroImpresso();
				break;
			}
			case 2: {
				cadastrarLivroEletronico();
				break;
			}
			case 3: {
				cadastrarLivroImpresso();
				cadastrarLivroEletronico();
				break;
			}
			case 4: {
				return;
			}
			default: {
				System.out.println("Entrada inválida.");
			}
			}
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida.");
		}
	}

	public void cadastrarLivroImpresso() {
		if (numImpressos == MAX_IMPRESSOS) {
			System.out.println("Atingida a quantidade máxima de cadastro de livros impressos!");
		}
		try {
			String titulo;
			String autores;
			String editora;
			float preco;
			float frete;
			int estoque;

			System.out.println("*** Dados do livro impresso ***");
			System.out.print("Título: ");
			titulo = sc.nextLine();
			System.out.print("Autores: ");
			autores = sc.nextLine();
			System.out.print("Editora: ");
			editora = sc.nextLine();
			System.out.print("Preço: ");
			preco = sc.nextFloat();
			sc.nextLine();
			System.out.print("Frete: ");
			frete = sc.nextFloat();
			sc.nextLine();
			System.out.print("Estoque: ");
			estoque = sc.nextInt();
			sc.nextLine();

			Impresso impresso = new Impresso(titulo, autores, editora, preco, frete, estoque);
			adicionarLivroImpresso(impresso);
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida. Digita um valor numérico correspondente.");
		}
	}

	public void cadastrarLivroEletronico() {
		if (numEletronicos == MAX_ELETRONICOS) {
			System.out.println("Atingida a quantidade máxima de cadastro de livros eletrônicos!");
			return;
		}
		try {
			String titulo;
			String autores;
			String editora;
			float preco;
			int tamanho;

			System.out.println("*** Dados do livro eletrônico ***");
			System.out.print("Título: ");
			titulo = sc.nextLine();
			System.out.print("Autores: ");
			autores = sc.nextLine();
			System.out.print("Editora: ");
			editora = sc.nextLine();
			System.out.print("Preço: ");
			preco = sc.nextFloat();
			sc.nextLine();
			System.out.print("Tamanho: ");
			tamanho = sc.nextInt();
			sc.nextLine();

			Eletronico eletronico = new Eletronico(titulo, autores, editora, preco, tamanho);
			adicionarLivroEletronico(eletronico);
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida.");
		}
	}

	public void realizarVenda() {
		if (getNumImpressos() == 0 && getNumEletronicos() == 0) {
			System.out.println("Não há livros a serem comprados :(");
			return;
		}
		if (getNumVendas() >= MAX_VENDAS) {
			System.out.println("Atingida a quantidade máxima de vendas!");
			return;
		}
		try {
			String cliente;
			int quantidade;
			int opcao;
			int codigoLivro;

			System.out.println("*** Dados da venda ***");
			System.out.print("Nome do cliente: ");
			cliente = sc.nextLine();

			Venda venda = new Venda(cliente);

			System.out.print("Quantidade de livros a serem comprados: ");
			quantidade = sc.nextInt();
			sc.nextLine();

			for (int i = 0; i < quantidade;) {
				System.out.println("Informa o tipo de livro a ser comprado:");
				System.out.println("[1] Impresso");
				System.out.println("[2] Eletrônico");
				System.out.println("[3] Voltar");
				opcao = sc.nextInt();
				sc.nextLine();

				switch (opcao) {
				case 1: {
					listarLivrosImpressos();
					System.out.print("Informa o código do livro: ");
					codigoLivro = sc.nextInt();
					sc.nextLine();

					Impresso[] listaImpressos = getImpressos();
					Impresso livro = listaImpressos[codigoLivro - 1];

					if (livro.getEstoque() == 0) {
						System.out.println("Não há livro em estoque.");
						break;
					}

					venda.addLivro(livro, i);

					if (venda.getLivros()[i] != null) {
						livro.atualizarEstoque();
						System.out.println("Livro adicionado.");
						i++;
					}
					break;
				}
				case 2: {
					listarLivrosEletronicos();
					System.out.print("Informa o código do livro: ");
					codigoLivro = sc.nextInt();
					sc.nextLine();

					Eletronico[] listaEletronicos = getEletronicos();
					Eletronico livro = listaEletronicos[codigoLivro - 1];

					venda.addLivro(livro, i);

					if (venda.getLivros()[i] != null) {
						System.out.println("Livro adicionado.");
						i++;
					}
					break;
				}
				case 3: {
					return;
				}
				default: {
					System.out.println("Entrada inválida.");
					break;
				}
				}
			}

			if (getNumVendas() < MAX_VENDAS) {
				getVendas()[getNumVendas()] = venda;
				venda.finalizarVenda();
				setNumVendas(getNumVendas() + 1);
				System.out.println("Venda cadastrada.");

				LivrariaVirtualDAO livrariaDAO = new LivrariaVirtualDAO();
				livrariaDAO.cadastrarVendaNoBanco(venda);
			} else {
				System.out.println("Erro ao cadastrar venda: atingida a quantidade máxima de vendas.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida.");
		}
	}

	public void listarLivrosImpressos() {
		if (getNumImpressos() > 0) {
			System.out.println("*** Livros Impressos ***");
			for (int i = 0; i < getImpressos().length; i++) {
				if (getImpressos()[i] != null) {
					System.out.println("Livro [" + (i + 1) + "]");
					System.out.println(getImpressos()[i].toString());
					System.out.println("--------------------");
				} else {
					return;
				}
			}
		} else {
			System.out.println("Não há livros impressos.");
		}
	}

	public void listarLivrosEletronicos() {
		if (getNumEletronicos() > 0) {
			System.out.println("*** Livros Eletrônicos ***");
			for (int i = 0; i < getEletronicos().length; i++) {
				if (getEletronicos()[i] != null) {
					System.out.println("Livro [" + (i + 1) + "]");
					System.out.println(getEletronicos()[i].toString());
					System.out.println("--------------------");
				} else {
					return;
				}
			}
		} else {
			System.out.println("Não há livros eletrônicos.");
		}
	}

	public void listarLivros() {
		listarLivrosImpressos();
		listarLivrosEletronicos();
	}

	public void listarVendas() {
		if (getNumVendas() > 0) {
			System.out.println("*** Vendas realizadas ***");
			Venda[] listaVendas = getVendas();
			for (Venda venda : listaVendas) {
				if (venda == null) {
					return;
				}
				System.out.println("Venda " + venda.getNumero());
				System.out.println("Cliente: " + venda.getCliente());
				venda.listarLivros();
				System.out.println("Total: " + venda.getValor());
				System.out.println("---------------------");
			}
		} else {
			System.out.println("Não há vendas registradas :(");
		}
	}

	public void carregarInformacoesDoBanco() {
	    LivrariaVirtualDAO livrariaDAO = new LivrariaVirtualDAO();

	    Livro[] livros = livrariaDAO.consultarLivros();
	    if (livros != null && livros.length > 0) {
	        for (Livro livro : livros) {
	            if (livro instanceof Impresso) {
	                Impresso impresso = (Impresso) livro;
	                if (getNumImpressos() < MAX_IMPRESSOS) {
	                    getImpressos()[getNumImpressos()] = impresso;
	                    setNumImpressos(getNumImpressos() + 1);
	                }
	            } else if (livro instanceof Eletronico) {
	                Eletronico eletronico = (Eletronico) livro;
	                if (numEletronicos < MAX_ELETRONICOS) {
	                    getEletronicos()[getNumEletronicos()] = eletronico;
	                    setNumEletronicos(getNumEletronicos() + 1);
	                }
	            }
	        }
	    }

	    Venda[] vendas = livrariaDAO.consultarVendas();
	    if (vendas != null && vendas.length > 0) {
	        for (Venda venda : vendas) {
	            if (getNumVendas() < MAX_VENDAS) {
	                getVendas()[getNumVendas()] = venda;
	                venda.finalizarVenda();
	                setNumVendas(getNumVendas() + 1);
	            }
	        }
	        setNumVendas(vendas.length);
	        Venda.setNumVendas(getNumVendas());
	    }
	}

	public static void main(String[] args) {

		LivrariaVirtual livraria = new LivrariaVirtual();

		livraria.carregarInformacoesDoBanco();

		int opcao;

		while (true) {
			System.out.println("--------------------------");
			System.out.println("|    Livraria Virtual    |");
			System.out.println("|------------------------|");
			System.out.println("| [1] Cadastrar livro    |");
			System.out.println("| [2] Realizar uma venda |");
			System.out.println("| [3] Listar livros      |");
			System.out.println("| [4] Listar vendas      |");
			System.out.println("| [5] Sair do programa   |");
			System.out.println("--------------------------");
			System.out.print("Operação: ");
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1: {
				livraria.cadastrarLivro();
				break;
			}
			case 2: {
				livraria.realizarVenda();
				break;
			}
			case 3: {
				livraria.listarLivros();
				break;
			}
			case 4: {
				livraria.listarVendas();
				break;
			}
			case 5: {
				System.out.println("Saindo do programa...");
				sc.close();
				return;
			}
			default: {
				System.out.println("Entrada inválida.");
				break;
			}
			}
		}
	}
}
