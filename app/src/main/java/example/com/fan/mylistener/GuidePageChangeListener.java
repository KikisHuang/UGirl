package example.com.fan.mylistener;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

import static example.com.fan.utils.SynUtils.getTAG;


/**
 * viewPager监听器;
 */
public final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
    private static final String TAG = getTAG(GuidePageChangeListener.class);
    private ViewPager found_ViewPager;
    private FragmentManager supportFragmentManager;
    private List<String> list;


    public GuidePageChangeListener(ViewPager found_ViewPager, FragmentManager supportFragmentManager, List<String> list) {
        this.found_ViewPager = found_ViewPager;
        this.supportFragmentManager = supportFragmentManager;
        this.list = list;

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.i(TAG, "position======" + position);
        if (position == 0) {
            found_ViewPager.setCurrentItem(0);
        } else {
            found_ViewPager.setCurrentItem(1);
        }

    }
}