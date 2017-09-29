package example.com.fan.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.MyVideolAdapter;
import example.com.fan.bean.StoreBean;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SpringUtils.SpringViewInit;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/27.
 */
public class MyVideoActivity extends InitActivity implements SpringListener {

    private RecyclerView recyclerView;
    private MyVideolAdapter rcadapter;
    private List<StoreBean> rlist;
    private StaggeredGridLayoutManager mLayoutManager;

    private SpringView springview1;
    private SpringListener slistener;
    private int page = 0;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.my_video_layout);
        setTitles(this, "我的视频");
        slistener = this;
        recyclerView = f(R.id.recyclerView);
        springview1 = f(R.id.springview1);
        SpringViewInit(springview1, this, slistener);
        rlist = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列

        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETSHOPPINGMALL)
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page+20))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(MyVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);

                            RelativeLayout ll = f(R.id.loading_layout);
                            ll.setVisibility(View.GONE);
                            JSONArray ar = getJsonAr(response);
                            if (code == 1) {
                                if (b)
                                rlist.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    StoreBean sb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), StoreBean.class);
                                    rlist.add(sb);
                                }
                                if (rcadapter != null) {
                                    rcadapter.notifyDataSetChanged();
                                } else {
                                    rcadapter = new MyVideolAdapter(MyVideoActivity.this, rlist);
                                    recyclerView.setAdapter(rcadapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(MyVideoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
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
}
