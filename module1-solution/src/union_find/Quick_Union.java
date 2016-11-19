package union_find;

/**
 * Created by liangxibing on 19/11/16.
 */
public class Quick_Union {
    private int[] array;

    public Quick_Union(int n) {
        array = new int[n];
        for (int i : array)
            array[i] = i;
    }

    public boolean connected(int p, int q) {
        return findParent(p) == findParent(q);
    }

    public void union(int p, int q) {
        int parentP = findParent(p);
        int parentQ = findParent(q);
        if (parentP != parentQ) {
            array[parentP] = parentQ;
        }
    }

    private int findParent(int i) {
        int result = i;
        while (array[result] != result)
            result = array[result];
        return result;
    }
}
