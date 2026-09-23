package farmacia;


public abstract class Produto {
    private final int codigo;
    private final String nome;
    private final double preco;
    private int estoque;

    protected Produto(int codigo, String nome, double preco, int estoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    
    public abstract double getPrecoFinal();

    public abstract String getCategoria();

   
    public boolean exigeReceita() {
        return false;
    }

    public void baixarEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (quantidade > estoque) {
            throw new EstoqueInsuficienteException(nome, quantidade, estoque);
        }
        estoque -= quantidade;
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    protected double getPrecoBase() { return preco; }
    public int getEstoque() { return estoque; }

    @Override
    public String toString() {
        return String.format("[%d] %-22s %-14s R$ %7.2f  (estoque: %d)",
                codigo, nome, getCategoria(), getPrecoFinal(), estoque);
    }
}
