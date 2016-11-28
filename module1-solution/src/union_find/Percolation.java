/******************************************************************************
 *  Compilation:
 *  javac Percolation.java
 *  Execution:
 *  java Percolation
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // the bits represents open/connected to top/connected to bottom status
    private static final byte OPENBIT = 0x01;
    private static final byte TOPBIT = 0x02;
    private static final byte BOTTOMBIT = 0x04;

    // the weighted quick union finder we will used
    private WeightedQuickUnionUF uf;

    // the number of element for each row/column
    private int side;

    /* array of byte to control whether a site is open/blocked, whether it is 
     * connected to top/bottom.
     * from low to high position, first bit represents whether a site is open;
     * second bit represents whether a site is connected to top row;
     * third bit represents whether a site is conneted to bottom row.
     */
    private byte[] status;

    // whether the system is percolated
    private boolean isPercolated;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("side length is not bigger than 0!");
        this.side = n;
        this.status = new byte[n * n];

        // the first/last row is connected to top/bottom row
        for (int i = 1; i <= n; i++) {
            this.status[this.getElement(1, i)] |= TOPBIT;
            this.status[this.getElement(n, i)] |= BOTTOMBIT;
        }
        this.uf = new WeightedQuickUnionUF(n * n);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!this.isValid(row, col))
            throw new IndexOutOfBoundsException("row or col is out of side length!");
        if (this.isOpen(row, col))
            return;

        // set the site to opened status
        this.status[getElement(row, col)] |= OPENBIT;

        // see whether the site is connected to top/bottom row
        byte top = (byte) (this.status[getElement(row, col)] & TOPBIT);
        byte bottom = (byte) (this.status[getElement(row, col)] & BOTTOMBIT);
        int parent = -1;

        // see whether the surrounding sites' root is connected to top/bottom row
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            parent = this.uf.find(getElement(row - 1, col));
            top |= (this.status[parent] & TOPBIT);
            bottom |= (this.status[parent] & BOTTOMBIT);
        }
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            parent = this.uf.find(getElement(row + 1, col));
            top |= (this.status[parent] & TOPBIT);
            bottom |= (this.status[parent] & BOTTOMBIT);
        }
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            parent = this.uf.find(getElement(row, col - 1));
            top |= (this.status[parent] & TOPBIT);
            bottom |= (this.status[parent] & BOTTOMBIT);
        }
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            parent = this.uf.find(getElement(row, col + 1));
            top |= (this.status[parent] & TOPBIT);
            bottom |= (this.status[parent] & BOTTOMBIT);
        }

        // connect to each surrounding site
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            this.uf.union(getElement(row, col), getElement(row - 1, col));
        }
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            this.uf.union(getElement(row, col), getElement(row + 1, col));
        }
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            this.uf.union(getElement(row, col), getElement(row, col - 1));
        }
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            this.uf.union(getElement(row, col), getElement(row, col + 1));
        }

        // judge whether the root of the site is connected to top/bottom row
        parent = this.uf.find(getElement(row, col));
        this.status[parent] |= (top | bottom);

        // if the root is connected to top & bottom, then the whole system is percolated
        if (TOPBIT == (this.status[parent] & TOPBIT) && BOTTOMBIT == (this.status[parent] & BOTTOMBIT))
            this.isPercolated = true;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!this.isValid(row, col))
            throw new IndexOutOfBoundsException("row or col is out of side length!");

        // check the site's open status
        return OPENBIT == (this.status[this.getElement(row, col)] & OPENBIT);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!this.isValid(row, col))
            throw new IndexOutOfBoundsException("row or col is out of side length!");

        // the site that is opened and connected to top row is treated as the full site
        return isOpen(row, col) && TOPBIT == (this.status[this.uf.find(getElement(row, col))] & TOPBIT);
    }

    // does the system percolate?
    public boolean percolates() {
        return this.isPercolated;
    }

    // is the coordinate valid?
    private boolean isValid(int row, int col) {
        if (row < 1 || row > this.side || col < 1 || col > this.side)
            return false;
        return true;
    }

    // calculate the index by number of row and column
    private int getElement(int row, int col) {
        if (!this.isValid(row, col))
            throw new IndexOutOfBoundsException("row or col is out of side length!");

        // in our system, index is calculated from 0 but row and col is calculated from 1
        return this.side * (row - 1) + col - 1;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}