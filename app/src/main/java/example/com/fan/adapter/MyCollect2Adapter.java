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

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.MyCollectBean;
import example.com.fan.fragment.son.MyCollectFragment;
import example.com.fan.mylistener.DeleteListener;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class MyCollect2Adapter extends BaseAdapter {
    private static final String TAG = getTAG(MyCollect2Adapter.class);
    private Context context;
    private List<MyCollectBean> list;
    private LayoutInflater inflater;
    private DeleteListener deleteListener;
    private ItemClickListener listener;
    private MyCollectFragment fragment;

    public MyCollect2Adapter(Context context, List<MyCollectBean> list, DeleteListener deleteListener, MyCollectFragment fragment, ItemClickListener listener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
        this.fragment = fragment;
        this.deleteListener = deleteListener;
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
            root = inflater.inflate(R.layout.mycollect_list_item, null);

        ImageView nike_icon = OverallViewHolder.ViewHolder.get(root, R.id.icon_img);
        ImageView cover_img = OverallViewHolder.ViewHolder.get(root, R.id.cover_img);

        ImageView delete_img = OverallViewHolder.ViewHolder.get(root, R.id.delete_img);

        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
//        TextView stype_tv = (TextView) root.findViewById(R.id.type_tv);

        try {
            Glide.with(context)
                    .load(list.get(position).getCoverPath())
                    .apply(getRequestOptions(true, 0, 0,false))
                    .thumbnail(0.1f)
                    .into(cover_img);
            Glide.with(context)
                    .load(list.get(position).getUserHeadImgUrl())
                    .apply(getRequestOptions(true, 50, 50,true))
                    .into(nike_icon);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        name_tv.setText(list.get(position).getUserName());

        cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(5, list.get(position).getid());
            }
        });
        delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> d = new ArrayList<String>();
                d.add(list.get(position).getid());
                deleteListener.onDelete(d);
            }
        });
        return root;
    }

}
