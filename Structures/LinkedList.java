package Structures;

/**
 * Implementa uma Lista Ligada (Linked List) simples, onde cada elemento
 * (nó) contém uma referência apenas para o nó seguinte.
 * Esta implementação lida com elementos de tipo inteiro (int).
 *
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class LinkedList {

    /** O nó da cabeça (primeiro elemento) da lista. */
    private Node head;

    // Assumindo que a classe Node está definida em 'Structures' ou internamente.
    /*
    private static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    */

    /**
     * Adiciona um novo elemento inteiro ao final da lista.
     * Se a lista estiver vazia, o novo nó torna-se a cabeça.
     *
     * @param data O valor inteiro a ser adicionado à lista.
     */
    public void add(int data) {
        // Assumindo que Node(int data) é um construtor válido
        Node newNode = new Node(data);

        // Caso 1: Lista vazia
        if (head == null) {
            head = newNode;
        }
        // Caso 2: Lista não vazia, percorre até o fim
        else {
            Node current = head;
            while (current.next != null)
                current = current.next;
            current.next = newNode;
        }
    }

    /**
     * Inicia a impressão dos elementos da lista, usando um método auxiliar recursivo.
     * Os elementos são impressos do primeiro ao último.
     */
    public void printRecursive() {
        printRecursive(head);
        // Adiciona uma nova linha para melhor formatação
        System.out.println();
    }

    /**
     * Método auxiliar recursivo que atravessa a lista a partir do nó especificado
     * e imprime o valor de cada nó.
     *
     * @param node O nó atual no percurso.
     */
    private void printRecursive(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        printRecursive(node.next); // Chamada recursiva para o próximo nó
    }
}