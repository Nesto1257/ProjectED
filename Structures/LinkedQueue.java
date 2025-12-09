package Structures;

import Interface.QueueADT;
import Exceptions.*;

/**
 * Implementa uma Fila (Queue) usando uma estrutura ligada (Linked Structure),
 * onde os elementos são armazenados em nós {@code LinearNode<T>}.
 * Implementa o princípio FIFO (First-In, First-Out) e a interface {@code QueueADT}.
 *
 * @param <T> O tipo de elementos armazenados na fila.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueADT<T>
{
    /** O número de elementos atualmente na fila. */
    private int count;

    /** O nó da frente (primeiro elemento) e o nó da traseira (último elemento) da fila. */
    private LinearNode<T> front, rear;

    /**
     * Cria uma fila ligada vazia.
     */
    public LinkedQueue()
    {
        count = 0;
        front = rear = null;
    }

    /**
     * Adiciona o elemento especificado ao final (rear) desta fila (operação 'enqueue').
     *
     * @param element O elemento a ser adicionado ao final desta fila.
     */
    @Override
    public void enqueue (T element)
    {
        LinearNode<T> node = new LinearNode<T>(element);

        if (isEmpty())
            front = node; // O nó torna-se a frente e a traseira
        else
            rear.setNext (node); // O nó atual da traseira aponta para o novo nó

        rear = node; // O novo nó torna-se a nova traseira
        count++;
    }

    /**
     * Remove e retorna o elemento que está na frente (front) desta fila (operação 'dequeue').
     *
     * @return O elemento removido da frente da fila.
     * @throws EmptyCollectionException Se a fila estiver vazia.
     */
    @Override
    public T dequeue() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("fila");

        T result = front.getElement();
        front = front.getNext(); // Move a frente para o nó seguinte
        count--;

        if (isEmpty())
            rear = null; // Se a fila ficou vazia, a traseira também deve ser nula

        return result;
    }

    /**
     * Retorna, sem o remover, o elemento que está na frente (front) desta fila.
     *
     * @return O primeiro elemento desta fila.
     * @throws EmptyCollectionException Se a fila estiver vazia.
     */
    @Override
    public T first() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("fila");

        return front.getElement();
    }

    /**
     * Retorna 'verdadeiro' se esta fila não contiver elementos.
     *
     * @return 'Verdadeiro' se esta fila estiver vazia; 'Falso', caso contrário.
     */
    @Override
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Retorna o número de elementos nesta fila.
     *
     * @return O número de elementos nesta fila.
     */
    @Override
    public int size()
    {
        return count;
    }

    /**
     * Retorna uma representação em 'string' desta fila, listando os elementos
     * da frente para a traseira, cada um numa nova linha.
     *
     * @return A representação em 'string' desta fila.
     */
    @Override
    public String toString()
    {
        String result = "";
        LinearNode<T> current = front;

        while (current != null)
        {
            result = result + (current.getElement()).toString() + "\n";
            current = current.getNext();
        }

        return result;
    }
}