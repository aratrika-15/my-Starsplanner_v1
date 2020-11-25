package stars;

import java.io.Console;
import java.util.*;

/**
 * Boundary Class to show login option and get users login input
 */
public class LoginUI {

    /**
     * Username entered by current user
     */
    private static String enteredUsername;
    /**
     * Password entered by current user
     */
    private static String enteredPassword;
    /**
     * User domain entered by current user
     */
    private static String typeOfUser;

    public static void main(String[] args) {
        FileController fc = new FileController();
        /**
         * initialise the database once before first use of STARS system
         */
//        fc.initialise();
        /**
         * Retrieve all .dat files needed to run STARS system
         */
        fc.RetrieveAdmins();
        fc.RetrieveCourses();
        fc.RetrieveStudents();
        fc.RetrieveSchools();

        displayWelcomeMessage();
        Scanner sc = new Scanner(System.in);
        LoginController login = new LoginController();
        Console console = System.console();


        System.out.print("Enter your username: ");
        enteredUsername= sc.next();
        if (console != null) {
            System.out.print("Please enter your password: ");
            char[] passString = console.readPassword();
            enteredPassword = new String(passString);

        } else {
            System.out.print("Please enter your password: ");
            enteredPassword = sc.next().trim();
        }
        System.out.print("Enter type of User (Student/Admin): ");
        typeOfUser = sc.next();

        while (!login.validateLogin(enteredUsername, enteredPassword, typeOfUser)) {

            System.out.print("Enter your username: ");
            enteredUsername = sc.next();
            if (console != null) {
                System.out.print("Please enter your password ");
                char[] passString = console.readPassword();
                enteredPassword = new String(passString);

            } else {
                System.out.print("Please enter your password :");
                enteredPassword = sc.next().trim();
            }
            System.out.print("Enter type of User (Student/Admin): ");
            typeOfUser = sc.next();

        }

        sc.close();

    }

    private static void displayWelcomeMessage()
    {
        System.out.println("");
        System.out.println("Welcome!");
        System.out.println("#############################################################");
        System.out.println("|\t\t___  ___      _____ _____ ___  ______  _____ \t\t|");
        System.out.println("|\t\t|  \\/  |     /  ___|_   _/ _ \\ | ___ \\/  ___|\t\t|");
        System.out.println("|\t\t| .  . |_   _\\ `--.  | |/ /_\\ \\| |_/ /\\ `--. \t\t|");
        System.out.println("|\t\t| |\\/| | | | |`--. \\ | ||  _  ||    /  `--. \\\t\t|");
        System.out.println("|\t\t| |  | | |_| /\\__/ / | || | | || |\\ \\ /\\__/ /\t\t|");
        System.out.println("|\t\t\\_|  |_/\\__, \\____/  \\_/\\_| |_/\\_| \\_|\\____/ \t\t|");
        System.out.println("|\t\t         __/ |                               \t\t|");
        System.out.println("|\t\t        |___/                                \t\t|");
        System.out.println("");
        System.out.println("#############################################################");
        System.out.println("Login to begin interaction with the planner");
        System.out.println("*******************************************");

    }

}
