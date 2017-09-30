package example.com.fan.bean;

/**
 * Created by lian on 2017/7/6.
 */
public class OverPayVideoVrBean {
    private int collectionCount;

    private int commentCount;

    private String coverPath;

    private String createTime;

    private String id;

    private String info;

    private boolean isCollection;

    private boolean isShowBanner;

    private boolean isShowHome;

    private JoinUser joinUser;

    private String joinUserId;

    private int likesCount;
    private int typeFlag;

    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }

    private McSettingPublishType mcSettingPublishType;

    private String mcUserByModelUserId;

    private McUserByUserId mcUserByUserId;

    private String name;

    private double price;

    private int sellCount;

    private int status;
    private int seeCount;

    public int getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(int seeCount) {
        this.seeCount = seeCount;
    }

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
    public void setIsCollection(boolean isCollection){
        this.isCollection = isCollection;
    }
    public boolean getIsCollection(){
        return this.isCollection;
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
    public void setMcSettingPublishType(McSettingPublishType mcSettingPublishType){
        this.mcSettingPublishType = mcSettingPublishType;
    }
    public McSettingPublishType getMcSettingPublishType(){
        return this.mcSettingPublishType;
    }
    public void setMcUserByModelUserId(String mcUserByModelUserId){
        this.mcUserByModelUserId = mcUserByModelUserId;
    }
    public String getMcUserByModelUserId(){
        return this.mcUserByModelUserId;
    }
    public void setMcUserByUserId(McUserByUserId mcUserByUserId){
        this.mcUserByUserId = mcUserByUserId;
    }
    public McUserByUserId getMcUserByUserId(){
        return this.mcUserByUserId;
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
    public void setUid(int uid){
        this.uid = uid;
    }
    public int getUid(){
        return this.uid;
    }


}
