package example.com.fan.activity;

import android.view.View;

import com.bumptech.glide.Glide;

import example.com.fan.R;
import example.com.fan.utils.ToastUtil;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lian on 2017/11/1.
 */
public class InstructionsPhotoActivity extends InitActivity {
    private PhotoView iv_main_pic;
    private String path = "";

    @Override
    protected void click() {
        iv_main_pic.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.instructions_photo_layout);
        iv_main_pic = f(R.id.iv_main_pic);
        path = getIntent().getStringExtra("instructions_photo_path");
        if (path == null || path.isEmpty()) {
            ToastUtil.toast2_bottom(this, "没有获取到图片路径。");
            finish();
        }
    }

    @Override
    protected void initData() {
        Glide.with(this).load(path).into(iv_main_pic);
    }
}
