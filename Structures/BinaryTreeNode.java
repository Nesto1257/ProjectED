package Structures;

/**
 * Representa um nó individual dentro de uma Árvore Binária (Binary Tree).
 * Cada nó armazena um elemento e referências para os seus nós filhos, esquerdo e direito.
 *
 * @param <T> O tipo de elemento armazenado no nó.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class BinaryTreeNode<T> {

    /** O elemento de dados armazenado neste nó. */
    protected T element;

    /** As referências para os nós filhos esquerdo e direito, respetivamente. */
    protected BinaryTreeNode<T> left, right;

    /**
     * Cria um novo nó de árvore binária com o elemento de dados especificado.
     * Os nós filhos esquerdo e direito são inicializados como nulos.
     *
     * @param obj O elemento de dados a ser armazenado no nó.
     */
    BinaryTreeNode (T obj)
    {
        element = obj;
        left = null;
        right = null;
    }

    /**
     * Retorna o número total de nós filhos não nulos (incluindo subfilhos)
     * desta árvore binária, a partir deste nó.
     * Este é, na verdade, o tamanho da subárvore com raiz neste nó, excluindo o nó raiz.
     *
     * @return O número de nós descendentes não nulos.
     */
    public int numChildren()
    {
        // Nota: A lógica deste método calcula o tamanho total da subárvore (excluindo o nó atual),
        // e não apenas o número de filhos imediatos (que seria 0, 1 ou 2).
        int children = 0;

        if (left != null)
            children = 1 + left.numChildren();

        if (right != null)
            children = children + 1 + right.numChildren();

        return children;
    }
}