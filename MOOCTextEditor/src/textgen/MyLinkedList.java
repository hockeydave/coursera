package textgen;


import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        // TODO: Implement this method
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     * @return true if element is added and the list changes.
     * @throws NullPointerException - if the specified element is null and this list does not permit null elements
     */
    public boolean add(E element) {
        if(element == null)
            throw new NullPointerException("failed.  Cannot add null element to linked-list");
        // TODO: Implement this method
        LLNode<E> node = new LLNode<>(element);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())e
     */
    public E get(int index) {
        // TODO: Implement this method.
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(size + " sized linked-list attempt to get element at invalid index " + index);
        LLNode<E> n = head;
        int i = 0;
        while (i++ < index) {
            n = n.next;
        }
        return n.data;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   where the element should be added
     * @param element The element to add
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index > size())
     * @throws NullPointerException - if the specified element is null and this list does not permit null elements
     */
    public void add(int index, E element) throws NullPointerException, IndexOutOfBoundsException {
        // TODO: Implement this method
        if (element != null) {
            if (index > size || index < 0)
                throw new IndexOutOfBoundsException(size + " sized linked-list attempt to add element at invalid index " + index);
        } else {
            throw new NullPointerException("failed.  Cannot add null element to linked-list");
        }

        LLNode<E> n = head;
        LLNode<E> node = new LLNode<>(element);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    node.next = n;
                    node.prev = n.prev;
                    if (n != head) {
                        n.prev.next = node;
                        n.prev = node;
                    } else {
                        head = node;
                    }

                    break;
                } else n = n.next;
            }
        }
        size++;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        // TODO: Implement this method
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException if the index is out of bounds. i.e. < index < 0 or index > size
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        LLNode<E> node = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                if (node == head) head = node.next;
                else node.prev.next = node.next;
                if (node == tail) tail = node.prev;
                else node.next.prev = node.prev;

                size--;
                return node.data;
            } else node = node.next;
        }
        throw new IndexOutOfBoundsException(size + " sized linked-list attempt to remove element at invalid index " + index);
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds. i.e. < index < 0 or index >= size
     * @throws NullPointerException - if the specified element is null and this list does not permit null elements
     */
    public E set(int index, E element) throws NullPointerException, IndexOutOfBoundsException {
        // TODO: Implement this method
        if(element == null) {
            throw new NullPointerException("set:  Cannot set a null element");
        }
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(size + " sized linked-list attempt to add element at invalid index " + index);

        LLNode<E> n = head;
        LLNode<E> node = new LLNode<>(element);
        E previous = null;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                previous = n.data;
                node.next = n.next;
                node.prev = n.prev;
                if (n == tail)
                    tail = node;
                if (n == head)
                    head = node;
                else
                    n.prev.next = node;
                n.prev = node;
                break;
            } else n = n.next;
        }
        return previous;
    }

    /**
     * override the toString method of class.
     *
     * @return String representation of this node
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LLNode<E> n = head;
        while (n.next != null) {
            sb.append(n);
            n = n.next;
        }
        return sb.toString();
    }
}

/**
 *
 * @param <E>
 */
class LLNode<E> {


    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    public LLNode() {
        this.prev = null;
        this.next = null;
        this.data = null;
    }

    public LLNode(E e) {
        this();
        this.data = e;
    }


    /**
     * override the toString method of class.
     *
     * @return String representation of this node
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("data: ").append(data);
        if (this.prev != null) sb.append(" prev ").append(this.prev.data);
        if (this.next != null) sb.append(" next ").append(this.next.data);
        return sb.toString();
    }
}
