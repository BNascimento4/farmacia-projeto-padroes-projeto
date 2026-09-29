package farmacia.model;


public class MedicamentoControlado extends Medicamento {
    public MedicamentoControlado(int codigo, String nome, double preco, int estoque) {
        super(codigo, nome, preco, estoque);
    }

    @Override
    public boolean exigeReceita() {
        return true;
    }

    @Override
    public String getCategoria() {
        return "Controlado";
    }
}
