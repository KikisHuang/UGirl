package example.com.fan.bean;

/**
 * Created by lian on 2017/10/27.
 */
public class McVipBean {

    /**
     * createTime : 2017-06-21 14:40:14
     * iconUrl : http://fns-system.oss-cn-hangzhou.aliyuncs.com/1499625831111c72568.png
     * id : test
     * info : 送价值69元VR眼镜
     * level : 2
     * monthCount : 1
     * name : 单月包期
     * price : 99.0
     * type : 0
     * uid : -4
     */

    private String createTime;
    private String iconUrl;
    private String id;
    private String info;
    private int level;
    private int monthCount;
    private String name;
    private double price;
    private int type;
    private int uid;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
