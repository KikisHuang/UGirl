package example.com.fan.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.fragment.MyFragment;
import example.com.fan.fragment.sgamer.WithdrawFragment;
import example.com.fan.mylistener.onPhotoCutListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.RippleView;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goSelectPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.PhotoPictureDialog;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/28.
 */
public class AttestationActivity extends InitActivity implements View.OnClickListener, onPhotoCutListener {
    private static final String TAG = getTAG(AttestationActivity.class);

    private LinearLayout hint_layout, phone_verify_layout, phone_code_layout, fill_in_layout, over_info_layout;
    private List<View> views;
    private int tag;
    private RippleView commint_button, next_step_button, phone_next_button, bangdin_phone_button, renz_btn;

    private FrameLayout upload_layout;
    private LinearLayout city_layout, BWH_layout, height_layout, wx_layout;
    private EditText city_ed, BWH_ed, height_ed, wx_ed, phone_ed;
    private ImageView back_img;
    private File file = null;
    private String city;
    private String province;
    private String B;
    private String W;
    private String H;
    private TextView phone2_tv, time_tv;

    private EditText alipay_account_ed, alipay_name_ed, code_ed;

    private Handler handler;
    private int page = 60;
    private Runnable regisRunnable;
    private boolean sflag;

    @Override
    protected void click() {
        switch (tag) {
            case 0:
                break;
            case 1:
                //1234
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                //423

                break;
        }
    }

    private void ChangeInit() {
        alipay_account_ed = f(R.id.alipay_account_ed);
        alipay_name_ed = f(R.id.alipay_name_ed);

        next_step_button = f(R.id.next_step_button);
        phone_next_button = f(R.id.phone_next_button);
        bangdin_phone_button = f(R.id.bangdin_phone_button);

        time_tv = f(R.id.time_tv);
        phone2_tv = f(R.id.phone2_tv);
        code_ed = f(R.id.code_ed);
        next_step_button = f(R.id.next_step_button);
        phone_ed = f(R.id.phone_ed);

        next_step_button.setOnClickListener(this);
        phone_next_button.setOnClickListener(this);
        bangdin_phone_button.setOnClickListener(this);
        hand();

    }

    private void hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        time_tv.setText("接受短信大约需要" + page + "秒" + "");
                        if (page <= 0) {
                            sflag = false;
                            page = 60;
                        } else
                            page--;

                        break;
                }

            }
        };
    }

    //完善信息初始化;
    private void OverInfoInit() {

        commint_button = f(R.id.commint_button);
        upload_layout = f(R.id.upload_layout);
        city_layout = f(R.id.city_layout);
        BWH_layout = f(R.id.BWH_layout);
        height_layout = f(R.id.height_layout);
        wx_layout = f(R.id.wx_layout);

        city_ed = f(R.id.city_ed);
        BWH_ed = f(R.id.BWH_ed);
        height_ed = f(R.id.height_ed);
        wx_ed = f(R.id.wx_ed);
        back_img = f(R.id.back_img);

        commint_button.setOnClickListener(this);
        upload_layout.setOnClickListener(this);

        city_layout.setOnClickListener(this);
        BWH_layout.setOnClickListener(this);
        height_layout.setOnClickListener(this);


    }

    @Override
    protected void init() {
        setContentView(R.layout.attestation_activity);
        setTitles(this, "");
        views = new ArrayList<>();
        views.add(hint_layout = f(R.id.hint_layout));
        views.add(phone_verify_layout = f(R.id.phone_verify_layout));
        views.add(phone_code_layout = f(R.id.phone_code_layout));
        views.add(fill_in_layout = f(R.id.fill_in_layout));
        views.add(over_info_layout = f(R.id.over_info_layout));


        hideLayout();
        ReceiverTag();
    }

    private void hideLayout() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).setVisibility(View.GONE);
        }
    }

    private void ReceiverTag() {
        tag = Integer.parseInt(getIntent().getStringExtra("attesta_tag"));
        switch (tag) {
            case 0:
                OverInfoInit();
                over_info_layout.setVisibility(View.VISIBLE);

                break;
            case 1:
                BinDingInit();
                hint_layout.setVisibility(View.VISIBLE);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                ChangeInit();
                fill_in_layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void BinDingInit() {
        renz_btn = f(R.id.renz_btn);

        commint_button = f(R.id.commint_button);
        phone_next_button = f(R.id.phone_next_button);
        next_step_button = f(R.id.next_step_button);
        bangdin_phone_button = f(R.id.bangdin_phone_button);

        alipay_account_ed = f(R.id.alipay_account_ed);
        alipay_name_ed = f(R.id.alipay_name_ed);
        next_step_button = f(R.id.next_step_button);
        bangdin_phone_button = f(R.id.bangdin_phone_button);
        time_tv = f(R.id.time_tv);
        phone2_tv = f(R.id.phone2_tv);
        code_ed = f(R.id.code_ed);
        next_step_button = f(R.id.next_step_button);
        phone_ed = f(R.id.phone_ed);

        renz_btn.setOnClickListener(this);
        next_step_button.setOnClickListener(this);
        phone_next_button.setOnClickListener(this);
        bangdin_phone_button.setOnClickListener(this);
        hand();
    }

    @Override
    protected void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city_layout:
                goSelectPage(this, 2);
                break;
            case R.id.BWH_layout:
                goSelectPage(this, 3);
                break;
            case R.id.height_layout:
                goSelectPage(this, 1);
                break;
            case R.id.upload_layout:
                PhotoPictureDialog(this, false, 103);
                break;
            case R.id.commint_button:
                if (file != null)
                    PerfectInfo();
                else
                    ToastUtil.toast2_bottom(this, "请选择一张封面图片后再提交..");
                break;
            case R.id.renz_btn:
                if (tag == 1) {
                    hint_layout.setVisibility(View.GONE);
                    phone_verify_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.next_step_button:
                if (tag == 4) {
                    if (!alipay_account_ed.getText().toString().isEmpty() || !alipay_name_ed.getText().toString().isEmpty()) {
                        fill_in_layout.setVisibility(View.GONE);
                        phone_verify_layout.setVisibility(View.VISIBLE);
                    } else if (alipay_account_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(this, "支付宝账户不能为空！！！");
                    else if (alipay_name_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(this, "支付宝姓名不能为空！！！");
                }
                if (tag == 1)
                    BinDingPhone();

                break;
            case R.id.phone_next_button:
                if (!phone_ed.getText().toString().isEmpty() && phone_ed.getText().toString().length() == 11)
                    getPhoneCode();
                else if (phone_ed.getText().toString().length() != 11)
                    ToastUtil.toast2_bottom(this, "请填写正确的手机号！！！");
                else if (phone_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "手机号不能为空！！！");
                break;
            case R.id.bangdin_phone_button:
                if (tag == 4) {
                    if (!code_ed.getText().toString().isEmpty())
                        ChangePhoneAndAliAccount();
                    else if (code_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(this, "验证码不能为空！！！");
                }
                if (tag == 1) {
                    if (!code_ed.getText().toString().isEmpty()) {
                        phone_code_layout.setVisibility(View.GONE);
                        fill_in_layout.setVisibility(View.VISIBLE);
                    } else if (code_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(this, "验证码不能为空！！！");
                }
                break;
        }
    }

    private void BinDingPhone() {
        ChangePhoneAndAliAccount();
    }

    private void ChangePhoneAndAliAccount() {
        /**
         * 修改用户手机号\账号;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URl + MzFinal.MODIFYPHONE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("phone", phone_ed.getText().toString())
                .addParams("cashAccount", alipay_account_ed.getText().toString())
                .addParams("cashUserName", alipay_name_ed.getText().toString())
                .addParams("code", code_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AttestationActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {

                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();
                                if (WithdrawFragment.listener != null)
                                    WithdrawFragment.listener.onUpDataUserInfo();

                                if (tag == 4)
                                    ToastUtil.toast2_bottom(AttestationActivity.this, "修改成功！！！");
                                if (tag == 1)
                                    ToastUtil.toast2_bottom(AttestationActivity.this, "绑定成功！！！");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(AttestationActivity.this, response, code);


                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getPhoneCode() {

        /**
         * 获取验证码;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CREATEMODIFYPHONECODE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("phone", phone_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AttestationActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(AttestationActivity.this, "已发送验证码！！");
                                phone_verify_layout.setVisibility(View.GONE);
                                phone2_tv.setText("+86 " + phone_ed.getText().toString());
                                phone_code_layout.setVisibility(View.VISIBLE);
                                startCountDown();
                            } else
                                ToastUtil.ToastErrorMsg(AttestationActivity.this, response, code);


                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void startCountDown() {
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
    }

    private void PerfectInfo() {
        /**
         * 申请模特认证;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URl + MzFinal.MODELAPPLY)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("residentProvince", province)
                .addParams("residentCity", city)
                .addParams("upperMeasurement", B)
                .addParams("inMeasurement", W)
                .addParams("lowerMeasurement", H)
                .addParams("height", height_ed.getText().toString())
                .addParams("wx", wx_ed.getText().toString())
                .addFile("img", file.getName(), file)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AttestationActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(AttestationActivity.this, "修改成功!");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(AttestationActivity.this, response, code);


                        } catch (Exception e) {

                        }
                    }
                });
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
    public void PhotoListener(String path) {

    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        bitmap.recycle();
        if (page == 103) {
            file = new File(path);
            Glide.with(this).load(file).into(back_img);
        }

    }
}
