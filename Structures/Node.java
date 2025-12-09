package Structures;

/**
 * Representa um nó genérico em uma lista duplamente ligada (Doubly Linked List).
 * Cada nó armazena um elemento de dados e contém referências para o nó seguinte (next)
 * e para o nó anterior (prev).
 *
 * @param <T> O tipo de elemento armazenado neste nó.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class Node<T> {

    /** O elemento de dados armazenado neste nó. */
    T data;

    /** A referência para o nó que precede este nó. */
    Node<T> prev;

    /** A referência para o nó que segue este nó. */
    Node<T> next;

    /**
     * Cria um novo nó armazenando o elemento especificado.
     * As referências 'prev' e 'next' são inicializadas como nulas.
     *
     * @param data O elemento de dados a ser armazenado no nó.
     */
    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}