package example.com.fan.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.com.fan.bean.McOfficialSellImgUrls;

/**
 * Created by kk on 2016/7/26.
 */
public class BuyPagerAdapter extends PagerAdapter {
    private List<McOfficialSellImgUrls> list;
    private Context context;

    public BuyPagerAdapter(List<McOfficialSellImgUrls> list, Context context) {
        this.list = list;
        this.context = context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = list.get(position).getView();

        container.addView(view);
        return list.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position).getView());
    }
}

