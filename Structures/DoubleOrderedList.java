package Structures;

import Exceptions.NonComparableElementException;
import Interface.OrderedListADT;

/**
 * Representa uma implementação de uma Lista Ordenada Duplamente Ligada (Double Ordered List).
 * Estende {@code DoubleList} e implementa a interface {@code OrderedListADT}.
 * Os elementos são inseridos automaticamente na posição correta para manter a lista ordenada.
 *
 * @param <T> O tipo de elementos armazenados na lista, que deve ser {@code Comparable}.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoubleOrderedList<T> extends DoubleList<T> implements OrderedListADT<T>
{
    /**
     * Cria uma lista ordenada duplamente ligada vazia.
     */
    public DoubleOrderedList()
    {
        super();
    }


    /**
     * Adiciona o elemento especificado à lista na posição correta para manter a ordem.
     * Lança {@code NonComparableElementException} se o elemento não for comparável.
     *
     * @param element O elemento a ser adicionado à lista.
     * @throws NonComparableElementException Se o elemento não implementar a interface {@code Comparable}.
     */
    @Override
    public void add (T element)
    {
        Comparable temp;
        // Verifica se o elemento é comparável
        if (element instanceof Comparable)
            temp = (Comparable)element;
        else
            throw new NonComparableElementException("lista ordenada duplamente ligada");

        DoubleNode<T> traverse = front;
        DoubleNode<T> newnode  = new DoubleNode<T>(element);
        // boolean found = false; // Variável 'found' não é usada, pode ser removida.

        // Caso 1: Lista vazia
        if (isEmpty())
        {
            front = newnode;
            rear = newnode;
        }
        // Caso 2: Inserir no final ( >= ao último elemento)
        else if (temp.compareTo(rear.getElement()) >= 0)
        {
            rear.setNext(newnode);
            newnode.setPrevious(rear);
            newnode.setNext(null);
            rear = newnode;
        }
        // Caso 3: Inserir no início ( <= ao primeiro elemento)
        else if (temp.compareTo(front.getElement()) <= 0)
        {
            front.setPrevious(newnode);
            newnode.setNext(front);
            newnode.setPrevious(null);
            front = newnode;
        }
        // Caso 4: Inserir no meio
        else
        {
            // Encontrar o nó que será o seguinte (o primeiro nó maior ou igual)
            while ((temp.compareTo(traverse.getElement()) > 0))
                traverse = traverse.getNext();

            // O novo nó é inserido antes de 'traverse'
            newnode.setNext(traverse);
            newnode.setPrevious(traverse.getPrevious());

            // Ligações do nó anterior e seguinte
            traverse.getPrevious().setNext(newnode);
            traverse.setPrevious(newnode);
        }
        count++;

    }
}