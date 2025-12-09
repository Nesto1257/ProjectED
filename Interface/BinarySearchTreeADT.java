package Interface;

/**
 * Define a interface para uma Árvore Binária de Pesquisa (Binary Search Tree - BST).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que estende a interface {@code BinaryTreeADT}.
 * As operações garantem que a estrutura da árvore permaneça ordenada de acordo com
 * as propriedades de uma BST (elementos esquerdos menores, elementos direitos maiores).
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T>
{
    /**
     * Adiciona o elemento especificado a esta Árvore Binária de Pesquisa.
     * * @param element O elemento a ser adicionado à árvore.
     */
    public void addElement (T element);

    /**
     * Remove uma e apenas uma ocorrência do elemento alvo especificado desta árvore
     * e retorna uma referência para ele.
     * Lança uma {@code ElementNotFoundException} se o elemento não for encontrado.
     * * @param targetElement O elemento a ser removido.
     * @return Uma referência ao elemento removido.
     */
    public T removeElement (T targetElement);

    /**
     * Remove todas as ocorrências do elemento alvo especificado desta árvore.
     * * @param targetElement O elemento a ser removido.
     */
    public void removeAllOccurrences (T targetElement);

    /**
     * Remove o elemento com o valor mínimo desta árvore e retorna uma referência para ele.
     * * @return Uma referência ao elemento mínimo.
     */
    public T removeMin();

    /**
     * Remove o elemento com o valor máximo desta árvore e retorna uma referência para ele.
     * * @return Uma referência ao elemento máximo.
     */
    public T removeMax();

    /**
     * Retorna uma referência ao elemento com o valor mínimo nesta árvore.
     * * @return Uma referência ao elemento mínimo.
     */
    public T findMin();

    /**
     * Retorna uma referência ao elemento com o valor máximo nesta árvore.
     * * @return Uma referência ao elemento máximo.
     */
    public T findMax();

}