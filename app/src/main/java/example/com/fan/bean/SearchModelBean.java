package example.com.fan.bean;

/**
 * Created by lian on 2017/7/3.
 */
public class SearchModelBean {
    private String headImgUrl;

    private String id;

    private String name;

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
