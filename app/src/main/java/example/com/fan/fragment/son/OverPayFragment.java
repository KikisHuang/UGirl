package example.com.fan.fragment.son;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.OverPay2Adapter;
import example.com.fan.adapter.OverPayAdapter;
import example.com.fan.adapter.OverPayWxAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.OverPayBean;
import example.com.fan.bean.OverPayVideoVrBean;
import example.com.fan.bean.OverPayWxBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.MzFinal;
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
 * Created by lian on 2017/5/5.
 */
public class OverPayFragment extends BaseFragment implements ItemClickListener, TwoParamaListener, ShareRequestListener {
    private static final String TAG = getTAG(OverPayFragment.class);
    private RecyclerView recyclerView;
    private OverPayAdapter adapter;
    private OverPay2Adapter adapter2;
    private List<OverPayBean> rlist;
    private List<OverPayVideoVrBean> vlist;
    private List<OverPayWxBean> wxlist;
    private StaggeredGridLayoutManager mLayoutManager;
    private ItemClickListener listener;
    private TwoParamaListener tlistener;
    private ShareRequestListener slistener;
    private ListView listView;
    private FrameLayout no_data;
    private int tag;
    private int pagesize = 999;
    private OverPayWxAdapter adapter3;

    public void setTag(int tag) {
        this.tag = tag;
    }


    @Override
    protected int initContentView() {
        return R.layout.over_fragment;
    }

    public void newInstance() {
        switch (tag) {
            case 0:
                Create("0");
                break;
            case 1:
                Create2("4");
                break;
            case 2:
                Create2("5");
                break;
            case 3:
                wxlist = new ArrayList<>();
                Create3();
                break;
        }
    }

    private void Create3() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYMODELWXBYPAGE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, String.valueOf(pagesize))
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
                                wxlist.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    OverPayWxBean ob = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), OverPayWxBean.class);
                                    wxlist.add(ob);
                                }
                                if (adapter3 != null) {
                                    adapter3.notifyDataSetChanged();
                                    Log.i(TAG, "notifyDataSetChanged");
                                } else {
                                    adapter3 = new OverPayWxAdapter(getActivity(), wxlist);
                                    listView.setAdapter(adapter3);
                                    Log.i(TAG, "setAdapter");
                                }
                                listView.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);

                                no_data.setBackgroundResource(R.color.white);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void Create2(String type) {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETSELLRECORD)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, String.valueOf(pagesize))
                .addParams(MzFinal.TYPE, type)
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
                                vlist.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    OverPayVideoVrBean ob = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), OverPayVideoVrBean.class);
                                    vlist.add(ob);
                                }
                                if (adapter2 != null) {
                                    adapter2.notifyDataSetChanged();
                                    Log.i(TAG, "notifyDataSetChanged");
                                } else {
                                    adapter2 = new OverPay2Adapter(vlist, getActivity(), listener, tlistener, slistener);
                                    listView.setAdapter(adapter2);
                                    Log.i(TAG, "setAdapter");
                                }
                                listView.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);

                                no_data.setBackgroundResource(R.color.white);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void Create(String type) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPURCHASEBYPAGE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, String.valueOf(pagesize))
                .addParams(MzFinal.TYPE, type)
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
                                rlist.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    OverPayBean ob = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), OverPayBean.class);
                                    rlist.add(ob);
                                }

                                Log.i(TAG, "tag=====" + tag);
                                if (tag < 1) {
                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged();
                                        Log.i(TAG, "notifyDataSetChanged");
                                    } else {
                                        adapter = new OverPayAdapter(getActivity(), rlist, listener);
                                        recyclerView.setAdapter(adapter);
                                        Log.i(TAG, "setAdapter");
                                    }
                                    recyclerView.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                } else {
                                }
                                no_data.setBackgroundResource(R.color.white);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listener != null)
            listener = null;
        if (tlistener != null)
            tlistener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        listener = this;
        tlistener = this;
        slistener = this;
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        listView = (ListView) view.findViewById(R.id.listView);
        no_data = (FrameLayout) view.findViewById(R.id.no_data);
        rlist = new ArrayList<>();
        vlist = new ArrayList<>();
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
    public void onItemClickListener(int position, String id) {
        if (LoginStatusQuery()) {
            Log.i(TAG, "typeFlag ====== " + position);
            switch (position) {
                case 0:
                    goPhotoPage(getActivity(), id, 0);
                    break;
                case -2:
                    goPrivatePhotoPage(getActivity(), id, 0);
                    break;
                case 2:
                    goHomePage(getActivity(), id);
                    break;
            }
        } else
            Login(getActivity());
    }

    @Override
    public void onGoPlayPage(String id, int typeFlag) {
        goPlayerPage(getActivity(), id, typeFlag);
    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
//        ShareApp(getActivity(), userid, name, info, id);
        getSystemShare(getActivity(),id);
    }
}
