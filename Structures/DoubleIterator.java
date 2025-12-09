package Structures;

import java.util.*;

/**
 * Implementa um iterador para coleções baseadas em nós duplamente ligados (DoubleNode).
 * Permite percorrer os elementos da coleção de forma sequencial.
 * Implementa a interface {@code Iterator}.
 *
 * @param <T> O tipo de elementos que o iterador irá percorrer.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoubleIterator<T> implements Iterator
{
    /** O número de elementos na coleção. */
    private int count;

    /** A posição atual (o nó atual) na iteração. */
    private DoubleNode<T> current;

    /**
     * Configura este iterador usando a lista de nós especificada.
     *
     * @param list O primeiro nó da lista a ser iterada (o nó head).
     * @param size O número de elementos válidos na lista.
     */
    public DoubleIterator (DoubleNode<T> list, int size)
    {
        current = list;
        count = size;
    }

    /**
     * Retorna 'verdadeiro' se esta iteração ainda tiver pelo menos um elemento para entregar.
     * O iterador tem um próximo elemento se o nó atual não for nulo.
     *
     * @return 'Verdadeiro' se existirem mais elementos; 'Falso', caso contrário.
     */
    @Override
    public boolean hasNext()
    {
        return (current != null);
    }

    /**
     * Retorna o próximo elemento na iteração e avança o iterador para o nó seguinte.
     *
     * @return O próximo elemento na iteração.
     * @throws NoSuchElementException Se a iteração não tiver mais elementos.
     */
    @Override
    public T next()
    {
        if (! hasNext())
            throw new NoSuchElementException();

        T result = current.getElement();
        current = current.getNext(); // Move para o próximo nó
        return result;
    }

    /**
     * A operação de remoção não é suportada por este iterador.
     *
     * @throws UnsupportedOperationException Sempre lançada, pois o método não é suportado.
     */
    @Override
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}