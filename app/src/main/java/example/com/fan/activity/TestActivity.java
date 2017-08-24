package example.com.fan.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.com.fan.R;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/23.
 * 测试页(用于测试新方法或bug调试);
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TestActivity.class);
    private EditText edit;
    private Button button;


    private TextView tv_signature;
    private PackageManager manager;
    private PackageInfo packageInfo;
    private Signature[] signatures;
    private StringBuilder builder;
    private String signature;

    @Override
    protected void click() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        edit = f(R.id.edit);
        button = f(R.id.button);
        tv_signature = f(R.id.tv_signature);
        manager = getPackageManager();
        builder = new StringBuilder();
    }

    public void getSignature(View view) {
        String pkgname = edit.getText().toString();
        boolean isEmpty = TextUtils.isEmpty(pkgname);
        if (isEmpty) {
            Toast.makeText(this, "应用程序的包名不能为空！", Toast.LENGTH_SHORT);
        } else {
            try {
                /** 通过包管理器获得指定包名包含签名的包信息 **/
                packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
                /******* 通过返回的包信息获得签名数组 *******/
                signatures = packageInfo.signatures;
                /******* 循环遍历签名数组拼接应用签名 *******/
                for (Signature signature : signatures) {
                    builder.append(signature.toCharsString());
                }
                /************** 得到应用签名 **************/
                signature = builder.toString();
                tv_signature.setText(signature);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
