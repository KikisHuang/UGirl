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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.activity.MyCollectActivity;
import example.com.fan.bean.MyCollectBean;
import example.com.fan.fragment.son.MyCollectFragment;
import example.com.fan.mylistener.DeleteListener;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.view.RippleView;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by wiky on 2015/12/25.
 */
public class MyCollectAdapter extends RecyclerView.Adapter<MyCollectAdapter.ViewHolder> {
    private static final String TAG = getTAG(MyCollectAdapter.class);
    private List<MyCollectBean> mDataset;
    private Context context;
    private ItemClickListener listener;
    private boolean SWITCH = false;
    //删除集合;
    private List<String> delecte;
    //记录recycler的状态;
    private List<Boolean> state;
    private DeleteListener deleteListener;
    private RippleView delete_tv;
    private MyCollectFragment f;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
     static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView home_icon, keep_out, delect_img;


        private ViewHolder(View v) {
            super(v);
            home_icon = (ImageView) v.findViewById(R.id.home_icon);
            keep_out = (ImageView) v.findViewById(R.id.keep_out);
            delect_img = (ImageView) v.findViewById(R.id.delect_img);

        }
    }

    public void setSwitch(boolean SWITCH) {
        this.SWITCH = SWITCH;
        delecte.clear();
        state.clear();
        for (int i = 0; i < mDataset.size(); i++) {
            state.add(false);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyCollectAdapter(Context context, List<MyCollectBean> myDataset, ItemClickListener listener, RippleView delete_tv, DeleteListener deleteListener, MyCollectFragment f) {
        this.context = context.getApplicationContext();
        this.mDataset = myDataset;
        this.listener = listener;
        this.delete_tv = delete_tv;
        this.deleteListener = deleteListener;
        this.f = f;
        delecte = new ArrayList<>();
        state = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycollect_rc_item, parent, false);
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
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getWindowWidth(context) * 4 / 10));
        holder.home_icon.setLayoutParams(lp);

        if (SWITCH) {

            holder.delect_img.setVisibility(View.VISIBLE);
            holder.keep_out.setVisibility(View.VISIBLE);
            if (state.get(position) == true)
                holder.delect_img.setImageResource(R.mipmap.on_icon);
            else
                holder.delect_img.setImageResource(R.mipmap.off_icon);

            holder.keep_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (delecte.size() > 0) {
                        boolean f = false;

                        for (int i = 0; i < delecte.size(); i++) {
                            Log.i(TAG, "删除集合输出" + delecte.get(i));
                            if (mDataset.get(position).getMcPublishRecord().equals(delecte.get(i))) {
                                holder.delect_img.setImageResource(R.mipmap.off_icon);
                                delecte.remove(i);
                                state.set(position, false);
                                f = true;
                            }
                        }
                        if (!f) {
                            delecte.add(mDataset.get(position).getMcPublishRecord());
                            holder.delect_img.setImageResource(R.mipmap.on_icon);
                            state.set(position, true);
                        }

                    } else {

                        Log.i(TAG, "position" + position);
                        delecte.add(mDataset.get(position).getMcPublishRecord());
                        holder.delect_img.setImageResource(R.mipmap.on_icon);
                        state.set(position, true);
                    }
                }
            });
            delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.onDelete(delecte);
                }
            });
        } else {
            holder.delect_img.setVisibility(View.GONE);
            holder.keep_out.setVisibility(View.GONE);
            holder.home_icon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (MyCollectActivity.slistener != null) {
                        f.TvShow();
                    }
                    return false;
                }
            });
            holder.home_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(position, mDataset.get(position).getMcPublishRecord());
                }
            });
        }
        try {

            Glide.with(context)
                    .load(mDataset.get(position).getCoverPath())
                    .crossFade(200)
                    .centerCrop()
                    .into(holder.home_icon);
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
