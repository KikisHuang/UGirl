package example.com.fan.activity;

import android.support.design.widget.TabLayout;
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
 * 热门模特页面;
 */
public class HostModelActivity extends InitActivity {
    private static final String TAG = getTAG(HostModelActivity.class);
    private CustomViewPager find_viewPager;
    private TabLayout mTab;
    private HostModelAdapter page_adapter;
    private List<String> list;
    private List<Fragment> flist;


    @Override
    protected void click() {

    }

    private void setPager() {
        mTab.setupWithViewPager(find_viewPager);
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
        flist.add(new HostFragment());
        ((HostFragment) flist.get(0)).setTag(false, 1);
        ((HostFragment) flist.get(1)).setTag(false, 2);
    }
    @Override
    protected void init() {
        setContentView(R.layout.host_activity_layout);
        TitleUtils.setTitles(HostModelActivity.this, getResources().getString(R.string.host_model));
        list = new ArrayList<>();
        flist = new ArrayList<>();
        mTab = f(R.id.tab_layout);
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        list.add(this.getResources().getString(R.string.newest));
        list.add(this.getResources().getString(R.string.newhost));
        mTab.addTab(mTab.newTab().setText(list.get(0)));
        mTab.addTab(mTab.newTab().setText(list.get(1)));
        find_viewPager = f(R.id.viewPager);
        find_viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initData() {
        setFragment();
        setPager();
    }
}
