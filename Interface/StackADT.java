package Interface;

/**
 * Define a interface para uma Pilha (Stack), implementando o princípio LIFO (Last-In, First-Out).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que especifica as operações
 * básicas de empilhar, desempilhar e inspecionar o elemento do topo.
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface StackADT<T> {

    /** * Adiciona um elemento ao topo desta pilha (operação 'push').
     * * @param element O elemento a ser empilhado.
     */
    public void push(T element);

    /** * Remove e retorna o elemento que está no topo desta pilha (operação 'pop').
     * Lança {@code EmptyStackException} se a pilha estiver vazia.
     * * @return O elemento removido do topo da pilha.
     */
    public T pop();

    /** * Retorna, sem o remover, o elemento que está no topo desta pilha (operação 'peek').
     * Lança {@code EmptyStackException} se a pilha estiver vazia.
     * * @return O elemento que está no topo da pilha.
     */
    public T peek();

    /** * Retorna 'verdadeiro' se esta pilha não contiver elementos.
     * * @return Valor booleano que indica se a pilha está vazia.
     */
    public boolean isEmpty();

    /** * Retorna o número de elementos nesta pilha.
     * * @return O número de elementos nesta pilha.
     */
    public int size();

    /** * Retorna uma representação em 'string' desta pilha.
     * * @return A representação em 'string' desta pilha.
     */
    @Override
    public String toString();
}