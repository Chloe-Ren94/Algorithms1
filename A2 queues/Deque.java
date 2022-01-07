/* *****************************************************************************
 *  Name: Chloe
 *  Date: January 7, 2022
 *  Description: A double-ended queue or deque (pronounced “deck”) is a generalization
 *               of a stack and a queue that supports adding and removing items
 *               from either the front or the back of the data structure.
 *               Create a generic data type Deque that implements the following API.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A deque implementation by circular double-linked list
 */
public class Deque<Item> implements Iterable<Item> {

    // use sentinel node to avoid special empty case
    private final Node sentinel;
    // the number of items in deque
    private int size;

    private class Node {
        private final Item item;
        private Node prev;
        private Node next;

        public Node(Item i, Node p, Node n) {
            this.item = i;
            this.prev = p;
            this.next = n;
        }
    }

    // construct an empty deque
    public Deque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item is null");
        Node newNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item is null");
        Node newNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("deque is empty");
        Item item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("deque is empty");
        Item item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // an iterator for deque
    private class DequeIterator implements Iterator<Item> {
        private Node current = sentinel.next;

        public boolean hasNext() {
            return current != sentinel;
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("no next item");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        StdOut.println(deque.size());
        StdOut.println(deque.isEmpty());

        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        StdOut.println(deque.size());
        StdOut.println(deque.isEmpty());
        for (Integer i : deque)
            StdOut.print(i + " ");
        StdOut.println();

        deque.addLast(4);
        deque.addLast(5);
        for (Integer i : deque)
            StdOut.print(i + " ");
        StdOut.println();

        deque.removeFirst();
        deque.removeFirst();
        for (Integer i : deque)
            StdOut.print(i + " ");
        StdOut.println();

        StdOut.println(deque.removeLast());
    }

}
