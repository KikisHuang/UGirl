package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.activity.MainActivity;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goHelpPage;
import static example.com.fan.utils.IntentUtils.goLoginPage;
import static example.com.fan.utils.IntentUtils.goOverPayPage;
import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.IntentUtils.goPersonInfoPage;
import static example.com.fan.utils.IntentUtils.goSettingPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getSex;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * Page页面侧边菜单Popupwindos实现;
 */
public class SlidePopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(SlidePopupWindow.class);
    private static TextView user_name;
    private static ImageView user_icon;
    private static LinearLayout login_ll, layout_1, layout_3, layout_5, layout_6, layout_7;
    private static PopupWindow popupWindow;
    private static UserInfoBean info = null;
    private Context mContext;

    public SlidePopupWindow(Context context) {
        this.mContext = context;
    }


    public void ScreenPopupWindow() {
        if (popupWindow == null) {

            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.slide_popu_layout, null);
            init(contentView);
            click();
            getData(mContext);
            int width = DeviceUtils.getWindowWidth(mContext) * 7 / 10;
            popupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
            backgroundAlpha(0.4f, mContext);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f, mContext);
                    popupWindow.dismiss();
                    popupWindow = null;
                    info = null;
                    user_name = null;
                    user_icon = null;
                    login_ll = null;
                    layout_1 = null;
                    layout_3 = null;
                    layout_5 = null;
                    layout_6 = null;
                    layout_7 = null;
                    if (MainActivity.materialMenuView != null)
                        MainActivity.materialMenuView.animateIconState(MaterialMenuDrawable.IconState.BURGER);
                }
            });

            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));

            // 设置好参数之后再show
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            popupWindow.showAsDropDown(LayoutInflater.from(mContext).inflate(R.layout.activity_main, null));
        }
    }

    private static void getData(final Context mContext) {

        if (!LoginStatusQuery()) {
            user_name.setText("立即登录");
        } else {

            /**
             * 获取个人信息;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    JSONObject ob = getJsonOb(response);
                                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                    info = ub;
                                    MyAppcation.UserIcon = ub.getHeadImgUrl();
                                    MyAppcation.myInvitationCode = ub.getMyInvitationCode();
                                    try {
                                        if (ub.getHeadImgUrl() == null)
                                            Glide.with(mContext.getApplicationContext()).load(R.mipmap.test_icon).apply(getRequestOptions(false, 350, 350,true))
                                                    .into(user_icon);
                                        else
                                            Glide.with(mContext.getApplicationContext()).load(ub.getHeadImgUrl()).apply(getRequestOptions(false, 350, 350,true)).into(user_icon);
                                    } catch (Exception e) {
                                        Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                    }
                                    user_name.setText(info.getName());
                                } else
                                    ToastUtil.ToastErrorMsg(mContext, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });

        }
    }

    private void click() {
        login_ll.setOnClickListener(this);

        layout_1.setOnClickListener(this);
        layout_3.setOnClickListener(this);
        layout_5.setOnClickListener(this);
        layout_6.setOnClickListener(this);
        layout_7.setOnClickListener(this);
    }


    private static void init(View contentView) {
        login_ll = (LinearLayout) contentView.findViewById(R.id.login_ll);
        user_icon = (ImageView) contentView.findViewById(R.id.user_icon);
        user_name = (TextView) contentView.findViewById(R.id.user_name);

        layout_1 = (LinearLayout) contentView.findViewById(R.id.layout_1);
        layout_3 = (LinearLayout) contentView.findViewById(R.id.layout_3);
        layout_5 = (LinearLayout) contentView.findViewById(R.id.layout_5);
        layout_6 = (LinearLayout) contentView.findViewById(R.id.layout_6);
        layout_7 = (LinearLayout) contentView.findViewById(R.id.layout_7);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public static void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_ll:
                if (info != null)
                    login();
                break;
            case R.id.layout_1:
                pay();
                break;
            case R.id.layout_3:
                overpay();
                break;
            case R.id.layout_5:
                share();
                break;
            case R.id.layout_6:
                help();
                break;
            case R.id.layout_7:
                setting();
                break;
        }
    }

    /**
     * 分享
     */
    private void share() {
        if (LoginStatusQuery()) {
            if (!cleanNull(MyAppcation.myInvitationCode)) {
                SharePopupWindow sp = new SharePopupWindow(mContext, MyAppcation.myInvitationCode);
                sp.ScreenPopupWindow(LayoutInflater.from(mContext).inflate(R.layout.activity_main, null));
            }
        } else
            Login(mContext);
    }

    /**
     * 设置
     */
    private void setting() {
        goSettingPage(mContext);
    }

    /**
     * 任务
     */
    private void help() {
        if (LoginStatusQuery()) {
            goHelpPage(mContext);
        } else
            Login(mContext);
    }

    /**
     * 已购买
     */
    private void overpay() {
        if (LoginStatusQuery()) {
            goOverPayPage(mContext);
        } else
            Login(mContext);
    }

    /**
     * 支付
     */
    private void pay() {
        if (LoginStatusQuery())
            goPayPage(mContext);
        else
            Login(mContext);
    }

    /**
     * 登录
     */
    private void login() {
        if (!LoginStatusQuery()) {
            goLoginPage(mContext);
//            backgroundAlpha(1f, mContext);

        } else {
            if (info != null) {
                goPersonInfoPage(mContext, info.getHeadImgUrl(), info.getName(), String.valueOf(getSex(info.getSex())), info.getWx());
//                backgroundAlpha(1f, mContext);
            }
        }
    }
}
