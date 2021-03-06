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
public class PictureSlidePagerAdapter3 extends FragmentStatePagerAdapter {
    private List<PhotoType> list;

    public PictureSlidePagerAdapter3(FragmentManager fm, List<PhotoType> strings) {
        super(fm);
        this.list = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return PrivateFragment2.setTag(list.get(position).getId(),true);
    }

    @Override
    public int getCount() {
        return list.size();//指定ViewPager的总页数
    }
    //去除页面切换时的滑动翻页效果
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTypeName();
    }
}
