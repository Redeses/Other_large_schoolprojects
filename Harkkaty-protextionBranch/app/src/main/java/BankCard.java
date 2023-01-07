import java.util.Date;

public class BankCard {
    private String CardNumber;
    private int pinCode;
    private int verificationNumber;
    private Date expireDate;
    private String ownerID;
    private String accountID;
    private String type;
    private int credit;
    private boolean outOfComission;

    public BankCard(){
        CardNumber = "0";
        pinCode = 0000;
        verificationNumber = 000;
        expireDate = null;
        ownerID  = "";
        type = "credit&debit";
        credit = 0;
        outOfComission = false;
    }
}
