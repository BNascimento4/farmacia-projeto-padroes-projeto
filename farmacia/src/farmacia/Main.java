package farmacia;

public class Main {
    public static void main(String[] args) {
        
        ProdutoRepositorio repositorio = new ProdutoRepositorioMemoria();
        VendaService service = new VendaService(repositorio);

        repositorio.adicionar(new Medicamento(1, "Dipirona 500mg", 8.50, 20));
        repositorio.adicionar(new Medicamento(2, "Ibuprofeno 400mg", 15.90, 10));
        repositorio.adicionar(new MedicamentoControlado(3, "Clonazepam 2mg", 32.00, 5));
        repositorio.adicionar(new Cosmetico(4, "Protetor Solar FPS 50", 45.00, 8));

        System.out.println("=== ESTOQUE ===");
        for (Produto p : repositorio.listarTodos()) {
            System.out.println(p);
        }
        System.out.println();

        Cliente cliente = new Cliente("Maria Silva", "123.456.789-00");

        System.out.println("=== VENDA 1 (com sucesso) ===");
        Venda v1 = service.iniciarVenda(cliente);
        try {
            service.adicionarItem(v1, 1, 2, false);
            service.adicionarItem(v1, 4, 1, false);
            service.adicionarItem(v1, 3, 1, true); // controlado, com receita
            service.finalizar(v1, PagamentoFactory.criar("pix"));
        } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException | ReceitaObrigatoriaException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("=== TESTES DE ERRO (try/catch) ===");
        Venda v2 = service.iniciarVenda(cliente);
        try {
            service.adicionarItem(v2, 3, 1, false);
        } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException | ReceitaObrigatoriaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        try {
            service.adicionarItem(v2, 2, 99, false);
        } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException | ReceitaObrigatoriaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        try {
            service.adicionarItem(v2, 42, 1, false);
        } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException | ReceitaObrigatoriaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        try {
            PagamentoFactory.criar("boleto");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
