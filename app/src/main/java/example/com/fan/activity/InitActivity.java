package example.com.fan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import example.com.fan.MyAppcation;
import example.com.fan.fragment.son.PictureSlideFragment;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.pay.ali.alipayTool;

import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;

/**
 * Created by lian on 2017/6/6.
 * 自定义初始化方法Activity;
 */
public abstract class InitActivity extends FragmentActivity implements alipayTool.alipayResult {
    private static final String TAG = getTAG(InitActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        click();
        initData();
        System.gc();
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
        MobclickAgent.onResume(this);
        System.gc();
        Log.i(TAG, "GlideCache==== " + GlideCacheUtil.getCacheSize(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.setSessionContinueMillis(30000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAppcation.getRefWatcher(this).watch(this);
        System.gc();
    }

    @Override
    public void result(int result) {
        try {
            if (PictureSlideFragment.PayListener != null && PhotoActivity.tlistener != null) {
                MzFinal.isPay = true;
                PictureSlideFragment.PayListener.onPayRefresh();
            }
            if (PayActivity.paylistener != null)
                PayActivity.paylistener.onPayRefresh();

            getUserVip(getApplicationContext());
        } catch (Exception e) {
            Log.i(TAG, "Error ==== " + e);
        }
    }

    @Override
    public void failure() {
    }

}
