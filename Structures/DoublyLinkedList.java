package Structures;

/**
 * Implementa uma Lista Ligada Dupla (Doubly Linked List) para manipulação de inteiros.
 * Esta estrutura de dados mantém referências para o nó seguinte e anterior,
 * permitindo o percurso em ambas as direções.
 *
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoublyLinkedList {

    /** O nó da cabeça (primeiro elemento) da lista. */
    private Node head;

    /** O nó da cauda (último elemento) da lista. */
    private Node tail;

    // Assumindo a existência de uma classe 'Node' no pacote Structures.

    /**
     * Adiciona um novo elemento inteiro ao final (cauda) da lista.
     *
     * @param data O valor inteiro a ser adicionado.
     */
    public void add(int data) {
        // Assumindo que Node(int data) é um construtor válido
        Node newNode = new Node(data);

        // Caso 1: Lista vazia
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        // Caso 2: Lista não vazia
        else {
            tail.next = newNode; // O antigo 'tail' aponta para o novo nó
            newNode.prev = tail; // O novo nó aponta para o antigo 'tail'
            tail = newNode;      // O novo nó torna-se o 'tail'
        }
    }

    /**
     * Inicia a impressão dos elementos da lista, do primeiro ao último.
     */
    public void printForward() {
        System.out.println("Elementos (do primeiro ao último):");
        printForward(head);
        System.out.println();
    }

    /**
     * Método auxiliar recursivo que imprime os elementos da lista na ordem direta,
     * começando pelo nó especificado.
     *
     * @param node O nó atual na iteração.
     */
    private void printForward(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        printForward(node.next); // Chamada recursiva para o próximo nó
    }


    /**
     * Inicia a impressão dos elementos da lista, do último ao primeiro.
     */
    public void printBackward() {
        System.out.println("Elementos (do último ao primeiro):");
        printBackward(tail);
        System.out.println();
    }

    /**
     * Método auxiliar recursivo que imprime os elementos da lista na ordem inversa,
     * começando pelo nó especificado.
     *
     * @param node O nó atual na iteração.
     */
    private void printBackward(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        printBackward(node.prev); // Chamada recursiva para o nó anterior
    }
}