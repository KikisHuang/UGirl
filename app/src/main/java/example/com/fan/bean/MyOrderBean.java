package example.com.fan.bean;

import java.io.Serializable;

/**
 * Created by lian on 2017/6/28.
 */
public class MyOrderBean implements Serializable {

    private int count;


    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    //0 wehcat; 1 alipay;
    private int payType;

    public int getPaytype() {
        return payType;
    }

    public void setPaytype(int paytype) {
        this.payType = paytype;
    }

    private int transportType;

    private String createTime;

    private String img;

    private int status;

    private String subInfo;

    private String title;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private int type;
    private String price;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setSubInfo(String subInfo) {
        this.subInfo = subInfo;
    }

    public String getSubInfo() {
        return this.subInfo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
