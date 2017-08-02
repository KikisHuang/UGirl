package example.com.fan.mylistener;

import android.graphics.Bitmap;

/**
 * Created by lian on 2017/6/12.
 */
public interface CutPhotoListener {
    void onSucceed(String url, Bitmap bitmap);
    void onFail();

}
