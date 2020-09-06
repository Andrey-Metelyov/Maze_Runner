import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int z1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();
        int z2 = scanner.nextInt();

        int[] box1 = sortThree(new int[]{x1, y1, z1});
        int[] box2 = sortThree(new int[]{x2, y2, z2});

        if (box1[0] < box2[0] && box1[1] < box2[1] && box1[2] < box2[2]) {
            System.out.println("Box 1 < Box 2");
        } else if (box1[0] > box2[0] && box1[1] > box2[1] && box1[2] > box2[2]) {
            System.out.println("Box 1 > Box 2");
        } else {
            System.out.println("Incompatible");
        }
    }

    private static int[] sortThree(int[] ints) {
        assert ints.length == 3;
        int tmp;
        // sort first two
        if (ints[0] > ints[1]) {
            tmp = ints[0];
            ints[0] = ints[1];
            ints[1] = tmp;
        }
        // x <= y
        if (ints[0] > ints[2]) {
            // z is the smallest
            tmp = ints[0];
            ints[0] = ints[2];
            ints[2] = ints[1];
            ints[1] = tmp;
        } else if (ints[1] > ints[2]) {
            // swap y and z
            tmp = ints[1];
            ints[1] = ints[2];
            ints[2] = tmp;
        }
        return ints;
    }
}