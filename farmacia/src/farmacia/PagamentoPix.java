package farmacia;

public class PagamentoPix implements Pagamento {
    @Override
    public String getNome() { return "Pix (3% de desconto)"; }

    @Override
    public double calcularValorFinal(double valor) { return valor * 0.97; }
}
