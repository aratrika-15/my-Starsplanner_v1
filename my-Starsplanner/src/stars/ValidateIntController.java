package stars;

import java.util.*;

/**
 * Controller class to ensure input by a user is as required
 */
public class ValidateIntController {
    Scanner sc = new Scanner(System.in);

    /**
     * Method to validate if an input by the user is int and is within a specific range
     * @param min minimum value of the range
     * @param max maximum value of the range
     * @return int value input if valid
     */
    public int validateInt(int min, int max) {
        do {
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                if (choice>=min && choice <=max) {
                    return choice;
                } else {
                    System.out.println("Invalid selection. Enter a valid input.");
                }
            }else {
                sc.next();
                System.out.println("Invalid selection.Enter a valid input.");
            }
        } while(true);
    }

}
