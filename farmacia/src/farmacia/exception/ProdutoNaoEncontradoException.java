package farmacia.exception;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException(int codigo) {
        super("Produto de código " + codigo + " não encontrado");
    }
}
