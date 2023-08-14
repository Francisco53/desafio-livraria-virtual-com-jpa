public class LivrariaVirtual {
    private static final int MAX_IMPRESSOS = 100;
    private static final int MAX_ELETRONICOS = 100;
    private static final int MAX_VENDAS = 100;

    private Impresso[] impressos;
    private Eletronico[] eletronicos;
    private Venda[] vendas;
    private int numImpressos;
    private int numEletronicos;
    private int numVendas;

    public LivrariaVirtual() {
        impressos = new Impresso[MAX_IMPRESSOS];
        eletronicos = new Eletronico[MAX_ELETRONICOS];
        vendas = new Venda[MAX_VENDAS];
        numImpressos = 0;
        numEletronicos = 0;
        numVendas = 0;
    }

    public void cadastrarLivro() {
        // Implementação do método de cadastro de livros
    }

    public void realizarVenda() {
        // Implementação do método de realização de venda
    }

    public void listarLivrosImpressos() {
        // Implementação para listar livros impressos
    }

    public void listarLivrosEletronicos() {
        // Implementação para listar livros eletrônicos
    }

    public void listarLivros() {
        // Implementação para listar todos os livros cadastrados
    }

    public void listarVendas() {
        // Implementação para listar todas as vendas realizadas
    }
}
