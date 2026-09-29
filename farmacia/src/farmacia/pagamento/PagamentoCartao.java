package farmacia.pagamento;

import farmacia.model.Pagamento;

public class PagamentoCartao implements Pagamento {
    @Override
    public String getNome() { return "Cartão (sem desconto)"; }

    @Override
    public double calcularValorFinal(double valor) { return valor; }
}
