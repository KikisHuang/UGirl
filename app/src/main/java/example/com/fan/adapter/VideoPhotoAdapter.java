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
import example.com.fan.bean.VideoImgBean;
import example.com.fan.mylistener.VideoImgSettingListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;

/**
 * Created by Administrator on 2017/9/17.
 */

public class VideoPhotoAdapter extends RecyclerView.Adapter<VideoPhotoAdapter.ViewHolder> {
    private static final String TAG = "AddPrivatePhotoAdapter";
    private LayoutInflater inflater;
    private Context context;
    private VideoImgSettingListener listener;
    private List<VideoImgBean> data;
    int num;

    public VideoPhotoAdapter(Context context, List<VideoImgBean> list, VideoImgSettingListener listener) {
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.data = list;
        num = 0;
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView img, select_img;
        private FrameLayout fragment_laoyout;

        private ViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.img);
            select_img = (ImageView) v.findViewById(R.id.select_img);
            fragment_laoyout = (FrameLayout) v.findViewById(R.id.fragment_laoyout);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_photo_item, parent, false);
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
        if (data.get(position).getSelectFlag())
            Glide.with(context).load(R.mipmap.on_icon).into(holder.select_img);
        else
            Glide.with(context).load(R.mipmap.off_icon).into(holder.select_img);

        Glide.with(context).load(data.get(position).getFilePath()).apply(getRequestOptions(true, 0, 0, false)).into(holder.img);
        Log.i(TAG, "图片路径 ====" + data.get(position).getFilePath());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getSelectFlag())
                        num++;
                }
                if (num >= 3) {
                    ToastUtil.toast2_bottom(context, "最多只能选择3张图片哦!!");
                } else {
                    if (data.get(position).getSelectFlag() == false) {
                        Glide.with(context).load(R.mipmap.on_icon).into(holder.select_img);
                        listener.onSelect(position, data.get(position).getFilePath());
                    } else {
                        Glide.with(context).load(R.mipmap.off_icon).into(holder.select_img);
                        listener.onCancle(position, data.get(position).getFilePath());
                    }
                }

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
