package farmacia;

public class PagamentoDinheiro implements Pagamento {
    @Override
    public String getNome() { return "Dinheiro (5% de desconto)"; }

    @Override
    public double calcularValorFinal(double valor) { return valor * 0.95; }
}
