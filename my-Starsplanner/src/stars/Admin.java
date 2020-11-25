package stars;
import java.io.Serializable;

/**
 * Represents an Admin in the system. extends from the {@link #User(String, String, String, String)}
 */
public class Admin extends User implements Serializable {
    /**
     * Unique ID of each admin
     */
    private String adminID;
    /**
     * Invoke user class attributes: email, password, typeOfUser and userName
     */
    public Admin()
    {
        super();
    }
    /**
     * Creates a new admin with the given attributes by invoking user class constructor with an additional adminID attribute
     * @param email Email of this admin
     * @param password password of this admin
     * @param typeOfUser user type of this admin
     * @param userName username of this admin
     * @param adminID adminID of this admin
     */
    public Admin(String email, String password,String typeOfUser, String userName, String adminID) {
        super(email, password, typeOfUser, userName);
        this.adminID = adminID;
    }
    /**
     * Get the adminID of this admin
     * @return this admin's ID
     */
    public String getAdminID() {
        return adminID;
    }
    /**
     * Change the adminID of this admin
     * @param adminID This admin's new ID
     */
    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

}