/**
 * @author Sepehr Mansouri
 * Artificial Intelligence - Assignment 1
 */
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

        // A 2x2 Matrix representing the room names
        char[][] perceptRooms = {
                {'A', 'B'},
                {'C', 'D'}
        };

        // A 2x2 Matrix representing the status of each room
        boolean[][] perceptRoomStatus = {
                {A_status, B_status},
                {C_status, D_status}
        };

        // If all rooms are clean, keep location at the current room and exit
        if (A_status && B_status && C_status && D_status) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        // Sets matrix index values given a room lettter
        getIndexFromChar(current_location);

        // If current room is clean, stay and clean current location then exit
        if (perceptRooms[horizIndex][vertIndex] == current_location && !perceptRoomStatus[horizIndex][vertIndex]) {
            System.out.println("Action - Next Location = " + current_location);
            return;
        }

        // Finds the relevant action provided dirty rooms
        char nextLocation = findAction(perceptRooms, perceptRoomStatus);
        System.out.println("Action - Next Location = " + nextLocation);
    }

    /**
     * Finds the appropriate action given the room and room status percepts
     * @param rooms a 2x2 Matrix of chars
     * @param statuses a 2x2 Matrix of the room status (Dirty/Clean)
     * @return an Action - Location of the agent.
     */
    public static char findAction(char[][] rooms, boolean[][] statuses) {

        char roomDirection = rooms[horizIndex][(vertIndex + 1) % rooms.length];
        boolean isClean = statuses[horizIndex][(vertIndex + 1) % rooms.length];

        // If the horizontal neighbour is clean, move to vertical position.
        if (isClean) {
            roomDirection = rooms[(horizIndex + 1) % rooms.length][vertIndex];
        }

        return roomDirection;
    }

    /**
     * Responsible for converting the given starting position to indices on a 2x2 Maatrix
     * @param curr_position a Character representing the starting position of the agent.
     */
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