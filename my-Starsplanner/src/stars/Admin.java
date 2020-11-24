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