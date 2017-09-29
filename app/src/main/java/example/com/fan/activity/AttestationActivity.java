package example.com.fan.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;

import example.com.fan.R;
import example.com.fan.mylistener.onPhotoCutListener;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.RippleView;

import static example.com.fan.utils.SynUtils.PhotoPictureDialog;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/28.
 */
public class AttestationActivity extends InitActivity implements onPhotoCutListener, View.OnClickListener {
    private EditText renz_name, renz_acode, contact_ed;
    private ImageView ren_icon1, ren_icon2;
    private RippleView renz_btn;
    private HashMap<String, File> map;
    private File frontFile = null;
    private File reverseFile = null;

    @Override
    protected void click() {
        ren_icon1.setOnClickListener(this);
        ren_icon2.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.attestation_activity);
        setTitles(this, "实名认证");
        map = new HashMap<>();
        ren_icon1 = f(R.id.ren_icon1);
        ren_icon2 = f(R.id.ren_icon2);
        contact_ed = f(R.id.contact_ed);
        renz_name = f(R.id.renz_name);
        renz_acode = f(R.id.renz_acode);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void PhotoListener(String path) {

    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        if (page == 103) {
            Glide.with(this).load(path).into(ren_icon1);
            frontFile = new File(path);
        }

        if (page == 104) {
            Glide.with(this).load(path).into(ren_icon2);
            reverseFile = new File(path);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ren_icon1:
                PhotoPictureDialog(this, false, 103);
                break;
            case R.id.ren_icon2:
                PhotoPictureDialog(this, false, 104);
                break;
            case R.id.renz_btn:
                if (frontFile != null && reverseFile != null && !renz_name.getText().toString().isEmpty() && !renz_acode.getText().toString().isEmpty() && !contact_ed.getText().toString().isEmpty() && renz_acode.getText().toString().length() == 18)
                    SendId();
                else if (frontFile == null)
                    ToastUtil.toast2_bottom(this, "身份证正面照不可为空！！");
                else if (frontFile == null)
                    ToastUtil.toast2_bottom(this, "身份证反面照不可为空！！");
                else if (renz_name.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "身份证名字不可为空！！");
                else if (renz_acode.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "身份证号不可为空！！");
                else if (contact_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "联系方式不可为空，请勿提交虚假联系方式，否则无法通过审核！");
                else if (renz_acode.getText().toString().length() > 18 || renz_acode.getText().toString().length() < 18)
                    ToastUtil.toast2_bottom(this, "身份证号有误，请核实后再上传！");
                break;
        }
    }

    /**
     * 发送信息认证;
     */
    private void SendId() {
        map.clear();
        map.put(frontFile.getName(), frontFile);
        map.put(reverseFile.getName(), reverseFile);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reverseFile = null;
        frontFile = null;
    }
}
