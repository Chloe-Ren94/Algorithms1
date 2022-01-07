/* *****************************************************************************
 *  Name: Chloe
 *  Date: January 7, 2022
 *  Description: Write a client program Permutation.java that takes an integer k
 *               as a command-line argument; reads a sequence of strings from
 *               standard input using StdIn.readString(); and prints exactly k
 *               of them, uniformly at random.
 *               Print each item from the sequence at most once.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());
        Iterator<String> iterator = rq.iterator();
        for (int i = 0; i < k; i++)
            StdOut.println(iterator.next());
    }
}
