package example.com.fan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import example.com.fan.R;
import example.com.fan.view.dialog.ActionSheetDialog;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private Button share_bt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        share_bt = (Button) findViewById(R.id.share_bt);
        share_bt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_bt:

                new ActionSheetDialog(this).builder().
                        addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                    }
                }).show();
                break;
        }
    }
}
