package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.bean.PayBean;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 充值页面2,popupWindows实现;
 */
public class InterceptPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(InterceptPopupWindow.class);
    private Context context;
    private View v;
    private View view;
    private PopupWindow popupWindow;
    private Button pay_bt;
    private AliWechatPopupWindow aw;
    private TextView price_tv;
    private String payid = "";

    public InterceptPopupWindow(Context context) {
        this.context = context;
    }

    public synchronized void ScreenPopupWindow(View vv) {
        if (popupWindow == null) {
            this.v = vv;
            view = LayoutInflater.from(context).inflate(R.layout.intercept_layout, null);
            init();
            getData();
            click();
            backgroundAlpha(0.3f, context);
            int width = (int) (DeviceUtils.getWindowWidth(context) * 8.5 / 10);
            popupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(false);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (MyAppcation.VipFlag) {
                        backgroundAlpha(1.0f, context);
                        popupWindow.dismiss();
                        popupWindow = null;
                        price_tv = null;
                        pay_bt = null;
                        view = null;
                        aw = null;
                        v = null;
                    }
                }
            });
            // 设置好参数之后再show
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    public void onMyDismiss() {
        if (popupWindow != null) {
            Log.i(TAG,"onMyDismiss");
            backgroundAlpha(1.0f, context);
            popupWindow.dismiss();
            popupWindow = null;
            price_tv = null;
            pay_bt = null;
            view = null;
            aw = null;
            v = null;
        }
    }

    private void getData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETVIPBYTYPE)
                .addParams("type", String.valueOf(1))
                .tag(this)
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
                                String s = getJsonSring(response);
                                JSONArray ar = new JSONArray(s);

                                PayBean pb = new Gson().fromJson(String.valueOf(ar.getJSONObject(2)), PayBean.class);
                                price_tv.setText("青铜会员价格：￥" + pb.getPrice());
                                payid = pb.getId();
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void click() {
        pay_bt.setOnClickListener(this);
    }

    private void init() {
        pay_bt = (Button) view.findViewById(R.id.pay_bt);
        price_tv = (TextView) view.findViewById(R.id.price_tv);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();

        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_bt:
                if (!payid.isEmpty()) {
                    if (LoginStatusQuery()) {
                        aw = new AliWechatPopupWindow(context, new String[]{MzFinal.ALIPAYVIP, MzFinal.WXPAYVIP});
                        aw.ScreenPopupWindow(LayoutInflater.from(context).inflate(R.layout.store_fragment2, null), payid);
                    } else
                        Login(context);
                }

                break;
        }
    }

}
