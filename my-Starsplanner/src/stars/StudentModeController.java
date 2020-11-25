package stars;
import java.util.*;
import de.zabuza.grawlox.*;

import java.io.*;

/**
 * Controller class to facilitate the logic for the Student functions
 */
public class StudentModeController {
    /**
     * used to get user's input
     */
    Scanner sc=new Scanner(System.in);

    /**
     * Student to select from menu which school, which course and which index the student wants to register for
     * Student will only be taken to the addcourse method if index is valid
     * @param student student login
     */
    public void inputAddCourse(Student student){
        DisplayDataController dd = new DisplayDataController();


        School school1 = dd.schSelection();
        if (school1 != null) {
            Course course = dd.courseSelection(school1);
            if(course != null) {
                Index index = indexInput(course);
                if(index != null){
                    addCourse(student,student.getRegCourses(),course,index);
                }
            }
        }
    }

    /**
     * Print out schedule of index of selected course then ask student to select index
     * @param course course selected by student
     * @return index chosen by student
     */
    private Index indexInput(Course course) {
        ValidateIntController vc = new ValidateIntController();

        String time_1, time_2;

        Map<Integer, String> week = new HashMap<>();
        week.put(1, "Monday");
        week.put(2, "Tuesday");
        week.put(3, "Wednesday");
        week.put(4, "Thursday");
        week.put(5, "Friday");
        week.put(6, "Saturday");
        week.put(7, "Sunday");

        ArrayList<Index> indexes = course.getIndex();
        System.out.println("Select one of the indexes to add:");
        for (int i = 0; i< indexes.size(); i++){

            ArrayList<StudyGroup> studyGroups = new ArrayList<>();
            for (StudyGroup studyGroup : indexes.get(i).getStudyGroup()) {
                studyGroups.add(studyGroup);
            }

            Collections.sort(studyGroups, Comparator
                    .comparing(StudyGroup::getDayOfWeek)
                    .thenComparing(StudyGroup::getStartTime));

            System.out.println("");

            System.out.println((i+1)+ ": "+indexes.get(i).getIndexNum());
            System.out.println("Lesson Type  Day       Start Time - End Time   Venue    Week Type");
            System.out.println("-----------------------------------------------------------------");
            for (StudyGroup sg : studyGroups){
                System.out.printf("%-13s",sg.getLessonType());
                System.out.printf("%-16s",week.get(sg.getDayOfWeek()));

                time_1 = String.valueOf(sg.getStartTime());
                time_2 = String.valueOf(sg.getEndTime());

                while (time_1.length() < 4) {
                    time_1 = "0" + time_1;
                }

                while (time_2.length() < 4) {
                    time_2 = "0" + time_2;
                }
                System.out.printf("%s - %-11s", time_1, time_2);

                System.out.printf("%-9s", sg.getVenue());
                System.out.printf("%s\n", sg.getWeekType());
            }

        }
        int i = vc.validateInt(1,indexes.size());

        return indexes.get(i-1);
    }


    /**
     * Method for student to register for a course
     * @param student student object of this student login
     * @param registeredCourses array list of registered course of this student
     * @param course course object of course to be registered
     * @param index index object of index to be registered
     */
    public void addCourse(Student student, ArrayList<RegisteredCourse> registeredCourses, Course course, Index index) {
        FileController fc=new FileController();

        /**
         * Check for total AUS of this student if course is registered for
         */
        if (student.getNumberOfAUs() + course.getTotalAUs() > Student.MAX_AUs) {
                System.out.printf("You currently have %d AUs.\nYou are not allowed to exceed the AUs limit of %d.\n", student.getNumberOfAUs(), Student.MAX_AUs );
                return;
            }

        /**
         * Checks if the student is already registered for this course or on wait list of this course
         */
        if(student.getRegCourses().isEmpty()==false) {
                    for(RegisteredCourse registeredCourse : registeredCourses) {
                        Index idx =fc.getIndexByID(registeredCourse.getRegIndex());
                        //System.out.println(idx.getIndexNum());
                        if(idx.getCourse().equals(course.getCourseCode())) {
                            System.out.printf("You have already been registered for index %d\n", idx.getIndexNum());
                            System.out.printf("The current status is %s\n", registeredCourse.getRegStatus());
                            return;
                        }
                    }
                }


        /**
         * Check if index registered for clashes with current registered courses of this student
         */
        ArrayList<StudyGroup> s = getStudyGroups(student);
            if (s!=null) {
                if (checkClash(index, s)) {
                    return;
                }
            }


            String status;
        /**
         * Check the vacancies of this index
         */
        if (index.getVacancies() <= 0) {

                System.out.println("There are no more vacancies for this index.");
                System.out.printf("There are currently %d people in the wait list.", index.getWaitList().size());
                System.out.println("Would you like to join the wait list? (Y/N)");

                Scanner sc = new Scanner(System.in);
                char answer = sc.next().charAt(0);

                if (answer == 'N') {
                    return;
                }

            /**
             * Adds studnet into waitlist if there is no vacancies
             */
            status = "Waitlist";
                index.addToWaitList(student);
            } else {
                status = "Registered";
                student.setNumberOfAUs(student.getNumberOfAUs()+course.getTotalAUs());
                index.setVacancies(index.getVacancies()-1);


            }
            RegisteredCourse rc = new RegisteredCourse(index.getIndexNum(), status , student.getUserName());
            student.addRegCourses(rc);
            index.addToRegList(rc);

            System.out.printf("You have been successfully added for index %d\n", index.getIndexNum());
            System.out.printf("The current status for the course is %s.\n", status);
            return;
        }

    /**
     * Method for student to drop a registered course
     * @param student student object of this student login
     * @param course course object of course to drop
     * @param index index object of index to drop
     * @param rc registered course object of registered course to drop
     */
        public void dropCourse(Student student, Course course, Index index, RegisteredCourse rc) {
                char ch;
                do {
                    System.out.println("Are you sure you want to drop course? (Y/N)");
                    ch=sc.nextLine().charAt(0);
                    if(ch=='N'||ch=='n')
                        return;
                    if(ch!='Y'&&ch!='N'&&ch!='y'&&ch!='n')
                        System.out.println("Invalid input. Try again");
                }while(ch!='Y'&&ch!='y');
                student.setNumberOfAUs(student.getNumberOfAUs()-course.getTotalAUs());
                System.out.println("The course has been dropped for you.");
                index.setVacancies(index.getVacancies()+1);
                updateVacancies(course.getCourseCode(), 1);

                allocateVacancies(course, index);
                student.removeRegCourses(rc);

                ArrayList<RegisteredCourse> indRegList=index.getRegisteredCourses();

                if(!indRegList.isEmpty()) {
                    for(int i = 0;i < indRegList.size(); i++) {
                        if(indRegList.get(i).getStudent().equals(student.getUserName())) {
                            index.removeFromRegList(indRegList.get(i));
                        }
                    }
                }


            }
//        }

    /**
     * Print courses and its information student is registered in (timetable)
     * @param student student object of student login
     */
    public void printRegisteredCourses(Student student) {
        FileController fc=new FileController();
        ArrayList<RegisteredCourse> regCourses = student.getRegCourses();
        ArrayList<RegisteredCourse> waitlistedCourses = new ArrayList<>();
        ArrayList<RegisteredCourse> registeredCourses = new ArrayList<>();

        if (!regCourses.isEmpty()) {

            for (RegisteredCourse regCourse: regCourses) {
                if (regCourse.getRegStatus().equals("Waitlist")) {
                    waitlistedCourses.add(regCourse);
                } else {
                    registeredCourses.add(regCourse);
                }
            }
            if(registeredCourses.size()==0){
                System.out.println("There are no courses registered for this  Student");
            } else {
                System.out.println("List of Registered Courses");
                System.out.println("CourseID    CourseName \t\t\t\t\t\t\t\t      Index   ");
                System.out.println("==========================================================================");
                for (int i = 0; i < registeredCourses.size(); i++) {
                    Index idx = fc.getIndexByID(registeredCourses.get(i).getRegIndex());
                    Course course = fc.getCourseByCode(idx.getCourse());
                    System.out.printf("%-11s %-45s %s\n", course.getCourseCode(), course.getName(), registeredCourses.get(i).getRegIndex());

                }


                getTimetable(getStudyGroups(student));
            }

            if(waitlistedCourses.size()==0){
                System.out.println("There are no courses waitlisted for this student");
            }
            else{

            System.out.println("List of Waitlisted Courses");
                System.out.println("CourseID    CourseName \t\t\t\t\t\t\t      Index   ");
                System.out.println("==========================================================================");
            for (int i = 0; i < waitlistedCourses.size(); i++) {
                Index idx = fc.getIndexByID(waitlistedCourses.get(i).getRegIndex());
                Course course = fc.getCourseByCode(idx.getCourse());

                System.out.printf("%-11s %-45s %s\n", course.getCourseCode(), course.getName(), waitlistedCourses.get(i).getRegIndex());
            }}
        } else {
            System.out.println("Sorry! No Course Registered found for this Student");
        }
        return;
    }

    /**
     * Check vacancies available in index of choice
     */
    public void checkVacanciesAvailable() {
        DisplayDataController dd = new DisplayDataController();
        School school = dd.schSelection();
        if (school != null) {
            Course course = dd.courseSelection(school);
            if(course != null) {
                Index index = dd.indexSelection(course);
                if(index != null){
                    System.out.println("Number Of Vacancies available " + index.getVacancies() + " outOf " + index.getTotalSlots());
                }
                else{
                    System.out.println("There is no Index with IndexNum "+index+" available");
                }
            }
        }
    }

    /**
     * Change index number of course student is registered in
     * @param student student object of current student
     */
    public void changeIndexNumber(Student student){
        FileController fc=new FileController();
        DisplayDataController dd = new DisplayDataController();
        RegisteredCourse rc = dd.selectRegisteredCourses(student);
        School sch = fc.getSchoolByName(student.getSchool());
        Index currentIn = fc.getIndexByID(rc.getRegIndex());
        Course indexCourse = fc.getCourseByCode(currentIn.getCourse());
        Index newIn = indexInput(indexCourse);

        /**
         * Check if the new index the student wants belongs to the same course as the old index
         */
        if (!currentIn.getCourse().equals(newIn.getCourse())) {
            System.out.println("Current index and new index does not belong to the same course.");
            return;
        }

        /**
         * Check if student is registered in new index
         */
        if(currentIn.getIndexNum()== newIn.getIndexNum())
        {
            System.out.println("You are already registered for this index");
            return;
        }

        /**
         * Get the number of vacancies in the new index
         */
        if(newIn.getVacancies()==0){
            System.out.println("The system did not change the index number since the new index has no vacancies");
            return;
        }
        dropCourse(student, indexCourse, currentIn, rc);
        addCourse(student, student.getRegCourses(), indexCourse, newIn);
    }

    /**
     * Swap index number with another student
     * @param student1 current student login
     */
    public void swapIndexnumber(Student student1) {
        FileController fc=new FileController();
        DisplayDataController dd = new DisplayDataController();

        /**
         * Get the index student wants to swap
         * Get information of other student and other index
         * Checks if other student is registered in the other index and if either of them will have timetable clashes after swap
         */
        RegisteredCourse rc = dd.selectRegisteredCourses(student1);
        if (rc != null) {
            Student student2;
            do {
                System.out.println("Enter the username of the student you want to swap index with: ");
                String unStudent2 = sc.next();
                student2 = fc.getStudentByUsername(unStudent2);
                if(student2==null)
                {
                    System.out.println("Student with this username does not exist");
                }
            } while (student2 == null);
            Index index1 = fc.getIndexByID(rc.getRegIndex());
            Course c = fc.getCourseByCode(index1.getCourse());
            ArrayList<RegisteredCourse> regList2 = student2.getRegCourses();
            if (regList2.size() == 0){System.out.println("The student does not have the course you are trying to swap for");}
            else {
                for (int i = 0; i < regList2.size(); i++) {
                    RegisteredCourse regC = regList2.get(i);
                    Index index2 = fc.getIndexByID(regC.getRegIndex());
                    Course secondC = fc.getCourseByCode(index2.getCourse());
                    if (secondC.getCourseCode().equals(c.getCourseCode())) {
                        if(regC.getRegStatus().equals("Waitlist")){System.out.println("The student is not registered in this index."); return;}
                        System.out.println("Enter the index you want to swap with: ");
                        int Ix2 = sc.nextInt();
                        if (index2.getIndexNum() == Ix2) {

                            ArrayList<StudyGroup> s1 = getStudyGroups(student1);
                            ArrayList<StudyGroup> s2 = getStudyGroups(student2);
                            ArrayList<StudyGroup> s1_2 = s1;

                            for (int k = 0; k < s1_2.size(); k++) {
                                if (s1_2.get(k).getIndex() == index1.getIndexNum()) {
                                    s1_2.remove(k);
                                    k--;
                                }
                            }

                            if (checkClash(index2, s1_2)) {
                                return;
                            }
                            System.out.printf("You have no clashes with %d.\n", index2.getIndexNum());

                            ArrayList<StudyGroup> s2_2 = s2;
                            for (int j = 0; j < s2_2.size(); j++) {
                                if (s2_2.get(j).getIndex() == index2.getIndexNum()) {
                                    s2_2.remove(j);
                                    j--;
                                }
                            }

                            if (checkClash(index1, s2_2)) {
                                return;
                            }
                            System.out.printf("%s has no clash with %d.\n", student2.getName(), index1.getIndexNum());


                            ArrayList<RegisteredCourse> regCourses1 = student1.getRegCourses();
                            for (int l = 0; l < regCourses1.size(); l++) {
                                Index idx1 = fc.getIndexByID(regCourses1.get(l).getRegIndex());
                                Course theCourse1 = fc.getCourseByCode(idx1.getCourse());
                                if (theCourse1.getCourseCode().equals(c.getCourseCode())) {
                                    RegisteredCourse oldRegisteredCourse1 = regCourses1.get(l);
                                    student1.removeRegCourses(oldRegisteredCourse1);
                                    for (int n = 0; n < idx1.getRegisteredCourses().size(); n++) {
                                        if (idx1.getRegisteredCourses().get(n).getStudent().equals(student1.getUserName())) {
                                            idx1.removeFromRegList(idx1.getRegisteredCourses().get(n));
                                        }
                                    }

                                    String status1 = "Registered";
                                    RegisteredCourse newRegisteredCourse1 = new RegisteredCourse(index2.getIndexNum(), status1, student1.getUserName());
                                    student1.addRegCourses(newRegisteredCourse1);
                                    index2.getRegisteredCourses().add(newRegisteredCourse1);
                                }
                            }

                            ArrayList<RegisteredCourse> regCourses2 = student2.getRegCourses();
                            for (int m = 0; m < regCourses2.size(); m++) {
                                Index idx2 = fc.getIndexByID(regCourses2.get(m).getRegIndex());
                                Course theCourse2 = fc.getCourseByCode(idx2.getCourse());
                                if (theCourse2.equals(secondC)) {
                                    RegisteredCourse oldRegisteredCourse2 = regCourses2.get(m);
                                    student2.removeRegCourses(oldRegisteredCourse2);

                                    for (int o = 0; o < idx2.getRegisteredCourses().size(); o++) {
                                        if (idx2.getRegisteredCourses().get(o).getStudent().equals(student2.getUserName())) {
                                            idx2.removeFromRegList(idx2.getRegisteredCourses().get(o));

                                        }
                                    }

                                    String status2 = "Registered";
                                    RegisteredCourse newRegisteredCourse2 = new RegisteredCourse(index1.getIndexNum(), status2, student2.getUserName());
                                    student2.addRegCourses(newRegisteredCourse2);
                                    index1.getRegisteredCourses().add(newRegisteredCourse2);

                                }
                            }

                            System.out.printf("Swap of index %d with  index %d completed.\n", index1.getIndexNum(), Ix2);
                            return;
                        }
                    }
                }
                System.out.printf("The student does not have inputted index you are trying to swap");
            }}
        return;


    }

    /**
     * Check the access period of STARS system for the school this student belongs in
     * @param school school to check access period of
     */
    public void checkAccessPeriod(School school) {
        Date regStartDate=school.getRegistrationStartPeriod();
        Date regEndDate=school.getRegistrationEndPeriod();
        System.out.println("The registration access period is::  "+regStartDate+" to "+regEndDate);

    }

    /**
     * Displays the list of Reviews that a Student has written and allows the student to select from the list.
     * Used when selecting reviews to edit or delete
     * @param student The current student user who is logged in
     * @return The Review selected by the student user
     */
    private Review reviewSelection(Student student) {
        int reviewSelection;
        boolean flag = true;

        ArrayList<Review> reviews = student.getMyReviews();
        if (reviews == null || reviews.size() == 0){
            System.out.println("You have not written any reviews.");
            flag = false;
        }
        while(flag) {
            // Get user's selection of courses
            System.out.println("Select the Review you would like to make changes to:");
            for(int i = 0; i < reviews.size(); i++) {
                System.out.println(i+1 + ": ");
                System.out.println("Course: "+ reviews.get(i).getCourse());
                System.out.println("Review: "+ reviews.get(i).getReview());
                System.out.println("Recommended: "+ reviews.get(i).isRecommended());
                System.out.println("");
            }
            reviewSelection = sc.nextInt();

            if (reviewSelection > 0 && reviewSelection < reviews.size() + 1) {
                Review review = reviews.get(reviewSelection - 1);
                flag = false;
                return review;
            }
            else {
                System.out.println("Invalid selection.");
            }
        }
        return null;
    }

    /**
     * Displays and allows student to select from a list of Courses that the student is eligible to write Reviews for.
     * These include courses that the student has previously taken (pastCourses) and has not yet written a review for.
     * Triggered when student needs to select a course to add review for
     * @param student The current student user who is logged in
     * @return The Course selected by the student user to add review for
     */
    private Course reviewCourseSelection(Student student) {
        int courseSelection;
        boolean flag = true;
//
        ArrayList<Course> courses = (ArrayList<Course>)student.getPastCourses().clone();
        ArrayList<Review> reviews = student.getMyReviews();
        if (reviews != null) {
            for (Review r : reviews) {
                for (int a = 0; a < courses.size(); a++) {

                    if (courses.get(a).getCourseCode().equals(r.getCourse())) {
                        courses.remove(a);
                    }
                }
            }
        }
        if (courses == null || courses.size() == 0){
            System.out.println("You have no past Courses");
            flag = false;
        }
        while(flag) {
            System.out.println("Select the course you would like to add review for:");
            for(int i = 0; i < courses.size(); i++) {
                System.out.println(i+1 + ". " + courses.get(i).getCourseCode());
            }
            courseSelection = sc.nextInt();

            if (courseSelection > 0 && courseSelection < courses.size() + 1) {
                Course course = courses.get(courseSelection - 1);
                flag = false;
                return course;
            }
            else {
                System.out.println("Invalid selection.");
            }
        }
        return null;
    }

    /**
     * Gets a valid recommendation from the student for a course.
     * @return return true if input is 1, false otherwise
     */
    private boolean getRec() {
        ValidateIntController vc = new ValidateIntController();

        System.out.println("Would you recommend the course? (1/0)");
        int i = vc.validateInt(0,1);
        if (i==0){return false;}
        else {return true;}

    }

    /**
     * Gets a review input from the Student and filters the input by replacing the swearwords with *
     * filter() function is used from the Grawlox package to filter the input
     * @return the filtered review
     */
    private String filterReview(){
        sc.nextLine();
        String input="";

        input+=sc.nextLine();

        try{
            Grawlox grawlox = Grawlox.createFromDefault();
            return grawlox.filter(input);
        } catch (IOException e) {
            System.out.println("Invalid input");
            return null;
        }

    }

    /**
     * Allows student to add a review to one of their past courses and displays the added review once successful
     * @param student The student currently logged in
     */
    public void addReview(Student student){

        Course c = reviewCourseSelection(student);
        if (c != null){
            System.out.println("Enter your review below:");
            String r = filterReview();
            boolean rec = getRec();

            Review rev = new Review(r, rec, student.getUserName(), c.getCourseCode());
            System.out.println("The following Review has been succesfully added.");
            System.out.println("Review: "+ r);
            System.out.println("Recommended: "+ rec);
        }

    }

    /**
     * Allows student to edit one of the reviews they have previously written
     * Student can either edit the review or the recommendation
     * @param student The student currently logged in
     */
    public void editReview(Student student) {
        Review rev = reviewSelection(student);
        if (rev != null){
            System.out.println("1. Change review.");
            System.out.println("2. Ammend recommended status.");
            boolean flag = true;
            do {
                System.out.println("Input a choice (1/2)");
                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    switch(choice) {
                        case 1:
                            flag = false;
                            System.out.println("Enter your edited review below:");
                            String r = filterReview();
                            rev.setReview(r);
                            System.out.println("Your Review has been successfully edited with the following details:");
                            System.out.println("Review: "+rev.getReview());
                            System.out.println("Recommended: "+rev.isRecommended());
                            break;
                        case 2:
                            flag = false;
                            boolean rec = getRec();
                            rev.setRecommended(rec);
                            System.out.println("Your Review has been successfully edited with the following details:");
                            System.out.println("Review: "+rev.getReview());
                            System.out.println("Recommended: "+rev.isRecommended());
                            break;

                        default:
                            System.out.println("Invalid selection.");
                            break;
                    }
                }else {
                    String input = sc.next();
                    System.out.println("Invalid selection.");
                }
            } while(flag);
        }

    }

    /**
     * Allows student to delete one of the reviews they have previously written
     * @param student The student currently logged in
     */
    public void deleteReview(Student student) {
        FileController fc=new FileController();
        Review rev = reviewSelection(student);
        if (rev != null){
            student.deleteReview(rev);
            String cCode = rev.getCourse();
            Course c = fc.getCourseByCode(cCode);
            c.deleteReview(rev);
        }


    }
    /**
     * Allows user to sort courses by number of reviews or percentage recommended.
     * Course information is displayed based on the selction and the student can select one of the courses to view reviews for
     * Then, display the reviews.
     */
    public void displayReviews() {
        int select; int courseSelection;
        boolean flag = true;
        DisplayDataController dd = new DisplayDataController();
        Scanner sc = new Scanner(System.in);
        School sch = dd.schSelection();
        ArrayList<Course> schCourses = sch.getCourses();
        do {
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
        } while(flag);
        do {
            System.out.println("Select the course to view Reviews for:");
            System.out.println("");
            for(int i = 0; i < schCourses.size(); i++) {
                System.out.println("------------------------"+(i+1)+"------------------------");
                Course c = schCourses.get(i);
                System.out.println(c.getCourseCode() + "       "+ c.getName());
                System.out.println("Course Type: "+c.getCourseType());
                System.out.println("Number of AUs: "+ c.getTotalAUs());
                System.out.println("Vacancy: "+ c.getVacancy());
                System.out.println("Number of Reviews: "+c.getTotalReviews());
                System.out.printf("Percentage Recommended: %.2f%%\n",c.getPercRecommended()*100);
            }
            courseSelection = sc.nextInt();

            if (courseSelection > 0 && courseSelection < schCourses.size() + 1) {
                Course course = schCourses.get(courseSelection - 1);
                ArrayList<Review> reviewList = course.getReviews();
                if (reviewList == null || reviewList.size() == 0){
                    System.out.println("There are no reviews for the course.");
                } else {
                    for(int i = 0; i< reviewList.size();i++) {
                        System.out.println("Review "+(i+1)+": ");
                        System.out.println(reviewList.get(i).getReview());
                        System.out.println("Recommended: "+ reviewList.get(i).isRecommended());
                        System.out.println("");
                    }
                }

            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);
    }

    /**
     * Prints the timetable for a student.
     * @param studyGroups ArrayList of studyGroups of the student
     */
    public void getTimetable(ArrayList<StudyGroup> studyGroups) {
        FileController fc=new FileController();


        String time_1, time_2;

        Collections.sort(studyGroups, Comparator
                .comparing(StudyGroup::getDayOfWeek)
                .thenComparing(StudyGroup::getStartTime));

        Map<Integer, String> week = new HashMap<>();
        week.put(1, "Monday");
        week.put(2, "Tuesday");
        week.put(3, "Wednesday");
        week.put(4, "Thursday");
        week.put(5, "Friday");
        week.put(6, "Saturday");
        week.put(7, "Sunday");
        System.out.println("\n\n==============Timetable============\n");
        System.out.println("Course  Index  Lesson Type  Start Time - End Time  Venue    Week Type");

        for (Map.Entry<Integer, String> day : week.entrySet()) {
            System.out.println("------------------------"+day.getValue()+"------------------------");


            for(StudyGroup studyGroup : studyGroups) {
                if (studyGroup.getDayOfWeek() == day.getKey()) {

                    time_1 = String.valueOf(studyGroup.getStartTime());
                    time_2 = String.valueOf(studyGroup.getEndTime());

                    while (time_1.length() < 4) {
                        time_1 = "0" + time_1;
                    }

                    while (time_2.length() < 4) {
                        time_2 = "0" + time_2;
                    }
                    Index idx = fc.getIndexByID(studyGroup.getIndex());
                    Course theCourse = fc.getCourseByCode(idx.getCourse());

                    System.out.printf("%-8s",theCourse.getCourseCode());
                    System.out.printf("%-7d",idx.getIndexNum());
                    System.out.printf("%-20s", studyGroup.getLessonType());
                    System.out.printf("%-4s - %-8s ", time_1, time_2);
                    System.out.printf("%-10s", studyGroup.getVenue());
                    System.out.printf("%-11s\n", studyGroup.getWeekType());
                }
            }
            System.out.println("");
        }

    }

    /**
     * Check for clashes in timetable: Compares whether there exists an overlapping range and prints all clashes.
     * @param index The new index to check clash for
     * @param studyGroups ArrayList of registered studyGroups of the student
     * @return true if there is clash, false otherwise
     */
    private boolean checkClash(Index index, ArrayList<StudyGroup> studyGroups) {
        FileController fc=new FileController();

        String time_1, time_2, time_3, time_4;
        boolean clash = false;
        ArrayList<String> day = new ArrayList<>();
        day.add(0, "test");
        day.add(1,"Monday");
        day.add(2,"Tuesday");
        day.add(3,"Wednesday");
        day.add(4,"Thursday");
        day.add(5,"Friday");
        day.add(6,"Saturday");
        day.add(7,"Sunday");


        for (StudyGroup studyGroup : index.getStudyGroup()) {
            for (StudyGroup studyGroup2 : studyGroups) {
                if (studyGroup.getDayOfWeek() == studyGroup2.getDayOfWeek()) {
                    if (studyGroup.getStartTime() < studyGroup2.getEndTime()
                            && studyGroup.getEndTime() > studyGroup2.getStartTime()) {
                        if (studyGroup.getWeekType().equals("ALL") || studyGroup2.getWeekType().equals("ALL") || studyGroup.getWeekType().equals(studyGroup2.getWeekType())) {
                            time_1 = String.valueOf(studyGroup.getStartTime());
                            time_2 = String.valueOf(studyGroup.getEndTime());
                            time_3 = String.valueOf(studyGroup2.getStartTime());
                            time_4 = String.valueOf(studyGroup2.getEndTime());

                            while (time_1.length() < 4) {
                                time_1 = "0" + time_1;
                            }

                            while (time_2.length() < 4) {
                                time_2 = "0" + time_2;
                            }

                            while (time_3.length() < 4) {
                                time_3 = "0" + time_3;
                            }

                            while (time_4.length() < 4) {
                                time_4 = "0" + time_4;
                            }

                            Index idx= fc.getIndexByID(studyGroup2.getIndex());
                            Course theCourse = fc.getCourseByCode(idx.getCourse());
                            System.out.printf("The index you are trying to add %d (%s) clashes with index %d (%s)\n",
                                    index.getIndexNum(), index.getCourse(), idx.getIndexNum(), theCourse.getCourseCode());

                            System.out.println(day.get(studyGroup.getDayOfWeek()));

                            System.out.printf("Index %d time: %s - %s\n", index.getIndexNum(), time_1, time_2);
                            System.out.printf("Index %d time: %s - %s\n\n", idx.getIndexNum(),time_3, time_4);

                            clash = true;
                        }
                    }
                }
            }
        }
        if (clash == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Change vacancies in a course by n
     * @param cCode course code of course to update
     * @param n addition or subtraction of vacancies by this amount
     */
    private void updateVacancies(String cCode, int n){
        FileController fc=new FileController();
        Course theCourse = fc.getCourseByCode(cCode);
        theCourse.editVacancies(n);
    }

    /**
     * Get array list of study groups of this student
     * @param student the current student login
     * @return array list of study group
     */
    public ArrayList<StudyGroup> getStudyGroups(Student student) {

        ArrayList<StudyGroup> studyGroups = new ArrayList<>();
        FileController fc = new FileController();
        if (student.getRegCourses().isEmpty()==false ) {
            for (RegisteredCourse regCourse : student.getRegCourses() ) {
                Index idx = fc.getIndexByID(regCourse.getRegIndex());

                if (regCourse.getRegStatus() != "Waitlist") {
                    for (StudyGroup studyGroup : idx.getStudyGroup()) {
                        studyGroups.add(studyGroup);
                    }
                }
            }
            return studyGroups;
        }
        else {
            return null;
        }

    }

    /**
     * Allocate vacancies after a student drop a course
     * @param course course dropped by student
     * @param index index of course dropped by student
     */
    public void allocateVacancies(Course course, Index index) {

        Student student;
        Queue<Student> waitList = index.getWaitList();
        int vacancy;
        FileController fc = new FileController();

        for (vacancy = index.getVacancies(); vacancy > 0; vacancy--) {
            if (waitList != null){
                Student ss = waitList.poll();
                if (ss != null) {
                    student = fc.getStudentByUsername(ss.getUserName());

                    for(RegisteredCourse registeredCourse : student.getRegCourses()) {
                        Index idx = fc.getIndexByID(registeredCourse.getRegIndex());
                        if(idx.getCourse().equals(course.getCourseCode())) {

                            if (student.getNumberOfAUs() + course.getTotalAUs() > Student.MAX_AUs) {
                                student.removeRegCourses(registeredCourse);
                                ArrayList<RegisteredCourse> indRegList=index.getRegisteredCourses();
                                if(!indRegList.isEmpty()) {
                                    for(int i = 0;i < indRegList.size(); i++) {
                                        if(indRegList.get(i).getStudent().equals(student.getUserName())) {
                                            index.removeFromRegList(indRegList.get(i));
                                        }
                                    }
                                }
                                vacancy++;
                            } else {
                                student.removeRegCourses(registeredCourse);
                                ArrayList<RegisteredCourse> indRegList=index.getRegisteredCourses();
                                if(!indRegList.isEmpty()) {
                                    for(int i = 0;i < indRegList.size(); i++) {
                                        if(indRegList.get(i).getStudent().equals(student.getUserName())) {
                                            index.removeFromRegList(indRegList.get(i));
                                        }
                                    }
                                }
                                RegisteredCourse regCourse=new RegisteredCourse(index.getIndexNum(),"Registered",student.getUserName());
                                student.setNumberOfAUs(student.getNumberOfAUs() + course.getTotalAUs());
                                student.addRegCourses(regCourse);
                                index.addToRegList(regCourse);
//
                            }
                        }
                        //set new timetable schedule
                    }
                    NotificationController nc = new NotificationController();
                    HashMap<String, String> choice = student.getNotificationType();
                    String keyMethod = choice.keySet().stream().findFirst().get();
                    String recipient = choice.get(keyMethod);
                    String name = student.getName();
                    int indexNum = index.getIndexNum();
                    String courseCode = fc.getCourseByCode(index.getCourse()).getCourseCode();

                    nc.notify(recipient, name, indexNum, courseCode, keyMethod);
                } else {
                    index.setVacancies(vacancy);
                    break;
                }
            }
        }
        index.setVacancies(vacancy);
    }

}
