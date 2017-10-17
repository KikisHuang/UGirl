package example.com.fan.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.VideoCoverAdapter;
import example.com.fan.adapter.VideoPhotoAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.VideoImgBean;
import example.com.fan.bean.mcPublishImgUrls;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.VideoImgSettingListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.EditTextTextImposeView;
import example.com.fan.view.GlideRoundTransform;
import example.com.fan.view.RippleView;
import okhttp3.Call;

import static example.com.fan.utils.FileSizeUtil.getFileOrFilesSize;
import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goAwardPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.deleteDir;
import static example.com.fan.utils.SynUtils.getRingDuring;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getVideoThumbnail;
import static example.com.fan.utils.SynUtils.saveImage;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/10/13.
 */
public class UserPhotoOfVideoUpLoadActivity extends InitActivity implements View.OnClickListener, VideoImgSettingListener, ItemClickListener {
    private final static String TAG = getTAG(UserPhotoOfVideoUpLoadActivity.class);
    private ImageView cover_img, video_cover1, video_cover2, video_cover3, video_img_return, cover_change_img;
    private EditText content_ed;
    private TextView text_number_tv, select_title, tag_tv;
    private LinearLayout change_video_cover, setting_give_layout, video_img_layout;
    private RippleView save_button;
    private int flag;
    private String FilePath = "";
    private EditTextTextImposeView eti;
    private List<VideoImgBean> fils;
    private SaveTask task;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private StaggeredGridLayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mLayoutManager1;

    private LinearLayout video_setting_layout;
    //视频截图选择适配器;
    private VideoPhotoAdapter adapter;
    //封面选择适配器;
    private VideoCoverAdapter coveradapter;
    //手选视频截图集合;
    private List<String> CustomImgs;
    private Button confirm_bt;
    private VideoImgSettingListener listener;
    //封面路径;
    private String coverPath = "";
    //LocalMedia
    private List<LocalMedia> selectList;
    //未完成list;
    private List<mcPublishImgUrls> unlist;
    //私照封面集合;
    private List<String> photolist;
    //视频封面集合;
    private List<String> videolist;
    //设置价格标识符;
    private boolean PriceFlag = false;
    private String price = "";
    private int chargeNumber = 0;
    private String photo_specialId = "";

    @Override
    protected void click() {
        save_button.setOnClickListener(this);
        setting_give_layout.setOnClickListener(this);
        change_video_cover.setOnClickListener(this);
        cover_change_img.setOnClickListener(this);
    }

    @Override
    protected void init() {

        setContentView(R.layout.user_photo_video_upload_layout);
        Receiver();

        cover_img = f(R.id.cover_img);
        video_img_layout = f(R.id.video_img_layout);
        cover_change_img = f(R.id.cover_change_img);

        content_ed = f(R.id.content_ed);
        text_number_tv = f(R.id.text_number_tv);
        change_video_cover = f(R.id.change_video_cover);
        setting_give_layout = f(R.id.setting_give_layout);
        save_button = f(R.id.save_button);
        recyclerView = f(R.id.recyclerView);
        recyclerView1 = f(R.id.recyclerView1);
        select_title = f(R.id.select_title);
        tag_tv = f(R.id.tag_tv);
        video_img_return = f(R.id.video_img_return);
        confirm_bt = f(R.id.confirm_bt);
        video_setting_layout = f(R.id.video_setting_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        mLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView1.setLayoutManager(mLayoutManager1);

        NumberInit();
    }

    private void NumberInit() {
        eti = new EditTextTextImposeView(content_ed, text_number_tv);
        eti.ImPoseText();
    }

    private void PhotoInit() {
        setTitles(this, "私照上传");
        selectList = (ArrayList<LocalMedia>) getIntent().getSerializableExtra("selectLists");
        photolist = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            if (getFileOrFilesSize(selectList.get(i).getPath(), 3) > 1.5) {
                photolist.add(selectList.get(i).getCompressPath());
                Log.i(TAG, "大于1.5m使用压缩图片");
            } else {
                photolist.add(selectList.get(i).getPath());
                Log.i(TAG, "小于1.5m使用原图");
            }
        }

        video_img_layout.setVisibility(View.GONE);
        Glide.with(this)
                .load(FilePath)
                .apply(getRequestOptions(true, 0, 0, false).transform(new GlideRoundTransform(this)))
                .into(cover_img);
    }

    private void PhotoInit2() {
        setTitles(this, "私照上传");
        PriceFlag = true;
        photo_specialId = getIntent().getStringExtra("photo_specialId");
        chargeNumber = Integer.parseInt(getIntent().getStringExtra("photo_of_chargeNumber"));

        unlist = (ArrayList<mcPublishImgUrls>) getIntent().getSerializableExtra("mcPublishImgUrls");
        photolist = new ArrayList<>();
        for (int i = 0; i < unlist.size(); i++) {
            photolist.add(unlist.get(i).getPath());
        }
        video_img_layout.setVisibility(View.GONE);
        Glide.with(this)
                .load(FilePath)
                .apply(getRequestOptions(true, 0, 0, false).transform(new GlideRoundTransform(this)))
                .into(cover_img);
    }

    private void VideoInit() {
        setTitles(this, "视频上传");
        video_img_layout.setVisibility(View.VISIBLE);
        listener = this;
        video_cover1 = f(R.id.video_cover1);
        video_cover2 = f(R.id.video_cover2);
        video_cover3 = f(R.id.video_cover3);

        fils = new ArrayList<>();
        CustomImgs = new ArrayList<>();
        videolist = new ArrayList<>();
        task = (SaveTask) new SaveTask().execute(FilePath);
        video_img_return.setOnClickListener(this);
        confirm_bt.setOnClickListener(this);
    }

    private void Receiver() {
        flag = Integer.parseInt(getIntent().getStringExtra("photo_of_video_flag"));
        FilePath = getIntent().getStringExtra("photo_of_video_FilePath");
        if (FilePath == null || FilePath.isEmpty()) {
            finish();
            if (flag == 0)
                ToastUtil.toast2_bottom(this, "没有获取到图片路径!!");
            if (flag == 1)
                ToastUtil.toast2_bottom(this, "没有获取到视频路径!!");
            if (flag == 3)
                ToastUtil.toast2_bottom(this, "没有获取到图片路径!!");
        }

    }

    @Override
    protected void initData() {
        if (flag == 0)
            PhotoInit();
        if (flag == 1)
            VideoInit();
        if (flag == 3)
            PhotoInit2();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_video_cover:
                if (flag == 1 && recyclerView.getVisibility() == View.GONE && fils.size() == 10)
                    SettingVideoImg();
                break;
            case R.id.setting_give_layout:
                if (PriceFlag) {
                    ToastUtil.toast2_bottom(this, "已经设置过价格不能再修改了哦~");
                } else {
                    if (flag == 0 || flag == 3)
                        goAwardPage(this, 0, photolist);
                    else
                        goAwardPage(this, flag, null);
                }
                break;
            case R.id.confirm_bt:
                if (flag == 1 && recyclerView.getVisibility() == View.VISIBLE)
                    listener.onChange();
                break;
            case R.id.save_button:
                if (flag == 0 || flag == 3) {
                    if (chargeNumber != 0)
                        PrivatePhotoUpload();
                    else if (chargeNumber == 0)
                        ToastUtil.toast2_bottom(this, "请先设置价格!!");

                } else if (flag == 1) {
                    if (!price.isEmpty() && videolist.size() > 0)
                        PrivateVideoUpload();
                    else if (price.isEmpty())
                        ToastUtil.toast2_bottom(this, "请先设置价格!!");
                }

                break;
            case R.id.video_img_return:
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    video_setting_layout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
                if (recyclerView1.getVisibility() == View.VISIBLE) {
                    video_setting_layout.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.GONE);
                }
                break;
            case R.id.cover_change_img:
                if (flag == 0 && recyclerView.getVisibility() == View.GONE && photolist.size() > 0)
                    coverSetting(photolist);
                if (flag == 3 && recyclerView.getVisibility() == View.GONE && photolist.size() > 0)
                    coverSetting(photolist);
                if (flag == 1 && videolist.size() >= 10 && recyclerView.getVisibility() == View.GONE)
                    coverSetting(videolist);
                break;
        }
    }

    private void PrivateVideoUpload() {
        Show(UserPhotoOfVideoUpLoadActivity.this, "上传视频中..", false, null);
        String info = "";
        if (content_ed.getText().toString().isEmpty()) {
            if (flag == 0 || flag == 3)
                info = "我的私密照片";
            else
                info = "我的私密视频";
        } else
            info = content_ed.getText().toString();

        File file = new File(FilePath);
        File file2 = new File(coverPath);
        HashMap<String, File> map = new HashMap<>();
        if (CustomImgs.size() != 3) {
            Log.i(TAG, "自动选视频截图");
            for (int i = 0; i < 3; i++) {
                File file3 = new File(videolist.get(i + 1));
                map.put(file3.getName(), file3);
            }
        } else {
            Log.i(TAG, "手选视频截图");
            for (int i = 0; i < CustomImgs.size(); i++) {
                File file3 = new File(CustomImgs.get(i));
                map.put(file3.getName(), file3);
            }
        }

        /**
         * 上传私密视频;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URl + MzFinal.ADDPRIVATEVIDEO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("price", price)
                .addParams("info", info)
                .addFile("file", file.getName(), file)
                .addFile("cover", file2.getName(), file2)
                .files("imgs", map)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UserPhotoOfVideoUpLoadActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Log.i(TAG, "PUBLISHPRIVATEPHOTO" + response);
                                ToastUtil.toast2_bottom(UserPhotoOfVideoUpLoadActivity.this, "成功提交！审核通过后专辑自动发布！");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(UserPhotoOfVideoUpLoadActivity.this, response, code);

                            Cancle();
                        } catch (Exception e) {
                            Cancle();
                        }
                    }
                });
    }

    private void PrivatePhotoUpload() {
        String info = "";
        if (content_ed.getText().toString().isEmpty()) {
            if (flag == 0 || flag == 3)
                info = "我的私密照片";
            else
                info = "我的私密视频";
        } else
            info = content_ed.getText().toString();
        /**
         * 发布照片;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.PUBLISHPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", photo_specialId)
                .addParams("info", info)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(UserPhotoOfVideoUpLoadActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Log.i(TAG, "PUBLISHPRIVATEPHOTO" + response);
                                ToastUtil.toast2_bottom(UserPhotoOfVideoUpLoadActivity.this, "成功提交！审核通过后专辑自动发布！");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(UserPhotoOfVideoUpLoadActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    private void coverSetting(List<String> list) {
        tag_tv.setText("请选择一张封面图片");
        select_title.setText("选择封面图片");
        video_setting_layout.setVisibility(View.VISIBLE);
        confirm_bt.setVisibility(View.GONE);
        recyclerView1.setVisibility(View.VISIBLE);
        if (coveradapter == null) {
            coveradapter = new VideoCoverAdapter(UserPhotoOfVideoUpLoadActivity.this, list, this);
            recyclerView1.setAdapter(coveradapter);
        } else
            coveradapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 7744:
                    PriceFlag = true;

                    switch (resultCode) {
                        case 111:
                            Bundle bundle = data.getExtras();
                            Log.i(TAG, "Price ===" + bundle.getString("price"));
                            Log.i(TAG, "SpecialId ===" + bundle.getString("SpecialId"));
                            Log.i(TAG, "chargeNumber ===" + bundle.getString("chargeNumber"));
                            price = bundle.getString("price");
                            photo_specialId = bundle.getString("SpecialId");
                            chargeNumber = Integer.parseInt(bundle.getString("chargeNumber"));
                            break;
                        case 100:
                            Bundle bundle1 = data.getExtras();
                            Log.i(TAG, "Price ===" + bundle1.getString("price"));
                            price = bundle1.getString("price");
                            break;
                    }
                    break;
            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            video_setting_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);

            return false;
        } else if (recyclerView1.getVisibility() == View.VISIBLE) {
            video_setting_layout.setVisibility(View.GONE);
            recyclerView1.setVisibility(View.GONE);
        } else
            return super.onKeyDown(keyCode, event);
        return true;
    }

    private void SettingVideoImg() {
        tag_tv.setText("你一共可以选择3张图片");
        select_title.setText("选择视频截图");
        video_setting_layout.setVisibility(View.VISIBLE);
        confirm_bt.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new VideoPhotoAdapter(UserPhotoOfVideoUpLoadActivity.this, fils, this);
            adapter.setTag(getNum());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setTag(getNum());
            adapter.notifyDataSetChanged();
        }
    }

    private int getNum() {
        int s = 0;
        for (int i = 0; i < fils.size(); i++) {
            if (fils.get(i).getSelectFlag())
                s++;
        }
        return s;
    }

    @Override
    public void onSelect(int pos, String path) {
        if (CustomImgs.size() < 4) {
            CustomImgs.add(path);
            fils.get(pos).setSelectFlag(true);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCancle(int pos, String path) {
        if (CustomImgs.size() > 0) {
            for (int i = 0; i < CustomImgs.size(); i++) {
                if (path.equals(CustomImgs.get(i))) {
                    CustomImgs.remove(i);
                    fils.get(pos).setSelectFlag(false);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onChange() {
        Log.i(TAG, "CustomImgs.size ===" + CustomImgs.size());
        if (CustomImgs.size() == 3) {
            video_setting_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            ToastUtil.toast2_bottom(this, "成功更换图片!!");

            Glide.with(getApplicationContext())
                    .load(CustomImgs.get(0))
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover1);
            Glide.with(getApplicationContext())
                    .load(CustomImgs.get(1))
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover2);
            Glide.with(getApplicationContext())
                    .load(CustomImgs.get(2))
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover3);

            adapter.notifyDataSetChanged();
        } else if (CustomImgs.size() < 3)
            ToastUtil.toast2_bottom(this, "必须选择3张图片!!");
    }

    @Override
    public void onItemClickListener(int position, String id) {
        coverPath = id;
        if (recyclerView1.getVisibility() == View.VISIBLE) {
            video_setting_layout.setVisibility(View.GONE);
            recyclerView1.setVisibility(View.GONE);
            Glide.with(getApplicationContext())
                    .load(coverPath)
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(cover_img);
            ToastUtil.toast2_bottom(this, "成功替换封面图片!!");
        }
    }

    /**
     * 异步任务执行保存图片操作
     */
    class SaveTask extends AsyncTask<Object, Void, Object> {
        long time = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Show(UserPhotoOfVideoUpLoadActivity.this, "正在截取视频图片...", true, null);
        }

        protected Object doInBackground(Object... params) {  //三个点，代表可变参数
            int len = getRingDuring(String.valueOf(params[0]));
            time = System.currentTimeMillis();
            Log.i(TAG, "lenth  =====" + len);
            for (int i = 11; i > 1; i--) {
                VideoImgBean vb = new VideoImgBean();
                String path = saveImage(getVideoThumbnail(String.valueOf(params[0]), len / i, len));
                vb.setFilePath(path);
                vb.setSelectFlag(false);
                fils.add(vb);
                videolist.add(path);
                if (fils.size() == 10)
                    return "next";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(0).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(cover_img);
            coverPath = fils.get(0).getFilePath();
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(1).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover1);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(2).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover2);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(3).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover3);

            if (String.valueOf(o).equals("next")) {
                long t = (System.currentTimeMillis() - time) / 1000;
                Log.i(TAG, " time ===" + t);
                Cancle();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel(true);
            task = null;
        }
        deleteDir(Environment.getExternalStorageDirectory() + "/Boohee");
        Cancle();
    }
}
