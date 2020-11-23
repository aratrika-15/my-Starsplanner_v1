package stars;
import java.util.*;
import de.zabuza.grawlox.*;
import jdk.jfr.Registered;

import java.io.*;
public class StudentModeController {
    Scanner sc=new Scanner(System.in);
    FileController fc=new FileController();
    DisplayDataController dd = new DisplayDataController();
    ValidateIntController vc = new ValidateIntController();

    public void inputAddCourse(Student student){

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

    private Index indexInput(Course course) {
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

//                if (indexes != null || indexes.size() != 0){
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
            System.out.println("-------------------------------------------------------");
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


    public void addCourse(Student student, ArrayList<RegisteredCourse> registeredCourses, Course course, Index index) {

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

               /* if(index.getRegisteredCourses() != null){
                    index.getRegisteredCourses().add(rc);
                }
                else{
                    ArrayList<RegisteredCourse> regList = new ArrayList<RegisteredCourse>();
                    regList.add(rc);
                    index.setRegisteredCourses(regList);
                }*/

            }
        RegisteredCourse rc = new RegisteredCourse(index.getIndexNum(), status , student.getUserName());
        student.addRegCourses(rc);
        index.addToRegList(rc);

            //Set Registered
            System.out.printf("You have been successfully added for index %d\n", index.getIndexNum());
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
                char ch;
                do {
                    System.out.println("Are you sure you want to drop course? (Y/N)");
                    ch=sc.nextLine().charAt(0);
                    if(ch=='N')
                        return;
                    if(ch!='Y'&&ch!='N')
                        System.out.println("Invalid input. Try again");
                }while(ch!='Y');
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


                getTimetable(student.getStudyGroups());
            }

            if(waitlistedCourses.size()==0){
                System.out.println("There are no courses waitlisted for this student");
            }
            else{

            System.out.println("List of Waitlisted Courses");
                System.out.println("CourseID    CourseName \t\t\t\t\t\t\t\t      Index   ");
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

        RegisteredCourse rc = dd.selectRegisteredCourses(student);
        School sch = fc.getSchoolByName(student.getSchool());
        Index currentIn = fc.getIndexByID(rc.getRegIndex());
        Course indexCourse = fc.getCourseByCode(currentIn.getCourse());
        Index newIn = indexInput(indexCourse);

        if (!currentIn.getCourse().equals(newIn.getCourse())) {
            System.out.println("Current index and new index does not belong to the same course.");
            return;
        }
        if(currentIn.getIndexNum()== newIn.getIndexNum())
        {
            System.out.println("You are already registered for this index");
            return;
        }
        if(newIn.getVacancies()==0){
            System.out.println("The system did not change the index number since the new index has no vacancies");
            return;
        }
        dropCourse(student, indexCourse, currentIn, rc);
        addCourse(student, student.getRegCourses(), indexCourse, newIn);
    }
    public void swapIndexnumber(Student student1, Student student2, int Ix2) {
        //TODO
        FileController FileController = new FileController();
        //Index Index2 = FileController.getIndexByID(Ix2);

        RegisteredCourse rc = dd.selectRegisteredCourses(student1);
        Index index1 = fc.getIndexByID(rc.getRegIndex());
        Course c = fc.getCourseByCode(index1.getCourse());
        ArrayList<RegisteredCourse> regList2 = student2.getRegCourses();

        for(int i = 0; i<regList2.size(); i++) {
            RegisteredCourse regC = regList2.get(i);
            Index index2 = fc.getIndexByID(regC.getRegIndex());
            Course secondC = fc.getCourseByCode(index2.getCourse());
            if(secondC.getCourseCode().equals(c.getCourseCode())) {
                if (index2.getIndexNum() == Ix2) {

                    ArrayList<StudyGroup> s1 = student1.getStudyGroups();
                    ArrayList<StudyGroup> s2 = student2.getStudyGroups();

                    //Check if clash with current timetable for both students
                    //ArrayList<StudyGroup> s1 = student1.getStudyGroups();
                    ArrayList<StudyGroup> s1_2 = s1;

                    for(int k=0; k<s1_2.size(); k++) {
                        if (s1_2.get(k).getIndex() == index1.getIndexNum()) {
                            s1_2.remove(k);
                            k--;
                        }
                    }

                    if (checkClash(index2, s1_2)) {
                        return;
                    }
                    System.out.printf("You have no clashes with %d.\n",index2.getIndexNum());

                    ArrayList<StudyGroup> s2_2 = s2;
                    for(int j=0; j<s2_2.size(); j++) {
                        if (s2_2.get(j).getIndex() == index2.getIndexNum()) {
                            s2_2.remove(j);
                            j--;
                        }
                    }

                    if (checkClash(index1, s2_2)) {
                        return;
                    }
                    System.out.printf("%s has no clash with %d.\n",student2.getName(),index1.getIndexNum());


                    ArrayList<RegisteredCourse> regCourses1 = student1.getRegCourses();
                    for (int l = 0; l<regCourses1.size(); l++) {
                        Index idx1 = fc.getIndexByID(regCourses1.get(l).getRegIndex());
                        Course theCourse1 = fc.getCourseByCode(idx1.getCourse());
                        if (theCourse1.getCourseCode().equals(c.getCourseCode())) {
                            RegisteredCourse oldRegisteredCourse1 = regCourses1.get(l);
                            student1.removeRegCourses(oldRegisteredCourse1);
                            //idx1.getRegisteredCourses().remove(oldRegisteredCourse1);

                            for(int n = 0;n < idx1.getRegisteredCourses().size(); n++) {
                                if(idx1.getRegisteredCourses().get(n).getStudent().equals(student1.getUserName())) {
                                    idx1.removeFromRegList(idx1.getRegisteredCourses().get(n));
                                    //System.out.println("Student has been removed from index.");

                                }
                            }

                            String status1 = "Registered";
                            RegisteredCourse newRegisteredCourse1 = new RegisteredCourse(index2.getIndexNum(), status1, student1.getUserName());
                            student1.addRegCourses(newRegisteredCourse1);
                            index2.getRegisteredCourses().add(newRegisteredCourse1);
                        }
                    }

                    ArrayList<RegisteredCourse> regCourses2 = student2.getRegCourses();
                    for (int m = 0; m<regCourses2.size(); m++) {
                        Index idx2 = fc.getIndexByID(regCourses2.get(m).getRegIndex());
                        Course theCourse2 = fc.getCourseByCode(idx2.getCourse());
                        if (theCourse2.equals(secondC)) {
                            RegisteredCourse oldRegisteredCourse2 = regCourses2.get(m);
                            student2.removeRegCourses(oldRegisteredCourse2);
                            //idx2.getRegisteredCourses().remove(oldRegisteredCourse2);

                            for(int o = 0;o < idx2.getRegisteredCourses().size(); o++) {
                                if(idx2.getRegisteredCourses().get(o).getStudent().equals(student2.getUserName())) {
                                    idx2.removeFromRegList(idx2.getRegisteredCourses().get(o));
                                    //System.out.println("Student has been removed from index.");

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
        System.out.printf("The student does not have the entered index you are trying to swap");
        //Index index2 = dd.indexSelection(c);
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
