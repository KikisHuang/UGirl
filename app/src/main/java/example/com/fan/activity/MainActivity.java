package example.com.fan.activity;

import android.animation.ObjectAnimator;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.fragment.MyFragment;
import example.com.fan.fragment.PageFragment;
import example.com.fan.fragment.PrivateFragment;
import example.com.fan.fragment.StoreFragment;
import example.com.fan.fragment.VRFragment;
import example.com.fan.mylistener.PushRefreshListener;
import example.com.fan.receiver.MyNetworkReceiver;
import example.com.fan.server.MyJobService;
import example.com.fan.utils.AnimationUtil;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SynUtils;
import example.com.fan.utils.TitleUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.SlidePopupWindow;
import example.com.fan.view.dialog.AlertDialog;
import okhttp3.Call;

import static example.com.fan.utils.AnimationUtil.ShakeAnima;
import static example.com.fan.utils.IntentUtils.goBuyGoodsPage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;
import static example.com.fan.utils.SynUtils.isNotificationEnabled;
import static example.com.fan.view.dialog.PhotoProgress.LoadingCancle;

/**
 * Created by lian on 2017/5/15.
 * Activity主页面;
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, PushRefreshListener {
    private static final String TAG = getTAG(MainActivity.class);
    private PageFragment pageFragment = null;
    private PrivateFragment twoFragment = null;
    private VRFragment threeFragment = null;
    private StoreFragment fourFragment = null;
    private MyFragment fiveFragment = null;
    private FrameLayout page_img, two_img, three_img, four_img, five_img;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;
    private List<FrameLayout> imglist;
    private RelativeLayout main_rl, title;

    private ImageView search_img;
    private TextView menu1, menu2, menu3, menu4, menu5;
    private ImageView img1, img2, img3, img4, img5;
    private List<TextView> nlist;
    private List<ImageView> ilist;
    private MyNetworkReceiver receiver;
    public static PushRefreshListener listener;
    public static MaterialMenuView materialMenuView;
    /**
     * JPush;
     */
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


    private void getWelcomeAction() {
        if (WelcomeActivity.plistener != null)
            startAnima();

        try {
            String type = getIntent().getStringExtra("main_type");
            String id = getIntent().getStringExtra("main_info_id");
            this.onRefresh(Integer.parseInt(type), id);
        } catch (Exception e) {

        }
    }

    /**
     * 检查通知栏权限;
     */
    private void checkNotify() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isNotificationEnabled(getApplicationContext())) {
                Log.i(TAG, "通知栏消息推送权限已获得..");
            } else {
                new AlertDialog(this).builder().setCancelable(true).setTitle("提示").setMsg("您未开启通知，一些最新动态将无法及时通知您，是否去开启？").setNegativeButton("取消", null).setPositiveButton("去开启", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SynUtils.getAppDetailSettingIntent(MainActivity.this);
                    }
                }).show();
            }
        }
    }

    private void getUserInfo() {
        /**
         * 获取个人信息;
         */
        if (LoginStatusQuery()) {
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(MainActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    JSONObject ob = getJsonOb(response);
                                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                    MyAppcation.myInvitationCode = ub.getMyInvitationCode();
                                    MyAppcation.UserIcon = ub.getHeadImgUrl();
                                } else
                                    ToastUtil.ToastErrorMsg(MainActivity.this, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });
        }
    }

    /**
     * 动态注册网络监听广播;
     */
    private void addAction() {
        IntentFilter intentFilter = new IntentFilter();
        //addAction
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        receiver = new MyNetworkReceiver();
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 5.0以上进程拉起服务器初始化;
     */
    private void JobServerInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()))
                    .setPeriodic(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }
    }

    private void startAnima() {
        try {
            /**
             * 5.0以上转场动画;
             */
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                main_rl.post(new Runnable() {
                    @Override
                    public void run() {
                        AnimationUtil.cuttoAnima(main_rl, (main_rl.getLeft() + main_rl.getRight()) / 2, (main_rl.getTop() + main_rl.getBottom()) / 2);
                    }
                });
            } else {
                if (WelcomeActivity.plistener != null)
                    WelcomeActivity.plistener.onIncrease();
            }
        } catch (Exception e) {
            Log.i(TAG, "Error ===" + e);
        }
    }

    public void click() {
        page_img.setOnClickListener(this);
        two_img.setOnClickListener(this);
        three_img.setOnClickListener(this);
        four_img.setOnClickListener(this);
        five_img.setOnClickListener(this);
        materialMenuView.setOnClickListener(this);
        search_img.setOnClickListener(this);

    }

    /**
     * Fragment初始化;
     */
    private void initFragment() {
        ft = fm.beginTransaction();
        setSelected(page_img);
        one();
    }

    public void init() {
        setContentView(R.layout.activity_main);

        nlist = new ArrayList<>();
        imglist = new ArrayList<>();
        ilist = new ArrayList<>();
        listener = this;

        search_img = f(R.id.search_img);
        findViewById(R.id.back_img).setVisibility(View.INVISIBLE);
        main_rl = f(R.id.main_rl);

        imglist.add(page_img = f(R.id.page_img));
        imglist.add(two_img = f(R.id.two_img));
        imglist.add(three_img = f(R.id.three_img));
        imglist.add(four_img = f(R.id.four_img));
        imglist.add(five_img = f(R.id.five_img));
        materialMenuView = f(R.id.material_menu_button);
        title = f(R.id.title);
        menu1 = (TextView) page_img.findViewById(R.id.name);
        menu1.setText(getRouString(R.string.page));
        menu2 = (TextView) two_img.findViewById(R.id.name);
        menu2.setText(getRouString(R.string.private_photo));
        ImageView NewIm = (ImageView) two_img.findViewById(R.id.new_img);
        NewIm.setVisibility(View.VISIBLE);

        menu3 = (TextView) three_img.findViewById(R.id.name);
        menu3.setText("VR");
        menu4 = (TextView) four_img.findViewById(R.id.name);
        menu4.setText(getRouString(R.string.privacy));
        menu5 = (TextView) five_img.findViewById(R.id.name);
        menu5.setText(getRouString(R.string.my));

        img1 = (ImageView) page_img.findViewById(R.id.img);
        img1.setImageResource(MzFinal.main_un_bottom[0]);
        img2 = (ImageView) two_img.findViewById(R.id.img);
        img2.setImageResource(MzFinal.main_un_bottom[1]);
        img3 = (ImageView) three_img.findViewById(R.id.img);
        img3.setImageResource(MzFinal.main_un_bottom[2]);
        img4 = (ImageView) four_img.findViewById(R.id.img);
        img4.setImageResource(MzFinal.main_un_bottom[3]);
        img5 = (ImageView) five_img.findViewById(R.id.img);
        img5.setImageResource(MzFinal.main_un_bottom[4]);

        ilist.add(img1);
        ilist.add(img2);
        ilist.add(img3);
        ilist.add(img4);
        ilist.add(img5);

        nlist.add(menu1);
        nlist.add(menu2);
        nlist.add(menu3);
        nlist.add(menu4);
        nlist.add(menu5);

//        silide_img = f(R.id.silide_img);

        getWelcomeAction();
    }


    @Override
    public void initData() {
        getUserInfo();
        getUserVip();
        checkNotify();
        JobServerInit();
        initFragment();
        addAction();
    }

    @Override
    public void onClick(View view) {
        ft = getSupportFragmentManager().beginTransaction();

        switch (view.getId()) {

            case R.id.material_menu_button:
                if (pageFragment != null && pageFragment.gdlist.size() > 0) {
                    materialMenuView.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                    SlidePopupWindow sd = new SlidePopupWindow(this);
                    sd.ScreenPopupWindow();
                }
                break;
            case R.id.search_img:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.page_img:
                setSelected(page_img);
                one();
                title.setVisibility(View.VISIBLE);
                TitleUtils.ChangeTitleLayout(this, false, 0, "");
                BaseFragment.UpTouch = true;
                break;
            case R.id.two_img:
                setSelected(two_img);
                two();
                title.setVisibility(View.VISIBLE);
                TitleInit(getRouString(R.string.private_photo));
                break;
            case R.id.three_img:
                setSelected(three_img);
                three();
                title.setVisibility(View.VISIBLE);
                TitleInit(getRouString(R.string.VR));
                break;
            case R.id.four_img:
                setSelected(four_img);
                four();
                title.setVisibility(View.VISIBLE);
                TitleInit(getRouString(R.string.private1));
                break;
            case R.id.five_img:
                setSelected(five_img);
                five();
                title.setVisibility(View.GONE);
                if (MyFragment.fragment != null)
                    MyFragment.fragment.onUpDataUserInfo();
                break;
        }
    }

    private void TitleInit(String title) {
        TitleUtils.hideImagTitle(this, title);
        TitleUtils.ChangeTitleLayout(this, false, 1, title);
        BaseFragment.UpTouch = true;
    }

    /**
     * Fragment Hide方法;
     *
     * @param page_img
     */
    private void setSelected(FrameLayout page_img) {

        for (int i = 0; i < imglist.size(); i++) {
            if (imglist.get(i).equals(page_img)) {
                ilist.get(i).setImageResource(MzFinal.main_bottom[i]);
                nlist.get(i).setTextColor(getRouColors(R.color.bottom_color));
            } else {
                ilist.get(i).setImageResource(MzFinal.main_un_bottom[i]);
                nlist.get(i).setTextColor(getRouColors(R.color.bottom_color_un));
            }
        }
        if (pageFragment != null) {
            // 隐藏fragment
            ft.hide(pageFragment);
        }
        if (twoFragment != null) {
            ft.hide(twoFragment);
        }
        if (threeFragment != null) {
            ft.hide(threeFragment);
        }
        if (fourFragment != null) {
            ft.hide(fourFragment);
        }
        if (fiveFragment != null) {
            ft.hide(fiveFragment);
        }
    }

    /**
     * 页面切换实物提交;
     */
    private void one() {
        ObjectAnimator anima = ShakeAnima(img1);
        anima.start();
        // 提交事务
        if (pageFragment == null) {
            pageFragment = new PageFragment();
            ft.add(R.id.main_ll, pageFragment).show(pageFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(pageFragment);
            Log.i(TAG, "show");
        }
        ft.commitAllowingStateLoss();
    }

    private void two() {
        ObjectAnimator anima = ShakeAnima(img2);
        anima.start();
        if (twoFragment == null) {
            twoFragment = new PrivateFragment();
            ft.add(R.id.main_ll, twoFragment).show(twoFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(twoFragment);
            Log.i(TAG, "show");
        }
        ft.commitAllowingStateLoss();
    }

    private void three() {
        ObjectAnimator anima = ShakeAnima(img3);
        anima.start();
        if (threeFragment == null) {
            threeFragment = new VRFragment();
            ft.add(R.id.main_ll, threeFragment).show(threeFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(threeFragment);
            Log.i(TAG, "show");
        }
        ft.commitAllowingStateLoss();
    }


    private void four() {
        ObjectAnimator anima = ShakeAnima(img4);
        anima.start();
        if (fourFragment == null) {
            fourFragment = new StoreFragment();
            ft.add(R.id.main_ll, fourFragment).show(fourFragment);
        } else {
            ft.show(fourFragment);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 不保存状态;
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    private void five() {
        ObjectAnimator anima = ShakeAnima(img5);
        anima.start();
        if (fiveFragment == null) {
            fiveFragment = new MyFragment();
            ft.add(R.id.main_ll, fiveFragment).show(fiveFragment);
        } else {
            ft.show(fiveFragment);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        Log.i(TAG, "Main onResume");
//        GlideCacheUtil.clearImageAllCache(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
        Log.i(TAG, "onPauser");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        GlideCacheUtil.clearImageAllCache(this);
        if (receiver != null)
            unregisterReceiver(receiver);
        try {
            LoadingCancle();
        } catch (Exception e) {
        }
    }

    @Override
    public void onRefresh(int type, String id) {
        if (LoginStatusQuery()) {
            switch (type) {
                case MzFinal.PRIVATEPHOTO:
                    goPhotoPage(this, id, 0);
                    break;
                case MzFinal.APRICE:
                    goBuyGoodsPage(this, id);
                    break;
                case MzFinal.CROWDFUNDING:
                    ft = getSupportFragmentManager().beginTransaction();
                    setSelected(four_img);
                    four();
                    title.setVisibility(View.VISIBLE);
                    TitleInit(getRouString(R.string.private1));
                    break;
                case MzFinal.VIDEO:
                    goPlayerPage(this, id, 4);
                    break;
                case MzFinal.VRVIDEO:
                    goPlayerPage(this, id, 5);
                    break;
                case MzFinal.PRIVATE_PHOTO:
                    goPrivatePhotoPage(this, id, 0);
                    break;
                case MzFinal.PRIVATEVIDEO:
                    goPlayerPage(this, id, -3);
                    break;
            }
        } else
            Login(this);
    }

}
