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
import example.com.fan.bean.OverPayVideoVrBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class OverPay2Adapter extends BaseAdapter {
    private static final String TAG = getTAG(OverPay2Adapter.class);
    private Context context;
    private List<OverPayVideoVrBean> list;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private TwoParamaListener tlistener;
    private ShareRequestListener slistener;

    public OverPay2Adapter(List<OverPayVideoVrBean> list, Context context, ItemClickListener listener, TwoParamaListener tlistener, ShareRequestListener slistener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
        this.tlistener = tlistener;
        this.slistener = slistener;
        this.inflater = LayoutInflater.from(context);
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
            root = inflater.inflate(R.layout.over_list_item, null);

        ImageView nike_icon = OverallViewHolder.ViewHolder.get(root, R.id.icon_img);
        ImageView cover_img = OverallViewHolder.ViewHolder.get(root, R.id.cover_img);
        ImageView share_img = OverallViewHolder.ViewHolder.get(root, R.id.share_img);
        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        TextView title_tv = OverallViewHolder.ViewHolder.get(root, R.id.title_tv);
        TextView playe_num = OverallViewHolder.ViewHolder.get(root, R.id.playe_num);

        String num = "";
        if (list.get(position).getSeeCount() > 10000)
            num = "已播放" + list.get(position).getSeeCount() / 10000 + "万次";
        else if (list.get(position).getSeeCount() <= 0)
            num = "已播放" + "0" + "次";
        else
            num = "已播放" + list.get(position).getSeeCount() + "次";

       playe_num.setText(num);
       name_tv.setText(list.get(position).getJoinUser().getName());
       title_tv.setText(list.get(position).getName());
       nike_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context != null)
                    listener.onItemClickListener(2, list.get(position).getJoinUser().getId());
            }
        });

       cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tlistener.onGoPlayPage(list.get(position).getId(), list.get(position).getMcSettingPublishType().getTypeFlag());
            }
        });
        try {

            Glide.with(context)
                    .load(list.get(position).getCoverPath())
                    .apply(getRequestOptions(true, 0, 0,false))
                    .thumbnail(0.1f)
                    .into(cover_img);
            Glide.with(context)
                    .load(list.get(position).getJoinUser().getHeadImgUrl())
                    .apply(getRequestOptions(true, 50, 50,true))
                    .into(nike_icon);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
       share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slistener.onShare(list.get(position).getJoinUser().getId(), list.get(position).getName(), list.get(position).getInfo(), list.get(position).getId());
            }
        });
        return root;
    }
}
