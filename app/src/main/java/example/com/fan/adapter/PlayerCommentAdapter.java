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
 * Created by lian on 2017/6/19.
 */
public class PlayerCommentAdapter extends BaseAdapter {
    private static final String TAG = getTAG(PlayerCommentAdapter.class);
    private Context context;
    private List<CommentBean> list;
    private LayoutInflater inflater;

    public PlayerCommentAdapter(List<CommentBean> list, Context context) {
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
            root = inflater.inflate(R.layout.player_comment_item, null);

        ImageView comment_icon = OverallViewHolder.ViewHolder.get(root, R.id.comment_icon);
        TextView user_name = OverallViewHolder.ViewHolder.get(root, R.id.user_name);
        TextView comment_content = OverallViewHolder.ViewHolder.get(root, R.id.comment_content);

        try {
            Glide.with(context).load(list.get(position).getUserHeadImgUrl()).apply(getRequestOptions(false, 50, 50,true)).into(comment_icon);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        user_name.setText(list.get(position).getUserName());
        comment_content.setText(list.get(position).getInfo());

        return root;
    }
}
