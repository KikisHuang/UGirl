package example.com.fan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import example.com.fan.R;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/3.
 */
public class BaseActivity extends InitActivity {
    private static final String TAG = getTAG(BaseActivity.class);
    protected long timeDValue = 0; // 计算时间差值，判断是否需要退出
    public static UMShareAPI mShareAPI;
    public static SHARE_MEDIA platform;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareAPI = UMShareAPI.get(this);
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断退出
            if (timeDValue == 0) {
                ToastUtil.toast2_bottom(this, getResources().getString(R.string.appfinish), 1000);
                timeDValue = System.currentTimeMillis();
                return true;
            } else {
                timeDValue = System.currentTimeMillis() - timeDValue;
                if (timeDValue >= 1500) { // 大于1.5秒不处理。
                    timeDValue = 0;
                    return true;
                } else {// 退出应用
                    MobclickAgent.onKillProcess(this);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 微信登录回调方法;
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (mShareAPI == null)
                mShareAPI = UMShareAPI.get(this);
            mShareAPI.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            Log.i(TAG, "Error" + e);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}
