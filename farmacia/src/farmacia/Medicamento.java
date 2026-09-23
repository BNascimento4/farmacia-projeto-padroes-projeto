package farmacia;

public class Medicamento extends Produto {
    public Medicamento(int codigo, String nome, double preco, int estoque) {
        super(codigo, nome, preco, estoque);
    }

    @Override
    public double getPrecoFinal() {
        return getPrecoBase();
    }

    @Override
    public String getCategoria() {
        return "Medicamento";
    }
}
