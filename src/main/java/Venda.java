public class Venda {
    private Livro[] livros;
    private static int numVendas = 0;
    private int numero;
    private String cliente;
    private double valor;
    private int numLivros;

    public Venda(){

    }


    public Venda(String cliente) {
        numVendas++;
        this.numero = numVendas;
        this.cliente = cliente;
        this.valor = 0;
        this.numLivros = 0;
        // Definindo um tamanho inicial para o vetor de livros (pode ser ajustado conforme a necessidade)
        this.livros = new Livro[10];
    }

    // Método para adicionar um livro na lista de livros da venda
    public void addLivro(Livro livro) {
        if (numLivros < livros.length) {
            livros[numLivros] = livro;
            valor += livro.getPreco();
            numLivros++;
        } else {
            System.out.println("A lista de livros está cheia.");
        }
    }

    // Método para listar todos os livros da venda
    public void listarLivros() {
        System.out.println("Livros da venda #" + numero + " para " + cliente + ":");
        for (int i = 0; i < numLivros; i++) {
            System.out.println(livros[i].toString());
        }
        System.out.println("Valor total da venda: R$" + valor);
    }

    // Getters para número de vendas e número da venda
    public static int getNumVendas() {
        return numVendas;
    }

    public int getNumero() {
        return numero;
    }
}
