package farmacia.model;


public class ItemVenda {
    private final Produto produto;
    private final int quantidade;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public double getSubtotal() {
        return produto.getPrecoFinal() * quantidade;
    }
}
