package example.com.fan.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import example.com.fan.R;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private ImageView img;

    @Override
    protected void click() {
        img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        img = f(R.id.img);
        Glide.with(this)
                .load("http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15035476852731eaf84.jpg")
                .into(img);
    }

    @Override
    protected void initData() {
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img:
                Intent intent = new Intent(this, PhotoActivity.class);
                ActivityOptions options =  ActivityOptions.makeSceneTransitionAnimation
                            (this, v, "transition_morph_view");
                startActivity(intent, options.toBundle());
                break;
        }
    }

}
