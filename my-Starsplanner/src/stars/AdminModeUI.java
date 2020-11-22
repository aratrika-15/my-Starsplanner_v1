package stars;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminModeUI implements PrintMenuUI, DisplayErrorMsgUI{
    public void showMenu(User user) {
        //TODO
        if(user !=null)
        {
            //retrieve admin object from file controller
            //if admin object is not null
            int choice;
            Scanner sc = new Scanner(System.in);
            AdminModeController amc = new AdminModeController();
            SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm");
            do {
                System.out.println("");
                System.out.println("");
                System.out.println("Welcome to STARS! You may perform the following Actions:");
                System.out.println("--------------------------------------------------------");
                System.out.println("1: Edit Student Access Period");
                System.out.println("2: Add Student");
                System.out.println("3: Add Course");
                System.out.println("4: Update Course");
                System.out.println("5: Remove Course");
                System.out.println("6: Check Available Slots");
                System.out.println("7: Print Student List by Index");
                System.out.println("8: Print Student List by Course");
                System.out.println("9: Quit");
                System.out.print("-------Please Enter your choice:");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        amc.editStudentAccessPeriod();
                        break;
                    case 2:
                        amc.addStudent();
                        break;

                    case 3:
                        amc.addCourse();
                        break;
                    case 4:
                        amc.updateCourse();
                        break;
                    case 5:
                        amc.removeCourse();
                        break;
                    case 6:
                        amc.checkAvailableSlot();
                        break;
                    case 7:
                        ArrayList<String[]> stuListByIndex = amc.printStulistByIndex();
                        if (!stuListByIndex.isEmpty()){
                            displayStuList(stuListByIndex);
                        }
                        else {
                            System.out.println("No student has been added.");
                        }
                        break;
                    case 8:
                        ArrayList<String[]> stuListByCourse = amc.printStulistByCourse();
                        if (!stuListByCourse.isEmpty()) {
                            displayStuList(stuListByCourse);
                        }
                        else{
                            System.out.println("No student has been added.");
                        }
                        break;
                    case 9:
                        System.out.println("Program terminating Ö.");
                        break;
                    default:
                        System.out.println("Error! Please choose a valid option");
                        break;
                }
            } while (choice !=9);
        }
        else
            {
                displayErrorMsg("You are not allowed to access this admin menu. Sorry");
            }
        }
        public void displayErrorMsg(String errorMsg)
        {
                System.out.println(errorMsg);
        }
    /**
     * Print our arraylist of student arrays.
     * @param stuList
     */
    private void displayStuList(ArrayList<String[]> stuList){
        for(int i = 0; i < stuList.size(); i++) {
            String [] array = stuList.get(i);
            System.out.println(Arrays.toString(array));
        }
    }


}
