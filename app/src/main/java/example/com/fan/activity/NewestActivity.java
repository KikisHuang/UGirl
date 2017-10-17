package example.com.fan.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.FindAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.TitleUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.ShareUtils.getSystemShare;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/1.
 * 最新页面;
 */
public class NewestActivity extends BaseActivity implements ItemClickListener, SpringListener, TwoParamaListener, ShareRequestListener {
    private static final String TAG = getTAG(NewestActivity.class);
    private SpringView springview;
    private ListView listView;
    private List<ModelBean> rlist;
    private FindAdapter adapter;
    private ItemClickListener listener;
    private TwoParamaListener tlistener;
    private View top;
    private int page = 0;
    private ShareRequestListener slistener;


    private void addHeader() {
        LayoutInflater inflater = getLayoutInflater();
        top = inflater.inflate(R.layout.newest_top, null);
        if (listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(top);
        }
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private void getData(final boolean b) {
        /**
         * 模特所属的私照或视频详情数据;
         */
        final long start = System.currentTimeMillis();
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPUBLISHRECORDBYMODEL)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 20))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(NewestActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            long end = System.currentTimeMillis();
                            String a = String.valueOf(end - start);
                            Log.i(TAG, "" + a + "ms");
                            int code = getCode(response);
                            if (b)
                                rlist.clear();
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                    rlist.add(mb);
                                }
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter = new FindAdapter(NewestActivity.this, rlist, listener, tlistener, slistener, false);
                                    listView.setAdapter(adapter);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(NewestActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    protected void init() {
        setContentView(R.layout.newest_activity_layout);
        TitleUtils.setTitles(NewestActivity.this, getResources().getString(R.string.newest));
        listener = this;
        tlistener = this;
        slistener = this;
        rlist = new ArrayList<>();
        listView = f(R.id.listView);
        springview = f(R.id.springview);
        SpringUtils.SpringViewInit(springview, this, this);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        if (LoginStatusQuery()) {
            switch (position) {
                case 0:
                    goPhotoPage(NewestActivity.this, id, 0);
                    break;
                case -2:
                    goPrivatePhotoPage(NewestActivity.this, id, 0);
                    break;
                case 1:
                    goHomePage(NewestActivity.this, id);
                    break;
                case -3:

                    break;
            }
        } else
            Login(NewestActivity.this);
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
    public void initData() {
        getData(true);
        addHeader();
    }

    @Override
    public void onGoPlayPage(String id, int typeFlag) {
        if (LoginStatusQuery()) {
            goPlayerPage(NewestActivity.this, id, typeFlag);
        } else
            Login(NewestActivity.this);
    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
//        ShareApp(NewestActivity.this, userid, name, info, id);
        getSystemShare(NewestActivity.this,id);
    }
}
