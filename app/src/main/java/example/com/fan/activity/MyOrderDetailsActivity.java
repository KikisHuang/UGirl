package example.com.fan.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.MyOrderBean;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.utils.JsonUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.JsonUtils.NullDispose;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/27.
 * 我的订单详情页面;
 */
public class MyOrderDetailsActivity extends InitActivity {
    private static final String TAG = getTAG(MyOrderDetailsActivity.class);
    private TextView create_time, order_state, order_title, order_price, goods_name, order_number, end_time, consignee, address, address_tv, pay_way, place_time, rental_tv;
    private String phone, name, add;
    private ImageView order_img;
    private MyOrderBean mb;
    private LinearLayout address_ll;


    @Override
    protected void click() {

    }

    private void setData() {
        create_time.setText(mb.getCreateTime());
        place_time.setText(mb.getCreateTime());

        String state = "";
        switch (mb.getStatus()) {
            case 0:
                state = "未支付";
                order_state.setTextColor(getRouColors(R.color.orange1));
                break;
            case 1:
                state = "支付成功";
                order_state.setTextColor(getRouColors(R.color.orange1));
                break;
            case -1:
                state = "支付失败";
                order_state.setTextColor(getRouColors(R.color.gray3));
                break;
            case -2:
                state = "校验失败";
                order_state.setTextColor(getRouColors(R.color.gray3));
                break;
            case -3:
                state = "退款";
                order_state.setTextColor(getRouColors(R.color.gray3));
                break;
        }
        order_state.setText(state);
        try {
            Glide.with(this).load(mb.getImg()).apply(getRequestOptions(true, 100, 100,false)).into(order_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        order_title.setText(mb.getTitle());
        goods_name.setText(mb.getSubInfo());
        order_price.setText("￥" + mb.getPrice());
        rental_tv.setText("￥" + mb.getPrice());
        order_number.setText("x" + mb.getCount());

        if (mb.getType() == 1) {
            switch (mb.getTransportType()) {
                case 0:
                    end_time.setText("未发货");
                    break;
                case 1:
                    end_time.setText("已发货");
                    break;
                case -1:
                    end_time.setText("已退货");
                    break;

                default:
                    end_time.setText("");
                    break;
            }
        }

        if (mb.getPaytype() == 0)
            pay_way.setText(getRouString(R.string.wechat_pay));
        if (mb.getPaytype() == 1)
            pay_way.setText(getRouString(R.string.ali_pay));
    }

    private void receiver() {
        Intent intent = this.getIntent();
        mb = (MyOrderBean) intent.getSerializableExtra("MyOrderBean");
    }

    private void getAddress() {
        if (mb.getType() == 1) {
            /**
             * 获取地址;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETADDRESSBYUSER)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(MyOrderDetailsActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    JSONObject ob = JsonUtils.getJsonOb(response);
                                    phone = NullDispose(ob, "phone");
                                    add = NullDispose(ob, "address");
                                    address.setText(getRouString(R.string.address) + add);

                                    consignee.setText(getRouString(R.string.consignee) + name + " " + phone);
                                } else if (code == 0) {
                                    consignee.setText(getRouString(R.string.address));
                                } else
                                    ToastUtil.ToastErrorMsg(MyOrderDetailsActivity.this, response, code);
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
                            ToastUtil.toast2_bottom(MyOrderDetailsActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    JSONObject ob = getJsonOb(response);
                                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                    name = ub.getName();
                                    consignee.setText(getRouString(R.string.consignee) + name + " " + phone);

                                } else if (code == 0) {
                                    consignee.setText(getRouString(R.string.consignee));
                                } else
                                    ToastUtil.ToastErrorMsg(MyOrderDetailsActivity.this, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });

        } else {
            address_ll.setVisibility(View.GONE);
            address_tv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.order_details_activity_layout);
        setTitles(this, getRouString(R.string.order_details));
        receiver();
        create_time = f(R.id.create_time);
        order_state = f(R.id.order_state);
        order_title = f(R.id.order_title);
        order_price = f(R.id.order_price);
        goods_name = f(R.id.goods_name);
        order_number = f(R.id.order_number);
        end_time = f(R.id.end_time);
        consignee = f(R.id.consignee);
        address = f(R.id.address);
        pay_way = f(R.id.pay_way);
        place_time = f(R.id.place_time);
        rental_tv = f(R.id.rental_tv);
        order_img = f(R.id.order_img);

        address_tv = f(R.id.address_tv);
        address_ll = f(R.id.address_ll);
    }

    @Override
    protected void initData() {
        setData();
        getAddress();
    }
}
