package example.com.fan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.activity.RankingActivity;
import example.com.fan.activity.SearchActivity;
import example.com.fan.adapter.BottomGridAdapter;
import example.com.fan.adapter.MyPagerButtomAdapter;
import example.com.fan.adapter.PageTopBannerAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.GalleryBean;
import example.com.fan.bean.PageBotGridBean;
import example.com.fan.bean.PageTopBannerBean;
import example.com.fan.bean.PageTopBean;
import example.com.fan.bean.RankBean;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.bean.VersionBean;
import example.com.fan.bean.mcPublishRecords;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.mylistener.VersionCheckListener;
import example.com.fan.utils.DownLoadUtils;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SynUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.ZoomOutPageTransformer;
import example.com.fan.utils.homeViewPageUtils;
import example.com.fan.view.CustomGridView;
import example.com.fan.view.ObservableScrollView;
import example.com.fan.view.Popup.CodePopupWindow;
import example.com.fan.view.ViewPagerScroller;
import example.com.fan.view.WrapContentHeightViewPager;
import example.com.fan.view.dialog.AlertDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static com.sina.weibo.sdk.utils.ImageUtils.isWifi;
import static example.com.fan.base.sign.save.SPreferences.getInViCode;
import static example.com.fan.base.sign.save.SPreferences.saveInViCode;
import static example.com.fan.utils.DeviceUtils.getWindowWidth;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getRouDrawable;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getVersionCode;
import static example.com.fan.utils.SynUtils.startPlay;
import static example.com.fan.utils.SynUtils.stopPlay;
import static example.com.fan.utils.getVersionUtils.getVersionInfo;
import static example.com.fan.view.dialog.PhotoProgress.LoadingCancle;
import static example.com.fan.view.dialog.PhotoProgress.LoadingShow;


/**
 * Created by lian on 2017/4/22.
 */
public class PageFragment extends BaseFragment implements View.OnClickListener, ItemClickListener, ObservableScrollView.ScrollListener, OverallRefreshListener, VersionCheckListener, PositionAddListener {
    private static final String TAG = getTAG(PageFragment.class);
    //顶部ViewPager;
    private ViewPager mViewPager;
    //顶部圆点Layout;
    private LinearLayout mLinearLayoutDot;
    //顶部viewPagerImgList;
    private List<PageTopBannerBean> mImageViewList;
    //顶部数据集合;
    private List<PageTopBannerBean> toplist;
    //顶部viewPager圆点ImgList;
    private List<ImageView> mImageViewDotList;
    //画廊imgview;
    private List<GalleryBean> bottomList;
    //画廊page数据;
    private List<GalleryBean> blist;
    //画廊viewPager
    private WrapContentHeightViewPager bottom_page;
    //底部GridView;
    private CustomGridView end_grid;
    public List<PageBotGridBean> gdlist;
    //排行榜;
    private ImageView ranking_img0, ranking_img1, ranking_img2;
    private List<RankBean> rklist;

    private TextView ranking_more;
    //自动切换position;
    private int currentPosition = 1;
    //底部画廊position;
    private int buttomcurrentPosition = 0;
    private int dotPosition = 0;
    private int prePosition = 0;
    private LinearLayout bottom_ll;
    private RelativeLayout search_rl;
    private ObservableScrollView scrollView;
    public static PositionAddListener polistener;

    private Handler handler;

    @Override
    protected int initContentView() {
        return R.layout.page_fragment;
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

    /**
     * 最新版本检查;
     */
    private void CheckVersion() {
        getVersionInfo(getActivity().getApplicationContext(), this);
    }

    /**
     * 邀请码弹窗;
     */
    private void Invitation() {
        Log.i(TAG, " Invitation ===" + getInViCode());
        if (getInViCode() && LoginStatusQuery()) {
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
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
                                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);

                                    if (ub.getUseInvitationCode() == null) {
                                        CodePopupWindow cw = new CodePopupWindow(getActivity());
                                        cw.ScreenPopupWindow();
                                    } else
                                        saveInViCode(false);

                                } else
                                    ToastUtil.ToastErrorMsg(getActivity(), response, code);
                            } catch (Exception e) {

                            }
                        }
                    });
        }
    }

    private void getData() {

        /**
         * 顶部Banner数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "0")
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

                                initPageData();
                                pagerInit();
                                setViewPager();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        /**
         * 排行榜数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETTOPRANKING)
                .addParams("code", "2,3,4")
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
                                    RankBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), RankBean.class);
                                    rklist.add(rb);

                                }
                                setRankingIcon();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        /**
         * 中间画廊BottomViewPager;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETHOMEBANNERPHOTOBYTYPE)
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
                                    GalleryBean gb = new GalleryBean();
                                    JSONObject ob = ar.getJSONObject(i);
                                    gb.setTypeName(ob.optString("typeName"));
                                    gb.setId(ob.optInt("id"));

                                    String a = ob.optString("mcPublishRecords");
                                    JSONArray arr = new JSONArray(a);
                                    List<mcPublishRecords> l = new ArrayList<mcPublishRecords>();
                                    for (int j = 0; j < arr.length(); j++) {
                                        JSONObject obj = arr.getJSONObject(j);
                                        mcPublishRecords pr = new mcPublishRecords();
                                        pr.setCoverPath(obj.optString("coverPath"));
                                        pr.setId(obj.optString("id"));
                                        l.add(pr);
                                    }
                                    gb.setMcPublishRecords(l);
//                        GalleryBean gb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), GalleryBean.class);
                                    blist.add(gb);
                                }
                                initPageData2();
                                setBottomPager();
                                Invitation();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });

        /**
         * 底部Grid写真数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPHOTOBYHOME)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "12")
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
                                    PageBotGridBean rb;
                                    rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PageBotGridBean.class);
                                    gdlist.add(rb);
                                }
                                setBottomGrid();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void setRankingIcon() {

        for (int i = 0; i < rklist.size(); i++) {
            ImageView im = null;
            try {
                switch (i) {
                    case 0:
                        im = ranking_img0;
                        Glide.with(getActivity().getApplicationContext()).load(rklist.get(i).getHeadImgUrl()).centerCrop().bitmapTransform(new CropCircleTransformation(getActivity())).crossFade(300).override(500, 500).into(im);

                        break;
                    case 1:
                        im = ranking_img1;
                        Glide.with(getActivity().getApplicationContext()).load(rklist.get(i).getHeadImgUrl()).centerCrop().bitmapTransform(new CropCircleTransformation(getActivity())).crossFade(300).override(500, 500).into(im);

                        break;
                    case 2:
                        im = ranking_img2;
                        Glide.with(getActivity().getApplicationContext()).load(rklist.get(i).getHeadImgUrl()).centerCrop().bitmapTransform(new CropCircleTransformation(getActivity())).crossFade(300).override(500, 500).into(im);

                        break;
                }
            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
        }

    }

    private void scrollListener() {
        scrollView.setScrollListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            polistener = this;
            startPlay(handler, mViewPager, 0);
            Log.i(TAG, "onResume");
            Invitation();
        } else {
            polistener = null;
            stopPlay();
            Log.i(TAG, "onPauser");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        try {
            polistener = this;
            startPlay(handler, mViewPager, 0);
            Invitation();
        } catch (Exception e) {
            Log.i(TAG, "Error ==" + e);
        }
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
        ranking_img0.setOnClickListener(this);
        ranking_img1.setOnClickListener(this);
        ranking_img2.setOnClickListener(this);
        ranking_more.setOnClickListener(this);
        search_rl.setOnClickListener(this);
    }

    private void setBottomGrid() {
        end_grid.setAdapter(new BottomGridAdapter(getActivity(), gdlist, this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }
    @Override
    protected void init() {
        polistener = this;
        onDownTouchListener(0, "");
        mImageViewList = new ArrayList<>();
        toplist = new ArrayList<>();
        rklist = new ArrayList<>();
        bottomList = new ArrayList<>();
        mImageViewDotList = new ArrayList();
        gdlist = new ArrayList<>();
        blist = new ArrayList<>();

        //注册观察者监听网络;
        ListenerManager.getInstance().registerListtener(this);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_main);
        bottom_page = (WrapContentHeightViewPager) view.findViewById(R.id.bottom_page);
        bottom_ll = (LinearLayout) view.findViewById(R.id.bottom_ll);
        search_rl = (RelativeLayout) view.findViewById(R.id.search_rl);
        end_grid = (CustomGridView) view.findViewById(R.id.end_grid);
        scrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        ranking_img0 = (ImageView) view.findViewById(R.id.ranking_img0);
        ranking_img1 = (ImageView) view.findViewById(R.id.ranking_img1);
        ranking_img2 = (ImageView) view.findViewById(R.id.ranking_img2);
        ranking_more = (TextView) view.findViewById(R.id.ranking_more);
        mLinearLayoutDot = (LinearLayout) view.findViewById(R.id.ll_main_dot);

        end_grid.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时默认的背景色

        int[] testback = {R.drawable.main_back1, R.drawable.main_back2};
        Random ra = new Random();
        int a = ra.nextInt(testback.length);
        scrollView.setBackgroundDrawable(getRouDrawable(testback[a]));
    }

    @Override
    protected void initData() {
        handInit();
        getData();
        scrollListener();
        startPlay(handler, mViewPager, 0);
        CheckVersion();
    }

    private void pagerInit() {
        //top;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getWindowWidth(getActivity()) / 2.8));
        lp.setMargins(0, 25, 0, 0);
        mViewPager.setLayoutParams(lp);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒
        //边距设置;
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.top_page_margin));

        //bottom;
//        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.getWindowWidth(getActivity()) * 6 / 10, (int) (getWindowHeight(getActivity()) / 2.1));
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER;
        lp1.setMargins(0, 80, 0, 70);
        bottom_page.setLayoutParams(lp1);
        ViewPagerScroller scroller1 = new ViewPagerScroller(getActivity());
        scroller1.setScrollDuration(800);
        scroller1.initViewPagerScroll(bottom_page);//这个是设置切换过渡时间为2秒

        /**** 重要部分  ******/
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        bottom_page.setClipChildren(false);
        bottom_ll.setClipChildren(false);
        //设置ViewPager切换效果，即实现画廊效果
        bottom_page.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private void initPageData() {
        mImageViewList.clear();
        mImageViewDotList.clear();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mImageViewList = homeViewPageUtils.getTopImg(toplist, getActivity().getApplicationContext(), mImageViewList, 0, inflater);
        homeViewPageUtils.setDot(toplist.size(), getActivity().getApplicationContext(), mImageViewDotList, mLinearLayoutDot, dotPosition);
    }

    private void initPageData2() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        bottomList = homeViewPageUtils.getbottomImg(blist, getActivity().getApplicationContext(), inflater);
    }
    private void setBottomPager() {
        /**
         * 画廊viewPager;
         */
        MyPagerButtomAdapter adapter1 = new MyPagerButtomAdapter(bottomList, getActivity());
        bottom_page.setAdapter(adapter1);
        bottom_page.setOffscreenPageLimit(3);
        bottom_page.setCurrentItem(1);
        //页面改变监听
        bottom_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    bottom_page.setCurrentItem(buttomcurrentPosition, false);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    buttomcurrentPosition = blist.size();
                } else if (position == blist.size() + 1) {
                    buttomcurrentPosition = 1;
                } else {
                    buttomcurrentPosition = position;
                }
//                bottom_page.setCurrentItem(buttomcurrentPosition, false);
            }
        });
    }

    private void setViewPager() {
        /**
         *top;
         */
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
                        startPlay(handler, mViewPager, 0);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ranking_img0:
                skipRanking(String.valueOf(2));
                break;
            case R.id.ranking_img1:
                skipRanking(String.valueOf(3));
                break;
            case R.id.ranking_img2:
                skipRanking(String.valueOf(4));
                break;
            case R.id.ranking_more:
                skipRanking(String.valueOf(0));
                break;
            case R.id.search_rl:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void skipRanking(String tag) {
        Intent intent = new Intent(getActivity(), RankingActivity.class);
        intent.putExtra("Rangking_Tag", tag);
        startActivity(intent);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        goHomePage(getActivity(), id);
        Log.i(TAG, "position" + position);
    }

    @Override
    public void scrollOritention(int oritention) {
        switch (oritention) {
            case ObservableScrollView.SCROLL_DOWN:
                this.onDownTouchListener(0, "");
                break;
            case ObservableScrollView.SCROLL_UP:
                this.onUpTouchListener(0, "");
                break;
        }
    }

    @Override
    public void notifyAllActivity(boolean net) {
//        if (net)
//            getData();
    }

    @Override
    public void onVersion(VersionBean vb) {
        int old = SynUtils.getVersionCode(getActivity().getApplicationContext());

        if (vb.getAndroidVersion() > old) {
            LoadingShow(getActivity(), false, getRouString(R.string.VersonUp));
            /**
             * 获取apk数据;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.DOWNLOADPATH)
                    .addParams("version", String.valueOf(getVersionCode(getActivity())))
                    .addParams("channelCode", MyAppcation.CHANNEL)
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
                                    final String url = getJsonSring(response);
                                    if (!url.isEmpty()) {
                                        /**
                                         * 判断是否Wifi环境;
                                         */
                                        if (isWifi(getActivity())) {
                                            DownLoadUtils du = new DownLoadUtils(getActivity());
                                            du.download(url);
                                        } else {
                                            new AlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("检测到您不是Wifi环境,是否还继续下载?").setNegativeButton("下次再说", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    LoadingCancle();
                                                }
                                            }).setPositiveButton("下载", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    DownLoadUtils du = new DownLoadUtils(getActivity());
                                                    du.download(url);
                                                }
                                            }).show();
                                        }
                                    } else
                                        Log.i(TAG, "没有获取到新版本下载路径");
                                } else {
                                    ToastUtil.ToastErrorMsg(getActivity(), response, code);
                                    LoadingCancle();
                                }
                            } catch (Exception e) {

                            }
                        }
                    });

        } else {
            Log.i(TAG, "后台更新检测完毕,已经是最新版本,无需更新");
        }
    }

    @Override
    public void onFail() {
        LoadingCancle();
    }

    @Override
    public void onIncrease() {
        currentPosition++;
    }
}
