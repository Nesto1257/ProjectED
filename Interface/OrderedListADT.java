package Interface;


/**
 * Define a interface para uma Lista Ordenada (Ordered List).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que estende a {@code ListADT}
 * e garante que os elementos são mantidos numa ordem específica.
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface OrderedListADT<T> extends ListADT<T>
{
    /**
     * Adiciona o elemento especificado à lista, mantendo a lista na sua ordem adequada.
     * * @param element O elemento a ser adicionado à lista.
     */
    public void add (T element);
}