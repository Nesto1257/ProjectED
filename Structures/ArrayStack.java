package Structures;

import Exceptions.EmptyCollectionException;
import Interface.StackADT;

/**
 * Representa uma implementação de uma Pilha (Stack) usando um array redimensionável.
 * Implementa o princípio LIFO (Last-In, First-Out).
 * Implementa a interface {@code StackADT}.
 *
 * @param <T> O tipo de elementos armazenados na pilha.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class ArrayStack<T> implements StackADT<T>
{
    /**
     * Constante que representa a capacidade padrão do array.
     */
    private final int DEFAULT_CAPACITY = 100;

    /**
     * Variável inteira que representa tanto o número de elementos como o próximo
     * índice disponível no array (o topo lógico da pilha).
     */
    private int top;

    /**
     * Array de elementos genéricos que representa a pilha.
     */
    private T[] stack;

    /**
     * Cria uma pilha vazia usando a capacidade padrão.
     */
    public ArrayStack()
    {
        top = 0;
        stack = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Cria uma pilha vazia usando a capacidade especificada.
     * * @param initialCapacity A capacidade especificada para a pilha.
     */
    public ArrayStack (int initialCapacity)
    {
        top = 0;
        stack = (T[])(new Object[initialCapacity]);
    }

    /**
     * Adiciona o elemento especificado ao topo desta pilha (operação 'push'),
     * expandindo a capacidade do array da pilha, se necessário.
     *
     * @param element O elemento genérico a ser empilhado.
     */
    @Override
    public void push (T element)
    {
        if (size() == stack.length)
            expandCapacity();

        stack[top] = element;
        top++;
    }

    /**
     * Remove o elemento no topo desta pilha e retorna uma referência a ele (operação 'pop').
     *
     * @return O elemento removido do topo da pilha.
     * @throws EmptyCollectionException Se for tentada uma operação 'pop' numa pilha vazia.
     */
    @Override
    public T pop() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException("Pilha");

        top--;
        T result = stack[top];
        stack[top] = null;

        return result;
    }

    /**
     * Retorna uma referência ao elemento no topo desta pilha sem o remover (operação 'peek').
     *
     * @return O elemento que está no topo da pilha.
     * @throws EmptyCollectionException Se for tentada uma operação 'peek' numa pilha vazia.
     */
    @Override
    public T peek() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException("Pilha");

        return stack[top-1];
    }

    /**
     * Retorna 'verdadeiro' se esta pilha estiver vazia e 'falso' caso contrário.
     *
     * @return Valor booleano: 'verdadeiro' se esta pilha estiver vazia; 'falso' caso contrário.
     */
    @Override
    public boolean isEmpty()
    {
        return (top == 0);
    }

    /**
     * Retorna o número de elementos nesta pilha.
     *
     * @return O número de elementos nesta pilha.
     */
    @Override
    public int size()
    {
        return top;
    }

    /**
     * Retorna uma representação em 'string' desta pilha.
     * A ordem dos elementos na 'string' é do topo para a base.
     *
     * @return A representação em 'string' desta pilha.
     */
    @Override
    public String toString()
    {
        String result = "";

        for (int scan=top-1; scan >= 0; scan--)
            result = result + stack[scan].toString() + " ";

        return result;
    }

    /**
     * Cria um novo array para armazenar o conteúdo desta pilha com
     * o dobro da capacidade do array antigo.
     */
    private void expandCapacity()
    {
        T[] larger = (T[])(new Object[stack.length*2]);

        for (int index=0; index < stack.length; index++)
            larger[index] = stack[index];

        stack = larger;
    }
}