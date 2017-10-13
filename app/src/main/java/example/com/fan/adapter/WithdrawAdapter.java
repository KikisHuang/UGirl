package example.com.fan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.AccountDetailsBean;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class WithdrawAdapter extends BaseAdapter {
    private static final String TAG = getTAG(WithdrawAdapter.class);
    private Context context;
    private List<AccountDetailsBean> list;
    private LayoutInflater inflater;

    public WithdrawAdapter(List<AccountDetailsBean> tlist, Context context) {
        this.context = context.getApplicationContext();
        this.list = tlist;
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
            root = inflater.inflate(R.layout.withdraw_item, null);

        TextView income_tv = OverallViewHolder.ViewHolder.get(root, R.id.income_tv);
        TextView withdraw_date = OverallViewHolder.ViewHolder.get(root, R.id.withdraw_date);
        TextView withdraw_name = OverallViewHolder.ViewHolder.get(root, R.id.withdraw_name);

        income_tv.setText("+" + list.get(position).getMoney());
        withdraw_date.setText(list.get(position).getUpdateTime());
        withdraw_name.setText(list.get(position).getCashAccount());

        return root;
    }
}
