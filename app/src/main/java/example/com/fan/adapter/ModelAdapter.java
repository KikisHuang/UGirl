package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.DateUtils.getMonthAndDay;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/9/26.
 */
public class ModelAdapter extends BaseAdapter {
    private static final String TAG = getTAG(BottomGridAdapter.class);
    private Context context;
    private List<ModelBean> blist;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private LinearLayout item_topic_image_layout, top, mid, bottom;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;


    public ModelAdapter(Context context, List<ModelBean> blist, ItemClickListener hlistener) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.listener = hlistener;
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
            root = inflater.inflate(R.layout.model_online_item, null);

        ImageView user_icon = OverallViewHolder.ViewHolder.get(root, R.id.user_icon);
        ImageView share_img = OverallViewHolder.ViewHolder.get(root, R.id.share_img);
        ImageView video_img = OverallViewHolder.ViewHolder.get(root, R.id.video_img);

        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(root, R.id.content_tv);
        TextView date_tv = OverallViewHolder.ViewHolder.get(root, R.id.date_tv);
        TextView type_tv = OverallViewHolder.ViewHolder.get(root, R.id.type_tv);
        TextView popularity_tv = OverallViewHolder.ViewHolder.get(root, R.id.popularity_tv);

        FrameLayout video_layout = OverallViewHolder.ViewHolder.get(root, R.id.video_layout);

        item_topic_image_layout = OverallViewHolder.ViewHolder.get(root, R.id.item_topic_image_layout);

        imageView1 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima1);
        imageView2 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima2);
        imageView3 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima3);
        imageView4 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima4);
        imageView5 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima5);

        imageView6 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima6);
        imageView7 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima7);
        imageView8 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima8);
        imageView9 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima9);

        top = OverallViewHolder.ViewHolder.get(root, R.id.top_img_layout);
        mid = OverallViewHolder.ViewHolder.get(root, R.id.bottom_img_layout);
        bottom = OverallViewHolder.ViewHolder.get(root, R.id.add_img_layout);

        type_tv.setText(blist.get(position).getTypeName());
        name_tv.setText(blist.get(position).getModelRealName());
        date_tv.setText(getMonthAndDay(blist.get(position).getCreateTime()));
        content_tv.setText(blist.get(position).getInfo());
        popularity_tv.setText(getRouString(R.string.moods_index) + blist.get(position).getSeeCount());

        if (cleanNull(blist.get(position).getInfo()))
            content_tv.setVisibility(View.GONE);
        else
            content_tv.setVisibility(View.VISIBLE);

        switch (blist.get(position).getTypeFlag()) {
            //私密照;
            case -2:
                video_layout.setVisibility(View.GONE);
                popularity_tv.setVisibility(View.VISIBLE);
                SpecialInit(position);
                break;
            //私密视频;
            case -3:
                video_layout.setVisibility(View.VISIBLE);
                popularity_tv.setVisibility(View.GONE);
                video_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(-3, blist.get(position).getModelId());
                    }
                });
                Glide.with(context).load(blist.get(position).getCoverPath()).bitmapTransform(new CropCircleTransformation(context)).into(video_img);

                break;
        }
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(1002, blist.get(position).getModelId());
            }
        });
        try {
            Glide.with(context).load(blist.get(position).getHeadImgUrl()).bitmapTransform(new CropCircleTransformation(context)).into(user_icon);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        Log.i(TAG, "GlideCache==== " + GlideCacheUtil.getCacheSize(context));
        return root;
    }

    private void SpecialInit(final int position) {
        if (blist.size() > 0) {
            item_topic_image_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(-2, blist.get(position).getId());
                }
            });
        }
        hideImg();
        LinearLayout.LayoutParams params = getLayoutParams(blist.get(position).getImgs().size());

        for (int i = 0; i < blist.get(position).getImgs().size(); i++) {
            Log.i(TAG, "position ===" + position + "size ===" + blist.get(position).getImgs().size());
            switch (i) {
                case 0:
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView1);
                    break;
                case 1:
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView2);
                    break;
                case 2:

                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView3);

                    break;
                case 3:

                    imageView4.setVisibility(View.VISIBLE);
                    imageView4.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView4);
                    break;
                case 4:

                    imageView5.setVisibility(View.VISIBLE);
                    imageView5.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView5);

                    break;
                case 5:
                    imageView6.setVisibility(View.VISIBLE);
                    imageView6.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView6);
                    break;
                case 6:
                    imageView7.setVisibility(View.VISIBLE);
                    imageView7.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView7);
                    break;
                case 7:
                    imageView8.setVisibility(View.VISIBLE);
                    imageView8.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView8);
                    break;
                case 8:
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setLayoutParams(params);
                    setBitmap(blist.get(position).getImgs().get(i).getPath(), imageView9);
                    break;
            }
        }
    }

    private LinearLayout.LayoutParams getLayoutParams(int size) {
        int w = (int) (DeviceUtils.getWindowWidth(context) * 2.3 / 10);
        int w1 = DeviceUtils.getWindowWidth(context) * 4 / 10;
        int w3 = (int) (DeviceUtils.getWindowWidth(context) * 7.2 / 10);
        int w2 = DeviceUtils.getWindowWidth(context) * 3 / 10;

        if (size == 1) {
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(w3, w1);
            params1.topMargin = DeviceUtils.dip2px(context, 5);
            return setSpacing(params1);
        }
        if (size == 2) {
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(w2, w2);
            params2.topMargin = DeviceUtils.dip2px(context, 5);

            return setSpacing(params2);
        }
        if (size >= 3) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, w);
            params.topMargin = DeviceUtils.dip2px(context, 3);

            return setSpacing(params);
        }

        return null;
    }

    private void setBitmap(String str, ImageView img) {
        try {
            Glide.with(context)
                    .load(str)
                    .centerCrop()
                    .override(150, 150)
                    .into(img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
    }

    private void hideImg() {
        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
        imageView5.setVisibility(View.GONE);
        imageView6.setVisibility(View.GONE);
        imageView7.setVisibility(View.GONE);
        imageView8.setVisibility(View.GONE);
        imageView9.setVisibility(View.GONE);
    }

    public LinearLayout.LayoutParams setSpacing(LinearLayout.LayoutParams params) {
        params.rightMargin = DeviceUtils.dip2px(context, 3);
        return params;
    }
}
