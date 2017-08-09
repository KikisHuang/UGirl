package example.com.fan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import example.com.fan.R;
import example.com.fan.bean.TestBean;
import example.com.fan.fragment.TestFragment;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    ViewPager viewpager;
    SmartTabLayout viewPagerTab;
    ArrayList<TestBean> cb;

    @Override
    protected void click() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        viewpager = f(R.id.viewpager);
        viewPagerTab = f(R.id.viewpagertab);
    }

    @Override
    protected void initData() {
        initSmartTabInfos();
        initSmartTablayoutAndViewPager();
    }
    protected  void initSmartTabInfos(){
        //初始化Adapter需要使用的数据,标题,创建的Fragment对象,传递的参数
        cb = new ArrayList<TestBean>();
        // Fragment fragment = Fragment.instantiate(mContext, NewsPagerFragment.class.getName());
        cb.add(new TestBean("最新动弹", new TestFragment()));
        cb.add(new TestBean("热门",new TestFragment()));
        cb.add(new TestBean("我的动弹1", new TestFragment()));
    }
    private void initSmartTablayoutAndViewPager() {

        viewpager.setAdapter(new MyPagerAdapter(this.getSupportFragmentManager()));
        viewPagerTab.setViewPager(viewpager);
    }
    @Override
    public void onClick(View v) {
    }
    class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return cb.get(position).mClazz;
        }

        @Override
        public int getCount() {
            return cb.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return cb.get(position).mTitle;
        }
    }

}
