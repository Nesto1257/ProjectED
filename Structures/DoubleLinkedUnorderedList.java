package Structures;

import java.util.NoSuchElementException;

/**
 * Representa uma Lista Não Ordenada Duplamente Ligada.
 * Esta lista utiliza nós que mantêm ligações para o nó seguinte e anterior,
 * permitindo o percurso em ambas as direções.
 *
 * @param <T> O tipo de elementos armazenados na lista.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoubleLinkedUnorderedList<T> {

    /** O primeiro nó (cabeça) da lista. */
    private Node<T> head;

    /** O último nó (cauda) da lista. */
    private Node<T> tail;

    /** O número de elementos na lista. */
    private int size;

    // A classe Node<T> é assumida estar definida em algum lugar ou ser interna.
    /*
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }
    */

    /**
     * Cria uma lista duplamente ligada não ordenada vazia.
     */
    public DoubleLinkedUnorderedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adiciona o elemento especificado à frente (início) desta lista.
     *
     * @param element O elemento a ser adicionado.
     */
    public void addToFront(T element) {
        // Assumindo a existência de uma classe Node<T>
        Node<T> newNode = new Node<>(element);

        if (head == null) { // lista vazia
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }

    /**
     * Adiciona o elemento especificado à parte de trás (fim) desta lista.
     *
     * @param element O elemento a ser adicionado.
     */
    public void addToRear(T element) {
        Node<T> newNode = new Node<>(element);

        if (tail == null) { // lista vazia
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    /**
     * Adiciona o elemento especificado imediatamente após a primeira ocorrência do
     * elemento alvo especificado.
     *
     * @param element O elemento a ser adicionado.
     * @param target O elemento após o qual o novo elemento será inserido.
     * @throws RuntimeException Se o elemento alvo não for encontrado.
     */
    public void addAfter(T element, T target) {
        Node<T> current = head;

        // Encontrar o nó alvo
        while (current != null && !current.data.equals(target)) {
            current = current.next;
        }

        if (current == null) {
            throw new RuntimeException("Elemento alvo não encontrado: " + target);
        }

        Node<T> newNode = new Node<>(element);

        // O novo nó aponta para o nó atual e para o que está a seguir ao atual
        newNode.prev = current;
        newNode.next = current.next;

        // Ajustar a ligação do nó seguinte (se existir)
        if (current.next != null) {
            current.next.prev = newNode;
        } else {
            // Se o nó atual for a cauda, o novo nó torna-se a nova cauda
            tail = newNode;
        }

        // Ajustar a ligação do nó atual
        current.next = newNode;
        size++;
    }

    /**
     * Remove a primeira ocorrência do elemento especificado desta lista.
     *
     * @param element O elemento a ser removido.
     * @return {@code true} se o elemento foi removido com sucesso; {@code false} caso contrário.
     */
    public boolean remove(T element) {
        Node<T> current = head;

        // Procurar o nó
        while (current != null && !current.data.equals(element)) {
            current = current.next;
        }

        if (current == null) return false; // não encontrado

        // Caso 1: Nó é a cabeça (head)
        if (current == head) {
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null; // Lista ficou vazia
        }
        // Caso 2: Nó é a cauda (tail)
        else if (current == tail) {
            tail = tail.prev;
            if (tail != null) tail.next = null;
            else head = null; // Lista ficou vazia (embora já devesse ter sido tratado no caso 1)
        }
        // Caso 3: Nó está no meio
        else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        size--;
        return true;
    }

    /**
     * Imprime todos os elementos da lista na ordem, da cabeça à cauda.
     */
    public void printList() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    /**
     * Imprime todos os elementos da lista na ordem inversa, da cauda à cabeça.
     */
    public void printReverse() {
        Node<T> current = tail;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }
        System.out.println();
    }

    /**
     * Retorna o número de elementos nesta lista.
     * * @return O número de elementos (o tamanho) da lista.
     */
    public int getSize() {
        return size;
    }
}