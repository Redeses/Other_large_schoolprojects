import java.util.ArrayList;

public class account {
    private String accountID;
    private double accountBalance;
    private String userIDBorrowed;
    private String type;
    private boolean frozen;



    public account(){
        accountID = "";
        accountBalance= 0;
        userIDBorrowed = "none GIven";
        type = "";
        frozen = false;
    }
}
