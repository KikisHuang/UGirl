package example.com.fan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import example.com.fan.R;
import example.com.fan.bean.OverPayBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.DateUtils.getDate;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by wiky on 2015/12/25.
 */
public class OverPayAdapter extends RecyclerView.Adapter<OverPayAdapter.ViewHolder> {
    private static final String TAG = getTAG(OverPayAdapter.class);
    private List<OverPayBean> mDataset;
    private Context context;
    private ItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
     static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView icon_name, time_tv;
        private CardView home_layout2;
        private ImageView home_icon2, icon_logo;


        private ViewHolder(View v) {
            super(v);
            icon_name = (TextView) v.findViewById(R.id.icon_name);
            time_tv = (TextView) v.findViewById(R.id.time_tv);
            home_icon2 = (ImageView) v.findViewById(R.id.home_icon2);
            icon_logo = (ImageView) v.findViewById(R.id.icon_logo);
            home_layout2 = (CardView) v.findViewById(R.id.home_layout2);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OverPayAdapter(Context context, List<OverPayBean> myDataset, ItemClickListener listener) {
        this.context = context.getApplicationContext();
        this.mDataset = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.over_rc_item, parent, false);
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
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.getWindowWidth(context) * 5 / 10, (int) (DeviceUtils.getWindowHeight(context) * 4.5 / 10));
        holder.home_icon2.setLayoutParams(lp);
        holder.home_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(0, mDataset.get(position).getId());
            }
        });

        Random ran = new Random();
        int a = ran.nextInt(5);
        int b = a % 3;
        holder.time_tv.setText("上架于:" + getDate(mDataset.get(position).getCreateTime()));
        holder.icon_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(2, mDataset.get(position).getUser_id());
            }
        });
        holder.home_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(0,mDataset.get(position).getId());
            }
        });
        holder.icon_name.setText(mDataset.get(position).getUser_name());
        holder.home_layout2.setCardBackgroundColor(context.getResources().getColor(MzFinal.privatePhotoColors[b]));
        try {
            Glide.with(context)
                    .load(mDataset.get(position).getCoverPath())
                    .centerCrop()
                    .thumbnail(0.1f)
                    .crossFade(1000)
                    .into(holder.home_icon2);
            Glide.with(context)
                    .load(mDataset.get(position).getUser_headImgUrl())
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .crossFade(300)
                    .override(50, 50)
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
