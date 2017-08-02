package example.com.fan.bean;

import java.util.List;

/**
 * Created by lian on 2017/6/29.
 */
public class ModelBean {

    private int collectionCount;

    private int commentCount;

    private String coverPath;

    private String createTime;

    private String id;

    private boolean isShowBanner;

    private boolean isShowHome;

    private JoinUser joinUser;

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

    public JoinUser getJoinUser() {
        return joinUser;
    }

    public void setJoinUser(JoinUser joinUser) {
        this.joinUser = joinUser;
    }

    private int likesCount;

    private List<mcPublishImgUrls> mcPublishImgUrls ;

    private List<mcPublishVideoUrls> mcPublishVideoUrls ;

    private McSettingPublishType mcSettingPublishType;

    private String name;

    private double price;

    private int sellCount;

    private int status;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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
    public void setLikesCount(int likesCount){
        this.likesCount = likesCount;
    }
    public int getLikesCount(){
        return this.likesCount;
    }
    public void setMcPublishImgUrls(List<mcPublishImgUrls> mcPublishImgUrls){
        this.mcPublishImgUrls = mcPublishImgUrls;
    }
    public List<mcPublishImgUrls> getMcPublishImgUrls(){
        return this.mcPublishImgUrls;
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
}
