public class MyClass {
    public static void main(String[] args) {
        char current_location = args[0].charAt(0);
        //state: true -> clean, false -> not clean
        boolean A_status = Boolean.parseBoolean(args[1]);
        boolean B_status = Boolean.parseBoolean(args[2]);
        boolean C_status = Boolean.parseBoolean(args[3]);
        boolean D_status = Boolean.parseBoolean(args[4]);

        System.out.println("Current Location = " + current_location + "\n" +
                "Square A status = " + A_status + "\n"+
                "Square B status = " + B_status + "\n"+
                "Square C status = " + C_status + "\n"+
                "Square D status = " + D_status + "\n");
        // ----------------
        // Assignment 1: Model-based Agent Function
        // ----------------

        char[][] rooms = {
                {'A', 'B'},
                {'C', 'D'}};

        if (A_status && B_status && C_status && D_status) {
            System.out.println("\nAction - Next Location = " + current_location);
            return;
        }

        //  vaccum cleaner only moves horiz or vertically

        // horiz moves have highest priority over vertical


        // If all squares clean, vacuum stays in current position

        // If the current loc is not clean, vacum stays in current loc. to cleanup

        // vacuum cleaner moves one square at a time

        System.out.println("\nAction - Next Location = " + current_location);
    }


    public void processCleaning() {

    }
}