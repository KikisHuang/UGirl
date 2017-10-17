package example.com.fan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;

/**
 * Created by Administrator on 2017/9/17.
 */

public class VideoCoverAdapter extends RecyclerView.Adapter<VideoCoverAdapter.ViewHolder> {
    private static final String TAG = "AddPrivatePhotoAdapter";
    private LayoutInflater inflater;
    private Context context;
    private ItemClickListener listener;
    private List<String> data;

    public VideoCoverAdapter(Context context, List<String> list, ItemClickListener listener) {
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.data = list;
        this.listener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView img;
        private FrameLayout fragment_laoyout;

        private ViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.img);
            fragment_laoyout = (FrameLayout) v.findViewById(R.id.fragment_laoyout);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_photo_cover_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        int w = (int) (DeviceUtils.getWindowWidth(context) * 5 / 10);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, w);

        fl.leftMargin = DeviceUtils.dip2px(context, 3);
        fl.topMargin = DeviceUtils.dip2px(context, 3);
        if (position % 2 != 0)
            fl.rightMargin = DeviceUtils.dip2px(context, 3);
        holder.fragment_laoyout.setLayoutParams(fl);

        Glide.with(context).load(data.get(position)).apply(getRequestOptions(true, 0, 0, false)).into(holder.img);
        Log.i(TAG, "图片路径 ====" + data.get(position));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(0,data.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
