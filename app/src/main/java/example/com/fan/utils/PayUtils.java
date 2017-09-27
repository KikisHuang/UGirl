package example.com.fan.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.MyAppcation;
import example.com.fan.activity.InitActivity;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.AliPayBean;
import example.com.fan.utils.pay.ali.alipayTool;
import example.com.fan.utils.pay.ali.wechat.WeiXinTool;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.MzFinal.AlisOfWecha;
import static example.com.fan.utils.MzFinal.getAPPID;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/7/6.
 */
public class PayUtils {

    private static final String TAG = getTAG(PayUtils.class);

    /**
     * 支付通用接口
     *
     * @param context
     * @param PayId    支付id;
     * @param url      支付路径;
     * @param Pay_Type 支付类型(0支付宝,1微信);
     * @param num      商品数量;
     */
    public static void PayNow(final Context context, String PayId, String url, final int Pay_Type, final int num) {
        String appid = "";
        //支付方式判断;
        if (AlisOfWecha(url))
            appid = MyAppcation.ALIID;
        else {
            if (getAPPID(MyAppcation.pkName))
                appid = MyAppcation.WECHATID;
            else
                appid = MzFinal.xiaoketang;
        }

        Log.i(TAG," appid ========"+appid);
        OkHttpUtils
                .post()
                .url(MzFinal.URl + url)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.APPID, appid)
                .addParams(MzFinal.ID, PayId)
                .addParams(MzFinal.COUNT, String.valueOf(num))
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
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                switch (Pay_Type) {
                                    case 0:
                                        AliPayBean ab = new Gson().fromJson(String.valueOf(ob), AliPayBean.class);
                                        alipayTool atool = new alipayTool();
                                        atool.setalipayResultListener(((InitActivity) context));
                                        atool.pay((Activity) context, ab.getInfo());
                                        break;
                                    case 1:
                                        WeiXinTool wtool = new WeiXinTool(context);
                                        wtool.setMap(ob);
                                        wtool.sendPayReq();
                                        break;
                                }

                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
