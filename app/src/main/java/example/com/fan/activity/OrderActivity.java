package example.com.fan.activity;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.mylistener.AddressListener;
import example.com.fan.utils.JsonUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.ChangeAddressPopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.NullDispose;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.PayUtils.PayNow;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/5.
 * 订单处理页面;
 * params tag  判断是众筹订单(tag==1), 还是商品订单 (tag==0);
 */
public class OrderActivity extends InitActivity implements View.OnClickListener, AddressListener {
    private static final String TAG = getTAG(OrderActivity.class);
    //0=ali,1=wechat;
    private int Pay_Type = 0;
    private LinearLayout ali_ll, wecha_ll;
    private ImageView ali_logo, wechat_logo, cover_img;
    private TextView ali_tv, wechat_tv, buy_name, buy_price, name_photo, address_tv, Pay_now, wx_tv, bottom_price, report_content;
    private CardView report_cd, take_cd, leave_msg;
    private RelativeLayout change_address;
    private String phone, name, wx, address;
    private int tag;
    private String PayId = "";
    private int num = 0;

    private void getAddress() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETADDRESSBYUSER)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(OrderActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = JsonUtils.getJsonOb(response);
                                phone = NullDispose(ob, "phone");
                                address = NullDispose(ob, "address");
                                address_tv.setText(getRouString(R.string.address) + address);

                                name_photo.setText(getRouString(R.string.consignee) + name + " " + phone);
                            } else if (code == 0) {
                                address_tv.setText(getRouString(R.string.address));
                            } else
                                ToastUtil.ToastErrorMsg(OrderActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        /**
         * 获取个人信息;
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
                        ToastUtil.toast2_bottom(OrderActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                name = ub.getName();
                                wx = ub.getWx();
                                name_photo.setText(getRouString(R.string.consignee) + name + " " + phone);
                                wx_tv.setText(getRouString(R.string.wechat) + wx);

                            } else if (code == 0) {
                                name_photo.setText(getRouString(R.string.consignee));
                                wx_tv.setText(getRouString(R.string.wechat));
                            } else
                                ToastUtil.ToastErrorMsg(OrderActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        }

    private void receiveData() {
        tag = Integer.parseInt(getIntent().getStringExtra("buy_tag"));
        num = Integer.parseInt(getIntent().getStringExtra("buy_num"));
        String money = getIntent().getStringExtra("buy_money");
        String name = getIntent().getStringExtra("buy_name");
        String info = getIntent().getStringExtra("buy_info");
        PayId = getIntent().getStringExtra("buy_id");

        report_content.setText(info);
        try {
            Glide.with(this).load(getIntent().getStringExtra("buy_cover")).into(cover_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        buy_price.setText(getResources().getString(R.string.overpaym) + String.valueOf(Double.parseDouble(money) * num));
        bottom_price.setText("￥" + String.valueOf(Double.parseDouble(money) * num));

        if (num == 0)
            buy_name.setText(name);
        else if (num < 99 && num > 0) {
            if (num == 1)
                buy_name.setText(name);
            else
                buy_name.setText(name + "X" + String.valueOf(num));
        }

        switch (tag) {
            case 0:

                report_cd.setVisibility(View.GONE);

                take_cd.setVisibility(View.VISIBLE);
                leave_msg.setVisibility(View.VISIBLE);

                break;
            case 1:

                report_cd.setVisibility(View.VISIBLE);

                take_cd.setVisibility(View.GONE);
                leave_msg.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    @Override
    protected void click() {
        ali_ll.setOnClickListener(this);
        wecha_ll.setOnClickListener(this);
        Pay_now.setOnClickListener(this);
        change_address.setOnClickListener(this);
    }
    @Override
    protected void init() {
        setContentView(R.layout.order_activity_layout);
        setTitles(this, getResources().getString(R.string.order));
        ali_ll = f(R.id.ali_ll);
        wecha_ll = f(R.id.wecha_ll);
        ali_logo = f(R.id.ali_logo);
        wechat_logo = f(R.id.wechat_logo);
        bottom_price = f(R.id.bottom_price);
        wechat_tv = f(R.id.wechat_tv);
        ali_tv = f(R.id.ali_tv);
        buy_name = f(R.id.buy_name);
        buy_price = f(R.id.buy_price);
        name_photo = f(R.id.name_photo);
        wx_tv = f(R.id.wx_tv);
        address_tv = f(R.id.address_tv);
        report_content = f(R.id.report_content);
        Pay_now = f(R.id.Pay_now);

        change_address = f(R.id.change_address);

        report_cd = f(R.id.report_cd);
        take_cd = f(R.id.take_cd);
        cover_img = f(R.id.cover_img);
        leave_msg = f(R.id.leave_msg);
    }

    @Override
    protected void initData() {
        receiveData();
        getAddress();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ali_ll:
                if (Pay_Type == 1) {
                    ali_ll.setBackgroundResource(R.drawable.cherry_corner3);
                    ali_logo.setImageResource(R.mipmap.unali_pay);
                    ali_tv.setTextColor(getResources().getColor(R.color.white));
                    Pay_Type--;

                    wecha_ll.setBackgroundResource(R.drawable.gray_shape_corner);
                    wechat_logo.setImageResource(R.mipmap.wechat_pay);
                    wechat_tv.setTextColor(getResources().getColor(R.color.gray1));
                }
                break;

            case R.id.wecha_ll:
                if (Pay_Type == 0) {
                    wecha_ll.setBackgroundResource(R.drawable.cherry_corner3);
                    wechat_logo.setImageResource(R.mipmap.unwechat_pay);
                    wechat_tv.setTextColor(getResources().getColor(R.color.white));
                    Pay_Type++;

                    ali_ll.setBackgroundResource(R.drawable.gray_shape_corner);
                    ali_logo.setImageResource(R.mipmap.ali_pay);
                    ali_tv.setTextColor(getResources().getColor(R.color.gray1));

                }
                break;
            case R.id.Pay_now:
                if (Pay_Type == 0 || Pay_Type == 1) {
                    if (name_photo.getText().toString().isEmpty() || address_tv.getText().toString().isEmpty() && tag == 0) {
                        ToastUtil.toast2_bottom(OrderActivity.this, "请填写地址及联系方式!!");
                    } else {
                        if (wx_tv.getText().toString().isEmpty() && tag == 0) {
                            ToastUtil.toast2_bottom(OrderActivity.this, "请填写微信号方便我们通知您!!");
                        } else {
                            /**
                             * 判断是众筹还是一口价的支付;
                             */
                            switch (tag) {
                                case 0:
                                    if (Pay_Type == 0)
                                        PayNow(this, PayId, MzFinal.ALIPAYOFFICIALSELL, Pay_Type, num);
                                    if (Pay_Type == 1)
                                        PayNow(this, PayId, MzFinal.WXPAYOFFICIALSELL, Pay_Type, num);
                                    break;
                                case 1:
                                    if (Pay_Type == 0)
                                        PayNow(this, PayId, MzFinal.ALIPAYCROWDFUNDING, Pay_Type, 1);
                                    if (Pay_Type == 1)
                                        PayNow(this, PayId, MzFinal.WXPAYCROWDFUNDING, Pay_Type, 1);
                                    break;
                            }
                        }
                    }
                } else {
                    ToastUtil.toast2_bottom(OrderActivity.this, "没有获取到支付方式...");
                    finish();
                }

                break;
            case R.id.change_address:
//                String getSignInfo = reqResult.substring(reqResult.indexOf(",") + 1);
                String Info = name_photo.getText().toString();
                ChangeAddressPopupWindow.ScreenPopupWindow(OrderActivity.this, wx, phone, address, this);
                break;
        }
    }

    private boolean flag;

    @Override
    public void onChange(final String a, final String w, final String p) {
        if (a.equals(address) && w.equals(wx) && p.equals(phone)) {
            Log.i(TAG, "地址无修改,无需上传...");
        } else {
            flag = false;
            /**
             * 修改地址;
             */
            OkHttpUtils
                    .post()
                    .url(MzFinal.URl + MzFinal.MODIFYADDRESS)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams("address", a)
                    .addParams("phone", p)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(OrderActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    address = a;
                                    phone = p;
                                    if (flag) {
                                        ToastUtil.toast2_bottom(OrderActivity.this, "地址信息修改成功!");
                                        address_tv.setText(getRouString(R.string.address) + a);
                                        name_photo.setText(getRouString(R.string.consignee) + name + " " + p);
                                        wx_tv.setText(getRouString(R.string.wechat) + w);
                                    } else {
                                        flag = true;
                                    }
                                } else
                                    ToastUtil.ToastErrorMsg(OrderActivity.this, response, code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            /**
             * 修改用户信息;
             */
            OkHttpUtils
                    .post()
                    .url(MzFinal.URl + MzFinal.MODIFYUSER)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams("wx", w)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(OrderActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    wx = w;
                                    if (flag) {
                                        ToastUtil.toast2_bottom(OrderActivity.this, "地址信息修改成功!");
                                        address_tv.setText(getRouString(R.string.address) + a);
                                        name_photo.setText(getRouString(R.string.consignee) + name + " " + p);
                                        wx_tv.setText(getRouString(R.string.wechat) + w);
                                    } else {
                                        flag = true;
                                    }
                                } else
                                    ToastUtil.ToastErrorMsg(OrderActivity.this, response, code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
}
