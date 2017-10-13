package example.com.fan.fragment.son;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import example.com.fan.adapter.RcModelAdapter;
import example.com.fan.bean.VrBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.mylistener.homepageListener;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SpringUtils.SpringViewInit;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/1.
 */


public class ModelRcFragment extends BaseFragment implements SpringListener, homepageListener, OverallRefreshListener, PositionAddListener, TwoParamaListener {
    private static final String TAG = getTAG(ModelRcFragment.class);
    private RecyclerView recyclerView;
    private RcModelAdapter rcadapter;
    private List<VrBean> rlist;
    private StaggeredGridLayoutManager mLayoutManager;

    private SpringView springview1;
    private SpringListener slistener;
    private TwoParamaListener tlistener;

    private int page = 0;
    private int tag;

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    protected int initContentView() {
        return R.layout.modelonline_fragment1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }

    public void newInstance() {
        Log.i(TAG, "   position ====" + tag);
        getData(true);
    }

    private void getData(final boolean b) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETSHOPPINGMALL)
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 20))
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

                            RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.loading_layout);
                            ll.setVisibility(View.GONE);
                            JSONArray ar = getJsonAr(response);
                            if (code == 1) {
                                if (b)
                                    rlist.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    VrBean vb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), VrBean.class);
                                    rlist.add(vb);
                                }
                                if (rcadapter != null) {
                                    rcadapter.notifyDataSetChanged();
                                } else {
                                    rcadapter = new RcModelAdapter(getActivity(), rlist,tlistener);
                                    recyclerView.setAdapter(rcadapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        slistener = this;
        tlistener = this;
        //注册观察者监听网络;
        ListenerManager.getInstance().registerListtener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        springview1 = (SpringView) view.findViewById(R.id.springview1);
        SpringViewInit(springview1, getActivity(), slistener);
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

    //刷新;
    @Override
    public void IsonRefresh(int i) {
        page = i;
        getData(true);
    }

    //下滑;
    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    //modelIcon点击事件;
    @Override
    public void onIconClick(String id) {
        goHomePage(getActivity(), id);
    }

    @Override
    public void notifyAllActivity(boolean net) {

        if (net) {
            Log.i(TAG, "   position ====" + tag);
            getData(false);
        }
    }

    @Override
    public void onIncrease() {
    }


    @Override
    public void onGoPlayPage(String id, int typeFlag) {
        if (LoginStatusQuery()) {
            goPlayerPage(getActivity(), id, typeFlag);
        } else
            Login(getActivity());
    }
}
