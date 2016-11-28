/******************************************************************************
 *  Compilation:
 *  javac PercolationStats.java
 *  Execution:
 *  java PercolationStats
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fraction;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("side length or trial times is not bigger than 0!");
        this.fraction = new double[trials];
        Percolation percolation;
        int randomRow;
        int randomCol;
        int count;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            count = 0;
            while (true) {
                randomRow = StdRandom.uniform(1, n + 1);
                randomCol = StdRandom.uniform(1, n + 1);
                if (percolation.isOpen(randomRow, randomCol))
                    continue;
                count++;
                percolation.open(randomRow, randomCol);
                if (percolation.percolates()) {
                    break;
                }
            }
            this.fraction[i] = count / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(this.fraction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(this.fraction.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = 200;
        int t = 100;
        if (1 <= args.length)
            n = Integer.parseInt(args[0]);
        if (2 <= args.length)
            t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo()
                           + ", " + percolationStats.confidenceHi());
    }
}