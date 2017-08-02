package example.com.fan.bean;

import java.util.List;

/**
 * Created by lian on 2017/6/23.
 */
public class BuyGoodsBean {
    private String coverPath;

    private String createTime;

    private String id;

    private List<McOfficialSellImgUrls> mcOfficialSellImgContentUrls;

    private List<McOfficialSellImgUrls> mcOfficialSellImgUrls;

    private double price;

    private int status;

    private int stock;

    private int stockMax;

    private String subInfo;

    private String title;

    private int uid;

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getCoverPath() {
        return this.coverPath;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setMcOfficialSellImgContentUrls(List<McOfficialSellImgUrls> mcOfficialSellImgContentUrls) {
        this.mcOfficialSellImgContentUrls = mcOfficialSellImgContentUrls;
    }

    public List<McOfficialSellImgUrls> getMcOfficialSellImgContentUrls() {
        return this.mcOfficialSellImgContentUrls;
    }

    public void setMcOfficialSellImgUrls(List<McOfficialSellImgUrls> mcOfficialSellImgUrls) {
        this.mcOfficialSellImgUrls = mcOfficialSellImgUrls;
    }

    public List<McOfficialSellImgUrls> getMcOfficialSellImgUrls() {
        return this.mcOfficialSellImgUrls;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStockMax(int stockMax) {
        this.stockMax = stockMax;
    }

    public int getStockMax() {
        return this.stockMax;
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

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return this.uid;
    }

}
