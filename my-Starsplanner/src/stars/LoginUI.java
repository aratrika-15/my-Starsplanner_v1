package stars;

import java.util.*;

public class LoginUI {


private static String enteredUsername;
private static String enteredPassword;
private static String typeOfUser;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        displayWelcomeMessage();
        Scanner sc = new Scanner(System.in);
        LoginController login = new LoginController();


        System.out.print("Enter your username: ");
        enteredUsername= sc.next();
        System.out.print("Enter your password: ");
        enteredPassword = sc.next();
        System.out.print("Enter type of User (Student/Admin): ");
        typeOfUser = sc.next();
        while (!login.validateLogin(enteredUsername, enteredPassword, typeOfUser)) {

            System.out.print("Enter your username: ");
            enteredUsername = sc.next();
            System.out.print("Enter your password: ");
            enteredPassword = sc.next();
            System.out.print("Enter type of User (Student/Admin): ");
            typeOfUser = sc.next();

        }

        sc.close();

    }

    private static void displayWelcomeMessage()
    {
        System.out.println("");
        System.out.println("");
        System.out.println("Welcome to STARS");
        System.out.println("");
        System.out.println("Login required");
        System.out.println("*******************");
    }

}
