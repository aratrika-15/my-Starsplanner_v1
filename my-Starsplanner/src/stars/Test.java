package stars;

public class Test {
    public static void main(String[] args){
        FileController fc = new FileController();
        //fc.initialise();
        fc.RetrieveAdmins();
        fc.RetrieveCourses();
        fc.RetrieveStudents();
        fc.RetrieveSchools();

        LoginUI.main(null);
    }
}
