package stars;
import java.util.*;
import de.zabuza.grawlox.*;
import jdk.jfr.Registered;

import java.io.*;
public class StudentModeController {
    Scanner sc=new Scanner(System.in);
    FileController fc=new FileController();
    ValidateIntController vc = new ValidateIntController();


    public void addCourse(Student student, ArrayList<RegisteredCourse> registeredCourses, Course course, Index index) {
            //TODO

            //Check for total AUS after addition of the course
            if (student.getNumberOfAUs() + course.getTotalAUs() > 21) {
                System.out.printf("You currently have %d AUs.\nYou are not allowed to exceed the AUs limit of 21.\n", student.getNumberOfAUs());
                return;
            }

            //Check if applied before (either accepted/waitlisted)
        //System.out.println(student.getRegCourses());
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


            //Check if clash with current timetable
            ArrayList<StudyGroup> s = student.getStudyGroups();
            if (s!=null) {
               // for(int i=0;i<s.size();i++)
                //{
                   // System.out.println(s.get(i).getIndex());
                //}
                if (checkClash(index, s)) {
                    return;
                }
            }


            String status;
            //Get Vacancy
            if (index.getVacancies() <= 0) {

                System.out.println("There are no more vacancies for this index.");
                System.out.printf("There are currently %d people in the wait list.", index.getWaitList().size());
                System.out.println("Would you like to join the wait list? (Y/N)");

                Scanner sc = new Scanner(System.in);
                char answer = sc.next().charAt(0);

                if (answer == 'N') {
                    return;
                }

                //Add student into wait list
                status = "Waitlist";
                index.addToWaitList(student);
            } else {
                status = "Registered";
                //Set new number of AUs
                student.setNumberOfAUs(student.getNumberOfAUs()+course.getTotalAUs());
                index.setVacancies(index.getVacancies()-1);
                RegisteredCourse rc = new RegisteredCourse(index.getIndexNum(), "Registered", student.getUserName());
                student.addRegCourses(rc);
                index.addToRegList(rc);
               /* if(index.getRegisteredCourses() != null){
                    index.getRegisteredCourses().add(rc);
                }
                else{
                    ArrayList<RegisteredCourse> regList = new ArrayList<RegisteredCourse>();
                    regList.add(rc);
                    index.setRegisteredCourses(regList);
                }*/

            }

            //Set Registered
            System.out.printf("You have been registered for index %d\n", index.getIndexNum());
            System.out.printf("The current status for the course is %s\n", status);
            return;
        }
        public void dropCourse(Student student, Course course, Index index, RegisteredCourse rc) {
            //TODO
            boolean isRegistered=false;
            for(int i=0;i<student.getRegCourses().size();i++) {
                Index idx = fc.getIndexByID(student.getRegCourses().get(i).getRegIndex());
                if (idx.equals(index))
                    isRegistered = true;
            }
            if (!isRegistered) {
                System.out.printf("You are not currently registered for index %d, course %s\n", index.getIndexNum(), fc.getCourseByCode(index.getCourse()).getCourseCode());
                return;
            } else {
                student.setNumberOfAUs(student.getNumberOfAUs()-course.getTotalAUs());
                //setTimetableSchedule();
                System.out.println("The course has been dropped for you.");
                index.setVacancies(index.getVacancies()+1);
                updateVacancies(course.getCourseCode(), 1);

                index.allocateVacancies(course, index);
                student.removeRegCourses(rc);

                ArrayList<RegisteredCourse> indRegList=index.getRegisteredCourses();

                if(!indRegList.isEmpty()) {
                    for(int i = 0;i < indRegList.size(); i++) {
                        if(indRegList.get(i).getStudent().equals(student.getUserName())) {
                            index.removeFromRegList(indRegList.get(i));
                            //System.out.println("Student has been removed from index.");

                        }
                    }
                }


            }
        }

    public void printRegisteredCourses(Student student) {
        ArrayList<RegisteredCourse> regCourses = student.getRegCourses();
        ArrayList<RegisteredCourse> waitlistedCourses = new ArrayList<>();
        ArrayList<RegisteredCourse> registeredCourses = new ArrayList<>();

        if (!regCourses.isEmpty()) {

            for (RegisteredCourse regCourse: regCourses) {
                if (regCourse.getRegStatus().equals("Waitlisted")) {
                    waitlistedCourses.add(regCourse);
                } else {
                    registeredCourses.add(regCourse);
                }
            }

            System.out.println("List of Registered Courses");
            System.out.println("   CourseID   CourseName   Index   ");
            System.out.println("===================================");
            for (int i = 0; i < registeredCourses.size(); i++) {
                Index idx = fc.getIndexByID(registeredCourses.get(i).getRegIndex());
                Course course = fc.getCourseByCode(idx.getCourse());

                System.out.println(
                        "   " + course.getCourseCode() + "       " + course.getName()+ "         " + registeredCourses.get(i).getRegIndex());
            }


            getTimetable(student.getStudyGroups());

            System.out.println("List of Waitlisted Courses");
            System.out.println("   CourseID   CourseName   Index   ");
            System.out.println("===================================");
            for (int i = 0; i < waitlistedCourses.size(); i++) {
                Index idx = fc.getIndexByID(waitlistedCourses.get(i).getRegIndex());
                Course course = fc.getCourseByCode(idx.getCourse());

                System.out.println(
                        "   " + course.getCourseCode() + "       " + course.getName() + "         " + waitlistedCourses.get(i).getRegIndex());
            }
        } else {
            System.out.println("Sorry! No Course Registered found for this Student");
        }
        return;
    }
    public void checkVacanciesAvailable() {
        //TODO
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
    public void changeIndexNumber(Student student){
        FileController fc = new FileController();
        DisplayDataController dd = new DisplayDataController();
        RegisteredCourse rc = dd.selectRegisteredCourses(student);
        School sch = fc.getSchoolByName(student.getSchool());
        Index currentIn = fc.getIndexByID(rc.getRegIndex());
        Course indexCourse = fc.getCourseByCode(currentIn.getCourse());
        Index newIn = dd.indexSelection(indexCourse);

        if (!currentIn.getCourse().equals(newIn.getCourse())) {
            System.out.println("Current index and new index does not belong to the same course.");
            return;
        }


        dropCourse(student, indexCourse, currentIn, rc);
        addCourse(student, student.getRegCourses(), indexCourse, newIn);
    }
    public void swapIndexnumber(Student student1, Student student2, int Ix1, int Ix2) {
        //TODO
        FileController FileController = new FileController();
        Index Index1 = FileController.getIndexByID(Ix1);
        Index Index2 = FileController.getIndexByID(Ix2);

        ArrayList<StudyGroup> s1 = student1.getStudyGroups();
        for(int i =0; i <s1.size(); i++) {
            System.out.println(s1.get(i).getIndex());
        }
        ArrayList<StudyGroup> s2 = student2.getStudyGroups();
        for(int i =0; i <s2.size(); i++) {
            System.out.println(s2.get(i).getIndex());
        }

        if (!Index2.getCourse().equals(Index2.getCourse())) {
            System.out.println("Both indexes does not belong to the same course.");
        }
        System.out.println("Both indexes belong to the same course.");

        Course Course1 = fc.getCourseByCode(Index1.getCourse());
        for(int i = 0; i < student1.getRegCourses().size(); i++) {
            Index ind1 = fc.getIndexByID(student1.getRegCourses().get(i).getRegIndex());
            Course cou1 = fc.getCourseByCode(ind1.getCourse());
            if(!cou1.getCourseCode().equals(Course1.getCourseCode())){
                System.out.printf("Student %s is not currently registered for index %d, course %s", student1.getName(), Index1.getIndexNum(), Index1.getCourse());
                return;
            }
        }

        Course Course2 = fc.getCourseByCode(Index2.getCourse());
        for(int i = 0; i < student2.getRegCourses().size(); i++) {
            Index ind2 = fc.getIndexByID(student1.getRegCourses().get(i).getRegIndex());
            Course cou2 = fc.getCourseByCode(ind2.getCourse());
            if(!cou2.getCourseCode().equals(Course1.getCourseCode())){
                System.out.printf("Student %s is not currently registered for index %d, course %s", student2.getName(), Index2.getIndexNum(), Index2.getCourse());
                return;
            }
        }

        //Check if clash with current timetable for both students
        //ArrayList<StudyGroup> s1 = student1.getStudyGroups();
        ArrayList<StudyGroup> s1_2 = s1;
        for(int i=0; i<s1_2.size(); i++) {
            System.out.println(s1_2.get(i).getIndex());
        }
        System.out.println("");
        for(int i=0; i<s1_2.size(); i++) {
            if (s1_2.get(i).getIndex() == Index1.getIndexNum()) {
                s1_2.remove(i);
                i--;
                System.out.println(i);
            }
        }
        System.out.println(s1_2.size());

        if (checkClash(Index2, s1_2)) {
            return;
        }
        System.out.println("Student1 has no clash with index2.");

        ArrayList<StudyGroup> s2_2 = s2;
        for(int i=0; i<s2_2.size(); i++) {
            if (s2_2.get(i).getIndex() == Index2.getIndexNum()) {
                s2_2.remove(i);
                i--;
                System.out.println(i);
            }
        }
        System.out.println(s2_2.size());

        if (checkClash(Index1, s2_2)) {
            return;
        }
        System.out.println("Student2 has no clash with index1.");

        ArrayList<RegisteredCourse> regCourses1 = student1.getRegCourses();
        for (int i = 0; i<regCourses1.size(); i++) {
            Index idx1 = fc.getIndexByID(regCourses1.get(i).getRegIndex());
            Course theCourse1 = fc.getCourseByCode(idx1.getCourse());
            if (theCourse1.getCourseCode().equals(Course1.getCourseCode())) {
                RegisteredCourse oldRegisteredCourse1 = regCourses1.get(i);
                student1.removeRegCourses(oldRegisteredCourse1);
                idx1.getRegisteredCourses().remove(oldRegisteredCourse1);
                String status1 = "Registered";
                RegisteredCourse newRegisteredCourse1 = new RegisteredCourse(Index2.getIndexNum(), status1, student1.getUserName());
                student1.addRegCourses(newRegisteredCourse1);
                Index2.getRegisteredCourses().add(newRegisteredCourse1);
            }
        }

        ArrayList<RegisteredCourse> regCourses2 = student2.getRegCourses();
        for (int i = 0; i<regCourses2.size(); i++) {
            Index idx2 = fc.getIndexByID(regCourses1.get(i).getRegIndex());
            Course theCourse2 = fc.getCourseByCode(idx2.getCourse());
            if (theCourse2.equals(Course2)) {
                RegisteredCourse oldRegisteredCourse2 = regCourses2.get(i);
                student2.removeRegCourses(oldRegisteredCourse2);
                idx2.getRegisteredCourses().remove(oldRegisteredCourse2);
                String status2 = "Registered";
                RegisteredCourse newRegisteredCourse2 = new RegisteredCourse(Index1.getIndexNum(), status2, student2.getUserName());
                student2.addRegCourses(newRegisteredCourse2);
                Index1.getRegisteredCourses().add(newRegisteredCourse2);
            }
        }

        return;
    }
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

//        System.out.println(courses.toString());
        if (courses == null || courses.size() == 0){
            System.out.println("You have no past Courses");
            flag = false;
        }
        while(flag) {
            // Get user's selection of courses
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
        } ;
        return null;
    }

    /**
     * Gets a valid recommendation from the student for a course.
     * @return return true if input is 1, false otherwise
     */
    private boolean getRec() {
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
        //print all the courses(previously taken courses) and get course choice to add review to
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
            // Get user's selection of courses
            System.out.println("Select the course to view Reviews for:");
            System.out.println("");
            for(int i = 0; i < schCourses.size(); i++) {
                System.out.println("------------------------"+i+"------------------------");
                Course c = schCourses.get(i);
                System.out.println(c.getCourseCode() + "       "+ c.getName());
                System.out.println("Course Type: "+c.getCourseType());
                System.out.println("Number of AUs: "+ c.getTotalAUs());
                System.out.println("Vacancy: "+ c.getVacancy());
                System.out.println("Number of Reviews: "+c.getTotalReviews());
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

//            System.out.printf("%s\n",day.getValue());
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
    private void updateVacancies(String cCode, int n){
        Course theCourse = fc.getCourseByCode(cCode);
        theCourse.editVacancies(n);
    }

}
