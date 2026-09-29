package farmacia.pagamento;

import farmacia.model.Pagamento;

/** Padrão Factory: centraliza a criação do Pagamento a partir de um texto. */
public class PagamentoFactory {
    private PagamentoFactory() { }

    public static Pagamento criar(String tipo) {
        switch (tipo.toLowerCase()) {
            case "dinheiro": return new PagamentoDinheiro();
            case "pix":      return new PagamentoPix();
            case "cartao":   return new PagamentoCartao();
            default:
                throw new IllegalArgumentException("Forma de pagamento inválida: " + tipo);
        }
    }
}
