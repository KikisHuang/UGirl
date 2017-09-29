package example.com.fan.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.AddPrivatePhotoAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.MirrorBean;
import example.com.fan.bean.PrivatePhotoUpBean;
import example.com.fan.bean.mcPublishImgUrls;
import example.com.fan.mylistener.addImgListener;
import example.com.fan.mylistener.onPhotoCutListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.CustomGridView;
import example.com.fan.view.RippleView;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.PhotoPictureDialog;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/9/27.
 */
public class UploadPrivatePhotoActivity extends InitActivity implements View.OnClickListener, addImgListener, onPhotoCutListener {
    private static final String TAG = getTAG(UploadPrivatePhotoActivity.class);
    private LinearLayout lock_layout, upload_layout;
    private RippleView lock_button, save_button;
    private CustomGridView gridView;
    private EditText price_detail_ed, price_ed;
    private List<mcPublishImgUrls> list;
    private AddPrivatePhotoAdapter adapter;
    private addImgListener add;
    public static onPhotoCutListener listener;
    //第几张收费标记;
    private int chargeNumber = 0;
    //第几张图片标记
    private int pos;
    //id存储;
    private String SpecialId = "";
    //图片修改标识符;
    private boolean ImgChange = false;

    @Override
    protected void click() {
        lock_button.setOnClickListener(this);
        save_button.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.upload_photo_activity);
        setTitles(this, "上传私密照片");
        add = this;
        listener = this;
        lock_button = f(R.id.lock_button);
        lock_layout = f(R.id.lock_layout);
        save_button = f(R.id.save_button);
        price_ed = f(R.id.price_ed);
        list = new ArrayList<>();
        mcPublishImgUrls mp = new mcPublishImgUrls();
        list.add(mp);
        price_detail_ed = f(R.id.price_detail_ed);
        gridView = f(R.id.gridView);
        upload_layout = f(R.id.upload_layout);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        /**
         * 获取未完成私密照片;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETOLDPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UploadPrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONObject ar = getJsonOb(response);
                            if (code == 1) {

                                Log.i(TAG, "GETOLDPRIVATEPHOTO" + response);
                                if (ar.optJSONArray("mcPublishImgUrls").length() > 0) {

                                    MirrorBean mb = new Gson().fromJson(String.valueOf(getJsonOb(response)), MirrorBean.class);
                                    SpecialId = mb.getId();
                                    chargeNumber = ar.optInt("hidePosition");

                                    for (int i = 0; i < mb.getMcPublishImgUrls().size(); i++) {
                                        mcPublishImgUrls mpiu = mb.getMcPublishImgUrls().get(i);
                                        list.add(mpiu);
                                    }
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new AddPrivatePhotoAdapter(UploadPrivatePhotoActivity.this, list, add);
                                    gridView.setAdapter(adapter);
                                }
                                upload_layout.setVisibility(View.VISIBLE);
                            } else if (code == 0)
                                lock_layout.setVisibility(View.VISIBLE);
                            else
                                ToastUtil.ToastErrorMsg(UploadPrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lock_button:
                if (!price_detail_ed.getText().toString().isEmpty())
                    SettingImgPostion();
                else
                    ToastUtil.toast2_bottom(this, "请设置从第几张开始收费!!");
                break;

            case R.id.save_button:
                if (!price_ed.getText().toString().isEmpty() && Integer.valueOf(price_ed.getText().toString()) > 1 && Integer.valueOf(price_ed.getText().toString()) <= 28)
                    Issue();
                else if (!price_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(this, "私照金额不能为空!");
                else if (Integer.valueOf(price_ed.getText().toString()) < 2)
                    ToastUtil.toast2_bottom(this, "私照金额不能小于2元!");
                else if (Integer.valueOf(price_ed.getText().toString()) > 28)
                    ToastUtil.toast2_bottom(this, "私照金额不能大于28元!");

                break;

        }
    }

    private void Issue() {
        /**
         * 发布专辑;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.PUBLISHPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("price", price_ed.getText().toString())
                .addParams("id", SpecialId)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UploadPrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Log.i(TAG, "PUBLISHPRIVATEPHOTO" + response);
                                ToastUtil.toast2_bottom(UploadPrivatePhotoActivity.this, "成功提交！审核通过后专辑自动发布！");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(UploadPrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void SettingImgPostion() {
        /**
         * 设置专辑收费照片(创建专辑);
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CREATEPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("hidePosition", price_detail_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UploadPrivatePhotoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                chargeNumber = Integer.parseInt(price_detail_ed.getText().toString());
                                PrivatePhotoUpBean pp = new Gson().fromJson(String.valueOf(getJsonOb(response)), PrivatePhotoUpBean.class);
                                SpecialId = pp.getId();

                                adapter = new AddPrivatePhotoAdapter(UploadPrivatePhotoActivity.this, list, add);
                                gridView.setAdapter(adapter);
                                lock_layout.setVisibility(View.GONE);
                                upload_layout.setVisibility(View.VISIBLE);

                            } else
                                ToastUtil.ToastErrorMsg(UploadPrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onAdd(int position) {
        if (list.size() < 16) {
            ImgChange = false;
            pos = list.size();
            PhotoPictureDialog(this, false, 102);
        }else
            ToastUtil.toast2_bottom(this,"已经15张了哦！如果想继续发布先上传此专辑吧！");

    }

    @Override
    public void onChange(int position) {
        if (position <= list.size()) {
            ImgChange = true;
            pos = position;
            PhotoPictureDialog(this, false, 102);
        }
    }

    @Override
    public void onDelete(int pos) {

    }

    @Override
    public void PhotoListener(String path) {

    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        bitmap.recycle();
        if (page == 102)
            NewPrivatePhoto(new File(path));
    }

    private void NewPrivatePhoto(File file) {
        Show(UploadPrivatePhotoActivity.this, "", true, null);
        int price;
        if (chargeNumber <= pos)
            price = 1;
        else
            price = 0;

        Log.i(TAG, "num == " + pos + "  needMoney == " + price + "id == " + SpecialId + "  chargeNumber==" + chargeNumber);
        /**
         * 新增私照;
         */
        OkHttpUtils.post()
                .url(MzFinal.URl + MzFinal.ADDPHOTOIMG)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", SpecialId)
                .addParams("needMoney", String.valueOf(price))
                .addParams("num", String.valueOf(pos))
                .addFile("file", file.getName(), file)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UploadPrivatePhotoActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            Log.i(TAG, "ADDPHOTOIMG" + response);
                            if (code == 1) {
                                if (ImgChange) {
                                    mcPublishImgUrls mpiu = new Gson().fromJson(String.valueOf(getJsonOb(response)), mcPublishImgUrls.class);

                                    list.get(pos).setBasePath(mpiu.getBasePath());
                                    list.get(pos).setId(mpiu.getId());
                                    list.get(pos).setNeedMoney(mpiu.getNeedMoney());
                                    list.get(pos).setPath(mpiu.getPath());
                                    if (adapter != null)
                                        adapter.notifyDataSetChanged();

                                } else {
                                    mcPublishImgUrls mpiu = new Gson().fromJson(String.valueOf(getJsonOb(response)), mcPublishImgUrls.class);
                                    list.add(mpiu);
                                    if (adapter != null)
                                        adapter.notifyDataSetChanged();
                                }

                            } else
                                ToastUtil.ToastErrorMsg(UploadPrivatePhotoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                        Cancle();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        listener = null;
        Cancle();
    }
}
