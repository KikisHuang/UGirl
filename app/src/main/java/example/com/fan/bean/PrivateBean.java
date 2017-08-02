package example.com.fan.bean;

/**
 * Created by lian on 2017/6/16.
 */
public class PrivateBean {

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_headImgUrl() {
        return user_headImgUrl;
    }

    public void setUser_headImgUrl(String user_headImgUrl) {
        this.user_headImgUrl = user_headImgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String coverPath;
    String user_id;
    String user_name;
    String user_headImgUrl;
    String id;
}
