package example.com.fan.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.utils.TitleUtils;

/**
 * Created by lian on 2017/6/27.
 */
public class TaskActivity extends InitActivity {
    private LinearLayout task_ll;
    private List<View> vlist;


    @Override
    protected void click() {

    }

    private void getData() {
        for (int i = 0; i < 5; i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.task_include_layout, null);
            ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressbar);
            final LinearLayout outside_ll = (LinearLayout) v.findViewById(R.id.outside_ll);
            final LinearLayout inside_ll = (LinearLayout) v.findViewById(R.id.inside_ll);
            pb.setProgress(i);
            pb.setMax(5);
            vlist.add(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < vlist.size(); i++) {
                        vlist.get(i).findViewById(R.id.outside_ll).setVisibility(View.VISIBLE);
                        vlist.get(i).findViewById(R.id.inside_ll).setVisibility(View.GONE);
                    }
                    if (inside_ll.getVisibility() == View.GONE) {
                        outside_ll.setVisibility(View.GONE);
                        inside_ll.setVisibility(View.VISIBLE);
                    } else {
                        outside_ll.setVisibility(View.VISIBLE);
                        inside_ll.setVisibility(View.GONE);
                    }
                }
            });
            task_ll.addView(v);
        }
    }


    @Override
    protected void init() {
        setContentView(R.layout.task_activity_layout);
        TitleUtils.setTitles(this, getResources().getString(R.string.daily_task));
        task_ll = f(R.id.task_ll);
        vlist = new ArrayList<>();
    }

    @Override
    protected void initData() {
        getData();
    }
}
