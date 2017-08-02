package example.com.fan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import example.com.fan.bean.MyOrderBean;
import example.com.fan.mylistener.MyOrderDetailsListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 */
public class MyOrderAdapter extends BaseAdapter {
    private static final String TAG = getTAG(MyOrderAdapter.class);
    private Context context;
    private List<MyOrderBean> blist;
    private LayoutInflater inflater;
    private int tag;
    private MyOrderDetailsListener listener;

    public MyOrderAdapter(Context context, List<MyOrderBean> blist, MyOrderDetailsListener listener, int tag) {
        this.blist = blist;
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.tag = tag;
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
            root = inflater.inflate(R.layout.my_order_item, null);

        ImageView order_img = OverallViewHolder.ViewHolder.get(root,R.id.order_img);
//        ImageView cover_img = (ImageView) root.findViewById(R.id.cover_img);

        CardView home_layout2 = OverallViewHolder.ViewHolder.get(root,R.id.home_layout2);

        TextView order_title = OverallViewHolder.ViewHolder.get(root,R.id.order_title);

        TextView create_time = OverallViewHolder.ViewHolder.get(root,R.id.create_time);

        TextView order_state = OverallViewHolder.ViewHolder.get(root,R.id.order_state);
        TextView goods_name = OverallViewHolder.ViewHolder.get(root,R.id.goods_name);
        TextView end_time = OverallViewHolder.ViewHolder.get(root,R.id.end_time);
        TextView order_number = OverallViewHolder.ViewHolder.get(root,R.id.order_number);
        TextView order_price = OverallViewHolder.ViewHolder.get(root,R.id.order_price);
//        TextView delete_tv = (TextView) root.findViewById(R.id.delete_tv);

//        RelativeLayout bottom_rl = (RelativeLayout) root.findViewById(R.id.bottom_rl);


        create_time.setText(blist.get(position).getCreateTime());
        order_title.setText(blist.get(position).getTitle());
        goods_name.setText(blist.get(position).getSubInfo());
//        0未支付 1支付成功 -1支付失败/支付取消 -2校验失败 -3退款
        String state = "";
        switch (blist.get(position).getStatus()) {
            case 0:
                state = "未支付";
                order_state.setTextColor(getRouColors(R.color.orange1));
                break;
            case 1:
                state = "支付成功";
                order_state.setTextColor(getRouColors(R.color.orange1));
                break;
            case -1:
                state = "支付失败";
                order_state.setTextColor(getRouColors(R.color.gray3));
                break;
            case -2:
                state = "校验失败";
                order_state.setTextColor(getRouColors(R.color.gray3));
                break;
            case -3:
                state = "退款";
                order_state.setTextColor(getRouColors(R.color.gray3));
                break;
        }
        order_state.setText(state);
        order_price.setText("￥" + blist.get(position).getPrice());
        order_number.setText("x" + blist.get(position).getCount());

        if (blist.get(position).getType() == 1) {
            switch (blist.get(position).getTransportType()) {
                case 0:
                    end_time.setText("未发货");
                    break;
                case 1:
                    end_time.setText("已发货");
                    break;
                case -1:
                    end_time.setText("已退货");
                    break;

                default:
                    end_time.setText("");
                    break;
            }
        }

        try {
            Glide.with(context).load(blist.get(position).getImg()).override(100, 100).centerCrop().crossFade(200).into(order_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }

        home_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDetails(blist.get(position));
            }
        });

        return root;
    }
}
