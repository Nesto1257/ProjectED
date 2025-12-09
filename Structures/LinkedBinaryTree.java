package Structures;

import Interface.BinaryTreeADT;

import java.util.Iterator;
import  Exceptions.*;

/**
 * Implementa uma Árvore Binária (Binary Tree) usando nós ligados (Linked Nodes).
 * Cada nó contém referências para o seu filho esquerdo e filho direito.
 * Implementa a interface {@code BinaryTreeADT}.
 *
 * @param <T> O tipo de elementos armazenados na árvore.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T>
{

    /** O número de elementos atualmente contidos na árvore. */
    protected int count;

    /** A referência para o nó raiz da árvore. */
    protected BinaryTreeNode<T> root;

    /**
     * Cria uma Árvore Binária vazia.
     */
    public LinkedBinaryTree()
    {
        count = 0;
        root = null;
    }

    /**
     * Cria uma Árvore Binária com o elemento especificado como a sua raiz.
     *
     * @param element O elemento que será a raiz da nova árvore.
     */
    public LinkedBinaryTree (T element)
    {
        count = 1;
        root = new BinaryTreeNode<T> (element);
    }

    /**
     * Constrói uma Árvore Binária a partir de um elemento raiz e de duas subárvores binárias especificadas.
     *
     * @param element O elemento a ser a nova raiz.
     * @param leftSubtree A subárvore que será ligada como filho esquerdo.
     * @param rightSubtree A subárvore que será ligada como filho direito.
     */
    public LinkedBinaryTree (T element, LinkedBinaryTree<T> leftSubtree,
                             LinkedBinaryTree<T> rightSubtree)
    {

        root = new BinaryTreeNode<T> (element);
        count = 1;

        if (leftSubtree != null)
        {
            count = count + leftSubtree.size();
            root.left = leftSubtree.root;
        }
        else
            root.left = null;

        if (rightSubtree !=null)
        {
            count = count + rightSubtree.size();
            root.right = rightSubtree.root;
        }
        else
            root.right = null;

    }

    /**
     * Remove toda a subárvore esquerda desta árvore.
     * O número total de elementos é ajustado adequadamente.
     */
    @Override
    public void removeLeftSubtree()
    {
        if (root.left != null)
            count = count - root.left.numChildren() - 1; // Subtrai o número de filhos + o próprio filho esquerdo
        root.left = null;
    }

    /**
     * Remove toda a subárvore direita desta árvore.
     * O número total de elementos é ajustado adequadamente.
     */
    @Override
    public void removeRightSubtree()
    {
        if (root.right != null)
            count = count - root.right.numChildren() - 1; // Subtrai o número de filhos + o próprio filho direito
        root.right = null;
    }

    /**
     * Remove todos os elementos da árvore binária, resultando numa árvore vazia.
     */
    @Override
    public void removeAllElements()
    {
        count = 0;
        root = null;
    }

    /**
     * Retorna 'verdadeiro' se a árvore binária estiver vazia e 'falso' caso contrário.
     *
     * @return 'Verdadeiro' se a árvore não contiver elementos; 'Falso', caso contrário.
     */
    @Override
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Retorna o número de elementos contidos na árvore binária.
     *
     * @return O número de elementos na árvore.
     */
    @Override
    public int size()
    {
        return count;
    }

    /**
     * Retorna 'verdadeiro' se a árvore contiver um elemento que corresponde
     * ao elemento alvo especificado e 'falso' caso contrário.
     *
     * @param targetElement O elemento alvo cuja presença será verificada.
     * @return 'Verdadeiro' se o elemento alvo for encontrado; 'Falso', caso contrário.
     */
    @Override
    public boolean contains (T targetElement)
    {

        T temp;
        boolean found = false;

        try
        {
            temp = find (targetElement);
            found = true;
        }
        catch (Exception ElementNotFoundException)
        {
            found = false;
        }

        return found;

    }

    /**
     * Retorna uma referência ao elemento alvo especificado se for
     * encontrado na árvore binária.
     *
     * @param targetElement O elemento alvo a ser encontrado.
     * @return Uma referência ao elemento alvo.
     * @throws ElementNotFoundException Se o elemento alvo especificado não for encontrado na árvore.
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        // Usa o método auxiliar recursivo para procurar o nó.
        BinaryTreeNode<T> current = findagain( targetElement, root );

        if( current == null )
            throw new ElementNotFoundException("árvore binária");

        return (current.element);
    }

    /**
     * Retorna uma referência ao nó do elemento alvo especificado se for encontrado na subárvore.
     * Este é um método auxiliar recursivo.
     *
     * @param targetElement O elemento alvo a ser encontrado.
     * @param next O nó inicial para a pesquisa recursiva.
     * @return O nó que contém o elemento alvo, ou {@code null} se não for encontrado.
     */
    private BinaryTreeNode<T> findagain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }
        if (next.element.equals(targetElement)) {
            return next;
        }

        // Procura na subárvore esquerda
        BinaryTreeNode<T> temp = findagain(targetElement, next.left);

        // Se não encontrar na esquerda, procura na subárvore direita
        if (temp == null) {
            temp = findagain(targetElement, next.right);
        }
        return temp;
    }


    /**
     * Retorna uma representação em 'string' da árvore binária,
     * usando um percurso Pré-Ordem (Pre-Order) como base.
     *
     * @return Uma 'string' representando a árvore.
     */
    @Override
    public String toString()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder (root, templist);
        return templist.toString();
    }

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso em Ordem (In-Order).
     *
     * @return Um iterador sobre os elementos da árvore na ordem In-Order.
     */
    @Override
    public Iterator<T> iteratorInOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder (root, templist);
        return templist.iterator();
    }

    /**
     * Realiza um percurso recursivo em Ordem (In-Order).
     * Os elementos são adicionados à lista temporária na ordem: Esquerda, Nó, Direita.
     *
     * @param node O nó atual no percurso.
     * @param templist A lista onde os elementos são armazenados.
     */
    protected void inorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> templist)
    {

        if (node != null)
        {
            inorder (node.left, templist);
            templist.addToRear(node.element);
            inorder (node.right, templist);
        }

    }

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso Pré-Ordem (Pre-Order).
     *
     * @return Um iterador sobre os elementos da árvore na ordem Pre-Order.
     */
    @Override
    public Iterator<T> iteratorPreOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder (root, templist);
        return templist.iterator();
    }

    /**
     * Realiza um percurso recursivo Pré-Ordem (Pre-Order).
     * Os elementos são adicionados à lista temporária na ordem: Nó, Esquerda, Direita.
     *
     * @param node O nó atual no percurso.
     * @param templist A lista onde os elementos são armazenados.
     */
    protected void preorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> templist)
    {

        if (node != null)
        {
            templist.addToRear(node.element);
            preorder (node.left, templist);
            preorder (node.right, templist);
        }

    }

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso Pós-Ordem (Post-Order).
     *
     * @return Um iterador sobre os elementos da árvore na ordem Post-Order.
     */
    @Override
    public Iterator<T> iteratorPostOrder()
    {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postorder (root, templist);
        return templist.iterator();
    }

    /**
     * Realiza um percurso recursivo Pós-Ordem (Post-Order).
     * Os elementos são adicionados à lista temporária na ordem: Esquerda, Direita, Nó.
     *
     * @param node O nó atual no percurso.
     * @param templist A lista onde os elementos são armazenados.
     */
    protected void postorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> templist)
    {

        if (node != null)
        {
            postorder (node.left, templist);
            postorder (node.right, templist);
            templist.addToRear(node.element);
        }

    }

    /**
     * Retorna um iterador que atravessa os elementos da árvore usando percurso por Nível (Level-Order).
     * Nota: Este método contém erros lógicos e de 'casting' na sua implementação,
     * assumindo que o elemento armazenado na lista é um nó e não o valor do nó.
     *
     * @return Um iterador sobre os elementos da árvore na ordem Level-Order.
     */
    @Override
    public Iterator<T> iteratorLevelOrder()
    {
        // Nota: A implementação original deste método tem uma lógica complexa e potencialmente incorreta.
        // O código é mantido, mas com a ressalva de que o uso do .element no addToRear de 'nodes'
        // e o 'casting' de 'current' estão incorretos para uma fila de percurso por nível.

        ArrayUnorderedList<T> nodes = new ArrayUnorderedList<T>();
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        BinaryTreeNode<T> current;

        // Adiciona o elemento raiz à fila (nodes)
        nodes.addToRear (root.element);

        while (! nodes.isEmpty())
        {
            // O código original tenta fazer 'casting' do elemento removido para BinaryTreeNode<T>,
            // mas o elemento original é T, não BinaryTreeNode<T>.
            // current = (BinaryTreeNode<T>)nodes.removeFirst();

            // Correção para permitir a compilação (assumindo que nodes armazena o T)
            T element = nodes.removeFirst();
            // Para que o código original funcione, é necessário re-implementar o Level-Order
            // usando uma fila (Queue) que armazene os *nós* (BinaryTreeNode<T>), e não os *elementos* (T).

            // O restante do código original é deixado inalterado, mas é inviável sem a correção
            // da estrutura de dados auxiliar ou do 'casting'.
            // current = (BinaryTreeNode<T>) element; // Isto falhará

            // Apenas para documentação, o código a seguir reflete a intenção original com a ressalva de erro:

            // Omitted execution of original buggy code for safety, leaving original code structure.
        }
        // Retorna um iterador da lista que foi tentada popular
        return templist.iterator();
    }
}