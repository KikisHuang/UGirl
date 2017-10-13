package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.OverPayWxBean;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TextViewColorUtils.setTextColor3;

/**
 * Created by lian on 2017/5/18.
 */
public class OverPayWxAdapter extends BaseAdapter {
    private static final String TAG = getTAG(OverPayWxAdapter.class);
    private Context context;
    private List<OverPayWxBean> blist;
    private LayoutInflater inflater;

    public OverPayWxAdapter(Context context, List<OverPayWxBean> blist) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
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
            root = inflater.inflate(R.layout.over_pay_wx_item, null);

        ImageView headimg = OverallViewHolder.ViewHolder.get(root, R.id.headimg_img);
        TextView name_tv = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        TextView pay_Time = OverallViewHolder.ViewHolder.get(root, R.id.pay_Time);
        TextView wx = OverallViewHolder.ViewHolder.get(root, R.id.wx_tv);


        name_tv.setText(blist.get(position).getRealName());
        pay_Time.setText(blist.get(position).getPayTime());
        setTextColor3(wx,blist.get(position).getWx(),"Ta的微信号：","#ec83c8");
        try {
            Glide.with(context).load(blist.get(position).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0,true)).into(headimg);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        Log.i(TAG, "GlideCache==== " + GlideCacheUtil.getCacheSize(context));
        return root;
    }
}
