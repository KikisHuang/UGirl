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
import example.com.fan.bean.PageBotGridBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class BottomGridAdapter extends BaseAdapter {
    private static final String TAG = getTAG(BottomGridAdapter.class);
    private Context context;
    private List<PageBotGridBean> blist;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    public BottomGridAdapter(Context context, List<PageBotGridBean> blist, ItemClickListener listener) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return blist.size();
    }

    @Override
    public Object getItem(int position) {
        return blist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.bottom_grid_item, null);

        ImageView grid_img = OverallViewHolder.ViewHolder.get(root, R.id.bottom_grid_img);
        LinearLayout grid_ll = OverallViewHolder.ViewHolder.get(root, R.id.grid_ll);
        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);

        int w = DeviceUtils.getWindowWidth(context) * 5 / 10;
        name_tv.setText(blist.get(position).getName());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w * 7 / 5);
        grid_img.setLayoutParams(lp);
        grid_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position, blist.get(position).getId());
            }
        });
        try {
            Glide.with(context).load(blist.get(position).getCoverPath()).fitCenter().into(grid_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        Log.i(TAG, "GlideCache==== " + GlideCacheUtil.getCacheSize(context));
        return root;
    }

/*    static class ViewHolder {
        private ImageView grid_img;
        private TextView name_tv;
        private LinearLayout grid_ll;
    }*/
}
