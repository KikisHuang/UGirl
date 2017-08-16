package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.StoreBean;
import example.com.fan.mylistener.CrowdItemClickListener;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.DateUtils.daysBetween;
import static example.com.fan.utils.DateUtils.getDate;
import static example.com.fan.utils.SynUtils.getRouDrawable;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class StoreAdapter extends BaseAdapter {
    private static final String TAG = getTAG(StoreAdapter.class);
    private Context context;
    private List<StoreBean> list;
    private LayoutInflater inflater;
    private int tag;
    private CrowdItemClickListener listener;
    private ItemClickListener hlistener;

    public StoreAdapter(List<StoreBean> list, Context context, CrowdItemClickListener listener, ItemClickListener hlistener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
        this.hlistener = hlistener;
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
        ViewHolder holder = null;
        View root = convertView;
        if (root == null && tag == 0) {
            holder = new ViewHolder();
            root = inflater.inflate(R.layout.store_item, null);

            holder.goods_img = (ImageView) root.findViewById(R.id.goods_img);
            holder.goods_content = (TextView) root.findViewById(R.id.goods_content);
            holder.goods_price = (TextView) root.findViewById(R.id.goods_price);
            holder.goods_buy_bt = (TextView) root.findViewById(R.id.goods_buy_bt);
            holder.goods_title_tv = (TextView) root.findViewById(R.id.goods_title_tv);
            holder.got_money = (TextView) root.findViewById(R.id.got_money);
            holder.details_time = (TextView) root.findViewById(R.id.details_time);
            holder.reach_tv = (TextView) root.findViewById(R.id.reach_tv);
            holder.support_num = (TextView) root.findViewById(R.id.support_num);
            holder.goal_tv = (TextView) root.findViewById(R.id.goal_tv);
            holder.top_ll = (LinearLayout) root.findViewById(R.id.top_ll);
            holder.progress_ll = (LinearLayout) root.findViewById(R.id.progress_ll);
            holder.support_ll = (LinearLayout) root.findViewById(R.id.support_ll);
            holder.progressbar = (ProgressBar) root.findViewById(R.id.progressbar);
            holder.tag_tv = (TextView) root.findViewById(R.id.tag_tv);

            root.setTag(holder);
        }
        if (root == null && tag == 1) {
            holder = new ViewHolder();
            root = inflater.inflate(R.layout.actions_item, null);

            holder.goods_img = (ImageView) root.findViewById(R.id.goods_img);
            holder.actions_title = (TextView) root.findViewById(R.id.actions_title);
            holder.actions_time = (TextView) root.findViewById(R.id.actions_time);
            holder.icon_ll = (LinearLayout) root.findViewById(R.id.icon_ll);
            holder.tag_tv = (TextView) root.findViewById(R.id.tag_tv);


            root.setTag(holder);
        }
        holder = (ViewHolder) root.getTag();
        if (list.size() > 0) {
            switch (tag) {
                case 0:
                    holder.support_ll.setVisibility(View.GONE);

                    if (list.get(position).getType() == 2) {
                        //众筹;
                        holder.progress_ll.setVisibility(View.VISIBLE);
                        holder.top_ll.setVisibility(View.GONE);
                        holder.goods_content.setVisibility(View.GONE);
                        holder.tag_tv.setVisibility(View.VISIBLE);
                        holder.support_num.setVisibility(View.GONE);
                        try {
                            String date = "";
                            if (daysBetween(list.get(position).getMcCrowdFundingShoppingMall().getStartTime(), list.get(position).getMcCrowdFundingShoppingMall().getEndTime()) > 0) {
                                date = daysBetween(list.get(position).getMcCrowdFundingShoppingMall().getStartTime(), list.get(position).getMcCrowdFundingShoppingMall().getEndTime()) + "天" + "\n剩余";
                                holder.tag_tv.setBackground(getRouDrawable(R.mipmap.underway_tag_img));
                                holder.tag_tv.setText(getRouString(R.string.underway));
                                holder.goods_img.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        hlistener.onItemClickListener(0, list.get(position).getMcCrowdFundingShoppingMall().getId());

                                    }
                                });
                            } else {
                                holder.tag_tv.setBackground(getRouDrawable(R.mipmap.end_tag_img));
                                holder.tag_tv.setText(getRouString(R.string.ending));
                                date = "0" + "天" + "\n剩余";
                                holder.goods_img.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ToastUtil.toast2_bottom(context, "该项目已结束众筹,请选择其他项目!");
                                    }
                                });
                            }

                            holder.details_time.setText(date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        holder.got_money.setText(list.get(position).getMcCrowdFundingShoppingMall().getMcCrowdFundingTargets().get(0).getSumMoney() + "\n已筹集");
                        holder.reach_tv.setText(list.get(position).getMcCrowdFundingShoppingMall().getMcCrowdFundingTargets().get(0).getProgress() + "%" + "\n" +
                                "达成");
                        holder.progressbar.setProgress(list.get(position).getMcCrowdFundingShoppingMall().getMcCrowdFundingTargets().get(0).getProgress());
                        holder.goal_tv.setText("目标:" + list.get(position).getMcCrowdFundingShoppingMall().getMcCrowdFundingTargets().get(0).getTargetMoney() + "元");
//                    setSupp(holder, position);
                        try {
                            Glide.with(context).load(list.get(position).getMcCrowdFundingShoppingMall().getCoverPath()).into(holder.goods_img);
                        } catch (Exception e) {
                            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                        }
                        holder.goods_title_tv.setText(list.get(position).getMcCrowdFundingShoppingMall().getTitle());
                    }
                    if (list.get(position).getType() == 1) {

                        //单件商品;
                        holder.progress_ll.setVisibility(View.GONE);
                        holder.tag_tv.setVisibility(View.GONE);
                        holder.top_ll.setVisibility(View.VISIBLE);
                        holder.goods_content.setVisibility(View.VISIBLE);
                        holder.goods_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hlistener.onItemClickListener(0, list.get(position).getMcOfficialSellShoppingMall().getId());
                            }
                        });

                        try {
                            Glide.with(context).load(list.get(position).getMcOfficialSellShoppingMall().getCoverPath()).into(holder.goods_img);
                        } catch (Exception e) {
                            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                        }
                        holder.goods_title_tv.setText(list.get(position).getMcOfficialSellShoppingMall().getTitle());
                        holder.goods_content.setText(list.get(position).getMcOfficialSellShoppingMall().getSubInfo());
                        holder.goods_price.setText("￥" + list.get(position).getMcOfficialSellShoppingMall().getPrice());
                    }
                    holder.goods_buy_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hlistener.onItemClickListener(0, list.get(position).getMcOfficialSellShoppingMall().getId());
                        }
                    });

                    break;
                case 1:
                    holder.icon_ll.removeAllViews();
                    holder.tag_tv.setVisibility(View.VISIBLE);
                    for (int i = 0; i < list.get(position).getView().size(); i++) {
                        holder.icon_ll.addView(list.get(position).getView().get(i));
                    }
                    try {
                        if (daysBetween(list.get(position).getMcCrowdFundingShoppingMall().getStartTime(), list.get(position).getMcCrowdFundingShoppingMall().getEndTime()) > 0) {
                            holder.tag_tv.setBackground(getRouDrawable(R.mipmap.underway_tag_img));
                            holder.tag_tv.setText("进行中");
                            holder.goods_img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    listener.onCrowdofShopItemClickListener(list.get(position).getMcCrowdFundingShoppingMall().getTitle(), list.get(position).getMcCrowdFundingShoppingMall().getId());

                                }
                            });
                        } else {
                            holder.tag_tv.setBackground(getRouDrawable(R.mipmap.end_tag_img));
                            holder.tag_tv.setText("已结束");
                            holder.goods_img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.toast2_bottom(context, "该项目已结束众筹,请选择其他项目!");
                                }
                            });
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        Glide.with(context).load(list.get(position).getMcCrowdFundingShoppingMall().getCoverPath()).into(holder.goods_img);
                    } catch (Exception e) {
                        Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                    }
                    holder.actions_title.setText(list.get(position).getMcCrowdFundingShoppingMall().getTitle());
                    String start = getDate(list.get(position).getMcCrowdFundingShoppingMall().getStartTime());
                    String e = getDate(list.get(position).getMcCrowdFundingShoppingMall().getEndTime());
                    String end = list.get(position).getMcCrowdFundingShoppingMall().getEndTime().substring(5, e.length());

                    holder.actions_time.setText(start + " " + end);

                    break;
            }
        }
        return root;
    }

    /**
     * 通过tag来切换item布局;
     *
     * @param tag
     */
    public void setTag(int tag) {
        this.tag = tag;
    }

    static class ViewHolder {
        public ImageView goods_img;
        public TextView goods_content, goods_price, actions_time, actions_title, goods_buy_bt, goods_title_tv, got_money, details_time, reach_tv, support_num, goal_tv, tag_tv;
        private LinearLayout top_ll, progress_ll, support_ll, icon_ll;
        private ProgressBar progressbar;
    }

}
