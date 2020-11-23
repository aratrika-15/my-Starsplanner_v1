package stars;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.String;

import static stars.CourseType.LEC_TUT_LAB;

public class FileController implements Serializable {
    private static final String adminFileLoc= "./source/Admin.dat";
    private static final String studentFileLoc = "./source/Student.dat";
    private static final String schoolFileLoc = "./source/School.dat";
    private static final String courseFileLoc= "./source/Course.dat";

    private static ArrayList<Admin> adminList = new ArrayList<Admin>();
    private static ArrayList<Student> studentList = new ArrayList<Student>();
    private static ArrayList<School> schoolList = new ArrayList<School>();
    private static ArrayList<Course> courseList=new ArrayList<Course>();
    private static User currentUser;

    public ArrayList<Admin> getAdminList() {
        return adminList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }
    public ArrayList<School> getSchoolList() {

        return schoolList;
    }
    public ArrayList<Course> getCourseList() {

        return courseList;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    //method to save admins to the dat file

    public void saveAdminList(){
        try {
            FileOutputStream fos = new FileOutputStream(adminFileLoc);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(adminList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to save students to the dat file
    public void saveStudentList(){
        try {
            FileOutputStream fos = new FileOutputStream(studentFileLoc);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(studentList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to save schools to the dat file
    public void saveSchoolList(){
        try {
            FileOutputStream fos = new FileOutputStream(schoolFileLoc);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(schoolList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveCourseList(){
        try {
            FileOutputStream fos = new FileOutputStream(courseFileLoc);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(courseList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to retrieve admin info from files into the system
    public void RetrieveAdmins(){
        try {
            FileInputStream fis = new FileInputStream(adminFileLoc);
            ObjectInputStream ois = new ObjectInputStream(fis);
            adminList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry file does not exist");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry class does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to retrieve school info from files into the system
    public void RetrieveStudents(){

        try {
            FileInputStream fis = new FileInputStream(studentFileLoc);
            ObjectInputStream ois = new ObjectInputStream(fis);
            studentList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry file does not exist");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry class does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to retrieve school info from files into the system
    public void RetrieveSchools(){

        try {
            FileInputStream fis = new FileInputStream(schoolFileLoc);
            ObjectInputStream ois = new ObjectInputStream(fis);
            schoolList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry file does not exist");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry class does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void RetrieveCourses(){

        try {
            FileInputStream fis = new FileInputStream(courseFileLoc);
            ObjectInputStream ois = new ObjectInputStream(fis);
            courseList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry file does not exist");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry class does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //function to get the Student object reference by username
    public Student getStudentByUsername(String userName)
    {
        for (int i=0;i<studentList.size();i++) {
            if (studentList.get(i).getUserName() != null && studentList.get(i).getUserName().equals(userName))
                return studentList.get(i);
        }
        return null;
    }

//function to get the Admin object reference by username
    public Admin getAdminByUsername(String userName)
    {
        for (int i=0;i<adminList.size();i++) {
            if (adminList.get(i).getUserName() != null && adminList.get(i).getUserName().equals(userName))
                return adminList.get(i);
        }
        return null;
    }

    //function to get the School object reference by username
    public School getSchoolByName(String name)
    {
        for (int i=0;i<schoolList.size();i++) {
            if (schoolList.get(i).getName() != null && schoolList.get(i).getName().equals(name))
                return schoolList.get(i);
        }
        return null;
    }
    
    public Index getIndexByID(int ID) {
    	for (int i=0;i<schoolList.size();i++) {
    		for (Course c : schoolList.get(i).getCourses()) {
    			for (Index ind : c.getIndex()) {
    				if (ind.getIndexNum() != 0 && ind.getIndexNum() == ID) {
    					return ind;
    				}
    			}
    		}
        }
        return null;
    }
    public Course getCourseByCode(String cCode) {
    	for (int i=0;i<schoolList.size();i++) {
    		for (Course c : schoolList.get(i).getCourses()) {
				if (c.getCourseCode() != null && c.getCourseCode().equals(cCode)) {
					return c;
    		
    			}
    		}
        }
        return null;
    }
    /*public void populate(){
        // write to serialized file - update/insert/delete
        // example - add one more Admin
        //Admin(String email, String password, String typeOfUser, String userName, String adminID)
        Admin ad1 = new Admin("rach@ntu.edu.sg", "password123", "Admin", "Rachel_green", "1234");
        // add to list
        adminList.add(ad1);
        // list.remove(p);  // remove if p equals object in the list

        saveAdminList();
    }*/
    public void initialise(){
        // write to serialized file - update/insert/delete
        // example - add one more Admin
        //Admin(String email, String password, String typeOfUser, String userName, String adminID)
        Admin ad1 = new Admin("rach@ntu.edu.sg", "password123", "Admin", "Rachel_green", "1234");
        Admin ad2 = new Admin("jos@ntu.edu.sg","password124", "Admin", "joshua_brown", "1235");
        Admin ad3 = new Admin("mick@ntu.edu.sg", "password125",  "Admin", "mickey_mouse", "1236");
        Admin ad4 = new Admin("pat@ntu.edu.sg","password126", "Admin", "patrick_star", "1237");
        Admin ad5 = new Admin("sponge@ntu.edu.sg","password127", "Admin", "spongebob_squarepants", "1238");
        // add to list
        adminList.add(ad1);
        adminList.add(ad2);
        adminList.add(ad3);
        adminList.add(ad4);
        adminList.add(ad5);

        //hashing password of all admins
        for (int i =0; i< adminList.size(); i++){
            String hashedPW = adminList.get(i).getPassword();
            hashedPW = adminList.get(i).buildPasswordHash(hashedPW);
            adminList.get(i).setPassword(hashedPW);
        }

        // list.remove(p);  // remove if p equals object in the list

        saveAdminList();
        String sdate_SCSE = "11/11/2019";
        String edate_SCSE = "11/12/2021";
        String sdate_NBS = "11/11/2016";
        String edate_NBS = "11/12/2019";
        Date registrationStartPeriod_SCSE = formatDate(sdate_SCSE);
        Date registrationEndPeriod_SCSE = formatDate(edate_SCSE);
        Date registrationStartPeriod_NBS = formatDate(sdate_NBS);
        Date registrationEndPeriod_NBS = formatDate(edate_NBS);

        School SCSE = new School("SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", registrationStartPeriod_SCSE, registrationEndPeriod_SCSE);
        schoolList.add(SCSE);

        ArrayList<Course> SCSE_courses = new ArrayList<Course>();
        Course CZ2001 = new Course("ALGORITHMS", "CZ2001", "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", LEC_TUT_LAB, 20,3, 2);
        Course CZ2002 = new Course("OBJECT ORIENTED DESIGN & PROGRAMMING", "CZ2002", "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", LEC_TUT_LAB, 20,3, 2);
        Course CZ2005 = new Course("OPERATING SYSTEMS", "CZ2005", "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", LEC_TUT_LAB,2,3, 1);
        SCSE_courses.add(CZ2001);
        SCSE_courses.add(CZ2002);
        SCSE_courses.add(CZ2005);
        SCSE.setCourses(SCSE_courses);
        courseList.add(CZ2001);
        courseList.add(CZ2002);
        courseList.add(CZ2005);

        ArrayList<Index> CZ2001_Index = new ArrayList<Index>();
        //setIndex(int indexNum, String groupNum, int vacancies)
        Index Index_10124 = new Index(10124, "SSP1", 10, "CZ2001");
        Index Index_10125 = new Index(10125, "SSP2", 10, "CZ2001");
        CZ2001_Index.add(Index_10124);
        CZ2001_Index.add(Index_10125);
        CZ2001.setIndexList(CZ2001_Index);
        //CZ2001.setIndex(10125, "SSP2", 10);

        ArrayList<StudyGroup> SG_10124 = new ArrayList<StudyGroup>();
        StudyGroup CS2_LEC1_10124 = new StudyGroup("LT10", 830, 930, 5, "ALL", LessonType.LECTURE, 10124);
        StudyGroup CS2_LEC2_10124 = new StudyGroup("LT11", 1030, 1130, 1, "ALL", LessonType.LECTURE, 10124);
        StudyGroup SSR1_TUT_10124 = new StudyGroup("TR+9", 1630, 1730, 4, "ALL", LessonType.TUTORIAL, 10124);
        StudyGroup SSR1_LAB_10124 = new StudyGroup("HWLAB1", 1030, 1230, 3, "ODD", LessonType.LAB, 10124);
        SG_10124.add(CS2_LEC1_10124);
        SG_10124.add(CS2_LEC2_10124);
        SG_10124.add(SSR1_TUT_10124);
        SG_10124.add(SSR1_LAB_10124);
        Index_10124.setStudyGroup(SG_10124);

        ArrayList<StudyGroup> SG_10125 = new ArrayList<StudyGroup>();
        StudyGroup CS2_LEC1_10125 = new StudyGroup("LT10", 830, 930, 5, "ALL", LessonType.LECTURE, 10125);
        StudyGroup CS2_LEC2_10125 = new StudyGroup("LT11", 1030, 1130, 1, "ALL", LessonType.LECTURE, 10125);
        StudyGroup SSR5_TUT_10125 = new StudyGroup("TR+15", 1330, 1430, 3, "ALL", LessonType.TUTORIAL, 10125);
        StudyGroup SSR5_LAB_10125 = new StudyGroup("HWLAB2", 830, 1030, 3, "ODD", LessonType.LAB, 10125);
        SG_10125.add(CS2_LEC1_10125);
        SG_10125.add(CS2_LEC2_10125);
        SG_10125.add(SSR5_TUT_10125);
        SG_10125.add(SSR5_LAB_10125);
        Index_10125.setStudyGroup(SG_10125);

        ArrayList<Index> CZ2002_Index = new ArrayList<Index>();
        Index Index_10126 = new Index(10126, "SSP3", 10, "CZ2002");
        Index Index_10127 = new Index(10127, "SSP4", 10, "CZ2002");
        CZ2002_Index.add(Index_10126);
        CZ2002_Index.add(Index_10127);
        CZ2002.setIndexList(CZ2002_Index);

        ArrayList<StudyGroup> SG_10126 = new ArrayList<StudyGroup>();
        StudyGroup CS2_LEC1_10126 = new StudyGroup("LT11", 830, 930, 4, "ALL", LessonType.LECTURE, 10126);
        StudyGroup CS2_LEC2_10126 = new StudyGroup("LT11", 1430, 1530, 2, "ALL", LessonType.LECTURE, 10126);
        StudyGroup FEP1_TUT_10126 = new StudyGroup("TR+17", 930, 1030, 3, "ALL", LessonType.TUTORIAL, 10126);
        StudyGroup FEP1_LAB_10126 = new StudyGroup("SPL", 1230, 1630, 1, "ODD", LessonType.LECTURE.LAB, 10126);
        SG_10126.add(CS2_LEC1_10126);
        SG_10126.add(CS2_LEC2_10126);
        SG_10126.add(FEP1_TUT_10126);
        SG_10126.add(FEP1_LAB_10126);
        Index_10126.setStudyGroup(SG_10126);

        ArrayList<StudyGroup> SG_10127 = new ArrayList<StudyGroup>();
        StudyGroup CS2_LEC1_10127 = new StudyGroup("LT11", 830, 930, 4, "ALL", LessonType.LECTURE, 10127);
        StudyGroup CS2_LEC2_10127 = new StudyGroup("LT11", 1430, 1530, 2, "ALL", LessonType.LECTURE, 10127);
        StudyGroup FSP3_TUT_10127 = new StudyGroup("TR+9", 1330, 1430, 3, "ALL", LessonType.TUTORIAL, 10127);
        StudyGroup FSP3_LAB_10127 = new StudyGroup("SPL", 1430, 1630, 1, "EVEN", LessonType.LAB, 10127);
        SG_10127.add(CS2_LEC1_10127);
        SG_10127.add(CS2_LEC2_10127);
        SG_10127.add(FSP3_TUT_10127);
        SG_10127.add(FSP3_LAB_10127);
        Index_10127.setStudyGroup(SG_10127);

        ArrayList<Index> CZ2005_Index = new ArrayList<Index>();
        Index Index_10128 = new Index(10128, "SSP5", 2, "CZ2005");
        CZ2005_Index.add(Index_10128);
        CZ2005.setIndexList(CZ2005_Index);
        ArrayList<StudyGroup> SG_10128 = new ArrayList<StudyGroup>();
        StudyGroup CS2_LEC1_10128 = new StudyGroup("LT10", 830, 930, 6, "ALL", LessonType.LECTURE, 10128);
        SG_10128.add(CS2_LEC1_10128);
        Index_10128.setStudyGroup(SG_10128);




        School NBS = new School("NANYANG_BUSINESS_SCHOOL", registrationStartPeriod_NBS, registrationEndPeriod_NBS);
        schoolList.add(NBS);

        ArrayList<Course> NBS_courses = new ArrayList<Course>();
        Course AD2101 = new Course("MANAGEMENT ACCOUNTING", "AD2101", "NANYANG_BUSINESS_SCHOOL", CourseType.LEC, 50, 3,3);
        Course BC2407 = new Course("ANALYTICS II: ADVANCED PREDICTIVE TECHNIQUES", "BC2407", "NANYANG_BUSINESS_SCHOOL", CourseType.LEC, 50,3, 3);
        NBS_courses.add(AD2101);
        NBS_courses.add(BC2407);
        NBS.setCourses(NBS_courses);
        courseList.add(AD2101);
        courseList.add(BC2407);

        ArrayList<Index> AD2101_Index = new ArrayList<Index>();
        Index Index_00146 = new Index(146, "SEM1", 10, "AD2101");
        Index Index_00147 = new Index(147, "SEM2", 10, "AD2101");
        Index Index_00148 = new Index(148, "SEM3", 10, "AD2101");
        Index Index_00149 = new Index(149, "SEM4", 10, "AD2101");
        Index Index_00150 = new Index(150, "SEM5", 10, "AD2101");
        AD2101_Index.add(Index_00146);
        AD2101_Index.add(Index_00147);
        AD2101_Index.add(Index_00148);
        AD2101_Index.add(Index_00149);
        AD2101_Index.add(Index_00150);
        AD2101.setIndexList(AD2101_Index);

        ArrayList<StudyGroup> SG_00146 = new ArrayList<StudyGroup>();
        StudyGroup SEM1_00146 = new StudyGroup("ONLINE", 830, 1130, 1, "ALL", LessonType.LECTURE, 146);
        SG_00146.add(SEM1_00146);
        Index_00146.setStudyGroup(SG_00146);
        ArrayList<StudyGroup> SG_00147 = new ArrayList<StudyGroup>();
        StudyGroup SEM2_00147 = new StudyGroup("ONLINE", 1330, 1630, 4, "ALL", LessonType.LECTURE, 147);
        SG_00147.add(SEM2_00147);
        Index_00147.setStudyGroup(SG_00147);
        ArrayList<StudyGroup> SG_00148 = new ArrayList<StudyGroup>();
        StudyGroup SEM3_00148 = new StudyGroup("ONLINE", 830, 1130, 2, "ALL", LessonType.LECTURE, 148);
        SG_00148.add(SEM3_00148);
        Index_00148.setStudyGroup(SG_00148);
        ArrayList<StudyGroup> SG_00149 = new ArrayList<StudyGroup>();
        StudyGroup SEM4_00149 = new StudyGroup("ONLINE", 1330, 1630, 3, "ALL", LessonType.LECTURE, 149);
        SG_00149.add(SEM4_00149);
        Index_00149.setStudyGroup(SG_00149);
        ArrayList<StudyGroup> SG_00150 = new ArrayList<StudyGroup>();
        StudyGroup SEM5_00150 = new StudyGroup("ONLINE", 1430, 1730, 4, "ALL", LessonType.LECTURE, 150);
        SG_00150.add(SEM5_00150);
        Index_00150.setStudyGroup(SG_00150);

        ArrayList<Index> BC2407_Index = new ArrayList<Index>();
        Index Index_00676 = new Index(676, "SEM1", 10, "BC2407");
        Index Index_00677 = new Index(677, "SEM2", 10, "BC2407");
        Index Index_00678 = new Index(678, "SEM3", 10, "BC2407");
        Index Index_00679 = new Index(679, "SEM4", 10, "BC2407");
        Index Index_00680 = new Index(680, "SEM5", 10, "BC2407");
        BC2407_Index.add(Index_00676);
        BC2407_Index.add(Index_00677);
        BC2407_Index.add(Index_00678);
        BC2407_Index.add(Index_00679);
        BC2407_Index.add(Index_00680);
        BC2407.setIndexList(BC2407_Index);

        ArrayList<StudyGroup> SG_00676 = new ArrayList<StudyGroup>();
        StudyGroup SEM1_00676 = new StudyGroup("S3-SR4", 930, 1230, 1, "ALL", LessonType.LECTURE, 676);
        SG_00676.add(SEM1_00676);
        Index_00676.setStudyGroup(SG_00676);
        ArrayList<StudyGroup> SG_00677 = new ArrayList<StudyGroup>();
        StudyGroup SEM2_00677 = new StudyGroup("S4-SR9", 930, 1230, 3, "ALL", LessonType.LECTURE, 677);
        SG_00677.add(SEM2_00677);
        Index_00677.setStudyGroup(SG_00677);
        ArrayList<StudyGroup> SG_00678 = new ArrayList<StudyGroup>();
        StudyGroup SEM3_00678 = new StudyGroup("S4-SR11", 1430, 1730, 3, "ALL", LessonType.LECTURE, 678);
        SG_00678.add(SEM3_00678);
        Index_00678.setStudyGroup(SG_00678);
        ArrayList<StudyGroup> SG_00679 = new ArrayList<StudyGroup>();
        StudyGroup SEM4_00679 = new StudyGroup("S3-SR2", 1830, 2130, 1, "ALL", LessonType.LECTURE, 679);
        SG_00679.add(SEM4_00679);
        Index_00679.setStudyGroup(SG_00679);
        ArrayList<StudyGroup> SG_00680 = new ArrayList<StudyGroup>();
        StudyGroup SEM5_00680 = new StudyGroup("S3-SR3", 1430, 1730, 4, "ALL", LessonType.LECTURE, 680);
        SG_00680.add(SEM5_00680);
        Index_00680.setStudyGroup(SG_00680);

        //(String name, String matricNumber, String gender, String nationality, int year, School school,String choice,String recipient, String email, String password,String typeOfUser, String userName)
        Student std1 = new Student("Peter Griffen", "2456", "Female", "Turkey", 2, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "pete@gmail.com", "pete@ntu.edu.sg", "password361", "Student", "Peter_Griffen");
        Student std2 = new Student("Sandy Cheeks", "2457", "Male", "China", 1, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendTele", "84216859", "sand@ntu.edu.sg", "password362", "Student", "Sandy_Cheeks");
        Student std3 = new Student("Harry Potter", "2458", "Female", "Vietnam", 2, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "hairyntu@gmail.com", "hairy@ntu.edu.sg", "password363", "Student", "Harry_Potter");
        Student std4 = new Student("Sherlock Holmes", "2459", "Female", "Poland", 4, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "sher@gmail.com", "sher@ntu.edu.sg", "password364", "Student", "Sherlock_Holmes");
        Student std5 = new Student("Lewis Hamilton", "2460", "Male", "England", 3, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "lew@gmail.com", "lew@ntu.edu.sg", "password365", "Student", "Lewis_Hamilton");
        Student std6 = new Student("John Green", "2461", "Female", "Singapore", 2, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "reddysaiteja5@gmail.com", "john@ntu.edu.sg", "password366", "Student", "John_Green");
        Student std7 = new Student("Oliver Twist", "2462", "Male", "India", 1, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "oliv@gmail.com", "oliv@ntu.edu.sg", "password367", "Student", "Oliver_Twist");
        Student std8 = new Student("Anne Frank", "2463", "Female", "Sweden", 2, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "anne@gmail.com", "anne@ntu.edu.sg", "password368", "Student", "Anne_Frank");
        Student std9 = new Student("Christopher Williams", "2464", "Female", "United Kingdom", 4, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "chris@gmail.com", "chris@ntu.edu.sg", "password369", "Student", "Christopher_Williams");
        Student std10 = new Student("Michelle Phang", "2465", "Male", "Singapore", 3, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "mich@gmail.com", "mich@ntu.edu.sg", "password370", "Student", "Michelle_Phang");
        Student std11 = new Student("Philips Stroll", "2466", "Female", "Singapore", 2, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "phil@gmail.com", "phil@ntu.edu.sg", "password371", "Student", "Philips_Stroll");
        Student std12 = new Student("Chan Bo Seng", "2467", "Male", "Singapore", 1, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "chan@gmail.com", "chan@ntu.edu.sg", "password372", "Student", "Chan_Bo_Seng");
        Student std13 = new Student("Chloe Lee", "2468", "Female", "Singapore", 2, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "chlo@gmail.com", "chlo@ntu.edu.sg", "password373", "Student", "Chloe_Lee");
        Student std14 = new Student("Mary Poppins", "2469", "Female", "Singapore", 4, "NANYANG_BUSINESS_SCHOOL", "SendEmail", "mary@gmail.com", "mary@ntu.edu.sg", "password374", "Student", "Mary_Poppins");
        Student std15 = new Student("Lim Jin Long", "2470", "Male", "Singapore", 3, "SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING", "SendEmail", "limj@gmail.com", "limj@ntu.edu.sg", "password375", "Student", "Lim_Jin_Long");

        ArrayList<Course> pastC = new ArrayList<Course>();
        pastC.add(CZ2001);
        pastC.add(CZ2002);

        std2.setPastCourses(pastC);
        std5.setPastCourses(pastC);
        Review r1 = new Review("My TA was awesome! How do I get him again for future courses??", true, "CZ2002");
        Review r2 = new Review("While it was taught well, gotta say I didnt really enjoy OODP", false, "CZ2002");
        Review r3 = new Review("Learnt so much from this mod. Was able to apply a lot of it during my internship!", true,  "CZ2002");
        Review rr1 = new Review("Executed badly, My brain was not made for this! Could have catered better for the not so smart people like me.",false,"CZ2001");
        Review rr2= new Review("Very useful for any aspiring software developers. Otherwise, dont waste your brain cells", true, "CZ2001");


//

        studentList.add(std1);
        studentList.add(std2);
        studentList.add(std3);
        studentList.add(std4);
        studentList.add(std5);
        studentList.add(std6);
        studentList.add(std7);
        studentList.add(std8);
        studentList.add(std9);
        studentList.add(std10);
        studentList.add(std11);
        studentList.add(std12);
        studentList.add(std13);
        studentList.add(std14);
        studentList.add(std15);

        for (int i =0; i< studentList.size(); i++){
            String hashedPW = studentList.get(i).getPassword();
            hashedPW = studentList.get(i).buildPasswordHash(hashedPW);
            studentList.get(i).setPassword(hashedPW);
        }

        saveStudentList();

        ArrayList<Student> SCSE_studentList = new ArrayList<Student>();
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            School studentSchool = getSchoolByName(currentStudent.getSchool());
            String studentSchoolName = studentSchool.getName();
            if (studentSchoolName.equals("SCHOOL_OF_COMPUTER_SCIENCE_AND_ENGINEERING")) {
                SCSE_studentList.add(currentStudent);
            }
        }
        SCSE.setStudents(SCSE_studentList);

        ArrayList<Student> NBS_studentList = new ArrayList<Student>();
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            School studentSchool = getSchoolByName(currentStudent.getSchool());
            String studentSchoolName = studentSchool.getName();
            if (studentSchoolName.equals("NANYANG_BUSINESS_SCHOOL")) {
                NBS_studentList.add(currentStudent);
            }
        }
        NBS.setStudents(NBS_studentList);

        saveSchoolList();
        saveCourseList();
    }
    public void printStudentList()
    {   System.out.println("");
        System.out.println("List of students");
        System.out.println("\t Student Name\t\t\t   Matriculation Number\t   Gender \t  Nationality \t\t   School\t\t\t\t\t\t\t\t\t\t\t  Year of Study\t\t");
        System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________");
        for(int i=0;i<studentList.size();i++)
        {
            Student student=studentList.get(i);
            String column0Format = "%3d";
            String column1Format = "%-25s";
            String column2Format = "%-23s";
            String column3Format = "%-10s";
            String column4Format = "%-20s";
            String column5Format = "%-50s";
            String column6Format = "%-1d";
            String formatInfo = column0Format+ ". " + column1Format + " " + column2Format + " " + column3Format + " " + column4Format + " " + column5Format + " " + column6Format;
            System.out.format(formatInfo, (i+1), student.getName(), student.getMatricNumber(), student.getGender(), student.getNationality(), getSchoolByName(student.getSchool()).getName(), student.getYear());
            System.out.println();
        }

    }
    public void printCourseList() {
        System.out.println("==========List of Courses==========\n");
        System.out.println("School\t\t\t\t\t\t\t\t\t\t\t  Course Code    Course Name");
        System.out.println("----------------------------------------------------------------------------------------");
        for (int i=0;i<schoolList.size();i++) {
            for (Course c : schoolList.get(i).getCourses()) {
                if (c.getCourseCode() != null) {
                    System.out.printf("%-50s",schoolList.get(i).getName());
                    System.out.printf("%-15s",c.getCourseCode());
                    System.out.printf("%-50s\n",c.getName());
                }
            }
        }


    }
    public Date formatDate(String s)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            System.out.println("Eror!! Please Enter the Correct Date Format");
            System.out.println();
            return null;
        }
        return date;
    }


    // get a specific index from the list of schools




}
