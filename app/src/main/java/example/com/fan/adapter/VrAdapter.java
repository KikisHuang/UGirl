package example.com.fan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.VrBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class VrAdapter extends BaseAdapter {
    private static final String TAG = getTAG(VrAdapter.class);
    private Context context;
    private List<VrBean> list;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private TwoParamaListener tlistener;
    private ShareRequestListener slistener;

    public VrAdapter(List<VrBean> list, Context context, ItemClickListener listener, TwoParamaListener tlistener, ShareRequestListener slistener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
        this.tlistener = tlistener;
        this.slistener = slistener;
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
            root = inflater.inflate(R.layout.vr_item, null);

        ImageView icon_img = OverallViewHolder.ViewHolder.get(root,R.id.icon_img);
        ImageView cover_img = OverallViewHolder.ViewHolder.get(root,R.id.cover_img);
        TextView  user_name = OverallViewHolder.ViewHolder.get(root,R.id.user_name);
        TextView number_tv = OverallViewHolder.ViewHolder.get(root,R.id.number_tv);
        TextView  flag_tv = OverallViewHolder.ViewHolder.get(root,R.id.flag_tv);
        TextView  title_tv = OverallViewHolder.ViewHolder.get(root,R.id.title_tv);
        ImageView  share_img = OverallViewHolder.ViewHolder.get(root,R.id.share_img);


        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slistener.onShare(list.get(position).getUser_id(), list.get(position).getName(), list.get(position).getName(), list.get(position).getId());
            }
        });
        icon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tlistener.onGoPlayPage(list.get(position).getUser_id(), 0);
            }
        });
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowWidth(context) * 9 / 16);
        cover_img.setLayoutParams(lp);
        cover_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            Glide.with(context)
                    .load(list.get(position).getCoverPath())
                    .thumbnail(0.1f)
                    .crossFade(200)
                    .into(cover_img);

            Glide.with(context)
                    .load(list.get(position).getUser_headImgUrl())
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .crossFade(200)
                    .override(50, 50)
                    .into(icon_img);
        } catch (Exception e) {

        }
        user_name.setText(list.get(position).getUser_name());
        if (list.get(position).getSeeCount() == null)
            number_tv.setText("已播放0次");
        else
            number_tv.setText("已播放" + list.get(position).getSeeCount() + "次");
        title_tv.setText(list.get(position).getName());

        cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(list.get(position).getFlag(), list.get(position).getId());
            }
        });

        if (list.get(position).getFlag() == 4)
            flag_tv.setText(getRouString(R.string.comvideo));
        else
            flag_tv.setText(getRouString(R.string.vrvideo));


        return root;
    }

}
