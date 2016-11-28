/******************************************************************************
 *  Compilation:
 *  javac Percolation.java
 *  Execution:
 *  java Percolation
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int side;
    /* array of byte to control whether a site is open/blocked, whether it is 
     * connected to top/bottom.
     * from low to high position, first bit represents whether a site is open;
     * second bit represents whether a site is connected to top row;
     * third bit represents whether a site is conneted to bottom row.
     */
    private byte[] status;
    private boolean isPercolated;
    private static final byte OPENBIT = 0x01;
    private static final byte TOPBIT = 0x02;
    private static final byte BOTTOMBIT = 0x04;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("side length is not bigger than 0!");
        this.side = n;
        this.status = new byte[n * n];
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
        this.status[getElement(row, col)] |= OPENBIT;
        byte top = (byte) (this.status[getElement(row, col)] & TOPBIT);
        byte bottom = (byte) (this.status[getElement(row, col)] & BOTTOMBIT);
        int parent = -1;
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
        parent = this.uf.find(getElement(row, col));
        this.status[parent] |= (top | bottom);
        if (TOPBIT == (this.status[parent] & TOPBIT) && BOTTOMBIT == (this.status[parent] & BOTTOMBIT))
            this.isPercolated = true;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!this.isValid(row, col))
            throw new IndexOutOfBoundsException("row or col is out of side length!");
        return OPENBIT == (this.status[this.getElement(row, col)] & OPENBIT);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!this.isValid(row, col))
            throw new IndexOutOfBoundsException("row or col is out of side length!");
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
        return this.side * (row - 1) + col - 1;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}