package farmacia.service;


import farmacia.exception.EstoqueInsuficienteException;
import farmacia.exception.ProdutoNaoEncontradoException;
import farmacia.repository.ProdutoRepositorio;
import farmacia.exception.ReceitaObrigatoriaException;
import farmacia.model.*;

public class VendaService {
    private final ProdutoRepositorio repositorio;

    public VendaService(ProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Venda iniciarVenda(Cliente cliente) {
        return new Venda(cliente);
    }

    public void adicionarItem(Venda venda, int codigo, int quantidade, boolean temReceita)
            throws ProdutoNaoEncontradoException, EstoqueInsuficienteException, ReceitaObrigatoriaException {
        Produto produto = repositorio.buscarPorCodigo(codigo);
        if (produto.exigeReceita() && !temReceita) {
            throw new ReceitaObrigatoriaException(produto.getNome());
        }
        produto.baixarEstoque(quantidade);
        venda.adicionarItem(new ItemVenda(produto, quantidade));
    }

    public double finalizar(Venda venda, Pagamento pagamento) {
        double valor = pagamento.calcularValorFinal(venda.getTotal());
        venda.imprimirRecibo();
        System.out.println("Pagamento: " + pagamento.getNome());
        System.out.printf("VALOR COBRADO: R$ %.2f%n%n", valor);
        return valor;
    }
}
