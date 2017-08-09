package example.com.fan.bean;

import android.support.v4.app.Fragment;

/**
 * Created by lian on 2017/8/8.
 */
public class TestBean {
    public String mTitle;
    public Fragment mClazz;

    public TestBean(String title, Fragment clazz) {
        mTitle = title;
        mClazz = clazz;
    }
}
