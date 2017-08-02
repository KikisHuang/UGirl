package example.com.fan.fragment.son;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.MyCollect2Adapter;
import example.com.fan.adapter.MyCollectAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.MyCollectBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.DeleteListener;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.dialog.AlertDialog;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class MyCollectFragment extends BaseFragment implements ItemClickListener, DeleteListener {
    private static final String TAG = getTAG(MyCollectFragment.class);
    private RecyclerView recyclerView;
    private MyCollectAdapter adapter;
    private MyCollect2Adapter adapter2;
    private List<MyCollectBean> rlist;
    private StaggeredGridLayoutManager mLayoutManager;
    private ItemClickListener listener;
    private ListView listView;
    public TextView delete_tv;
    private FrameLayout no_data;
    private int tag;
    private DeleteListener deleteListener;
    private MyCollectFragment fragment;

    public void setTag(final int tag) {
        this.tag = tag;
    }

    @Override
    protected int initContentView() {
        return R.layout.mycollect_fragment;
    }

    public void newInstance() {
        if (rlist != null)
            rlist.clear();
        switch (tag) {
            case 0:
                getCollectPhoto();
                break;
            case 1:
                getCollectVideo();
                break;
            case 2:
                getCollectVR();
                break;
        }
    }

    //VR
    private void getCollectVR() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYCOLLECTIONVRVIDEO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "999")
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
                                for (int i = 0; i < ar.length(); i++) {
                                    MyCollectBean mb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), MyCollectBean.class);
                                    rlist.add(mb);
                                }
                                Log.i(TAG, "tag=====" + tag);
                                if (adapter2 != null)
                                    adapter2.notifyDataSetChanged();
                                else {
                                    adapter2 = new MyCollect2Adapter(getActivity(), rlist, deleteListener, fragment, listener);
                                    listView.setAdapter(adapter2);
                                    Log.i(TAG, "setAdapter");
                                }
                                recyclerView.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                                no_data.setBackgroundResource(R.color.white);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    //视频
    private void getCollectVideo() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYCOLLECTIONVIDEO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "999")
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
                                for (int i = 0; i < ar.length(); i++) {
                                    JSONObject ob = ar.optJSONObject(i);
                                    MyCollectBean mb = new MyCollectBean();
                                    mb.setCoverPath(ob.optString("coverPath"));
                                    mb.setMcPublishRecord(ob.optString("id"));
                                    mb.setUserName(ob.optString("userName"));
                                    rlist.add(mb);
                                }
                                Log.i(TAG, "tag=====" + tag);
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new MyCollectAdapter(getActivity(), rlist, listener, delete_tv, deleteListener, fragment);
                                    adapter.setSwitch(false);
                                    recyclerView.setAdapter(adapter);
                                    Log.i(TAG, "setAdapter");
                                }

                                recyclerView.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                                no_data.setBackgroundResource(R.color.white);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (deleteListener != null)
            deleteListener = null;
        if (listener != null)
            listener = null;
    }

    //专辑私照
    private void getCollectPhoto() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYCOLLECTIONPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "999")
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
                                if (rlist != null)
                                    rlist.clear();
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    MyCollectBean mb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), MyCollectBean.class);
                                    rlist.add(mb);
                                }
                                Log.i(TAG, "tag=====" + tag);
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new MyCollectAdapter(getActivity(), rlist, listener, delete_tv, deleteListener, fragment);
                                    adapter.setSwitch(false);
                                    recyclerView.setAdapter(adapter);
                                    Log.i(TAG, "setAdapter");
                                }

                                recyclerView.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                                no_data.setBackgroundResource(R.color.white);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    public void TvShow() {
        delete_tv.setVisibility(View.VISIBLE);
        adapter.setSwitch(true);
        adapter.notifyDataSetChanged();
    }


    public void TvHide() {
        delete_tv.setVisibility(View.GONE);
        adapter.setSwitch(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void click() {

    }
    @Override
    protected  void init() {
        fragment = this;
        deleteListener = this;
        listener = this;
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        listView = (ListView) view.findViewById(R.id.listView);
        no_data = (FrameLayout) view.findViewById(R.id.no_data);
        delete_tv = (TextView) view.findViewById(R.id.delete_tv);
        rlist = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        newInstance();
    }


    @Override
    public void onItemClickListener(int position, String id) {

        switch (tag) {
            case 0:
                goPhotoPage(getActivity(), id, 0);
                break;
            case 1:
                goPlayerPage(getActivity(), id, 4);
                break;
            case 2:
                goPlayerPage(getActivity(), id, 5);
                break;
        }

    }

    @Override
    public void onDelete(final List<String> list) {
        switch (tag) {
            case 0:
                for (int i = 0; i < list.size(); i++) {
                    DeletePhotoCollect(list.get(i));
                }

                break;
            case 1:
                for (int i = 0; i < list.size(); i++) {
                    DeleteCollectVideo(list.get(i));
                }
                break;
            case 2:
                new AlertDialog(getActivity()).builder().setCancelable(true).setMsg("确定删除吗？").setNegativeButton("", null).setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < list.size(); i++) {
                            DeleteCollectVideo(list.get(i));
                        }
                    }
                }).show();
                break;

        }
    }

    /**
     * 删除视频收藏;
     */
    private void DeleteCollectVideo(final String id) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.COLLECTIONVIDEO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
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
                                switch (getJsonSring(response)) {
                                    case "1":

                                        break;
                                    case "0":
                                        for (int j = 0; j < rlist.size(); j++) {
                                            if (rlist.get(j).getMcPublishRecord().equals(id))
                                                rlist.remove(j);
                                        }
                                        if (tag == 1)
                                            adapter.notifyDataSetChanged();
                                        if (tag == 2)
                                            adapter2.notifyDataSetChanged();
                                        ToastUtil.toast2_bottom(getActivity(), "取消收藏!");
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void DeletePhotoCollect(final String id) {
        /**
         收藏接口(二次发送就是取消收藏,即删除);
         **/
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.COLLECTIONPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
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
                                switch (getJsonSring(response)) {
                                    case "0":
                                        for (int j = 0; j < rlist.size(); j++) {
                                            if (rlist.get(j).getMcPublishRecord().equals(id))
                                                rlist.remove(j);
                                        }
                                        adapter.notifyDataSetChanged();
                                        ToastUtil.toast2_bottom(getActivity(), "删除成功");
                                        break;
                                    default:
                                        ToastUtil.toast2_bottom(getActivity(), "未知错误..");
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        }
}
