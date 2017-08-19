package example.com.fan.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.gson.Gson;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PrivatePhotoAdapter;
import example.com.fan.bean.PrivateBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener, ItemClickListener,ShareRequestListener {
    private static final String TAG = getTAG(TestActivity.class);
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<PrivateBean> rlist ;
    private PrivatePhotoAdapter adapter;
    private ShareRequestListener slistener;
    private ItemClickListener listener;
    @Override
    protected void click() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        recyclerView = f(R.id.recyclerview);
        listener = this;
        slistener = this;
        rlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        newInstance();
    }

    private void Cardinit() {
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        CardConfig.initConfig(this);
        ItemTouchHelper.Callback callback = new RenRenCallback(recyclerView, adapter, rlist);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    private void newInstance() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPHOTOBYTYPE)
                .addParams("typeId", "test4")
                .addParams(MzFinal.PAGE, String.valueOf(0))
                .addParams(MzFinal.SIZE, "" + 20)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(TestActivity.this, "网络不顺畅...");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                rlist.clear();

                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    PrivateBean pt = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PrivateBean.class);
                                    rlist.add(pt);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new PrivatePhotoAdapter(getApplicationContext(), rlist, listener,slistener);
                                    recyclerView.setAdapter(adapter);

                                }
                                Cardinit();
                            } else
                                ToastUtil.ToastErrorMsg(TestActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onItemClickListener(int position, String id) {
        ToastUtil.toast2_bottom(this," id =="+id);
    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
        ToastUtil.toast2_bottom(this," id =="+id);
    }
}
