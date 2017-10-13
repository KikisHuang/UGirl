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
import example.com.fan.bean.CommentBean;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class CommentAdapter extends BaseAdapter {
    private static final String TAG = getTAG(CommentAdapter.class);
    private Context context;
    private List<CommentBean> blist;
    private LayoutInflater inflater;

    public CommentAdapter(Context context, List<CommentBean> blist) {
        this.blist = blist;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.comment_item, null);


        ImageView icon_img = OverallViewHolder.ViewHolder.get(root, R.id.icon_img);
        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        TextView comment_tv = OverallViewHolder.ViewHolder.get(root, R.id.comment_tv);

        try {
            Glide.with(context).load(blist.get(position).getUserHeadImgUrl()).apply(getRequestOptions(false, 50, 50,true)).into(icon_img);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        name_tv.setText(blist.get(position).getUserName());
        comment_tv.setText(blist.get(position).getInfo());

        return root;
    }
}
