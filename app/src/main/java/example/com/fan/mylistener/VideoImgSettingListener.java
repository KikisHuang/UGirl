package example.com.fan.mylistener;

/**
 * Created by lian on 2017/10/13.
 */
public interface VideoImgSettingListener {
    void onSelect(int pos, String path);

    void onCancle(int pos, String path);

    void onChange();
}
