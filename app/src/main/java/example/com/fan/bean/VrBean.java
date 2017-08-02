package example.com.fan.bean;

/**
 * Created by lian on 2017/6/17.
 */
public class VrBean {
    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String coverPath;
    String user_headImgUrl;
    String id;
    String user_id;
    String mcUserByModelUserId;

    public String getMcUserByModelUserId() {
        return mcUserByModelUserId;
    }

    public void setMcUserByModelUserId(String mcUserByModelUserId) {
        this.mcUserByModelUserId = mcUserByModelUserId;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int collectionCount;
    int commentCount;
    String name;
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * VR/视频 标识符;
     * 4普通视频,5VR视频;
     **/
    int flag;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    String user_name;

    public String getSellCount() {
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    String sellCount;
    String seeCount;

    public String getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(String seeCount) {
        this.seeCount = seeCount;
    }
}
