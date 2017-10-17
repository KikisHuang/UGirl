package example.com.fan.fragment.son;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.RankingAdapter;
import example.com.fan.bean.RankingBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.WswitchWay;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class RankingFragment extends BaseFragment implements ItemClickListener, View.OnClickListener, SpringListener {
    private static final String TAG = getTAG(RankingFragment.class);
    private ListView listView;
    private RankingAdapter adapter;
    private List<RankingBean> rlist;
    private View top;
    private int page = 0;
    private int tag;
    private SpringView springview1;
    private ItemClickListener listener;

    public void setTag(final int tag) {
        this.tag = tag;
    }

    public void newInstance(final boolean b) {
        Log.i(TAG, "   position ====" + tag);

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETRANKING)
                .addParams("code", String.valueOf(tag))
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 20))
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
                                if (tag <= 1) {
                                    for (int i = 0; i < ar.length(); i++) {
                                        RankingBean rb = new RankingBean();
                                        rb.setId(ar.optJSONObject(i).optString("id"));
                                        rb.setFollwCount(ar.optJSONObject(i).optString("collectionCount"));
                                        rb.setHeadImgUrl(ar.optJSONObject(i).optString("coverPath"));
                                        rb.setName(ar.optJSONObject(i).optString("user_name"));
                                        rlist.add(rb);
                                    }
                                } else {
                                    for (int i = 0; i < ar.length(); i++) {
                                        RankingBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), RankingBean.class);
                                        rlist.add(rb);
                                    }
                                }
                                if (rlist.size() > 0)
                                    addHead(rlist, getActivity(), tag);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void addHead(List<RankingBean> rlist, Context context, int tag) {
        top = LayoutInflater.from(context).inflate(R.layout.ranking_top_item, null);
        listView.setBackgroundResource(R.color.white);

        ImageView img0 = (ImageView) top.findViewById(R.id.top_icon0);
        TextView tv0 = (TextView) top.findViewById(R.id.top_name0);
        TextView num0 = (TextView) top.findViewById(R.id.top_sub_number0);

        ImageView img1 = (ImageView) top.findViewById(R.id.top_icon1);
        TextView tv1 = (TextView) top.findViewById(R.id.top_name1);
        TextView num1 = (TextView) top.findViewById(R.id.top_sub_number1);

        ImageView img2 = (ImageView) top.findViewById(R.id.top_icon2);
        TextView tv2 = (TextView) top.findViewById(R.id.top_name2);
        TextView num2 = (TextView) top.findViewById(R.id.top_sub_number2);

        FrameLayout bottom_fl = (FrameLayout) top.findViewById(R.id.bottom_fl);
        FrameLayout top_fl = (FrameLayout) top.findViewById(R.id.top_fl);
        FrameLayout mid_fl = (FrameLayout) top.findViewById(R.id.mid_fl);
        top_fl.setOnClickListener(this);
        mid_fl.setOnClickListener(this);
        bottom_fl.setOnClickListener(this);
        try {

            if (tag <= 1) {
                if (tag == 1) {
                    if (rlist.size() > 0) {
                        Glide.with(getActivity().getApplicationContext()).load(rlist.get(0).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img0);
                        tv0.setText(rlist.get(0).getName());
//                    num0.setText(getRouString(R.string.subscription) + rlist.get(0).getFollwCount() + "万"WswitchWay());
                        num0.setText(getRouString(R.string.subscription) + WswitchWay(Double.parseDouble(rlist.get(0).getFollwCount())));
                    }
                    if (rlist.size() > 1) {
                        Glide.with(getActivity().getApplicationContext()).load(rlist.get(1).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img1);
                        tv1.setText(rlist.get(1).getName());
                        num1.setText(getRouString(R.string.subscription) + WswitchWay(Double.parseDouble(rlist.get(1).getFollwCount())));
                    }
                    if (rlist.size() > 2) {
                        Glide.with(getActivity().getApplicationContext()).load(rlist.get(2).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img2);
                        tv2.setText(rlist.get(2).getName());
                        num2.setText(getRouString(R.string.subscription) + WswitchWay(Double.parseDouble(rlist.get(2).getFollwCount())));
                    }
                } else {
                    tv0.setText(rlist.get(0).getName());
                    tv1.setText(rlist.get(1).getName());
                    tv2.setText(rlist.get(2).getName());

                    num0.setText("");
                    num1.setText("");
                    num2.setText("");

                    Glide.with(getActivity().getApplicationContext()).load(rlist.get(0).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img0);
                    Glide.with(getActivity().getApplicationContext()).load(rlist.get(1).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img1);
                    Glide.with(getActivity().getApplicationContext()).load(rlist.get(2).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img2);
                }
            } else {
                if (rlist.size() > 0) {
                    Glide.with(getActivity().getApplicationContext()).load(rlist.get(0).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img0);
                    tv0.setText(rlist.get(0).getName());
                    num0.setText("被" + WswitchWay(Double.parseDouble(rlist.get(0).getFollwCount())) + "人" + getRouString(R.string.attention));
                }

                if (rlist.size() > 1) {
                    Glide.with(getActivity().getApplicationContext()).load(rlist.get(1).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img1);
                    tv1.setText(rlist.get(1).getName());
                    num1.setText("被" + WswitchWay(Double.parseDouble(rlist.get(1).getFollwCount())) + "人" + getRouString(R.string.attention));
                }
                if (rlist.size() > 2) {
                    Glide.with(getActivity().getApplicationContext()).load(rlist.get(2).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(img2);
                    tv2.setText(rlist.get(2).getName());
                    num2.setText("被" + WswitchWay(Double.parseDouble(rlist.get(2).getFollwCount())) + "人" + getRouString(R.string.attention));
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        if (listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(top);
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            Log.i(TAG, "notifyDataSetChanged");
        } else {
            adapter = new RankingAdapter(rlist, getActivity(), listener);
            listView.setAdapter(adapter);
            Log.i(TAG, "setAdapter");
            adapter.setTag(tag);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.ranking_fragment;
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        listView = (ListView) view.findViewById(R.id.listView);
        springview1 = (SpringView) view.findViewById(R.id.springview1);
        SpringUtils.SpringViewInit(springview1, getActivity(), this);
        rlist = new ArrayList<>();
        listener = this;
    }

    @Override
    protected void initData() {
        newInstance(true);
    }

    @Override
    public void onItemClickListener(int position, String id) {

        if (position > 1)
            goHomePage(getActivity(), id);
        else
            goPhotoPage(getActivity(), id, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_fl:
                if (rlist.size() > 2)
                    this.onItemClickListener(tag, rlist.get(0).getId());
                break;
            case R.id.mid_fl:
                if (rlist.size() > 2)
                    this.onItemClickListener(tag, rlist.get(1).getId());
                break;
            case R.id.bottom_fl:
                if (rlist.size() > 2)
                    this.onItemClickListener(tag, rlist.get(2).getId());
                break;

        }
    }

    @Override
    public void IsonRefresh(int i) {
        page = i;
        newInstance(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        newInstance(false);
    }
}
