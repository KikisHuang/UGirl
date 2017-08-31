package example.com.fan.activity;

import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import example.com.fan.R;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private Button button;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    String path = "";
    String fname = "U_Error";

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Override
    protected void click() {
        button.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        button = f(R.id.button);

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
