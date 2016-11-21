package union_find;

/**
 * Created by liangxibing on 21/11/16.
 */
public class Social_Network_Connectivity {
    private int[] array;
    private int[] weight;
    private boolean fullConnected = false;

    public Social_Network_Connectivity(int n) {
        array = new int[n];
        weight = new int[n];
        for (int i : array)
            array[i] = i;
        for (int i : weight)
            weight[i] = 1;
    }

    public boolean determine() {
        return fullConnected;
    }

    public void union(int p, int q) {
        int parentP = findParent(p);
        int parentQ = findParent(q);
        if (parentP != parentQ) {
            if (weight[parentP] > weight[parentQ]) {
                array[parentQ] = parentP;
                weight[parentP] += weight[parentQ];
                if (weight.length == weight[parentP])
                    fullConnected = true;
            }
            else {
                array[parentP] = parentQ;
                weight[parentQ] += weight[parentP];
                if (weight.length == weight[parentQ])
                    fullConnected = true;
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
