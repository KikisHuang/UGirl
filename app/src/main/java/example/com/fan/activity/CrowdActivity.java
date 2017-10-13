package example.com.fan.activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.CrowdAdapter;
import example.com.fan.bean.CrowDetailsBean;
import example.com.fan.bean.targetBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.homepageListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.TextViewColorUtils;
import example.com.fan.utils.TitleUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goProjectIncomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.KswitchWay;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/2.
 * 众筹页面;
 */
public class CrowdActivity extends InitActivity implements homepageListener, ItemClickListener {
    private static final String TAG = getTAG(CrowdActivity.class);
    private String title = "";
    private String id = "";
    private RelativeLayout loading_layout;
    private List<CrowDetailsBean> list;
    private List<targetBean> tlist;
    private CrowdAdapter adapter;
    private ListView listView;
    private homepageListener listener;
    private ItemClickListener itemlistener;
    private boolean detaiFlag = false;

    @Override
    protected void click() {

    }

    private void receive() {
        title = getIntent().getStringExtra("crowd_name");
        id = getIntent().getStringExtra("crowd_id");
    }

    private void getData() {

        /**
         * 获取众筹详情数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETCROWDDETAIL)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(CrowdActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                CrowDetailsBean cb = new Gson().fromJson(String.valueOf(ob), CrowDetailsBean.class);
                                list.add(cb);
                                addHHeader(cb);

                                for (int i = 0; i < cb.getMcCrowdFundingTargets().size(); i++) {
                                    if (i == cb.getMcCrowdFundingTargets().size() - 1)
                                        getTarget(cb.getMcCrowdFundingTargets().get(i).getTargetId(), true);
                                    else
                                        getTarget(cb.getMcCrowdFundingTargets().get(i).getTargetId(), false);
                                }

                                loading_layout.setVisibility(View.GONE);
                            } else
                                ToastUtil.ToastErrorMsg(CrowdActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    /**
     * 获取目标;
     *
     * @param targetId 目标id;
     * @param flag     根据flag判断是否获取完;
     */
    private void getTarget(String targetId, final boolean flag) {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETCROWDFUNDINGBYSUPPORTER)
                .addParams(MzFinal.ID, targetId)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "9")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(CrowdActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    targetBean cb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), targetBean.class);
                                    tlist.add(cb);
                                }
                                if (flag) {
                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged();
                                        Log.i(TAG, "notifyDataSetChanged");
                                    } else {
                                        adapter = new CrowdAdapter(list, tlist, CrowdActivity.this, listener, itemlistener);
                                        listView.setAdapter(adapter);
                                        Log.i(TAG, "setAdapter");
                                    }
                                }
                            } else
                                ToastUtil.ToastErrorMsg(CrowdActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void addHHeader(CrowDetailsBean cb) {
        View top = LayoutInflater.from(this).inflate(R.layout.crowd_top, null);
        LinearLayout ll = (LinearLayout) top.findViewById(R.id.icon_ll);
        ImageView top_ad_img = (ImageView) top.findViewById(R.id.top_ad_img);
        TextView num_tv = (TextView) top.findViewById(R.id.num_tv);
        TextView money_tv = (TextView) top.findViewById(R.id.money_tv);
        final TextView actions_detail = (TextView) top.findViewById(R.id.actions_detail);
        final TextView actions_content = (TextView) top.findViewById(R.id.actions_content);

        TextViewColorUtils.setTextColor(money_tv, cb.getSumMoney() + MzFinal.br, "共筹集范币", "#FF4979");
        TextViewColorUtils.setTextColor(num_tv, cb.getSumCount() + MzFinal.br, "支持总人数", "#FF4979");
        actions_content.setText(cb.getSubInfo());
        try {
            if (cb.getMcCrowdFundingImgUrls().size() > 0)
                Glide.with(getApplicationContext()).load(cb.getMcCrowdFundingImgUrls().get(0).getPath()).into(top_ad_img);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        if (listView.getHeaderViewsCount() == 0) {
            ModelInit(this, ll, cb);
            listView.addHeaderView(top);
        }
        actions_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detaiFlag) {
                    actions_content.setMaxLines(3);
                    actions_content.setEllipsize(TextUtils.TruncateAt.END);
                    detaiFlag = false;
                } else {
                    actions_content.setMaxLines(20);
                    detaiFlag = true;
                }
            }
        });
    }

    /**
     * 模特列表布局初始化;
     *
     * @param context
     * @param ll
     * @param cb
     * @return
     */
    private void ModelInit(Context context, LinearLayout ll, final CrowDetailsBean cb) {
        for (int i = 0; i < cb.getMcCrowdFundingTargets().size(); i++) {

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(context, 50), DeviceUtils.dip2px(context, 50), 1.0f);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            LinearLayout li = new LinearLayout(context);
            li.setOrientation(LinearLayout.VERTICAL);
            lp.rightMargin = DeviceUtils.dip2px(context, 10);
            li.setLayoutParams(lp);
            ImageView im = new ImageView(context);
            im.setLayoutParams(lp1);
            try {
                Glide.with(getApplicationContext()).load(cb.getMcCrowdFundingTargets().get(i).getHeadImgUrl()).apply(getRequestOptions(false, 100, 100,true)).into(im);

            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
            TextView name = new TextView(context);
            name.setTextColor(context.getResources().getColor(R.color.black12));
            name.setTextSize((int) this.getResources().getDimension(R.dimen.size13));
            name.setText(cb.getMcCrowdFundingTargets().get(i).getName());
            name.setMaxEms(5);
            name.setSingleLine(true);
            name.setEllipsize(TextUtils.TruncateAt.END);
            lp2.topMargin = DeviceUtils.dip2px(context, 5);
            lp2.bottomMargin = DeviceUtils.dip2px(context, 2);
            lp2.gravity = Gravity.CENTER;
            name.setLayoutParams(lp2);

            TextView num = new TextView(context);
            num.setTextColor(context.getResources().getColor(R.color.cherry1));

            num.setText(KswitchWay(Double.parseDouble(cb.getMcCrowdFundingTargets().get(i).getSumMoney())));
            num.setTextSize((int) this.getResources().getDimension(R.dimen.size12));
            lp3.gravity = Gravity.CENTER;
            num.setLayoutParams(lp3);

            final int finalI = i;
            li.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIconClick(cb.getMcCrowdFundingTargets().get(finalI).getUserId());
                }
            });
            li.addView(im);
            li.addView(name);
            li.addView(num);
            ll.addView(li);
        }
    }
    @Override
    protected void init() {
        setContentView(R.layout.crowd_activity_layout);
        receive();
        TitleUtils.setTitles(CrowdActivity.this, title);
        listener = this;
        itemlistener = this;
        list = new ArrayList<>();
        tlist = new ArrayList<>();
        listView = f(R.id.listView);
        loading_layout = f(R.id.loading_layout);
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
    public void onIconClick(String id) {

        goHomePage(CrowdActivity.this, id);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        goProjectIncomePage(CrowdActivity.this, id);
    }
}
