package Interface;

import java.util.Iterator;

/**
 * Define a interface para uma Árvore Binária (Binary Tree - BT).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que especifica as operações
 * básicas e de percurso para uma estrutura de árvore binária.
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface BinaryTreeADT<T> {

    /**
     * Remove toda a subárvore esquerda desta árvore.
     * Após a remoção, a árvore terá apenas o seu nó raiz e a subárvore direita.
     */
    public void removeLeftSubtree();

    /**
     * Remove toda a subárvore direita desta árvore.
     * Após a remoção, a árvore terá apenas o seu nó raiz e a subárvore esquerda.
     */
    public void removeRightSubtree();

    /**
     * Remove todos os elementos da árvore, resultando numa árvore vazia.
     */
    public void removeAllElements();

    /**
     * Determina se a árvore binária está vazia.
     * * @return Verdadeiro se a árvore não contém elementos; Falso, caso contrário.
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos contidos nesta árvore.
     * * @return O número de elementos na árvore.
     */
    public int size();

    /**
     * Determina se o elemento alvo especificado está contido nesta árvore.
     * * @param targetElement O elemento alvo cuja presença será verificada.
     * @return Verdadeiro se o elemento alvo for encontrado; Falso, caso contrário.
     */
    public boolean contains (T targetElement);

    /**
     * Retorna uma referência ao elemento alvo especificado, se este for encontrado nesta árvore.
     * Lança uma {@code ElementNotFoundException} se o elemento não for encontrado.
     * * @param targetElement O elemento alvo a ser encontrado.
     * @return Uma referência ao elemento alvo, se encontrado.
     */
    public T find (T targetElement);

    /**
     * Gera uma representação em 'string' desta árvore binária.
     * * @return Uma 'string' representando a árvore.
     */
    @Override
    public String toString();

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso em Ordem (In-Order).
     * * @return Um iterador sobre os elementos da árvore.
     */
    public Iterator<T> iteratorInOrder();

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso Pré-Ordem (Pre-Order).
     * * @return Um iterador sobre os elementos da árvore.
     */
    public Iterator<T> iteratorPreOrder();

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso Pós-Ordem (Post-Order).
     * * @return Um iterador sobre os elementos da árvore.
     */
    public Iterator<T> iteratorPostOrder();

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso por Nível (Level-Order).
     * * @return Um iterador sobre os elementos da árvore.
     */
    public Iterator<T> iteratorLevelOrder();

}