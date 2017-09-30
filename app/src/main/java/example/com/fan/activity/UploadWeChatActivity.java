package example.com.fan.activity;


import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModeInfoBean;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.RippleView;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/25.
 */
public class UploadWeChatActivity extends InitActivity implements View.OnClickListener {
    private final static String TAG = UploadWeChatActivity.class.getSimpleName();
    private EditText price_ed, wechat_ed;
    private RippleView save_button;

    @Override
    protected void click() {
        save_button.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.upload_wechat_activity);
        setTitles(this, "填写微信号");

        save_button = f(R.id.save_button);
        price_ed = f(R.id.price_ed);
        wechat_ed = f(R.id.wechat_ed);
    }

    @Override
    protected void initData() {
        getModelInfo();
    }

    private void getModelInfo() {
        /**
         * 获取模特微信;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETWXPRICE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UploadWeChatActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONObject ob = getJsonOb(response);
                            if (code == 1) {
                                ModeInfoBean mb = new Gson().fromJson(String.valueOf(ob), ModeInfoBean.class);
                                wechat_ed.setText(mb.getWx());
                                String p = "0";
                                if (cleanNull(mb.getWxPrice()))
                                    price_ed.setText(p);
                                else
                                    price_ed.setText(mb.getWxPrice());

                            } else
                                ToastUtil.ToastErrorMsg(UploadWeChatActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                if (!price_ed.getText().toString().isEmpty() && !wechat_ed.getText().toString().isEmpty() && Integer.valueOf(price_ed.getText().toString()) > 68 && Integer.valueOf(price_ed.getText().toString()) < 568)
                    SettingPrice();
                else if (wechat_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "微信号不能为空！！");
                else if (price_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "价格不能为空！！");
                else if (Integer.valueOf(price_ed.getText().toString()) < 68)
                    ToastUtil.toast2_bottom(this, "价格太便宜了哦！");
                else if (Integer.valueOf(price_ed.getText().toString()) > 568)
                    ToastUtil.toast2_bottom(this, "价格太高了哦！");

                break;
        }
    }

    private void SettingPrice() {
        /**
         * 获取模特微信;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.SETWXPRICE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("price", price_ed.getText().toString())
                .addParams("wx", wechat_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UploadWeChatActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(UploadWeChatActivity.this, "设置成功!!");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(UploadWeChatActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
