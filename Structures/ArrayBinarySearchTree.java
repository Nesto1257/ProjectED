package Structures;

import java.util.Iterator;
import Interface.*;
import Exceptions.*;

/**
 * Implementa uma Árvore Binária de Pesquisa (Binary Search Tree - BST) utilizando
 * um array para representar a estrutura da árvore.
 * Estende {@code ArrayBinaryTree} e implementa a interface {@code BinarySearchTreeADT}.
 * As operações de inserção mantêm a propriedade de pesquisa binária.
 *
 * @param <T> O tipo de elementos armazenados na árvore, que deve ser {@code Comparable}.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class ArrayBinarySearchTree<T>  extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    /**
     * A altura atual da árvore (número de níveis).
     */
    protected int height;

    /**
     * O índice mais alto do array que contém um elemento não nulo.
     */
    protected int maxIndex;

    /**
     * Cria uma Árvore Binária de Pesquisa vazia.
     */
    public ArrayBinarySearchTree()
    {
        super();
        height = 0;
        maxIndex = -1;
    }

    /**
     * Cria uma Árvore Binária de Pesquisa com o elemento especificado como a sua raiz.
     * * @param element O elemento que será a raiz da nova árvore.
     */
    public ArrayBinarySearchTree (T element)
    {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    /**
     * Adiciona o elemento especificado à Árvore Binária de Pesquisa na posição
     * apropriada de acordo com o seu valor chave. Elementos iguais são adicionados à direita.
     *
     * @param element O elemento a ser adicionado à árvore.
     */
    public void addElement (T element)
    {

        if (tree.length < maxIndex*2+3)
            expandCapacity();

        Comparable<T> tempelement = (Comparable<T>)element;

        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        }
        else
        {
            boolean added = false;
            int currentIndex = 0;

            while (!added)
            {
                if (tempelement.compareTo((tree[currentIndex]) ) < 0)
                {
                    // go left
                    if (tree[currentIndex*2+1] == null)
                    {
                        tree[currentIndex*2+1] = element;
                        added = true;
                        if (currentIndex*2+1 > maxIndex)
                            maxIndex = currentIndex*2+1;
                    }
                    else
                        currentIndex = currentIndex*2+1;
                }
                else {
                    // go right
                    if (tree[currentIndex*2+2] == null)
                    {
                        tree[currentIndex*2+2] = element;
                        added = true;
                        if (currentIndex*2+2 > maxIndex)
                            maxIndex = currentIndex*2+2;
                    }
                    else
                        currentIndex = currentIndex*2+2;
                }

            }//while
        }//else

        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;
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
    public T removeElement (T targetElement) throws
            ElementNotFoundException
    {
        T result = null;
        boolean found = false;

        if (isEmpty())
            return result;

        for (int i = 0; (i <= maxIndex) && !found; i++) {
            if ((tree[i] != null) && targetElement.equals(tree[i]))
            {
                found = true;
                result = tree[i] ;
                replace(i);
                count--;
            }
        }

        if (!found)
            throw new ElementNotFoundException("elemento não encontrado na árvore binária");

        int temp = maxIndex;
        maxIndex = -1;
        for (int i = 0; i <= temp; i++)
            if (tree[i] != null)
                maxIndex = i;

        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;

    }

    /**
     * Retorna uma cópia do array que contém os valores da árvore.
     * * @return Uma cópia do array interno da árvore.
     */
    public T[] getArray()
    {
        T[] temp;
        if (size() == 0) {
            temp = (T[]) new Object[0];
            return temp;
        }

        temp = (T[]) new Object[tree.length];
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null)
                temp[i] = tree[i];
            else
                temp[i] = null;
        }
        return temp;
    }

    /**
     * Retorna a altura (número de níveis) da árvore.
     * * @return A altura da árvore.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Retorna o índice máximo do array que contém um elemento.
     * * @return O índice máximo do array.
     */
    public int getMaxIndex()
    {
        return maxIndex;
    }

    /**
     * Remove todos os elementos da árvore binária.
     */
    public void removeAllElements()
    {
        super.removeAllElements();
        height = 0;
        maxIndex = -1;
    }

    /**
     * Remove todas as ocorrências do elemento alvo especificado da Árvore Binária de Pesquisa.
     *
     * @param targetElement O elemento alvo a ser removido.
     * @throws ElementNotFoundException Se o elemento alvo especificado não for encontrado na árvore.
     */
    public void removeAllOccurrences (T targetElement) throws
            ElementNotFoundException
    {
        removeElement(targetElement);

        while (contains(targetElement))
            removeElement(targetElement);

    }

    /**
     * Remove o nó com o valor mínimo da Árvore Binária de Pesquisa e retorna uma referência ao seu elemento.
     *
     * @return Uma referência ao elemento mínimo removido.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    public T removeMin() throws EmptyCollectionException
    {

        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else
        {
            int currentIndex = 1;
            int previousIndex = 0;
            while (tree[currentIndex] != null && currentIndex <= tree.length)
            {
                previousIndex = currentIndex;
                currentIndex = currentIndex * 2 + 1;
            } //while
            result = tree[previousIndex] ;
            replace(previousIndex);
        } //else

        count--;

        return result;
    }

    /**
     * Remove o nó com o valor máximo da Árvore Binária de Pesquisa e retorna uma referência ao seu elemento.
     *
     * @return Uma referência ao elemento máximo removido.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    public T removeMax() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else
        {
            int currentIndex = 2;
            int previousIndex = 0;
            while (tree[currentIndex] != null && currentIndex <= maxIndex)
            {
                previousIndex = currentIndex;
                currentIndex = currentIndex * 2 + 2;
            } //while
            result = tree[previousIndex] ;
            replace(previousIndex);
        } //else

        count--;

        return result;
    }

    /**
     * Retorna o elemento com o valor mínimo na Árvore Binária de Pesquisa, sem o remover.
     *
     * @return Uma referência ao elemento mínimo.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    public T findMin() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else {
            int currentIndex = 0;
            while ((currentIndex*2+1 <= maxIndex) && (tree[currentIndex*2+1] != null))
                currentIndex = currentIndex*2+1;
            result = tree[currentIndex] ;
        }
        return result;
    }

    /**
     * Retorna o elemento com o valor máximo na Árvore Binária de Pesquisa, sem o remover.
     *
     * @return Uma referência ao elemento máximo.
     * @throws EmptyCollectionException Se a Árvore Binária de Pesquisa estiver vazia.
     */
    public T findMax() throws EmptyCollectionException
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException ("árvore binária");
        else {
            int currentIndex = 0;
            while ((currentIndex*2+2 <= maxIndex) && (tree[currentIndex*2+2] != null))
                currentIndex = currentIndex*2+2;
            result = tree[currentIndex] ;
        }
        return result;
    }


    /**
     * Remove o nó especificado para remoção e reorganiza o array da árvore de acordo.
     * Este método é auxiliar e protegido.
     *
     * @param targetIndex O índice do nó a ser substituído/removido.
     */
    protected void replace (int targetIndex)
    {
        int currentIndex, parentIndex, temp, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<Integer>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<Integer>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<Integer>();
        Iterator<Integer> oldIt, newIt;

        // if target node has no children
        if ((targetIndex*2+1 >= tree.length) || (targetIndex*2+2 >= tree.length))
            tree[targetIndex] = null;

            // if target node has no children
        else if ((tree[targetIndex*2+1] == null) && (tree[targetIndex*2+2] == null))
            tree[targetIndex] = null;

            // if target node only has a left child
        else if ((tree[targetIndex*2+1] != null) && (tree[targetIndex*2+2] == null)) {

            // fill newlist with indices of nodes that will replace
            // the corresponding indices in oldlist

            // fill newlist
            currentIndex = targetIndex*2+1;
            templist.addToRear(new Integer(currentIndex));
            while (!templist.isEmpty()) {
                currentIndex = ((Integer)templist.removeFirst()).intValue();
                newlist.addToRear(new Integer(currentIndex));
                if ((currentIndex*2+2) <= (Math.pow(2,height)-2)) {
                    templist.addToRear(new Integer(currentIndex*2+1));
                    templist.addToRear(new Integer(currentIndex*2+2));
                }
            }

            // fill oldlist
            currentIndex = targetIndex;
            templist.addToRear(new Integer(currentIndex));
            while (!templist.isEmpty()) {
                currentIndex = ((Integer)templist.removeFirst()).intValue();
                oldlist.addToRear(new Integer(currentIndex));
                if ((currentIndex*2+2) <= (Math.pow(2,height)-2)) {
                    templist.addToRear(new Integer(currentIndex*2+1));
                    templist.addToRear(new Integer(currentIndex*2+2));
                }
            }

            // do replacement
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        }

        // if target node only has a right child
        else if ((tree[targetIndex*2+1] == null) && (tree[targetIndex*2+2] != null)) {

            // fill newlist with indices of nodes that will replace
            // the corresponding indices in oldlist

            // fill newlist
            currentIndex = targetIndex*2+2;
            templist.addToRear(new Integer(currentIndex));
            while (!templist.isEmpty()) {
                currentIndex = ((Integer)templist.removeFirst()).intValue();
                newlist.addToRear(new Integer(currentIndex));
                if ((currentIndex*2+2) <= (Math.pow(2,height)-2)) {
                    templist.addToRear(new Integer(currentIndex*2+1));
                    templist.addToRear(new Integer(currentIndex*2+2));
                }
            }

            // fill oldlist
            currentIndex = targetIndex;
            templist.addToRear(new Integer(currentIndex));
            while (!templist.isEmpty()) {
                currentIndex = ((Integer)templist.removeFirst()).intValue();
                oldlist.addToRear(new Integer(currentIndex));
                if ((currentIndex*2+2) <= (Math.pow(2,height)-2)) {
                    templist.addToRear(new Integer(currentIndex*2+1));
                    templist.addToRear(new Integer(currentIndex*2+2));
                }
            }

            // do replacement
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        }

        // target node has two children
        else
        {
            currentIndex = targetIndex*2+2;

            while (tree[currentIndex*2+1] != null) {
                currentIndex = currentIndex*2+1;
            }

            tree[targetIndex] = tree[currentIndex];

            // the index of the root of the subtree to be replaced
            int currentRoot = currentIndex;

            // if currentIndex has a right child
            if (tree[currentRoot*2+2] != null) {

                // fill newlist with indices of nodes that will replace
                // the corresponding indices in oldlist

                // fill newlist
                currentIndex = currentRoot*2+2;
                templist.addToRear(new Integer(currentIndex));
                while (!templist.isEmpty()) {
                    currentIndex = ((Integer)templist.removeFirst()).intValue();
                    newlist.addToRear(new Integer(currentIndex));
                    if ((currentIndex*2+2) <= (Math.pow(2,height)-2)) {
                        templist.addToRear(new Integer(currentIndex*2+1));
                        templist.addToRear(new Integer(currentIndex*2+2));
                    }
                }

                // fill oldlist
                currentIndex = currentRoot;
                templist.addToRear(new Integer(currentIndex));
                while (!templist.isEmpty()) {
                    currentIndex = ((Integer)templist.removeFirst()).intValue();
                    oldlist.addToRear(new Integer(currentIndex));
                    if ((currentIndex*2+2) <= (Math.pow(2,height)-2)) {
                        templist.addToRear(new Integer(currentIndex*2+1));
                        templist.addToRear(new Integer(currentIndex*2+2));
                    }
                }

                // do replacement
                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();
                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            }
            else
                tree[currentRoot] = null;
        }

    }

    /**
     * Retorna uma representação em 'string' desta Árvore Binária de Pesquisa,
     * listando os elementos não nulos do array.
     *
     * @return Uma 'string' representando a árvore.
     * @throws EmptyCollectionException Não lança exceção, mas a assinatura é mantida para compatibilidade.
     */
    public String toString() throws EmptyCollectionException
    {
        String result = "";

        for (int i = 0; i <= maxIndex; i++)
            if (tree[i] != null)
                result += tree[i] .toString() + "\n";

        return result;
    }

    /**
     * Retorna uma representação em 'string' formatada desta Árvore Binária de Pesquisa,
     * visualmente organizada por níveis.
     *
     * @return Uma 'string' que representa a árvore, formatada por nível.
     * @throws EmptyCollectionException Se a árvore estiver vazia.
     */
    public String toString2() throws EmptyCollectionException
    {
        String result = "";
        int counter = 1;
        int level = 1;
        int index = 1;

        if (isEmpty())
            return result;

        for (int i = 0; i < Math.pow(2, height-level)-1; i++)
            result += " ";
        result += tree[0] .toString();

        while (index <= maxIndex) {
            if (index == counter) {
                counter = counter*2 + 1;
                level++;
                result += "\n";
                for (int i = 0; i < Math.pow(2, height-level)-1; i++)
                    result += " ";
            }
            if (tree[index] != null)
                result += tree[index] .toString();
            else
                result += " ";
            for (int i = 0; i < Math.pow(2, height-level+1)-1; i++)
                result += " ";
            index++;
        }

        return result;
    }

}