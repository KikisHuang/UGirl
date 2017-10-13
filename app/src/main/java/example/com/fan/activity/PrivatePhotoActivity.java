package example.com.fan.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

import example.com.fan.R;
import example.com.fan.adapter.PhotoPagerAdapter2;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.CommentBean;
import example.com.fan.bean.MirrorBean;
import example.com.fan.fragment.son.CommentFragment;
import example.com.fan.mylistener.CollectListener;
import example.com.fan.mylistener.PhotoBarListener;
import example.com.fan.mylistener.TouchCloseListener;
import example.com.fan.mylistener.editeListener;
import example.com.fan.utils.AnimationUtil;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.TextViewColorUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.BigPhotoViewPager;
import example.com.fan.view.Popup.CommentEditPopupWindow;
import example.com.fan.view.Popup.PayPopupWindow;
import example.com.fan.view.SmartScrollView;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.ShareUtils.ShareApp;
import static example.com.fan.utils.SynUtils.KswitchWay;
import static example.com.fan.utils.SynUtils.ParseK;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/5/5.
 * 照片查看器页面;
 */
public class PrivatePhotoActivity extends InitActivity implements View.OnClickListener, editeListener, CollectListener, PhotoBarListener, TouchCloseListener {
    private static final String TAG = getTAG(PrivatePhotoActivity.class);
    private boolean SCRENNFLAG;
    private BigPhotoViewPager viewPager;
    private List<MirrorBean> urlList;
    private int oldposition;
    public RelativeLayout photo_top_rl, lead_rl;
    public LinearLayout photo_bottom_ll;
    public TextView num_tv;
    private FrameLayout share_fl, admire_fl, collect_fl, comment_fl;
    private TextView comment_ed;
    //评论,收藏,点赞,分享数量;
    private TextView share_num, admire_num, collect_num, comment_num, lead_tv;
    private int mposition;
    private ImageView title_right_icon;
    private ImageView rt_tv;
    private ImageView screnn_img;

    private LinearLayout fragment_ll;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;
    private CommentFragment commentFragment;
    //弹幕;
    private List<CommentBean> commentlist;
    private LinearLayout bullet_ll;
    int page = 0;
    private SmartScrollView scroll;
    private Handler handler;
    private Runnable mScrollToBottom;
    public static String id = "";
    //vip标识符;
    private PopupWindow pay;
    private Thread comThread;

    /**
     * 回调方法;
     */
    public static PhotoBarListener slistener;
    public static TouchCloseListener tlistener;
    public static editeListener elistener;
    public static CollectListener clistener;

    private List<View> CommentView;

    /**
     * 添加弹幕数据;
     */
    private void addScrennView() {
        AddMargin();
        for (int i = 0; i < commentlist.size(); i++) {
            /**
             * 弹幕item布局;
             */
            View view = LayoutInflater.from(this).inflate(R.layout.bullet_screen_include, null);
            ImageView im = (ImageView) view.findViewById(R.id.bullet_icon);
            TextView tv = (TextView) view.findViewById(R.id.bullet_cotent);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.bullet_ll);
            ll.setBackgroundResource(R.drawable.barrage_shape);
            CommentView.add(view);
            tv.setText(commentlist.get(i).getInfo());
            try {
                Glide.with(getApplicationContext()).load(commentlist.get(i).getUserHeadImgUrl()).apply(getRequestOptions(false, 60, 60,true)).into(im);
            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
            bullet_ll.addView(view);
        }
        AddMargin();
    }

    /**
     * 弹幕上下空白数据;
     */
    private void AddMargin() {
        for (int i = 0; i < 6; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.bullet_screen_include, null);
            CommentView.add(view);
            bullet_ll.addView(view);
        }
    }

    /**
     * 弹幕开启;
     */
    private void BulletStart() {
        /**
         * 获取评论数据;
         */
        commentlist.clear();
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYCOMMENT)
                .addParams(MzFinal.ID, id)
                .addParams(MzFinal.TYPE, "0")
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "50")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {

                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    CommentBean com = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), CommentBean.class);
                                    commentlist.add(com);
                                }

                                CreateComment();
                            } else
                                ToastUtil.ToastErrorMsg(PrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void CreateComment() {
        if (commentlist.size() > 0) {
            initHandler();
            addScrennView();
            SPreferences.saveBulletFlag(true);
            mScrollToBottom = new Runnable() {
                @Override
                public void run() {
                    while (SPreferences.getBulletFlag() && SCRENNFLAG) {

                        Message msg = new Message();
                        msg.what = 0;
                        try {
                            if (handler != null)
                                handler.sendMessage(msg);
                        } catch (Exception e) {
                            if (e instanceof NullPointerException)
                                CreateComment();
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
            if (comThread == null) {
                comThread = new Thread(mScrollToBottom);
                comThread.start();
            }
        } else
            SPreferences.saveBulletFlag(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SCRENNFLAG = true;
        if (SPreferences.getBulletFlag()) {
            screnn_img.setImageResource(R.mipmap.unscreen_img);
            BulletStart();
        } else
            screnn_img.setImageResource(R.mipmap.screen_img);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {
            SCRENNFLAG = false;
            handler.removeCallbacks(mScrollToBottom);
            handler = null;
            comThread = null;
            System.gc();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            SCRENNFLAG = false;
            handler.removeCallbacks(mScrollToBottom);
            handler = null;
            comThread = null;
            System.gc();
        }
    }

    /**
     * 弹幕关闭;
     */
    private void BulletStop() {
        SPreferences.saveBulletFlag(false);
        try {
            if (mScrollToBottom != null)
                handler.removeCallbacks(mScrollToBottom);
            comThread = null;
        } catch (Exception e) {
            Log.i(TAG, "评论开关异常抛出.." + e);
        }
        bullet_ll.removeAllViews();
    }

    private void initHandler() {
        if (handler == null) {

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 0:
                            page += 1;
                            if (page > 0) {
                                if (page > commentlist.size() * 300) {
                                    scroll.scrollTo(0, 0);
                                    page = 0;
                                } else
                                    scroll.scrollTo(0, page);
                            }
                            break;
                    }
                }
            };
        }
    }

    @Override
    protected void click() {

        rt_tv.setOnClickListener(this);
        lead_rl.setOnClickListener(this);
        share_fl.setOnClickListener(this);
        admire_fl.setOnClickListener(this);
        collect_fl.setOnClickListener(this);
        comment_fl.setOnClickListener(this);
        comment_ed.setOnClickListener(this);
        screnn_img.setOnClickListener(this);
        viewPager.setOnClickListener(this);
        title_right_icon.setOnClickListener(this);
    }

    /**
     * ViewPager初始化设置;
     */
    private void setPage() {
        viewPager.setAdapter(new PhotoPagerAdapter2(getSupportFragmentManager(), urlList, id));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mposition = position;
                TextViewColorUtils.setTextColor(num_tv, String.valueOf(mposition + 1), "/" + String.valueOf(urlList.get(0).getMcPublishImgUrls().size()), "#eb030d");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (urlList.get(0).getMcPublishImgUrls().get(oldposition).getNeedMoney()) {
            Log.i(TAG, "Photo ======" + urlList.get(0).getMcPublishImgUrls().get(oldposition).getNeedMoney());
            viewPager.setScrollble(MzFinal.isPay, clistener);
        } else
            viewPager.setScrollble(true, clistener);
        viewPager.setCurrentItem(oldposition);
    }

    @Override
    protected void init() {
        setContentView(R.layout.photo_activity_layout);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ft = fm.beginTransaction();
        elistener = this;
        clistener = this;
        slistener = this;
        tlistener = this;

        commentlist = new ArrayList<>();
        CommentView = new ArrayList<>();
        urlList = new ArrayList<>();
        photo_top_rl = f(R.id.photo_top_rl);
        lead_rl = f(R.id.lead_rl);
        photo_bottom_ll = f(R.id.photo_bottom_ll);
        rt_tv = f(R.id.rt_tv);
        num_tv = f(R.id.num_tv);
        bullet_ll = f(R.id.bullet_ll);
        scroll = f(R.id.scroll);
        screnn_img = f(R.id.screnn_img);
        lead_tv = f(R.id.lead_tv);

        share_num = f(R.id.share_num);
        admire_num = f(R.id.admire_num);
        collect_num = f(R.id.collect_num);
        comment_num = f(R.id.comment_num);

        fragment_ll = f(R.id.fragment_ll);

        title_right_icon = f(R.id.title_right_icon);
        viewPager = f(R.id.viewpager);

        share_fl = f(R.id.share_fl);
        admire_fl = f(R.id.admire_fl);
        collect_fl = f(R.id.collect_fl);
        comment_fl = f(R.id.comment_fl);
        comment_ed = f(R.id.comment_ed);

        lead_rl.setVisibility(View.VISIBLE);
        photo_bottom_ll.setVisibility(View.GONE);
        photo_top_rl.setVisibility(View.GONE);
        screnn_img.setVisibility(View.GONE);
        viewPager.setOffscreenPageLimit(1);
        MzFinal.isPay = false;
    }

    @Override
    protected void initData() {
        getIntents();
    }

    /**
     * pamars photo_list(获取传递过来的照片数据)
     * pamars 以及起始位置(oldposition);
     */
    private void getIntents() {
        try {

            String sID = getIntent().getStringExtra("photo_position");
            oldposition = Integer.parseInt(sID);
            id = getIntent().getStringExtra("photo_id");
            Log.i(TAG, "Photo_ID ====" + id);
            if (!id.isEmpty())
                getData(id);
            else
                finish();

        } catch (Exception e) {
            Log.i(TAG, "Error" + e);
            finish();
        }
    }

    private void getData(String id) {
        /**
         * 写真专辑数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ar = getJsonOb(response);

                                MirrorBean mb = new Gson().fromJson(String.valueOf(ar), MirrorBean.class);
                                urlList.add(mb);

                                lead_tv.setText(mb.getInfo());

                                MzFinal.isPay = urlList.get(0).getIsPay();
                                comment_num.setText(KswitchWay(urlList.get(0).getCommentCount()));
                                collect_num.setText(KswitchWay(urlList.get(0).getCollectionCount()));
                                admire_num.setText(KswitchWay(urlList.get(0).getLikesCount()));
                                share_num.setText(KswitchWay(urlList.get(0).getShareCount()));
                                try {
                                    Glide.with(getApplicationContext()).load(urlList.get(0).getJoinUser().getHeadImgUrl()).apply(getRequestOptions(false, 50, 50,true)).into(title_right_icon);
                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }

                                TextViewColorUtils.setTextColor(num_tv, String.valueOf(oldposition + 1), "/" + String.valueOf(urlList.get(0).getMcPublishImgUrls().size()), "#eb030d");
                                if (urlList.get(0).getMcPublishImgUrls().size() > 0)
                                    setPage();
//                        createFragment();

                            } else {
                                ToastUtil.ToastErrorMsg(PrivatePhotoActivity.this, response, code);
                                finish();
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.share_fl:
                if (urlList.size() > 0)
                    ShareApp(this, urlList.get(0).getJoinUser().getId(), urlList.get(0).getJoinUser().getName(), urlList.get(0).getInfo(), urlList.get(0).getId());
                break;
            case R.id.admire_fl:
                likePhoto();
                break;
            case R.id.collect_fl:
                collectPhoto();
                break;
            case R.id.title_right_icon:
                if (urlList.size() > 0 && !urlList.get(0).getJoinUser().getId().isEmpty()) {
                    goHomePage(this, urlList.get(0).getJoinUser().getId());
                    finish();
                }
                break;
            case R.id.comment_fl:

                commentFragment = new CommentFragment();
                commentFragment.setId(id, String.valueOf(urlList.get(0).getCollectionCount()));
                ft.add(R.id.fragment_ll, commentFragment).show(commentFragment);
                ft.commit();

                break;
            case R.id.comment_ed:
                CommentEditPopupWindow.ScreenPopupWindow(comment_ed, getApplicationContext(), elistener);
                break;

            case R.id.rt_tv:
                if (commentFragment == null)
                    finish();
                else {
                    fragment_ll.removeAllViews();
                    commentFragment = null;
                }
                break;
            case R.id.lead_rl:
                if (lead_rl.getVisibility() == View.VISIBLE) {
                    lead_rl.setVisibility(View.GONE);
                    HideofShow();

                }
                break;

            case R.id.screnn_img:
                if (SPreferences.getBulletFlag()) {
                    screnn_img.setImageResource(R.mipmap.screen_img);
                    BulletStop();
                    HideofShow();
                } else {
                    screnn_img.setImageResource(R.mipmap.unscreen_img);
                    BulletStart();
                    HideofShow();
                }
                break;
            case R.id.viewPager:
                HideofShow();
                break;
        }

    }

    private void likePhoto() {
        /**
         * 点赞接口;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.LIKEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonSring(response)) {
                                    case "1":
                                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "点赞成功!");
                                        admire_num.setText(ParseK(admire_num.getText().toString(), true));
                                        break;
                                    case "0":
                                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "取消点赞!");
                                        admire_num.setText(ParseK(admire_num.getText().toString(), false));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (commentFragment == null)
            finish();
        else {
            fragment_ll.removeAllViews();
            commentFragment = null;
        }
        return true;
    }

    private void collectPhoto() {
        /**
         * 收藏接口;
         */
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
                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonSring(response)) {
                                    case "1":
                                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "收藏成功!");
                                        collect_num.setText(ParseK(collect_num.getText().toString(), true));
                                        break;
                                    case "0":
                                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "成功取消收藏!");
                                        collect_num.setText(ParseK(collect_num.getText().toString(), false));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void showEditePopup(final String content) {
        /**
         * 评论发送接口;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.ADDPHOTOCOMMENT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .addParams("content", content)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                bullet_ll.removeAllViews();
                                if (handler != null) {
                                    handler.removeCallbacks(mScrollToBottom);
                                    handler = null;
                                    System.gc();
                                }
                                BulletStart();
                                ToastUtil.toast2_bottom(PrivatePhotoActivity.this, "评论成功!");
                            } else
                                ToastUtil.ToastErrorMsg(PrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
//        MyAppcation.getRefWatcher(this).watch(this);

        try {
            fragment_ll.removeAllViews();
            Log.i(TAG, "remove fragment");
            commentFragment = null;
            clearView();
        } catch (Exception e) {

        }
    }

    private void clearView() {
        elistener = null;
        clistener = null;
        slistener = null;
        tlistener = null;
        pay.dismiss();
        handler.removeCallbacksAndMessages(mScrollToBottom);
        handler = null;
        pay = null;
    }

    /**
     * 收藏回调方法;
     */
    @Override
    public void onCollect() {
        collectPhoto();
    }

    /**
     * vip弹窗回调方法;
     */
    @Override
    public void ShowVip() {
        if (pay != null && pay.isShowing()) {
            Log.i(TAG, "popunShow");
        } else {
            Log.i(TAG, "popShow");
            PayPopupWindow p = new PayPopupWindow(PrivatePhotoActivity.this, String.valueOf(urlList.get(0).getPrice()), id);
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(PrivatePhotoActivity.this).inflate(R.layout.pay_pp_layout, null);
            int width = DeviceUtils.getWindowWidth(PrivatePhotoActivity.this) * 8 / 10;
            int h = (int) (DeviceUtils.getWindowHeight(PrivatePhotoActivity.this) * 6 / 10);
            pay = new PopupWindow(contentView, width, h);
            p.ScreenPopupWindow(LayoutInflater.from(PrivatePhotoActivity.this).inflate(R.layout.photo_activity_layout, null), pay, 2, contentView);
        }
    }

    /**
     * 上下bar隐藏显示方法;
     */
    private void HideofShow() {
        if (photo_bottom_ll.getVisibility() == View.VISIBLE) {
            photo_bottom_ll.startAnimation(AnimationUtil.moveToViewBottom());
            photo_top_rl.startAnimation(AnimationUtil.moveToViewTop());
            photo_top_rl.setVisibility(View.GONE);
            photo_bottom_ll.setVisibility(View.GONE);
            screnn_img.setVisibility(View.GONE);
        } else {
            photo_bottom_ll.startAnimation(AnimationUtil.moveToViewLocation());
            photo_top_rl.startAnimation(AnimationUtil.moveToViewLocation1());
            photo_top_rl.setVisibility(View.VISIBLE);
            photo_bottom_ll.setVisibility(View.VISIBLE);
            screnn_img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowOfHide() {
        HideofShow();
    }

    /**
     * 关闭滑动回调方法;
     *
     * @param flag
     */
    @Override
    public void CloseOfOpenTouch(boolean flag) {
        viewPager.setScrollble(flag, PrivatePhotoActivity.clistener);
    }
}
