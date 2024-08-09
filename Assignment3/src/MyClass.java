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
    char[] colours = {'b', 'o', 'r', 'j'};
    int kValue;


    public char[] successorFunction() {
        Random rand = new Random();
        char[] newState = currentState.clone();

        // Randomly select a region to change its color
        int regionToChange = rand.nextInt(currentState.length);
        char currentColor = newState[regionToChange];

        // Ensure that the new color is different from the current color
        char newColor;
        do {
            newColor = colours[rand.nextInt(kValue)];
        } while (newColor == currentColor);

        newState[regionToChange] = newColor;
        return newState;
    }

    public int costFunction() {
        int totalCost = 0;
        for (char color : currentState) {
            totalCost += mapCost(color);
        }

        return totalCost;
    }

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

        int currentHeuristic = heuristicFunction(currentState);
        int currentCost = costFunction();

        while (currentHeuristic > 0) {
            char[] nextState = successorFunction();
            int nextHeuristic = heuristicFunction(nextState);
            int nextCost = costFunction();

            // Move to the successor state if it has a better heuristic or equal heuristic but lower cost
            if (nextHeuristic < currentHeuristic || (nextHeuristic == currentHeuristic && nextCost < currentCost)) {
                currentState = nextState;
                currentHeuristic = nextHeuristic;
                currentCost = nextCost;
            } else {
                break; // Local maximum reached
            }
        }

        System.out.println("Final state after hill climbing:");
        printStateArray(currentState);
        System.out.println("Heuristic: " + currentHeuristic);
        System.out.println("Total cost: " + currentCost);

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
            System.out.println("Please provide the k-Value as a Command Line Argument.");
            return;
        }

        int k = Integer.parseInt(args[0]);
        MyClass myClass = new MyClass(k);
        myClass.runHillClimbing();

    }

}
