package structure;

import java.util.NoSuchElementException;

/**DoublyLinkedList that can travel both backword and forward in the list from the tail or head.
 *
 * @param <E> Element type to be stored in the list
 * @author Alejandro Borroni
 * @version 2026.09.2.11
 */
@SuppressWarnings("unchecked")
public class DoublyLinkedList<E> {
    /**Node Structure for the DoublyLinkedList. Opted for a DoublyLinkedList that could go back and forth both ways.
     *
     * @param <E> The element type stored in Node
     */
    public static class Node<E> {
        //Data fields
        E data;
        Node<E> next;
        Node<E> prev;

        //Constructors

        /**Default Contractor
         *
         * @param data element to be added to node, should only be the same type as {@code <E>}
         */
        public Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        //Setters
        public void setData(E data) {
            this.data = data;
        }
        public void setNext(Node<E> next) {
            this.next = next;
        }
        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    //Data fields
    private Node<E> head;
    private Node<E> tail;
    private int size;

    //Constructors
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Default construct for single node in list.
     * @param data element to be placed in Node, should only be the same type as {@code <E>}
     */
    public DoublyLinkedList(E data) {
        Node<E> newNode = new Node<>(data);
        head = newNode;
        tail = newNode;
    }

    /**
     * Constructor for DoublyLinkedList which can take an arr and separate the values in different nodes
     * @param data take as an arr of elements, arr elements should only be the same type as {@code <E>}
     */
    public DoublyLinkedList(E[] data) {
        for (E datum : data) {
            Node<E> newNode = new Node<>(datum);
            if (head == null) {
                head = newNode;
                tail = newNode;
                size++;
            } else if (head.next == null) {
                head.setNext(newNode);
                tail = newNode;
                tail.setPrev(head);
                size++;
            }else {
                tail.setNext(newNode);
                Node<E> prev = tail;
                tail = newNode;
                tail.setPrev(prev);
                size++;
            }
        }
    }
    //Methods to add to the DoublyLinkedList

    /**
     * Adds the element given to the end of the list
     * @param data element to be added to DoublyLinkedList
     */
    public void add(E data) {
        if (head == null) {
            addFirst(data);
        } else {
            addLast(data);
        }
        size++;
    }

    /**
     * Adds the element given to the index given of the list
     * @param index index of element location to be placed
     * @param data element to be added to DoublyLinkedList
     */
    public void add(int index, E data) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1));
        }
        if (index == 0) {
            addFirst(data);
        } else if (index == size - 1) {
            Node<E> newNode = new Node<>(data);
            newNode.setNext(tail);
            newNode.setPrev(tail.prev);
            tail.prev.setNext(newNode);
            tail.setPrev(newNode);
        } else {
            Node<E> newNode = new Node<>(data);
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> prev = current.prev;
            prev.setNext(newNode);
            newNode.setPrev(prev);
            newNode.setNext(current);
            current.setPrev(newNode);
        }
        size++;
    }

    /**
     * Private method for adding to the first of the list.
     * @param data element to be added to the DoublyLinkedList
     */
    private void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }  else if  (head.next == null) {
            Node<E> next = head;
            head.setPrev(newNode);
            newNode.setNext(head);
            head = newNode;
            tail = next;
        }  else {
            Node<E> next = head;
            next.setPrev(newNode);
            newNode.setNext(next);
            head = newNode;
        }
    }

    /**
     * Private method for adding element to the tail of the list
     * @param data element to be added to DoublyLinkedList
     */
    private void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }  else if (head == tail) {
            head.setNext(newNode);
            newNode.setPrev(head);
            tail = newNode;
        }  else {
            Node<E> prev = tail;
            prev.setNext(newNode);
            newNode.setPrev(prev);
            tail = newNode;
        }
    }
    //Methods to remove from the DoublyLinkedList

    /**
     * Removes the element at index form the DoublyLinkedList
     * @param index the index of the element to be removed
     * @return the removed element
     */
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1));
        } else if (index == 0) {
            size--;
            return removeFirst();
        }  else if (index == size - 1) {
            Node<E> temp = tail;
            tail = tail.prev;
            tail.setNext(null);
            size--;
            return temp.data;
        }  else {
            Node<E> current = head;
            for (int i = 1; i <= index; i++) {
                current = current.next;
            }
            Node<E> prev = current.prev;
            prev.setNext(current.next);
            Node<E> next = current.next;
            next.setPrev(prev);
            current.next.prev = null;
            size--;
            return current.data;
        }
    }

    /**
     * If no index is specified, remove the first element from the DoublyLinkedList
     *
     * @return the removed element
     */
    public E remove() {
        return removeFirst();
    }

    /**
     * Removes the data provided from the DoublyLinkedList if found, otherwise throw NoSUchElementException.
     * @param data Element to be checked and removed, should only be the same type as {@code <E>}
     * @return the removed element for potional later use
     */
    public E remove(E data) {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) {
            if (((Comparable<E>)(head.data)).compareTo(data) == 0) {
                return removeFirst();
            } else  {
                throw new NoSuchElementException("Element does not exist in list");
            }
        }
        if (((Comparable<E>)(head.data)).compareTo(data) == 0) {
            return removeFirst();
        }
        Node<E> current = head.next;
        for (int i = 1; i < size-1; i++) {
            if (((Comparable<E>)(current.data)).compareTo(data) == 0) {
                return remove(i);
            } else {
                current = current.next;
            }
        }
        if (current == tail && ((Comparable<E>)(current.data)).compareTo(data) == 0) {
            return removeLast();
        } else {
            throw new NoSuchElementException("Element does not exist in list");
        }
    }

    /**
     * Removes the first element of the DoublyLinkedList
     * @return the removed element
     */
    private E removeFirst() {
        E e;
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) {
            e = head.data;
            head = tail = null;
        }
        if (head.next == tail) {
            e = head.data;
            head.setNext(null);
            tail.setPrev(null);
            head = tail;
        } else  {
            e = head.data;
            head = head.next;
            head.setPrev(null);
        }
        size--;
        return e;
    }

    /**
     * Removes the last element of the DoublyLinkedList
     * @return the removed element
     */
    private E removeLast() {
        E e;
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) {
            e = tail.data;
            head = tail = null;
        }  else {
            e = tail.data;
            tail = tail.prev;
            tail.setNext(null);
        }
        size--;
        return e;
    }
    //General use methods

    /**
     * Gets an element at the given index
     * @param index index of element to be returned
     * @return element at given index
     */
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1));
        }
        Node<E> current = head;
        if (index != 0) {
            for (int i = 1; i <= index; i++) {
                current = current.next;
            }
        }
        return current.data;
    }

    /**
     * Sets the element at the given index to a new element given
     * @param index index of element to be set
     * @param data new element to replace old element
     * @return old element
     */
    public E set(int index, E data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1));
        }
        Node<E> current = head;
        E temp;
        if (index != 0) {
            for (int i = 1; i <= index; i++) {
                current = current.next;
            }
        }
        temp = current.data;
        current.data = data;
        return temp;
    }

    /**
     * Searches the DoublyLinkedList for the element given
     * @param data element to be searched for
     * @return index of element found in DoublyLinkedList, otherwise -1 is returned
     */
    public int indexOf(E data) {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        Node<E> current = head;
        int index = 0;
        if (((Comparable<E>)(head.data)).compareTo(data) == 0) {
            return index;
        }
        for (int i = 0; i < size; i++) {
            index++;
            current = current.next;
            if (((Comparable<E>)(current.data)).compareTo(data) == 0) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Searches the DoublyLinkedList for the element given
     * @param data element to be searched for, should only be the same type as {@code <E>}
     * @return true if element is found in DoublyLinkedList, false otherwise
     */
    public boolean contains(E data) {
        for (Node<E> current = head; current != null; current = current.next) {
            if (((Comparable<E>)(current.data)).compareTo(data) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that returns the size in an int of the DoublyLinkedList
     * @return size of DoublyLinkedList as an int
     */
    public int getSize() {
        return size;
    }

}
