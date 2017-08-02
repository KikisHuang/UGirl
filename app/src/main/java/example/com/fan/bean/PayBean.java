package example.com.fan.bean;

import java.util.List;

/**
 * Created by lian on 2017/6/24.
 */
public class PayBean {

    private String id;

    private String name;

    private String info;

    private int monthCount;

    private String iconUrl;

    private double price;

    private int type;

    private List<McVipDescribes> mcVipDescribes ;

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
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }
    public void setMonthCount(int monthCount){
        this.monthCount = monthCount;
    }
    public int getMonthCount(){
        return this.monthCount;
    }
    public void setIconUrl(String iconUrl){
        this.iconUrl = iconUrl;
    }
    public String getIconUrl(){
        return this.iconUrl;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setMcVipDescribes(List<McVipDescribes> mcVipDescribes){
        this.mcVipDescribes = mcVipDescribes;
    }
    public List<McVipDescribes> getMcVipDescribes(){
        return this.mcVipDescribes;
    }
}
