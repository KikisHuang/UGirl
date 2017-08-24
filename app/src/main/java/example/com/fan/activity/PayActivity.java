package example.com.fan.activity;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.McVipDescribes;
import example.com.fan.bean.PayBean;
import example.com.fan.bean.PayDetailBean;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.mylistener.PayRefreshListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.TitleUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.AliWechatPopupWindow;
import example.com.fan.view.dialog.AlertDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goPersonInfoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getSex;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/10.
 */
public class PayActivity extends InitActivity implements PayRefreshListener {
    private static final String TAG = getTAG(PayActivity.class);
    //布局list;
    private List<PayDetailBean> dlist;
    private LinearLayout top_ll, bottom_ll;
    private TextView balance_tv;
    private AliWechatPopupWindow aw;
    public static PayRefreshListener paylistener;
    private List<UserInfoBean> info;

    @Override
    protected void click() {

    }

    private void getData() {
        /**
         * 获取个人信息;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                .addParams("key", SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PayActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                info.add(ub);
                                balance_tv.setText(ub.getBalance() + "元");
                            } else
                                ToastUtil.ToastErrorMsg(PayActivity.this, response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        /**
         * 套餐资讯查询;
         */
        for (int i = 0; i < 2; i++) {
            final int finalI = i;
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETVIPBYTYPE)
                    .addParams("type", String.valueOf(i))
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(PayActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    String s = getJsonSring(response);
                                    JSONArray ar = new JSONArray(s);
                                    switch (finalI) {
                                        case 0:
                                            for (int i = 0; i < ar.length(); i++) {
                                                PayBean pb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PayBean.class);
                                                CreateLayouts(finalI, pb);
                                            }
                                            break;
                                        case 1:
                                            for (int i = 0; i < ar.length(); i++) {
                                                PayBean pb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PayBean.class);
                                                CreateLayouts(finalI, pb);
                                            }
                                            break;
                                    }
                                } else
                                    ToastUtil.ToastErrorMsg(PayActivity.this, response, code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        }
    }

    /**
     * 动态创建布局;
     *
     * @param flag 套餐和会员标识符; 0 套餐  1 会员;
     * @param pb   数据;
     */
    private void CreateLayouts(int flag, final PayBean pb) {
        final View view = LayoutInflater.from(PayActivity.this).inflate(R.layout.pay_include, null);

        ImageView im = (ImageView) view.findViewById(R.id.vip_icon);
        try {
            Glide.with(this).load(pb.getIconUrl()).into(im);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        TextView title = (TextView) view.findViewById(R.id.vip_name);
        TextView content = (TextView) view.findViewById(R.id.vip_explain);
        final TextView price = (TextView) view.findViewById(R.id.vip_price);
        final TextView details = (TextView) view.findViewById(R.id.details_tv);
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.pay_details);

        CardView ca = (CardView) view.findViewById(R.id.card_view);
        Random ra = new Random();

        int a = ra.nextInt(MzFinal.pay_colors.length);

        ca.setCardBackgroundColor(getRouColors(MzFinal.pay_colors[a]));


        title.setText(pb.getName());
        content.setText(pb.getInfo());
        price.setText("￥ " + pb.getPrice());
        PayDetailBean dd = new PayDetailBean();
        dd.setDetails_tv(details);
        dd.setPay_details(ll);

        for (int i = 0; i < pb.getMcVipDescribes().size(); i++) {
            createDetailsLayout(ll, pb.getMcVipDescribes().get(i));
        }

        dlist.add(dd);

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aw != null)
                    aw.ScreenPopupWindow(view, pb.getId());
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowofHideDetail(details, ll);
            }
        });

        if (flag == 0)
            bottom_ll.addView(view);
        else
            top_ll.addView(view);

    }

    /**
     * 动态创建套餐详情布局;
     *
     * @param pay_details    布局;
     * @param mcVipDescribes 数据;
     */
    private void createDetailsLayout(LinearLayout pay_details, McVipDescribes mcVipDescribes) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        int dp1 = DeviceUtils.dip2px(this, 20);
        int dp2 = DeviceUtils.dip2px(this, 5);
        lp.setMargins(dp1, dp2, dp1, dp2);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 34), DeviceUtils.dip2px(this, 34));
        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setLayoutParams(lp1);
        lp1.rightMargin = dp1;

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.CENTER;

        TextView tv = new TextView(this);
        int sizeOfText = (int) getResources().getDimension(R.dimen.size12);
        tv.setTextSize(sizeOfText);
        tv.setTextColor(getRouColors(R.color.blackk));
        tv.setGravity(Gravity.CENTER | Gravity.LEFT);
        tv.setLayoutParams(lp2);

        try {
            Glide.with(getApplicationContext()).load(mcVipDescribes.getIconUrl()).bitmapTransform(new CropCircleTransformation(this)).crossFade(200).into(img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        tv.setText(mcVipDescribes.getInfo());

        ll.addView(img);
        ll.addView(tv);
        pay_details.addView(ll);

    }

    @Override
    protected void init() {
        setContentView(R.layout.pay_activity_layout);
        TitleUtils.setTitles(this, "充值");
        info = new ArrayList<>();
        paylistener = this;
        aw = new AliWechatPopupWindow(PayActivity.this, new String[]{MzFinal.ALIPAYVIP, MzFinal.WXPAYVIP});
        dlist = new ArrayList<>();
        top_ll = f(R.id.top_ll);
        balance_tv = f(R.id.balance_tv);
        bottom_ll = f(R.id.bottom_ll);
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aw = null;
        paylistener = null;
        OkHttpUtils.getInstance().cancelTag(this);
    }

    /**
     * 套餐明细隐藏/显示方法;
     * @param tv view;
     * @param ll 布局;
     */
    private void ShowofHideDetail(TextView tv, LinearLayout ll) {
        if (tv.getText().toString().equals(getResources().getString(R.string.click_unfold))) {
            for (int i = 0; i < dlist.size(); i++) {
                if (dlist.get(i).getPay_details() == ll) {
                    ll.setVisibility(View.VISIBLE);
                    tv.setText(R.string.click_close);
                } else {
                    dlist.get(i).getPay_details().setVisibility(View.GONE);
                    dlist.get(i).getDetails_tv().setText(R.string.click_unfold);
                }
            }
        } else {
            ll.setVisibility(View.GONE);
            tv.setText(R.string.click_unfold);
        }

    }

    @Override
    public void onPayRefresh() {

        new AlertDialog(PayActivity.this).builder().setTitle("提示").setCancelable(true).setMsg("为了保证赠送的礼品能成功的送到您的手上,请去完善资料。").setPositiveButton("前往", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.size() > 0)
                    goPersonInfoPage(PayActivity.this, info.get(0).getHeadImgUrl(), info.get(0).getName(), String.valueOf(getSex(info.get(0).getSex())), info.get(0).getWx());
                finish();
            }
        }).show();
    }
}
