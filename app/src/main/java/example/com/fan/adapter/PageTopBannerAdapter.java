package example.com.fan.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.com.fan.MyAppcation;
import example.com.fan.bean.PageTopBannerBean;

import static example.com.fan.utils.BannerUtils.goBannerPage;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by kk on 2016/7/26.
 */
public class PageTopBannerAdapter extends PagerAdapter {
    private static final String TAG = getTAG(PageTopBannerAdapter.class);
    private List<PageTopBannerBean> list;
    private Context context;
    private int tag;

    public PageTopBannerAdapter(List<PageTopBannerBean> list, Context context, int tag) {
        this.list = list;
        this.tag = tag;
        this.context = context;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginStatusQuery()) {
                    switch (tag) {
                        case 0:
                            goBannerPage(context,list.get(position).getType(),list.get(position).getHttpUrl(),list.get(position).getValue());
                            break;
                        case 1:
                            if (MyAppcation.VipFlag)
                                goBannerPage(context,list.get(position).getType(),list.get(position).getHttpUrl(),list.get(position).getValue());

                            break;

                    }

                } else
                    Login(context);

            }
        });
        container.addView(view);
        return list.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position).getView());
    }
}

