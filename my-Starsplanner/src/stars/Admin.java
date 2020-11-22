package stars;
import java.io.Serializable;
public class Admin extends User implements Serializable {
    private String adminID;
    public Admin()
    {
        super();
    }

    public Admin(String email, String password,String typeOfUser, String userName, String adminID) {
        super(email, password, typeOfUser, userName);
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

}

// we never pass in type of user as constructor for User in class diagram
// method signature of Admin class incomplete as well in class diagram
// also missing Admin constructor in class diagram