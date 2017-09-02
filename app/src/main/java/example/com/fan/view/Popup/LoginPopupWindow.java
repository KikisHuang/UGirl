package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.activity.BaseActivity;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.fragment.MyFragment;
import example.com.fan.mylistener.AccreditListener;
import example.com.fan.mylistener.EditChangedListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.base.sign.save.SPreferences.getLoginWay;
import static example.com.fan.base.sign.save.SPreferences.saveLoginWay;
import static example.com.fan.utils.IntentUtils.goRegisterPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.SaveLoginWay;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.tpartyLoginUtils.getUMAuthListener;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;


/**
 * Created by lian on 2017/3/29.
 * 登录页面,popupWindows实现;
 */
public class LoginPopupWindow implements View.OnClickListener, AccreditListener {
    private static final String TAG = getTAG(LoginPopupWindow.class);
    private static EditText pass_ed, user_ed;
    private static LinearLayout userid_ll, userpass_ll, wechat_login, qq_login;
    private static ImageView clear_img, user_icon, pass_icon;
    private static TextView login_bt, code_tv, register_tv, login_tag;
    private Context context;
    private boolean sflag;
    private SHARE_MEDIA platform;
    private UMShareAPI mShareAPI;
    private int login_flag;
    private Handler handler;
    private Runnable pRunnable;

    private int page = 60;
    private static AccreditListener listener;

    public LoginPopupWindow(Context context) {
        this.context = context;
    }

    public static PopupWindow popupWindow;

    public void ScreenPopupWindow(View view) {
        if (popupWindow == null && handler == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(context).inflate(R.layout.login_popu_layout, null);
            int width = DeviceUtils.getWindowWidth(context) * 8 / 10;
            int h = (int) (DeviceUtils.getWindowHeight(context) * 8.5 / 10);
            popupWindow = new PopupWindow(contentView, width, h);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);
            init(contentView);
            click();
            hand();
            backgroundAlpha(0.4f);
            popupWindow.setFocusable(true);
//            popupWindow.setOutsideTouchable(true);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                    if (handler != null) {
                        handler.removeCallbacks(pRunnable);
                        handler = null;
                        sflag = false;
                    }
                    popupWindow.dismiss();
                    listener = null;
                    popupWindow = null;
                    pass_ed = null;
                    user_ed = null;
                    userid_ll = null;
                    userpass_ll = null;
                    wechat_login = null;
                    qq_login = null;
                    clear_img = null;
                    user_icon = null;
                    pass_icon = null;
                    qq_login = null;
                    login_bt = null;
                    code_tv = null;
                    register_tv = null;
                    login_tag = null;
                    mShareAPI = null;
                    platform = null;
                }
            });
            // 设置好参数之后再show
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }

    private void hand() {
        try {

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    try {
                        switch (msg.what) {
                            case 0:
                                code_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gray_corner2));
                                code_tv.setText("重发(" + page + "s" + ")");
                                if (page <= 0) {
                                    sflag = false;
                                    code_tv.setEnabled(true);
                                    code_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cherry_corner3));
                                    code_tv.setText("获取验证码");
                                    page = 60;
                                } else {
                                    code_tv.setEnabled(false);
                                    page--;
                                }


                                break;
                        }
                    } catch (Exception e) {

                    }
                }
            };

        } catch (Exception e) {

        }
    }

    private void click() {
        clear_img.setOnClickListener(this);
        login_bt.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);

        pass_ed.setOnClickListener(this);
        user_ed.setOnClickListener(this);
        register_tv.setOnClickListener(this);
        code_tv.setOnClickListener(this);
        user_ed.addTextChangedListener(new EditChangedListener(clear_img));

        user_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    ChangeLayoutBackdrop(0);
            }
        });
        pass_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    ChangeLayoutBackdrop(1);
            }
        });
    }

    private void init(View contentView) {
        listener = this;
        platform = ((BaseActivity) context).platform;
        mShareAPI = ((BaseActivity) context).mShareAPI;

        pass_ed = (EditText) contentView.findViewById(R.id.pass_ed);
        user_ed = (EditText) contentView.findViewById(R.id.user_ed);
        userid_ll = (LinearLayout) contentView.findViewById(R.id.userid_ll);
        userpass_ll = (LinearLayout) contentView.findViewById(R.id.userpass_ll);
        wechat_login = (LinearLayout) contentView.findViewById(R.id.wechat_login);
        qq_login = (LinearLayout) contentView.findViewById(R.id.qq_login);
        clear_img = (ImageView) contentView.findViewById(R.id.clear_img);

        register_tv = (TextView) contentView.findViewById(R.id.register_tv);
        login_tag = (TextView) contentView.findViewById(R.id.login_tag);

        login_bt = (TextView) contentView.findViewById(R.id.login_bt);
        code_tv = (TextView) contentView.findViewById(R.id.code_tv);

        user_icon = (ImageView) contentView.findViewById(R.id.user_icon);
        pass_icon = (ImageView) contentView.findViewById(R.id.pass_icon);

        if (getLoginWay() == null || getLoginWay().isEmpty())
            login_tag.setText("");
        else
            login_tag.setText(getLoginWay());
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userid_ll:
                ChangeLayoutBackdrop(0);
                break;
            case R.id.userpass_ll:
                ChangeLayoutBackdrop(1);
                break;
            case R.id.register_tv:
                goRegisterPage(context);
                break;
            case R.id.clear_img:
                user_ed.setText("");
                break;
            case R.id.login_bt:
                if (user_ed.getText().toString().length() == 11) {
                    if (pass_ed.getText().toString().length() == 0)
                        ToastUtil.toast2_bottom(context, "请获取验证码后再进行登录...");
                    else
                        Login(user_ed.getText().toString(), String.valueOf(pass_ed.getText().toString()));
                } else {
                    if (user_ed.getText().toString().length() != 11)
                        ToastUtil.toast2_bottom(context, "请输入正确的手机号!");
                }

                break;
            case R.id.qq_login:
                login_flag = 0;
                platform = SHARE_MEDIA.QQ;
                mShareAPI.isInstall((Activity) context, platform);
                mShareAPI.doOauthVerify((Activity) context, platform, getUMAuthListener(context, listener, MzFinal.QQ));

                break;
            case R.id.wechat_login:
                login_flag = 1;
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.isInstall((Activity) context, platform);
                mShareAPI.doOauthVerify((Activity) context, platform, getUMAuthListener(context, listener, MzFinal.WECHAT));

                break;
            case R.id.code_tv:
                if (user_ed.getText().toString().length() == 11) {
                    code_tv.setEnabled(false);
                    Log.i(TAG, "click...");
                    sflag = true;
                    pRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "倒计时...");
                            while (sflag) {
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };

                    new Thread(pRunnable).start();
                    getPhoneCode();

                } else {
                    ToastUtil.toast2_bottom(context, "请输入正确的手机号!");
                }
                break;

        }
    }

    private void getPhoneCode() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CREATECODE)
                .addParams("account", user_ed.getText().toString())
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
                                ToastUtil.toast2_bottom(context, "已发送验证码!");
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void Login(String use, String pass) {
        Map<String, String> map = new HashMap<>();

        map.put("account", use);
        map.put("code", pass);
        map.put("channelCode", MyAppcation.CHANNEL);

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.DEFAULT)
                .addParams("account", use)
                .addParams("code", pass)
                .addParams("channelCode", MyAppcation.CHANNEL)
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
                                SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                Log.i(TAG, "token Log======" + new JSONObject(response).optString("data"));
                                ToastUtil.toast2_bottom(context, "登录成功！");
                                user_ed.setText("");
                                pass_ed.setText("");
                                saveLoginWay(getRouString(R.string.phone_login));
                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();

                                popupWindow.dismiss();
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void ChangeLayoutBackdrop(int tag) {
        try {

            if (tag == 0) {
                userid_ll.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_edittext_focused));
                user_ed.setHintTextColor(context.getResources().getColor(R.color.white));
                user_icon.setImageResource(R.mipmap.user_icon);
                setFocu(user_ed);

                userpass_ll.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_edittext_normal));
                pass_ed.setHintTextColor(context.getResources().getColor(R.color.gray3));
                pass_icon.setImageResource(R.mipmap.unpass_icon);

            }
            if (tag == 1) {
                userpass_ll.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_edittext_focused));
                pass_ed.setHintTextColor(context.getResources().getColor(R.color.white));
                pass_icon.setImageResource(R.mipmap.pass_icon);
                setFocu(pass_ed);

                userid_ll.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_edittext_normal));
                user_ed.setHintTextColor(context.getResources().getColor(R.color.gray3));
                user_icon.setImageResource(R.mipmap.unuser_icon);

            }
        } catch (Exception e) {
            Log.i(TAG, "ChangeLayoutBackdrop 空指针... ");
            popupWindow.dismiss();
        }
    }

    private void setFocu(EditText ed1) {
        ed1.setFocusable(true);
        ed1.setFocusableInTouchMode(true);
        ed1.requestFocus();
    }

    @Override
    public void onSucceed(String accessToken, String openid, String url) {
//        mShareAPI.getPlatformInfo(((Activity) context), platform, getUMDataListener(context));

        Show(context, "登录中", true, null);
        OkHttpUtils
                .get()
                .url(MzFinal.URl + url)
                .addParams("openId", openid)
                .addParams("accessToken", accessToken)
                .addParams("channelCode", MyAppcation.CHANNEL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(context, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                ToastUtil.toast2_bottom(context, "登录成功！");
                                user_ed.setText("");
                                pass_ed.setText("");
                                SaveLoginWay(login_flag);
                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();
                                popupWindow.dismiss();
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                            Cancle();
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onFail() {

    }
}
