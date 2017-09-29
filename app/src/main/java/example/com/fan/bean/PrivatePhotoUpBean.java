package example.com.fan.bean;

import java.util.List;

/**
 * Created by lian on 2017/9/27.
 */
public class PrivatePhotoUpBean {

    /**
     * collectionCount : 0
     * commentCount : 0
     * createTime : 2017-09-27 17:28:13
     * hidePosition : 1
     * id : p47e74a63a9534509a1cbb629bc008796
     * isCollection : false
     * isPay : false
     * isShowBanner : false
     * isShowHome : false
     * joinUserId : u58f8b4b9145340459fb46eb90ca1c6a4
     * likesCount : 0
     * mcSettingPublishType : {"createTime":"2017-09-27 17:26:26","id":"t----","typeFlag":-2,"typeName":"模特私密照片","uid":21,"value":0}
     * mcUserByModelUserId : u58f8b4b9145340459fb46eb90ca1c6a4
     * mcUserByUserId : {"account":"13878141913","balance":0,"commentLevel":0,"createTime":"2017-06-16 14:54:01","follwCount":0,"headImgUrl":"http://fns-userimg-public.oss-cn-hangzhou.aliyuncs.com/1503543663562658034.jpeg","hotspotFlag":false,"id":"u58f8b4b9145340459fb46eb90ca1c6a4","invitationUserId":"u58f8b4b9145340459fb46eb90ca1c6a4","loginTime":"2017-09-27 11:40:58","modelFlag":1,"myInvitationCode":"06161f682f","name":"空调病","networkHotspotFlag":0,"officialFlag":false,"photographerFlag":0,"realNameFlag":false,"recommendFlag":false,"sex":1,"status":true,"uid":11,"useInvitationCode":"06161f682f","vipFlag":true,"wx":"649799100"}
     * price : 0.0
     * seeCount : 0
     * sellCount : 0
     * shareCount : 0
     * status : -3
     */

    private int collectionCount;
    private int commentCount;
    private String createTime;
    private int hidePosition;
    private String id;
    private boolean isCollection;
    private boolean isPay;
    private boolean isShowBanner;
    private boolean isShowHome;
    private String joinUserId;
    private int likesCount;
    private McSettingPublishType mcSettingPublishType;
    private String mcUserByModelUserId;
    private McUserByUserId mcUserByUserId;
    private double price;
    private int seeCount;
    private int sellCount;
    private int shareCount;
    private int status;
    private List<mcPublishImgUrls> mcPublishImgUrls;

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public boolean isShowBanner() {
        return isShowBanner;
    }

    public void setShowBanner(boolean showBanner) {
        isShowBanner = showBanner;
    }

    public boolean isShowHome() {
        return isShowHome;
    }

    public void setShowHome(boolean showHome) {
        isShowHome = showHome;
    }

    public List<example.com.fan.bean.mcPublishImgUrls> getMcPublishImgUrls() {
        return mcPublishImgUrls;
    }

    public void setMcPublishImgUrls(List<example.com.fan.bean.mcPublishImgUrls> mcPublishImgUrls) {
        this.mcPublishImgUrls = mcPublishImgUrls;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getHidePosition() {
        return hidePosition;
    }

    public void setHidePosition(int hidePosition) {
        this.hidePosition = hidePosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public boolean isIsPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }

    public boolean isIsShowBanner() {
        return isShowBanner;
    }

    public void setIsShowBanner(boolean isShowBanner) {
        this.isShowBanner = isShowBanner;
    }

    public boolean isIsShowHome() {
        return isShowHome;
    }

    public void setIsShowHome(boolean isShowHome) {
        this.isShowHome = isShowHome;
    }

    public String getJoinUserId() {
        return joinUserId;
    }

    public void setJoinUserId(String joinUserId) {
        this.joinUserId = joinUserId;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public McSettingPublishType getMcSettingPublishType() {
        return mcSettingPublishType;
    }

    public void setMcSettingPublishType(McSettingPublishType mcSettingPublishType) {
        this.mcSettingPublishType = mcSettingPublishType;
    }

    public String getMcUserByModelUserId() {
        return mcUserByModelUserId;
    }

    public void setMcUserByModelUserId(String mcUserByModelUserId) {
        this.mcUserByModelUserId = mcUserByModelUserId;
    }

    public McUserByUserId getMcUserByUserId() {
        return mcUserByUserId;
    }

    public void setMcUserByUserId(McUserByUserId mcUserByUserId) {
        this.mcUserByUserId = mcUserByUserId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(int seeCount) {
        this.seeCount = seeCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
