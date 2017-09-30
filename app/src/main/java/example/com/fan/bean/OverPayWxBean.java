package example.com.fan.bean;

/**
 * Created by lian on 2017/9/30.
 */
public class OverPayWxBean {

    /**
     * headImgUrl : http://fns-userimg-public.oss-cn-hangzhou.aliyuncs.com/1506571373242ec5087.jpg
     * payTime : 2017-09-30 11:27:00
     * realName : 小燕
     * wx : 686869
     */

    private String headImgUrl;
    private String payTime;
    private String realName;
    private String wx;
    private String wxPrice;

    public String getWxPrice() {
        return wxPrice;
    }

    public void setWxPrice(String wxPrice) {
        this.wxPrice = wxPrice;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }
}
