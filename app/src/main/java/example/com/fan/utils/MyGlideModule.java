package example.com.fan.utils;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {

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

}
