package example.com.fan.bean;

/**
 * Created by lian on 2017/10/13.
 */
public class AccountDetailsBean {
    public float getPhotoSumMoney() {
        return photoSumMoney;
    }

    public void setPhotoSumMoney(float photoSumMoney) {
        this.photoSumMoney = photoSumMoney;
    }

    public float getVideoSumMoney() {
        return videoSumMoney;
    }

    public void setVideoSumMoney(float videoSumMoney) {
        this.videoSumMoney = videoSumMoney;
    }

    public float getCashSumMoney() {
        return cashSumMoney;
    }

    public void setCashSumMoney(float cashSumMoney) {
        this.cashSumMoney = cashSumMoney;
    }

    public float getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(float sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(String cashAccount) {
        this.cashAccount = cashAccount;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private float photoSumMoney;
    private float videoSumMoney;
    private float cashSumMoney;
    private float sumMoney;
    private String cashAccount;
    private float money;
    private String updateTime;

}
