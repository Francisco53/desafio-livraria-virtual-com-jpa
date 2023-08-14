import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LivrariaVirtualDAO livrariaDAO = new LivrariaVirtualDAO();

        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Realizar uma venda");
            System.out.println("3. Listar livros");
            System.out.println("4. Listar vendas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarLivro(scanner);
                    break;
                case 2:
                    //livrariaDAO.realizarVenda();
                    break;
                case 3:
                    livrariaDAO.listarTodosLivros();
                    break;
                case 4:
                    //livrariaDAO.listarLivrosImpressos();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }
    public static void cadastrarLivro(Scanner scanner) {

        LivrariaVirtualDAO livrariaDAO = new LivrariaVirtualDAO();

        System.out.println("Escolha o tipo de livro:");
        System.out.println("1. Livro Impresso");
        System.out.println("2. Livro Eletrônico");
        System.out.println("3. Ambos");
        int escolhaTipo = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após a leitura do número

        if (escolhaTipo == 1 || escolhaTipo == 3) {
            System.out.print("Título do livro impresso: ");
            String titulo = scanner.nextLine();
            System.out.print("Autores: ");
            String autores = scanner.nextLine();
            System.out.print("Editora: ");
            String editora = scanner.nextLine();
            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            System.out.print("Frete: ");
            double frete = scanner.nextDouble();
            System.out.print("Estoque: ");
            int estoque = scanner.nextInt();

            Impresso novoImpresso = new Impresso(titulo, autores, editora, preco, frete, estoque);
            livrariaDAO.cadastrarLivroNoBanco(novoImpresso); // Chamar método para cadastrar no banco
            System.out.println("Livro impresso cadastrado com sucesso!");
        }

        if (escolhaTipo == 2 || escolhaTipo == 3) {
            System.out.print("Título do livro eletrônico: ");
            String titulo = scanner.nextLine();
            System.out.print("Autores: ");
            String autores = scanner.nextLine();
            System.out.print("Editora: ");
            String editora = scanner.nextLine();
            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            System.out.print("Tamanho (KB): ");
            int tamanho = scanner.nextInt();

            Eletronico novoEletronico = new Eletronico(titulo, autores, editora, preco, tamanho);
            livrariaDAO.cadastrarLivroNoBanco(novoEletronico); // Chamar método para cadastrar no banco
            System.out.println("Livro eletrônico cadastrado com sucesso!");
        }
    }

}
