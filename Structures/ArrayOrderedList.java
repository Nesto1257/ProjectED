package Structures;

import Interface.*;
import Exceptions.*;

/**
 * Representa uma implementação de uma lista linear ordenada (Ordered List)
 * usando um array redimensionável.
 * Estende {@code ArrayList} e implementa a interface {@code OrderedListADT}.
 * Os elementos são mantidos numa ordem ascendente natural ou definida pelo seu método {@code compareTo}.
 *
 * @param <T> O tipo de elementos armazenados na lista, que deve ser {@code Comparable}.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class ArrayOrderedList<T> extends ArrayList<T>
        implements OrderedListADT<T>
{
    /**
     * Cria uma lista ordenada vazia usando a capacidade padrão (100).
     */
    public ArrayOrderedList()
    {
        super();
    }

    /**
     * Cria uma lista ordenada vazia usando a capacidade inicial especificada.
     * * @param initialCapacity A capacidade inicial do array subjacente.
     */
    public ArrayOrderedList (int initialCapacity)
    {
        super(initialCapacity);
    }

    /**
     * Adiciona o elemento especificado à lista na posição correta para manter a ordem.
     * Se a capacidade do array for excedida, a capacidade é expandida.
     *
     * @param element O elemento a ser adicionado à lista.
     * @throws NonComparableElementException Se o elemento não implementar {@code Comparable}.
     */
    @Override
    public void add (T element)
    {
        if (size() == list.length)
            expandCapacity();

        // Conversão para Comparable é necessária para usar compareTo
        Comparable<T> temp = (Comparable<T>)element;

        // 1. Encontrar o ponto de inserção
        int scan = 0;
        while (scan < rear && temp.compareTo(list[scan]) > 0)
            scan++;

        // 2. Deslocar os elementos (abrir espaço)
        for (int scan2=rear; scan2 > scan; scan2--)
            list[scan2] = list[scan2-1];

        // 3. Inserir o elemento
        list[scan] = element;
        rear++;
    }
}