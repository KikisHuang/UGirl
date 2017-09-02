package example.com.fan.fragment.son;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import example.com.fan.adapter.PrivatePhotoAdapter;
import example.com.fan.bean.PrivateBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.RecyclerViewScrollDetector;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.ShareUtils.ShareApp;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class PrivateFragment2 extends BaseFragment implements SpringListener, ItemClickListener ,ShareRequestListener{
    private static final String TAG = getTAG(PrivateFragment2.class);
    private RecyclerView recyclerView;
    private PrivatePhotoAdapter adapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private SpringView springView;
    private ItemClickListener listener;
    private boolean control;
    private RecyclerViewScrollDetector rs;
    private RelativeLayout loading_layout;
    private String Typeid = "";
    private int size = 20;
    private int page = 0;
    private List<PrivateBean> rlist;
    private ShareRequestListener slistener;

    public static PrivateFragment2 setTag(String Typeid, boolean control) {
        PrivateFragment2 f = new PrivateFragment2();
        Bundle args = new Bundle();
        args.putString("Typeid", Typeid);
        args.putBoolean("control", control);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeid = getArguments() != null ? getArguments().getString("Typeid") : "0";
        control = getArguments() != null ? getArguments().getBoolean("control") : false;
    }

    @Override
    protected void click() {

    }

    private void newInstance() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPHOTOBYTYPE)
                .addParams("typeId", Typeid)
                .addParams(MzFinal.PAGE, String.valueOf(page))
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
                                    rlist.clear();

                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    PrivateBean pt = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PrivateBean.class);
                                    rlist.add(pt);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new PrivatePhotoAdapter(getActivity().getApplicationContext(), rlist, listener,slistener);
                                    recyclerView.setAdapter(adapter);
                                }

                                loading_layout.setVisibility(View.GONE);

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected int initContentView() {
        return R.layout.private2_layout;
    }

    protected void init() {
        listener = this;
        slistener = this;
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        springView = (SpringView) view.findViewById(R.id.springview);
        loading_layout = (RelativeLayout) view.findViewById(R.id.loading_layout);
        SpringUtils.SpringViewInit(springView, getActivity(), this);

        rs = new RecyclerViewScrollDetector(this, getRouString(R.string.private_photo));
        rlist = new ArrayList<>();

        recyclerView.setHasFixedSize(true);

        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);

        if (control) {
            recyclerView.setOnScrollListener(rs);
            rs.setScrollThreshold(25);
        }
    }

    @Override
    protected void initData() {
        newInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void IsonRefresh() {
        if (control)
            this.onDownTouchListener(1, getRouString(R.string.private_photo));
        size = 20;
        newInstance();

    }

    @Override
    public void IsonLoadmore() {
        size += 20;
        newInstance();
    }

    @Override
    public void onItemClickListener(int position, String id) {

        switch (position) {
            case 0:
                if (LoginStatusQuery())
                    goHomePage(getActivity(), id);
                else
                    Login(getActivity());
                break;
            case 1:
                if (LoginStatusQuery())
                    goPhotoPage(getActivity(), id, 0);
                else
                    Login(getActivity());
                break;

        }

    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
        ShareApp(getActivity(), userid, name, info, id);
    }
}
