package Structures;

import Exceptions.EmptyStackException;
import Interface.StackADT;

/**
 * Implementa uma Pilha (Stack) usando uma estrutura ligada (Linked Structure),
 * onde os elementos são armazenados em nós {@code LinearNode<T>}.
 * Implementa o princípio LIFO (Last-In, First-Out) e a interface {@code StackADT}.
 *
 * @param <T> O tipo de elementos armazenados na pilha.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class LinkedStack<T> implements StackADT<T>
{
    /** O número de elementos atualmente na pilha. */
    private int count;

    /** A referência para o nó no topo (top) da pilha. */
    private LinearNode<T> top;

    /**
     * Cria uma pilha ligada vazia.
     */
    public LinkedStack()
    {
        count = 0;
        top = null;
    }

    /**
     * Adiciona o elemento especificado ao topo desta pilha (operação 'push').
     *
     * @param element O elemento a ser empilhado.
     */
    @Override
    public void push (T element)
    {
        LinearNode<T> temp = new LinearNode<T> (element);

        // O novo nó aponta para o antigo topo
        temp.setNext(top);
        // O novo nó torna-se o novo topo
        top = temp;
        count++;
    }

    /**
     * Remove o elemento no topo desta pilha e retorna uma referência a ele (operação 'pop').
     *
     * @return O elemento removido do topo da pilha.
     * @throws EmptyStackException Se for tentada uma operação 'pop' numa pilha vazia.
     */
    @Override
    public T pop() throws EmptyStackException
    {
        if (isEmpty())
            throw new EmptyStackException();

        T result = top.getElement();
        // O topo move-se para o nó seguinte
        top = top.getNext();
        count--;

        return result;
    }

    /**
     * Retorna uma referência ao elemento no topo desta pilha sem o remover (operação 'peek').
     *
     * @return O elemento que está no topo da pilha.
     * @throws EmptyStackException Se for tentada uma operação 'peek' numa pilha vazia.
     */
    @Override
    public T peek() throws EmptyStackException
    {
        if (isEmpty())
            throw new EmptyStackException();

        return top.getElement();
    }

    /**
     * Retorna 'verdadeiro' se esta pilha não contiver elementos.
     *
     * @return 'Verdadeiro' se esta pilha estiver vazia; 'Falso', caso contrário.
     */
    @Override
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Retorna o número de elementos nesta pilha.
     *
     * @return O número de elementos nesta pilha.
     */
    @Override
    public int size()
    {
        return count;
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
        LinearNode current = top;

        while (current != null)
        {
            result = result + (current.getElement()).toString() + " ";
            current = current.getNext();
        }

        return result;
    }
}