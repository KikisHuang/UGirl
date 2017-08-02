package example.com.fan.bean;

/**
 * Created by lian on 2017/7/4.
 */
public class AliPayBean {
    private String app_id;

    private String biz_content;

    private String charset;

    private String format;

    private String method;

    private String notify_url;

    private String sign;

    private String sign_type;

    private String timestamp;

    private String version;
    private String info;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    private String notifyUrl;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setApp_id(String app_id){
        this.app_id = app_id;
    }
    public String getApp_id(){
        return this.app_id;
    }
    public void setBiz_content(String biz_content){
        this.biz_content = biz_content;
    }
    public String getBiz_content(){
        return this.biz_content;
    }
    public void setCharset(String charset){
        this.charset = charset;
    }
    public String getCharset(){
        return this.charset;
    }
    public void setFormat(String format){
        this.format = format;
    }
    public String getFormat(){
        return this.format;
    }
    public void setMethod(String method){
        this.method = method;
    }
    public String getMethod(){
        return this.method;
    }
    public void setNotify_url(String notify_url){
        this.notify_url = notify_url;
    }
    public String getNotify_url(){
        return this.notify_url;
    }
    public void setSign(String sign){
        this.sign = sign;
    }
    public String getSign(){
        return this.sign;
    }
    public void setSign_type(String sign_type){
        this.sign_type = sign_type;
    }
    public String getSign_type(){
        return this.sign_type;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public String getTimestamp(){
        return this.timestamp;
    }
    public void setVersion(String version){
        this.version = version;
    }
    public String getVersion(){
        return this.version;
    }

}
