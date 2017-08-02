package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostModelAdapter;
import example.com.fan.fragment.son.UnReadFragment;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/28.
 */
public class UnReadActivity extends InitActivity {

    private CustomViewPager pr_viewPager;
    private HostModelAdapter page_adapter;
    private TabLayout mTab;
    private List<String> list;
    private List<Fragment> flist;
    private String title[] = {getRouString(R.string.private_photo), getRouString(R.string.VC), getRouString(R.string.Vr)};


    @Override
    protected void click() {

    }

    private void getData() {
        setNavi();
        setPager();
    }

    private void setNavi() {
        for (int i = 0; i < 3; i++) {
            UnReadFragment rf = new UnReadFragment();
            flist.add(rf);
            ((UnReadFragment) flist.get(i)).setTag(i);
            createView(title[i]);
        }
    }

    private void setPager() {
        page_adapter = new HostModelAdapter(getSupportFragmentManager(), flist, list);
        mTab.setupWithViewPager(pr_viewPager);

        pr_viewPager.setAdapter(page_adapter);

        pr_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        pr_viewPager.setCurrentItem(0);
    }

    private void createView(String str) {
        list.add(str);
        mTab.addTab(mTab.newTab().setText(str));
    }


    @Override
    protected void init() {
        setContentView(R.layout.unread_activity_layout);
        setTitles(this, getRouString(R.string.unread));
        list = new ArrayList<>();
        flist = new ArrayList<>();
        mTab = f(R.id.tab_layout);
        //2.MODE_FIXED模式
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        pr_viewPager = f(R.id.viewPager);
        pr_viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initData() {
        getData();
    }
}
