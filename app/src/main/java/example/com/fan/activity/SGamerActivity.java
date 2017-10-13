package example.com.fan.activity;

import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.fragment.sgamer.MyProductionFragment;
import example.com.fan.fragment.sgamer.WithdrawFragment;
import example.com.fan.view.Popup.UpLoadPhotoOfVideoPopupWindow;

import static example.com.fan.utils.AnimationUtil.ShakeAnima;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.ShowTitle;
import static example.com.fan.utils.TitleUtils.hideTitle;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/10.
 */
public class SGamerActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(MainActivity.class);
    private MyProductionFragment oneFragment = null;
    private WithdrawFragment twoFragment = null;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;
    private ImageView img1, img2;
    private LinearLayout withdraw_layout, my_production_layout;
    private TextView tv1, tv2;
    private LinearLayout add_layout;

    @Override
    protected void click() {
        withdraw_layout.setOnClickListener(this);
        my_production_layout.setOnClickListener(this);
        add_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.sgamer_layout);
        ft = fm.beginTransaction();
        img1 = f(R.id.img1);
        img2 = f(R.id.img2);
        add_layout = f(R.id.add_layout);
        my_production_layout = f(R.id.my_production_layout);
        withdraw_layout = f(R.id.withdraw_layout);
        tv1 = f(R.id.tv1);
        tv2 = f(R.id.tv2);

        setSelected(0);
        one();
        setTitles(this, "我的作品");

    }

    @Override
    protected void initData() {

    }

    /**
     * 页面切换实物提交;
     */
    private void one() {
        ObjectAnimator anima = ShakeAnima(img1);
        anima.start();
        // 提交事务
        if (oneFragment == null) {
            oneFragment = new MyProductionFragment();
            ft.add(R.id.production_layout, oneFragment).show(oneFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(oneFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void two() {
        ObjectAnimator anima = ShakeAnima(img2);
        anima.start();
        if (twoFragment == null) {
            twoFragment = new WithdrawFragment();
            ft.add(R.id.production_layout, twoFragment).show(twoFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(twoFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    /**
     * Fragment Hide方法;
     */
    private void setSelected(int tag) {

        if (tag == 0) {
            img1.setImageResource(R.mipmap.my_production_icon);
            tv1.setTextColor(getRouColors(R.color.black11));

            img2.setImageResource(R.mipmap.withdraw_icon);
            tv2.setTextColor(getRouColors(R.color.content4));
        } else {
            img2.setImageResource(R.mipmap.withdraw_icon);
            tv2.setTextColor(getRouColors(R.color.black11));

            img1.setImageResource(R.mipmap.my_production_icon);
            tv1.setTextColor(getRouColors(R.color.content4));
        }
        if (oneFragment != null) {
            // 隐藏fragment
            ft.hide(oneFragment);
        }
        if (twoFragment != null) {
            ft.hide(twoFragment);
        }
    }

    @Override
    public void onClick(View v) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.withdraw_layout:
                setSelected(1);
                hideTitle(this);
                two();
                break;
            case R.id.my_production_layout:
                ShowTitle(this);
                setTitles(this, "我的作品");
                setSelected(0);
                one();
                break;
            case R.id.add_layout:
                UpLoadPhotoOfVideoPopupWindow pb = new UpLoadPhotoOfVideoPopupWindow(this);
                pb.ScreenPopupWindow();
                break;

        }
    }
}
