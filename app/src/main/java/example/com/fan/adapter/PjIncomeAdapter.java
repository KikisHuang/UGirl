package example.com.fan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.CrowdPayDetailBean;
import example.com.fan.mylistener.PayListener;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class PjIncomeAdapter extends BaseAdapter {
    private static final String TAG = getTAG(PjIncomeAdapter.class);
    private Context context;
    private List<CrowdPayDetailBean> list;
    private LayoutInflater inflater;
    private  PayListener tlistener;


    public PjIncomeAdapter(List<CrowdPayDetailBean> list, Context context, PayListener tlistener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.tlistener = tlistener;
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

            root = inflater.inflate(R.layout.pjincome_item, null);
        TextView draw_rule_tv = OverallViewHolder.ViewHolder.get(root,R.id.draw_rule_tv);
        TextView supp_tv = OverallViewHolder.ViewHolder.get(root,R.id.supp_tv);
        TextView rule_tv = OverallViewHolder.ViewHolder.get(root,R.id.rule_tv);
        final TextView title_tv = OverallViewHolder.ViewHolder.get(root,R.id.title_tv);
        TextView moeny_tv = OverallViewHolder.ViewHolder.get(root,R.id.moeny_tv);


        moeny_tv.setText("￥"+list.get(position).getPrice()+" 抽奖");
        title_tv.setText(list.get(position).getTitle());
        rule_tv.setText(list.get(position).getInfo());
        if (position == 0)
            draw_rule_tv.setVisibility(View.VISIBLE);
        else
            draw_rule_tv.setVisibility(View.GONE);

        supp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tlistener.onPay(String.valueOf(1),title_tv.getText().toString(),String.valueOf(list.get(position).getPrice()),"1","",list.get(position).getInfo(),list.get(position).getId());
            }
        });

        return root;
    }
}
