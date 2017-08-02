package example.com.fan.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.fragment.MyFragment;
import example.com.fan.mylistener.EditChangedListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static com.tencent.open.utils.Util.isNumeric;
import static example.com.fan.base.sign.save.SPreferences.saveInViCode;
import static example.com.fan.base.sign.save.SPreferences.saveLoginWay;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/16.
 */
public class RegisterActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(RegisterActivity.class);
    private EditText photo_ed, code_ed;
    private ImageView clear_img, check_img;
    private TextView code_tv, submit_info;
    private boolean check = true;
    private int page = 60;
    private Handler handler;
    private boolean sflag;
    private Runnable regisRunnable;


    private void hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        code_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_corner2));
                        code_tv.setText("重发(" + page + ")");
                        if (page <= 0) {
                            sflag = false;
                            code_tv.setEnabled(true);
                            code_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.person_info_bt_selector));
                            code_tv.setText("获取验证码");
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
        code_tv.setOnClickListener(this);
        submit_info.setOnClickListener(this);
        check_img.setOnClickListener(this);
        clear_img.setOnClickListener(this);
    }


    @Override
    protected void init() {
        setContentView(R.layout.register_activity_layout);
        setTitles(this, getRouString(R.string.register));
        photo_ed = f(R.id.photo_ed);
        clear_img = f(R.id.clear_img);
        code_tv = f(R.id.code_tv);
        submit_info = f(R.id.submit_info);
        check_img = f(R.id.check_img);
        code_ed = f(R.id.code_ed);

        photo_ed.addTextChangedListener(new EditChangedListener(clear_img));
    }

    @Override
    protected void initData() {
        hand();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_img:
                photo_ed.setText("");
                break;
            case R.id.code_tv:
                if (photo_ed.getText().toString().length() == 11 && isNumeric(photo_ed.getText().toString())) {
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
                    if (isNumeric(photo_ed.getText().toString()))
                        ToastUtil.toast2_bottom(RegisterActivity.this, "输入的手机号包含非法符号!");
                    else
                        ToastUtil.toast2_bottom(RegisterActivity.this, "请输入正确的手机号!");
                }
                break;
            case R.id.submit_info:
                if (!code_ed.getText().toString().trim().isEmpty() && check && isNumeric(code_ed.getText().toString())) {

                    if (!photo_ed.getText().toString().isEmpty()) {

                        if (photo_ed.getText().toString().length() == 11 && isNumeric(photo_ed.getText().toString())) {
                            Login(code_ed.getText().toString(), String.valueOf(photo_ed.getText().toString()));
                        } else {
                            if (photo_ed.getText().toString().length() != 11)
                                ToastUtil.toast2_bottom(this, "请输入正确的手机号!");
                            if (!isNumeric(photo_ed.getText().toString()))
                                ToastUtil.toast2_bottom(RegisterActivity.this, "输入的手机号包含非法符号!");
                        }
                    } else
                        ToastUtil.toast2_bottom(RegisterActivity.this, "请输入手机号!!!");

                } else {
                    if (code_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(RegisterActivity.this, "请填写验证码!!!");
                    if (!isNumeric(code_ed.getText().toString()))
                        ToastUtil.toast2_bottom(RegisterActivity.this, "输入的验证码包含非法符号!");
                }

                break;
            case R.id.check_img:
                if (check) {
                    check = false;
                    check_img.setImageResource(R.mipmap.off_img);

                } else {
                    check = true;
                    check_img.setImageResource(R.mipmap.on_img);

                }

                break;
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

    private void Login(String code, String num) {

        OkHttpUtils
                .post()
                .url(MzFinal.URl + MzFinal.DEFAULT)
                .addParams("account", num)
                .addParams("code", code)
                .addParams("channelCode", MyAppcation.CHANNEL)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(RegisterActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                Log.i(TAG, "token Log======" + new JSONObject(response).optString("data"));
                                ToastUtil.toast2_bottom(RegisterActivity.this, "登录成功！");
                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();
                                saveInViCode(true);
                                saveLoginWay(getRouString(R.string.phone_login));
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(RegisterActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void getPhoneCode() {
        /**
         * 验证码获取;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CREATECODE)
                .addParams("account", photo_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(RegisterActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(RegisterActivity.this, "已发送验证码!");
                            } else
                                ToastUtil.ToastErrorMsg(RegisterActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
