package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostModelAdapter;
import example.com.fan.fragment.son.OverPayFragment;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/16.
 */
public class OverPayActivity extends InitActivity {
    private CustomViewPager pr_viewPager;
    private HostModelAdapter page_adapter;
    private TabLayout mTab;
    private List<String> list;
    private List<Fragment> flist;
    private String title[] = {getRouString(R.string.private_photo), getRouString(R.string.VC), getRouString(R.string.Vr),getRouString(R.string.wx_number)};

    @Override
    protected void click() {

    }

    private void getData() {
        setNavi();
        setPager();
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

    private void setNavi() {
        for (int i = 0; i < 4; i++) {
            OverPayFragment rf = new OverPayFragment();
            flist.add(rf);
            rf.setTag(i);
            createView(title[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void init() {
        setContentView(R.layout.over_pay_activity_layout);
        setTitles(this, getRouString(R.string.over_pay));
        list = new ArrayList<>();
        flist = new ArrayList<>();
        mTab = f(R.id.tab_layout);
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        //2.MODE_FIXED模式
        mTab.setTabMode(TabLayout.MODE_FIXED);
        pr_viewPager = f(R.id.viewPager);
        pr_viewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void createView(String str) {
        list.add(str);
        mTab.addTab(mTab.newTab().setText(str));
    }
}
