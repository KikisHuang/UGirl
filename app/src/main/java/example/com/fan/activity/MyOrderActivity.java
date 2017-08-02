package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostModelAdapter;
import example.com.fan.fragment.son.MyOrderFragment;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/27.
 * 我的订单页面;
 */
public class MyOrderActivity extends InitActivity {
    private CustomViewPager pr_viewPager;
    private HostModelAdapter page_adapter;
    private TabLayout mTab;
    private List<String> list;
    private List<Fragment> flist;
    private String title[] = {getRouString(R.string.allorder), getRouString(R.string.over_pay), getRouString(R.string.refund_order)};


    @Override
    protected void click() {

    }

    private void getData() {
        setNavi();
        setPager();
    }

    private void setNavi() {
        for (int i = 0; i < 3; i++) {
            MyOrderFragment rf = new MyOrderFragment();
            flist.add(rf);
            rf.setTag(i);
            createView(title[i]);
        }
    }

    private void createView(String str) {
        list.add(str);
        mTab.addTab(mTab.newTab().setText(str));
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

    @Override
    protected  void init() {
        setContentView(R.layout.myorder_activity_layout);
        setTitles(this, getRouString(R.string.my_order));
        list = new ArrayList<>();
        flist = new ArrayList<>();
        mTab = f(R.id.tab_layout);
        //2.MODE_FIXED模式
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        pr_viewPager = f(R.id.viewPager);
        pr_viewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void initData() {
        getData();
    }

}
