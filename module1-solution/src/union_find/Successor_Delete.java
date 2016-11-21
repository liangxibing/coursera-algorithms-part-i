package union_find;

/**
 * Created by liangxibing on 21/11/16.
 */
public class Successor_Delete {
    private int[] array;
    private int[] weight;

    public Successor_Delete(int n) {
        array = new int[n];
        for (int i : array)
            array[i] = i;
        weight = new int[n];
        for (int i : weight)
            weight[i] = 1;
    }

    public void remove(int x) {
        union(x, x + 1);
    }

    public int successor(int x) {
        return findParent(x);
    }

    private void union(int p, int q) {
        int parentP = findParent(p);
        int parentQ = findParent(q);
        if (parentP != parentQ) {
            if (weight[parentP] > weight[parentQ]) {
                array[parentQ] = parentP;
                weight[parentP] += weight[parentQ];
            }
            else {
                array[parentP] = parentQ;
                weight[parentQ] += weight[parentP];
            }
        }
    }

    private int findParent(int i) {
        int result = i;
        while (array[result] != result) {
            array[result] = array[array[result]];
            result = array[result];
        }
        return result;
    }
}
