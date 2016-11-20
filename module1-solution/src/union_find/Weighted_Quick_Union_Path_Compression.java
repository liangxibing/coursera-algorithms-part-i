package union_find;

/**
 * Created by liangxibing on 20/11/16.
 */
public class Weighted_Quick_Union_Path_Compression {
    private int[] array;
    private int[] weight;

    public Weighted_Quick_Union_Path_Compression(int n) {
        array = new int[n];
        for (int i : array)
            array[i] = i;
        weight = new int[n];
        for (int i : weight)
            weight[i] = 1;
    }

    public boolean connected(int p, int q) {
        return findParent(p) == findParent(q);
    }

    public void union(int p, int q) {
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
