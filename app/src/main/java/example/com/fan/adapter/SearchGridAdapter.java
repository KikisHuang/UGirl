package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.SearchTagBean;
import example.com.fan.mylistener.SearchItemListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class SearchGridAdapter extends BaseAdapter {
    private static final String TAG = getTAG(SearchGridAdapter.class);
    private Context context;
    private List<SearchTagBean> blist;
    private LayoutInflater inflater;
    private SearchItemListener listener;
    private int tag;


    public SearchGridAdapter(Context context, List<SearchTagBean> blist, SearchItemListener listener, int tag) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.tag = tag;
        this.inflater = LayoutInflater.from(context);
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
            root = inflater.inflate(R.layout.search_grid_item, null);

            TextView name_tv = OverallViewHolder.ViewHolder.get(root,R.id.name_tv);
            RelativeLayout grid_ll = OverallViewHolder.ViewHolder.get(root,R.id.grid_ll);
            RelativeLayout item_rl = OverallViewHolder.ViewHolder.get(root,R.id.item_rl);

        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(DeviceUtils.getWindowWidth(context) * 2 / 10, DeviceUtils.dip2px(context, 38));
        grid_ll.setLayoutParams(lp);
        switch (tag){
            case 0:
                name_tv.setText(blist.get(position).getRealName());
                break;
            case 1:
                name_tv.setText(blist.get(position).getName());
                break;

        }
        item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "click");
                if(tag==1)
                listener.onItemClick(tag + 10, blist.get(position).getName());
                else
                    listener.onItemClick(tag + 10, blist.get(position).getUserId());
            }
        });
        return root;
    }

}
