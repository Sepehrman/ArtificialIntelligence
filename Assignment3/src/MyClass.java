import java.util.ArrayList;

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
    public static ArrayList<Character> colorSet = new ArrayList<>();
    int kValue;
    private boolean improvementFound;

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

    /**
     * Maps the cost of a color to
     * @param color
     * @return
     */
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
        return counter;
    }

    public void runHillClimbing() {
        int currentCost = costFunction();

        System.out.println("Initial State: " + printState(currentState));
        System.out.println("Initial Cost: " + currentCost);

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
                currentCost = nextCost;
            } else {
                break; // Local maximum reached
            }
        }
        System.out.println("\nFinal State:" + printState(currentState));
        System.out.println("Total cost: " + currentCost + "\n");

    }

    private String printState(char[] state) {
        StringBuilder output = new StringBuilder("");
        for (char c : state) {
            output.append(c + " ");
        }

        return output.toString();
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

        if (k == 3) {
            // Replace 'j' with a color that costs less
            updateToThreeStates();
        }
    }


    public void updateToThreeStates() {
        char[] newInitialState = currentState.clone();
        // Loop through all colors in the state array
        for (int index = 0; index < newInitialState.length; index++) {
            if (newInitialState[index] == 'j') {
                // Replace the fourth color 'j' with a valid color
                newInitialState[index] = getCheapestValidColour(index, newInitialState);
            }
        }
        currentState = newInitialState; // Update the current state
    }



    public char getCheapestValidColour(int regionIndex, char[] currentState) {
        // Step 1: Find colors used by adjacent regions
        boolean[] usedColors = new boolean[colors.length]; // Track colors that are in use by adjacent regions
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[regionIndex][i] == 1) { // If i is adjacent to regionIndex
                char adjacentColor = currentState[i];
                // Mark the color as used
                for (int j = 0; j < colors.length; j++) {
                    if (colors[j] == adjacentColor) {
                        usedColors[j] = true;
                        break;
                    }
                }
            }
        }

        // Step 2: Find the cheapest valid color
        char cheapestColor = colors[0];
        int minCost = Integer.MAX_VALUE;

        for (int j = 0; j < colors.length; j++) {
            if (!usedColors[j]) { // If color is not used by adjacent regions
                int colorCost = mapCost(colors[j]);
                if (colorCost < minCost) {
                    minCost = colorCost;
                    cheapestColor = colors[j];
                }
            }
        }

        return cheapestColor; // Return the cheapest valid color
    }


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide a valid k-value as a command line");
            return;
        }
        int kValue = Integer.parseInt(args[0]);

        if (kValue <= 2 || kValue > 4)

            System.out.println("K-value is: " + kValue);

        for (int index = 0; index < kValue; index++) {
            colorSet.add(colors[index]);
        }

        MyClass myClass = new MyClass(kValue);
        myClass.runHillClimbing();

    }

}