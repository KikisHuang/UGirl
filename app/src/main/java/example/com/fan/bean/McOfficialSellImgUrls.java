package example.com.fan.bean;

import android.view.View;

/**
 * Created by lian on 2017/6/23.
 */
public class McOfficialSellImgUrls {
    private String id;

    private String path;

    private int uid;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private View view;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return this.uid;
    }

}
