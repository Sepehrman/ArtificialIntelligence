import java.util.Random;

public class MyClass {

    int[][] adjacencyMatrix = {
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}
    };

    // Array for initial state, S0
    char[] currentState = {'b', 'o', 'o', 'o', 'r', 'r', 'j', 'j', 'j', 'j', 'j', 'j', 'j'};
    char[] colours = {'b', 'o', 'r', 'j'}; // blue, orange, red, jungle



    public static void main(String[] args) {
        int kValue;

        // If K-Value is not given, it is chosen by random between 1-10
        if (args.length != 0) {
            kValue = Integer.parseInt(args[0]);

        } else {
            Random rand = new Random();
            kValue = rand.nextInt(10) + 1;
        }

        System.out.println(kValue);



    }

}
