package farmacia.exception;

public class ReceitaObrigatoriaException extends Exception {
    public ReceitaObrigatoriaException(String nome) {
        super("O medicamento '" + nome + "' exige apresentação de receita");
    }
}
