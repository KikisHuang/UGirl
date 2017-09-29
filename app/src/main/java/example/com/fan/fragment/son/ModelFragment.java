package example.com.fan.fragment.son;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.ModelAdapter;
import example.com.fan.adapter.PageTopBannerAdapter;
import example.com.fan.bean.ModeInfoBean;
import example.com.fan.bean.PageTopBannerBean;
import example.com.fan.bean.PageTopBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.homepageListener;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.homeViewPageUtils;
import example.com.fan.view.ViewPagerScroller;
import okhttp3.Call;

import static example.com.fan.utils.DeviceUtils.BannerHeight;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SpringUtils.SpringViewInit;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.startPlay;
import static example.com.fan.utils.SynUtils.stopPlay;

/**
 * Created by lian on 2017/6/1.
 */


public class ModelFragment extends BaseFragment implements SpringListener, homepageListener, OverallRefreshListener, PositionAddListener, ItemClickListener {
    private static final String TAG = getTAG(ModelFragment.class);
    private ListView listView;
    private ModelAdapter adapter;
    private List<ModeInfoBean> rlist;
    private ViewPager mViewPager;

    private List<PageTopBannerBean> mImageViewList;

    private int currentPosition = 1;
    private int dotPosition = 0;
    private int prePosition = 0;
    private List<ImageView> mImageViewDotList;
    //顶部数据集合;
    private List<PageTopBannerBean> toplist;

    private LinearLayout dot;
    private View top;
    public int tag = 0;
    private SpringView springview1;
    private SpringListener slistener;
    private ItemClickListener hlistener;
    public static PositionAddListener polistener;
    private int page = 0;

    private Handler handler;

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    protected int initContentView() {
        return R.layout.modelonline_fragment;
    }

    private void handInit() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    mViewPager.setCurrentItem(currentPosition);
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }

    public void newInstance() {
        Log.i(TAG, "   position ====" + tag);
        getBanner();
    }

    private void getData(final boolean b) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMODELBYPAGE)
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
                                    ModeInfoBean sb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModeInfoBean.class);
                                    rlist.add(sb);
                                }
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter = new ModelAdapter(getActivity(), rlist, tag, hlistener);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (hlistener != null)
            hlistener = null;
    }

    private void getBanner() {
        /**
         * 顶部Banner数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "3")
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
                                toplist.clear();
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {

                                    PageTopBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PageTopBean.class);
                                    PageTopBannerBean pb = new PageTopBannerBean();
                                    pb.setHttpUrl(rb.getHttpUrl());
                                    pb.setValue(rb.getValue());
                                    pb.setUid(rb.getUid());
                                    pb.setId(rb.getId());
                                    pb.setType(rb.getType());
                                    pb.setRemarks(rb.getRemarks());
                                    pb.setImgUrl(rb.getImgUrl());

                                    toplist.add(pb);
                                }
                                if (toplist.size() > 0)
                                    Headerinit();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void Headerinit() {
        //活动顶部viewPager布局;

        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒

        setViewPager();
        if (listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(top);
            startPlay(handler, mViewPager, 4);
        }
        getData(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            polistener = this;
            startPlay(handler, mViewPager, 4);
            Log.i(TAG, "onResume");
        } else {
            stopPlay();
            polistener = null;
            Log.i(TAG, "onPause");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        try {
            polistener = this;
            startPlay(handler, mViewPager, 4);
        } catch (Exception e) {
            Log.i(TAG, "Error ==" + e);
        }
    }

    private void setViewPager() {
        mImageViewDotList.clear();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mImageViewList = homeViewPageUtils.getTopImg(toplist, getActivity().getApplicationContext(), mImageViewList, 1, inflater);
        homeViewPageUtils.setDot(toplist.size(), getActivity().getApplicationContext(), mImageViewDotList, dot, dotPosition);
        PageTopBannerAdapter adapter = new PageTopBannerAdapter(mImageViewList, getActivity(), 0);

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MzFinal.TouchTime = System.currentTimeMillis();
                        stopPlay();
                        break;
                    case MotionEvent.ACTION_UP:
                        startPlay(handler, mViewPager, 4);
                        break;
                }
                return false;
            }
        });
        //页面改变监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {    //判断当切换到第0个页面时把currentPosition设置为images.length,即倒数第二个位置，小圆点位置为length-1
                    currentPosition = toplist.size();
                    dotPosition = toplist.size() - 1;
                } else if (position == toplist.size() + 1) {    //当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
                    currentPosition = 1;
                    dotPosition = 0;
                } else {
                    currentPosition = position;
                    dotPosition = position - 1;
                }
                //  把之前的小圆点设置背景为白，当前小圆点设置为黑色
                mImageViewDotList.get(prePosition).setBackgroundResource(R.drawable.dot_corners_false);
                mImageViewDotList.get(dotPosition).setBackgroundResource(R.drawable.dot_corners_true);
                prePosition = dotPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mViewPager.setCurrentItem(currentPosition, false);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        try {
            polistener = null;
            stopPlay();

        } catch (Exception e) {
            Log.i(TAG, "Error ==" + e);
        }
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        slistener = this;
        hlistener = this;
        polistener = this;
        //注册观察者监听网络;
        ListenerManager.getInstance().registerListtener(this);

        listView = (ListView) view.findViewById(R.id.listView);
        springview1 = (SpringView) view.findViewById(R.id.springview1);
        SpringViewInit(springview1, getActivity(), slistener);
        rlist = new ArrayList<>();
        toplist = new ArrayList<>();

        top = getActivity().getLayoutInflater().inflate(R.layout.actions_top, null);
        mViewPager = (ViewPager) top.findViewById(R.id.viewPager);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BannerHeight(getActivity()));
        mViewPager.setLayoutParams(fl);

        dot = (LinearLayout) top.findViewById(R.id.ll_dot);
        mImageViewList = new ArrayList<>();
        mImageViewDotList = new ArrayList();
        handInit();
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
            getData(true);
        }
    }

    @Override
    public void onIncrease() {
        currentPosition++;
    }

    @Override
    public void onItemClickListener(int position, String id) {
        if (LoginStatusQuery()) {
            goHomePage(getActivity(), id);
        } else
            Login(getActivity());
    }
}
