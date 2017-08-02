package example.com.fan.bean;

import android.view.View;

import java.util.List;

/**
 * Created by lian on 2017/6/20.
 * 商城bean（未完成）;
 */
public class StoreBean {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    String id;
    int type;
    int uid;
    private List<View> view;

    public List<View> getView() {
        return view;
    }

    public void setView(List<View> view) {
        this.view = view;
    }

    public mcOfficialSellShoppingMall getMcOfficialSellShoppingMall() {
        return mcOfficialSellShoppingMall;
    }

    public void setMcOfficialSellShoppingMall(mcOfficialSellShoppingMall mcOfficialSellShoppingMall) {
        this.mcOfficialSellShoppingMall = mcOfficialSellShoppingMall;
    }

    public mcCrowdFundingShoppingMall getMcCrowdFundingShoppingMall() {
        return mcCrowdFundingShoppingMall;
    }

    public void setMcCrowdFundingShoppingMall(mcCrowdFundingShoppingMall mcCrowdFundingShoppingMall) {
        this.mcCrowdFundingShoppingMall = mcCrowdFundingShoppingMall;
    }

    mcOfficialSellShoppingMall mcOfficialSellShoppingMall;
    mcCrowdFundingShoppingMall mcCrowdFundingShoppingMall;
}
