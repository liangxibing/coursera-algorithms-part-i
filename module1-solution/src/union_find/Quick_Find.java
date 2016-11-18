package union_find;

/**
 * Created by liangxibing on 18/11/16.
 */
public class Quick_Find {
    private int[] array;

    public Quick_Find(int n) {
        array = new int[n];
        for (int i : array) {
            array[i] = i;
        }
    }

    public void union(int p, int q) {
        int from = array[p];
        int to = array[q];
        if (from == to)
            return;
        for (int i : array) {
            if (array[i] == p)
                array[i] = q;
        }
    }

    public boolean connected(int p, int q) {
        return array[p] == array[q];
    }
}
