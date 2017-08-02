package example.com.fan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lian on 2017/5/25.
 */
public class PictureSlidePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private  List<String> title;

    public PictureSlidePagerAdapter(FragmentManager fm, List<Fragment> list, List<String> title) {
        super(fm);
        this.list = list;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();//指定ViewPager的总页数
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

}
