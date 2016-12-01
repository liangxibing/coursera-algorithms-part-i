public class BitonicArray {

    public boolean find(int[] a, int key) {
        if (null == a || a.length <= 0)
            return false;
        int lo = 0;
        int hi = a.length - 1;
        int mid = -1;
        int first;
        int second;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            first = a[mid - 1];
            second = a[mid];
            if (second < key) {
                if (first < second)
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
            else if (second > key)
                if (first < second)
                    hi = mid - 1;
                else
                    lo = mid + 1;
            else
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {2,3,4,6,7,123,41241,532424,124125124};
        System.out.println(new BitonicArray().find(a, 7));
    }
}