package example.com.fan.fragment.sgamer;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.AccountBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ChangeUserInfoListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.WithdrawPopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goAccountDetailsPage;
import static example.com.fan.utils.IntentUtils.goRzPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/10/10.
 */
public class WithdrawFragment extends BaseFragment implements View.OnClickListener, ChangeUserInfoListener {
    private static final String TAG = getTAG(WithdrawFragment.class);

    private LinearLayout fill_in_layout, record_layout;
    private TextView phone_tv, account_name_tv, account_tv, withdraw_tv, money_tv, details_tv;
    //0 = fail  ,1 = success;
    private int BINDING = 99;
    public static ChangeUserInfoListener listener;

    @Override
    protected int initContentView() {
        return R.layout.withdraw_fragment;
    }

    @Override
    protected void click() {
        fill_in_layout.setOnClickListener(this);
        details_tv.setOnClickListener(this);
        record_layout.setOnClickListener(this);
        withdraw_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        phone_tv = (TextView) view.findViewById(R.id.phone_tv);
        account_name_tv = (TextView) view.findViewById(R.id.account_name_tv);
        account_tv = (TextView) view.findViewById(R.id.account_tv);
        withdraw_tv = (TextView) view.findViewById(R.id.withdraw_tv);
        details_tv = (TextView) view.findViewById(R.id.details_tv);
        money_tv = (TextView) view.findViewById(R.id.money_tv);
        fill_in_layout = (LinearLayout) view.findViewById(R.id.fill_in_layout);
        record_layout = (LinearLayout) view.findViewById(R.id.record_layout);
        listener = this;
    }

    @Override
    protected void initData() {
        getPhoneBinding();
        getData();
    }

    private void getPhoneBinding() {
        /**
         * 手机绑定判断;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CHECKPHONE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (cleanNull(getJsonSring(response)))
                                    BINDING = 0;
                                else
                                    BINDING = 1;
                            } else if (code == 0)
                                BINDING = 0;
                            else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);

                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getData() {
        /**
         * 获取账户数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYACCOUNT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONObject ob = getJsonOb(response);
                            if (code == 1) {
                                AccountBean ab = new Gson().fromJson(String.valueOf(ob), AccountBean.class);
                                money_tv.setText(String.valueOf(ab.getBalance()));

                                if (!cleanNull(String.valueOf(ab.getPhone())))
                                    phone_tv.setText(String.valueOf(ab.getPhone()));
                                if (!cleanNull(String.valueOf(ab.getCashAccount())))
                                    account_tv.setText(String.valueOf(ab.getCashAccount()));
                                if (!cleanNull(ab.getCashUserName()))
                                    account_name_tv.setText(ab.getCashUserName());

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);

                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_tv:
                goAccountDetailsPage(getActivity(), 0);
                break;
            case R.id.record_layout:
                goAccountDetailsPage(getActivity(), 1);
                break;
            case R.id.fill_in_layout:
                if (BINDING == 1)
                    goRzPage(getActivity(), 4);
                if (BINDING == 0)
                    goRzPage(getActivity(), 1);
                break;
            case R.id.withdraw_tv:
                if (BINDING == 1) {
                    if (Double.valueOf(money_tv.getText().toString()) < 5) {
                        ToastUtil.toast2_bottom(getActivity(), "账户金额小于5元不能提现..");
                    } else {
                        WithdrawPopupWindow ww = new WithdrawPopupWindow(getActivity(), money_tv.getText().toString());
                        ww.ScreenPopupWindow();
                    }

                } else if (BINDING == 0) {
                    goRzPage(getActivity(), 1);
                    ToastUtil.toast2_bottom(getActivity(), "请先绑定手机和支付宝账号！！");
                }

                break;
        }
    }

    @Override
    public void onUpDataUserInfo() {
        getPhoneBinding();
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
