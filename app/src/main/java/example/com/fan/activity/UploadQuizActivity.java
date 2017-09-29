package example.com.fan.activity;

import example.com.fan.R;

import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/27.
 */
public class UploadQuizActivity extends InitActivity {
    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.upload_quiz_activity);
        setTitles(this, "私密提问");
    }

    @Override
    protected void initData() {

    }
}
