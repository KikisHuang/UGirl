package example.com.fan.bean;

/**
 * Created by lian on 2017/7/7.
 */
public class VersionBean {
    private double androidVersion;

    private String iosVersion;

    private String TQq;

    private String kfQq;

    private String kfPhone;

    private String kfWx;

    public String getKfWx() {
        return kfWx;
    }

    public void setKfWx(String kfWx) {
        this.kfWx = kfWx;
    }

    public void setAndroidVersion(double androidVersion){
        this.androidVersion = androidVersion;
    }
    public double getAndroidVersion(){
        return this.androidVersion;
    }
    public void setIosVersion(String iosVersion){
        this.iosVersion = iosVersion;
    }
    public String getIosVersion(){
        return this.iosVersion;
    }
    public void setTQq(String TQq){
        this.TQq = TQq;
    }
    public String getTQq(){
        return this.TQq;
    }
    public void setKfQq(String kfQq){
        this.kfQq = kfQq;
    }
    public String getKfQq(){
        return this.kfQq;
    }
    public void setKfPhone(String kfPhone){
        this.kfPhone = kfPhone;
    }
    public String getKfPhone(){
        return this.kfPhone;
    }

}
