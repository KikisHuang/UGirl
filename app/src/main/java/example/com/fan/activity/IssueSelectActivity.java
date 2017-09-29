package example.com.fan.activity;

import android.widget.ListView;

import example.com.fan.R;

/**
 * Created by lian on 2017/9/27.
 * 问题筛选页面;
 */
public class IssueSelectActivity extends InitActivity {

    private ListView listView;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.issue_select_layout);
        listView = f(R.id.listView);
    }

    @Override
    protected void initData() {

    }
}
