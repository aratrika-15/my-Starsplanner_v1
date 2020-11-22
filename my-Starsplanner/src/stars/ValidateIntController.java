package stars;

import java.util.*;
public class ValidateIntController {
    Scanner sc = new Scanner(System.in);
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
