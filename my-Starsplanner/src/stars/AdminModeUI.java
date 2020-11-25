package stars;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Boundary Class to display the admin functions to and get choice from an admin user
 */
public class AdminModeUI implements PrintMenuUI, DisplayErrorMsgUI{
    /**
     * Prints Admin mode menu of STARS system
     * @param user Admin user
     */
    public void showMenu(User user) {
        if(user !=null)
        {
            int choice;
            Scanner sc = new Scanner(System.in);
            AdminModeController amc = new AdminModeController();
            FileController fc=new FileController();
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
                            System.out.println("   Name   Gender   Nationality   ");
                            System.out.println("===================================");
                            displayStuList(stuListByIndex);
                        }
                        else {
                            displayErrorMsg("No student has been added.");
                        }
                        break;
                    case 8:
                        ArrayList<String[]> stuListByCourse = amc.printStulistByCourse();
                        if (!stuListByCourse.isEmpty()) {
                            System.out.println("   Name   Gender   Nationality   ");
                            System.out.println("===================================");
                            displayStuList(stuListByCourse);
                        }
                        else{
                            displayErrorMsg("No student has been added.");
                        }
                        break;
                    case 9:
                        fc.saveSchoolList();
                        fc.saveStudentList();
                        fc.saveAdminList();
                        fc.saveCourseList();
                        System.out.println("Program terminating .");
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
     * @param stuList array list of students
     */
    private void displayStuList(ArrayList<String[]> stuList){
        for(int i = 0; i < stuList.size(); i++) {
            String [] array = stuList.get(i);
            System.out.println(Arrays.toString(array));
        }
    }


}
