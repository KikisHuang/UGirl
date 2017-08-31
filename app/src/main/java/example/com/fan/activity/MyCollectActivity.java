package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostModelAdapter;
import example.com.fan.fragment.son.MyCollectFragment;
import example.com.fan.mylistener.SelectListener;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getRouString;

/**
 * Created by lian on 2017/6/26.
 */
public class MyCollectActivity extends InitActivity implements View.OnClickListener, SelectListener {

    private CustomViewPager pr_viewPager;
    private HostModelAdapter page_adapter;
    private TabLayout mTab;
    private List<String> list;
    private List<Fragment> flist;
    private String title[] = {getRouString(R.string.special), getRouString(R.string.VC), getRouString(R.string.Vr)};
    private ImageView back_img;
    private TextView search_tv;
    private int mpos;
    public static SelectListener slistener;

    @Override
    protected void click() {
        search_tv.setOnClickListener(this);
        back_img.setOnClickListener(this);
    }

    private void getData() {
        setNavi();
        setPager();
    }

    private void setNavi() {
        for (int i = 0; i < 3; i++) {
            MyCollectFragment rf = new MyCollectFragment();
            flist.add(rf);
            ((MyCollectFragment) flist.get(i)).setTag(i);
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
                mpos = position;
                if (position < 2) {
                    search_tv.setVisibility(View.VISIBLE);
                    if (((MyCollectFragment) flist.get(position)).delete_tv.getVisibility() == View.GONE)
                        search_tv.setText("选择");
                    else
                        search_tv.setText("取消");
                } else
                    search_tv.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createView(String str) {
        list.add(str);
        mTab.addTab(mTab.newTab().setText(str));
    }

    @Override
    protected void init() {
        setContentView(R.layout.mycollect_activity_layout);
        list = new ArrayList<>();
        flist = new ArrayList<>();
        mTab = f(R.id.tab_layout);
        slistener = this;

        //2.MODE_FIXED模式
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        pr_viewPager = f(R.id.viewPager);
        pr_viewPager.setOffscreenPageLimit(3);
        back_img = f(R.id.back_img);
        search_tv = f(R.id.search_tv);
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.search_tv:
                if (search_tv.getText().toString().equals("选择")) {
                    search_tv.setText("取消");
                    ((MyCollectFragment) flist.get(mpos)).TvShow();
                } else {
                    search_tv.setText("选择");
                    ((MyCollectFragment) flist.get(mpos)).TvHide();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (slistener != null)
            slistener = null;
    }

    @Override
    public void onSelect() {
        if (search_tv.getText().toString().equals("选择"))
            search_tv.setText("取消");
        else
            search_tv.setText("选择");

    }
}
