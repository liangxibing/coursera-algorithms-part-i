public class EggDrop {

    public int findT(boolean[] a) {
        if (a[0])
            return 1;
        int index = 1;
        while (!a[index * index]) {
            index++;
        }
        int t = index * index - 2 * index + 2;
        while (t < index * index) {
            if (a[t])
                break;
            t++;
        }
        return t + 1;
    }

    public static void main(String[] args) {
        boolean[] a = {false, false, false, false, false, false, false, true, true, true};
        System.out.println(new EggDrop().findT(a));
    }
}