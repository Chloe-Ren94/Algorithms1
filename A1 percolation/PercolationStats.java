/* *****************************************************************************
 *  Name:              Chloe
 *  Coursera User ID:  123456
 *  Last modified:     January 5, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * To perform a series of computational experiments, create a data type PercolationStats with the
 * following API.
 */
public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trails;   // the number of trails
    private final double[] thresholds;  // the threshold value of each trail

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trails must be greater than 0");

        this.trails = trials;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            // open random sites until percolation
            while (!perc.percolates()) {
                int row;
                int col;
                // pick a site at random and open this site if it is blocked; if not, repeat.
                do {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                } while (perc.isOpen(row, col));
                perc.open(row, col);
            }
            thresholds[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double s = stddev();
        return mean - CONFIDENCE_95 * s / Math.sqrt(trails);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double s = stddev();
        return mean + CONFIDENCE_95 * s / Math.sqrt(trails);
    }

    /**
     * Also, include a main() method that takes two command-line arguments n and T,
     * performs T independent computational experiments (discussed above) on an
     * n-by-n grid, and prints the sample mean, sample standard deviation, and
     * the 95% confidence interval for the percolation threshold.
     */
    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trails);
        System.out.printf("mean = %f%n", ps.mean());
        System.out.printf("stddev = %f%n", ps.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]%n",
                          ps.confidenceLo(), ps.confidenceHi());
    }

}
