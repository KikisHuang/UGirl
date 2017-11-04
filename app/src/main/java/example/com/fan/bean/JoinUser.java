package example.com.fan.bean;

/**
 * Created by lian on 2017/6/29.
 */
public class JoinUser {
    private String headImgUrl;

    private String id;

    private String name;
    private boolean isFolllw;

    public boolean getIsFolllw() {
        return isFolllw;
    }

    public void setIsFolllw(boolean isFolllw) {
        this.isFolllw = isFolllw;
    }

    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }
    public String getHeadImgUrl(){
        return this.headImgUrl;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
