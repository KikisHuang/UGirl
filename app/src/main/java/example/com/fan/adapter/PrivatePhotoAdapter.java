package example.com.fan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import example.com.fan.R;
import example.com.fan.bean.PrivateBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.view.YuanJiaoImageView;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by wiky on 2015/12/25.
 */
public class PrivatePhotoAdapter extends RecyclerView.Adapter<PrivatePhotoAdapter.ViewHolder> {
    private static final String TAG = getTAG(PrivatePhotoAdapter.class);
    private List<PrivateBean> mDataset;
    private Context context;
    private ItemClickListener listener;
    private ShareRequestListener slistener;
    private int[] drwables = {R.drawable.private_corner1, R.drawable.private_corner2, R.drawable.private_corner3};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView icon_name;
        private FrameLayout home_layout2;
        private ImageView icon_logo, share_img;
        private YuanJiaoImageView home_icon2;

        private ViewHolder(View v) {
            super(v);
            icon_name = (TextView) v.findViewById(R.id.icon_name);
            home_icon2 = (YuanJiaoImageView) v.findViewById(R.id.home_icon2);
            icon_logo = (ImageView) v.findViewById(R.id.icon_logo);
            share_img = (ImageView) v.findViewById(R.id.share_img);
            home_layout2 = (FrameLayout) v.findViewById(R.id.home_layout2);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PrivatePhotoAdapter(Context context, List<PrivateBean> myDataset, ItemClickListener listener, ShareRequestListener slistener) {
        this.context = context;
        this.mDataset = myDataset;
        this.listener = listener;
        this.slistener = slistener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.private_photo_item, parent, false);
        parent.setBackgroundColor(Color.TRANSPARENT);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.home_icon2.setAdjustViewBounds(true);
        int w = DeviceUtils.getWindowWidth(context) * 5 / 10;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, w * 7 / 5);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp1.leftMargin = DeviceUtils.dip2px(context, 3);
        lp1.topMargin = DeviceUtils.dip2px(context, 3);
        if (position % 2 != 0)
            lp1.rightMargin = DeviceUtils.dip2px(context, 3);

        holder.home_icon2.setLayoutParams(lp);
        holder.home_layout2.setLayoutParams(lp1);
        holder.icon_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(0, mDataset.get(position).getUser_id());
            }
        });
        holder.home_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(1, mDataset.get(position).getId());
            }
        });
        holder.home_icon2.setScaleType(ImageView.ScaleType.FIT_XY);

        Random ran = new Random();
        int a = ran.nextInt(5);
        int b = a % 3;

        holder.home_layout2.setBackground(context.getResources().getDrawable(drwables[b]));

        holder.icon_name.setText(mDataset.get(position).getName());
        holder.share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slistener.onShare(mDataset.get(position).getUser_id(), mDataset.get(position).getUser_name(), mDataset.get(position).getUser_name(), mDataset.get(position).getId());
            }
        });
        try {
            //做个tag判断,防止不设置固定高度时刷新的闪烁问题;
            if (!mDataset.get(position).getCoverPath().equals(holder.home_icon2.getTag(R.id.home_icon2))) {
                Glide.with(context)
                        .load(mDataset.get(position).getCoverPath())
                        .into(holder.home_icon2);
                holder.home_icon2.setTag(R.id.home_icon2, mDataset.get(position).getCoverPath());
            }

            Glide.with(context)
                    .load(mDataset.get(position).getUser_headImgUrl())
                    .apply(getRequestOptions(true, 50, 50,true))
                    .into(holder.icon_logo);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
