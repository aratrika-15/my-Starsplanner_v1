package stars;
import java.util.Scanner;

public class StudentModeUI implements PrintMenuUI, DisplayErrorMsgUI {
    public void showMenu(User user) {
        if (user != null) {
            //first use FileController to get student object reference by username
            //check if the student user !=null
            //if student is not null, then check if it is within access period. If true, display menu, else display error message
            int choice;
            Scanner sc = new Scanner(System.in);
            StudentModeController smc = new StudentModeController();
            FileController fc=new FileController();
            DisplayDataController dd = new DisplayDataController();
            do {
                System.out.println("");
                System.out.println("");
                System.out.println("Welcome to STARS Student Version! You may perform the following Actions:");
                System.out.println("--------------------------------------------------------");
                System.out.println("1: Add a Course");
                System.out.println("2: Drop a Course");
                System.out.println("3: Print Registered Courses and Timetable");
                System.out.println("4: Check Vacancies");
                System.out.println("5: Change Index Number");
                System.out.println("6: Swap Index Number");
                System.out.println("7: Check Access Period");
                System.out.println("8: Add Review");//new
                System.out.println("9: Edit Review");//new
                System.out.println("10: Delete Review");//new
                System.out.println("11: View Courses and Reviews");//new
                System.out.println("12: Quit");//new
                System.out.print("-------Please Enter your choice:");
                choice = sc.nextInt();
                Student student = (Student)user;
                Course c;
                Index i;
                switch (choice) {
                    case 1:
                        smc.inputAddCourse(student);
                        break;
                    case 2:
                        RegisteredCourse rc = dd.selectRegisteredCourses(student);
                        if (rc != null) {
                            Index idx = fc.getIndexByID(rc.getRegIndex());
                            Course cDrop = fc.getCourseByCode(idx.getCourse());
                            smc.dropCourse(student, cDrop, idx, rc);
                        }
                        break;
                    case 3:
                        smc.printRegisteredCourses(student);
                        break;
                    case 4:
                        smc.checkVacanciesAvailable();
                        break;
                    case 5:
                        smc.changeIndexNumber(student);
                        break;
                    case 6:



                        smc.swapIndexnumber(student);
                        break;
                    case 7:
                        School school= fc.getSchoolByName(student.getSchool());
                        smc.checkAccessPeriod(school);
                        break;
                    case 8:////new
                        smc.addReview(student) ;
                        break;
                    case 9:////new
                        smc.editReview(student);
                        break;
                    case 10://new
                        smc.deleteReview(student);
                        break;
                    case 11://new
                        smc.displayReviews();
                        break;
                    case 12://updated
                        fc.saveSchoolList();
                        fc.saveStudentList();
                        fc.saveAdminList();
                        fc.saveCourseList();
                        System.out.println("Program terminating .");
                        break;
                    default:
                        System.out.println("Error!");
                        break;
                }
            } while (choice != 12);
        }
        else
        {
            displayErrorMsg("You are not allowed to access this student menu. Sorry");
        }
        }
        public void displayErrorMsg(String errorMsg)
        {
            System.out.println(errorMsg);
        }


}
