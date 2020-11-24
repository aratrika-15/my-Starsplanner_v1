package stars;
import java.io.Serializable;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable{
    private String password;
    private String email;
    private String typeOfUser;
    private String userName;
    private static final String SALT= "STARWARS";
    public User()
    {

    }
    public User(String email,String password,String typeOfUser, String userName) {
        this.password = password;
        this.email = email;
        this.typeOfUser = typeOfUser;
        this.userName=userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfUser() {
        return this.typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean matchPassword(String password) {
        //method that will be used for password checking during login
        password=buildPasswordHash(password);
        if (this.password.equals(password))
            return true;
        else
            return false;
    }
    public static String getHash(String password) {
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
        String saltedPassword = password + SALT;
        String hashedPassword = getHash(saltedPassword);
        return hashedPassword;
    }
    //method to validate the email and check if it is correct
    public boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.find();
    }
}

