package entities;

import application.LivrariaVirtual;

public class Venda {

	private static int numVendas = 0;
	private int numero;
	private String cliente;
	private float valor;
	private Livro[] livros;

	public Venda() {
	}

	public Venda(String cliente) {
		this.cliente = cliente;
		livros = new Livro[LivrariaVirtual.getMaxLivros()];
	}

	public static int getNumVendas() {
		return numVendas;
	}

	public static void setNumVendas(int numVendas) {
		Venda.numVendas = numVendas;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Livro[] getLivros() {
		return livros;
	}

	public void setLivros(Livro[] livros) {
		this.livros = livros;
	}

	public void addLivro(Livro l, int index) {
		if (index < 0 || index >= livros.length) {
			throw new IndexOutOfBoundsException("Índice inválido: " + index);
		}

		livros[index] = l;

		if (l instanceof Impresso) {
			Impresso impresso = (Impresso) l;
			setValor(getValor() + l.getPreco() + impresso.getFrete());
		} else {
			setValor(getValor() + l.getPreco());
		}
	}

	public void listarLivros() {
		Livro[] listaLivros = getLivros();

		if (listaLivros == null || listaLivros.length == 0) {
			System.out.println("Nenhum livro na venda.");
		} else {
			System.out.println("Livros na venda:");
			for (Livro livro : listaLivros) {
				if (livro == null) {
					return;
				}
				System.out.println(livro.toString());
			}
		}
	}

	public void finalizarVenda() {
		numVendas++;
		setNumero(numVendas);
	}
}