package stars;
import java.util.*;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;

/**
 * Controller class to facilitate the logic for the admin functions
 */
public class AdminModeController implements DisplayErrorMsgUI{
    /**
     * To get user input
     */
    Scanner sc = new Scanner(System.in);


    /**
     * Change the period a student can access STARS system
     */
    public void editStudentAccessPeriod() {
        FileController fc = new FileController();
        DisplayDataController dd= new DisplayDataController();

        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        School updateSchool;
        Date convertedStartDate=null,convertedEndDate=null;
        format.setLenient(false);
        /**
         * Select school to update period for
         */
        updateSchool = dd.schSelection();

        /**
         * Get new start date and end date from admin
         * Checks if date entered is valid
         */
        do{
            try{
                System.out.println("Enter the new registration start period in dd/mm/yyyy/hh/mm");
                String startDate=sc.next();

                convertedStartDate = format.parse(startDate);
                System.out.println(convertedStartDate);
                System.out.println(format.format(convertedStartDate));
            } catch (ParseException e) {
                System.out.println("Invalid registration period format");
            }
        } while (convertedStartDate == null);

        do{
            try{
                System.out.println("Enter the new registration end period in dd/mm/yyyy/hh/mm");
                String endDate=sc.next();

                convertedEndDate = format.parse(endDate);
                System.out.println(convertedEndDate);
                System.out.println(format.format(convertedEndDate));
            } catch (ParseException e) {
                System.out.println("Invalid registration period format");
            }
        } while (convertedEndDate == null);

        if(convertedStartDate.compareTo(convertedEndDate)<0) {
            updateSchool.setRegistrationStartPeriod(convertedStartDate);
            updateSchool.setRegistrationEndPeriod(convertedEndDate);
            fc.saveSchoolList();
            System.out.println("Access Period has been updated.");
        }
        else
        {
            System.out.println("Registration end period must be after registration start period");
        }

    }


    /**
     * Method for admin to add new student into STARS system
     */
    public void addStudent() {
        FileController fc = new FileController();
        ValidateIntController vc = new ValidateIntController();
        DisplayDataController dd= new DisplayDataController();

        /**
         * Enter information needed to create a new student
         */
        sc.nextLine();
        Student student = new Student();
        student.setTypeOfUser("Student");
        String username, email, matricNo, gender, nationality, name, schoolName;
        String choice = "sendEmail";
        String recipient;
        int year;
        School school;
        String pass1, pass2;
        boolean passMatch = false;
        boolean validEmail = false;
        char ch;
        Student presentStudent;
        do {
            System.out.print("Please enter the student's username: ");
            username = sc.nextLine().trim();
            presentStudent = fc.getStudentByUsername(username);
            if (presentStudent != null) {
                System.out.println("Error. Student with this username already exists.");
            }
        } while (presentStudent != null);

        do {
            System.out.print("Please enter the student's email: ");
            email = sc.nextLine().trim();
            User user = student;
            validEmail = user.validateEmail(email);
            if (validEmail == false) {
                System.out.println("Error. You entered an invalid email");
            }
        } while (validEmail != true);
        recipient = email;


        Console console = System.console();
        do {
            if (console != null) {
                System.out.print("Please enter the student's password: ");
                char[] passString = console.readPassword();
                pass1 = new String(passString);
                System.out.print("Please confirm the password: ");
                passString = console.readPassword();
                pass2 = new String(passString);

                if (pass1.equals(pass2))
                    passMatch = true;
                else
                    System.out.println("Error. The 2 passwords are not same.");
            } else {
                System.out.print("Please enter the student's password: ");
                pass1 = sc.nextLine().trim();
                System.out.print("Please confirm the password: ");
                pass2 = sc.nextLine().trim();
                if (pass1.equals(pass2))
                    passMatch = true;
                else
                    System.out.println("Error. The 2 passwords are not same.");
            }
        } while (passMatch != true);


        System.out.println("Enter the student's name: ");
        name = sc.nextLine().trim();
        System.out.println("Enter the student's matriculation number: ");
        matricNo = sc.nextLine().trim();
        System.out.println("Enter the student's nationality: ");
        nationality = sc.nextLine().trim();
        System.out.println("Enter the student's year: ");
        year = vc.validateInt(1,6);
        do {
            System.out.println("Enter the student's gender: ");
            gender = sc.nextLine().trim();
            if (!gender.equals("Female") && !gender.equals("Male")) {
                System.out.println("Gender must be Male or Female");
            }
        } while (!gender.equals("Female") && !gender.equals("Male"));

        school = dd.schSelection();
        do {
            System.out.println("Enter the student's preferred notification type (Email E/Telegram T/Whatsapp W): ");
            ch = sc.nextLine().charAt(0);
            switch (ch) {
                case 'E':
                    recipient = email;
                    choice = "SendEmail";
                    break;
                case 'T':
                    System.out.println("Enter the student's telegram number: ");
                    recipient = sc.nextLine().trim();
                    choice = "SendTele";
                    break;
                case 'W':
                    System.out.println("Enter the student's whatsapp number: ");
                    recipient = sc.nextLine().trim();
                    choice = "SendWhatsapp";
                    break;
                default:
                    System.out.println("Incorrect input. Try again.");
            }
        } while (ch != 'T' && ch != 'W' && ch != 'E');


        /**
         * performs password hashing on actual password before storing
         */
        pass1 = student.buildPasswordHash(pass1);

        student = school.addStudent(name, matricNo, gender, nationality, year, school.getName(), choice, recipient, email, pass1, "Student", username);
        fc.getStudentList().add(student);
        fc.saveStudentList();
        fc.saveSchoolList();
        System.out.println(student.getSchool());

        /**
         * Prints student list for admin to check if student is added
         */
        dd.printStudentList();
    }


    /**
     * Validate the format of the course code entered
     * Checks if course code entered is of 6 characters, 2 letters followed by 4 numbers
     * @param cCode Course code entered by user
     * @return course code that is checked to be of correct format
     */
    private String validateCourseCodeFormat(String cCode) {
        if (cCode.length() == 6) {
            try {
                int d = Integer.parseInt(cCode.substring(2));
                if (d > 0) {
                    if ((Character.isLetter(cCode.charAt(0)) == true)) {
                        if ((Character.isLetter(cCode.charAt(1)) == true)) {
                            String formatted = cCode.substring(0, 2).toUpperCase() + cCode.substring(2);
                            return formatted;
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                return null;
            }
        }
        return null;
    }

    /**
     * Add new study group for an index
     * @param i index to add study group to
     * @param lessonType lesson type of index
     */
    private void addStudyGroup(Index i, LessonType lessonType) {
        ValidateIntController vc = new ValidateIntController();

        String venue, weekType;
        int dayOfWeek, startTime;
        int endTime = -1;
        /**
         * Enter details of new study group
         */
        System.out.println("Enter the following lesson details for index " + i.getIndexNum() + ":");
        System.out.println(lessonType + " venue:");
        sc.nextLine();
        venue = sc.nextLine();
        System.out.println(lessonType + " start time:");
        startTime = vc.validateInt(800,2000);
        System.out.println(lessonType + " end time:");
        boolean flag = true;
        do {
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                if (choice>=800 && choice <=2000 && choice> startTime) {
                    flag = false;
                    endTime = choice;
                } else {
                    System.out.println("Invalid selection. Enter a valid input.");
                }
            }else {
                String input = sc.next();
                System.out.println("Invalid selection.Enter a valid input.");
            }
        } while(flag);
        System.out.println("Day of the week the " + lessonType + "  will take place: (1.Mon, 2.Tues, 3.Wed, 4.Thurs, 5.Fri, 6.Sat)");//how to add for multiple days?
        dayOfWeek = vc.validateInt(1,6);
        System.out.println("Week type of " + lessonType + "(ODD, EVEN, ALL)");
        weekType = sc.next();
        String wTypes[] = {"ODD","EVEN","ALL"};
        while (!Arrays.stream(wTypes).anyMatch(weekType::equals)) {
            System.out.println("Invalid Input!");
            System.out.println("Week Type(ODD, EVEN, ALL) :");
            weekType = sc.next();
        }
        WeekType weekdayType = WeekType.valueOf(weekType);

        i.addStudyGroup(venue, startTime, endTime, dayOfWeek, weekdayType.name(), lessonType);
        System.out.println("Study group successfully added");
    }

    /**
     * Add new index to a course
     * @param c course to add new index to
     */
    private void addIndex(Course c) {
        ValidateIntController vc = new ValidateIntController();

        /**
         * Enter details of new index
         */
        System.out.println("Enter the following details of the Index you would like to add:");
        System.out.println("Index Number:");
        int indexNum = vc.validateInt(0, 99999);
        System.out.println("Group Number:");
        String groupNum = sc.next();
        System.out.println("Vacancies:");
        int vacancy = vc.validateInt(1,500);
        Index index = c.setIndex(indexNum, groupNum, vacancy);
        if (c.getCourseType().equals(CourseType.LEC_TUT_LAB)) {
            addStudyGroup(index, LessonType.LAB);
            addStudyGroup(index, LessonType.TUTORIAL);
        }
        if (c.getCourseType().equals(CourseType.LEC_TUT)) {
            addStudyGroup(index, LessonType.TUTORIAL);
        }
        for (int i = 0; i < c.getnLectures(); i++) {
            addStudyGroup(index, LessonType.LECTURE);
        }


    }

    /**
     * Add new course to school
     */
    //	@SuppressWarnings("unused")
    public void addCourse() {
        ValidateIntController vc = new ValidateIntController();
        DisplayDataController dd= new DisplayDataController();
        FileController fc = new FileController();

        /**
         * Select an existing school to add course to
         */
        School s = dd.schSelection();
        while (s == null){
            System.out.println("School does not exist");
            s = dd.schSelection();
        }

        /**
         * Enter details of new course
         */
        if (s != null) {
            System.out.println("Enter the following details of the course you would like to add:");
            System.out.println("Course Code:");
            String n = sc.next();
            while (validateCourseCodeFormat(n) == null) {
                System.out.println(n + " is not a valid input. Enter again:");
                n = sc.next();
            }
            String courseCode = validateCourseCodeFormat(n);

            Course check = fc.getCourseByCode(courseCode);
            if (check != null){
                System.out.println("The course you are trying to add already exists");
                return;
            }

            System.out.println("name:");
            sc.nextLine();
            String name = sc.nextLine();

            System.out.println("Course Type (LEC, LEC_TUT, LEC_TUT_LAB) :");
            String cType = sc.next();
            String cTypes[] = {"LEC", "LEC_TUT", "LEC_TUT_LAB"};
            while (!Arrays.stream(cTypes).anyMatch(cType::equals)) {
                System.out.println("Invalid Input!");
                System.out.println("Course Type (LEC, LEC_TUT, LEC_TUT_LAB) :");
                cType = sc.next();
            }
            CourseType courseType = CourseType.valueOf(cType);

            System.out.println("Number of lectures per week:");
            int nLectures = vc.validateInt(1,3);





            System.out.println("total AUs:");
            int totalAUs = vc.validateInt(1,4);

            Course c = s.addCourse(name, courseCode, courseType, totalAUs, nLectures);


            System.out.println("Enter the number of indexes for Course " + c.getCourseCode());
            int nIndexes = vc.validateInt(1,30);
            for (int i = 0; i < nIndexes; i++) {
                addIndex(c);
            }

        }
        /**
         * Print course list to check if course is added
         */
        dd.printCourseList();
    }

    /**
     * checks whether a course has studnets
     * @param c The course to check for
     * @return true if the course has students and false otherwise
     */
    public boolean checkCourseHasStudents(Course c)
    {
        ArrayList<Index> indices=c.getIndex();
        if(indices==null)
            return false;
        for(int i=0;i<indices.size();i++)
        {
            Index index=indices.get(i);
            ArrayList< RegisteredCourse> regCourse=index.getRegisteredCourses();
            if(regCourse!=null&&regCourse.isEmpty()==false)
                return true;

        }
            return false;
    }

    /**
     * Update information on a course or index of a course
     */
    public void updateCourse() {
        FileController fc = new FileController();
        ValidateIntController vc = new ValidateIntController();
        DisplayDataController dd= new DisplayDataController();

        /**
         * Select school for which the course or index to update belongs to
         */
        School s = dd.schSelection();
        while (s == null){
            System.out.println("School does not exist");
            s = dd.schSelection();
        }

        /**
         * Select the course or course that the index to update belongs to
         */
        Course c = dd.courseSelection(s);
        if (c != null) {
            System.out.println("1. Edit course information");
            System.out.println("2. Edit index information");
            System.out.println("----------choose one of the options above");
            int choice = vc.validateInt(1,2);
            switch (choice) {
                /**
                 * Menu for when course is chosen to be updated
                 */
                case 1:
                    System.out.println("1. Edit course Name");
                    System.out.println("2. Edit course Type");
                    System.out.println("3. Edit total AUs");
                    System.out.println("4. Add index");
                    System.out.println("5. Edit course Code");
                    System.out.println("6. Edit school");
                    System.out.println("----------choose one of the options above");
                    choice = vc.validateInt(1,4);
                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new course name:");
                            String course_name = sc.next();
                            c.setName(course_name);
                            System.out.println("Course Name successfully changed");
                            break;
                        case 2:
                            if(checkCourseHasStudents(c)==false) {
                                CourseType past_type = c.getCourseType();
                                System.out.println("Enter the new course Type (LEC, LEC_TUT, LEC_TUT_LAB):");
                                String cType = sc.next();
                                String cTypes[] = {"LEC", "LEC_TUT", "LEC_TUT_LAB"};
                                while (!Arrays.stream(cTypes).anyMatch(cType::equals)) {
                                    System.out.println("Invalid Input!");
                                    System.out.println("Course Type (LEC, LEC_TUT, LEC_TUT_LAB) :");
                                    cType = sc.next();
                                }
                                CourseType course_type = CourseType.valueOf(cType);

                                c.setCourseType(course_type);
                                System.out.println("Course type successfully changed");///need to loop through all the indexes to add/remove the study groups respctively??

                                if (c.getCourseType().equals(CourseType.LEC)) {
                                    for (Index i : c.getIndex()) {
                                        i.deleteStudyGroup(LessonType.TUTORIAL);
                                        i.deleteStudyGroup(LessonType.LAB);
                                    }
                                }
                                if (c.getCourseType().equals(CourseType.LEC_TUT)) {
                                    for (Index i : c.getIndex()) {
                                        i.deleteStudyGroup(LessonType.LAB);
                                    }
                                }
                                if (past_type.equals(CourseType.LEC)) {
                                    if (c.getCourseType().equals(CourseType.LEC_TUT)) {
                                        for (Index i : c.getIndex()) {
                                            addStudyGroup(i, LessonType.TUTORIAL);
                                        }
                                    } else if (c.getCourseType().equals(CourseType.LEC_TUT_LAB)) {
                                        for (Index i : c.getIndex()) {
                                            addStudyGroup(i, LessonType.TUTORIAL);
                                            addStudyGroup(i, LessonType.LAB);
                                        }
                                    }
                                }
                                if (past_type.equals(CourseType.LEC_TUT)) {
                                    if (c.getCourseType().equals(CourseType.LEC_TUT_LAB)) {
                                        for (Index i : c.getIndex()) {
                                            addStudyGroup(i, LessonType.LAB);
                                        }
                                    }
                                }
                            }
                            else
                            {
                                System.out.println("Cannot update course Type after students have been registered");
                            }
                            break;

                        case 3:
                            if(checkCourseHasStudents(c)==false) {
                                System.out.println("Enter new total number of AUs:");
                                int aus = vc.validateInt(1, 4);
                                c.setTotalAUs(aus);
                                System.out.println("Number of AUs successfully changed");
                            }
                            else
                            {
                                System.out.println("Cannot update total AUs of the course after students have been registered");
                            }
                            break;
                        case 4:
                            addIndex(c);
                            break;
                        case 5:
                            System.out.println("Enter the new course code");
                            String n = sc.next();
                            while (validateCourseCodeFormat(n) == null) {
                                System.out.println(n + " is not a valid input. Enter again:");
                                n = sc.next();
                            }
                            String courseCode = validateCourseCodeFormat(n);
                            c.setCourseCode(courseCode);
                            System.out.println("Course code successfully changed");
                            break;
                        case 6:
                            School newSchool;
                            System.out.println("Choose the new school you want");
                            do {
                               newSchool=dd.schSelection();
                               if(newSchool!=null)
                               {
                                   if(newSchool.getName()==c.getSchool())
                                   {
                                       System.out.println("The old school and the new school are the same");
                                   }
                               }
                            }while(newSchool.getName()==c.getSchool());
                            c.setSchool(newSchool.getName());
                            School updatedSchool=fc.getSchoolByName(newSchool.getName());
                            updatedSchool.getCourses().add(c);
                            School oldSchool=fc.getSchoolByName(c.getSchool());
                            oldSchool.getCourses().remove(c);
                            break;
                        default:
                            System.out.println("invalid input");
                    }
                    break;
                /**
                 * Menu for when index is chosen to be updated
                 */
                case 2:
                    Index i = dd.indexSelection(c);
                    if (i != null) {
                        System.out.println("1. Edit group number");
                        System.out.println("2. Add/remove vacancies");
                        System.out.println("3. delete Index");
                        System.out.println("4. Edit index number");
                        //TO ADD: edit lecture/tutorial/lab details/
                        System.out.println("----------choose one of the options above");
                        choice = sc.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("Enter the new group number:");
                                String grp_number = sc.next();
                                i.setGroupNum(grp_number);
                                System.out.println("Group Number successfully changed");
                                break;
                            case 2:
                                System.out.println("Enter the new vacancies");
                                int vacancies = vc.validateInt(1,500);
                                int new_vacancies = i.getVacancies() + vacancies;
                                if (new_vacancies >= 0) {
                                    i.setVacancies(i.getVacancies() + vacancies);
                                    ///need to get add students from waitlist
                                } else {
                                    System.out.println("Cannot remove required vacancies. Students have already filled up the places.");
                                }
                                System.out.println("Vacancies successfully changed");
                                break;
                            case 3:
                                if(checkCourseHasStudents(c)==false)
                                {c.deleteIndex(i);}
                                else
                                    System.out.println("Cannot delete index since students are registered");
                                break;
                            case 4:
                                System.out.println("Enter the new index number");
                                int indexNum = vc.validateInt(0, 99999);
                                for(int x=0;x<c.getIndex().size();x++)
                                {
                                    if(indexNum==c.getIndex().get(x).getIndexNum())
                                    {
                                        System.out.println("This index number already exists");
                                        break;
                                    }
                                }
                                i.setIndexNum(indexNum);
                                break;
                            default:
                                System.out.println("invalid input");
                                break;
                        }

                    } else {
                        System.out.println("Index does not exist!");
                    }
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } else {
            System.out.println("Course does not exist!");
        }
    }

    /**
     * Method to remove course from an existing school
     */
    public void removeCourse() {
        DisplayDataController dd= new DisplayDataController();

        /**
         * Select the school which the course to be removed belongs to
         */
        School s = dd.schSelection();
        while (s == null) {
                System.out.println("School does not exist");
                s = dd.schSelection();
            }
            Course c = dd.courseSelection(s);
            if (c != null) {
              if(checkCourseHasStudents(c)==false){
                  s.deleteCourse(c);
                  dd.printCourseList();
              }
              else
                  System.out.println("Cannot remove course since students are already registered");
            } else {
                System.out.println("Course doesnt exist!");
            }


    }


    /**
     * Check number of available slots for students to register in an index of choice
     */
    public void checkAvailableSlot() {
        DisplayDataController dd = new DisplayDataController();
        /**
         * Select school which course of choice belongs to
         */
        School school = dd.schSelection();
        if (school != null) {
            /**
             * Select course which the index belongs to
             */
            Course course = dd.courseSelection(school);
            if(course != null) {
                /**
                 * Select which index you want to check
                 */
                Index index = dd.indexSelection(course);
                if(index != null){
                    System.out.println("Number Of Vacancies available " + index.getVacancies() + " out of " + index.getTotalSlots());
                }
                else{
                    System.out.println("There is no Index with IndexNum "+index+" available");
                }
            }
        }
    }

    /**
     * Print student list by a course.
     * @return ArrayList of String[]
     */
    public ArrayList<String[]> printStulistByCourse() {
        FileController fc = new FileController();
        DisplayDataController dd= new DisplayDataController();

        ArrayList<String[]> stuList = new ArrayList<String[]>();
        School school = dd.schSelection();
        if (school != null) {
            Course course = dd.courseSelection(school);
            if (course != null) {
                ArrayList<Index> indexList = course.getIndex();
                for(int i = 0; i < indexList.size(); i++) {
                    Index index = indexList.get(i);
                    ArrayList<RegisteredCourse> regList = index.getRegisteredCourses();
                    if(!regList.isEmpty()) {
                        for(int j = 0; j < regList.size(); j++) {
                            RegisteredCourse regCourse = regList.get(j);
                            String status = regCourse.getRegStatus();
                            if (status.equals("Registered")) {
                                Student student = fc.getStudentByUsername(regCourse.getStudent()); //added Student attribute in RegisteredCouse with getter and setter
                                String name = student.getName();
                                String gender = student.getGender();
                                String nationality = student.getNationality();
                                String[] stuDetails = new String[] {name, gender, nationality};
                                stuList.add(stuDetails);
                            }
                            else {
                                displayErrorMsg("Error. No registration was made.");
                            }
                        }
                    }
                    else {
                        continue;
                    }
                }
                return stuList;
            }
            else {
                displayErrorMsg("Error. No such index."); //maybe should setup NullPointerException
                return null;
            }
        }
        else {
            displayErrorMsg("Error. No such course.");
            return null;
        }
    }
    /**
     * Print student list by index of a course.
     * @return ArrayList of String[]
     */
    public ArrayList<String[]> printStulistByIndex() {
        FileController fc = new FileController();
        DisplayDataController dd= new DisplayDataController();

        ArrayList<String[]> stuListByCourse = new ArrayList<String[]>();
        School school = dd.schSelection();
        if(school != null) {
            Course course = dd.courseSelection(school);
            if(course != null) {
                Index index = dd.indexSelection(course);
                if (index != null) {
                    ArrayList<RegisteredCourse> regList = index.getRegisteredCourses();
                    if(!regList.isEmpty()) {
                        for(int j = 0; j < regList.size(); j++) {
                            RegisteredCourse regCourse = regList.get(j);
                            String status = regCourse.getRegStatus(); //change spelling in sequence diagram
                            if (status.equals("Registered")) {
                                Student student = fc.getStudentByUsername(regCourse.getStudent()); //added Student attribute in RegisteredCouse with getter and setter
                                String name = student.getName();
                                String gender = student.getGender();
                                String nationality = student.getNationality();
                                String[] stuDetails = new String[] {name, gender, nationality};
                                stuListByCourse.add(stuDetails);
                            }
                            else {
                                System.out.println("Error. No registration was made.");
                            }
                        }
                    }
                    return stuListByCourse;
                }
                else {
                    System.out.println("Error. No such index."); //maybe should setup NullPointerException
                    return null;
                }
            }
            else {
                System.out.println("Error. No such course.");
                return null;
            }
        }
        else {
            System.out.println("Error.");
            return null;
        }
    }
    public void displayErrorMsg(String s)
    {   System.out.println("Error occurred");
        System.out.println(s);
    }

}
