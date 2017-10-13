package example.com.fan.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.WithdrawAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.AccountDetailsBean;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/11.
 */
public class Account_detailsActivity extends InitActivity {
    private TextView income_tv, withdraw_tv, photo_income, video_income;
    private LinearLayout head_layout;
    private ListView listView;
    private int flag;
    private List<AccountDetailsBean> alist;
    private WithdrawAdapter adapter;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.account_details_layout);
        Receiver();
        head_layout = f(R.id.head_layout);
        listView = f(R.id.listView);
        if (flag == 0) {
            setTitles(this, "明细");
            income_tv = f(R.id.income_tv);
            withdraw_tv = f(R.id.withdraw_tv);
            photo_income = f(R.id.photo_income);
            video_income = f(R.id.video_income);
            head_layout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            setTitles(this, "提现记录");
            head_layout.setVisibility(View.GONE);
            alist = new ArrayList<>();
        }
    }

    private void Receiver() {
        flag = Integer.parseInt(getIntent().getStringExtra("account_details_flag"));
    }

    @Override
    protected void initData() {
        if (flag == 0)
            getData(MzFinal.GETCASHDETAILS, false);
        if (flag == 1)
            getData(MzFinal.GETMYCASHDETAILS, true);
    }


    private void getData(String url, final boolean b) {
        /**
         * 获取明细、提现记录;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + url)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(Account_detailsActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (flag) {
                                    case 0:
                                        JSONObject ob = getJsonOb(response);
                                        AccountDetailsBean adb = new Gson().fromJson(String.valueOf(ob), AccountDetailsBean.class);
                                        income_tv.setText("￥" + adb.getSumMoney());
                                        withdraw_tv.setText("￥" + adb.getCashSumMoney());
                                        photo_income.setText("￥" + adb.getPhotoSumMoney());
                                        video_income.setText("￥" + adb.getVideoSumMoney());
                                        break;
                                    case 1:
                                        if (b)
                                            alist.clear();
                                        JSONArray ar = getJsonAr(response);
                                        for (int i = 0; i < ar.length(); i++) {
                                            AccountDetailsBean ab = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), AccountDetailsBean.class);
                                            alist.add(ab);
                                        }
                                        if (adapter == null) {
                                            adapter = new WithdrawAdapter(alist, Account_detailsActivity.this);
                                            listView.setAdapter(adapter);
                                        } else
                                            adapter.notifyDataSetChanged();
                                        break;
                                }

                            } else
                                ToastUtil.ToastErrorMsg(Account_detailsActivity.this, response, code);

                        } catch (Exception e) {

                        }
                    }
                });
    }

}
