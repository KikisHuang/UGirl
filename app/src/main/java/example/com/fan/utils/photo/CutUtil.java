package example.com.fan.utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import example.com.fan.activity.IconCutActivity;

import static example.com.fan.utils.DeviceUtils.getAutoFileOrFilesSize;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/4/10.
 */
public class CutUtil {

    private static final String TAG = getTAG(CutUtil.class);

    public void CutBitmap(String urlSTR, Activity ac, String flag) {
        Log.i(TAG, "未压缩的图片大小 ===" + getAutoFileOrFilesSize(urlSTR));
        Bitmap bitmap = CompressIamge.getBitmapFromUri2(urlSTR, ac);
        LruCacheUtils lcu = new LruCacheUtils();
        lcu.addBitmapToMemoryCache(urlSTR, bitmap);

        Intent intent = new Intent(ac,IconCutActivity.class);
        intent.putExtra("Bitmap_key", urlSTR);
        intent.putExtra("flag", flag);
        ac.startActivity(intent);
        Log.i(TAG,"URL LOG ==========="+urlSTR);

    }
}
