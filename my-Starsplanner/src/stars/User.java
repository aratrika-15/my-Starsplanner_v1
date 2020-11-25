package stars;
import java.io.Serializable;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents users in the system
 * Users can be students or Admin
 */
public class User implements Serializable{
    /**
     * Login password of user
     */
    private String password;
    /**
     * Email address of user
     */
    private String email;
    /**
     * Type of user of user ("Student"||"Admin")
     */
    private String typeOfUser;
    /**
     * Username of user
     */
    private String userName;
    /**
     * used in password hashing
     */
    private static final String SALT= "STARWARS";

    /**
     * default constructor
     */
    public User()
    {

    }
    /**
     * Create user object
     * @param email email of user
     * @param password password of user
     * @param typeOfUser user type of user
     * @param userName username of user
     */
    public User(String email,String password,String typeOfUser, String userName) {
        this.password = password;
        this.email = email;
        this.typeOfUser = typeOfUser;
        this.userName=userName;
    }

    /**
     * Get email of user
     * @return email of user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Change email of user
     * @param email new email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Get hashed password of user
     * @return hashed password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Change hashed password of user
     * @param password new hashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user type of user
     * @return user type of user
     */
    public String getTypeOfUser() {
        return this.typeOfUser;
    }

    /**
     * Change the user type of user
     * @param typeOfUser new user type
     */
    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    /**
     * Get the username of user
     * @return username of user
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Change the username of user
     * @param userName new username of user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Check if the password entered is the same as the password of the user
     * @param password password entered by user in login
     * @return  boolean if password match or not
     */
    public boolean matchPassword(String password) {
        //method that will be used for password checking during login
        password=buildPasswordHash(password);
        if (this.password.equals(password))
            return true;
        else
            return false;
    }

    /**
     * Get actual password of the user
     * @param password hashed password of user
     * @return actual password of user
     */
    private static String getHash(String password) {
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

    /**
     * Hash actual password of user
     * @param password actual password of user
     * @return hashed password of user
     */
    public static String buildPasswordHash(String password) {
        String saltedPassword = password + SALT;
        String hashedPassword = getHash(saltedPassword);
        return hashedPassword;
    }
    /**
     * Check if the email entered is of correct format
     * @param email email entered by user
     * @return boolean if format of email is correct or not
     */
    public boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.find();
    }
}

