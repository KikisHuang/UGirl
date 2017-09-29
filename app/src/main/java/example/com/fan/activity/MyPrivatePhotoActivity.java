package example.com.fan.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.MyPrivatePhotoAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/27.
 */
public class MyPrivatePhotoActivity extends InitActivity implements SpringListener, ItemClickListener {
    private static final String TAG = getTAG(MyPrivatePhotoActivity.class);
    private ListView listview;
    private SpringView springview;
    private int page = 0;
    private List<ModelBean> rlist;
    private MyPrivatePhotoAdapter adapter;
    private String id = "";
    private ItemClickListener listener;
    private ImageView nodata_img;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.my_private_layout);
        setTitles(this, "我的私照");
        listener = this;
        listview = f(R.id.listView);
        springview = f(R.id.springview);
        rlist = new ArrayList<>();
        nodata_img = f(R.id.nodata_img);
        SpringUtils.SpringViewInit(springview, this, this);
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {
        /**
         * 模特所属的私照或视频详情数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPRIVATERECORD)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.typeFlag, -2 + "")
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 20))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(MyPrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (b)
                                    rlist.clear();

                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0)
                                    nodata_img.setVisibility(View.GONE);

                                for (int i = 0; i < ar.length(); i++) {
                                    ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                    rlist.add(mb);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new MyPrivatePhotoAdapter(MyPrivatePhotoActivity.this, rlist, listener);
                                    listview.setAdapter(adapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(MyPrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });
    }

    @Override
    public void IsonRefresh(int i) {
        page = 0;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        goPrivatePhotoPage(this, id, 0);
    }
}
