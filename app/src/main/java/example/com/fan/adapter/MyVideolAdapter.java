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

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.StoreBean;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by wiky on 2015/12/25.
 */
public class MyVideolAdapter extends RecyclerView.Adapter<MyVideolAdapter.ViewHolder> {
    private static final String TAG = getTAG(MyVideolAdapter.class);
    private List<StoreBean> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView cover_img;
        private LinearLayout home_layout2;

        private ViewHolder(View v) {
            super(v);
            cover_img = (ImageView) v.findViewById(R.id.cover_img);
            home_layout2 = (LinearLayout) v.findViewById(R.id.home_layout2);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyVideolAdapter(Context context, List<StoreBean> myDataset) {
        this.context = context.getApplicationContext();
        this.mDataset = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_video_item, parent, false);
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

        int w = DeviceUtils.getWindowWidth(context) * 5 / 10;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, w * 7 / 5);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp1.leftMargin = DeviceUtils.dip2px(context, 5);
        lp1.topMargin = DeviceUtils.dip2px(context, 5);
        if (position % 2 != 0)
            lp1.rightMargin = DeviceUtils.dip2px(context, 5);

        holder.home_layout2.setLayoutParams(lp1);
        holder.cover_img.setLayoutParams(lp);

        try {

            Glide.with(context)
                    .load(mDataset.get(position).getMcOfficialSellShoppingMall().getCoverPath())
                    .apply(getRequestOptions(true, 0, 0,false))
                    .into(holder.cover_img);

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
