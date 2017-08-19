package example.com.fan.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.fragment.MyFragment;
import example.com.fan.mylistener.onPhotoCutListener;
import example.com.fan.utils.JsonUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.dialog.ActionSheetDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.base.sign.save.SPreferences.getInViCode;
import static example.com.fan.base.sign.save.SPreferences.saveInViCode;
import static example.com.fan.utils.IntentUtils.goUploadPhotoPage;
import static example.com.fan.utils.JsonUtils.NullDispose;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getSex;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/6/12.
 */
public class PersonalInfoActivity extends InitActivity implements View.OnClickListener, onPhotoCutListener {
    private static final String TAG = getTAG(PersonalInfoActivity.class);
    private EditText info_name, phone_tv, wx_tv, invite_tv;
    private ImageView clear_img, sex_icon, user_icon;
    private TextView info_sex, address_tv, submit_info;
    public static onPhotoCutListener listener;
    private String receiveSex, receiveName, receiveAdd, addressPhone, wechat;
    private LinearLayout invite_ll;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (listener != null)
            listener = null;
    }

    private void getAddress() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETADDRESSBYUSER)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PersonalInfoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = JsonUtils.getJsonOb(response);
                                receiveAdd = NullDispose(ob, "address");
                                addressPhone = NullDispose(ob, "phone");

                                address_tv.setText(receiveAdd);
                                phone_tv.setText(addressPhone);

                            } else if (code == 0) {
                                receiveAdd = "";
                                addressPhone = "";
                            } else
                                ToastUtil.ToastErrorMsg(PersonalInfoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void click() {
        info_sex.setOnClickListener(this);
        submit_info.setOnClickListener(this);
        user_icon.setOnClickListener(this);
        clear_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.person_info_activity_layout);
        setTitles(this, getRouString(R.string.my));
        listener = this;
        address_tv = f(R.id.address_tv);
        submit_info = f(R.id.submit_info);
        user_icon = f(R.id.user_icon);
        invite_tv = f(R.id.invite_tv);
        info_sex = f(R.id.info_sex);
        invite_ll = f(R.id.invite_ll);
        clear_img = f(R.id.clear_img);
        sex_icon = f(R.id.sex_icon);
        info_name = f(R.id.info_name);
        phone_tv = f(R.id.phone_tv);
        wx_tv = f(R.id.wx_tv);
        //第一次进入不弹出软键盘;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getInViCode()) {
            invite_ll.setVisibility(View.VISIBLE);
            saveInViCode(false);
        }

    }

    @Override
    protected void initData() {
        receiveData();
        getAddress();
    }

    private void receiveData() {
        receiveSex = getIntent().getStringExtra("person_sex");
        receiveName = getIntent().getStringExtra("person_name");
        wechat = getIntent().getStringExtra("person_wx");

        wx_tv.setText(wechat);
        info_sex.setText(receiveSex);
        if (info_sex.getText().toString().equals(R.string.man))
            sex_icon.setImageResource(R.mipmap.man_icon);
        else
            sex_icon.setImageResource(R.mipmap.men_icon);

        info_name.setText(receiveName);
        try {
            if (!getIntent().getStringExtra("person_icon").isEmpty())
                Glide.with(getApplicationContext()).load(getIntent().getStringExtra("person_icon")).bitmapTransform(new CropCircleTransformation(this)).into(user_icon);
            else
                Glide.with(getApplicationContext()).load(R.mipmap.test_icon).bitmapTransform(new CropCircleTransformation(this)).into(user_icon);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_sex:
                new ActionSheetDialog(this).builder().
                        addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                info_sex.setText(R.string.man);
                                sex_icon.setImageResource(R.mipmap.man_icon);
                            }
                        }).addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        info_sex.setText(R.string.men);
                        sex_icon.setImageResource(R.mipmap.men_icon);
                    }
                }).show();
                break;
            case R.id.user_icon:

                new ActionSheetDialog(this).builder().
                        addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goUploadPhotoPage(PersonalInfoActivity.this, "0");
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(PersonalInfoActivity.this, "1");
                    }
                }).show();

                break;
            case R.id.clear_img:
                info_name.setText("");
                break;
            case R.id.submit_info:
                String name = info_name.getText().toString().trim();
                String sex = info_sex.getText().toString().trim();
                String add = address_tv.getText().toString().trim();
                String addphone = phone_tv.getText().toString().trim();
                String wx = wx_tv.getText().toString().trim();

                if (!invite_tv.getText().toString().trim().isEmpty() && getInViCode() && invite_ll.getVisibility() == View.VISIBLE)
                    submitInVite();

                if (add.equals(receiveAdd) && name.equals(receiveName) && sex.equals(receiveSex) && addphone.equals(addressPhone) && wx.equals(wechat)) {
                    finish();
                    Log.i(TAG, "没有任何修改");
                } else
                    ChangeUserInfo(add, sex, name, addphone, wx);

                break;
        }
    }

    private void submitInVite() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.USEINVITATIONCODE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("useInvitationCode", invite_tv.getText().toString().trim())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PersonalInfoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                if (ub.getUseInvitationCode() == null)
                                    ToastUtil.toast2_bottom(PersonalInfoActivity.this, "邀请码不正确");
                                else
                                    ToastUtil.toast2_bottom(PersonalInfoActivity.this, "成功提交邀请码");
                            } else
                                ToastUtil.ToastErrorMsg(PersonalInfoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void ChangeUserInfo(final String add, final String sex, final String name, String addphone, String wx) {

        Map<String, String> map = new HashMap<>();
        if (!add.equals(receiveAdd) || !addphone.equals(addressPhone)) {
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.MODIFYADDRESS)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams("address", add)
                    .addParams("phone", addphone)
                    .addParams("name", receiveName)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(PersonalInfoActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    Log.i(TAG, "地址修改成功...");
                                    receiveAdd = add;
                                } else
                                    ToastUtil.ToastErrorMsg(PersonalInfoActivity.this, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });
        }

        /**
         * 修改用户信息;
         */

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.MODIFYUSER)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("sex", String.valueOf(getSex(info_sex.getText().toString().trim())))
                .addParams("wx", wx)
                .addParams("name", info_name.getText().toString().trim())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PersonalInfoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                receiveName = name;
                                receiveSex = sex;
                                ToastUtil.toast2_bottom(PersonalInfoActivity.this, "修改成功!");
                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(PersonalInfoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void PhotoListener(String path) {

    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap) {
        UploadIcon(path);
    }

    private void UploadIcon(final String path) {
        /**
         * 上传头像;
         */
        File file = new File(path);
        Show(PersonalInfoActivity.this, "正在上传头像...", true, null);
        OkHttpUtils.post()
                .addFile("file", file.getName(), file)
                .url(MzFinal.URl + MzFinal.UPLOADLOGO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PersonalInfoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);

                            Cancle();
                            if (code == 1) {
                                try {
                                    Glide.with(getApplicationContext()).load(path).bitmapTransform(new CropCircleTransformation(PersonalInfoActivity.this)).crossFade(300).into(user_icon);
                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }
                                ToastUtil.toast2_bottom(PersonalInfoActivity.this, "头像上传成功...");
                                if (MyFragment.fragment != null)
                                    MyFragment.fragment.onUpDataUserInfo();

                            } else
                                ToastUtil.ToastErrorMsg(PersonalInfoActivity.this, response, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
