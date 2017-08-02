package example.com.fan.utils.photo;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by ${Kikis} on 2016-07-27.
 */

public class LruCacheUtils {
    private static final String TAG = getTAG(LruCacheUtils.class);
    //获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
    public static int MAXMEMONRY = (int) Runtime.getRuntime().maxMemory();
//    //应用程序已获得内存
//    public static long totalMemory = ((int) Runtime.getRuntime().totalMemory())/1024;
//    //应用程序已获得内存中未使用内存
//    public static long freeMemory = ((int) Runtime.getRuntime().freeMemory())/1024;


    public static LruCache<String, Bitmap> mMemoryCache;

    public LruCacheUtils() {

        if (mMemoryCache == null)
            //给LruCache分配1/8的内存
            mMemoryCache = new LruCache<String, Bitmap>(MAXMEMONRY / 8) {


                //必须重写此方法，来测量Bitmap的大小
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }

                @Override
                protected void entryRemoved(boolean evicted, String key,
                                            Bitmap oldValue, Bitmap newValue) {
                    Log.v("tag", "hard cache is full , push to soft cache");

                }
            };

    }



    /**
 * 下面的方法分别是清空缓存、添加图片到缓存、从缓存中取得图片、从缓存中移除。
 移除和清除缓存是必须要做的事，因为图片缓存处理不当就会报内存溢出，所以一定要引起注意。
 */

    public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                Log.d("CacheUtils",
                        "mMemoryCache.size() " + mMemoryCache.size());
                mMemoryCache.evictAll();
                Log.d("CacheUtils", "mMemoryCache.size()" + mMemoryCache.size());
            }
            mMemoryCache = null;
        }else{

            return;
        }
    }
    /**
     * 添加Bitmap缓存
     */
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            if (key != null && bitmap != null)
                mMemoryCache.put(key, bitmap);
        }else{

            Log.w(TAG, "the res is aready exits");
        }
    }

    /**
     * 从缓存中获得图片
     */

    public static  Bitmap getBitmapFromMemCache(String key) {
        Bitmap bm = mMemoryCache.get(key);
        if (key != null) {
            return bm;
        }
        return null;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public static void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }

}
