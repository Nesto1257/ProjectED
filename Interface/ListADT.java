package Interface;

import java.util.Iterator;
import Exceptions.*;

/**
 * Define a interface para uma Lista (List).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que especifica as operações
 * básicas para a manipulação de uma coleção linear de elementos.
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface ListADT<T>
{
    /**
     * Remove e retorna o primeiro elemento da lista.
     * Lança {@code EmptyCollectionException} se a lista estiver vazia.
     * * @return O primeiro elemento da lista.
     */
    public T removeFirst ();

    /**
     * Remove e retorna o último elemento da lista.
     * Lança {@code EmptyCollectionException} se a lista estiver vazia.
     * * @return O último elemento da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    public T removeLast () throws EmptyCollectionException;

    /**
     * Remove e retorna a primeira ocorrência do elemento especificado da lista.
     * Lança {@code ElementNotFoundException} se o elemento alvo não for encontrado.
     * * @param element O elemento a ser removido.
     * @return O elemento removido.
     */
    public T remove (T element);

    /**
     * Retorna o primeiro elemento da lista sem o remover.
     * Lança {@code EmptyCollectionException} se a lista estiver vazia.
     * * @return O primeiro elemento da lista.
     */
    public T first ();

    /**
     * Retorna o último elemento da lista sem o remover.
     * Lança {@code EmptyCollectionException} se a lista estiver vazia.
     * * @return O último elemento da lista.
     */
    public T last ();

    /**
     * Verifica se a lista contém o elemento alvo especificado.
     * * @param target O elemento cuja presença será verificada.
     * @return Verdadeiro se o elemento alvo for encontrado; Falso, caso contrário.
     */
    public boolean contains (T target);

    /**
     * Verifica se a lista está vazia.
     * * @return Verdadeiro se a lista não contém elementos; Falso, caso contrário.
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos contidos na lista.
     * * @return O número de elementos na lista.
     */
    public int size();

    /**
     * Retorna um iterador sobre os elementos contidos na lista, na sua ordem própria.
     * * @return Um iterador que pode ser usado para atravessar a lista.
     */
    public Iterator<T> iterator();

    /**
     * Retorna uma representação em 'string' da lista.
     * * @return Uma 'string' que representa a lista.
     */
    @Override
    public String toString();
}