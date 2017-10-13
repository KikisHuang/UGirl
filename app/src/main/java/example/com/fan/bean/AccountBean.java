package example.com.fan.bean;

/**
 * Created by lian on 2017/10/11.
 */
public class AccountBean {

    /**
     * balance : 0.0
     * phone : null
     * cashAccount : null
     */
    private double balance;
    private String phone;
    private String cashAccount;
    private String cashUserName;

    public String getCashUserName() {
        return cashUserName;
    }

    public void setCashUserName(String cashUserName) {
        this.cashUserName = cashUserName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(String cashAccount) {
        this.cashAccount = cashAccount;
    }
}
