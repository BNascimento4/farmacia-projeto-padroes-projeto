package farmacia.repository;

import farmacia.exception.ProdutoNaoEncontradoException;
import farmacia.model.Produto;

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

    @Override
    public int gerarProximoCodigo() {
        int maxId = 0;
        for (Produto p : produtos) {
            if (p.getCodigo() > maxId) {
                maxId = p.getCodigo();
            }
        }
        return maxId + 1; // Retorna o maior ID + 1
    }

    @Override
    public void atualizar(Produto produto) throws ProdutoNaoEncontradoException {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo() == produto.getCodigo()) {
                produtos.set(i, produto); // Substitui o produto antigo pelo novo na mesma posição
                return;
            }
        }
        throw new ProdutoNaoEncontradoException(produto.getCodigo());
    }

    @Override
    public void remover(int codigo) throws ProdutoNaoEncontradoException {
        Produto p = buscarPorCodigo(codigo);
        produtos.remove(p);
    }
}
