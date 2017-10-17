package example.com.fan.view.Popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import example.com.fan.R;
import example.com.fan.activity.PlayerVideoActivity;
import example.com.fan.activity.PrivatePhotoActivity;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.fragment.son.PictureSlideFragment2;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 充值页面2,popupWindows实现;
 */
public class PaytwoPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(PaytwoPopupWindow.class);
    private static TextView info_tv, pay_tv, cancle_tv;
    private Context context;
    private View v;
    private View view;
    private String price = "";
    private PopupWindow popupWindow;
    private int flag;
    private String id = "";
    //1私密视频,0私照;
    private int tag;

    public PaytwoPopupWindow(Context context, String price, String id, int tag) {
        this.price = price;
        this.context = context;
        this.id = id;
        this.tag = tag;
        this.flag = 99;
    }

    public void ScreenPopupWindow(View vv) {
        if (popupWindow == null) {
            this.v = vv;
            view = LayoutInflater.from(context).inflate(R.layout.pay_two_pp_layout, null);
            getBalance();
            init();
            click();
            int width = DeviceUtils.getWindowWidth(context) * 7 / 10;
            popupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow.dismiss();
                    popupWindow = null;
                    v = null;
                    pay_tv = null;
                    info_tv = null;
                    cancle_tv = null;
                }
            });
            // 设置好参数之后再show
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    private void getBalance() {
        /**
         * 获取余额;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
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
                                JSONObject ob = getJsonOb(response);
                                UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                if (ub.getBalance() >= Double.valueOf(price)) {
                                    pay_tv.setText("确定");
                                    info_tv.setText("查看需要" + price + "币," + "您还剩余" + ub.getBalance() + "尤币");
                                    flag = 1;
                                } else {
                                    pay_tv.setText("充值");
                                    info_tv.setText("查看需要" + price + "币," + "尤币不足请充值");
                                    flag = 0;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void click() {
        pay_tv.setOnClickListener(this);
        cancle_tv.setOnClickListener(this);
    }

    private void init() {
        info_tv = (TextView) view.findViewById(R.id.info_tv);
        pay_tv = (TextView) view.findViewById(R.id.pay_tv);
        cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_tv:
                if (flag == 0) {
                    goPayPage(context);
                    popupWindow.dismiss();
                }
                if (flag == 1)
                    BuyVideo();
                break;
            case R.id.cancle_tv:
                popupWindow.dismiss();
                break;
        }
    }

    private void BuyVideo() {
        String u = "";
        if (tag == 1)
            u = MzFinal.PAYPRIVATEVIDEO;
        if (tag == 0)
            u = MzFinal.PAYPRIVATEPHOTO;
        /**
         *购买私密视频、私密照片;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + u)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
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
                                if (PlayerVideoActivity.infoListener != null && tag == 1)
                                    PlayerVideoActivity.infoListener.onUpDataUserInfo();

                                if (PictureSlideFragment2.PayListener != null && PrivatePhotoActivity.tlistener != null && tag == 0) {
                                    MzFinal.isPay = true;
                                    PictureSlideFragment2.PayListener.onPayRefresh();
                                }
                                ToastUtil.toast2_bottom(context, "购买成功！！");
                                popupWindow.dismiss();
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
