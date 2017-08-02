package example.com.fan.bean;

/**
 * Created by lian on 2017/6/28.
 */
public class ModeInfoBean {
    private String coverPath;
    private boolean isFolllw;

    public boolean isFolllw() {
        return isFolllw;
    }

    public void setFolllw(boolean folllw) {
        isFolllw = folllw;
    }

    private int height;

    private int inMeasurement;

    private int lowerMeasurement;

    private String realName;

    private int uid;

    private int upperMeasurement;

    private String userId;

    private int weight;

    public example.com.fan.bean.mcUser getMcUser() {
        return mcUser;
    }

    public void setMcUser(example.com.fan.bean.mcUser mcUser) {
        this.mcUser = mcUser;
    }

    private mcUser mcUser;

    public void setCoverPath(String coverPath){
        this.coverPath = coverPath;
    }
    public String getCoverPath(){
        return this.coverPath;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }
    public void setInMeasurement(int inMeasurement){
        this.inMeasurement = inMeasurement;
    }
    public int getInMeasurement(){
        return this.inMeasurement;
    }
    public void setLowerMeasurement(int lowerMeasurement){
        this.lowerMeasurement = lowerMeasurement;
    }
    public int getLowerMeasurement(){
        return this.lowerMeasurement;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    public String getRealName(){
        return this.realName;
    }
    public void setUid(int uid){
        this.uid = uid;
    }
    public int getUid(){
        return this.uid;
    }
    public void setUpperMeasurement(int upperMeasurement){
        this.upperMeasurement = upperMeasurement;
    }
    public int getUpperMeasurement(){
        return this.upperMeasurement;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public int getWeight(){
        return this.weight;
    }


}
