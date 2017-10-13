package example.com.fan.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.mylistener.ShareListener;
import okhttp3.Call;

/**
 * Created by lian on 2017/5/25.
 */
public class ShareUtils {
    /**
     * 分享通用方法;
     *
     * @param context 上下文
     * @param id      模特id
     * @param name    模特名称
     * @param info    分享的内容
     * @param id2     专辑id
     */
    public static void ShareApp(final Context context, String id, final String name, final String info, String id2) {
        /**
         * 获取分享路径;
         */

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETSHAREURL)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("modelId", id)
                .addParams(MzFinal.ID, id2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(context, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            new ShareAction((Activity) context).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                                    .withTitle(name)
                                    .withText(info)
                                    .withMedia(new UMImage(context, R.mipmap.logo))
                                    .withTargetUrl(response)
                                    .open();
                        } catch (Exception e) {

                        }
                    }
                });

    }

    /**
     * 获取分享路径分享通用方法;
     *
     * @param context  上下文
     * @param id       模特id
     * @param id2      专辑id
     * @param listener 获取路径回调接口
     */
    public static void getShareUrl(final Context context, String id, String id2, final ShareListener listener) {
        if (listener != null) {
            /**
             * 获取分享路径;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETSHAREURL)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams("modelId", id)
                    .addParams(MzFinal.ID, id2)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(context, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                if (response.length() > 0)
                                    listener.onSucceed(response);
                                else
                                    listener.onFail();
                            } catch (Exception e) {

                            }
                        }
                    });
        }
    }

    /**
     * 系统分享通用方法;
     *
     * @param context  上下文
     * @param imgurl   图片url
     * @param listener 获取路径回调接口
     *
     *
    String path = id;
    Intent imageIntent = new Intent(Intent.ACTION_SEND);
    imageIntent.setType("image/jpeg");
    imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
    startActivity(Intent.createChooser(imageIntent, "分享"));
     */
    public static void getSystemShare(final Context context, String imgurl, final ShareListener listener) {
     /*   if (listener != null) {
            RequestOptions options = new RequestOptions();
            Glide.with(context).asBitmap().asbyte.load(imgurl).into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] resource, Transition<? super byte[]> transition) {
                    try {
                        savaFileToSD(System.currentTimeMillis() + ".jpeg", resource, listener);
                    } catch (Exception e) {
                        listener.onFail();
                        e.printStackTrace();
                    }
                }
            });
        }*/
    }
    //往SD卡写入文件的方法
    public static void savaFileToSD(String filename, byte[] bytes, ShareListener listener) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/YouGridShare";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            listener.onSucceed(filename);
        } else
            listener.onFail();
    }
}
