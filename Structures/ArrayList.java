package Structures;

import Interface.*;
import Exceptions.*;
import java.util.Iterator;

/**
 * Representa uma implementação de uma lista linear (List) usando um array redimensionável.
 * Implementa a interface {@code ListADT}.
 *
 * @param <T> O tipo de elementos armazenados na lista.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class ArrayList<T> implements ListADT<T>
{
    /** A capacidade padrão do array se nenhuma for especificada. */
    protected final int DEFAULT_CAPACITY = 100;

    /** Constante usada para indicar que um elemento não foi encontrado. */
    private final int NOT_FOUND = -1;

    /** O índice do próximo espaço disponível na lista (o tamanho lógico da lista). */
    protected int rear;

    /** O array que armazena os elementos da lista. */
    protected T[] list;

    /**
     * Cria uma lista vazia usando a capacidade padrão ({@code DEFAULT_CAPACITY}).
     */
    public ArrayList()
    {
        rear = 0;
        list = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Cria uma lista vazia usando a capacidade inicial especificada.
     * * @param initialCapacity A capacidade inicial do array subjacente.
     */
    public ArrayList (int initialCapacity)
    {
        rear = 0;
        list = (T[])(new Object[initialCapacity]);
    }

    /**
     * Remove e retorna o último elemento da lista.
     * * @return O elemento removido do fim da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T removeLast () throws EmptyCollectionException
    {
        T result;

        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        rear--;
        result = list[rear];
        list[rear] = null;

        return result;
    }

    /**
     * Remove e retorna o primeiro elemento da lista.
     * Os elementos remanescentes são deslocados.
     * * @return O primeiro elemento removido da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T removeFirst() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        T result = list[0];
        rear--;
        // Desloca os elementos para a esquerda
        for (int scan=0; scan < rear; scan++)
            list[scan] = list[scan+1];


        list[rear] = null;

        return result;
    }

    /**
     * Remove e retorna a primeira ocorrência do elemento especificado.
     * Os elementos são deslocados para preencher o espaço.
     * * @param element O elemento a ser removido.
     * @return O elemento removido da lista.
     * @throws ElementNotFoundException Se o elemento especificado não for encontrado.
     */
    @Override
    public T remove (T element)
    {
        T result;
        int index = find (element);

        if (index == NOT_FOUND)
            throw new ElementNotFoundException ("lista");

        result = list[index];
        rear--;
        // Desloca os elementos apropriados
        for (int scan=index; scan < rear; scan++)
            list[scan] = list[scan+1];


        list[rear] = null;

        return result;
    }

    /**
     * Retorna o primeiro elemento da lista sem o remover.
     * * @return O primeiro elemento da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T first() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        return list[0];
    }

    /**
     * Retorna o último elemento da lista sem o remover.
     * * @return O último elemento da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T last() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("lista");

        return list[rear-1];
    }

    /**
     * Determina se a lista contém o elemento alvo especificado.
     * * @param target O elemento cuja presença será verificada.
     * @return 'Verdadeiro' se o elemento alvo for encontrado; 'Falso', caso contrário.
     */
    @Override
    public boolean contains (T target)
    {
        return (find(target) != NOT_FOUND);
    }

    /**
     * Retorna o índice da primeira ocorrência do elemento alvo, ou {@code NOT_FOUND} se não for encontrado.
     * * @param target O elemento alvo a ser encontrado.
     * @return O índice do elemento, ou {@code NOT_FOUND}.
     */
    private int find (T target)
    {
        int scan = 0, result = NOT_FOUND;
        boolean found = false;

        if (! isEmpty())
            while (! found && scan < rear)
                if (target.equals(list[scan]))
                    found = true;
                else
                    scan++;

        if (found)
            result = scan;

        return result;
    }

    /**
     * Verifica se a lista está vazia.
     * * @return 'Verdadeiro' se a lista estiver vazia; 'Falso', caso contrário.
     */
    @Override
    public boolean isEmpty()
    {
        return (rear == 0);
    }

    /**
     * Retorna o número de elementos contidos na lista.
     * * @return O número de elementos na lista.
     */
    @Override
    public int size()
    {
        return rear;
    }

    /**
     * Retorna um iterador sobre os elementos contidos nesta lista.
     * * @return Um iterador para a lista.
     */
    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator<T> (list, rear);
    }

    /**
     * Retorna uma representação em 'string' da lista.
     * * @return Uma 'string' representando a lista.
     */
    @Override
    public String toString()
    {
        String result = "";

        for (int scan=0; scan < rear; scan++)
            result = result + list[scan].toString() + " ";

        return result;
    }

    /**
     * Aumenta a capacidade do array subjacente, duplicando o seu tamanho.
     * Este é um método auxiliar e protegido.
     */
    protected void expandCapacity()
    {
        T[] larger = (T[])(new Object[list.length*2]);

        for (int scan=0; scan < list.length; scan++)
            larger[scan] = list[scan];

        list = larger;
    }
}