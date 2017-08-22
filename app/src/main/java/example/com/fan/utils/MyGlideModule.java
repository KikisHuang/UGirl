package example.com.fan.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class MyGlideModule implements GlideModule {

    private static final String TAG = getTAG(MyGlideModule.class);

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
 /*       //定义缓存大小为100M
        int diskCacheSize = 100 * 1024 * 1024;
        //自定义缓存 路径 和 缓存大小
        String diskCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/UGirl-GlideCache";
        //自定义磁盘缓存：这种缓存存在SD卡上，所有的应用都可以访问到
        builder.setDiskCache(new DiskLruCacheFactory(diskCachePath, diskCacheSize));*/
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
