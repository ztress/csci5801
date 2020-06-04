/**
 * Main
 *
 * The main class takes in user input, calls necessary functions to run the election, and displays the output
 * It also calls the necessary functions to create an audit file
 *
 * @author Jessica Moore
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Election!");

        System.out.println("Type 'help' for a description of how to use this software. Type anything else to continue.");
        if (scan.nextLine().equals("help")) {
            helpOption();
        }

        System.out.println("Enter number of seats to be filled: ");
        boolean validInt = false;
        int numSeats = 0;
        while (!validInt) {
            try {
                numSeats = Integer.parseInt(scan.nextLine());
                validInt = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid integer for number of seats to be filled: ");
            }
        }

        System.out.println("Enter location of ballot file (separate file names by commas): ");
        String fileLocation = scan.nextLine();

        System.out.println("Enter election type (type 's' for STV, 'p' for plurality): ");
        String electionType = scan.nextLine();
        while (!(electionType.equals("s") || electionType.equals("p"))) {
            System.out.println("Please enter valid election type (type 's' for STV, 'p' for plurality):");
            electionType = scan.nextLine();
        }

        System.out.println("Press enter to begin the election.");
        scan.nextLine();
        System.out.println("Your results for your election will be displayed shortly. A report of your election will be generated at Election-Audit-File.");
        // create instance of election and run election
        Election election;
        if (electionType.equals("s")) {
            if ((args.length > 0) && (args[0].equals("-s"))) {
                election = new STVElection(numSeats, fileLocation, false);
            } else {
                election = new STVElection(numSeats, fileLocation, true);
            }
        } else {
            election = new PluralityElection(numSeats, fileLocation);
        }
        scan.close();
        election.runElection();
        election.displayStats();

        // generate log file
        Report report = new Report(election);
        report.createLog();
    }

    /**
     * Prints out the help menu for the user
     * Tells the user how to run and use the program
     */
    private static void helpOption() {
        System.out.println("This is a software to calculate the results of an election.");
        System.out.println("You will have to enter 3 things as prompted to use this software.");
        System.out.println("To begin, enter a number indicating the number of seats. " +
                "You must enter a valid positive integer.");
        System.out.println("Next, enter the location of the ballot files. " +
                "Please enter the path of the files, comma separated if there are multiple.");
        System.out.println("Lastly, enter the type of election that is being run." +
                "If you would like to run an STV election, please enter 's'." +
                "If you would like to run a plurality election, please enter 'p'.");
        System.out.println("Once all the information has been entered, please enter to begin the election.");
    }
}
