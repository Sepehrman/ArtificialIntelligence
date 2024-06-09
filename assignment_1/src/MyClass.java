public class MyClass {

    private static final int MAX_INDEX = 2;

    private static int horizIndex = 0;
    private static int vertIndex = 0;

    public static void main(String[] args) {
        char current_location = args[0].charAt(0);
        boolean A_status = Boolean.parseBoolean(args[1]);
        boolean B_status = Boolean.parseBoolean(args[2]);
        boolean C_status = Boolean.parseBoolean(args[3]);
        boolean D_status = Boolean.parseBoolean(args[4]);

        System.out.println("Current Location = " + current_location + "\n" +
                "Square A status = " + A_status + "\n" +
                "Square B status = " + B_status + "\n" +
                "Square C status = " + C_status + "\n" +
                "Square D status = " + D_status + "\n");

        char[][] perceptRooms = {
                {'A', 'B'},
                {'C', 'D'}
        };

        boolean[][] perceptStatus = {
          {A_status, B_status},
          {C_status, D_status}
        };

        if (A_status && B_status && C_status && D_status) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        convertCharToIndex(current_location);

        if (perceptStatus[horizIndex][vertIndex]) {
        } else {
            // Clean the current room
            return;
        }

        char nextLocation = getNextLocation(perceptRooms, perceptStatus, current_location);
        System.out.println("\nAction - Next Location = " + nextLocation);
    }
    
    public static Object[] findHorizontalNeighbour(char[][] rooms, boolean[][] statuses, int horizIndex, int vertIndex) {
        char horizontalRoom = rooms[horizIndex][(vertIndex + 1) % rooms[0].length];
        boolean isClean = statuses[horizIndex][(vertIndex + 1) % rooms.length];
        System.out.println("horiz neighbor: " + horizontalRoom + ": " + isClean);
        
        if (isClean) {
            horizontalRoom = rooms[(horizIndex + 1) % rooms[0].length][vertIndex];
            isClean = statuses[(horizIndex + 1) % rooms.length][vertIndex];
            System.out.println("vertical neighbor: " + horizontalRoom + ": " + isClean);
        }

        return new Object[]{horizontalRoom, isClean};
    }

    public static char getNextLocation(char[][] rooms, boolean[][] statuses, char currentLocation) {
            
        Object[] horizontalNeighbour = findHorizontalNeighbour(rooms, statuses, horizIndex, vertIndex);
        char nextRoom = (char) horizontalNeighbour[0];
        boolean nextStatus = (boolean) horizontalNeighbour[1];
            
        // Implement logic to find the next location based on the current percept
        // Example: move to the next horizontal neighbor if available, otherwise move to the next vertical neighbor
        return nextRoom;
    }

    public static void convertCharToIndex(char curr_position) {
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
}
