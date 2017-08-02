package example.com.fan.bean;

/**
 * Created by lian on 2017/6/29.
 */
public class mcPublishVideoUrls {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(boolean needMoney) {
        this.needMoney = needMoney;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    private String id;
    private boolean needMoney;
    private String path;
    private int type;
}
