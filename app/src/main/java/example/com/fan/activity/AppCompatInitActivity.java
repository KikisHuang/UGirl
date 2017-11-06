package example.com.fan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import example.com.fan.fragment.MyFragment;
import example.com.fan.fragment.son.PictureSlideFragment;
import example.com.fan.fragment.son.PictureSlideFragment2;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.pay.ali.alipayTool;

import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;

/**
 * Created by lian on 2017/6/6.
 * 自定义初始化方法Activity;
 */
public abstract class AppCompatInitActivity extends AppCompatActivity implements alipayTool.alipayResult {
    private static final String TAG = getTAG(AppCompatInitActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        click();
        initData();
        Runtime.getRuntime().gc();
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
//        Cancle();
        Runtime.getRuntime().gc();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.i(TAG, "KEYCODE_VOLUME_UP");
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.i(TAG, "KEYCODE_VOLUME_DOWN");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void result(int result) {
        try {
            if (PictureSlideFragment.PayListener != null && PhotoActivity.tlistener != null) {
                MzFinal.isPay = true;
                PictureSlideFragment.PayListener.onPayRefresh();
            }
            if (PictureSlideFragment2.PayListener != null) {
                MzFinal.isPay = true;
                PictureSlideFragment2.PayListener.onPayRefresh();
            }
            if (PayActivity.paylistener != null)
                PayActivity.paylistener.onPayRefresh();

            if (PlayerVideoActivity.infoListener != null)
                PlayerVideoActivity.infoListener.onUpDataUserInfo();

            if (MyFragment.fragment != null)
                MyFragment.fragment.onUpDataUserInfo();

            getUserVip();
        } catch (Exception e) {
            Log.i(TAG, "Error ==== " + e);
        }
    }

    @Override
    public void failure() {

    }
}
