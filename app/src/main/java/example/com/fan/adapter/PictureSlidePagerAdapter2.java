package example.com.fan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import example.com.fan.bean.PhotoType;
import example.com.fan.fragment.son.PrivateFragment2;

/**
 * Created by lian on 2017/5/25.
 */
public class PictureSlidePagerAdapter2 extends FragmentStatePagerAdapter {
    private List<PhotoType> title;

    public PictureSlidePagerAdapter2(FragmentManager fm, List<PhotoType> strings) {
        super(fm);
        this.title = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return PrivateFragment2.setTag(title.get(position).getId(),false);
    }

    @Override
    public int getCount() {
        return title.size();//指定ViewPager的总页数
    }
    //去除页面切换时的滑动翻页效果

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position).getTypeName();
    }
}
