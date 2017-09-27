package example.com.fan.activity;


import android.widget.EditText;

import example.com.fan.R;
import example.com.fan.view.RippleView;

import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/25.
 */
public class UploadWeChatActivity extends InitActivity {
    private final static String TAG = UploadWeChatActivity.class.getSimpleName();
    private EditText price_ed;
    private RippleView save_button;
    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.upload_wechat_activity);
        setTitles(this, "填写微信号");

        save_button = f(R.id.save_button);
        price_ed = f(R.id.price_ed);
    }

    @Override
    protected void initData() {

    }
}
