package example.com.fan.bean;

/**
 * Created by lian on 2017/6/21.
 */
public class mcPublishImgUrls {
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(boolean needMoney) {
        this.needMoney = needMoney;
    }

    private String path;
    private boolean needMoney;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String basePath;
    private String id;
    private String publishRecordId;

    public String getPublishRecordId() {
        return publishRecordId;
    }

    public void setPublishRecordId(String publishRecordId) {
        this.publishRecordId = publishRecordId;
    }
}
