package stars;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class LoginController {

    private User user;
    FileController fc=new FileController();
    public boolean validateLogin(String userName, String enteredPassword, String typeOfUser)
    {
        if(userName!=null && enteredPassword!=null&& (typeOfUser.equals("Student") || typeOfUser.equals("Admin")) ) {
            //String hashedPassword = User.buildPasswordHash(enteredPassword);
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
                        //System.out.println(user.getPassword());
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
            System.out.println("Please enter valid email, password and user domain");

               return false;
        }


   /* private static String getHash(String password) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256"); // using SHA-256 algorithm for password hashing
            byte[] hashedBytes = sha.digest(password.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; idx++) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // hash generation failure
        }

        return (hash.toString());
    }

    public static String buildPasswordHash(String password) {
        String saltedPassword = password+SALT;
        String hashedPassword = getHash(saltedPassword);
        return hashedPassword;
    }*/
}
