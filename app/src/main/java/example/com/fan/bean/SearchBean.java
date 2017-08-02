package example.com.fan.bean;

/**
 * Created by lian on 2017/7/3.
 */
public class SearchBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTypeFlag() {
        return TypeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        TypeFlag = typeFlag;
    }

    private String name;

    private String coverPath;

    private String id;
    /**
     * TypeFlag:跳转标识符 (0:模特页面,1:专辑页面,4:普通视频页面,5:VR视频页面);
     */
    private int TypeFlag;
}
