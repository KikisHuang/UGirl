package example.com.fan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostModelAdapter;
import example.com.fan.fragment.son.HostFragment;
import example.com.fan.utils.TitleUtils;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/31.
 * Vip页面;
 */
public class VipActivity extends InitActivity {
    private static final String TAG = getTAG(VipActivity.class);
    private CustomViewPager find_viewPager;
    private HostModelAdapter page_adapter;
    private List<String> list;
    private List<Fragment> flist;


    @Override
    protected void click() {

    }

    private void setPager() {
        page_adapter = new HostModelAdapter(this.getSupportFragmentManager(), flist, list);
        find_viewPager.setAdapter(page_adapter);
        find_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        find_viewPager.setCurrentItem(0);
    }

    private void setFragment() {
        flist.add(new HostFragment());
        ((HostFragment) flist.get(0)).setTag(true, 3);
    }

    @Override
    protected void init() {
        setContentView(R.layout.vip_activity_layout);
        TitleUtils.setTitles(VipActivity.this, getResources().getString(R.string.vip));
        list = new ArrayList<>();
        flist = new ArrayList<>();
        find_viewPager = f(R.id.find_viewPager);
        find_viewPager.setOffscreenPageLimit(1);
    }

    @Override
    protected void initData() {
        setFragment();
        setPager();
    }
}
