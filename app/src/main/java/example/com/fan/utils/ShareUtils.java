package example.com.fan.utils;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
}
