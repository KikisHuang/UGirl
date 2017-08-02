package example.com.fan.bean;

/**
 * Created by lian on 2017/7/5.
 */
public class VrVideoBean {

    private int collectionCount;

    private int commentCount;

    private String coverPath;

    private int flag;

    private String id;

    private int likesCount;

    private String name;

    private int sellCount;
    private int seeCount;

    public int getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(int seeCount) {
        this.seeCount = seeCount;
    }

    private String user_headImgUrl;

    private String user_id;

    private String user_name;

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
    public void setFlag(int flag){
        this.flag = flag;
    }
    public int getFlag(){
        return this.flag;
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
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
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

}
