/**
 * @author Sepehr Mansouri
 * Artificial Intelligence - Assignment 1
 * The purpose of this assignment was to develop a Model-Based Reflex Agent for a vacuum cleaner.
 * This was achieved by keeping track of the internal state of each room and making decisions accordingly based on the
 * Horizontal and Vertical movement of the vacuum agent within a 2x2 environment.
 */
public class Assignment1 {

    private static int horizIndex = 0;
    private static int vertIndex = 0;
    private static final boolean[][] roomStates = new boolean[2][2];

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
        char[][] environment = {
                {'A', 'B'},
                {'C', 'D'}
        };

        // Update internal state with the percepts
        updateRoomState('A', A_status);
        updateRoomState('B', B_status);
        updateRoomState('C', C_status);
        updateRoomState('D', D_status);

        // If all rooms are clean, keep location at the current room and exit
        if (A_status && B_status && C_status && D_status) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        // Sets matrix index values given a room letter
        getIndexFromChar(current_location);

        // If current room is clean, stay and clean current location then exit
        if (environment[horizIndex][vertIndex] == current_location && !roomStates[horizIndex][vertIndex]) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        // Finds the relevant action provided there is a dirty room in neighbouring rooms
        char action = findAction(environment);
        System.out.println("\nAction - Next Location = " + action);
    }

    /**
     * Finds the appropriate action given the room and room status percepts. With the agent first moving horizontally
     * Then proceeding to move vertically if the neighbouring state is clean.
     * @param rooms a 2x2 Matrix of chars representing the rooms
     * @return an Action - Location of the agent.
     */
    public static char findAction(char[][] rooms) {

        // Circular horizontal movement to prevent out of bounds index
        char roomDirection = rooms[horizIndex][(vertIndex + 1) % rooms.length];
        boolean isClean = roomStates[horizIndex][(vertIndex + 1) % rooms.length];

        // If the horizontal neighbour is clean, move to vertical position.
        // Circular vertical movement to prevent out of bounds index
        if (isClean) {
            roomDirection = rooms[(horizIndex + 1) % rooms.length][vertIndex];
        }

        return roomDirection;
    }


    /**
     * Updates the internal state of the agent based on percepts
     * @param room a Character representing the room
     * @param status a boolean representing the status of the room (Dirty/Clean)
     */
    public static void updateRoomState(char room, boolean status) {
        switch (room) {
            case 'A':
                roomStates[0][0] = status;
                break;
            case 'B':
                roomStates[0][1] = status;
                break;
            case 'C':
                roomStates[1][0] = status;
                break;
            case 'D':
                roomStates[1][1] = status;
                break;
        }
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