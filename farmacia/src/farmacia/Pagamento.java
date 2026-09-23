package farmacia;


public interface Pagamento {
    String getNome();
    double calcularValorFinal(double valor);
}
