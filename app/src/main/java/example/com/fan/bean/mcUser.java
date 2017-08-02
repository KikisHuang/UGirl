package example.com.fan.bean;

/**
 * Created by lian on 2017/6/29.
 */
public class mcUser {
    private String birthday;

    private int follwCount;

    private String headImgUrl;

    private String id;

    private String name;

    private String residentCity;

    private String residentProvince;

    private int sex;

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public void setFollwCount(int follwCount){
        this.follwCount = follwCount;
    }
    public int getFollwCount(){
        return this.follwCount;
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
    public void setResidentCity(String residentCity){
        this.residentCity = residentCity;
    }
    public String getResidentCity(){
        return this.residentCity;
    }
    public void setResidentProvince(String residentProvince){
        this.residentProvince = residentProvince;
    }
    public String getResidentProvince(){
        return this.residentProvince;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }

}
