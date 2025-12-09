package Structures;

/**
 * Implementa uma Árvore Binária de Pesquisa AVL (Georgy Adelson-Velsky e Evgenii Landis),
 * uma árvore auto-balanceada que garante que a diferença de altura entre as subárvores
 * esquerda e direita de qualquer nó (fator de balanceamento) seja, no máximo, 1.
 *
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class AVLTree {

    /**
     * O nó raiz da Árvore AVL.
     */
    private AVLNode raiz;

    /**
     * Obtém a altura de um nó. Retorna 0 se o nó for nulo.
     *
     * @param n O nó para o qual a altura será calculada.
     * @return A altura do nó, ou 0 se o nó for nulo.
     */
    private int altura(AVLNode n) {
        if (n == null) return 0;
        return n.altura;
    }

    /**
     * Calcula o fator de balanceamento de um nó: altura(esquerda) - altura(direita).
     * Um valor > 1 indica que a subárvore esquerda está mais alta; um valor < -1
     * indica que a subárvore direita está mais alta.
     *
     * @param n O nó para o qual o fator de balanceamento será calculado.
     * @return O fator de balanceamento do nó.
     */
    private int fatorBalanceamento(AVLNode n) {
        if (n == null) return 0;
        return altura(n.esquerda) - altura(n.direita);
    }

    /**
     * Realiza uma Rotação à Direita (Right Rotation) no nó desbalanceado.
     * Esta rotação é usada para resolver o caso Esquerda-Esquerda.
     *
     * @param y O nó (raiz da subárvore desbalanceada) a ser rotacionado.
     * @return O novo nó raiz da subárvore após a rotação.
     */
    private AVLNode rotacaoDireita(AVLNode y) {
        AVLNode x = y.esquerda;
        AVLNode t2 = x.direita;

        // Efetuar rotação
        x.direita = y;
        y.esquerda = t2;

        // Atualizar alturas (de baixo para cima)
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        // Novo nó, raiz após rotação
        return x;
    }

    /**
     * Realiza uma Rotação à Esquerda (Left Rotation) no nó desbalanceado.
     * Esta rotação é usada para resolver o caso Direita-Direita.
     *
     * @param x O nó (raiz da subárvore desbalanceada) a ser rotacionado.
     * @return O novo nó raiz da subárvore após a rotação.
     */
    private AVLNode rotacaoEsquerda(AVLNode x) {
        AVLNode y = x.direita;
        AVLNode t2 = y.esquerda;

        // Efetuar rotação
        y.esquerda = x;
        x.direita = t2;

        // Atualizar alturas (de baixo para cima)
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        // Novo nó, raiz após rotação
        return y;
    }

    /**
     * Método auxiliar recursivo para inserir uma chave na subárvore com raiz no 'node' especificado.
     * Após a inserção, verifica e corrige o desbalanceamento através de rotações.
     *
     * @param node O nó raiz da subárvore atual.
     * @param chave O valor a ser inserido.
     * @return O nó raiz da subárvore modificada (que pode ter sido alterado após a rotação).
     */
    private AVLNode inserir(AVLNode node, int chave) {
        // 1. Inserção normal de árvore binária de pesquisa
        if (node == null)
            return new AVLNode(chave);

        if (chave < node.chave)
            node.esquerda = inserir(node.esquerda, chave);
        else if (chave > node.chave)
            node.direita = inserir(node.direita, chave);
        else
            return node; // chaves duplicadas não são permitidas

        // 2. Atualizar altura do nó atual
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        // 3. Obter fator de balanceamento
        int balance = fatorBalanceamento(node);

        // 4. Casos de desbalanceamento e rotações
        // Esquerda-Esquerda
        if (balance > 1 && chave < node.esquerda.chave)
            return rotacaoDireita(node);

        // Direita-Direita
        if (balance < -1 && chave > node.direita.chave)
            return rotacaoEsquerda(node);

        // Esquerda-Direita (rotação dupla)
        if (balance > 1 && chave > node.esquerda.chave) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }

        // Direita-Esquerda (rotação dupla)
        if (balance < -1 && chave < node.direita.chave) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        // Retornar nó (sem alterações se estiver balanceado)
        return node;
    }

    /**
     * Método público para inserir uma chave na Árvore AVL.
     *
     * @param chave O valor inteiro a ser inserido.
     */
    public void inserir(int chave) {
        raiz = inserir(raiz, chave);
    }

    /**
     * Inicia o percurso da árvore em ordem (in-order), começando pela raiz.
     * Imprime os elementos no console.
     */
    public void percorrerEmOrdem() {
        percorrerEmOrdem(raiz);
        System.out.println();
    }

    /**
     * Método auxiliar recursivo que realiza o percurso em ordem.
     * * @param node O nó raiz da subárvore a ser percorrida.
     */
    private void percorrerEmOrdem(AVLNode node) {
        if (node != null) {
            percorrerEmOrdem(node.esquerda);
            System.out.print(node.chave + " ");
            percorrerEmOrdem(node.direita);
        }
    }

}