package example.com.fan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.PageBotGridBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class NewsOrderAdapter extends BaseAdapter {
    private static final String TAG = getTAG(NewsOrderAdapter.class);
    private Context context;
    private List<PageBotGridBean> blist;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    public NewsOrderAdapter(Context context, List<PageBotGridBean> blist, ItemClickListener listener) {
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
            root = inflater.inflate(R.layout.news_order_item, null);

        TextView order_data = OverallViewHolder.ViewHolder.get(root, R.id.order_data);
        TextView order_price = OverallViewHolder.ViewHolder.get(root, R.id.order_price);

        return root;
    }
}
