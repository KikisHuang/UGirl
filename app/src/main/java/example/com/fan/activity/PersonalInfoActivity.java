package example.com.fan.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuView;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.fragment.MyFragment;
import example.com.fan.utils.JsonUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.ServerPopupWindow;
import example.com.fan.view.RippleView;
import example.com.fan.view.dialog.ActionSheetDialog;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.JsonUtils.NullDispose;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.PhotoUtils.onSinglePhoto;
import static example.com.fan.utils.SynUtils.Finish;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getSex;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/6/12.
 */
public class PersonalInfoActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(PersonalInfoActivity.class);
    private EditText info_name, phone_tv, wx_tv/*, invite_tv*/;
    private ImageView clear_img, sex_icon, user_icon;
    private TextView info_sex, address_tv;
    private RippleView submit_info,wx_bt;
    private String receiveSex, receiveName, receiveAdd, addressPhone, wechat;
//    private LinearLayout invite_ll;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL)
            return true;
        else if (keyCode == KeyEvent.KEYCODE_BACK) {
            Finish(this);
            return super.onKeyDown(keyCode, event);
        } else
            return super.onKeyDown(keyCode, event);
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
        wx_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.person_info_activity_layout);
        MYsetTitles(this, getRouString(R.string.my));
        address_tv = f(R.id.address_tv);
        submit_info = f(R.id.submit_info);
        user_icon = f(R.id.user_icon);
//        invite_tv = f(R.id.invite_tv);
        info_sex = f(R.id.info_sex);
//        invite_ll = f(R.id.invite_ll);
        clear_img = f(R.id.clear_img);
        sex_icon = f(R.id.sex_icon);
        info_name = f(R.id.info_name);
        phone_tv = f(R.id.phone_tv);
        wx_bt = f(R.id.wx_bt);
        wx_tv = f(R.id.wx_tv);
        //第一次进入不弹出软键盘;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      /*  if (getInViCode()) {
            invite_ll.setVisibility(View.VISIBLE);
            saveInViCode(false);
        }*/

    }

    private void MYsetTitles(final PersonalInfoActivity ac, String str) {

        TextView textView = (TextView) ac.findViewById(R.id.title_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setText(str);
        hideView(ac);
        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Finish(ac);
            }
        });
    }

    /**
     * View控件隐藏;
     *
     * @param ac
     */
    public static void hideView(Activity ac) {
        MaterialMenuView silide_img = (MaterialMenuView) ac.findViewById(R.id.material_menu_button);
        ImageView search_img = (ImageView) ac.findViewById(R.id.search_img);
        ImageView title_img = (ImageView) ac.findViewById(R.id.title_img);
        silide_img.setVisibility(View.GONE);
        search_img.setVisibility(View.GONE);
        title_img.setVisibility(View.GONE);
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
                Glide.with(getApplicationContext()).load(getIntent().getStringExtra("person_icon")).apply(getRequestOptions(false, 0, 0, true)).into(user_icon);
            else
                Glide.with(getApplicationContext()).load(R.mipmap.test_icon).apply(getRequestOptions(false, 0, 0, true)).into(user_icon);

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
                                onSinglePhoto(PersonalInfoActivity.this, true);
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        onSinglePhoto(PersonalInfoActivity.this, false);
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

                if (add.equals(receiveAdd) && name.equals(receiveName) && sex.equals(receiveSex) && addphone.equals(addressPhone) && wx.equals(wechat)) {
                    Finish(this);
                    Log.i(TAG, "没有任何修改");
                } else
                    ChangeUserInfo(add, sex, name, addphone, wx);

                break;
            case R.id.wx_bt:
                ServerPopupWindow sp = new ServerPopupWindow(this);
                sp.ScreenPopupWindow();
                break;
        }
    }

    private void ChangeUserInfo(final String add, final String sex, final String name, String addphone, String wx) {

        if (!add.equals(receiveAdd) || !addphone.equals(addressPhone)) {
            if (receiveName == null)
                receiveName = "";
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
                                Finish(PersonalInfoActivity.this);
                            } else
                                ToastUtil.ToastErrorMsg(PersonalInfoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "压缩后路径===" + selectList.get(0).getCompressPath());
                    String path = selectList.get(0).getCompressPath();
                    UploadIcon(path);
                    break;
            }
        }
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
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);

                            Cancle();
                            if (code == 1) {
                                try {
                                    Glide.with(getApplicationContext()).load(path).apply(getRequestOptions(false, 0, 0, true)).into(user_icon);
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
