package example.com.fan.activity;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private TextView tv;
    private Button button;
    private boolean flag = true;
    //属性动画对象
    ValueAnimator va;

    @Override
    protected void click() {
        button.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        tv = f(R.id.test_tv);
        button = f(R.id.button);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (tv.getLayoutParams().height == 150)
                    //隐藏view，高度从height变为0
                    va = ValueAnimator.ofInt(DeviceUtils.dip2px(this, 50), DeviceUtils.dip2px(this, 25));
                else
                    va = ValueAnimator.ofInt(DeviceUtils.dip2px(this, 25), DeviceUtils.dip2px(this, 50));

                flag = !flag;
                Log.i(TAG, "flag ===" + tv.getLayoutParams().height);

                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //获取当前的height值
                        int h = (Integer) valueAnimator.getAnimatedValue();
                        //动态更新view的高度
                        tv.getLayoutParams().height = h;
                        tv.requestLayout();
                    }
                });
                va.setDuration(800);
                //开始动画
                va.start();
                break;

        }
    }
}
