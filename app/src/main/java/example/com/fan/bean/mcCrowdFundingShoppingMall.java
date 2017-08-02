package example.com.fan.bean;

import java.util.List;

/**
 * Created by lian on 2017/6/20.
 *  商城众筹商品bean;
 */
public class mcCrowdFundingShoppingMall {

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    String coverPath;
    String id;
    int sumCount;
    double sumMoney;
    String title;
    int type;

    public List<example.com.fan.bean.mcCrowdFundingTargets> getMcCrowdFundingTargets() {
        return mcCrowdFundingTargets;
    }

    public void setMcCrowdFundingTargets(List<example.com.fan.bean.mcCrowdFundingTargets> mcCrowdFundingTargets) {
        this.mcCrowdFundingTargets = mcCrowdFundingTargets;
    }

    List<mcCrowdFundingTargets> mcCrowdFundingTargets;
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    String endTime;
    String startTime;

}
