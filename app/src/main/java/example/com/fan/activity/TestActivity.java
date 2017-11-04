package example.com.fan.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import example.com.fan.R;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private Button share_bt;
    private ImageView img;
    private int t = 0;

    @Override
    protected void click() {
        share_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        share_bt = f(R.id.share_bt);
        img = f(R.id.img);
    }

    @Override
    protected void initData() {


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_bt:
                if (t % 2 == 0)
                    ReadImg("http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/1509521902851f50e5d.jpg");
                else
                    ReadImg("http://fns-photo-hide.oss-cn-hangzhou.aliyuncs.com/1509521902851f50e5d.jpg?Expires=1509589903&OSSAccessKeyId=J2eLFL9RzW0ox6sE&Signature=tV%2FzkQsteu25jCZLGhpScKkbmxs%3D");
                t++;
                break;
        }
    }
    private void ReadImg(String url) {
        try {
            RequestOptions options = new RequestOptions();
            options.fitCenter().error(R.drawable.load_fail_img);
            Glide.with(getApplicationContext())
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .apply(options).into(img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
    }
}
