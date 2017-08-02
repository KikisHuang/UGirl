package example.com.fan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import example.com.fan.R;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity {
    private static final String TAG = getTAG(TestActivity.class);
    private TextView tv, tv1, tv2, tv3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void click() {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    protected void init() {
        setContentView(R.layout.test_layout);
        tv = (TextView) findViewById(R.id.edit);

    }

    @Override
    protected void initData() {

    }

}
