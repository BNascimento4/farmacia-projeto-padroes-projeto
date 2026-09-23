package farmacia;

public class EstoqueInsuficienteException extends Exception {
    public EstoqueInsuficienteException(String nome, int pedido, int disponivel) {
        super("Estoque insuficiente de '" + nome + "': pedido " + pedido + ", disponível " + disponivel);
    }
}
