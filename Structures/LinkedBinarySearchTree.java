package Structures;

import Interface.*;
import Exceptions.*;

/**
 * Implementa uma Árvore Binária de Pesquisa (Binary Search Tree - BST) usando nós ligados
 * (Linked Nodes).
 * Estende {@code LinkedBinaryTree} e implementa a interface {@code BinarySearchTreeADT}.
 * As operações de inserção e remoção mantêm as propriedades de pesquisa binária.
 *
 * @param <T> O tipo de elementos armazenados na árvore, que deve ser {@code Comparable}.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class LinkedBinarySearchTree<T>  extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {

    /**
     * Cria uma Árvore Binária de Pesquisa vazia.
     */
    public LinkedBinarySearchTree()
    {
        super();
    }

    /**
     * Cria uma Árvore Binária de Pesquisa com o elemento especificado como a sua raiz.
     *
     * @param element O elemento que será a raiz da nova árvore.
     */
    public LinkedBinarySearchTree (T element)
    {
        super (element);
    }

    /**
     * Adiciona o objeto especificado à Árvore Binária de Pesquisa na posição
     * apropriada de acordo com o seu valor chave. Elementos iguais são adicionados à direita.
     *
     * @param element O elemento a ser adicionado à árvore.
     * @throws NonComparableElementException Se o elemento não for comparável.
     */
    @Override
    public void addElement (T element)
    {

        BinaryTreeNode<T> temp = new BinaryTreeNode<T> (element);
        Comparable<T> comparableElement = (Comparable<T>)element;

        if (isEmpty())
            root = temp;
        else
        {
            BinaryTreeNode<T> current = root;
            boolean added = false;

            while (!added)
            {
                if (comparableElement.compareTo(current.element) < 0)

                    if (current.left == null)
                    {
                        current.left = temp;
                        added = true;
                    }
                    else
                        current = current.left;
                else
                if (current.right == null)
                {
                    current.right = temp;
                    added = true;
                }
                else
                    current = current.right;
            }//while
        }//else

        count++;

    }

    /**
     * Remove o primeiro elemento que corresponde ao elemento alvo especificado
     * da Árvore Binária de Pesquisa e retorna uma referência a ele.
     *
     * @param targetElement O elemento alvo a ser removido.
     * @return Uma referência ao elemento removido.
     * @throws ElementNotFoundException Se o elemento alvo especificado não for encontrado na árvore.
     */
    @Override
    public T removeElement (T targetElement) throws
            ElementNotFoundException
    {

        T result = null;

        if (!isEmpty())

            if (((Comparable)targetElement).equals(root.element))
            {
                result =  root.element;
                root = replacement (root);
                count--;
            }
            else
            {
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;

                if (((Comparable)targetElement).compareTo(root.element) < 0)
                    current = root.left;
                else
                    current = root.right;

                while (current != null && !found)
                {
                    if (targetElement.equals(current.element))
                    {
                        found = true;
                        count--;
                        result =  current.element;

                        if (current == parent.left)
                        {
                            parent.left = replacement (current);
                        }
                        else
                        {
                            parent.right = replacement (current);
                        }
                    }
                    else
                    {
                        parent = current;

                        if (((Comparable)targetElement).compareTo(current.element) < 0)
                            current = current.left;
                        else
                            current = current.right;
                    }
                }
                if (!found)
                    throw new ElementNotFoundException("árvore binária");
            }

        return result;

    }

    /**
     * Remove todas as ocorrências do elemento alvo especificado da Árvore Binária de Pesquisa.
     *
     * @param targetElement O elemento alvo a ser removido.
     * @throws ElementNotFoundException Se o elemento alvo especificado não for encontrado na árvore.
     */
    @Override
    public void removeAllOccurrences (T targetElement) throws
            ElementNotFoundException
    {
        removeElement(targetElement);

        try
        {
            while (contains( (T) targetElement))
                removeElement(targetElement);
        }
        catch (Exception ElementNotFoundException)
        {
            // Captura a exceção de elemento não encontrado, que é esperada quando o elemento é totalmente removido.
        }

    }

    /**
     * Remove o nó com o valor mínimo da Árvore Binária de Pesquisa e retorna uma referência ao seu elemento.
     *
     * @return Uma referência ao elemento mínimo removido.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    @Override
    public T removeMin() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else
        {
            if (root.left == null)
            {
                result = root.element;
                root = root.right;
            }
            else
            {
                BinaryTreeNode<T> parent = root;
                BinaryTreeNode<T> current = root.left;
                while (current.left != null)
                {
                    parent = current;
                    current = current.left;
                }
                result =  current.element;
                parent.left = current.right;
            }

            count--;
        }

        return result;

    }

    /**
     * Remove o nó com o valor máximo da Árvore Binária de Pesquisa e retorna uma referência ao seu elemento.
     *
     * @return Uma referência ao elemento máximo removido.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    @Override
    public T removeMax() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else
        {
            if (root.right == null)
            {
                result =  root.element;
                root = root.left;
            }
            else
            {
                BinaryTreeNode<T> parent = root;
                BinaryTreeNode<T> current = root.right;

                while (current.right != null)
                {
                    parent = current;
                    current = current.right;
                }

                result =  current.element;
                parent.right = current.left;
            }

            count--;
        }

        return result;

    }

    /**
     * Retorna o elemento com o valor mínimo na Árvore Binária de Pesquisa, sem o remover.
     *
     * @return Uma referência ao elemento mínimo.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    @Override
    public T findMin() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else
        {
            BinaryTreeNode<T> current = root;

            while (current.left != null)
                current = current.left;

            result = current.element;
        }

        return result;

    }

    /**
     * Retorna o elemento com o valor máximo na Árvore Binária de Pesquisa, sem o remover.
     *
     * @return Uma referência ao elemento máximo.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    @Override
    public T findMax() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else
        {
            BinaryTreeNode<T> current = root;

            while (current.right != null)
                current = current.right;

            result = current.element;
        }

        return result;

    }

    /**
     * Retorna uma referência ao elemento alvo especificado se for encontrado na árvore binária.
     *
     * @param targetElement O elemento alvo a ser encontrado.
     * @return Uma referência ao elemento alvo.
     * @throws ElementNotFoundException Se o elemento alvo especificado não for encontrado na árvore.
     */
    @Override
    public T find (T targetElement) throws ElementNotFoundException
    {
        // Nota: A lógica de pesquisa deste método 'find' é complexa e envolve um método auxiliar.
        // É mantida conforme o código original.

        BinaryTreeNode<T> current = root;
        BinaryTreeNode<T> temp = current;


        if (!(current.element.equals(targetElement)) && (current.left !=null)&&(((Comparable)current.element).compareTo(targetElement) > 0))
            current = findagain( targetElement, current.left);

        else if (!(current.element.equals(targetElement)) && (current.right != null))
            current = findagain( targetElement, current.right);

        if (!(current.element.equals(targetElement)))
            throw new ElementNotFoundException ("árvore binária");

        return current.element;

    }

    /**
     * Retorna uma referência ao nó do elemento alvo especificado se for encontrado na subárvore.
     * Este é um método auxiliar recursivo.
     *
     * @param targetElement O elemento alvo a ser encontrado.
     * @param next O nó inicial para a pesquisa recursiva.
     * @return O nó que contém o elemento alvo.
     */
    private BinaryTreeNode<T> findagain (T targetElement, BinaryTreeNode<T> next)
    {
        BinaryTreeNode<T> current = next;
        if (!(next.element.equals(targetElement)) && (next.left !=null) &&(((Comparable)next.element).compareTo(targetElement) > 0))
            next = findagain( targetElement, next.left);
        else if (!(next.element.equals(targetElement)) && (next.right != null))
            next = findagain( targetElement, next.right);

        return next;

    }


    /**
     * Retorna uma referência a um nó que irá substituir o nó especificado para remoção.
     * Nos casos em que o nó removido tem dois filhos, o sucessor em ordem (inorder successor)
     * é usado como substituto.
     *
     * @param node O nó a ser substituído/removido.
     * @return O nó que deve tomar o lugar do nó removido.
     */
    protected BinaryTreeNode<T> replacement (BinaryTreeNode<T> node)
    {
        BinaryTreeNode<T> result = null;

        if ((node.left == null)&&(node.right==null))
            result = null;
        else if ((node.left != null)&&(node.right==null))
            result = node.left;
        else if ((node.left == null)&&(node.right != null))
            result = node.right;
        else
        {
            BinaryTreeNode<T> current = node.right;
            BinaryTreeNode<T> parent = node;

            while (current.left != null)
            {
                parent = current;
                current = current.left;
            }

            if (node.right == current)
                current.left = node.left;
            else
            {
                parent.left = current.right;
                current.right = node.right;
                current.left = node.left;
            }
            result = current;
        }
        return result;


    }

}