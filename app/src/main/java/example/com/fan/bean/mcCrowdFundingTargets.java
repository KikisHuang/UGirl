package example.com.fan.bean;

/**
 * Created by lian on 2017/6/30.
 */
public class mcCrowdFundingTargets {

    private String id;

    private String mcUserId;

    private int progress;

    private String sumMoney;

    public String getSumCount() {
        return sumCount;
    }

    public void setSumCount(String sumCount) {
        this.sumCount = sumCount;
    }

    private String sumCount;

    private String targetMoney;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    private String headImgUrl;


    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String coverPath;

    private String name;

    private String targetId;

    private String userId;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setMcUserId(String mcUserId){
        this.mcUserId = mcUserId;
    }
    public String getMcUserId(){
        return this.mcUserId;
    }
    public void setProgress(int progress){
        this.progress = progress;
    }
    public int getProgress(){
        return this.progress;
    }
    public void setSumMoney(String sumMoney){
        this.sumMoney = sumMoney;
    }
    public String getSumMoney(){
        return this.sumMoney;
    }
    public void setTargetMoney(String targetMoney){
        this.targetMoney = targetMoney;
    }
    public String getTargetMoney(){
        return this.targetMoney;
    }
}
