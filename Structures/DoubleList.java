package Structures;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interface.ListADT;

import java.util.Iterator;

/**
 * Representa uma implementação de uma Lista Linear (List) usando uma estrutura de nós
 * duplamente ligados (DoubleNode).
 * Cada nó mantém referências para o nó seguinte (next) e para o nó anterior (previous).
 * Implementa a interface {@code ListADT}.
 *
 * @param <T> O tipo de elementos armazenados na lista.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoubleList<T> implements ListADT<T>
{
    /** O nó da frente (primeiro elemento) e o nó da traseira (último elemento) da lista. */
    protected DoubleNode<T> front, rear;

    /** O número de elementos atualmente na lista. */
    protected int count;

    /**
     * Cria uma lista duplamente ligada vazia.
     */
    public DoubleList()
    {
        rear = null;
        front = null;
        count = 0;
    }


    /**
     * Remove e retorna o último elemento na lista (nó traseiro).
     *
     * @return O elemento removido.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T removeLast () throws EmptyCollectionException
    {
        T result;

        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        result = rear.getElement();
        rear = rear.getPrevious(); // Move o 'rear' para o nó anterior

        if (rear == null)
            front = null; // A lista ficou vazia
        else
            rear.setNext(null); // O novo último nó não tem próximo

        count--;

        return result;
    }

    /**
     * Remove e retorna o primeiro elemento na lista (nó da frente).
     *
     * @return O elemento removido.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T removeFirst() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        T result = front.getElement();
        front = front.getNext(); // Move o 'front' para o nó seguinte

        if (front == null)
            rear = null; // A lista ficou vazia
        else
            front.setPrevious(null); // O novo primeiro nó não tem anterior

        count--;

        return result;
    }

    /**
     * Remove e retorna o elemento especificado, se ele for encontrado.
     * Lança {@code ElementNotFoundException} se o elemento não for encontrado.
     *
     * @param element O elemento a ser removido.
     * @return O elemento que foi removido da lista.
     * @throws ElementNotFoundException Se o elemento alvo não for encontrado.
     */
    @Override
    public T remove (T element)
    {
        T result;
        DoubleNode<T> nodeptr = find (element);


        if (nodeptr == null)
            throw new ElementNotFoundException("lista");

        result = nodeptr.getElement();

        // Verifica se é o nó da frente ou da traseira
        if (nodeptr == front)
            result = this.removeFirst(); // Usa o método removeFirst
        else if (nodeptr == rear)
            result = this.removeLast(); // Usa o método removeLast
        else
        {
            // O nó está no meio: liga o anterior ao seguinte e vice-versa
            nodeptr.getNext().setPrevious(nodeptr.getPrevious());
            nodeptr.getPrevious().setNext(nodeptr.getNext());
            count--;
        }

        return result;
    }


    /**
     * Retorna uma referência ao elemento na frente da lista sem o remover.
     *
     * @return O elemento na frente da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T first() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        return front.getElement();
    }

    /**
     * Retorna uma referência ao elemento na traseira da lista sem o remover.
     *
     * @return O elemento na traseira da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T last() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        return rear.getElement();
    }

    /**
     * Retorna 'verdadeiro' se esta lista contiver o elemento alvo especificado.
     *
     * @param target O elemento alvo a ser verificado.
     * @return 'Verdadeiro' se o elemento for encontrado; 'Falso', caso contrário.
     */
    @Override
    public boolean contains (T target)
    {
        return (find(target) != null);
    }

    /**
     * Retorna uma referência ao nó do elemento especificado, ou 'null' se não for encontrado.
     * Este é um método auxiliar privado.
     *
     * @param target O elemento alvo a ser encontrado.
     * @return O nó que contém o elemento alvo, ou 'null'.
     */
    private DoubleNode<T> find (T target)
    {
        boolean found = false;
        DoubleNode<T> traverse = front;
        DoubleNode<T> result = null;

        if (! isEmpty())
            while (! found && traverse != null)
                if (target.equals(traverse.getElement()))
                    found = true;
                else
                    traverse = traverse.getNext();

        if (found)
            result = traverse;

        return result;
    }

    /**
     * Retorna 'verdadeiro' se esta lista estiver vazia e 'falso' caso contrário.
     *
     * @return 'Verdadeiro' se a lista estiver vazia.
     */
    @Override
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Retorna o número de elementos atualmente nesta lista.
     *
     * @return O número de elementos (o tamanho) da lista.
     */
    @Override
    public int size()
    {
        return count;
    }

    /**
     * Retorna um iterador sobre os elementos contidos nesta lista.
     *
     * @return Um iterador para a lista.
     */
    @Override
    public Iterator<T> iterator()
    {
        return new DoubleIterator<T> (front, count);
    }

    /**
     * Retorna uma representação em 'string' desta lista, listando os elementos
     * da frente para a traseira, cada um numa nova linha.
     *
     * @return A representação em 'string' da lista.
     */
    @Override
    public String toString()
    {
        String result = "";
        DoubleNode<T> traverse = front;

        while (traverse != null)
        {
            result = result + (traverse.getElement()).toString() + "\n";
            traverse = traverse.getNext();
        }
        return result;
    }

}