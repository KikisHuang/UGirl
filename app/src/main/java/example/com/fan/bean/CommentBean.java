package example.com.fan.bean;

/**
 * Created by lian on 2017/6/21.
 */
public class CommentBean {
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserHeadImgUrl() {
        return userHeadImgUrl;
    }

    public void setUserHeadImgUrl(String userHeadImgUrl) {
        this.userHeadImgUrl = userHeadImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String createTime;
    private String id;
    private String info;
    private String userHeadImgUrl;
    private String userId;
    private String userName;
}
