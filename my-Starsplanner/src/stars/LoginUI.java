package stars;

import java.io.Console;
import java.util.*;

public class LoginUI {


private static String enteredUsername;
private static String enteredPassword;
private static String typeOfUser;

    public static void main(String[] args) {
        FileController fc = new FileController();
        //fc.initialise();
        fc.RetrieveAdmins();
        fc.RetrieveCourses();
        fc.RetrieveStudents();
        fc.RetrieveSchools();

        displayWelcomeMessage();
        Scanner sc = new Scanner(System.in);
        LoginController login = new LoginController();
        Console console = System.console();
        String enteredPassword;


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
        System.out.println("Login to begin interaction");
        System.out.println("*******************");
    }

}
