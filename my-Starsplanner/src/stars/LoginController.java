package stars;
public class LoginController {

    /**
     * Validate the login information entered
     * @param userName username entered by user
     * @param enteredPassword password entered by user
     * @param typeOfUser user domain entered by user
     * @return boolean if this is a valid login
     */
    public boolean validateLogin(String userName, String enteredPassword, String typeOfUser)
    {
        User user;
        FileController fc=new FileController();
        if(userName!=null && enteredPassword!=null&& (typeOfUser.equals("Student") || typeOfUser.equals("Admin")) ) {
            if (typeOfUser.equals("Student")) {

                user = fc.getStudentByUsername(userName);
                if (user != null)
                {
                        Student s=(Student)user;//downcasting to a Student object so as to access the student's school
                    School sch = fc.getSchoolByName(s.getSchool());
                    if(sch.checkWithinAccessPeriod())
                    {
                        if (user.matchPassword(enteredPassword))
                        {
                            PrintMenuUI studentUI = new StudentModeUI();
                            studentUI.showMenu(user);
                            return true;
                        }
                        else
                        {
                            System.out.println("Please enter valid password");
                        }
                    }
                    else
                    {
                        System.out.println("Sorry. Cannot access if it is not access period.");
                    }
                }
                else {
                    user = fc.getAdminByUsername(userName);
                    if(user==null)
                    {
                        System.out.println("Please enter a valid username");
                    }
                    else {
                        System.out.println("Please enter a valid domain");
                    }

                }
            }
            else {
                user = fc.getAdminByUsername(userName);
                if (user != null)
                {
                    if (user.matchPassword(enteredPassword))
                    {
                        PrintMenuUI adminUI = new AdminModeUI();
                        adminUI.showMenu(user);
                        return true;

                    }
                    else
                    {
                        System.out.println("Please enter a valid password");
                    }
                }
                else
                {
                    user = fc.getStudentByUsername(userName);
                    if(user==null)
                    {
                    System.out.println("Please enter a valid username");
                    }
                    else {
                        System.out.println("Please enter a valid domain");
                    }
                }
            }
        }
        else
            System.out.println("Please enter valid username, password and user domain");

               return false;
        }
}
