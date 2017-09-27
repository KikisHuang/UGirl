package example.com.fan.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PictureSlidePagerAdapter;
import example.com.fan.fragment.son.ModelFragment;
import example.com.fan.fragment.son.ModelRcFragment;
import example.com.fan.view.CustomViewPager;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/4/22.
 */
public class StoreFragment extends BaseFragment {
    private static final String TAG = getTAG(StoreFragment.class);
    private List<Fragment> flist;
    private List<String> title;
    private CustomViewPager st_viewPager;
    private TabLayout mTab;

    @Override
    protected int initContentView() {
        return R.layout.store_fragment2;
    }

    private void setPager() {
        st_viewPager.setAdapter(new PictureSlidePagerAdapter(getChildFragmentManager(), flist, title));
        mTab.setupWithViewPager(st_viewPager);

        st_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }

    private void setNavi() {
        title.add(getRouString(R.string.model));
        title.add(getRouString(R.string.dynamic));
        mTab.addTab(mTab.newTab().setText(getRouString(R.string.model)));
        mTab.addTab(mTab.newTab().setText(getRouString(R.string.dynamic)));

        ModelFragment rf = new ModelFragment();
        ModelRcFragment rf1 = new ModelRcFragment();
        rf.setTag(0);
        rf1.setTag(1);
        flist.add(rf);
        flist.add(rf1);
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        st_viewPager = (CustomViewPager) view.findViewById(R.id.viewPager);
        mTab = (TabLayout) view.findViewById(R.id.tab_layout);
        mTab.setTabGravity(TabLayout.GRAVITY_CENTER);

        mTab.setTabMode(TabLayout.MODE_FIXED);

        st_viewPager.setOffscreenPageLimit(2);
        flist = new ArrayList<>();
        title = new ArrayList<>();
    }

    @Override
    protected void initData() {
        setNavi();
        setPager();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        } else {
            Log.i(TAG, "onPauser");
        }
    }

}
