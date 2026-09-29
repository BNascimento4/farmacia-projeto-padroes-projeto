package farmacia.repository;

import farmacia.exception.ProdutoNaoEncontradoException;
import farmacia.model.Produto;

import java.util.List;


public interface ProdutoRepositorio {
    void adicionar(Produto produto);
    Produto buscarPorCodigo(int codigo) throws ProdutoNaoEncontradoException;
    List<Produto> listarTodos();
}
