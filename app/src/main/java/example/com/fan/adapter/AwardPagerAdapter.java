package example.com.fan.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.AwardBean;
import example.com.fan.mylistener.AwardListener;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by kk on 2016/7/26.
 */
public class AwardPagerAdapter extends PagerAdapter {
    private static final String TAG = getTAG(AwardPagerAdapter.class);
    private List<AwardBean> list;
    private Context context;
    private AwardListener listener;
    private LayoutInflater inflater;
    private List<View> views;

    public AwardPagerAdapter(List<AwardBean> list, Context context, AwardListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        views = new ArrayList<>();
        inflater = ((Activity) context).getLayoutInflater();
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
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = list.get(position).getView();
        views.add(view);
        final ImageView select = (ImageView) view.findViewById(R.id.select_img);
        final ImageView dim_img = (ImageView) view.findViewById(R.id.dim_img);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    ToastUtil.toast2_bottom(context, "至少要有一张图片是免费的哦~~");
                else
                    listener.onSelect(position, views);
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

