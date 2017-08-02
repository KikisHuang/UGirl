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
import example.com.fan.bean.VrVideoBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class VdVrAdapter extends BaseAdapter {
    private static final String TAG = getTAG(VdVrAdapter.class);
    private Context context;
    private List<VrVideoBean> blist;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private ShareRequestListener slistener;

    public VdVrAdapter(Context context, List<VrVideoBean> blist, ItemClickListener listener, ShareRequestListener slistener) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.slistener = slistener;
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
            root = inflater.inflate(R.layout.vdvr_item, null);

        ImageView icon_img = OverallViewHolder.ViewHolder.get(root,R.id.icon_img);
        ImageView cover_img = OverallViewHolder.ViewHolder.get(root,R.id.cover_img);
        ImageView share_img = OverallViewHolder.ViewHolder.get(root,R.id.share_img);
        TextView user_name = OverallViewHolder.ViewHolder.get(root,R.id.user_name);
        TextView title_tv = OverallViewHolder.ViewHolder.get(root,R.id.title_tv);
        TextView number_tv = OverallViewHolder.ViewHolder.get(root,R.id.number_tv);
            
        try {
           cover_img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(blist.get(position).getUser_headImgUrl()).override(100, 100).bitmapTransform(new CropCircleTransformation(context)).crossFade(500).into(icon_img);
            Glide.with(context).load(blist.get(position).getCoverPath()).override(800, 480).into(cover_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
       user_name.setText(blist.get(position).getUser_name());
       title_tv.setText(blist.get(position).getName());

       icon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(1, blist.get(position).getUser_id());
            }
        });
       cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(0, blist.get(position).getId());
            }
        });
       share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slistener.onShare(blist.get(position).getUser_id(), blist.get(position).getUser_name(), blist.get(position).getName(), blist.get(position).getId());
            }
        });
        String playNumb = "";
        if (blist.get(position).getSeeCount() > 10000) {
            playNumb = String.valueOf("已播放" + blist.get(position).getSeeCount() / 10000) + "万次";
        } else
            playNumb = String.valueOf("已播放" + blist.get(position).getSeeCount()) + "次";

       number_tv.setText(playNumb);
        return root;
    }
}
