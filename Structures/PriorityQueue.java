package Structures;

import Exceptions.EmptyCollectionException;

public class PriorityQueue<T> extends ArrayHeap<PriorityQueueNode<T>> {

    public PriorityQueue() {
        super();
    }

    /**
     * Adds an element to the priority queue with the specified priority.
     * Lower priority values have higher precedence.
     */
    public void addElement(T object, int priority) {
        PriorityQueueNode<T> node = new PriorityQueueNode<T>(object, priority);
        super.addElement(node);
    }

    /**
     * Removes and returns the element with the highest priority (lowest priority value).
     */
    public T removeNext() throws EmptyCollectionException {
        PriorityQueueNode<T> temp = super.removeMin();
        return temp.getElement();
    }

    /**
     * Returns the element with the highest priority without removing it.
     */
    public T peekNext() throws EmptyCollectionException {
        PriorityQueueNode<T> temp = super.findMin();
        return temp.getElement();
    }
}