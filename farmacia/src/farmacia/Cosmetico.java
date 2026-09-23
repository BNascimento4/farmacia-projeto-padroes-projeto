package farmacia;

public class Cosmetico extends Produto {
    private static final double ACRESCIMO = 0.10; 

    public Cosmetico(int codigo, String nome, double preco, int estoque) {
        super(codigo, nome, preco, estoque);
    }

    @Override
    public double getPrecoFinal() {
        return getPrecoBase() * (1 + ACRESCIMO);
    }

    @Override
    public String getCategoria() {
        return "Cosmético";
    }
}
