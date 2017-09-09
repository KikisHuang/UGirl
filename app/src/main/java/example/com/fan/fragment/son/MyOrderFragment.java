package example.com.fan.fragment.son;

import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.MyOrderAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.MyOrderBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.MyOrderDetailsListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goMyOrderDetailsPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class MyOrderFragment extends BaseFragment implements MyOrderDetailsListener, SpringListener {
    private static final String TAG = getTAG(MyOrderFragment.class);
    private MyOrderAdapter adapter;
    private List<MyOrderBean> rlist;
    private MyOrderDetailsListener listener;
    private SpringView springView;
    private ListView listView;
    private FrameLayout no_data;
    private int tag;
    private int pageSize = 20;

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    protected int initContentView() {
        return R.layout.myorder_fragment;
    }

    public void newInstance() {
        switch (tag) {
            case 0:
                getAllOrder("");
                break;
            case 1:
                getAllOrder("1");
                break;
            case 2:
                getAllOrder("-3");
                break;
        }
    }

    private void getAllOrder(String sta) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYORDERBYPAGE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("status", sta)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, String.valueOf(pageSize))
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
                                if (ar.length() > 0)
                                    no_data.setBackgroundResource(R.color.content7);

                                for (int i = 0; i < ar.length(); i++) {
                                    MyOrderBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), MyOrderBean.class);
                                    rlist.add(mb);
                                }
                                Log.i(TAG, "tag=====" + tag);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                    Log.i(TAG, "notifyDataSetChanged");
                                } else {
                                    adapter = new MyOrderAdapter(getActivity(), rlist, listener, tag);
                                    listView.setAdapter(adapter);
                                    Log.i(TAG, "setAdapter");
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    protected void click() {

    }

    protected void init() {
        listener = this;
        listView = (ListView) view.findViewById(R.id.listView);
        no_data = (FrameLayout) view.findViewById(R.id.no_data);
        rlist = new ArrayList<>();

        springView = (SpringView) view.findViewById(R.id.springview);
        SpringUtils.SpringViewInit(springView, getActivity(), this);
    }

    @Override
    protected void initData() {
        newInstance();
    }


    @Override
    public void IsonRefresh() {
        pageSize = 20;
        newInstance();
    }

    @Override
    public void IsonLoadmore() {
        pageSize += 20;
        newInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onDetails(MyOrderBean mb) {
        goMyOrderDetailsPage(getActivity(), mb);
    }
}
