package example.com.fan.bean;

import java.util.List;

/**
 * Created by lian on 2017/6/29.
 */
public class VideoPlayBean {
    private int collectionCount;

    private int commentCount;

    private String coverPath;
    private int shareCount;

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    private String createTime;

    private String id;

    private String info;

    private boolean isShowBanner;

    private boolean isShowHome;

    private JoinUser joinUser;

    private String joinUserId;

    private int likesCount;

    private List<mcPublishVideoUrls> mcPublishVideoUrls ;

    private McSettingPublishType mcSettingPublishType;

    private String name;

    private double price;

    private int seeCount;

    private int sellCount;

    private int status;

    private int uid;

    public void setCollectionCount(int collectionCount){
        this.collectionCount = collectionCount;
    }
    public int getCollectionCount(){
        return this.collectionCount;
    }
    public void setCommentCount(int commentCount){
        this.commentCount = commentCount;
    }
    public int getCommentCount(){
        return this.commentCount;
    }
    public void setCoverPath(String coverPath){
        this.coverPath = coverPath;
    }
    public String getCoverPath(){
        return this.coverPath;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }
    public void setIsShowBanner(boolean isShowBanner){
        this.isShowBanner = isShowBanner;
    }
    public boolean getIsShowBanner(){
        return this.isShowBanner;
    }
    public void setIsShowHome(boolean isShowHome){
        this.isShowHome = isShowHome;
    }
    public boolean getIsShowHome(){
        return this.isShowHome;
    }
    public void setJoinUser(JoinUser joinUser){
        this.joinUser = joinUser;
    }
    public JoinUser getJoinUser(){
        return this.joinUser;
    }
    public void setJoinUserId(String joinUserId){
        this.joinUserId = joinUserId;
    }
    public String getJoinUserId(){
        return this.joinUserId;
    }
    public void setLikesCount(int likesCount){
        this.likesCount = likesCount;
    }
    public int getLikesCount(){
        return this.likesCount;
    }
    public void setMcPublishVideoUrls(List<mcPublishVideoUrls> mcPublishVideoUrls){
        this.mcPublishVideoUrls = mcPublishVideoUrls;
    }
    public List<mcPublishVideoUrls> getMcPublishVideoUrls(){
        return this.mcPublishVideoUrls;
    }
    public void setMcSettingPublishType(McSettingPublishType mcSettingPublishType){
        this.mcSettingPublishType = mcSettingPublishType;
    }
    public McSettingPublishType getMcSettingPublishType(){
        return this.mcSettingPublishType;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setSeeCount(int seeCount){
        this.seeCount = seeCount;
    }
    public int getSeeCount(){
        return this.seeCount;
    }
    public void setSellCount(int sellCount){
        this.sellCount = sellCount;
    }
    public int getSellCount(){
        return this.sellCount;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setUid(int uid){
        this.uid = uid;
    }
    public int getUid(){
        return this.uid;
    }
}
