package farmacia.view;

import farmacia.pagamento.PagamentoFactory;
import farmacia.repository.ProdutoRepositorio;
import farmacia.exception.EstoqueInsuficienteException;
import farmacia.exception.ProdutoNaoEncontradoException;
import farmacia.exception.ReceitaObrigatoriaException;
import farmacia.model.*;
import farmacia.service.VendaService;

import java.util.Scanner;

public class MenuFarmacia {
    private ProdutoRepositorio repositorio;
    private VendaService service;
    private Scanner scanner;

    public MenuFarmacia(ProdutoRepositorio repositorio, VendaService service) {
        this.repositorio = repositorio;
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean executando = true;

        while (executando) {
            exibirOpcoes();
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarEstoque();
                    break;
                case 2:
                    realizarVenda();
                    break;
                case 3:
                    cadastrarProduto();
                    break;
                case 4:
                    alterarProduto();
                    break;
                case 5:
                    deletarProduto();
                    break;
                case 6:
                    executando = false;
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void exibirOpcoes() {
        System.out.println("\n=== SISTEMA DE FARMÁCIA ===");
        System.out.println("1. Listar Estoque");
        System.out.println("2. Realizar Venda");
        System.out.println("3. Cadastrar Produto");
        System.out.println("4. Alterar Produto");
        System.out.println("5. Deletar Produto");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void listarEstoque() {
        System.out.println("\n=== ESTOQUE ===");
        for (Produto p : repositorio.listarTodos()) {
            System.out.println(p);
        }
    }

    private void realizarVenda() {
        System.out.println("\n=== NOVA VENDA ===");
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf);
        Venda venda = service.iniciarVenda(cliente);

        adicionarItensNaVenda(venda);
        finalizarVenda(venda);
    }

    private void adicionarItensNaVenda(Venda venda) {
        boolean adicionando = true;
        while (adicionando) {
            System.out.print("\nID do Produto (ou 0 para ir ao pagamento): ");
            int idProduto = scanner.nextInt();

            if (idProduto == 0) {
                scanner.nextLine();
                break;
            }

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("O cliente apresentou receita médica? (S/N): ");
            String respReceita = scanner.nextLine();
            boolean temReceita = respReceita.equalsIgnoreCase("S");

            try {
                service.adicionarItem(venda, idProduto, quantidade, temReceita);
                System.out.println("-> Item adicionado com sucesso!");
            } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException | ReceitaObrigatoriaException e) {
                System.out.println("-> ERRO: " + e.getMessage());
            }
        }
    }

    private void finalizarVenda(Venda venda) {
        System.out.print("\nForma de pagamento (pix, cartao, dinheiro): ");
        String formaPagamento = scanner.nextLine();

        try {
            service.finalizar(venda, PagamentoFactory.criar(formaPagamento));
            System.out.println(">>> Venda finalizada com sucesso! <<<");
        } catch (IllegalArgumentException e) {
            System.out.println("-> ERRO no Pagamento: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("-> ERRO ao finalizar: " + e.getMessage());
        }
    }

    private void cadastrarProduto() {
        System.out.println("\n=== CADASTRAR NOVO PRODUTO ===");
        System.out.println("Tipos disponíveis: 1. Medicamento | 2. Controlado | 3. Cosmético");
        System.out.print("Escolha o tipo (1-3): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo < 1 || tipo > 3) {
            System.out.println("-> ERRO: Tipo de produto inválido. Cadastro cancelado.");
            return;
        }

        // O sistema gera o código sozinho aqui, sem interagir com o teclado
        int codigo = repositorio.gerarProximoCodigo();

        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Preço Base: ");
        double preco = Double.parseDouble(scanner.nextLine().replace(",", "."));

        System.out.print("Quantidade Inicial em Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        Produto novoProduto;
        switch (tipo) {
            case 1:
                novoProduto = new Medicamento(codigo, nome, preco, estoque);
                break;
            case 2:
                novoProduto = new MedicamentoControlado(codigo, nome, preco, estoque);
                break;
            case 3:
                novoProduto = new Cosmetico(codigo, nome, preco, estoque);
                break;
            default:
                throw new IllegalStateException("Tipo de produto inesperado: " + tipo);
        }

        repositorio.adicionar(novoProduto);
        System.out.println(">>> Produto '" + nome + "' (ID: " + codigo + ") cadastrado com sucesso! <<<");
    }

    private void alterarProduto() {
        System.out.println("\n=== ALTERAR PRODUTO ===");
        System.out.print("Digite o ID do produto que deseja alterar: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine();

        try {
            Produto produtoAntigo = repositorio.buscarPorCodigo(idProduto);
            System.out.println("Produto encontrado: " + produtoAntigo.getNome());

            System.out.println("Tipos: 1. Medicamento | 2. Controlado | 3. Cosmético");
            System.out.print("Novo tipo (1-3): ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            if (tipo < 1 || tipo > 3) {
                System.out.println("-> ERRO: Tipo inválido. Alteração cancelada.");
                return;
            }

            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();

            System.out.print("Novo preço Base: ");
            double preco = Double.parseDouble(scanner.nextLine().replace(",", "."));

            System.out.print("Novo estoque: ");
            int estoque = scanner.nextInt();
            scanner.nextLine();

            Produto produtoAtualizado;
            // Usamos o MESMO idProduto para substituir o antigo
            switch (tipo) {
                case 1: produtoAtualizado = new Medicamento(idProduto, nome, preco, estoque); break;
                case 2: produtoAtualizado = new MedicamentoControlado(idProduto, nome, preco, estoque); break;
                case 3: produtoAtualizado = new Cosmetico(idProduto, nome, preco, estoque); break;
                default: throw new IllegalStateException("Tipo de produto inesperado: " + tipo);
            }

            repositorio.atualizar(produtoAtualizado);
            System.out.println(">>> Produto ID " + idProduto + " alterado com sucesso! <<<");

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("-> ERRO: " + e.getMessage());
        }
    }

    private void deletarProduto() {
        System.out.println("\n=== DELETAR PRODUTO ===");
        System.out.print("Digite o ID do produto que deseja deletar: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine();

        try {
            Produto produto = repositorio.buscarPorCodigo(idProduto);
            System.out.print("Tem certeza que deseja deletar o produto '" + produto.getNome() + "'? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                repositorio.remover(idProduto);
                System.out.println(">>> Produto deletado com sucesso! <<<");
            } else {
                System.out.println("-> Exclusão cancelada.");
            }
        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("-> ERRO: " + e.getMessage());
        }
    }
}