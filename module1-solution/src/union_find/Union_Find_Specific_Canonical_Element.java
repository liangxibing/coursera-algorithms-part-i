package union_find;

/**
 * Created by liangxibing on 21/11/16.
 */
public class Union_Find_Specific_Canonical_Element {
    private int[] array;
    private int[] weight;
    private int[] max;

    public Union_Find_Specific_Canonical_Element(int n) {
        array = new int[n];
        for (int i : array)
            array[i] = i;
        weight = new int[n];
        for (int i : weight)
            weight[i] = 1;
        max = new int[n];
        for (int i : max)
            max[i] = i;
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
                max[parentP] = Math.max(max[parentP], max[parentQ]);
            }
            else {
                array[parentP] = parentQ;
                weight[parentQ] += weight[parentP];
                max[parentQ] = Math.max(max[parentP], max[parentQ]);
            }
        }
    }

    private int findParent(int i) {
        int result = i;
        int temp;
        while (array[result] != result) {
            array[result] = array[array[result]];
            result = array[result];
        }
        return result;
    }

    public int find(int i) {
        return max[findParent(i)];
    }
}
