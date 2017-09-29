package example.com.fan.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import example.com.fan.adapter.FindAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModeInfoBean;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.GlideCircleTransform;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.PullToZoomListView;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonInt;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.ShareUtils.ShareApp;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/31.
 * 模特个人主页;
 */
public class HomePage2Activity extends BaseActivity implements ItemClickListener, View.OnClickListener, TwoParamaListener, ShareRequestListener {

    private static final String TAG = getTAG(HomePage2Activity.class);

    private View top;
    private ImageView home_page_icon, home_page_finish;
    private TextView model_name, city_tv, bwh_tv, height_tv, attention_tv, follwCount;
    private PullToZoomListView listView;
    private FindAdapter adapter;
    private List<ModelBean> rlist;
    private LinearLayout title_ll;
    private String user_id = "";
    private ItemClickListener listener;
    private TwoParamaListener tlistener;
    private ShareRequestListener slistener;
    private int page = 999;
    private String cover = "";

    protected void click() {
        home_page_finish.setOnClickListener(this);
        attention_tv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getData();
    }

    private void Headerinit() {

        //设置头部的图片;
        ImageView img = listView.getHeaderView();
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            Glide.with(getApplicationContext()).load(cover).override(1296, 1080).into(img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        //高度;
        listView.setmHeaderHeight(DeviceUtils.getWindowHeight(this) * 5 / 10);

        //设置头部的的布局;
        listView.getHeaderContainer().addView(top);
        listView.setHeaderView();
        listView.setAdapter(adapter);
//        listView.setFloatingView(null);

        //阻尼listview下拉监听回调;
        listView.setonRefreshListener(new PullToZoomListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                Log.i(TAG, "刷新");
                listView.onRefreshComplete();
            }

            @Override
            public void onPullStart() {
                // TODO Auto-generated method stub
                Log.i(TAG, "开始下拉");
            }

            @Override
            public void onPullComplete() {
                // TODO Auto-generated method stub
                Log.i(TAG, "下拉完成");
            }
        });
    }

    private void getData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMODELINFO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, user_id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(HomePage2Activity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);

                                ModeInfoBean mib = new Gson().fromJson(String.valueOf(ob), ModeInfoBean.class);

                                bwh_tv.setText("三围\n" + mib.getUpperMeasurement() + "/" + mib.getInMeasurement() + "/" + mib.getLowerMeasurement());
                                height_tv.setText("身高\n" + mib.getHeight());
                                city_tv.setText("城市\n" + mib.getMcUser().getResidentCity());
                                model_name.setText(mib.getRealName());
                                cover = mib.getCoverPath();
                                follwCount.setText(mib.getMcUser().getFollwCount() + " 人已关注她");
//                    headImg = mib.getMcUser().getHeadImgUrl();
                                if (mib.isFolllw())
                                    attention_tv.setText(getRouString(R.string.unattention));
                                else
                                    attention_tv.setText(getRouString(R.string.attention) + "Ta");

                                try {
                                    Glide.with(getApplicationContext()).load(mib.getMcUser().getHeadImgUrl()).transform(new GlideCircleTransform(HomePage2Activity.this, 1, getRouColors(R.color.white))).crossFade(200).into(home_page_icon);

                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }
                            } else
                                ToastUtil.ToastErrorMsg(HomePage2Activity.this, response, code);
                            getDetails();
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getDetails() {
        /**
         * 模特所属的私照或视频详情数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPUBLISHRECORDBYMODEL)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, user_id)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, String.valueOf(page))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(HomePage2Activity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                rlist.clear();
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                    rlist.add(mb);
                                }

                                adapter = new FindAdapter(HomePage2Activity.this, rlist, listener, tlistener, slistener, true);
                                Headerinit();
                            } else
                                ToastUtil.ToastErrorMsg(HomePage2Activity.this, response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });
    }

    protected void init() {
        setContentView(R.layout.home_page_activity_layout);
        try {
            user_id = getIntent().getStringExtra("user_id");
            Log.i(TAG, "user_id =====" + user_id);
        } catch (Exception e) {
            Log.i(TAG, "Error =====" + e);
            finish();
        }
        listener = this;
        slistener = this;
        tlistener = this;
        LayoutInflater inflater = getLayoutInflater();
        top = inflater.inflate(R.layout.home_page_top, null);
        home_page_icon = (ImageView) top.findViewById(R.id.home_page_icon);

        attention_tv = (TextView) top.findViewById(R.id.attention_tv);
        model_name = (TextView) top.findViewById(R.id.model_name);
        city_tv = (TextView) top.findViewById(R.id.city_tv);
        bwh_tv = (TextView) top.findViewById(R.id.bwh_tv);
        follwCount = (TextView) top.findViewById(R.id.follwCount);
        height_tv = (TextView) top.findViewById(R.id.height_tv);
        listView = f(R.id.listView);
        home_page_finish = f(R.id.home_page_finish);
        title_ll = f(R.id.title_ll);
        title_ll.setBackgroundColor(Color.TRANSPARENT);
        rlist = new ArrayList<>();


    }

    @Override
    public void onItemClickListener(int position, String id) {
        if (LoginStatusQuery()) {
            switch (position) {
                case -2:
                    goPrivatePhotoPage(HomePage2Activity.this, id, 0);
                    break;
                default:
                    goPhotoPage(HomePage2Activity.this, id, 0);
                    break;

            }
        } else
            Login(HomePage2Activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_finish:
                finish();
                break;
            case R.id.attention_tv:
                if (LoginStatusQuery()) {
                    if (!cover.isEmpty())
                        Attention();
                } else
                    Login(this);

                break;

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;
    }

    private void Attention() {

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
                        ToastUtil.toast2_bottom(HomePage2Activity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonInt(response)) {
                                    case 0:
                                        ToastUtil.toast2_bottom(HomePage2Activity.this, "成功取消关注");
                                        attention_tv.setText(getRouString(R.string.attention) + "Ta");
                                        break;
                                    case 1:
                                        ToastUtil.toast2_bottom(HomePage2Activity.this, "关注成功!");
                                        attention_tv.setText(getRouString(R.string.unattention));
                                        break;
                                }

                            } else
                                ToastUtil.ToastErrorMsg(HomePage2Activity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        tlistener = null;
        listener = null;
        slistener = null;
    }

    @Override
    public void onGoPlayPage(String id, int typeFlag) {

        if (LoginStatusQuery()) {
            goPlayerPage(HomePage2Activity.this, id, typeFlag);
        } else
            Login(HomePage2Activity.this);
    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
        ShareApp(HomePage2Activity.this, userid, name, info, id);
    }
}
