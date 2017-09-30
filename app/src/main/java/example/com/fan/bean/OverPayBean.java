package example.com.fan.bean;

/**
 * Created by lian on 2017/7/6.
 */
public class OverPayBean {
    private int collectionCount;

    private int commentCount;

    private String coverPath;

    private String createTime;

    private String id;

    private int likesCount;

    private String orderId;

    private int seeCount;

    private int sellCount;

    private String user_headImgUrl;

    private String user_id;

    private String user_name;

    private int user_sex;

    private int typeFlag;

    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
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
    public void setLikesCount(int likesCount){
        this.likesCount = likesCount;
    }
    public int getLikesCount(){
        return this.likesCount;
    }
    public void setOrderId(String orderId){
        this.orderId = orderId;
    }
    public String getOrderId(){
        return this.orderId;
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
    public void setUser_headImgUrl(String user_headImgUrl){
        this.user_headImgUrl = user_headImgUrl;
    }
    public String getUser_headImgUrl(){
        return this.user_headImgUrl;
    }
    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
    public String getUser_id(){
        return this.user_id;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public void setUser_sex(int user_sex){
        this.user_sex = user_sex;
    }
    public int getUser_sex(){
        return this.user_sex;
    }
}
