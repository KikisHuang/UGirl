package example.com.fan.bean;

/**
 * Created by lian on 2017/7/3.
 */
public class CrowdPayDetailBean {
    private CrowdFundingTarget crowdFundingTarget;

    private String crowdFundingTargetId;

    private String id;

    private String info;

    private double price;

    private String title;

    public void setCrowdFundingTarget(CrowdFundingTarget crowdFundingTarget){
        this.crowdFundingTarget = crowdFundingTarget;
    }
    public CrowdFundingTarget getCrowdFundingTarget(){
        return this.crowdFundingTarget;
    }
    public void setCrowdFundingTargetId(String crowdFundingTargetId){
        this.crowdFundingTargetId = crowdFundingTargetId;
    }
    public String getCrowdFundingTargetId(){
        return this.crowdFundingTargetId;
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
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

}
