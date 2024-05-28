public class MyClass {

    private static final int MAX_MOVE = 1;
    private static final int MAX_INDEX = 2;

    private static int horizIndex = 0;
    private static int vertIndex = 0;

    public static void main(String[] args) {
        char current_location = args[0].charAt(0);
        // state: true -> clean, false -> not clean
        boolean A_status = Boolean.parseBoolean(args[1]);
        boolean B_status = Boolean.parseBoolean(args[2]);
        boolean C_status = Boolean.parseBoolean(args[3]);
        boolean D_status = Boolean.parseBoolean(args[4]);

        System.out.println("Current Location = " + current_location + "\n" +
                "Square A status = " + A_status + "\n" +
                "Square B status = " + B_status + "\n" +
                "Square C status = " + C_status + "\n" +
                "Square D status = " + D_status + "\n");

        char[][] rooms = {
                {'A', 'B'},
                {'C', 'D'}
        };
        convertChartToIndex(current_location);

        if (A_status && B_status && C_status && D_status) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        // TODO: find next neighbour in horizontal and vertical positions

        // Incomplete while loop - placeholder for your logic
        // while () {
        // }

        // Do 1 single move
        // vacuum cleaner only moves horizontally or vertically
        // horizontal moves have highest priority over vertical
        // If all squares clean, vacuum stays in current position
        // If the current location is not clean, vacuum stays in current location to clean up
        // vacuum cleaner moves one square at a time

        System.out.println("\nAction - Next Location = " + current_location);
    }

    public static void convertChartToIndex(char curr_position) {
        switch (curr_position) {
            case 'A':
                horizIndex = 0;
                vertIndex = 0;
                break;
            case 'B':
                horizIndex = 1;
                vertIndex = 0;
                break;
            case 'C':
                horizIndex = 0;
                vertIndex = 1;
                break;
            case 'D':
                horizIndex = 1;
                vertIndex = 1;
                break;
        }
    }

    public void findHorizNeighbour(char input) {
        // Implementation needed
    }

    private char translatePositionToChar() {
        // Implementation needed
        return ' ';
    }

    public void processCleaning() {
        // Implementation needed
    }
}
