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
import example.com.fan.bean.AttentBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class AttentionAdapter extends BaseAdapter {
    private static final String TAG = getTAG(AttentionAdapter.class);
    private Context context;
    private List<AttentBean> list;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    public AttentionAdapter(List<AttentBean> list, Context context, ItemClickListener listener) {
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
            root = inflater.inflate(R.layout.attention_item, null);

        ImageView at_icon = OverallViewHolder.ViewHolder.get(root, R.id.at_icon);
        TextView at_name = OverallViewHolder.ViewHolder.get(root, R.id.at_name);
        TextView at_number = OverallViewHolder.ViewHolder.get(root, R.id.at_number);
        LinearLayout attent_ll = OverallViewHolder.ViewHolder.get(root, R.id.attent_ll);
        TextView cancel_tv = OverallViewHolder.ViewHolder.get(root, R.id.cancel_tv);

        try {
            Glide.with(context).load(list.get(position).getHeadImgUrl()).apply(getRequestOptions(false, 80, 80,true)).into(at_icon);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }

        at_number.setText("被" + list.get(position).getFollwCount() + "人关注");
        at_name.setText(list.get(position).getName());
        attent_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(0, list.get(position).getId());
            }
        });

        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(1, list.get(position).getId());
            }
        });
        return root;
    }
}
