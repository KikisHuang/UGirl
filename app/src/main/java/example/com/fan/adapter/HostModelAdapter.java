package example.com.fan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lian on 2017/5/25.
 */
public class HostModelAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private List<String> title;


    public HostModelAdapter(FragmentManager fm, List<Fragment> list, List<String> strings) {
        super(fm);
        this.list = list;
        this.title = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();//指定ViewPager的总页数
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
