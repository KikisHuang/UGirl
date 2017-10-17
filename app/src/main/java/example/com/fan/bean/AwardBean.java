package example.com.fan.bean;

import android.view.View;

/**
 * Created by lian on 2017/10/14.
 */
public class AwardBean {
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private String path;
    private View view;

}
