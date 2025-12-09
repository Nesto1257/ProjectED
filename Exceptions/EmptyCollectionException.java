package Exceptions;

/**
 * Exceção lançada quando é tentada uma operação que requer uma coleção não vazia,
 * mas a coleção em questão está vazia.
 * Estende {@code RuntimeException} para ser uma exceção não verificada (unchecked).
 * * @author Ernesto Guilherme
 * @version 1.0
 */
public class EmptyCollectionException extends RuntimeException {

    /**
     * Cria uma instância de {@code EmptyCollectionException} sem mensagem de detalhe.
     */
    public EmptyCollectionException() {
        super();
    }

    /**
     * Cria uma instância de {@code EmptyCollectionException} com a mensagem de detalhe especificada.
     * * @param msg A mensagem de detalhe que será associada à exceção.
     */
    public EmptyCollectionException(String msg) {
        super(msg);
    }
}