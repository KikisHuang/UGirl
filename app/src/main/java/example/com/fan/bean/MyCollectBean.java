package example.com.fan.bean;

/**
 * Created by lian on 2017/6/27.
 */
public class MyCollectBean {
    private int collectionCount;

    private String coverPath;

    private String id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    private String userName;
    private String userHeadImgUrl;
    private int typeFlag;

    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }

    private String userId;
    private String mcPublishRecord;


    public String getMcPublishRecord() {
        return mcPublishRecord;
    }

    public void setMcPublishRecord(String mcPublishRecord) {
        this.mcPublishRecord = mcPublishRecord;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getCollectionCount() {
        return this.collectionCount;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getCoverPath() {
        return this.coverPath;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getid() {
        return this.id;
    }
}
