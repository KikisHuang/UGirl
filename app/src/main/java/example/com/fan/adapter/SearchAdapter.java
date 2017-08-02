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
import example.com.fan.bean.SearchBean;
import example.com.fan.mylistener.SearchItemListener;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class SearchAdapter extends BaseAdapter {
    private static final String TAG = getTAG(SearchAdapter.class);
    private Context context;
    private List<SearchBean> list;
    private LayoutInflater inflater;
    private SearchItemListener listener;

    public SearchAdapter(List<SearchBean> list, Context context, SearchItemListener listener) {
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
            root = inflater.inflate(R.layout.search_item, null);

        ImageView icon_img = OverallViewHolder.ViewHolder.get(root,R.id.icon_img);
        TextView type_tv = OverallViewHolder.ViewHolder.get(root,R.id.type_tv);
        TextView title_tv = OverallViewHolder.ViewHolder.get(root,R.id.title_tv);
        LinearLayout search_ll = OverallViewHolder.ViewHolder.get(root,R.id.search_ll);

        try {
            Glide.with(context)
                    .load(list.get(position).getCoverPath())
                    .override(100, 100)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .crossFade(200)
                    .into(icon_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        title_tv.setText(list.get(position).getName());
        switch (list.get(position).getTypeFlag()) {
            case 0:
                type_tv.setText(getRouString(R.string.model));
                break;
            case 1:
                type_tv.setText(getRouString(R.string.special));
                break;
            case 4:
                type_tv.setText(getRouString(R.string.Video));
                break;
            case 5:
                type_tv.setText(getRouString(R.string.vrvideo));
                break;
        }
        search_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(list.get(position).getTypeFlag(), list.get(position).getId());
            }
        });
        return root;
    }
}
