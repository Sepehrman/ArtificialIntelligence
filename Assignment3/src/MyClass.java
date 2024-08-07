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
    Random random = new Random();

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.hillClimbing();
    }

    public void hillClimbing() {
        System.out.print("Initial state: ");
        printStateArray(currentState);

        while (true) {
            int currentCost = calculateCost(currentState);
            System.out.println("Current cost: " + currentCost);

            // Generate a list of successors
            char[] bestState = null;
            int bestCost = Integer.MAX_VALUE;
            for (int i = 0; i < currentState.length; i++) {
                for (char colour : colours) {
                    if (currentState[i] != colour) {
                        char[] newState = currentState.clone();
                        newState[i] = colour;
                        int newCost = calculateCost(newState);
                        if (newCost < bestCost) {
                            bestCost = newCost;
                            bestState = newState;
                        }
                    }
                }
            }

            // If no better state is found, the algorithm has reached a local maximum
            if (bestCost >= currentCost) {
                System.out.print("Final state: ");
                printStateArray(currentState);
                System.out.println("Final cost: " + currentCost);
                break;
            }

            // Move to the best state found
            currentState = bestState;
        }
    }

    private int calculateCost(char[] state) {
        int cost = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 1 && state[i] == state[j]) {
                    cost++;
                }
            }
        }
        return cost;
    }

    private void printStateArray(char[] state) {
        for (char c : state) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}
