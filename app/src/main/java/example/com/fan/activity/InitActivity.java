package example.com.fan.activity;

import android.os.Bundle;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.pay.ali.alipayTool;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;

/**
 * Created by lian on 2017/6/6.
 * 自定义初始化方法Activity;
 */
public abstract class InitActivity extends SwipeBackActivity implements alipayTool.alipayResult {
    private static final String TAG = getTAG(InitActivity.class);
    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
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
//        MyAppcation.getRefWatcher(this).watch(this);
        System.gc();
    }

    @Override
    public void result(int result) {
        getUserVip(getApplicationContext());
    }

    @Override
    public void failure() {
    }
    @Override
    public void onBackPressed() {
        scrollToFinishActivity();//左滑退出activity
    }

}
