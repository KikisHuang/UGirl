package example.com.fan.bean;

/**
 * Created by lian on 2017/6/29.
 */
public class McSettingPublishType {
    private String createTime;

    private String id;

    private int typeFlag;

    private String typeName;

    private int value;

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTypeFlag(int typeFlag){
        this.typeFlag = typeFlag;
    }
    public int getTypeFlag(){
        return this.typeFlag;
    }
    public void setTypeName(String typeName){
        this.typeName = typeName;
    }
    public String getTypeName(){
        return this.typeName;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

}
