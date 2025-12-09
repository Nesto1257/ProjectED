package Structures;

import java.util.*;

/**
 * Implementa um iterador para um array de elementos de forma a permitir
 * o percurso da coleção de elementos de um array, respeitando o número de elementos
 * válidos ({@code count}).
 * Implementa a interface {@code Iterator}.
 *
 * @param <T> O tipo de elementos que o iterador irá percorrer.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class ArrayIterator<T> implements Iterator
{
    /** O número de elementos válidos na coleção. */
    private int count;

    /** A posição atual na iteração. */
    private int current;

    /** O array que contém os elementos a serem iterados. */
    private T[] items;


    /**
     * Cria uma nova instância de {@code ArrayIterator}.
     *
     * @param collection O array de elementos a ser percorrido.
     * @param size O número real de elementos válidos no array (o tamanho lógico).
     */
    public ArrayIterator (T[] collection, int size)
    {
        items = collection;
        count = size;
        current = 0;
    }

    /**
     * Retorna 'verdadeiro' se a iteração ainda tiver elementos a serem percorrido.
     * * @return 'Verdadeiro' se existirem mais elementos; 'Falso', caso contrário.
     */
    @Override
    public boolean hasNext()
    {
        return (current < count);
    }

    /**
     * Retorna o próximo elemento na iteração.
     * Avança o iterador para o próximo elemento.
     *
     * @return O próximo elemento na iteração.
     * @throws NoSuchElementException Se a iteração não tiver mais elementos.
     */
    @Override
    public T next()
    {
        if (! hasNext())
            throw new NoSuchElementException();

        current++;

        return items[current - 1];
    }

    /**
     * Remove da coleção o último elemento devolvido pelo iterador.
     * Esta operação não é suportada por esta implementação.
     *
     * @throws UnsupportedOperationException Sempre lançada, pois o método não é suportado.
     */
    @Override
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}