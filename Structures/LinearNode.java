package Structures;

/**
 * Representa um nó individual em uma lista linearmente ligada (Linked List).
 * Cada nó armazena um elemento e contém uma referência para o nó seguinte (next).
 *
 * @param <T> O tipo de elemento armazenado neste nó.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class LinearNode<T> {
    /** Referência para o próximo nó na lista. */
    private LinearNode<T> next;

    /** Elemento armazenado neste nó. */
    private T element;

    /**
     * Cria um nó vazio, inicializando a referência para o próximo nó e o elemento como nulos.
     */
    public LinearNode() {
        next = null;
        element = null;
    }

    /**
     * Cria um nó armazenando o elemento especificado.
     * A referência para o próximo nó é inicializada como nula.
     *
     * @param elem O elemento a ser armazenado.
     */
    public LinearNode(T elem) {
        next = null;
        element = elem;
    }

    /**
     * Retorna o nó que segue este nó na lista.
     *
     * @return A referência para o próximo nó.
     */
    public LinearNode<T> getNext() {
        return next;
    }

    /**
     * Define o nó que segue este nó.
     *
     * @param node O nó que será o próximo.
     */
    public void setNext(LinearNode<T> node) {
        next = node;
    }

    /**
     * Retorna o elemento armazenado neste nó.
     *
     * @return O elemento armazenado neste nó.
     */
    public T getElement() {
        return element;
    }

    /**
     * Define o elemento a ser armazenado neste nó.
     *
     * @param elem O elemento a ser armazenado neste nó.
     */
    public void setElement(T elem) {
        element = elem;
    }
}