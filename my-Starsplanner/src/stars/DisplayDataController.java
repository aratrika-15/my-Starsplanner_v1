package stars;

import java.util.ArrayList;
import java.util.Scanner;

public class DisplayDataController {
    Scanner sc = new Scanner(System.in);
    FileController fc = new FileController();

    public DisplayDataController() {}

    /**
     * Retrieve list of schools from database and print it out for user to see and select.
     * @return School
     */
    public School schSelection() {
        int schSelection;
        boolean flag = true;

        ArrayList<School> schList = fc.getSchoolList();

        do {
            // Get user's selection of school
            System.out.println("Select the school:");
            for(int i = 0; i < schList.size(); i++) {
                System.out.println(i+1 + ". " + schList.get(i).getName());
            }

            schSelection = sc.nextInt();

            if (schSelection > 0 && schSelection < schList.size()+1) {
                School school = schList.get(schSelection-1);
                flag = false;
                return school;
            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);

        return null;
    }

    /**
     * Retrieve list of courses from selected school and display for user to see and select.
     * Allows user to choose sorting method to sort by either number of reviews or by percentage recommended.
     * @param sch
     * @return Course
     */
    public Course courseSelection(School sch) {
        int courseSelection; int select;
        boolean flag = true;

        ArrayList<Course> schCourses = sch.getCourses();

        /*do {
            System.out.println("Select the sorting method");
            System.out.println("1. Sort by number of reviews");
            System.out.println("2. Sort by percentage recommended");
            select = sc.nextInt();

            switch (select) {
                case 1:
                    schCourses.sort(new NumReviewsSorter());
                    flag = false;
                    break;
                case 2:
                    schCourses.sort(new PercRecommendedSorter());
                    flag= false;
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        } while(flag);*/

        flag = true;
        do {
            // Get user's selection of courses
            System.out.println("Select the course:");
            for(int i = 0; i < schCourses.size(); i++) {
                System.out.println(i+1 + ". " + schCourses.get(i).getName());
            }
            courseSelection = sc.nextInt();

            if (courseSelection > 0 && courseSelection < schCourses.size() + 1) {
                Course course = schCourses.get(courseSelection - 1);
                flag = false;
                return course;
            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);
        return null;
    }

    /**
     * Retrieve list of indexes from selected course and display for user to see and select.
     * @param course
     * @return Index
     */
    public Index indexSelection(Course course) {
        int indexSelection;
        boolean flag = true;
        ArrayList<Index> indexList = course.getIndex();

        do {
            System.out.println("Select the index:");
            for(int i = 0; i < indexList.size(); i++){
                System.out.println(i+1 + ". " + indexList.get(i).getIndexNum());
            }
            indexSelection = sc.nextInt();

            if(indexSelection> 0 && indexSelection < indexList.size()+1){
                Index index = indexList.get(indexSelection - 1);
                flag = false;
                return index;
            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);
        return null;
    }

    public RegisteredCourse selectRegisteredCourses(Student s) {
        ArrayList<RegisteredCourse> regCourses = s.getRegCourses();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        int choice;

        do {
            if (!regCourses.isEmpty()) {
                System.out.println("Select the course:");
                for(int i =0; i< regCourses.size(); i++) {
                    Index idx = fc.getIndexByID(regCourses.get(i).getRegIndex());
                    Course c = fc.getCourseByCode(idx.getCourse());
                    System.out.println(i+1+ ". " + c.getCourseCode());
                }
            }
            else {
                System.out.println("No registered courses.");
                flag = false;
                break;
            }

            choice = sc.nextInt();

            if(choice> 0 && choice < regCourses.size()+1){
                RegisteredCourse rc = regCourses.get(choice - 1);
                flag = false;
                return rc;
            }
            else {
                System.out.println("Invalid selection.");
            }
        }while(flag);

        return null;
    }
}