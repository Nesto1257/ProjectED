package Structures;

/**
 * Representa um nó em uma lista duplamente ligada.
 * Cada nó armazena um elemento e contém referências para o nó seguinte (next)
 * e para o nó anterior (previous).
 *
 * @param <E> O tipo de elemento armazenado neste nó.
 * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public class DoubleNode<E>
{
    /** O nó que segue este nó na lista. */
    private DoubleNode<E> next;

    /** O elemento de dados armazenado neste nó. */
    private E element;

    /** O nó que precede este nó na lista. */
    private DoubleNode<E> previous;

    /**
     * Cria um nó vazio, inicializando todas as referências para nulo.
     */
    public DoubleNode()
    {
        next = null;
        element = null;
        previous = null;
    }

    /**
     * Cria um nó armazenando o elemento especificado.
     * As referências 'next' e 'previous' são inicializadas como nulas.
     *
     * @param elem O elemento a ser armazenado no nó.
     */
    public DoubleNode(E elem)
    {
        next = null;
        element = elem;
        previous = null;
    }

    /**
     * Retorna o nó que segue este nó.
     *
     * @return O nó seguinte (next).
     */
    public DoubleNode<E> getNext()
    {
        return next;
    }

    /**
     * Retorna o nó que precede este nó.
     *
     * @return O nó anterior (previous).
     */
    public DoubleNode<E> getPrevious()
    {
        return previous;
    }

    /**
     * Define o nó que segue este nó.
     *
     * @param dnode O nó que será o próximo nó.
     */
    public void setNext (DoubleNode<E> dnode)
    {
        next = dnode;
    }

    /**
     * Define o nó que precede este nó.
     *
     * @param dnode O nó que será o nó anterior.
     */
    public void setPrevious (DoubleNode<E> dnode)
    {
        previous = dnode;
    }


    /**
     * Retorna o elemento armazenado neste nó.
     *
     * @return O elemento de dados.
     */
    public E getElement()
    {
        return element;
    }

    /**
     * Define o elemento armazenado neste nó.
     *
     * @param elem O elemento a ser armazenado.
     */
    public void setElement (E elem)
    {
        element = elem;
    }
}