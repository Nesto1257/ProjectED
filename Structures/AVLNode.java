package Structures;

/**
 * Representa um nó individual dentro de uma Árvore AVL (uma Árvore Binária de Pesquisa auto-balanceada).
 * Cada nó armazena uma chave, a sua altura dentro da árvore e referências para os seus filhos.
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class AVLNode {

    /** O valor da chave (key) armazenada neste nó. */
    int chave;

    /** A altura deste nó na subárvore (a altura de uma folha é 1). */
    int altura;

    /** A referência para o nó filho esquerdo. */
    AVLNode esquerda;

    /** A referência para o nó filho direito. */
    AVLNode direita;

    /**
     * Cria um novo nó AVL com a chave especificada.
     * A altura inicial é definida como 1, e os filhos são nulos.
     *
     * @param chave O valor inteiro que será armazenado no nó.
     */
    public AVLNode(int chave) {
        this.chave = chave;
        this.altura = 1; // um novo nó começa com altura 1
    }
}