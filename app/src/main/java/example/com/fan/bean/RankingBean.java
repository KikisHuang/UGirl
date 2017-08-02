package example.com.fan.bean;

/**
 * Created by lian on 2017/5/22.
 */
public class RankingBean {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getFollwCount() {
        return follwCount;
    }

    public void setFollwCount(String follwCount) {
        this.follwCount = follwCount;
    }

    String headImgUrl;
    String name;
    String follwCount;
    String id;
}
