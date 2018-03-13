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
import android.widget.ListView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.adapter.ModelAdapter;
import example.com.fan.adapter.PageTopBannerAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModelBean;
import example.com.fan.bean.PageTopBannerBean;
import example.com.fan.bean.PageTopBean;
import example.com.fan.bean.PrivateTypeBean;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.StoreItemClickListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ShareUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.homeViewPageUtils;
import example.com.fan.view.FakePopupWindow;
import example.com.fan.view.ListViewScrollListener;
import example.com.fan.view.Popup.InterceptPopupWindow;
import example.com.fan.view.Popup.PayPopupWindow;
import example.com.fan.view.ViewPagerScroller;
import okhttp3.Call;

import static example.com.fan.utils.DeviceUtils.BannerHeight;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.IntentUtils.goPrivateTypePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SpringUtils.SpringViewInit;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.PrivateVideoCheckPay;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.startPlay;
import static example.com.fan.utils.SynUtils.stopPlay;


/**
 * Created by lian on 2017/4/22.
 */
public class StoreFragment extends BaseFragment implements PositionAddListener, SpringListener, StoreItemClickListener, OverallRefreshListener {
    private static final String TAG = getTAG(StoreFragment.class);
    private ViewPager mViewPager;

    private List<PageTopBannerBean> mImageViewList;

    private ModelAdapter adapter;

    private int currentPosition = 1;
    private int dotPosition = 0;
    private int prePosition = 0;
    private List<ImageView> mImageViewDotList;
    //顶部数据集合;
    private List<PageTopBannerBean> toplist;

    private List<ModelBean> rlist;
    private ListView listView;

    private LinearLayout dot;
    private View top;
    public int tag = 0;
    private SpringView springview1;
    private SpringListener slistener;
    private StoreItemClickListener hlistener;
    public static PositionAddListener polistener;

    private int page = 0;

    private Handler handler;

    private LinearLayout private_type_layout;
    private InterceptPopupWindow intercept;
    private static StoreFragment fragment;
    private PayPopupWindow p;
    private PopupWindow pay;

    @Override
    protected int initContentView() {
        return R.layout.store_fragment2;
    }

    @Override
    protected void click() {

    }

    public static StoreFragment StoreInstance() {
        return fragment;
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
        fragment = this;
        listView.setOnScrollListener(new ListViewScrollListener(this, getRouString(R.string.privacy)));
        top = getActivity().getLayoutInflater().inflate(R.layout.private_top, null);
        mViewPager = (ViewPager) top.findViewById(R.id.viewPager);
        private_type_layout = (LinearLayout) top.findViewById(R.id.private_type_layout);

        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BannerHeight(getActivity()));
        mViewPager.setLayoutParams(fl);

        dot = (LinearLayout) top.findViewById(R.id.ll_dot);
        mImageViewList = new ArrayList<>();
        mImageViewDotList = new ArrayList();
//        CheckJurisdiction();

        handInit();
    }

    /**
     * 拦截页面;
     */
    public void CheckJurisdiction() {
        if (!MyAppcation.VipFlag) {
            if (intercept == null)
                intercept = new InterceptPopupWindow(getActivity());

            intercept.ScreenPopupWindow(view);
        } else {
            if (intercept != null)
                intercept.onMyDismiss();
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
    protected void initData() {
        newInstance();
        getScrolData();
    }

    private void getScrolData() {

        /**
         * 获取所有私密照片/私密视频的类型;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETALLTYPE)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "20")
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
                                    PrivateTypeBean ptb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), PrivateTypeBean.class);
                                    ScrolInit(ptb);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void ScrolInit(final PrivateTypeBean ptb) {
        ImageView im = new ImageView(getActivity());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.dip2px(getActivity(), 130), ViewGroup.LayoutParams.MATCH_PARENT);
        lp.rightMargin = DeviceUtils.dip2px(getActivity(), 5);
        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getActivity()).load(ptb.getImgUrl()).into(im);
        im.setLayoutParams(lp);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginStatusQuery()) {
                    getJurisdiction(ptb.getId(), ptb.getTypeName());
                } else
                    Login(getActivity());
            }
        });
        private_type_layout.addView(im);
    }

    private void getJurisdiction(final String type, final String name) {
        /**
         * 获取跳转权限;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CHECKTYPEPERMISSION)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("typeId", type)
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
                                goPrivateTypePage(getActivity(), type, name);
                            } else if (code == -1) {

                                FakePopupWindow fpw = new FakePopupWindow(getActivity(), new JSONObject(response).optString("erroMsg"));
                                fpw.ScreenPopupWindow(private_type_layout);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });

    }

    public void newInstance() {
        Log.i(TAG, "   position ====" + tag);
        getBanner();
    }

    private void getBanner() {
        /**
         * 顶部Banner数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "6")
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
    public void onDestroyView() {
        super.onDestroyView();
        if (hlistener != null)
            hlistener = null;
    }

    private void getData(final boolean b) {
        /**
         * 获取所有类型私密视频、私照;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPRIVATERECORD)
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
                                if (b) {
                                    rlist.clear();
                                }

                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                    rlist.add(mb);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new ModelAdapter(getActivity(), rlist, hlistener);
                                    listView.setAdapter(adapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });
    }


    private void setViewPager() {
        mImageViewDotList.clear();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mImageViewList = homeViewPageUtils.getTopImg(toplist, getActivity().getApplicationContext(), mImageViewList, 1, inflater);
        homeViewPageUtils.setDot(toplist.size(), getActivity().getApplicationContext(), mImageViewDotList, dot, dotPosition);
        PageTopBannerAdapter adapter = new PageTopBannerAdapter(mImageViewList, getActivity(), 1);

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            polistener = this;
            startPlay(handler, mViewPager, 4);
            Log.i(TAG, "onResume");
//            CheckJurisdiction();
        } else {
            stopPlay();
            if (intercept != null)
                intercept.onMyDismiss();

            polistener = null;
            Log.i(TAG, "onPause");
        }
    }

    @Override
    public void onIncrease() {
        currentPosition++;
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
    public void notifyAllActivity(boolean net) {
        if (net) {
            Log.i(TAG, "   position ====" + tag);
            getData(true);
        }
    }

    @Override
    public void onItemClickListener(int position, String id, int pos) {
        Log.i(TAG, "TypeFlag ===" + position);
        switch (position) {
            case 1002:
                goHomePage(getActivity(), id);
                break;
            case -2:
                if (LoginStatusQuery()) {
                    goPrivatePhotoPage(getActivity(), id, 0);
                } else
                    Login(getActivity());
                break;
            case -3:
                if (LoginStatusQuery()) {
                    if (MyAppcation.VipFlag)
                        PrivateVideoCheckPay(getActivity(), listView, id, String.valueOf(rlist.get(pos).getPrice()));
                    else {
                        if (p == null)
                            p = new PayPopupWindow(getActivity(), "", id);

                        // 一个自定义的布局，作为显示的内容
                        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pay_pp_layout, null);
                        int width = DeviceUtils.getWindowWidth(getActivity()) * 8 / 10;
                        int h = (int) (DeviceUtils.getWindowHeight(getActivity()) * 6 / 10);
                        pay = new PopupWindow(contentView, width, h);
                        p.ScreenPopupWindow(LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null), pay, 0, contentView);

                    }

                } else
                    Login(getActivity());
                break;
            case 112:
                if (LoginStatusQuery()) {
                    ShareUtils.getSystemShare(getActivity(), id);
                } else
                    Login(getActivity());

                break;
            case 113:
                if (LoginStatusQuery()) {
                    ShareUtils.getSystemShare(getActivity(), id);
                } else
                    Login(getActivity());
                break;
        }
    }
}
