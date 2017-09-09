package example.com.fan.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.fragment.MyFragment;
import example.com.fan.mylistener.AccreditListener;
import example.com.fan.mylistener.EditChangedListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.base.sign.save.SPreferences.getLoginWay;
import static example.com.fan.base.sign.save.SPreferences.saveInViCode;
import static example.com.fan.base.sign.save.SPreferences.saveLoginWay;
import static example.com.fan.utils.IntentUtils.goRegisterPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.Finish;
import static example.com.fan.utils.SynUtils.SaveLoginWay;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.tpartyLoginUtils.getUMAuthListener;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/6/8.
 * 登录页面;
 */
public class LoginActivity extends InitActivity implements View.OnClickListener, AccreditListener {
    private static final String TAG = getTAG(LoginActivity.class);
    private TextView login_bt, code_tv, register_tv, login_tag;
    private EditText pass_ed, user_ed;
    private LinearLayout userid_ll, userpass_ll, wechat_login, qq_login;
    private ImageView clear_img, user_icon, pass_icon, home_page_finish;
    private SHARE_MEDIA platform;
    private UMShareAPI mShareAPI;
    private AccreditListener listener;
    private boolean sflag;
    private Handler handler;
    private int page = 60;
    private int login_flag;
    private Runnable regisRunnable;

    private void hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        code_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_corner2));
                        code_tv.setText("重发(" + page + "s" + ")");
                        code_tv.setTextColor(getRouColors(R.color.white));
                        if (page <= 0) {
                            sflag = false;
                            code_tv.setEnabled(true);
                            code_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_corners));
                            code_tv.setText("获取验证码");
                            code_tv.setTextColor(getRouColors(R.color.gray4));
                            page = 60;
                        } else {
                            code_tv.setEnabled(false);
                            page--;
                        }

                        break;
                }

            }
        };
    }

    @Override
    protected void click() {
        userid_ll.setOnClickListener(this);
        userpass_ll.setOnClickListener(this);
        clear_img.setOnClickListener(this);
        login_bt.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);
        register_tv.setOnClickListener(this);
        home_page_finish.setOnClickListener(this);

        code_tv.setOnClickListener(this);
        pass_ed.setOnClickListener(this);
        user_ed.setOnClickListener(this);
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

    @Override
    protected void init() {
        setContentView(R.layout.login_activity_layout);
        listener = this;
        mShareAPI = UMShareAPI.get(this);

        login_bt = f(R.id.login_bt);
        userpass_ll = f(R.id.userpass_ll);
        userid_ll = f(R.id.userid_ll);
        code_tv = f(R.id.code_tv);
        register_tv = f(R.id.register_tv);

        home_page_finish = f(R.id.home_page_finish);

        wechat_login = f(R.id.wechat_login);
        qq_login = f(R.id.qq_login);

        login_tag = f(R.id.login_tag);

        pass_ed = f(R.id.pass_ed);
        user_ed = f(R.id.user_ed);

        user_icon = f(R.id.user_icon);
        pass_icon = f(R.id.pass_icon);

        clear_img = f(R.id.clear_img);

        ChangeLayoutBackdrop(0);
        if (getLoginWay() == null || getLoginWay().isEmpty())
            login_tag.setText("");
        else
            login_tag.setText(getLoginWay());

    }

    @Override
    protected void initData() {
        hand();
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
                goRegisterPage(this);
                break;
            case R.id.home_page_finish:
                Finish(this);
                break;
            case R.id.clear_img:
                user_ed.setText("");
                break;
            case R.id.login_bt:
                if (user_ed.getText().toString().length() == 11) {
                    if (pass_ed.getText().toString().length() == 0)
                        ToastUtil.toast2_bottom(LoginActivity.this, "请获取验证码后再进行登录...");
                    else
                        Login(user_ed.getText().toString(), String.valueOf(pass_ed.getText().toString()));

                } else {
                    if (user_ed.getText().toString().length() != 11)
                        ToastUtil.toast2_bottom(this, "请输入正确的手机号!");
                }

                break;
            case R.id.qq_login:
                platform = SHARE_MEDIA.QQ;
                mShareAPI.isInstall(this, platform);
                mShareAPI.doOauthVerify(LoginActivity.this, platform, getUMAuthListener(this, listener, MzFinal.QQ));
                login_flag = 0;
                break;
            case R.id.wechat_login:
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.isInstall(this, platform);
                mShareAPI.doOauthVerify(LoginActivity.this, platform, getUMAuthListener(this, listener, MzFinal.WECHAT));
                login_flag = 1;
                break;
            case R.id.code_tv:
                if (user_ed.getText().toString().length() == 11) {
                    code_tv.setEnabled(false);
                    Log.i(TAG, "click...");
                    sflag = true;
                    new Thread(regisRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "倒计时...");
                            while (sflag) {
                                Message msg = new Message();
                                msg.what = 0;
                                if (handler != null)
                                    handler.sendMessage(msg);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }).start();
                    getPhoneCode();

                } else {
                    ToastUtil.toast2_bottom(LoginActivity.this, "请输入正确的手机号!");
                }
                break;
        }

    }

    private void Login(String use, String pass) {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.DEFAULT)
                .addParams("account", use)
                .addParams("code", pass)
                .addParams("channelCode", MyAppcation.CHANNEL)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(LoginActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                Log.i(TAG, "token Log======" + new JSONObject(response).optString("data"));
                                ToastUtil.toast2_bottom(LoginActivity.this, "登录成功！");
                                saveInViCode(true);
                                user_ed.setText("");
                                pass_ed.setText("");
                                saveLoginWay(getRouString(R.string.phone_login));
                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();
                                Finish(LoginActivity.this);
                            } else
                                ToastUtil.ToastErrorMsg(LoginActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void ChangeLayoutBackdrop(int tag) {
        if (tag == 0) {
            userid_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_edittext_focused));
            user_ed.setHintTextColor(getResources().getColor(R.color.white));
            user_icon.setImageResource(R.mipmap.user_icon);
            setFocu(user_ed);

            userpass_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_edittext_normal));
            pass_ed.setHintTextColor(getResources().getColor(R.color.white));
            pass_icon.setImageResource(R.mipmap.unpass_icon);

        }
        if (tag == 1) {
            userpass_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_edittext_focused));
            pass_ed.setHintTextColor(getResources().getColor(R.color.white));
            pass_icon.setImageResource(R.mipmap.pass_icon);
            setFocu(pass_ed);

            userid_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_edittext_normal));
            user_ed.setHintTextColor(getResources().getColor(R.color.white));
            user_icon.setImageResource(R.mipmap.unuser_icon);

        }
    }

    private void getPhoneCode() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CREATECODE)
                .addParams("account", user_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(LoginActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(LoginActivity.this, "已发送验证码!");
                            } else
                                ToastUtil.ToastErrorMsg(LoginActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void setFocu(EditText ed1) {
        ed1.setFocusable(true);
        ed1.setFocusableInTouchMode(true);
        ed1.requestFocus();
    }

    //记得要重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSucceed(String access_token, String openid, String url) {
        try {

            Show(LoginActivity.this, "登录中", true, null);
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + url)
                    .addParams("openId", openid)
                    .addParams("oauth_consumer_key",MyAppcation.QQID)
                    .addParams("accessToken", access_token)
                    .addParams("channelCode", MyAppcation.CHANNEL)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(LoginActivity.this, "网络不顺畅...");
                            Cancle();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                    ToastUtil.toast2_bottom(LoginActivity.this, "登录成功！");
                                    saveInViCode(true);
                                    user_ed.setText("");
                                    pass_ed.setText("");
                                    SaveLoginWay(login_flag);
                                    if (MyFragment.fragment != null)
                                        MyFragment.fragment.onUpDataUserInfo();
                                    LoginActivity.this.finish();
                                } else
                                    ToastUtil.ToastErrorMsg(LoginActivity.this, response, code);
                                Cancle();
                            } catch (Exception e) {

                            }
                        }
                    });

        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (handler != null) {
            handler.removeCallbacks(regisRunnable);
            handler = null;
            sflag = false;
        }
    }

    @Override
    public void onFail() {

    }
}
