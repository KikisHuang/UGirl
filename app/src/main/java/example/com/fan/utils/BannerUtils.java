package example.com.fan.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.view.Popup.ApplySuperUserPopupWindow;
import example.com.fan.view.Popup.SharePopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goBuyGoodsPage;
import static example.com.fan.utils.IntentUtils.goOutsidePage;
import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.IntentUtils.goSGamerPage;
import static example.com.fan.utils.IntentUtils.goStorePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/7/15.
 * Banner跳转通用方法;
 */
public class BannerUtils {
    private static final String TAG = getTAG(BannerUtils.class);

    /**
     * @param context
     * @param type
     * @param path
     * @param id      type -1 外链 调用返回值：httpUrl
     *                type  0 私照
     *                type  1 一口价
     *                type  -2 私密照片
     *                type  4 视频
     *                type  5 VR
     *                type  -3 私密视频
     *                type  100 商城
     *                type  1000 充值
     *                type  1100 申请超级玩家
     *                type  1200 分享
     */
    public static void goBannerPage(Context context, int type, String path, String id) {
        Log.i(TAG, "Banner Type ===" + type);
        if (LoginStatusQuery()) {
            if (type == -1)
                goOutsidePage(context, path, "活动");
            if (type == 0)
                goPhotoPage(context, id, 0);
            if (type == 1)
                goBuyGoodsPage(context, id);
            if (type == -2)
                goPrivatePhotoPage(context, id, 0);
            if (type == 4)
                goPlayerPage(context, id, 4);
            if (type == 5)
                goPlayerPage(context, id, 5);
            if (type == -3)
                goPlayerPage(context, id, -3);
            if (type == 100)
                goStorePage(context);
            if (type == 1000)
                goPayPage(context);
            if(type ==1100){
                if (LoginStatusQuery()) {
                    if (MzFinal.MODELFLAG)
                        goSGamerPage(context);
                    else
                        getAuditStatus(context);
                } else
                    Login(context);
            }
            if(type ==1200){
                if (LoginStatusQuery()) {
                    try {
                        //分享popup;
                        SharePopupWindow sp = new SharePopupWindow(context, MyAppcation.myInvitationCode);
                        sp.ScreenPopupWindow(LayoutInflater.from(context).inflate(R.layout.page_fragment, null));
                    } catch (Exception e) {

                    }
                } else
                    Login(context);
            }

        } else
            Login(context);
    }


    private static void getAuditStatus(final Context context) {
        /**
         * 审核状态判断;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CHECKAPPLY)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(context, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            int data = Integer.parseInt(getJsonSring(response));
                            if (data == 1) {
                                ApplySuperUserPopupWindow aps = new ApplySuperUserPopupWindow(context);
                                aps.ScreenPopupWindow();
                            } else if (data == 0)
                                ToastUtil.toast2_bottom(context, "正在审核中...");
                            else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }
}
