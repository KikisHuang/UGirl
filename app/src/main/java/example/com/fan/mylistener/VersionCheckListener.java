package example.com.fan.mylistener;

import example.com.fan.bean.VersionBean;

/**
 * Created by lian on 2017/7/7.
 */
public interface VersionCheckListener {
    void onVersion(VersionBean vb);
    void onFail();
}
