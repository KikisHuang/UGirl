package example.com.fan.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private TextView textview;

    @Override
    protected void click() {
        textview.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        textview = f(R.id.textview);

    }

    @Override
    protected void initData() {
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview:
                ToastUtil.toast2_bottom(this, MyAppcation.CHANNEL);
                break;
        }
    }

}
