package example.com.fan.bean;

import android.view.View;

import java.util.List;

/**
 * Created by lian on 2017/6/20.
 */
public class GalleryBean {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<example.com.fan.bean.mcPublishRecords> getMcPublishRecords() {
        return mcPublishRecords;
    }

    public void setMcPublishRecords(List<example.com.fan.bean.mcPublishRecords> mcPublishRecords) {
        this.mcPublishRecords = mcPublishRecords;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    int id;
    List<mcPublishRecords> mcPublishRecords;
    String typeName;
    View view;
}
