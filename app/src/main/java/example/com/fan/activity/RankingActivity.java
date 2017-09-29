package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PictureSlidePagerAdapter;
import example.com.fan.fragment.son.RankingFragment;
import example.com.fan.utils.TitleUtils;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.Finish;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.setIndicator;

/**
 * Created by lian on 2017/5/19.
 * 排行榜页面;
 */
public class RankingActivity extends InitActivity {
    private int tag = 0;
    private List<String> list;
    private List<String> title;
    private List<Fragment> flist;
    private CustomViewPager rk_viewPager;
    private TabLayout mTab;

    @Override
    public void onStart() {
        super.onStart();
        mTab.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTab, 15, 15);
            }
        });
    }

    private void setPager() {
        rk_viewPager.setAdapter(new PictureSlidePagerAdapter(this.getSupportFragmentManager(), flist, title));
        mTab.setupWithViewPager(rk_viewPager);
        rk_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        rk_viewPager.setCurrentItem(tag);
    }


    @Override
    protected void click() {

//        for (int i = 0; i < llist.size(); i++) {
//            final int finalI = i;
//
//            llist.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    rk_viewPager.setCurrentItem(finalI);
//                }
//            });
//        }
    }

    private void setNavi() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                RankingFragment rf = new RankingFragment();
                rf.setTag(i);
                flist.add(rf);
            }
        }
    }

    private void getTag() {
        try {
            String a = getIntent().getStringExtra("Rangking_Tag");
            if (cleanNull(a))
                finish();

            tag = Integer.valueOf(a);
            String[] str = {getRouString(R.string.ranking1), getRouString(R.string.ranking2), getRouString(R.string.ranking3), getRouString(R.string.ranking4), getRouString(R.string.ranking5)};

            for (int i = 0; i < str.length; i++) {
                list.add(str[i]);
                mTab.addTab(mTab.newTab().setText(str[i]));
                title.add(str[i]);
            }
        } catch (NullPointerException e) {
            finish();
        }
    }

    @Override
    protected void init() {
        setContentView(R.layout.ranking_layout);
        TitleUtils.setTitles(this, getResources().getString(R.string.ranking));
        list = new ArrayList<>();
        title = new ArrayList<>();
        flist = new ArrayList<>();
        mTab = f(R.id.tab_layout);
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);

        mTab.setTabMode(TabLayout.MODE_FIXED);

        rk_viewPager = f(R.id.viewPager);
        rk_viewPager.setOffscreenPageLimit(5);
        getTag();
    }

    @Override
    protected void initData() {
        setNavi();
        setPager();
    }
}
