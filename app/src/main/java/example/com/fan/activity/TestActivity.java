package example.com.fan.activity;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;

import example.com.fan.R;
import example.com.fan.utils.MyGlideModule;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private ImageView glide_img;
    private String imgurl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510116912805&di=9294a206b7c086bfd9d1ace16b57f245&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F6%2F577dff80d6ee1.jpg";

    @Override
    protected void click() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        glide_img = f(R.id.glide_img);
        MyGlideModule GlideConfig = new MyGlideModule();
        GlideBuilder builder = new GlideBuilder();
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        GlideConfig.applyOptions(this, builder);
    }

    @Override
    protected void initData() {
        Glide.with(this).load(imgurl).apply(getRequestOptions(false, 0, 0, false)).into(glide_img);
        Uri uri = Uri.parse(imgurl);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
