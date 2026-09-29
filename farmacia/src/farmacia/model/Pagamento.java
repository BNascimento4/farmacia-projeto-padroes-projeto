package farmacia.model;


public interface Pagamento {
    String getNome();
    double calcularValorFinal(double valor);
}
