package example.com.fan.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.fragment.son.PictureSlideFragment;
import example.com.fan.fragment.son.PictureSlideFragment2;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.pay.ali.alipayTool;

import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;
import static example.com.fan.view.dialog.CustomProgress.Cancle;

/**
 * Created by lian on 2017/6/6.
 * 自定义初始化方法Activity;
 */
public abstract class InitActivity extends FragmentActivity implements alipayTool.alipayResult {
    private static final String TAG = getTAG(InitActivity.class);
    private Throwable able = new Throwable("手动抛出TOKEN异常信息。。。");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setAnima();
        init();
        click();
        initData();
        Runtime.getRuntime().gc();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setAnima() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
            getWindow().setExitTransition(explode);
            getWindow().setReenterTransition(explode);
            getWindow().setEnterTransition(explode);
        }
    }

    /**
     * 检查Token是否异常,手动抛出异常信息至服务器;
     */
    private void CheckLoginToken() {
        if (LoginStatusQuery() && SPreferences.getUserToken().length() <= 31) {
            if (able != null)
                MyAppcation.crashHandler.uncaughtException(new Thread(), able);
            else
                able = new Throwable("手动抛出TOKEN异常信息。。。");

        }
    }

    /**
     * 添加监听事件
     */
    protected abstract void click();

    /**
     * 所有初始化在此方法完成
     */
    protected abstract void init();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 自定义findViewById方法;
     *
     * @param viewID
     * @param <T>
     * @return
     */
    public <T> T f(int viewID) {
        return (T) findViewById(viewID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在所有的Activity都要调用，一般放在基类中
        JPushInterface.onResume(this);
        MainActivity.isForeground = true;

        MobclickAgent.onResume(this);
        CheckLoginToken();
        System.gc();
        Log.i(TAG, "GlideCache==== " + GlideCacheUtil.getCacheSize(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.isForeground = false;
        //在所有的Activity都要调用，一般放在基类中
        JPushInterface.onPause(this);

        MobclickAgent.onPause(this);
        MobclickAgent.setSessionContinueMillis(30000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MyAppcation.getRefWatcher(this).watch(this);
        Cancle();
        Runtime.getRuntime().gc();
    }


    @Override
    public void result(int result) {
        try {
            if (PictureSlideFragment.PayListener != null && PhotoActivity.tlistener != null) {
                MzFinal.isPay = true;
                PictureSlideFragment.PayListener.onPayRefresh();
            }
            if (PictureSlideFragment2.PayListener != null && PrivatePhotoActivity.tlistener != null) {
                MzFinal.isPay = true;
                PictureSlideFragment2.PayListener.onPayRefresh();
            }
            if (PayActivity.paylistener != null)
                PayActivity.paylistener.onPayRefresh();

            getUserVip();
        } catch (Exception e) {
            Log.i(TAG, "Error ==== " + e);
        }
    }

    @Override
    public void failure() {

    }
}
