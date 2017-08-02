package example.com.fan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.CrowDetailsBean;
import example.com.fan.bean.mcCrowdFundingTargets;
import example.com.fan.bean.targetBean;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.homepageListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.OverallViewHolder;
import example.com.fan.utils.TextViewColorUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.DateUtils.daysBetween;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class CrowdAdapter extends BaseAdapter {
    private static final String TAG = getTAG(CrowdAdapter.class);
    private Context context;
    private List<CrowDetailsBean> list;
    private List<targetBean> tlist;
    private LayoutInflater inflater;
    private homepageListener listener;
    private ItemClickListener itemlistener;

    public CrowdAdapter(List<CrowDetailsBean> list, List<targetBean> tlist, Context context, homepageListener listener, ItemClickListener itemlistener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.tlist = tlist;
        this.listener = listener;
        this.itemlistener = itemlistener;
        inflater = LayoutInflater.from(context);
    }


    //返回要显示的数量
    @Override
    public int getCount() {
        return list.get(0).getMcCrowdFundingTargets().size();
    }

    //返回当前Item显示的数据
    @Override
    public Object getItem(int position) {
        return list.get(0).getMcCrowdFundingTargets().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.crowd_item, null);

        ImageView top_icon = OverallViewHolder.ViewHolder.get(root,R.id.top_icon);
        ImageView content_img = OverallViewHolder.ViewHolder.get(root,R.id.content_img);
        TextView  top_name = OverallViewHolder.ViewHolder.get(root,R.id.top_name);
        TextView goal_tv = OverallViewHolder.ViewHolder.get(root,R.id.goal_tv);
        TextView details_time = OverallViewHolder.ViewHolder.get(root,R.id.details_time);
        TextView got_money = OverallViewHolder.ViewHolder.get(root,R.id.got_money);
        TextView reach_tv = OverallViewHolder.ViewHolder.get(root,R.id.reach_tv);
        TextView support_num = OverallViewHolder.ViewHolder.get(root,R.id.support_num);
        TextView supp_tv = OverallViewHolder.ViewHolder.get(root,R.id.supp_tv);
        ProgressBar progressbar = OverallViewHolder.ViewHolder.get(root,R.id.progressbar);

        ImageView img1 = OverallViewHolder.ViewHolder.get(root,R.id.img1);
        ImageView img2 = OverallViewHolder.ViewHolder.get(root,R.id.img2);
        ImageView img3 = OverallViewHolder.ViewHolder.get(root,R.id.img3);
        ImageView img4 = OverallViewHolder.ViewHolder.get(root,R.id.img4);
        ImageView img5 = OverallViewHolder.ViewHolder.get(root,R.id.img5);
        ImageView img6 = OverallViewHolder.ViewHolder.get(root,R.id.img6);
        ImageView img7 = OverallViewHolder.ViewHolder.get(root,R.id.img7);
        ImageView  img8 = OverallViewHolder.ViewHolder.get(root,R.id.img8);

//        LinearLayout support_ll = OverallViewHolder.ViewHolder.get(root,R.id.support_ll);

        final mcCrowdFundingTargets data = list.get(0).getMcCrowdFundingTargets().get(position);
        try {
            Glide.with(context).load(data.getHeadImgUrl()).bitmapTransform(new CropCircleTransformation(context)).crossFade(300).into(top_icon);
            Glide.with(context).load(data.getCoverPath()).crossFade(300).into(content_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        progressbar.setProgress(data.getProgress());
        top_name.setText(data.getName());
        goal_tv.setText("目标:" + data.getTargetMoney()+"YB");
        try {

            TextViewColorUtils.setTextColor(details_time, daysBetween(list.get(0).getStartTime(), list.get(0).getEndTime()) + "天" + MzFinal.br, "剩余", "#000000");
            TextViewColorUtils.setTextColor(got_money, data.getSumMoney() + MzFinal.br, "已筹集", "#FF4D85");
            TextViewColorUtils.setTextColor(reach_tv, data.getProgress()+"%" + MzFinal.br, "达成", "#000000");
            if (data.getSumCount() == null)
                TextViewColorUtils.setTextColor(support_num, "0", "人支持", "#FF4D85");
            else
                TextViewColorUtils.setTextColor(support_num, data.getSumCount(), "人支持", "#FF4D85");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tlist.size(); i++) {
            switch (i) {
                case 0:
                    supportNumberinit(img1, position);
                    break;
                case 1:
                    supportNumberinit(img2, position);
                    break;
                case 2:
                    supportNumberinit(img3, position);
                    break;
                case 3:
                    supportNumberinit(img4, position);
                    break;
                case 4:
                    supportNumberinit(img5, position);
                    break;
                case 5:
                    supportNumberinit(img6, position);
                    break;
                case 6:
                    supportNumberinit(img7, position);
                    break;
                case 7:
                    supportNumberinit(img8, position);
                    break;

            }

        }

        supp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemlistener.onItemClickListener(0,data.getTargetId());
            }
        });
        top_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIconClick(data.getUserId());
            }
        });
        content_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemlistener.onItemClickListener(0,data.getTargetId());
            }
        });


        return root;
    }

    private void supportNumberinit(ImageView img, int pos) {
        img.setVisibility(View.VISIBLE);
        try {
            Glide.with(context).load(tlist.get(pos).getHeadImgUrl()).centerCrop().override(40, 50).bitmapTransform(new CropCircleTransformation(context)).crossFade(300).into(img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
    }
}
