package Structures;

import Exceptions.*;
import Interface.*;

/**
 * Implementa uma Fila (Queue) usando uma estrutura de array circular redimensionável.
 * Esta implementação utiliza o módulo (%) para gerir o avanço dos ponteiros 'front' e 'rear'
 * dentro do array, tratando o array como se estivesse ligado nas extremidades.
 * Implementa a interface {@code QueueADT}.
 *
 * @param <T> O tipo de elementos armazenados na fila.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class CircularArrayQueue<T> implements QueueADT<T>
{
    /** Capacidade padrão do array subjacente. */
    private final int DEFAULT_CAPACITY = 100;

    /** Índice da frente (front), índice da traseira (rear) e o número de elementos na fila. */
    private int front, rear, count;

    /** O array que armazena os elementos da fila. */
    private T[] queue;

    /**
     * Cria uma fila vazia usando a capacidade padrão.
     */
    public CircularArrayQueue()
    {
        front = rear = count = 0;
        queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Cria uma fila vazia usando a capacidade inicial especificada.
     * * @param initialCapacity A capacidade inicial do array subjacente.
     */
    public CircularArrayQueue (int initialCapacity)
    {
        front = rear = count = 0;
        queue = ( (T[])(new Object[initialCapacity]) );
    }

    /**
     * Adiciona o elemento especificado ao final (rear) desta fila (operação 'enqueue').
     * Se a capacidade total for atingida, a capacidade do array é expandida.
     *
     * @param element O elemento a ser adicionado ao final desta fila.
     */
    @Override
    public void enqueue (T element)
    {
        if (size() == queue.length)
            expandCapacity();

        queue[rear] = element;

        // Atualiza o índice 'rear' circularmente
        rear = (rear + 1) % queue.length;

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

        T result = queue[front];
        queue[front] = null; // Limpa a referência do elemento removido

        // Atualiza o índice 'front' circularmente
        front = (front + 1) % queue.length;

        count--;

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

        return queue[front];
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
     * Retorna uma representação em 'string' desta fila.
     *
     * @return A representação em 'string' desta fila.
     */
    @Override
    public String toString()
    {
        String result = "";
        // Nota: Esta implementação de toString não utiliza a lógica circular, o que pode ser impreciso se front > rear.
        // A lógica do percurso deve ser revista para usar o índice 'front' e percorrer 'count' elementos de forma circular.
        int scan = 0;

        while(scan < count)
        {
            if(queue[scan]!=null)
            {
                result += queue[scan].toString()+"\n";
            }
            scan++;
        }

        return result;
    }

    /**
     * Cria um novo array para armazenar o conteúdo desta fila com
     * o dobro da capacidade do array antigo, mantendo a ordem dos elementos.
     * Os elementos são reindexados para começar do índice 0 no novo array.
     */
    public void expandCapacity()
    {
        T[] larger = (T[])(new Object[queue.length * 2]);

        // Copia os elementos para o novo array, começando sempre do índice 0
        for(int scan = 0; scan < count; scan++)
        {
            larger[scan] = queue[front];
            front = (front + 1) % queue.length; // Avança o ponteiro circular
        }

        // Reajusta os ponteiros e o array
        front = 0;
        rear = count;
        queue = larger;
    }
}