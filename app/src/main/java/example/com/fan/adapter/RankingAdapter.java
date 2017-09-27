package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.RankingBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class RankingAdapter extends BaseAdapter {
    private static final String TAG = getTAG(RankingAdapter.class);
    private Context context;
    private List<RankingBean> list;
    private LayoutInflater inflater;
    private int tag;
    private ItemClickListener listener;

    public RankingAdapter(List<RankingBean> list, Context context, ItemClickListener listener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    //返回要显示的数量
    @Override
    public int getCount() {
        return list.size();
    }

    //返回当前Item显示的数据
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.ranking_item, null);

        ImageView nike_icon = OverallViewHolder.ViewHolder.get(root,R.id.nike_icon);
        TextView nike_name = OverallViewHolder.ViewHolder.get(root,R.id.nike_name);
        TextView number_tv = OverallViewHolder.ViewHolder.get(root,R.id.number_tv);
        TextView sub_number = OverallViewHolder.ViewHolder.get(root,R.id.sub_number);
        LinearLayout ranking_layout = OverallViewHolder.ViewHolder.get(root,R.id.ranking_layout);

        /**
         * 将前三隐藏;
         */
        if (position > 2) {
            ranking_layout.setVisibility(View.VISIBLE);
            try {
                Glide.with(context).load(list.get(position).getHeadImgUrl()).bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(nike_icon);

            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
                nike_name.setText(list.get(position).getName());
            number_tv.setText("NO." + String.valueOf(position + 1));
            if (tag <= 1)
                sub_number.setText(getRouString(R.string.subscription) + list.get(position).getFollwCount() + "万");
            else
                sub_number.setText("被" + list.get(position).getFollwCount() + "人" + getRouString(R.string.attention));
        } else {
            ranking_layout.setVisibility(View.GONE);
        }
        ranking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(tag,list.get(position).getId());
            }
        });

        return root;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
