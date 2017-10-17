package example.com.fan.fragment;

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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PageTopBannerAdapter;
import example.com.fan.adapter.VrAdapter;
import example.com.fan.bean.PageTopBannerBean;
import example.com.fan.bean.PageTopBean;
import example.com.fan.bean.VrBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.homeViewPageUtils;
import example.com.fan.view.DirectionListView;
import example.com.fan.view.ViewPagerScroller;
import okhttp3.Call;

import static example.com.fan.utils.DeviceUtils.BannerHeight;
import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goVideoOfVrPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.ShareUtils.getSystemShare;
import static example.com.fan.utils.SpringUtils.SpringViewInit;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.startPlay;
import static example.com.fan.utils.SynUtils.stopPlay;

/**
 * Created by lian on 2017/6/17.
 */
public class VRFragment extends BaseFragment implements SpringListener, ItemClickListener, View.OnClickListener, OverallRefreshListener, PositionAddListener, ShareRequestListener, TwoParamaListener, DirectionListView.OnScrollDirectionListener {
    private static final String TAG = getTAG(VRFragment.class);

    private View top;
    private List<VrBean> vrlist;
    private ViewPager mViewPager;
    private List<ImageView> mImageViewDotList;
    private List<PageTopBannerBean> mImageViewList;
    private List<PageTopBannerBean> toplist;
    private LinearLayout dot;
    private ImageView video_cover, vr_cover;
    private DirectionListView listView;
    private int currentPosition = 1;
    private int dotPosition = 0;
    private int prePosition = 0;
    private Handler handler;
    private int page = 0;
    private SpringView springView;
    private VrAdapter adapter;
    private ItemClickListener listener;
    private FrameLayout page_framelayuot;
    public static PositionAddListener polistener;
    public static ShareRequestListener slistener;
    public static TwoParamaListener tlistener;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition;// 标记上次滑动位置

    @Override
    protected int initContentView() {
        return R.layout.vr_fragment;
    }

    private void getBanner() {
        /**
         * 顶部Banner数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "1")
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
                                toplist.clear();
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
        /**
         * 获取video 和VR Cover;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETVIDEOMODELCOVER)
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
                                try {
                                    Glide.with(getActivity().getApplicationContext()).load(ar.get(0)).apply(getRequestOptions(false, 480, 350,false)).into(video_cover);
                                    Glide.with(getActivity().getApplicationContext()).load(ar.get(1)).apply(getRequestOptions(false, 480, 350,false)).into(vr_cover);
                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getData(final boolean b) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETALLVIDEO)
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page+20))
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
                                    vrlist.clear();

                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    VrBean vb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), VrBean.class);
                                    vrlist.add(vb);
                                }
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter = new VrAdapter(vrlist, getActivity(), listener, tlistener, slistener);
                                    listView.setAdapter(adapter);
                                }
//                    getVrData();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            startPlay(handler, mViewPager, 2);
            Log.i(TAG, "onResume");
        } else {
            stopPlay();
            Log.i(TAG, "onPauser");
        }
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
    protected void click() {

    }

    @Override
    protected void init() {
        listener = this;
        polistener = this;
        slistener = this;
        tlistener = this;
        listView = (DirectionListView) view.findViewById(R.id.listView);
        springView = (SpringView) view.findViewById(R.id.springView);
        listView.setOnScrollDirectionListener(this);
        //注册观察者监听网络;
        ListenerManager.getInstance().registerListtener(this);

        vrlist = new ArrayList<>();
        toplist = new ArrayList<>();
        mImageViewList = new ArrayList<>();
        SpringViewInit(springView, getActivity(), this);

        top = getActivity().getLayoutInflater().inflate(R.layout.vr_header_include, null);
        video_cover = (ImageView) top.findViewById(R.id.video_cover);
        vr_cover = (ImageView) top.findViewById(R.id.vr_cover);

        handInit();
    }

    @Override
    protected void initData() {
        getBanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }

    private void Headerinit() {
        //活动顶部viewPager布局;
        video_cover.setOnClickListener(this);
        vr_cover.setOnClickListener(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getWindowWidth(getActivity()) * 3.5 / 10), 1.0f);
        video_cover.setLayoutParams(lp);
        vr_cover.setLayoutParams(lp);
        vr_cover.setScaleType(ImageView.ScaleType.FIT_XY);
        video_cover.setScaleType(ImageView.ScaleType.FIT_XY);

        mViewPager = (ViewPager) top.findViewById(R.id.viewPager);
        page_framelayuot = (FrameLayout) top.findViewById(R.id.page_framelayuot);
        LinearLayout.LayoutParams fl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BannerHeight(getActivity()));
        page_framelayuot.setLayoutParams(fl);

        dot = (LinearLayout) top.findViewById(R.id.ll_dot);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒
        mImageViewDotList = new ArrayList();

        setViewPager();
        if (listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(top);
            startPlay(handler, mViewPager, 2);
        }
        getData(true);
    }

    private void setViewPager() {
        mImageViewDotList.clear();
        mImageViewList.clear();
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
                        startPlay(handler, mViewPager, 2);
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
                //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
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
    public void IsonRefresh(int i) {
        page = i;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        if (LoginStatusQuery()) {
            goPlayerPage(getActivity(), id, position);
        } else
            Login(getActivity());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vr_cover:
                if (LoginStatusQuery()) {
                    goVideoOfVrPage(getActivity(), "1");
                } else
                    Login(getActivity());
                break;
            case R.id.video_cover:
                if (LoginStatusQuery()) {
                    goVideoOfVrPage(getActivity(), "0");
                } else
                    Login(getActivity());

                break;
        }
    }

    @Override
    public void notifyAllActivity(boolean net) {
        if (net)
            getData(true);
    }

    @Override
    public void onIncrease() {
        currentPosition++;
    }

    @Override
    public void onShare(String userid, String name, String info, String id) {
//        ShareApp(getActivity(), userid, name, info, id);
        getSystemShare(getActivity(),id);
    }

    @Override
    public void onGoPlayPage(String id, int typeFlag) {
        goHomePage(getActivity(), id);
    }

    @Override
    public void onScrollUp() {
        onUpTouchListener(1, getRouString(R.string.VR));
    }

    @Override
    public void onScrollDown() {
        onDownTouchListener(1, getRouString(R.string.VR));
    }
}
