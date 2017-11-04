package example.com.fan.fragment.son;

import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.VdVrAdapter;
import example.com.fan.bean.VrVideoBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.ShareUtils.getSystemShare;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class VDVRFragment extends BaseFragment implements SpringListener, ItemClickListener, ShareRequestListener {
    private static final String TAG = getTAG(VDVRFragment.class);
    private List<VrVideoBean> rlist;
    private ShareRequestListener slistener;
    private SpringView springView;
    private ItemClickListener listener;
    private int tag;
    private int i;
    private ListView listView;
    private VdVrAdapter adapter;
    private int page = 0;

    public void setTag(int tag, final int i) {
        this.tag = tag;
        this.i = i;
    }

    @Override
    protected int initContentView() {
        return R.layout.vdvr_fragment;
    }

    protected void click() {

    }

    protected void init() {
        listener = this;
        slistener = this;
        springView = (SpringView) view.findViewById(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);
        listView = (ListView) view.findViewById(R.id.listView);

        rlist = new ArrayList<>();

    }

    @Override
    protected void initData() {
        newInstance(true);
    }

    @Override
    public void IsonRefresh(int i) {
//        this.onDownTouchListener(1, getRouString(R.string.find_find));
        page = i;
        switch (tag) {
            case 0:
                newInstance(true);
                break;
            case 1:
                newInstance(true);
                break;
        }
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        switch (tag) {
            case 0:
                newInstance(false);
                break;
            case 1:
                newInstance(false);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        switch (tag) {
            case 0:
                if (position == 0)
                    goPlayerPage(getActivity(), id, 4);
                if (position == 1)
                    goHomePage(getActivity(), id);
                break;
            case 1:
                if (position == 0)
                    goPlayerPage(getActivity(), id, 5);
                if (position == 1)
                    goHomePage(getActivity(), id);
                break;
        }
    }


    public void newInstance(boolean b) {

        switch (tag) {
            case 0:
                getData(MzFinal.GETVIDEO, b);
                break;
            case 1:

                getData(MzFinal.GETVRVIDEO, b);
                break;
        }

//        requestListGet(new HttpUtil.GetTaskCallBack() {
//            @Override
//            public void onTaskPostExecute(final String callBackStr) throws JSONException {
//                int code = getCode(callBackStr);
//                if (code == 1) {
//                    JSONArray ar = getJsonAr(callBackStr);
//                    for (int i = 0; i < 10; i++) {
//                        rlist.add("");
//                    }
//                    if (adapter != null)
//                        adapter.notifyDataSetChanged();
//                    else {
//                        adapter = new VdVrAdapter(getActivity(), rlist);
//                        listView.setAdapter(adapter);
//                    }
//
//                } else
//
//                    ToastUtil.ToastErrorMsg(getActivity(), callBackStr, code);
//
//            }
//        }, getActivity(), false, MzFinal.GETPHOTOTYPE, null, "");
    }

    private void getData(String URL, final boolean b) {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + URL)
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page+20))
                .addParams("model", String.valueOf(i))
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
                                for (int i = 0; i < ar.length(); i++) {
                                    VrVideoBean vb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), VrVideoBean.class);
                                    rlist.add(vb);
                                }

                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new VdVrAdapter(getActivity(), rlist, listener, slistener);
                                    listView.setAdapter(adapter);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
//        getSystemShare(getActivity(),id);
        getSystemShare(getActivity(),"");
    }
}
