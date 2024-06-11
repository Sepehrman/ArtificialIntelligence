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

        boolean[][] perceptRoomStatus = {
                {A_status, B_status},
                {C_status, D_status}
        };

        // If all
        if (A_status && B_status && C_status && D_status) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        getIndexFromChar(current_location);

        // If current room is clean, stay and clean current location then exit
        if (perceptRooms[horizIndex][vertIndex] == current_location && !perceptRoomStatus[horizIndex][vertIndex]) {
            System.out.println("Action - Next Location = " + current_location);
            return;
        }

        char nextLocation = findAction(perceptRooms, perceptRoomStatus, current_location);
        System.out.println("Action - Next Location = " + nextLocation);
    }

    public static Object[] findNextDirtyRoom(char[][] rooms, boolean[][] statuses, int horizIndex, int vertIndex) {

        char horizontalRoom = rooms[horizIndex][(vertIndex + 1) % rooms.length];
        boolean isClean = statuses[horizIndex][(vertIndex + 1) % rooms.length];

        // If the horizontal
        if (isClean) {
            horizontalRoom = rooms[(horizIndex + 1) % rooms.length][vertIndex];
            isClean = statuses[(horizIndex + 1) % rooms.length][vertIndex];
        }

        return new Object[]{horizontalRoom, isClean};
    }

    public static char findAction(char[][] rooms, boolean[][] statuses, char currentLocation) {

        // finds the next location based the current percept
        Object[] horizontalNeighbour = findNextDirtyRoom(rooms, statuses, horizIndex, vertIndex);
        return (char) horizontalNeighbour[0];
    }

    public static void getIndexFromChar(char curr_position) {
        switch (curr_position) {
            case 'A':
                horizIndex = 0;
                vertIndex = 0;
                break;
            case 'B':
                horizIndex = 0;
                vertIndex = 1;
                break;
            case 'C':
                horizIndex = 1;
                vertIndex = 0;
                break;
            case 'D':
                horizIndex = 1;
                vertIndex = 1;
                break;
        }
    }
}