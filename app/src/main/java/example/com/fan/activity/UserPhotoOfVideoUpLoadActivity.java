package example.com.fan.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.VideoPhotoAdapter;
import example.com.fan.bean.VideoImgBean;
import example.com.fan.mylistener.VideoImgSettingListener;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.EditTextTextImposeView;
import example.com.fan.view.GlideRoundTransform;
import example.com.fan.view.RippleView;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.SynUtils.getRingDuring;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getVideoThumbnail;
import static example.com.fan.utils.SynUtils.saveImage;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/13.
 */
public class UserPhotoOfVideoUpLoadActivity extends InitActivity implements View.OnClickListener, VideoImgSettingListener {
    private final static String TAG = getTAG(UserPhotoOfVideoUpLoadActivity.class);
    private ImageView cover_img, video_cover1, video_cover2, video_cover3, video_img_return;
    private EditText content_ed;
    private TextView text_number_tv;
    private LinearLayout change_video_cover, setting_give_layout, video_img_layout;
    private RippleView save_button;
    private int flag;
    private String FilePath = "";
    private EditTextTextImposeView eti;
    private List<VideoImgBean> fils;
    private SaveTask task;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;

    private LinearLayout video_setting_layout;
    private VideoPhotoAdapter adapter;
    private List<String> CustomImgs;
    private Button confirm_bt;
    private VideoImgSettingListener listener;

    @Override
    protected void click() {
        save_button.setOnClickListener(this);
        setting_give_layout.setOnClickListener(this);
        change_video_cover.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.user_photo_video_upload_layout);
        Receiver();

        cover_img = f(R.id.cover_img);
        video_img_layout = f(R.id.video_img_layout);

        content_ed = f(R.id.content_ed);
        text_number_tv = f(R.id.text_number_tv);
        change_video_cover = f(R.id.change_video_cover);
        setting_give_layout = f(R.id.setting_give_layout);
        save_button = f(R.id.save_button);
        NumberInit();
    }

    private void NumberInit() {
        eti = new EditTextTextImposeView(content_ed, text_number_tv);
        eti.ImPoseText();
    }

    private void PhotoInit() {
        setTitles(this, "私照上传");
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
        video_img_return = f(R.id.video_img_return);
        confirm_bt = f(R.id.confirm_bt);
        video_setting_layout = f(R.id.video_setting_layout);
        recyclerView = f(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);

        fils = new ArrayList<>();
        CustomImgs = new ArrayList<>();
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
        }

    }

    @Override
    protected void initData() {
        if (flag == 0)
            PhotoInit();
        else
            VideoInit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_video_cover:
                if (flag == 1 && recyclerView.getVisibility() == View.GONE && fils.size() == 10)
                    SettingVideoImg();
                break;
            case R.id.setting_give_layout:
                break;
            case R.id.confirm_bt:
                if (flag == 1 && recyclerView.getVisibility() == View.VISIBLE)
                    listener.onChange();
                break;
            case R.id.video_img_return:
                CustomImgs.clear();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (video_setting_layout.getVisibility() == View.VISIBLE) {
            video_setting_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            CustomImgs.clear();
            return false;
        } else
            return super.onKeyDown(keyCode, event);
    }

    private void SettingVideoImg() {
        video_setting_layout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
            adapter = new VideoPhotoAdapter(UserPhotoOfVideoUpLoadActivity.this, fils, this);
            recyclerView.setAdapter(adapter);
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

        } else if (CustomImgs.size() < 3)
            ToastUtil.toast2_bottom(this, "必须选择3张图片!!");
    }

    /**
     * 异步任务执行保存图片操作
     */
    class SaveTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... params) {  //三个点，代表可变参数
            int len = getRingDuring(FilePath);
            Log.i(TAG, "lenth  =====" + len);
            for (int i = 11; i > 1; i--) {
                VideoImgBean vb = new VideoImgBean();
                vb.setFilePath(saveImage(getVideoThumbnail(FilePath, len / i)));
                vb.setSelectFlag(false);
                fils.add(vb);
            }
            return null;
        }

        //主要是更新UI
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(Uri.fromFile(new File(FilePath)))
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(cover_img);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(0).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover1);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(1).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover2);
            if (isCancelled())
                return;
            Glide.with(getApplicationContext())
                    .load(fils.get(2).getFilePath())
                    .apply(getRequestOptions(true, 0, 0, false))
                    .into(video_cover3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(true);
        task = null;
    }
}
