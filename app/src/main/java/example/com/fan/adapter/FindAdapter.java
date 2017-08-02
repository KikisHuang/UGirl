package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.ShareRequestListener;
import example.com.fan.mylistener.TwoParamaListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.DateUtils.getMonthAndDay;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class FindAdapter extends BaseAdapter {
    private static final String TAG = getTAG(FindAdapter.class);
    private Context context;
    private List<ModelBean> list;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private TwoParamaListener tlistener;
    private ShareRequestListener slistener;
    private LinearLayout item_topic_image_layout, top, bottom, mid;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    private ImageView icon_logo, share_img, video_cover;
    private TextView name_tv, type_tv, date_tv, content_tv, attention_tv;
    private boolean homeflag;

    public FindAdapter(Context context, List<ModelBean> list, ItemClickListener listener, TwoParamaListener tlistener, ShareRequestListener slistener,boolean homeflag) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
        this.tlistener = tlistener;
        this.slistener = slistener;
        this.homeflag = homeflag;
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
            root = inflater.inflate(R.layout.find_item, null);

        icon_logo = OverallViewHolder.ViewHolder.get(root, R.id.icon_logo);
        share_img = OverallViewHolder.ViewHolder.get(root, R.id.share_img);
        video_cover = OverallViewHolder.ViewHolder.get(root, R.id.video_cover);
        name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        type_tv = OverallViewHolder.ViewHolder.get(root, R.id.type_tv);
        date_tv = OverallViewHolder.ViewHolder.get(root, R.id.date_tv);
        content_tv = OverallViewHolder.ViewHolder.get(root, R.id.content_tv);
        attention_tv = OverallViewHolder.ViewHolder.get(root, R.id.attention_tv);
        top = OverallViewHolder.ViewHolder.get(root, R.id.top_img_layout);
        mid = OverallViewHolder.ViewHolder.get(root, R.id.bottom_img_layout);
        bottom = OverallViewHolder.ViewHolder.get(root, R.id.add_img_layout);

        imageView1 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima1);
        imageView2 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima2);
        imageView3 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima3);
        imageView4 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima4);
        imageView5 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima5);

        imageView6 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima6);
        imageView7 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima7);
        imageView8 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima8);
        imageView9 = OverallViewHolder.ViewHolder.get(root, R.id.item_ima9);
        item_topic_image_layout = (LinearLayout) root.findViewById(R.id.item_topic_image_layout);


        try {
            Glide.with(context).load(list.get(position).getJoinUser().getHeadImgUrl()).bitmapTransform(new CropCircleTransformation(context)).override(50, 50).crossFade(300).into(icon_logo);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        name_tv.setText(list.get(position).getName());
        date_tv.setText(getMonthAndDay(list.get(position).getCreateTime()));
        content_tv.setText(list.get(position).getInfo());

        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slistener.onShare(list.get(position).getJoinUser().getId(), list.get(position).getName(), list.get(position).getInfo(), list.get(position).getMcSettingPublishType().getId());
            }
        });
        if(!homeflag){
            icon_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(1,list.get(position).getJoinUser().getId());
                }
            });
        }
        switch (list.get(position).getMcSettingPublishType().getTypeFlag()) {
            //专辑
            case 0:
//                hideImage();
                type_tv.setText(getRouString(R.string.special));
                type_tv.setTextColor(getRouColors(R.color.cherry1));
                video_cover.setVisibility(View.GONE);
                item_topic_image_layout.setVisibility(View.VISIBLE);
                if (list.get(position).getMcPublishImgUrls().size() > 6) {
                    top.setVisibility(View.VISIBLE);
                    mid.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.VISIBLE);
                } else if (list.get(position).getMcPublishImgUrls().size() > 3) {
                    top.setVisibility(View.VISIBLE);
                    mid.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.GONE);
                } else {
                    top.setVisibility(View.VISIBLE);
                    mid.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                }
                SpecialInit(position);
                attention_tv.setText(String.valueOf("已被" + list.get(position).getCollectionCount() + "人订阅"));
                break;
            //视频
            case 4:
                type_tv.setText(getRouString(R.string.Video));
                type_tv.setTextColor(getRouColors(R.color.cherry1));
                video_cover.setVisibility(View.VISIBLE);
                item_topic_image_layout.setVisibility(View.GONE);
                try {
                    Glide.with(context).load(list.get(position).getCoverPath()).into(video_cover);
                } catch (Exception e) {
                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                }
                attention_tv.setText(String.valueOf("已被播放" + list.get(position).getSellCount() + "次"));
                video_cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tlistener.onGoPlayPage(list.get(position).getId(), 4);

                    }
                });
                break;
            //VR
            case 5:
                type_tv.setText(getRouString(R.string.Vr) + "视频");
                type_tv.setTextColor(getRouColors(R.color.orange6));
                video_cover.setVisibility(View.VISIBLE);
                item_topic_image_layout.setVisibility(View.GONE);
                try {
                    Glide.with(context).load(list.get(position).getCoverPath()).into(video_cover);
                } catch (Exception e) {
                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                }
                attention_tv.setText(String.valueOf("已被播放" + list.get(position).getSellCount() + "次"));
                video_cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        tlistener.onGoPlayPage(list.get(position).getId(), 5);
                    }
                });
                break;
        }

        return root;
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

    /**
     * 专辑图组初始化设置;
     * <p>
     * //     * @param position
     */
    private void SpecialInit(final int position) {
        if (list.size() > 0) {
            item_topic_image_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(0, list.get(position).getId());
                }
            });
        }
        hideImg();
        LinearLayout.LayoutParams params = getLayoutParams(list.get(position).getMcPublishImgUrls().size());

        for (int i = 0; i < list.get(position).getMcPublishImgUrls().size(); i++) {
            Log.i(TAG, "position ===" + position + "size ===" + list.get(position).getMcPublishImgUrls().size());
            switch (i) {
                case 0:
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView1);
                    break;
                case 1:
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView2);
                    break;
                case 2:

                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView3);

                    break;
                case 3:

                    imageView4.setVisibility(View.VISIBLE);
                    imageView4.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView4);
                    break;
                case 4:

                    imageView5.setVisibility(View.VISIBLE);
                    imageView5.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView5);

                    break;
                case 5:
                    imageView6.setVisibility(View.VISIBLE);
                    imageView6.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView6);
                    break;
                case 6:
                    imageView7.setVisibility(View.VISIBLE);
                    imageView7.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView7);
                    break;
                case 7:
                    imageView8.setVisibility(View.VISIBLE);
                    imageView8.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView8);
                    break;
                case 8:
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setLayoutParams(params);
                    setBitmap(list.get(position).getMcPublishImgUrls().get(i).getPath(), imageView9);
                    break;
            }
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
