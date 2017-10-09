package example.com.fan.utils;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.TimerTask;

import example.com.fan.activity.BuygoodsActivity;
import example.com.fan.fragment.PageFragment;
import example.com.fan.fragment.StoreFragment;
import example.com.fan.fragment.VRFragment;
import example.com.fan.fragment.son.StoreFragment2;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/4.
 * 广告Banner切换通用方法类;
 */
public class PageTopTask extends TimerTask {
    private static final String TAG = getTAG(PageTopTask.class);
    private Handler handler;
    private ViewPager mViewPager;
    /**
     * 广告页面标识符;
     * flag
     * 0: PageFragment
     * 1: StoreFragment2
     * 2: VRFragment
     * 3: BuygoodsActivity
     */
    private int flag;

    public PageTopTask(Handler handler, ViewPager mViewPager, int flag) {
        this.handler = handler;
        this.mViewPager = mViewPager;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (mViewPager != null) {
            Log.i(TAG, "PageTopTask run ...  flag ==" + flag);
            if (mViewPager.isShown() && System.currentTimeMillis() - MzFinal.TouchTime > 3000) {
                if (flag == 0)
                    if (PageFragment.polistener != null)
                        PageFragment.polistener.onIncrease();
                if (flag == 1)
                    if (StoreFragment2.polistener != null)
                        StoreFragment2.polistener.onIncrease();
                if (flag == 2)
                    if (VRFragment.polistener != null)
                        VRFragment.polistener.onIncrease();
                if (flag == 3)
                    if (BuygoodsActivity.polistener != null)
                        BuygoodsActivity.polistener.onIncrease();
                if (flag == 4)
                    if (StoreFragment.polistener != null)
                        StoreFragment.polistener.onIncrease();
                try {
                if (handler != null)
                    handler.sendEmptyMessage(1);
                }catch (Exception e){

                }
            }
        } else
            cancel();
    }

    /**
     * 关闭方法（防止内存泄露）;
     *
     * @param task
     */
    public void Close(PageTopTask task) {
        if (handler != null) {
            handler.removeCallbacks(task);
            handler = null;
        }
    }
}
