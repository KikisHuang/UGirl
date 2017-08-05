package example.com.fan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import example.com.fan.R;

import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends AppCompatActivity {
    private static final String TAG = getTAG(TestActivity.class);
    Toolbar mActionBarToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        init();
        click();
    }

    protected void click() {
    }

    protected void init() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("我的标题");
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("我的标题");
        mActionBarToolbar.setBackgroundColor(getRouColors(R.color.cherry1));
    }

}
