package example.com.fan.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.PageTopBean;
import example.com.fan.bean.VideoPlayBean;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.PaytwoPopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.BannerUtils.goBannerPage;
import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goOutsidePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.JsonUtils.getKeyMap;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/10/23.
 */
public class PrivateVideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private JZVideoPlayerStandard mJcVideoPlayerStandard;
    private VideoPlayBean vb;
    private String Path = "";
    private String id = "";
    private FrameLayout adv_layout;
    private ImageView adv_img;
    private ImageView close_img;
    private static final String TAG = getTAG(PrivateVideoPlayerActivity.class);
    private Handler handler;
    private int time = 5;
    private Timer tm;
    private boolean adShow = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_video_player_layout);
        init();
        getAdv();
    }

    private void getAdv() {
        /**
         * 底部广告接口;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "9")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivateVideoPlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0) {
                                    Hand();
                                    adShow = true;
                                    adv_layout.setVisibility(View.VISIBLE);
                                    final PageTopBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(0)), PageTopBean.class);
                                    Glide.with(PrivateVideoPlayerActivity.this).load(rb.getImgUrl()).into(adv_img);
                                    adv_img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goBannerPage(PrivateVideoPlayerActivity.this, rb.getType(), rb.getHttpUrl(), rb.getValue());
                                        }
                                    });

                                }
                                getVideoData();
                            }
                        } catch (Exception e) {
                            getVideoData();
                        }
                    }
                });
    }

    private void init() {

        mJcVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.jc_video);
        adv_layout = (FrameLayout) findViewById(R.id.adv_layout);
        adv_img = (ImageView) findViewById(R.id.adv_img);
        int w = (int) (DeviceUtils.getWindowWidth(this) * 1.5 / 2);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(w, w);
        fl.setMargins(0,12,12,0);
        adv_img.setLayoutParams(fl);
        close_img = (ImageView) findViewById(R.id.close_img);
        close_img.setOnClickListener(this);
        id = getIntent().getStringExtra("private_play_id");
    }

    private void Hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (!Path.isEmpty()) {
                            adv_layout.setVisibility(View.GONE);
                            mJcVideoPlayerStandard.startVideo();
                            if (tm != null) {
                                tm.cancel();
                                tm = null;
                            }
                        }
                        break;
                }
            }
        };
    }

    private void getVideoData() {
        Show(PrivateVideoPlayerActivity.this, "获取播放路径", false, null);
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.VIDEODETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivateVideoPlayerActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                vb = new Gson().fromJson(String.valueOf(ob), VideoPlayBean.class);

                                if (vb.getMcPublishVideoUrls().size() > 0 && vb.getMcSettingPublishType() != null) {

                                    if (vb.getMcSettingPublishType().getTypeFlag() == -3)
                                        getAccredit(vb.getMcPublishVideoUrls().get(0).getPath(), MzFinal.PRIVATEVIDEOAUTHENTICATION);
                                    if (adShow)
                                        TimerInit();
                                    try {
                                        Glide.with(getApplicationContext()).load(vb.getCoverPath()).apply(getRequestOptions(false, 1920, 1080, false)).into(mJcVideoPlayerStandard.thumbImageView);
                                    } catch (Exception e) {
                                        Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                    }
                                } else {
                                    ToastUtil.toast2_bottom(PrivateVideoPlayerActivity.this, "异常，没有获取到视频地址！");
                                    finish();
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PrivateVideoPlayerActivity.this, response, code);

                        } catch (Exception e) {
                            ToastUtil.toast2_bottom(PrivateVideoPlayerActivity.this, "获取不到该视频路径");
                            if (MyAppcation.crashHandler != null)
                                MyAppcation.crashHandler.uncaughtException(new Thread(), e);
                            Cancle();
                            finish();
                        }
                    }
                });
    }

    private void TimerInit() {

        tm = new Timer();
        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                if (time == 0) {

                } else {
                    time--;
                    handler.sendEmptyMessage(time);
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
        JZVideoPlayer.clearSavedProgress(this, Path);
        if (mJcVideoPlayerStandard != null) {
            mJcVideoPlayerStandard.removeAllViews();
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
            if (tm != null) {
                tm.cancel();
                tm = null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    /**
     * 获取授权加密;
     *
     * @param path
     * @param u
     */
    private void getAccredit(final String path, final String u) {
        final String url = path.substring(path.lastIndexOf("/") + 1, path.length());
        Map<String, String> map = getKeyMap();
        map.put("videoName", url);
        map.put("id", id);

        OkHttpUtils
                .get()
                .url(MzFinal.URl + u)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("videoName", url)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivateVideoPlayerActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (cleanNull(getJsonSring(response)))
                                    Pay();
                                else {
                                    Path = getJsonSring(response);
                                    //设置标题;
                                    mJcVideoPlayerStandard.setUp(getJsonSring(response)
                                            , 0, "");

                                }
                            } else if (code == 0)
                                Pay();
                            Cancle();
                        } catch (Exception e) {
                            Log.i(TAG, "Error ===" + e);
                            Cancle();
                        }
                    }
                });

    }

    private void Pay() {
        PaytwoPopupWindow pyp = new PaytwoPopupWindow(PrivateVideoPlayerActivity.this, String.valueOf(vb.getPrice()), vb.getId(), 1, null);
        pyp.ScreenPopupWindow(mJcVideoPlayerStandard);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                if (!Path.isEmpty()) {
                    adv_layout.setVisibility(View.GONE);
                    mJcVideoPlayerStandard.startVideo();
                    if (tm != null) {
                        tm.cancel();
                        tm = null;
                    }
                }
                break;

        }
    }
}
