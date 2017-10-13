package example.com.fan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
import example.com.fan.bean.NewstHostBean;
import example.com.fan.mylistener.VipAndModelItemClickListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by wiky on 2015/12/25.
 */
public class HostAdapter extends RecyclerView.Adapter<HostAdapter.ViewHolder> {
    private static final String TAG = getTAG(HostAdapter.class);
    private List<NewstHostBean> mDataset;
    private Context context;
    private VipAndModelItemClickListener listener;
    private boolean vip;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView icon_name, vip_icon_name;
        private CardView home_layout2;
        private ImageView home_icon2, icon_logo, vip_share_img;
        private LinearLayout vip_ll, model_ll;


        private ViewHolder(View v) {
            super(v);
            icon_name = (TextView) v.findViewById(R.id.icon_name);
            vip_icon_name = (TextView) v.findViewById(R.id.vip_icon_name);
            home_icon2 = (ImageView) v.findViewById(R.id.home_icon2);
            vip_share_img = (ImageView) v.findViewById(R.id.vip_share_img);
            icon_logo = (ImageView) v.findViewById(R.id.icon_logo);
            home_layout2 = (CardView) v.findViewById(R.id.home_layout2);
            model_ll = (LinearLayout) v.findViewById(R.id.model_ll);
            vip_ll = (LinearLayout) v.findViewById(R.id.vip_ll);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HostAdapter(Context context, List<NewstHostBean> myDataset, VipAndModelItemClickListener listener, boolean vip) {
        this.context = context.getApplicationContext();
        this.mDataset = myDataset;
        this.listener = listener;
        this.vip = vip;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.host_item, parent, false);
        parent.setBackgroundColor(Color.TRANSPARENT);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.home_icon2.setAdjustViewBounds(true);
        Random ran = new Random();
        int a = ran.nextInt(5);
        int b = a % 3;


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.getWindowWidth(context) * 5 / 10, (int) (DeviceUtils.getWindowHeight(context) * 4.3 / 10));
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp1.leftMargin = DeviceUtils.dip2px(context, 5);
        lp1.topMargin = DeviceUtils.dip2px(context, 5);
        if (position % 2 != 0)
            lp1.rightMargin = DeviceUtils.dip2px(context, 5);

        holder.home_layout2.setLayoutParams(lp1);
        holder.home_icon2.setLayoutParams(lp);
        if (vip) {
            holder.model_ll.setVisibility(View.GONE);
            holder.vip_ll.setVisibility(View.VISIBLE);
            holder.vip_icon_name.setText(mDataset.get(position).getUser_name());
            holder.home_layout2.setCardBackgroundColor(context.getResources().getColor(MzFinal.privatePhotoColors[b]));
            holder.vip_share_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.home_layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(position, mDataset.get(position).getUser_id());
                }
            });
        } else {

            holder.vip_ll.setVisibility(View.GONE);
            holder.model_ll.setVisibility(View.VISIBLE);
            holder.icon_name.setText(mDataset.get(position).getUser_name());
            holder.home_layout2.setCardBackgroundColor(context.getResources().getColor(MzFinal.privatePhotoColors[b]));
//            holder.share_img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ShareUtils.ShareApp(context,mDataset.get(position).getUser_id(),mDataset.get(position).getUser_name(),mDataset.get(position).getUser_name(),mDataset.get(position).getId());
//                }
//            });
//            holder.admire_num.setText(String.valueOf(mDataset.get(position).getLikesCount()));

            holder.home_layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(position, mDataset.get(position).getUser_id());
                }
            });
        }

        try {

            Glide.with(context)
                    .load(mDataset.get(position).getCoverPath())
                    .apply(getRequestOptions(true, 0, 0,false))
                    .thumbnail(0.1f)
                    .into(holder.home_icon2);
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
