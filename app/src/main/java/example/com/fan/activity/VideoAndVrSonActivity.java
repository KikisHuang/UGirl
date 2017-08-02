package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.HostModelAdapter;
import example.com.fan.fragment.son.VDVRFragment;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/19.
 */
public class VideoAndVrSonActivity extends InitActivity {
    /**
     * params : tag
     * 1 = vr;
     * 0 =video;
     */
    private int tag;
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<String> title;
    private List<Fragment> flist;
    private HostModelAdapter page_adapter;


    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.video_vr_activity_layout);
        getTag();
        mTab = f(R.id.tab_layout);
        viewPager = f(R.id.viewPager);

        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setOffscreenPageLimit(4);
        title = new ArrayList<>();
        flist = new ArrayList<>();
    }

    @Override
    protected void initData() {
        setMyTitles();
        setPager();
    }

    private void setMyTitles() {
        switch (tag) {
            case 0:
                setTitles(VideoAndVrSonActivity.this, getRouString(R.string.videoson));
                title.add(getRouString(R.string.newest));
                title.add(getRouString(R.string.newhost));
                title.add(getRouString(R.string.vip1));
                createView(title);
                break;
            case 1:
                setTitles(VideoAndVrSonActivity.this, getRouString(R.string.vrson));
                title.add(getRouString(R.string.free));
                title.add(getRouString(R.string.vip1));
                createView(title);
                break;
        }
    }

    /**
     * 根据标识符判断是Video页面还是Vr页面;
     */
    private void getTag() {

        tag = Integer.parseInt(getIntent().getStringExtra("VideoVR_tag"));
    }

    private void createView(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            flist.add(new VDVRFragment());
            ((VDVRFragment) flist.get(i)).setTag(tag,i);
            mTab.addTab(mTab.newTab().setText(str.get(i)));
        }
    }

    private void setPager() {
        page_adapter = new HostModelAdapter(getSupportFragmentManager(), flist, title);
        mTab.setupWithViewPager(viewPager);

        viewPager.setAdapter(page_adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        viewPager.setCurrentItem(0);
    }
}
