public class BinarySearch {

    public int binarySearch(int[] a, int key) {
        if (null == a || a.length <= 0)
            return -1;
        int lo = 0;
        int hi = a.length - 1;
        int mi;
        while (lo <= hi) {
            mi = lo + (hi - lo) / 2;
            if (key < a[mi])
                hi = mi - 1;
            else if (key > a[mi])
                lo = mi + 1;
            else
                return mi;
        }
        return -1;
    }
}