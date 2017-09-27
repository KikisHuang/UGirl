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
import example.com.fan.bean.StoreBean;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.OverallViewHolder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/9/26.
 */
public class ModelAdapter extends BaseAdapter {
    private static final String TAG = getTAG(BottomGridAdapter.class);
    private Context context;
    private List<StoreBean> blist;
    private LayoutInflater inflater;
    private int tag;

    public ModelAdapter(Context context, List<StoreBean> blist, int tag) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.tag = tag;
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

        ImageView cover_img = OverallViewHolder.ViewHolder.get(root, R.id.cover_img);
        ImageView user_icon = OverallViewHolder.ViewHolder.get(root, R.id.user_icon);
        ImageView add_wechat_img = OverallViewHolder.ViewHolder.get(root, R.id.add_wechat_img);
        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(root, R.id.content_tv);
        TextView title_tag = OverallViewHolder.ViewHolder.get(root, R.id.title_tag);


        if (position == 0)
            title_tag.setVisibility(View.VISIBLE);
        else
            title_tag.setVisibility(View.GONE);

        int h = DeviceUtils.getWindowHeight(context) * 6 / 10;
        name_tv.setText("123123");
        content_tv.setText("456456");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        cover_img.setLayoutParams(lp);
        cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        try {
            Glide.with(context).load(blist.get(position).getMcOfficialSellShoppingMall().getCoverPath()).bitmapTransform(new CropCircleTransformation(context)).into(user_icon);
            Glide.with(context).load(blist.get(position).getMcOfficialSellShoppingMall().getCoverPath()).into(cover_img);
            Log.i(TAG, "blist.get(position).getMcCrowdFundingShoppingMall().getCoverPath()).into(cover_img" + blist.get(position).getMcCrowdFundingShoppingMall().getCoverPath());
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        Log.i(TAG, "GlideCache==== " + GlideCacheUtil.getCacheSize(context));
        return root;
    }

}
