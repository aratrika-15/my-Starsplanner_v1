package stars;
import java.util.*;
import de.zabuza.grawlox.*;
import java.io.*;
public class StudentModeController {
    Scanner sc=new Scanner(System.in);
    FileController fc=new FileController();


        public void addCourse(Student student, ArrayList<RegisteredCourse> registeredCourses, Course course, Index index) {
            //TODO

            //Check for total AUS after addition of the course
            if (student.getNumberOfAUs() + course.getTotalAUs() > 21) {
                System.out.printf("You currently have %d AUs.\nYou are not allowed to exceed the AUs limit of 21.\n", student.getNumberOfAUs());
                return;
            }

            //Check if applied before (either accepted/waitlisted)

                if(student.getRegCourses() != null) {
                    for(RegisteredCourse registeredCourse : student.getRegCourses()) {
                        Index idx =fc.getIndexByID(registeredCourse.getRegIndex());
                        if(idx.getCourse().equals(course)) {
                            System.out.printf("You have already been registered for index %d", idx.getIndexNum());
                            System.out.printf("The current status is %s\n", registeredCourse.getRegStatus());
                            return;
                        }
                    }
                }


            //Check if clash with current timetable
            ArrayList<StudyGroup> s = student.getStudyGroups();
            if (!s.isEmpty()) {
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
            }

            //Create new Registered Course class
            RegisteredCourse registeredCourse = new RegisteredCourse(index.getIndexNum(), status, student.getUserName());

            //Add course to student's list of registered courses
            student.addRegCourses(registeredCourse);

            //Set Registered
            System.out.printf("You have been registered for index %d\n", index.getIndexNum());
            System.out.printf("The current status for the course is %s\n", status);
            return;
        }
        public void dropCourse(Student student, Course course, Index index) {
            //TODO
            boolean isRegistered=false;
            for(int i=0;i<student.getRegCourses().size();i++) {
                Index idx = fc.getIndexByID(student.getRegCourses().get(i).getRegIndex());
                if (idx.equals(index))
                    isRegistered = true;
            }
            if (!isRegistered) {
                System.out.printf("You are not currently registered for index %d, course %s", index.getIndexNum(), fc.getCourseByCode(index.getCourse()).getCourseCode());
                return;
            } else {
                student.setNumberOfAUs(student.getNumberOfAUs()-course.getTotalAUs());
                //setTimetableSchedule();

                index.setVacancies(index.getVacancies()+1);
                updateVacancies(course.getCourseCode(), 1);

                index.allocateVacancies(course, index);
                ArrayList<RegisteredCourse> stuRegList = student.getRegCourses();

                if(!stuRegList.isEmpty()) {
                    for(int i = 0;i < stuRegList.size(); i++) {
                        if(stuRegList.get(i).getRegIndex() == index.getIndexNum()) {
                            student.getRegCourses().remove(i);
                        }
                    }
                }

                System.out.println("The course has been dropped for you.");
            }
        }

    public void printRegisteredCourses(Student student) {
        //TODO
        System.out.println("   CourseID   CourseName   Index   ");
        System.out.println("===================================");
        ArrayList<RegisteredCourse> regCourses = student.getRegCourses();
        if (!regCourses.isEmpty()) {
            for (int i = 0; i < regCourses.size(); i++) {
                Index idx = fc.getIndexByID(regCourses.get(i).getRegIndex());
                Course course = fc.getCourseByCode(idx.getCourse());
                System.out.println(
                        "   " + course.getCourseCode() + "       " + course.getName()+ "         " + regCourses.get(i).getRegIndex());
            }
        } else {
            System.out.println("Sorry!,No Course Registered found for this Student");
        }
        return;
    }




    /*public void checkVacanciesAvailable(int index) {
        //TODO
        Index indObj = fc.getIndexByID(index);
                if(indObj!=null){
                    System.out.println("Number Of Vacancies available" + indObj.getVacancies() + "outOf" + indObj.getTotalSlots());
                }
                else{
                    System.out.println("There is no Index with IndexNum "+index+" available");
                }



    }*/
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
    public void changeIndexNumber(Student student, int current, int newIndex){
        FileController FileController = new FileController();
        Index currentIn = FileController.getIndexByID(current);
        Index newIn = FileController.getIndexByID(newIndex);

        if (!currentIn.getCourse().equals(newIn.getCourse())) {
            System.out.println("Current index and new index does not belong to the same course.");
        }

        ArrayList<RegisteredCourse> registeredCourses = student.getRegCourses();
        Course indexCourse = fc.getCourseByCode(currentIn.getCourse());
        dropCourse(student, indexCourse, currentIn);
        addCourse(student, registeredCourses, indexCourse, newIn);
    }
    public void swapIndexnumber(Student student1, Student student2, int Ix1, int Ix2) {
        //TODO
        FileController FileController = new FileController();
        Index Index1 = FileController.getIndexByID(Ix1);
        Index Index2 = FileController.getIndexByID(Ix2);

        if (!Index2.getCourse().equals(Index2.getCourse())) {
            System.out.println("Both indexes does not belong to the same course.");
        }

        Course Course1 = fc.getCourseByCode(Index1.getCourse());
        if (!student1.getRegCourses().contains(Course1)) {
            System.out.printf("One of the student is not currently registered for index %d, course %s", Index1.getIndexNum(), Index1.getCourse());
            return;
        }

        Course Course2 = fc.getCourseByCode(Index2.getCourse());
        if (!student2.getRegCourses().contains(Course2)) {
            System.out.printf("One of the student is not currently registered for index %d, course %s", Index1.getIndexNum(), Index1.getCourse());
            return;
        }

        //Check if clash with current timetable for both students
        ArrayList<StudyGroup> s1 = student1.getStudyGroups();
        if (checkClash(Index2, s1)) {
            return;
        }
        ArrayList<StudyGroup> s2 = student2.getStudyGroups();
        if (checkClash(Index1, s2)) {
            return;
        }

        ArrayList<RegisteredCourse> regCourses1 = student1.getRegCourses();
        for (int i = 0; i<regCourses1.size(); i++) {
            Index idx1 = fc.getIndexByID(regCourses1.get(i).getRegIndex());
            Course theCourse1 = fc.getCourseByCode(idx1.getCourse());
            if (theCourse1.equals(Course1)) {
                RegisteredCourse oldRegisteredCourse1 = regCourses1.get(i);
                student1.removeRegCourses(oldRegisteredCourse1);
                String status1 = "Registered";
                RegisteredCourse newRegisteredCourse1 = new RegisteredCourse(Index2.getIndexNum(), status1, student1.getUserName());
                student1.addRegCourses(newRegisteredCourse1);
            }
        }

        ArrayList<RegisteredCourse> regCourses2 = student2.getRegCourses();
        for (int i = 0; i<regCourses2.size(); i++) {
            Index idx2 = fc.getIndexByID(regCourses1.get(i).getRegIndex());
            Course theCourse2 = fc.getCourseByCode(idx2.getCourse());
            if (theCourse2.equals(Course2)) {
                RegisteredCourse oldRegisteredCourse2 = regCourses2.get(i);
                student2.removeRegCourses(oldRegisteredCourse2);
                String status2 = "Registered";
                RegisteredCourse newRegisteredCourse2 = new RegisteredCourse(Index1.getIndexNum(), status2, student2.getUserName());
                student2.addRegCourses(newRegisteredCourse2);
            }
        }

        return;
    }
    public void checkAccessPeriod(School school) {
        //TODO
        Date regStartDate=school.getRegistrationStartPeriod();
        Date regEndDate=school.getRegistrationEndPeriod();
        System.out.println("The registration access period is::  "+regStartDate+" to "+regEndDate);

    }
    private Review reviewSelection(Student student) {
        int reviewSelection;
        boolean flag = true;

        ArrayList<Review> reviews = student.getMyReviews();

        do {
            // Get user's selection of courses
            System.out.println("Select the Review you would like to make changes to:");
            for(int i = 0; i < reviews.size(); i++) {
                System.out.println(i+1 + ". " + reviews.get(i));
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
        } while(flag);
        return null;
    }

    private Course reviewCourseSelection(Student student) {
        int courseSelection;
        boolean flag = true;

        ArrayList<Course> courses = student.getPastCourses();

        do {
            // Get user's selection of courses
            System.out.println("Select the course you would like to add/edit/remove review for:");
            for(int i = 0; i < courses.size(); i++) {
                System.out.println(i+1 + ". " + courses.get(i));
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
        } while(flag);
        return null;
    }
    private boolean getRec() {
        do {
            System.out.println("Would you recommend the course? (1/0)");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                switch(choice) {
                    case 0:
                        return false;
                    case 1:
                        return true;

                    default:
                        System.out.println("Invalid selection.");
                        break;
                }
            }else {
                String input = sc.next();
                System.out.println("Invalid selection.");
            }
        } while(true);
    }
private String filterReview(String input){
        try{
            Grawlox grawlox = Grawlox.createFromDefault();
            return grawlox.filter(input);
        } catch (IOException e) {
            System.out.println("Invalid inout");
            return null;
        }

 }
    public void addReview(Student student){
        //print all the courses(previously taken courses) and get course choice to add review to
        Course c = reviewCourseSelection(student);
        System.out.println("Enter your review below:");
        String r = sc.next();
         r = filterReview(r);
        boolean rec = getRec();

        Review rev = new Review(r, rec, student.getUserName(), c.getCourseCode());
    }
    public void editReview(Student student) {
        Course c = reviewCourseSelection(student);
        Review rev = reviewSelection(student);
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
                        String r = sc.next();
                        r = filterReview(r);
                        rev.setReview(r);
                        break;
                    case 2:
                        flag = false;
                        boolean rec = getRec();
                        rev.setRecommended(rec);
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
    public void deleteReview(Student student) {
        Course c = reviewCourseSelection(student);
        Review rev = reviewSelection(student);
        student.deleteReview(rev);
        c.deleteReview(rev);

    }
    /**
     * Allows user to sort courses by number of reviews or percentage recommended and make selection.
     * The, display the reviews.
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
            System.out.println("Select the course:");
            for(int i = 0; i < schCourses.size(); i++) {
                System.out.println(i+1 + ". " + schCourses.get(i).getName());
            }
            courseSelection = sc.nextInt();

            if (courseSelection > 0 && courseSelection < schCourses.size() + 1) {
                Course course = schCourses.get(courseSelection - 1);
                ArrayList<Review> reviewList = course.getReviews();
                for(int i = 0; i< reviewList.size();i++) {
                    System.out.println(i+1 + "Review: " + reviewList.get(i).getReview());
                }
            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);
    }
    /*
       Prints the timetable for a student.
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

        for (Map.Entry<Integer, String> day : week.entrySet()) {

            System.out.printf("%d\n",day.getValue());

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

                    System.out.printf("%s - %s: ", time_1, time_2);
                    Index idx = fc.getIndexByID(studyGroup.getIndex());
                    Course theCourse = fc.getCourseByCode(idx.getCourse());
                    System.out.printf("%s (%d)", theCourse.getCourseCode(), idx.getIndexNum());

                    //prints week type if not weekly (i.e. biweekly)
                    if (studyGroup.getWeekType() != "Weekly") {
                        System.out.printf("\t %s", studyGroup.getWeekType());
                    }
                    System.out.println("");
                }
            }

        }

    }
    /*
    Check for clashes in timetable: Compares whether there exists an overlapping range and prints all clashes. Returns true if there is clash
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
                    if (studyGroup.getStartTime() <= studyGroup2.getEndTime()
                            && studyGroup.getEndTime() >= studyGroup2.getStartTime()) {

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
                                index.getIndexNum(), theCourse.getName(), idx.getIndexNum(), theCourse.getName());

                        System.out.println(day.get(studyGroup.getDayOfWeek()));

                        System.out.printf("Index %d time: %s - %s\n", index.getIndexNum(), time_1, time_2);
                        System.out.printf("Index %d time: %s - %s\n\n", idx.getIndexNum(),time_3, time_4);

                        clash = true;
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
