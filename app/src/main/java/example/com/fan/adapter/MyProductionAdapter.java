package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.SuperUseDeleteListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.DateUtils.getMonthAndDay;
import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/9/26.
 */
public class MyProductionAdapter extends BaseAdapter {
    private static final String TAG = getTAG(BottomGridAdapter.class);
    private Context context;
    private List<ModelBean> blist;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private SuperUseDeleteListener delete;
    private ImageView img1, img2, img3, img4, img5, img6;
    private ImageView user_icon, share_img, video_img;
    private TextView name_tv, content_tv, date_tv,seecount_tv,give_tv,status_tv,money_tv,delete_tv;
    private HorizontalScrollView photo_scroll;
    private FrameLayout video_layout;
    private LinearLayout photo_layout;

    public MyProductionAdapter(Context context, List<ModelBean> blist, ItemClickListener hlistener, SuperUseDeleteListener delete) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.listener = hlistener;
        this.delete = delete;
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
            root = inflater.inflate(R.layout.my_production_item, null);

        user_icon = OverallViewHolder.ViewHolder.get(root, R.id.user_icon);
        share_img = OverallViewHolder.ViewHolder.get(root, R.id.share_img);
        video_img = OverallViewHolder.ViewHolder.get(root, R.id.video_img);

        img1 = OverallViewHolder.ViewHolder.get(root, R.id.img1);
        img2 = OverallViewHolder.ViewHolder.get(root, R.id.img2);
        img3 = OverallViewHolder.ViewHolder.get(root, R.id.img3);
        img4 = OverallViewHolder.ViewHolder.get(root, R.id.img4);
        img5 = OverallViewHolder.ViewHolder.get(root, R.id.img5);
        img6 = OverallViewHolder.ViewHolder.get(root, R.id.img6);

        name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        content_tv = OverallViewHolder.ViewHolder.get(root, R.id.content_tv);
        date_tv = OverallViewHolder.ViewHolder.get(root, R.id.date_tv);
        photo_scroll = OverallViewHolder.ViewHolder.get(root, R.id.photo_scroll);

         seecount_tv = OverallViewHolder.ViewHolder.get(root, R.id.seecount_tv);

         give_tv = OverallViewHolder.ViewHolder.get(root, R.id.give_tv);
         status_tv = OverallViewHolder.ViewHolder.get(root, R.id.status_tv);
         money_tv = OverallViewHolder.ViewHolder.get(root, R.id.money_tv);
         delete_tv = OverallViewHolder.ViewHolder.get(root, R.id.delete_tv);

         video_layout = OverallViewHolder.ViewHolder.get(root, R.id.video_layout);
         photo_layout = OverallViewHolder.ViewHolder.get(root, R.id.photo_layout);

        name_tv.setText(blist.get(position).getModelRealName());
        date_tv.setText(getMonthAndDay(blist.get(position).getCreateTime()));
        content_tv.setText(blist.get(position).getInfo());

        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(12322, blist.get(position).getId());
            }
        });
        switch (blist.get(position).getStatus()) {
            case 0:
                status_tv.setText(getRouString(R.string.unaudit));
                delete_tv.setVisibility(View.VISIBLE);
                break;
            case 1:
                status_tv.setText(getRouString(R.string.issue));
                delete_tv.setVisibility(View.GONE);
                break;
            case -1:
                status_tv.setText(getRouString(R.string.refuse));
                delete_tv.setVisibility(View.VISIBLE);
                break;
            case -3:
                status_tv.setText(getRouString(R.string.unissue));
                delete_tv.setVisibility(View.VISIBLE);
                break;
        }
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.onDelete(blist.get(position).getTypeFlag(), blist.get(position).getId());
            }
        });
        seecount_tv.setText(blist.get(position).getSeeCount() + "次浏览");
        give_tv.setText(blist.get(position).getSumCount() + "人打赏");
        money_tv.setText("￥" + blist.get(position).getSumPrice());


        if (cleanNull(blist.get(position).getInfo()))
            content_tv.setVisibility(View.GONE);
        else
            content_tv.setVisibility(View.VISIBLE);

        switch (blist.get(position).getTypeFlag()) {
            //私密照;
            case -2:
                video_layout.setVisibility(View.GONE);
                if (blist.get(position).getImgs() != null) {
                    try {
                        if (blist.get(position).getImgs().size() > 0)
                            photo_scroll.setVisibility(View.VISIBLE);

                        AddPhoto(photo_layout, position);
                    } catch (Exception e) {
                        Log.e(TAG, " Error ===" + e);
                    }
                }

                break;
            //私密视频;
            case -3:
                video_layout.setVisibility(View.VISIBLE);
                photo_scroll.setVisibility(View.GONE);
                video_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(-3, blist.get(position).getId());
                    }
                });
                Glide.with(context).load(blist.get(position).getCoverPath()).apply(getRequestOptions(false, 0, 0, false)).into(video_img);

                break;
        }
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(1002, blist.get(position).getModelId());
            }
        });
        try {
            Glide.with(context).load(blist.get(position).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0, true)).into(user_icon);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        return root;
    }

    private void AddPhoto(LinearLayout photo_layout, final int position) {
        hideView();
        if (blist.get(position).getImgs().size() > 0) {
            photo_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(0, blist.get(position).getId());
                }
            });
            for (int i = 0; i < blist.get(position).getImgs().size(); i++) {
                if (i <= 6) {
                    switch (i) {
                        case 0:
                            img1.setVisibility(View.VISIBLE);
                            Glide.with(context).load(blist.get(position).getImgs().get(i).getPath()).into(img1);
                            break;
                        case 1:
                            img2.setVisibility(View.VISIBLE);
                            Glide.with(context).load(blist.get(position).getImgs().get(i).getPath()).into(img2);
                            break;
                        case 2:
                            img3.setVisibility(View.VISIBLE);
                            Glide.with(context).load(blist.get(position).getImgs().get(i).getPath()).into(img3);
                            break;
                        case 3:
                            img4.setVisibility(View.VISIBLE);
                            Glide.with(context).load(blist.get(position).getImgs().get(i).getPath()).into(img4);
                            break;
                        case 4:
                            img5.setVisibility(View.VISIBLE);
                            Glide.with(context).load(blist.get(position).getImgs().get(i).getPath()).into(img5);
                            break;
                        case 5:
                            img6.setVisibility(View.VISIBLE);
                            Glide.with(context).load(R.mipmap.more_img5).into(img6);
                            break;
                    }
                } else
                    break;
            }
        }
    }

    private void hideView() {
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        img6.setVisibility(View.GONE);
    }
}
