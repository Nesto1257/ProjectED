package Exceptions;

/**
 * Exceção lançada quando uma operação que tenta aceder ou remover um elemento
 * é invocada numa pilha (Stack) vazia.
 * Estende {@code RuntimeException} para ser uma exceção não verificada (unchecked).
 * * @author Ernesto Guilherme
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException
{
    /**
     * Cria uma instância de {@code EmptyStackException} com a mensagem de detalhe predefinida.
     * A mensagem predefinida é: "The stack is empty.".
     */
    public EmptyStackException()
    {
        super ("The stack is empty.");
    }

    /**
     * Cria uma instância de {@code EmptyStackException} com a mensagem de detalhe especificada.
     * * @param message A mensagem de detalhe que será associada à exceção.
     */
    public EmptyStackException (String message)
    {
        super (message);
    }
}