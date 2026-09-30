package farmacia.exception;

public class TipoProdutoInvalidoException extends Exception {
    public TipoProdutoInvalidoException(int tipoDigitado) {
        super("Tipo de produto inesperado: " + tipoDigitado + ". Escolha 1, 2 ou 3.");
    }
}
