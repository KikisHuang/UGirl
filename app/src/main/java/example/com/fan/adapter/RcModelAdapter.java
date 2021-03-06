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
import android.widget.TextView;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.VrBean;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by wiky on 2015/12/25.
 */
public class RcModelAdapter extends RecyclerView.Adapter<RcModelAdapter.ViewHolder> {
    private static final String TAG = getTAG(RcModelAdapter.class);
    private List<VrBean> mDataset;
    private Context context;
    private TwoParamaListener tlistener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView icon_name;
        private ImageView cover_img, icon_img;
        private FrameLayout home_layout2;

        private ViewHolder(View v) {
            super(v);
            icon_name = (TextView) v.findViewById(R.id.name_tv);
            cover_img = (ImageView) v.findViewById(R.id.cover_img);
            icon_img = (ImageView) v.findViewById(R.id.icon_img);
            home_layout2 = (FrameLayout) v.findViewById(R.id.home_layout2);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RcModelAdapter(Context context, List<VrBean> myDataset, TwoParamaListener tlistener) {
        this.context = context.getApplicationContext();
        this.mDataset = myDataset;
        this.tlistener = tlistener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rc_model_online_item, parent, false);
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
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp1.leftMargin = DeviceUtils.dip2px(context, 5);
        lp1.topMargin = DeviceUtils.dip2px(context, 5);
        if (position % 2 != 0)
            lp1.rightMargin = DeviceUtils.dip2px(context, 5);

        holder.home_layout2.setLayoutParams(lp1);
        holder.cover_img.setLayoutParams(lp);

        holder.cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tlistener.onGoPlayPage(mDataset.get(position).getUser_id(), mDataset.get(position).getFlag());
            }
        });
        try {

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
