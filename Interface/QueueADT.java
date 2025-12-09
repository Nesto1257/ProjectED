package Interface;

/**
 * Define a interface para uma Fila (Queue), implementando o princípio FIFO (First-In, First-Out).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que especifica as operações
 * básicas de enfileirar, desenfileirar e inspecionar o elemento da frente.
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface QueueADT<T>
{

    /**
     * Adiciona um elemento ao final (rear) desta fila.
     * * @param element O elemento a ser adicionado ao final desta fila.
     */
    public void enqueue (T element);

    /**
     * Remove e retorna o elemento que está na frente (front) desta fila.
     * Lança {@code EmptyCollectionException} se a fila estiver vazia.
     * * @return O elemento na frente desta fila.
     */
    public T dequeue();

    /**
     * Retorna, sem o remover, o elemento que está na frente (front) desta fila.
     * Lança {@code EmptyCollectionException} se a fila estiver vazia.
     * * @return O primeiro elemento desta fila.
     */
    public T first();

    /**
     * Retorna 'verdadeiro' se esta fila não contiver elementos.
     * * @return 'Verdadeiro' se esta fila estiver vazia.
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos nesta fila.
     * * @return A representação inteira do tamanho desta fila.
     */
    public int size();

    /**
     * Retorna uma representação em 'string' desta fila.
     * * @return A representação em 'string' desta fila.
     */
    @Override
    public String toString();
}