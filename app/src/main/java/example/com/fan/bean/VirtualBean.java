package example.com.fan.bean;

/**
 * Created by lian on 2017/10/16.
 */
public class VirtualBean {

    /**
     * freeGoldCount : 5.0
     * goldCount : 100.0
     * id : test
     * imgUrl : http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15077094403499da58e.gif
     * info : 充值100送5
     * price : 100.0
     * title : 充值100尤币
     */

    private double freeGoldCount;
    private double goldCount;
    private String id;
    private String imgUrl;
    private String info;
    private double price;
    private String title;

    public double getFreeGoldCount() {
        return freeGoldCount;
    }

    public void setFreeGoldCount(double freeGoldCount) {
        this.freeGoldCount = freeGoldCount;
    }

    public double getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(double goldCount) {
        this.goldCount = goldCount;
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
