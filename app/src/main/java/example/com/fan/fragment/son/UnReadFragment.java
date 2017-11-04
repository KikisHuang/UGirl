package example.com.fan.fragment.son;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.UnReadAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UnReadBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.PrivateVideoCheckPay;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class UnReadFragment extends BaseFragment implements SpringListener {
    private static final String TAG = getTAG(UnReadFragment.class);
    private UnReadAdapter adapter;
    private List<UnReadBean> rlist;
    private ListView listView;
    private FrameLayout no_data;
    private int tag;
    private SpringView springview;
    private int page = 0;

    public static Fragment setTag(int tag) {
//        this.tag = tag;
        UnReadFragment f = new UnReadFragment();
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments() != null ? getArguments().getInt("tag") : 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.unread_fragment;
    }

    public void newInstance(boolean b) {
        switch (tag) {
            case 0:
                getUnRead("0", b);
                break;
            case 1:
                getUnRead("4", b);
                break;
            case 2:
                getUnRead("5", b);
                break;
            case 3:
                getUnRead("-3", b);
                break;
            case 4:
                getUnRead("-2", b);
                break;
        }
    }

    private void getUnRead(String type, final boolean b) {
        /**
         * 未读;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETNOTREADRECORD)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 10))
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
                                if (b)
                                    rlist.clear();
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0) {

                                    for (int i = 0; i < ar.length(); i++) {
                                        UnReadBean ub = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), UnReadBean.class);
                                        rlist.add(ub);
                                    }
                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter = new UnReadAdapter(getActivity(), rlist);
                                        listView.setAdapter(adapter);
                                        Log.i(TAG, "setAdapter");
                                    }
                                    no_data.setBackgroundResource(R.color.white);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void click() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (rlist.size() > 0) {
                    switch (tag) {
                        case 0:
                            goPhotoPage(getActivity(), rlist.get(position).getId(), 0);
                            break;
                        case 1:
                            goPlayerPage(getActivity(), rlist.get(position).getId(), 4);
                            break;
                        case 2:
                            goPlayerPage(getActivity(), rlist.get(position).getId(), 5);
                            break;
                        case 3:
                            PrivateVideoCheckPay(getActivity(), listView, rlist.get(position).getId(), String.valueOf(rlist.get(position).getPrice()));
//                            goPlayerPage(getActivity(), rlist.get(position).getId(), -3);
                            break;
                        case 4:
                            goPrivatePhotoPage(getActivity(), rlist.get(position).getId(), 0);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void init() {
        listView = (ListView) view.findViewById(R.id.listView);
        no_data = (FrameLayout) view.findViewById(R.id.no_data);
        rlist = new ArrayList<>();
        springview = (SpringView) view.findViewById(R.id.springview);
        SpringUtils.SpringViewInit(springview, getActivity(), this);
    }

    @Override
    protected void initData() {
        newInstance(true);
    }

    @Override
    public void IsonRefresh(int i) {
        page = 0;
        newInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += 10;
        newInstance(false);
    }
}
