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
    final char[] INITIAL_STATE = {'b', 'o', 'o', 'o', 'r', 'r', 'j', 'j', 'j', 'j', 'j', 'j', 'j'};
    char[] colours; // blue, orange, red, jungle
    int kValue;


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the k-Value as a Command Line Argument.");
            return;
        }

        int k = Integer.parseInt(args[0]);
        MyClass myClass = new MyClass(k);
        myClass.runHillClimbing();
    }

    /**
     * Initializes the Initial State of our map
     * @param k
     */
    public MyClass(int k) {
        this.kValue = k;
        // Only allow the first k colours from the available set of colours
        char[] allColours = INITIAL_STATE;
        this.colours = new char[k];
        System.arraycopy(allColours, 0, colours, 0, k);

        System.out.print("Initial state: ");
        printStateArray(currentState);

        System.out.println(costFunction());
    }


    public void successorFunction() {

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


    public void heuristicFunction() {

    }

    public void runHillClimbing() {


    }

    private void printStateArray(char[] state) {
        for (char c : state) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

}
