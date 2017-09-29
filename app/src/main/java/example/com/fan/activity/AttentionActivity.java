package example.com.fan.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.AttentionAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.AttentBean;
import example.com.fan.bean.ModeInfoBean;
import example.com.fan.fragment.MyFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonInt;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/9.
 * 关注页面;
 */
public class AttentionActivity extends InitActivity implements SpringListener, ItemClickListener {
    private static final String TAG = getTAG(AttentionActivity.class);
    private LinearLayout icon_ll;
    private AttentionAdapter adapter;
    private ListView listview;
    private List<AttentBean> list;
    private SpringView springview;
    private ItemClickListener listener;
    private int page = 0;

    @Override
    protected void click() {

    }

    private void getData(final boolean b) {
        /**
         * 我关注的;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYFOLLOW)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page+20))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AttentionActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (b)
                                    list.clear();
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    AttentBean ab = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), AttentBean.class);
                                    list.add(ab);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new AttentionAdapter(list, AttentionActivity.this, listener);
                                    listview.setAdapter(adapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(AttentionActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        /**
         * 获取我最近访问;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETSELLMODEL)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, String.valueOf(20))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AttentionActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    ModeInfoBean cb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), ModeInfoBean.class);
                                    getcolletIcon(cb);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(AttentionActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });


    }

    @Override
    protected void init() {
        setContentView(R.layout.attention_activity_layout);
        setTitles(this, getResources().getString(R.string.attention));
        listener = this;
        icon_ll = f(R.id.icon_ll);
        listview = f(R.id.listView);
        springview = f(R.id.springview);
        list = new ArrayList<>();
        SpringUtils.SpringViewInit(springview, this, this);
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getcolletIcon(final ModeInfoBean cb) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.dip2px(AttentionActivity.this, 32), DeviceUtils.dip2px(AttentionActivity.this, 32));
        lp.gravity = Gravity.CENTER;
        lp.setMargins(0, 0, 15, 0);
        ImageView img = new ImageView(AttentionActivity.this);
        try {
            Glide.with(getApplicationContext()).load(cb.getMcUser().getHeadImgUrl()).override(45, 45).bitmapTransform(new CropCircleTransformation(AttentionActivity.this)).crossFade(500).into(img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        img.setLayoutParams(lp);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomePage(AttentionActivity.this, cb.getUserId());
            }
        });
        icon_ll.addView(img);
    }

    @Override
    public void IsonRefresh(int i) {
        page = i;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        switch (position) {
            case 0:
                goHomePage(AttentionActivity.this, id);
                break;
            case 1:
                Attention(id);
                break;
        }
    }

    private void Attention(String user_id) {
        /**
         * 关注接口;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.FOLLOWUSER)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, user_id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AttentionActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonInt(response)) {
                                    case 0:
                                        ToastUtil.toast2_bottom(AttentionActivity.this, "成功取消关注");
                                        if (MyFragment.fragment != null)
                                            MyFragment.fragment.onUpDataUserInfo();

                                        getData(true);
                                        break;
                                    case 1:
                                        ToastUtil.toast2_bottom(AttentionActivity.this, "关注成功!");
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(AttentionActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
