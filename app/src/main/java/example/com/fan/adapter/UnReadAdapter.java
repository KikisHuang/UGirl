package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.UnReadBean;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class UnReadAdapter extends BaseAdapter {
    private static final String TAG = getTAG(UnReadAdapter.class);
    private Context context;
    private List<UnReadBean> list;
    private LayoutInflater inflater;

    public UnReadAdapter(Context context, List<UnReadBean> list) {
        this.context = context.getApplicationContext();
        this.list = list;
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
            root = inflater.inflate(R.layout.unread_item, null);

            ImageView head_img = OverallViewHolder.ViewHolder.get(root,R.id.head_img);
            TextView title = OverallViewHolder.ViewHolder.get(root,R.id.title);
            TextView page_view = OverallViewHolder.ViewHolder.get(root,R.id.page_view);
            TextView name = OverallViewHolder.ViewHolder.get(root,R.id.name);
//           periods = (TextView) root.findViewById(R.id.periods);

       title.setText(list.get(position).getName());
        String see ="";
        if(list.get(position).getSeeCount()>10000)
            see = "被浏览"+list.get(position).getSeeCount()/10000+"万次";
        else
            see = "被浏览"+list.get(position).getSeeCount()+"次";

       page_view.setText(see);
       name.setText(list.get(position).getName());
        try {
            Glide.with(context)
                    .load(list.get(position).getCoverPath())
                    .apply(getRequestOptions(true, 0, 0,true))
                    .into(head_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        return root;
    }
}
