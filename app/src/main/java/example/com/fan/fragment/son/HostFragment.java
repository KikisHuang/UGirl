package example.com.fan.fragment.son;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostAdapter;
import example.com.fan.bean.NewstHostBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.VipAndModelItemClickListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class HostFragment extends BaseFragment implements SpringListener, VipAndModelItemClickListener {
    private static final String TAG = getTAG(HostFragment.class);
    private List<NewstHostBean> rlist;
    private StaggeredGridLayoutManager mLayoutManager;
    private SpringView springView1;
    private VipAndModelItemClickListener listener;
    private int tag;
    private RecyclerView recyclerView;
    private HostAdapter adapter;
    private int size = 20;
    private boolean flag;

    public void setTag(final boolean flag, int tag) {
        this.tag = tag;
        this.flag = flag;
    }


    @Override
    protected int initContentView() {
        return R.layout.host_fragment;
    }

    protected void click() {

    }

    protected void init() {
        listener = this;
        springView1 = (SpringView) view.findViewById(R.id.springview1);
        SpringUtils.SpringViewInit(springView1, getActivity(), this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列

        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void initData() {
        newInstance();
    }

    @Override
    public void IsonRefresh() {
        size = 20;
        newInstance();
    }

    @Override
    public void IsonLoadmore() {
        size += 20;
        newInstance();
    }


    public void newInstance() {

        Log.i(TAG, "   position ====" + tag);
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPHOTOBYTYPE)
                .addParams("typeId", String.valueOf(tag))
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "" + size)
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
                                JSONArray ar = getJsonAr(response);
                                if (rlist == null)
                                    rlist = new ArrayList<>();
                                else
                                    rlist.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    NewstHostBean nb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), NewstHostBean.class);
                                    rlist.add(nb);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new HostAdapter(getActivity(), rlist, listener, flag);
                                    recyclerView.setAdapter(adapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);

                            view.findViewById(R.id.loading_layout).setVisibility(View.GONE);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        goHomePage(getActivity(), id);
    }
}
