package example.com.fan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import example.com.fan.fragment.son.UnReadFragment;

/**
 * Created by lian on 2017/5/25.
 */
public class UnReadlAdapter extends FragmentStatePagerAdapter {
    private List<String> title;


    public UnReadlAdapter(FragmentManager fm,  List<String> strings) {
        super(fm);
        this.title = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return UnReadFragment.setTag(position);
    }

    @Override
    public int getCount() {
        return title.size();//指定ViewPager的总页数
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
