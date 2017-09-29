package example.com.fan.activity;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.adapter.NewsOrderAdapter;

/**
 * Created by lian on 2017/9/29.
 */
public class NewsOrderActivity extends InitActivity {
    private ListView listView;
    private TextView old_income, now_income, all_income;
    private LinearLayout head;
    private NewsOrderAdapter adapter;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.news_order_layout);
        head = (LinearLayout) getLayoutInflater().inflate(R.layout.news_head_item, null);
        listView = f(R.id.listView);
    }

    @Override
    protected void initData() {
            getData();
    }

    private void getData() {

    }
}
