package Structures;

import Interface.BinaryTreeADT;
import Exceptions.*;
import java.util.Iterator;

/**
 * Implementa uma Árvore Binária (Binary Tree) usando um array para armazenar
 * os elementos.
 * Implementa a interface {@code BinaryTreeADT}.
 * A estrutura utiliza a convenção de que, para um nó no índice $i$, o seu filho
 * esquerdo está em $2i+1$ e o seu filho direito está em $2i+2$.
 *
 * @param <T> O tipo de elementos armazenados na árvore.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class ArrayBinaryTree<T> implements BinaryTreeADT<T>
{

    /** O número de elementos atualmente contidos na árvore. */
    protected int count;

    /** O array que armazena os elementos da árvore. */
    protected T[] tree;

    /** A capacidade inicial e o tamanho de expansão do array. */
    private final int capacity = 50;

    /**
     * Cria uma Árvore Binária vazia com a capacidade predefinida.
     */
    public ArrayBinaryTree()
    {
        count = 0;
        tree = (T[]) new Object[capacity];
    }

    /**
     * Cria uma Árvore Binária com o elemento especificado como a sua raiz.
     * * @param element O elemento que será a raiz da nova árvore.
     */
    public ArrayBinaryTree (T element)
    {
        count = 1;
        tree = (T[]) new Object[capacity];

        tree[0] = element;
    }

    /**
     * Aumenta a capacidade do array que armazena os elementos da árvore, duplicando o seu tamanho.
     * Este é um método auxiliar e protegido.
     */
    protected void expandCapacity()
    {
        T[] temp = (T[]) new Object[tree.length * 2];
        for (int ct=0; ct < tree.length; ct++)
            temp[ct] = tree[ct];
        tree = temp;
    }

    /**
     * Remove toda a subárvore esquerda desta árvore.
     * * Nota: A implementação atual está vazia (não funcional).
     */
    @Override
    public void removeLeftSubtree()
    {
        // A implementação deve ser adicionada
    }

    /**
     * Remove toda a subárvore direita desta árvore.
     * * Nota: A implementação atual está vazia (não funcional).
     */
    @Override
    public void removeRightSubtree()
    {
        // A implementação deve ser adicionada
    }

    /**
     * Remove todos os elementos da árvore binária, resultando numa árvore vazia.
     */
    @Override
    public void removeAllElements()
    {
        count = 0;
        for (int ct=0; ct<tree.length; ct++)
            tree[ct] = null;
    }

    /**
     * Retorna 'verdadeiro' se a árvore binária estiver vazia e 'falso' caso contrário.
     * * @return 'Verdadeiro' se a árvore não contiver elementos; 'Falso', caso contrário.
     */
    @Override
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Retorna o número de elementos contidos na árvore binária.
     * * @return O número de elementos na árvore.
     */
    @Override
    public int size()
    {
        return count;
    }

    /**
     * Retorna 'verdadeiro' se a árvore contiver um elemento que corresponde
     * ao elemento alvo especificado e 'falso' caso contrário.
     * * @param targetElement O elemento alvo cuja presença será verificada.
     * @return 'Verdadeiro' se o elemento alvo for encontrado; 'Falso', caso contrário.
     */
    @Override
    public boolean contains (T targetElement)
    {
        boolean found = false;

        for (int ct=0; ct<count && !found; ct++)
            if (targetElement.equals(tree[ct]))
                found = true;

        return found;

    }

    /**
     * Retorna uma referência ao elemento alvo especificado se for
     * encontrado na árvore binária.
     * Lança {@code ElementNotFoundException} se o elemento alvo não for encontrado.
     * * @param targetElement O elemento alvo a ser encontrado.
     * @return Uma referência ao elemento alvo.
     * @throws ElementNotFoundException Se o elemento alvo não for encontrado na árvore.
     */
    @Override
    public T find (T targetElement) throws ElementNotFoundException
    {
        T temp=null;
        boolean found = false;

        for (int ct=0; ct<count && !found; ct++)
            if (targetElement.equals(tree[ct]))
            {
                found = true;
                temp = tree[ct];
            }

        if (!found)
            throw new ElementNotFoundException("árvore binária");

        return temp;


    }

    /**
     * Retorna uma representação em 'string' da árvore binária,
     * baseada num percurso em Ordem (In-Order).
     * * @return Uma 'string' representando a árvore.
     */
    @Override
    public String toString()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder (0, templist);
        return templist.toString();
    }

    /**
     * Realiza um percurso em Ordem (In-Order) na árvore binária e retorna um iterador
     * que atravessa os elementos nessa ordem.
     * * @return Um iterador sobre os elementos da árvore na ordem In-Order.
     */
    @Override
    public Iterator<T> iteratorInOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder (0, templist);
        return templist.iterator();
    }

    /**
     * Realiza um percurso recursivo em Ordem (In-Order) a partir do nó especificado.
     * Os elementos são adicionados à lista temporária.
     * * @param node O índice do nó atual.
     * @param templist A lista onde os elementos são armazenados durante o percurso.
     */
    protected void inorder (int node, ArrayUnorderedList<T> templist)
    {
        if (node < tree.length)
            if (tree[node] != null)
            {
                inorder (node*2+1, templist);           // Filho Esquerdo
                templist.addToRear(tree[node]);
                inorder (node*2+2, templist);           // Filho Direito
            }//if

    }

    /**
     * Realiza um percurso Pré-Ordem (Pre-Order) na árvore binária e retorna um iterador
     * que atravessa os elementos nessa ordem.
     * * @return Um iterador sobre os elementos da árvore na ordem Pre-Order.
     */
    @Override
    public Iterator<T> iteratorPreOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder (0, templist);
        return templist.iterator();
    }

    /**
     * Realiza um percurso recursivo Pré-Ordem (Pre-Order) a partir do nó especificado.
     * Os elementos são adicionados à lista temporária.
     * * @param node O índice do nó atual.
     * @param templist A lista onde os elementos são armazenados durante o percurso.
     */
    protected void preorder (int node, ArrayUnorderedList<T> templist)
    {
        if (node < tree.length)
            if (tree[node] != null)
            {
                templist.addToRear(tree[node]);
                preorder (node*2+1, templist);          // Filho Esquerdo
                preorder (node*2+2, templist);          // Filho Direito
            }//if



    }

    /**
     * Realiza um percurso Pós-Ordem (Post-Order) na árvore binária e retorna um iterador
     * que atravessa os elementos nessa ordem.
     * * @return Um iterador sobre os elementos da árvore na ordem Post-Order.
     */
    @Override
    public Iterator<T> iteratorPostOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postorder (0, templist);
        return templist.iterator();
    }

    /**
     * Realiza um percurso recursivo Pós-Ordem (Post-Order) a partir do nó especificado.
     * Os elementos são adicionados à lista temporária.
     * * @param node O índice do nó atual.
     * @param templist A lista onde os elementos são armazenados durante o percurso.
     */
    protected void postorder (int node, ArrayUnorderedList<T> templist)
    {
        if (node < tree.length)
            if (tree[node] != null)
            {
                postorder (node*2+1, templist);         // Filho Esquerdo
                postorder (node*2+2, templist);         // Filho Direito
                templist.addToRear(tree[node]);

            }//if


    }

    /**
     * Realiza um percurso por Nível (Level-Order) na árvore binária.
     * * @return Um iterador sobre os elementos da árvore na ordem Level-Order.
     */
    @Override
    public Iterator<T> iteratorLevelOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        for (int ct=0; ct<count; ct++)
            templist.addToRear(tree[ct]);
        return templist.iterator();
    }
}