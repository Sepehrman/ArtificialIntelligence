import java.util.ArrayList;
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
    public static char[] colors = {'b', 'o', 'r', 'j'};
    int kValue;


    public ArrayList<char[]> successorFunction() {
        ArrayList<char[]> successorStates = new ArrayList<>();
        for (int index = 0; index < currentState.length; index++) {
            char currentColour = currentState[index];
            for (char color : colorSet) {
                if (color != currentColour) {
                    char[] currentSuccessor = currentState.clone();
                    currentSuccessor[index] = color;
                    successorStates.add(currentSuccessor);
                }
            }
        }
        return successorStates;
    }

    public int costFunction() {
        int totalCost = 0;
        for (char color : currentState) {
            totalCost += mapCost(color);
        }

        return totalCost;
    }

    public static ArrayList<Character> colorSet = new ArrayList<>();


    public int mapCost(char color) {
        int cost = 0;
        switch(color) {
            case 'b':
                cost = 1;
                break;
            case 'o':
                cost = 3;
                break;
            case 'r':
                cost = 2;
                break;
            case 'j':
                cost = 5;
                break;
        }
        return cost;
    }

    /**
     * Heuristic Function that finds the number of adjacent regions that share the same color
     * @param currentState Array of Characters of the current state
     * @return an Integer that represents the number of adjacent regions that share the same color.
     */
    public int heuristicFunction(char[] currentState) {
        int counter = 0;
        for (int i = 0; i < currentState.length; i++) {
            for (int j = i + 1; j < currentState.length; j++) {
                // Check if there are adjacent regions and share the same color
                if (adjacencyMatrix[i][j] == 1 && currentState[i] == currentState[j]) {
                    counter++;
                }
            }
        }
        System.out.println(counter);
        return counter;
    }

    public void runHillClimbing() {
        System.out.println("Initial State: ");
        printStateArray(currentState);
        int currentCost = costFunction();

        while (!hasGoalState()) {
            ArrayList<char[]> successorStates = successorFunction();
            int currentHeuristic = heuristicFunction(currentState);
            int nextCost = costFunction();
            char[] nextState = null;

            for (char[] successor : successorStates) {

                int successorHeuristic = heuristicFunction(successor);

                if (heuristicFunction(successor) < currentHeuristic) {
                    nextState = successor;
                    currentHeuristic = successorHeuristic;
                }
            }

            // Move to the successor state if it has a better heuristic or equal heuristic but lower cost
            if (nextState != null) {
                currentState = nextState;
//                currentHeuristic = currentHeuristic;
                currentCost = nextCost;
            } else {
                break; // Local maximum reached
            }
        }
        System.out.println("Total cost: " + currentCost + "\n");

        System.out.println("Final State:");
        printStateArray(currentState);
    }

    private void printStateArray(char[] state) {
        for (char c : state) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * Check if a matrix is a goal state
     * @return a Boolean representing if a goal state has reached
     */
    public boolean hasGoalState() {
        return heuristicFunction(currentState) == 0;
    }


    /**
     * Initializes the Initial State of our map
     * @param k An Integer representing the K-Value
     */
    public MyClass(int k) {
        this.kValue = k;
        // Only allow the first k colours from the available set of colours
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide a valid k-value as a command line");
            return;
        }
        int k = Integer.parseInt(args[0]);

        if (k < 2 || k > 4)

        System.out.println("K-value is: " + k);

        for (int index = 0; index < k; index++) {
            colorSet.add(colors[index]);
        }

        MyClass myClass = new MyClass(k);
        myClass.runHillClimbing();

    }

}
