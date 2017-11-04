package example.com.fan.mylistener;

import android.graphics.Bitmap;

/**
 * Created by lian on 2017/11/2.
 */
public interface ShareBitmapListener {
    void onQRCode(Bitmap bm);
    void onError(Exception e);
}
