package example.com.fan.bean;

/**
 * Created by lian on 2017/7/4.
 */
public class WeChatBean {

    private String appid;

    private String noncestr;

    private String Package;

    private String partnerid;

    private String prepayid;

    private String sign;

    private String timestamp;

    public void setAppid(String appid){
        this.appid = appid;
    }
    public String getAppid(){
        return this.appid;
    }
    public void setNoncestr(String noncestr){
        this.noncestr = noncestr;
    }
    public String getNoncestr(){
        return this.noncestr;
    }
    public void setPackage(String Package){
        this.Package = Package;
    }
    public String getPackage(){
        return this.Package;
    }
    public void setPartnerid(String partnerid){
        this.partnerid = partnerid;
    }
    public String getPartnerid(){
        return this.partnerid;
    }
    public void setPrepayid(String prepayid){
        this.prepayid = prepayid;
    }
    public String getPrepayid(){
        return this.prepayid;
    }
    public void setSign(String sign){
        this.sign = sign;
    }
    public String getSign(){
        return this.sign;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public String getTimestamp(){
        return this.timestamp;
    }

}
