package example.com.fan.fragment.sgamer;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.MyProductionAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModeInfoBean;
import example.com.fan.bean.ModelBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TextViewColorUtils.setTextColor2;


/**
 * Created by lian on 2017/10/10.
 */
public class MyProductionFragment extends BaseFragment implements SpringListener, ItemClickListener {
    private static final String TAG = getTAG(MyProductionFragment.class);
    private TextView photo_tv, video_tv, income_tv;
    private SpringView springview;
    private ListView listView;
    private int page = 0;
    private ImageView cover_img;
    private LinearLayout top;
    private List<ModelBean> rlist;
    private ItemClickListener hlistener;
    private MyProductionAdapter adapter;

    @Override
    protected int initContentView() {
        return R.layout.myproduction_fragment;
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        top = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.myproduction_head_include, null);
        rlist = new ArrayList<>();
        hlistener = this;
        springview = (SpringView) view.findViewById(R.id.springview);
        SpringUtils.SpringViewInit(springview, getActivity(), this);
        listView = (ListView) view.findViewById(R.id.listView);
        photo_tv = (TextView) top.findViewById(R.id.photo_tv);
        cover_img = (ImageView) top.findViewById(R.id.cover_img);
        video_tv = (TextView) top.findViewById(R.id.video_tv);
        income_tv = (TextView) top.findViewById(R.id.income_tv);

    }


    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMODELINFO2)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, MzFinal.MYID)
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

                                JSONObject ob = getJsonOb(response);

                                ModeInfoBean mb = new Gson().fromJson(String.valueOf(ob), ModeInfoBean.class);
                                photo_tv.setText(getRouString(R.string.photo) + "\n" + mb.getPhotoCount());
                                video_tv.setText(getRouString(R.string.Video) + "\n" + mb.getVideoCount());
                                setTextColor2(income_tv, getRouString(R.string.income) + MzFinal.br, String.valueOf(mb.getSumOrder()), "#FF4D87");
                                Glide.with(getActivity()).load(mb.getCoverPath()).into(cover_img);

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPRIVATERECORD)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
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
                            if (code == 1) {
                                if (b)
                                    rlist.clear();
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0) {
                                    listView.setBackgroundResource(R.color.white);
                                    for (int i = 0; i < ar.length(); i++) {
                                        ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                        rlist.add(mb);
                                    }

                                    if (adapter != null)
                                        adapter.notifyDataSetChanged();
                                    else {
                                        adapter = new MyProductionAdapter(getActivity(), rlist, hlistener);
                                        listView.setAdapter(adapter);
                                    }
                                    if (listView.getHeaderViewsCount() == 0) {
                                        listView.addHeaderView(top);
                                    }
                                } else if (!b && ar.length() <= 0)
                                    listView.setBackgroundResource(R.drawable.account_back);

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });

    }

    @Override
    public void IsonRefresh(int i) {
        page += i;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        switch (position) {
            case 0:
                goPrivatePhotoPage(getActivity(), id, 0);
                break;
            case -3:
                goPlayerPage(getActivity(), id, -3);
                break;
            case 1002:
                goHomePage(getActivity(), id);
                break;
        }
    }
}
