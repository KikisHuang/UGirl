package example.com.fan.activity;

import android.view.View;
import android.widget.FrameLayout;

import example.com.fan.R;
import example.com.fan.view.RippleView;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private FrameLayout frament_ll;
    private RippleView button;

    @Override
    protected void click() {
        button.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        button = f(R.id.button);
        frament_ll = f(R.id.fragment_ll);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                break;
        }
    }
}
