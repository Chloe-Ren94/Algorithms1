/* *****************************************************************************
 *  Name: Chloe
 *  Date: January 7, 2022
 *  Description: A randomized queue is similar to a stack or queue, except that
 *               the item removed is chosen uniformly at random among items in
 *               the data structure. Create a generic data type RandomizedQueue
 *               that implements the following API
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue implementation by resizing array
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;   // the number of items in randomized queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // create a new array with new capacity and copy items to it
    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            newArray[i] = items[i];
        items = newArray;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item is null");
        if (size == items.length)
            resize(2 * items.length);
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("queue is empty");
        // randomly generate the index of the removed item
        int index = StdRandom.uniform(size);
        Item item = items[index];
        // replace the removed item with the last item
        items[index] = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("queue is empty");
        int index = StdRandom.uniform(size);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // an iterator for randomized queue
    private class RandomizedQueueIterator implements Iterator<Item> {
        // a copy of items in the original randomized queue
        private Item[] itemsCopy;
        // the number of items remained in the iterator
        private int remainingItems;

        // copy items from the original randomized queue to the iterator
        public RandomizedQueueIterator() {
            itemsCopy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++)
                itemsCopy[i] = items[i];
            remainingItems = size;
        }

        public boolean hasNext() {
            return remainingItems > 0;
        }

        // randomly remove and return a random item from the iterator
        // the original randomized queue is not changed
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("no next item");
            int index = StdRandom.uniform(remainingItems);
            Item item = itemsCopy[index];
            itemsCopy[index] = itemsCopy[--remainingItems];
            itemsCopy[remainingItems] = null;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}
