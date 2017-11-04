package example.com.fan.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import example.com.fan.adapter.SearchAdapter;
import example.com.fan.adapter.SearchGridAdapter;
import example.com.fan.bean.SearchBean;
import example.com.fan.bean.SearchTagBean;
import example.com.fan.mylistener.SearchEditChangedListener;
import example.com.fan.mylistener.SearchItemListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.GrapeGridview;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 搜索页面;
 */
public class SearchActivity extends InitActivity implements View.OnClickListener, SearchItemListener {
    private static final String TAG = getTAG(SearchActivity.class);

    private GrapeGridview model_grid, tag_grid;
    private List<SearchTagBean> mlist;
    private List<SearchTagBean> tlist;
    private SearchGridAdapter madapter;
    private SearchGridAdapter tadapter;
    private TextView cancel_tv;
    private SearchItemListener listener;
    private EditText search_edit;
    private ImageView no_data, search_img, clear_img;
    private ListView listView;
    private List<SearchBean> slist;
    private SearchAdapter adapter;
    private int dataSize = 0;
    private Handler handler;


    private void handInit() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        no_data.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        break;
                    case 2:
                        listView.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                        else {
                            adapter = new SearchAdapter(slist, SearchActivity.this, listener);
                            listView.setAdapter(adapter);
                        }
                        break;
                }

            }
        };
    }

    private void EditSearchListener() {
        search_edit.addTextChangedListener(new SearchEditChangedListener(listener));
    }

    @Override
    protected void click() {
        cancel_tv.setOnClickListener(this);
        clear_img.setOnClickListener(this);
        search_img.setOnClickListener(this);
        EditSearchListener();
    }

    private void getData() {
        /**
         * 获取模特、标签数据;
         */
        for (int i = 0; i < 2; i++) {
            mlist.clear();
            tlist.clear();
            final int finalI = i;

            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.getHotSearch)
                    .addParams(MzFinal.TYPE, String.valueOf(i))
                    .addParams(MzFinal.PAGE, "0")
                    .addParams(MzFinal.SIZE, "20")
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(SearchActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    JSONArray ar = getJsonAr(response);
                                    for (int j = 0; j < ar.length(); j++) {
                                        SearchTagBean sb = new Gson().fromJson(String.valueOf(ar.optJSONObject(j)), SearchTagBean.class);
                                        switch (finalI) {
                                            case 0:
                                                mlist.add(sb);
                                                if (j == ar.length() - 1) {
                                                    madapter = new SearchGridAdapter(SearchActivity.this, mlist, listener, finalI);
                                                    model_grid.setAdapter(madapter);
                                                }
                                                break;
                                            case 1:
                                                tlist.add(sb);
                                                if (j == ar.length() - 1) {
                                                    tadapter = new SearchGridAdapter(SearchActivity.this, tlist, listener, finalI);
                                                    tag_grid.setAdapter(tadapter);
                                                }
                                                break;
                                        }
                                    }
                                } else
                                    ToastUtil.ToastErrorMsg(SearchActivity.this, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });
        }

    }

    @Override
    protected void init() {
        setContentView(R.layout.search_layout);
        listener = this;
        tlist = new ArrayList<>();
        mlist = new ArrayList<>();
        slist = new ArrayList<>();

        model_grid = f(R.id.model_grid);
        cancel_tv = f(R.id.cancel_tv);
        tag_grid = f(R.id.tag_grid);
        no_data = f(R.id.no_data);
        search_edit = f(R.id.search_edit);
        clear_img = f(R.id.clear_img);
        listView = f(R.id.listView);
        search_img = f(R.id.search_img);
        handInit();
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.search_img:
                if (search_edit.getText().toString().trim().length() > 0)
                    listener.onSearch(true, search_edit.getText().toString().trim());
                break;
            case R.id.clear_img:
                if (search_edit.getText().toString().trim().length() > 0) {

                    if (adapter != null)
                        adapter.notifyDataSetChanged();

                    search_edit.setText("");
                }
                break;

        }
    }

    @Override
    public void onItemClick(int tag, String id) {

        switch (tag) {
            case 0:
                goHomePage(SearchActivity.this, id);
                break;
            case 1:
                goPhotoPage(SearchActivity.this, id, 0);
                break;
            case 4:
                goPlayerPage(SearchActivity.this, id, 4);
                break;
  /*          case -3:
              PrivateVideoCheckPay(SearchActivity.this, listView, tlist.get(pos).getId(), String.valueOf(tlist.get(pos).get));
                goPlayerPage(SearchActivity.this, id, -3);
                break;
            case -2:
                goPrivatePhotoPage(SearchActivity.this, id, 0);
                break;*/
            case 5:
                goPlayerPage(SearchActivity.this, id, 5);
                break;
            case 10:
                goHomePage(SearchActivity.this, id);
                break;
            case 11:
                listener.onSearch(true, id);
                break;
        }
    }

    @Override
    public void onSearch(boolean flag, final String s) {
        if (flag) {
            Log.i(TAG, "onSearch");
            slist.clear();
            if (adapter != null)
                adapter.notifyDataSetChanged();

            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.SEARCH)
                    .addParams("search", s)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(SearchActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);

                                if (code == 1) {
                                    Message message = new Message();
                                    dataSize = 0;
                                    JSONArray ar = getJsonAr(response);

                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONArray ar1 = ar.optJSONArray(i);
                                        dataSize += ar1.length();
                                        NewBean(ar1, i);
                                    }

                                    if (dataSize > 0) {
                                        message.what = 2;
                                        handler.sendMessage(message);
                                    } else {
                                        message.what = 1;
                                        handler.sendMessage(message);
                                    }

                                } else
                                    ToastUtil.ToastErrorMsg(SearchActivity.this, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });


        } else {
            no_data.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
    }

    /**
     * 将3个实体类集合成一个,便于适配器数据展示以及跳转;
     *
     * @param ar1
     * @param i
     */
    private void NewBean(JSONArray ar1, int i) {

        for (int j = 0; j < ar1.length(); j++) {
            JSONObject ob = ar1.optJSONObject(j);
            SearchBean sb = new SearchBean();

            switch (i) {
                case 0:
                    sb.setCoverPath(ob.optString("headImgUrl"));
                    sb.setId(ob.optString("id"));
                    sb.setName(ob.optString("name"));
                    sb.setTypeFlag(0);
                    break;
                case 1:
                    sb.setCoverPath(ob.optString("coverPath"));
                    sb.setId(ob.optString("id"));
                    sb.setName(ob.optString("name"));
                    sb.setTypeFlag(ob.optInt("typeFlag"));
                    break;
                case 2:
                    sb.setCoverPath(ob.optString("coverPath"));
                    sb.setId(ob.optString("id"));
                    sb.setName(ob.optString("name"));
                    sb.setTypeFlag(1);
                    break;
            }
            slist.add(sb);
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
