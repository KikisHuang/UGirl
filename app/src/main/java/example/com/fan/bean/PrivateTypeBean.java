package example.com.fan.bean;

/**
 * Created by lian on 2017/10/16.
 */
public class PrivateTypeBean {

    /**
     * id : t----
     * imgUrl : http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/150800590584864aa2d.jpg
     * typeFlag : -2
     * typeName : 丝袜
     */

    private String id;
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    private String imgUrl;
    private int typeFlag;
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
