package farmacia;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositorioMemoria implements ProdutoRepositorio {
    private final List<Produto> produtos = new ArrayList<>();

    @Override
    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public Produto buscarPorCodigo(int codigo) throws ProdutoNaoEncontradoException {
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        throw new ProdutoNaoEncontradoException(codigo);
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }
}
