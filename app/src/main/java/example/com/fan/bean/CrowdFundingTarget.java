package example.com.fan.bean;

/**
 * Created by lian on 2017/7/3.
 */
public class CrowdFundingTarget {
    private String id;

    private String mcUserId;

    private String name;

    private int progress;

    private int sumCount;

    private double sumMoney;

    private double targetMoney;

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
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setProgress(int progress){
        this.progress = progress;
    }
    public int getProgress(){
        return this.progress;
    }
    public void setSumCount(int sumCount){
        this.sumCount = sumCount;
    }
    public int getSumCount(){
        return this.sumCount;
    }
    public void setSumMoney(double sumMoney){
        this.sumMoney = sumMoney;
    }
    public double getSumMoney(){
        return this.sumMoney;
    }
    public void setTargetMoney(double targetMoney){
        this.targetMoney = targetMoney;
    }
    public double getTargetMoney(){
        return this.targetMoney;
    }
}
