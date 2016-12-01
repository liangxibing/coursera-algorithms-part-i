import java.util.Arrays;

public class ThreeSum {

    public int countThreeSumPair(int[] a) {
        Arrays.sort(a);
        int count = 0;
        int begin = -1;
        int end = -1;
        int length = a.length;
        for (int i = 0; i < length - 2; i++) {
            begin = i + 1;
            end = length - 1;
            while (begin < end) {
                if (0 < a[i] + a[begin] + a[end]) {
                    end -= 1;
                }
                else if (0 > a[i] + a[begin] + a[end]) {
                    begin += 1;
                }
                else {
                    count++;
                    end -= 1;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] a = {-25, -10, -7, -3, 0, 2, 4, 8, 10};
        System.out.println(new ThreeSum().countThreeSumPair(a));
    }
}