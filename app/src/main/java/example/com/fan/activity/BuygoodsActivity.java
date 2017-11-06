package example.com.fan.activity;

import android.graphics.PointF;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.BuyPagerAdapter;
import example.com.fan.bean.BuyGoodsBean;
import example.com.fan.bean.McOfficialSellImgUrls;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.homeViewPageUtils;
import example.com.fan.view.BuyGoodsScrollView;
import example.com.fan.view.Popup.ServerPopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goInstructionPhotoPage;
import static example.com.fan.utils.IntentUtils.goOrderPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.startPlay;
import static example.com.fan.utils.SynUtils.stopPlay;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/6/5.
 * 商品购买页面;
 */
public class BuygoodsActivity extends InitActivity implements View.OnClickListener, PositionAddListener {
    private static final String TAG = getTAG(BuygoodsActivity.class);
    private TextView buy_bt, buy_num, moeny_tv, buy_name, buy_content;
    private ImageView reduce_tv, add_tv;
    private int buyNumber = 0;
    private ImageView share_img, server_img;
    private ViewPager mViewPager;
    private int currentPosition = 1;
    private int dotPosition = 0;
    private int prePosition = 0;
    private FrameLayout page_frameLayout;

    private String id = "";
    private BuyGoodsScrollView top_scroll, bottom_scroll;
    private LinearLayout dot, bottom_details_layout;
    private List<McOfficialSellImgUrls> mImageViewList;
    private List<ImageView> mImageViewDotList;
    private Handler handler;
    private List<McOfficialSellImgUrls> toplist;
    private List<McOfficialSellImgUrls> detailist;
    private double price = 0;
    private String coverPath = "";
    private String buyId = "";
    private List<SubsamplingScaleImageView> sublist;
    public static PositionAddListener polistener;

    private void handlerInit() {
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

    private void getID() {
        id = getIntent().getStringExtra("buy_good_id");
    }

    private void getData() {
        Show(BuygoodsActivity.this, "", false, null);
        /**
         * 一口价数据;
         */

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.OFFGETDETAILS)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(BuygoodsActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                BuyGoodsBean bb = new Gson().fromJson(String.valueOf(ob), BuyGoodsBean.class);
                                toplist = bb.getMcOfficialSellImgUrls();
                                detailist = bb.getMcOfficialSellImgContentUrls();
                                coverPath = bb.getCoverPath();
                                buyId = bb.getId();
                                if (detailist.size() <= 0)
                                    ColoseTouch();
                                else
                                    setGoodsDetails();

                                setData(bb);
                                setViewPager();
                            } else
                                ToastUtil.ToastErrorMsg(BuygoodsActivity.this, response, code);
                            Cancle();
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void setGoodsDetails() {
//        bottom_details_layout
        for (final McOfficialSellImgUrls msi : detailist) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = DeviceUtils.dip2px(this, 5);
            lp.bottomMargin = DeviceUtils.dip2px(this, 10);
            lp.gravity = Gravity.CENTER;
            final SubsamplingScaleImageView im = new SubsamplingScaleImageView(getApplicationContext());
            im.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
//            im.setMinimumScaleType(SubsamplingScaleImageView.s);
            im.setMinScale(1.0F);
            im.setZoomEnabled(false);
            sublist.add(im);
            //将放大功能关闭;
            im.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            try {
                //下载图片保存到本地
                Glide.with(getApplicationContext())
                        .load(msi.getPath()).downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                        im.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0F, new PointF(0, 0), 0));
                    }
                });
            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
            im.setLayoutParams(lp);
            bottom_details_layout.addView(im);
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goInstructionPhotoPage(BuygoodsActivity.this, msi.getPath());
                }
            });
        }
    }

    private void setData(BuyGoodsBean bb) {
        buy_name.setText(bb.getTitle());
        setTitles(this, bb.getTitle());
        buy_content.setText(bb.getSubInfo());
        price = bb.getPrice();
        moeny_tv.setText("￥" + String.valueOf(price));
    }

    private void ColoseTouch() {
        top_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        bottom_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    private void setViewPager() {
        LayoutInflater inflater = BuygoodsActivity.this.getLayoutInflater();
        mImageViewList = homeViewPageUtils.getBuyTopImg(toplist, getApplicationContext(), inflater);
        homeViewPageUtils.setDot(toplist.size(), getApplicationContext(), mImageViewDotList, dot, dotPosition);
        BuyPagerAdapter adapter = new BuyPagerAdapter(mImageViewList, getApplicationContext());

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
                        startPlay(handler, mViewPager, 3);
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
        startPlay(handler, mViewPager, 3);
    }

    @Override
    protected void click() {
        share_img.setOnClickListener(this);
        server_img.setOnClickListener(this);
        reduce_tv.setOnClickListener(this);
        add_tv.setOnClickListener(this);
        buy_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.buy_goods_activity_layout);
        getID();
        mImageViewList = new ArrayList<>();
        mImageViewDotList = new ArrayList<>();
        sublist = new ArrayList<>();
        detailist = new ArrayList<>();
        toplist = new ArrayList<>();
        polistener = this;
        buy_bt = f(R.id.buy_bt);
        buy_num = f(R.id.buy_num);
        moeny_tv = f(R.id.moeny_tv);
        reduce_tv = f(R.id.reduce_tv);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowWidth(BuygoodsActivity.this) - 20);
        mViewPager = f(R.id.viewPager);
        mViewPager.setLayoutParams(fl);

        page_frameLayout = f(R.id.page_frameLayout);

        bottom_details_layout = f(R.id.bottom_details_layout);

        add_tv = f(R.id.add_tv);
        share_img = f(R.id.share_img);
        server_img = f(R.id.server_img);
        buy_name = f(R.id.buy_name);
        buy_content = f(R.id.buy_content);

        top_scroll = f(R.id.top_scroll);
        bottom_scroll = f(R.id.bottom_scroll);
        dot = f(R.id.ll_dot);

        buyNumber = Integer.parseInt(buy_num.getText().toString());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getWindowHeight(this) * 5.5 / 10));
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        page_frameLayout.setLayoutParams(lp);
        mViewPager.setLayoutParams(lp1);
        handlerInit();
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.server_img:
                ServerPopupWindow sp = new ServerPopupWindow(this);
                sp.ScreenPopupWindow();
                break;
            case R.id.share_img:
//                ShareApp(this);
                break;
            case R.id.add_tv:
                if (buyNumber < 99) {
                    buyNumber++;
                    buy_num.setText(String.valueOf(buyNumber));
                    moeny_tv.setText("￥" + String.valueOf(price * buyNumber));
                }
                break;
            case R.id.reduce_tv:
                if (buyNumber != 0 && buyNumber > 0) {
                    buyNumber--;
                    buy_num.setText(String.valueOf(buyNumber));
                    moeny_tv.setText("￥" + String.valueOf(price * buyNumber));
                }
                break;
            case R.id.buy_bt:
                if (buyNumber != 0 && !buyId.isEmpty() && price > 0) {
                    goOrderPage(this, buy_num.getText().toString(), buy_name.getText().toString(), String.valueOf(price), "0", coverPath, buy_content.getText().toString(), buyId);
                }

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (handler != null) {
            stopPlay();
            handler = null;
            polistener = null;
            top_scroll.removeAllViews();
            top_scroll = null;
            bottom_scroll.removeAllViews();
            bottom_scroll = null;
        }
        /**
         * SubsamplingScaleImageView recycle;
         */
        if (sublist.size() > 0) {
            for (int i = 0; i < sublist.size(); i++) {
                sublist.get(i).recycle();
            }
        }
    }

    @Override
    public void onIncrease() {
        currentPosition++;
    }

}
