package example.com.fan.activity;

import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PjIncomeAdapter;
import example.com.fan.bean.CrowdPayDetailBean;
import example.com.fan.mylistener.PayListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.TitleUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goOrderPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;

/**
 * Created by lian on 2017/6/7.
 * 众筹项目汇报页面;
 */
public class ProjectIncomeActivity extends InitActivity implements PayListener {
    private ListView listView;
    private PjIncomeAdapter adapter;
    private List<CrowdPayDetailBean> list;
    private String id = "";
    private PayListener tlistener;

    @Override
    protected void click() {

    }

    private void receive() {
        id = getIntent().getStringExtra("Target_id");
    }

    private void getData() {

        /**
         * 获取众筹详情数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETCROWDFUNDINGTARGETPRICE)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ProjectIncomeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    CrowdPayDetailBean cb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), CrowdPayDetailBean.class);
                                    list.add(cb);
                                }
                                adapter = new PjIncomeAdapter(list, ProjectIncomeActivity.this, tlistener);
                                listView.setAdapter(adapter);

                            } else
                                ToastUtil.ToastErrorMsg(ProjectIncomeActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    protected  void init() {
        setContentView(R.layout.pjincome_activity_layout);
        TitleUtils.setTitles(this, getResources().getString(R.string.project_income));
        receive();
        list = new ArrayList<>();
        listView = f(R.id.listView);
        tlistener = this;
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onPay(String num, String title, String price, String tag, String cover, String info, String id) {
        goOrderPage(ProjectIncomeActivity.this, num, title, price, tag, cover, info, id);
//        goOrderPage(context, String.valueOf(1), finalHolder.title_tv.getText().toString(), String.valueOf(list.get(position).getPrice()), "1","",list.get(position).getInfo(),list.get(position).getId());
    }
}
