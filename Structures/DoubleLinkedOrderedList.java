package Structures;

import java.util.NoSuchElementException;

/**
 * Representa uma Lista Ordenada Duplamente Ligada.
 * Esta lista armazena elementos de forma ordenada e mantém ligações bidirecionais
 * (próximo e anterior) entre os nós.
 *
 * @param <T> O tipo de elementos armazenados na lista, que devem estender {@code Comparable}.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoubleLinkedOrderedList<T extends Comparable<T>> {

    /** O primeiro nó (cabeça) da lista. */
    private Node<T> head;

    /** O último nó (cauda) da lista. */
    private Node<T> tail;

    /** O número de elementos na lista. */
    private int size;

    // A classe Node<T> deve estar definida em algum lugar ou ser interna
    // Exemplo de como Node<T> pode ser definida:
    /*
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }
    */

    /**
     * Cria uma lista duplamente ligada ordenada vazia.
     */
    public DoubleLinkedOrderedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adiciona o elemento especificado à lista, mantendo a ordem.
     * O elemento é inserido na posição correta determinada pela sua ordem natural ({@code Comparable}).
     *
     * @param data O elemento a ser adicionado.
     */
    public void add(T data) {
        // Assumindo a existência de uma classe Node<T>
        Node<T> newNode = new Node<>(data);

        // Caso 1: Lista vazia
        if (head == null) {
            head = tail = newNode;
        }
        // Caso 2: Inserir no início (data <= head.data)
        else if (data.compareTo(head.data) <= 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        // Caso 3: Inserir no fim (data >= tail.data)
        else if (data.compareTo(tail.data) >= 0) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        // Caso 4: Inserir no meio
        else {
            Node<T> current = head;
            // Encontra o primeiro nó onde a data é menor ou igual (ponto de inserção)
            while (current != null && data.compareTo(current.data) > 0) {
                current = current.next;
            }
            // current é o nó que virá DEPOIS do novo nó
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }

        size++;
    }

    /**
     * Remove a primeira ocorrência do elemento especificado desta lista.
     *
     * @param data O elemento a ser removido.
     * @return {@code true} se o elemento foi removido com sucesso; {@code false} caso contrário.
     */
    public boolean remove(T data) {
        Node<T> current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                // Caso 1: Nó é a cabeça (head)
                if (current == head) {
                    head = head.next;
                    if (head != null) head.prev = null;
                    else tail = null; // Lista ficou vazia
                }
                // Caso 2: Nó é a cauda (tail)
                else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                }
                // Caso 3: Nó está no meio
                else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Imprime todos os elementos da lista na ordem, do início ao fim.
     */
    public void printList() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        // É comum adicionar uma nova linha para melhor formatação
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