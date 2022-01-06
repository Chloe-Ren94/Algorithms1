/* *****************************************************************************
 *  Name:              Chloe
 *  Coursera User ID:  123456
 *  Last modified:     January 5, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * To model a percolation system, create a data type Percolation with the following API
 */
public class Percolation {

    private final int gridSize;     // grid size n
    private int openSites;          // number of open sites
    private boolean[] isOpen;       // open status of each site
    // union-find object to simulate connect and percolate
    private final WeightedQuickUnionUF uf;
    // auxiliary union-find object to avoid backwash
    private final WeightedQuickUnionUF ufAuxiliary;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n is less than 1");
        gridSize = n;
        openSites = 0;

        // initialize isOpen array
        isOpen = new boolean[n * n + 2];
        isOpen[0] = true;
        isOpen[n * n + 1] = true;
        for (int i = 1; i <= n * n; i++)
            isOpen[i] = false;

        // initialize uf (with virtual top and virtual bottom) and
        // ufAuxiliary (only with virtual top)
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufAuxiliary = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 1; i <= gridSize; i++) {
            uf.union(0, xyTo1D(1, i));
            uf.union(xyTo1D(gridSize, i), n * n + 1);
            ufAuxiliary.union(0, xyTo1D(1, i));
        }
    }

    // validate that p is a valid row or column
    private void validate(int p) {
        if (p <= 0 || p > gridSize) {
            throw new IllegalArgumentException("row/column index i out of bounds");
        }
    }

    // map from a 2-dimensional (row, column) pair (from 1 to n)
    // to a 1-dimensional union find object index
    private int xyTo1D(int row, int col) {
        validate(row);
        validate(col);
        return gridSize * (row - 1) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = xyTo1D(row, col);
        if (isOpen[index])
            return;

        // open the site
        isOpen[index] = true;
        openSites++;

        // connect the site with open neighbors
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                int left = xyTo1D(row, col - 1);
                uf.union(index, left);
                ufAuxiliary.union(index, left);
            }
        }
        if (col < gridSize) {
            if (isOpen(row, col + 1)) {
                int right = xyTo1D(row, col + 1);
                uf.union(index, right);
                ufAuxiliary.union(index, right);
            }
        }
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                int up = xyTo1D(row - 1, col);
                uf.union(index, up);
                ufAuxiliary.union(index, up);
            }
        }
        if (row < gridSize) {
            if (isOpen(row + 1, col)) {
                int bottom = xyTo1D(row + 1, col);
                uf.union(index, bottom);
                ufAuxiliary.union(index, bottom);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = xyTo1D(row, col);
        return isOpen[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        // use ufAuxiliary to estimate isFull to avoid backwash
        return isOpen(row, col) && ufAuxiliary.find(0) == ufAuxiliary.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // handle special case when n is 1
        if (gridSize == 1)
            return isOpen(1, 1);
        int n = gridSize;
        return uf.find(0) == uf.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(3, 2);
        p.open(4, 2);
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());
    }
}
