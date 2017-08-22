package example.com.fan.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.utovr.player.UVEventListener;
import com.utovr.player.UVInfoListener;
import com.utovr.player.UVMediaPlayer;
import com.utovr.player.UVMediaType;
import com.utovr.player.UVPlayerCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.adapter.PlayerCommentAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.CommentBean;
import example.com.fan.bean.RandomBean;
import example.com.fan.bean.VideoPlayBean;
import example.com.fan.mylistener.PlayerControl;
import example.com.fan.mylistener.VideoController;
import example.com.fan.mylistener.editeListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.PlayerUtils;
import example.com.fan.utils.ShareUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.CommentEditPopupWindow;
import example.com.fan.view.dialog.AlertDialog;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonInt;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.KswitchWay;
import static example.com.fan.utils.SynUtils.ParseK;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouDrawable;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

public class PlayerActivity extends InitActivity implements UVPlayerCallBack, PlayerControl, View.OnClickListener, editeListener {
    private static final String TAG = getTAG(PlayerActivity.class);
    private UVMediaPlayer mMediaplayer = null;  // 媒体视频播放器
    private VideoController mCtrl = null;
    //        private String Path = "http://cache.utovr.com/201508270528174780.m3u8";
    private String Path = "";

    private boolean bufferResume = true;
    private boolean needBufferAnim = true;
    private ImageView imgBuffer;                // 缓冲动画
    private ImageView imgBack, panorama_img, glasses_img1, glasses_img2;
    private RelativeLayout rlParent = null;
    protected int CurOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private int SmallPlayH = 0;
    private boolean colseDualScreen = false;
    private boolean PATTERN = true;
    private TextView panorama_click, glasses_click;
    private LinearLayout glasses_img;
    private FrameLayout play_click_fl;
    public FrameLayout cover_framelayout;
    private ToggleButton tbtnGyro, tbtnDualScreen;
    //底部评论;
    private TextView comment_ed;
    private View top;
    private LinearLayout more_content_ll, player_bottom;
    private PlayerCommentAdapter adapter;
    private TextView video_title, player_number, video_name, attention_tv, collect_num, admire_num,share_num;
    private ImageView video_icon, cover_img, video_tool_imgFullscreen;
    private ListView listView;
    private FrameLayout collect_fl, admire_fl, share_fl;
    private String id;
    private List<CommentBean> commentlist;
    private String userId = "";
    private VideoPlayBean vb;
    private editeListener elistener;


    private void receive() {
        id = getIntent().getStringExtra("play_id");
    }

    @Override
    protected void click() {
        panorama_click.setOnClickListener(this);
        glasses_click.setOnClickListener(this);
        play_click_fl.setOnClickListener(this);
        comment_ed.setOnClickListener(this);
        collect_fl.setOnClickListener(this);
        share_fl.setOnClickListener(this);
        admire_fl.setOnClickListener(this);
        video_tool_imgFullscreen.setOnClickListener(this);
        attention_tv.setOnClickListener(this);
        video_icon.setOnClickListener(this);
        panorama_click.setOnClickListener(this);
        glasses_click.setOnClickListener(this);
        panorama_img.setOnClickListener(this);
        glasses_img.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        getVideoData(MzFinal.GETRANDOMVRVIDEOBYPAGE);
    }

    private void getVideoData(String url) {
        Show(PlayerActivity.this, "加载中", false, null);
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
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                vb = new Gson().fromJson(String.valueOf(ob), VideoPlayBean.class);
                                userId = vb.getJoinUserId();
                                video_title.setText(vb.getName());
                                player_number.setText("已播放" + vb.getSeeCount() + "次");
                                try {
                                    Glide.with(PlayerActivity.this).load(vb.getJoinUser().getHeadImgUrl()).centerCrop().override(150, 150).bitmapTransform(new CropCircleTransformation(PlayerActivity.this)).crossFade(200).into(video_icon);
                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }
                                video_name.setText(vb.getJoinUser().getName());
                                if (vb.getMcPublishVideoUrls().size() > 0) {
                                    if (vb.getMcPublishVideoUrls().get(0).getNeedMoney()) {
                                        if (MyAppcation.VipFlag)
                                            getAccredit(vb.getMcPublishVideoUrls().get(0).getPath());
                                        else {
                                            new AlertDialog(PlayerActivity.this).builder().setTitle("提示").setCancelable(true).setMsg("成为会员才能看哦，更多精彩细节等着你!").setNegativeButton("下次再说", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    finish();
                                                }
                                            }).setPositiveButton("成为会员", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    goPayPage(PlayerActivity.this);
                                                    finish();
                                                }
                                            }).show();
                                        }
                                    } else
                                        Path = vb.getMcPublishVideoUrls().get(0).getPath();

                                    collect_num.setText(KswitchWay(vb.getCollectionCount()));
                                    admire_num.setText(KswitchWay(vb.getLikesCount()));
                                    share_num.setText(KswitchWay(vb.getShareCount()));
                                } else {
                                    ToastUtil.toast2_bottom(PlayerActivity.this, "没有获取到视频地址！");
                                    finish();
                                }
//                        Path = "http://fns-video-public.oss-cn-hangzhou.aliyuncs.com/960p.mp4";
                                try {
                                    Glide.with(getApplicationContext()).load(vb.getCoverPath()).bitmapTransform(new BlurTransformation(PlayerActivity.this, 25)).crossFade(200).into(cover_img);
                                    Glide.with(getApplicationContext()).load(vb.getJoinUser().getHeadImgUrl()).centerCrop().override(250, 250).bitmapTransform(new BlurTransformation(PlayerActivity.this, 25)).crossFade(200).into(cover_img);
                                    Glide.with(getApplicationContext()).load(vb.getCoverPath()).override(350, 350).crossFade(200).into(panorama_img);
                                    Glide.with(getApplicationContext()).load(vb.getCoverPath()).centerCrop().override(350, 350).crossFade(200).into(glasses_img1);
                                    Glide.with(getApplicationContext()).load(vb.getCoverPath()).centerCrop().override(350, 350).crossFade(200).into(glasses_img2);
                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }
                                if (listView.getHeaderViewsCount() == 0) {
                                    listView.addHeaderView(top);
                                }
                                getComment(String.valueOf(5));
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);

                            Cancle();
                        } catch (Exception e) {

                        }
                    }
                });
        /**
         * 随机获取
         */


        OkHttpUtils
                .get()
                .url(MzFinal.URl + url)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                more_content_ll.removeAllViews();
                                for (int i = 0; i < ar.length(); i++) {
                                    RandomBean rb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), RandomBean.class);
                                    if (!rb.getId().equals(id))
                                        addHeader(rb);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    /**
     * 获取授权;
     *
     * @param path
     */
    private void getAccredit(final String path) {
        String url = path.substring(path.lastIndexOf("/") + 1, path.length());

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.VIDEOAUTHENTICATION)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("videoName", url)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Path = getJsonSring(response);
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    /**
     * 获取评论;
     *
     * @param type
     */
    private void getComment(String type) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("type", type);
        map.put("page", "0");
        map.put("pageSize", "50");

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYCOMMENT)
                .addParams(MzFinal.ID, id)
                .addParams(MzFinal.TYPE, type)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "50")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                commentlist.clear();
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    CommentBean com = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), CommentBean.class);
                                    commentlist.add(com);
                                }
                                Log.i(TAG, "ID======" + id);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter = new PlayerCommentAdapter(commentlist, PlayerActivity.this);
                                    listView.setAdapter(adapter);
                                }


                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void addHeader(final RandomBean rb) {
//        more_content_ll
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 140), ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        ll.setLayoutParams(lp);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

        lp1.rightMargin = DeviceUtils.dip2px(this, 5);
        ImageView im = new ImageView(this);
        try {
            Glide.with(getApplicationContext()).load(rb.getCoverPath()).centerCrop().override(600, 360).into(im);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im.setLayoutParams(lp1);
        ll.addView(im);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = rb.getId();
                getData();
//                goPlayerPage(PlayerActivity.this, rb.getId(), 5);
//                finish();
            }
        });

        TextView tv1 = new TextView(this);
        tv1.setText(rb.getName());
        int sizeOfText = (int) this.getResources().getDimension(R.dimen.size12);
        tv1.setTextSize(sizeOfText);
        tv1.setTextColor(getRouColors(R.color.black));
        tv1.setMaxEms(7);
        tv1.setSingleLine(true);
        tv1.setEllipsize(TextUtils.TruncateAt.END);
        ll.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("播放" + rb.getSeeCount() + "次");
        tv2.setTextColor(getRouColors(R.color.gray3));
        int sizeOfText1 = (int) this.getResources().getDimension(R.dimen.size9);
        tv2.setTextSize(sizeOfText1);
        ll.addView(tv2);
        more_content_ll.addView(ll);
    }

    private void initPlayer() {
        if (mMediaplayer != null) {
            if (PATTERN) {
                mCtrl.setPanorama();
            } else {
                boolean isScreen = !this.isDualScreenEnabled();
                if (isScreen) {
                    this.setDualScreenEnabled(isScreen);
                    tbtnDualScreen.setChecked(isScreen);
                    this.setGyroEnabled(true);
                    tbtnGyro.setChecked(true);
                    Log.i(TAG, "眼镜模式");
                }
            }

            this.toFullScreen();
            this.coverEnabled();
            mCtrl.VideoonResume();
        } else {
            //初始化播放器
            RelativeLayout rlPlayView = f(R.id.activity_rlPlayView);
            mMediaplayer = new UVMediaPlayer(PlayerActivity.this, rlPlayView);

            //将工具条的显示或隐藏交个SDK管理，也可自己管理
            RelativeLayout rlToolbar = f(R.id.activity_rlToolbar);
            RelativeLayout video_play_top_tools = f(R.id.video_play_top_tools);
            tbtnGyro = (ToggleButton) video_play_top_tools.findViewById(R.id.video_tool_tbtnGyro);
            tbtnDualScreen = (ToggleButton) video_play_top_tools.findViewById(R.id.video_tool_tbtnVR);
            //sdk setToolbar方法最多容纳3个view;
            mMediaplayer.setToolbar(video_play_top_tools, rlToolbar, null);
            mCtrl = new VideoController(rlToolbar, this, true, video_play_top_tools);
            changeOrientation(false);
            this.coverEnabled();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaplayer != null) {
            mMediaplayer.onResume(PlayerActivity.this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaplayer != null) {
            mMediaplayer.onPause();
        }
    }

    @Override
    public void onDestroy() {
        elistener = null;
        OkHttpUtils.getInstance().cancelTag(this);
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeOrientation(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    private void changeOrientation(boolean isLandscape) {
        if (rlParent == null) {
            return;
        }
        if (isLandscape) {
            CurOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            rlParent.setLayoutParams(lp);
            if (colseDualScreen && mMediaplayer != null) {
                mCtrl.setDualScreenEnabled(true);
            }
            colseDualScreen = false;
            mCtrl.changeOrientation(true, 0);
        } else {
            CurOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getSmallPlayHeight();
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, SmallPlayH);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowWidth(this) * 9 / 16);
            rlParent.setLayoutParams(lp);
            if (mMediaplayer != null && mMediaplayer.isDualScreenEnabled()) {
                mCtrl.setDualScreenEnabled(false);
                colseDualScreen = true;
            }
            mCtrl.changeOrientation(false, 0);
        }
    }

    @Override
    protected void init() {
        setContentView(R.layout.player_activity);
        receive();
        elistener = this;
        commentlist = new ArrayList<>();
        rlParent = f(R.id.activity_rlParent);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowWidth(this) * 9 / 16);
        rlParent.setLayoutParams(lp);

        imgBuffer = f(R.id.activity_imgBuffer);
        imgBack = f(R.id.activity_imgBack);
        panorama_click = f(R.id.panorama_click);
        glasses_click = f(R.id.glasses_click);
        listView = f(R.id.listView);
        comment_ed = f(R.id.comment_ed);

        player_bottom = f(R.id.player_bottom);
        cover_img = f(R.id.cover_img);
        collect_num = f(R.id.collect_num);
        admire_num = f(R.id.admire_num);

        share_num = f(R.id.share_num);

        cover_framelayout = f(R.id.cover_framelayout);

        play_click_fl = f(R.id.play_click_fl);

        collect_fl = f(R.id.collect_fl);
        admire_fl = f(R.id.admire_fl);
        share_fl = f(R.id.share_fl);
        int w = (int) (DeviceUtils.getWindowWidth(this) * 2 / 3);

        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(w, w * 9 / 16);
        FrameLayout.LayoutParams fp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, w * 9 / 16);

        fp1.gravity = Gravity.CENTER;
        fp1.leftMargin = DeviceUtils.dip2px(this, 10);
        fp1.rightMargin = DeviceUtils.dip2px(this, 10);
        fp.gravity = Gravity.CENTER;
        panorama_img = f(R.id.panorama_img);
        glasses_img = f(R.id.glasses_img);

        glasses_img.setLayoutParams(fp1);

        glasses_img.setGravity(Gravity.CENTER);
        glasses_img.setOrientation(LinearLayout.HORIZONTAL);

        panorama_img.setLayoutParams(fp);
        panorama_img.setScaleType(ImageView.ScaleType.FIT_XY);

        glasses_img1 = f(R.id.glasses_img1);
        glasses_img2 = f(R.id.glasses_img2);


        LinearLayout.LayoutParams glp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        glasses_img1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        glasses_img2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        glasses_img1.setLayoutParams(glp);
        glasses_img2.setLayoutParams(glp);

        video_tool_imgFullscreen = f(R.id.video_tool_imgFullscreen);

        top = LayoutInflater.from(this).inflate(R.layout.player_top_layout, null);
        more_content_ll = (LinearLayout) top.findViewById(R.id.more_content_ll);
        video_title = (TextView) top.findViewById(R.id.video_title);
        player_number = (TextView) top.findViewById(R.id.player_number);
        video_name = (TextView) top.findViewById(R.id.video_name);
        video_icon = (ImageView) top.findViewById(R.id.video_icon);
        attention_tv = (TextView) top.findViewById(R.id.attention_tv);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

    }

    /**
     * 释放播放器资源;
     */
    public void releasePlayer() {
        if(mMediaplayer!=null){
            mMediaplayer.release();
            mMediaplayer = null;
        }
    }

    @Override
    public void createEnv() {
        try {
            // 创建媒体视频播放器
            mMediaplayer.initPlayer();
            mMediaplayer.setListener(mListener);
            mMediaplayer.setInfoListener(mInfoListener);
            //如果是网络MP4，可调用 mCtrl.startCachePro();mCtrl.stopCachePro();
//            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_M3U8, Path);
            mCtrl.VideoInit(PATTERN);
            player_bottom.setVisibility(View.GONE);
            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Path);
        } catch (Exception e) {
            Log.e("utovr", e.getMessage(), e);
        }
    }

    @Override
    public void updateProgress(long position) {
        if (mCtrl != null) {
            mCtrl.updateCurrentPosition();
        }
    }

    private UVEventListener mListener = new UVEventListener() {
        @Override
        public void onStateChanged(int playbackState) {
            Log.i("utovr", "+++++++ playbackState:" + playbackState);
            switch (playbackState) {
                case UVMediaPlayer.STATE_PREPARING:
                    break;
                case UVMediaPlayer.STATE_BUFFERING:
                    if (needBufferAnim && mMediaplayer != null && mMediaplayer.isPlaying()) {
                        bufferResume = true;
                        PlayerUtils.setBufferVisibility(imgBuffer, true);
                    }
                    break;
                case UVMediaPlayer.STATE_READY:
                    // 设置时间和进度条
                    mCtrl.setInfo();
                    if (bufferResume) {
                        bufferResume = false;
                        PlayerUtils.setBufferVisibility(imgBuffer, false);
                    }
                    break;
                case UVMediaPlayer.STATE_ENDED:
                    //这里是循环播放，可根据需求更改
                    mMediaplayer.replay();
                    break;
                case UVMediaPlayer.TRACK_DISABLED:
                case UVMediaPlayer.TRACK_DEFAULT:
                    break;
            }
        }

        @Override
        public void onError(Exception e, int ErrType) {
            Toast.makeText(PlayerActivity.this, PlayerUtils.getErrMsg(ErrType), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoSizeChanged(int width, int height) {
        }

    };

    private UVInfoListener mInfoListener = new UVInfoListener() {
        @Override
        public void onBandwidthSample(int elapsedMs, long bytes, long bitrateEstimate) {
        }

        @Override
        public void onLoadStarted() {
        }

        @Override
        public void onLoadCompleted() {
            if (bufferResume) {
                bufferResume = false;
                PlayerUtils.setBufferVisibility(imgBuffer, false);
            }
            if (mCtrl != null) {
                mCtrl.updateBufferProgress();
            }

        }
    };

    @Override
    public long getDuration() {
        return mMediaplayer != null ? mMediaplayer.getDuration() : 0;
    }

    @Override
    public long getBufferedPosition() {
        return mMediaplayer != null ? mMediaplayer.getBufferedPosition() : 0;
    }

    @Override
    public long getCurrentPosition() {
        return mMediaplayer != null ? mMediaplayer.getCurrentPosition() : 0;
    }

    @Override
    public void setGyroEnabled(boolean val) {
        if (mMediaplayer != null)
            mMediaplayer.setGyroEnabled(val);
    }

    @Override
    public boolean isGyroEnabled() {
        return mMediaplayer != null ? mMediaplayer.isGyroEnabled() : false;
    }

    @Override
    public boolean isDualScreenEnabled() {
        return mMediaplayer != null ? mMediaplayer.isDualScreenEnabled() : false;
    }

    @Override
    public void toolbarTouch(boolean start) {
        if (mMediaplayer != null) {
            if (true) {
                mMediaplayer.cancelHideToolbar();
            } else {
                mMediaplayer.hideToolbarLater();
            }
        }
    }

    @Override
    public void coverEnabled() {
        if (cover_framelayout.getVisibility() == View.GONE)
            cover_framelayout.setVisibility(View.VISIBLE);
        else
            cover_framelayout.setVisibility(View.GONE);
    }

    @Override
    public void pause() {
        if (mMediaplayer != null && mMediaplayer.isPlaying()) {
            mMediaplayer.pause();
        }
    }

    @Override
    public void seekTo(long positionMs) {
        if (mMediaplayer != null)
            mMediaplayer.seekTo(positionMs);
    }

    @Override
    public void play() {
        if (mMediaplayer != null && !mMediaplayer.isPlaying()) {
            mMediaplayer.play();
            player_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDualScreenEnabled(boolean val) {
        if (mMediaplayer != null)
            mMediaplayer.setDualScreenEnabled(val);
    }

    @Override
    public void toFullScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    /* 大小屏切 可以再加上 ActivityInfo.SCREEN_ORIENTATION_SENSOR 效果更佳**/

    private void back() {
        if (CurOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            finish();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            this.coverEnabled();
            this.pause();
            player_bottom.setVisibility(View.VISIBLE);
        }
    }

    private void getSmallPlayHeight() {
        if (this.SmallPlayH != 0) {
            return;
        }
        int ScreenW = getWindowManager().getDefaultDisplay().getWidth();
        int ScreenH = getWindowManager().getDefaultDisplay().getHeight();
        if (ScreenW > ScreenH) {
            int temp = ScreenW;
            ScreenW = ScreenH;
            ScreenH = temp;
        }
        SmallPlayH = ScreenW * ScreenW / ScreenH;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panorama_click:
                select(panorama_click);
                break;
            case R.id.glasses_click:
                select(glasses_click);
                break;
            case R.id.play_click_fl:
//                if (!Path.isEmpty()) {
//                    initPlayer();
//                }
                break;
            case R.id.panorama_img:
                if (!Path.isEmpty()) {
                    initPlayer();
                }
                break;
            case R.id.glasses_img:
                if (!Path.isEmpty()) {
                    initPlayer();
                }
                break;
            case R.id.comment_ed:
                CommentEditPopupWindow.ScreenPopupWindow(comment_ed, getApplicationContext(), elistener);
                break;
            case R.id.collect_fl:
                CollectVideo();
                break;
            case R.id.admire_fl:
                Admire();
                break;
            case R.id.video_icon:
                if (!userId.isEmpty())
                    goHomePage(this, userId);
                break;
            case R.id.attention_tv:
                if (!Path.isEmpty() && !userId.isEmpty())
                    Attention(userId);
                break;
            case R.id.share_fl:
                if (!userId.isEmpty())
                    ShareUtils.ShareApp(PlayerActivity.this, userId, vb.getName(), vb.getInfo(), vb.getId());
                break;
            case R.id.video_tool_imgFullscreen:
                if (mCtrl != null) {
                    toFullScreen();
                    coverEnabled();
                }
                break;

        }
    }

    /**
     * 关注;
     *
     * @param user_id
     */
    private void Attention(String user_id) {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.FOLLOWUSER)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, user_id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonInt(response)) {
                                    case 0:
                                        ToastUtil.toast2_bottom(PlayerActivity.this, "成功取消关注");
                                        attention_tv.setText(getRouString(R.string.attention_she));
                                        break;
                                    case 1:
                                        ToastUtil.toast2_bottom(PlayerActivity.this, "关注成功!");
                                        attention_tv.setText("取消关注");
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        }

    private void Admire() {


        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.LIKEVIDEO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonSring(response)) {
                                    case "1":
                                        ToastUtil.toast2_bottom(PlayerActivity.this, "点赞成功!");
                                        admire_num.setText(ParseK(admire_num.getText().toString(), true));
                                        break;
                                    case "0":
                                        ToastUtil.toast2_bottom(PlayerActivity.this, "取消点赞!");
                                        admire_num.setText(ParseK(admire_num.getText().toString(), false));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    /**
     * 视频收藏;
     */
    private void CollectVideo() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.COLLECTIONVIDEO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonSring(response)) {
                                    case "1":
                                        ToastUtil.toast2_bottom(PlayerActivity.this, "收藏成功!");
                                        collect_num.setText(ParseK(collect_num.getText().toString(), true));
                                        break;
                                    case "0":
                                        ToastUtil.toast2_bottom(PlayerActivity.this, "取消收藏!");
                                        collect_num.setText(ParseK(collect_num.getText().toString(), false));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void select(TextView tv) {
        if (tv.equals(panorama_click)) {
            panorama_click.setBackground(getRouDrawable(R.drawable.white_corners));
            panorama_click.setTextColor(getRouColors(R.color.gray3));
            glasses_click.setBackground(null);
            glasses_click.setTextColor(getRouColors(R.color.white));
            PATTERN = true;
            panorama_img.setVisibility(View.VISIBLE);
            glasses_img.setVisibility(View.GONE);
        }
        if (tv.equals(glasses_click)) {
            glasses_click.setBackground(getRouDrawable(R.drawable.white_corners));
            glasses_click.setTextColor(getRouColors(R.color.gray3));
            panorama_click.setBackground(null);
            panorama_click.setTextColor(getRouColors(R.color.white));
            PATTERN = false;
            panorama_img.setVisibility(View.GONE);
            glasses_img.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showEditePopup(String content) {

        /**
         * 发送评论;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.ADDVRVIDEOCOMMENT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, id)
                .addParams(MzFinal.CONTENT, content)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(PlayerActivity.this, "评论成功!");
                                getComment(String.valueOf(5));
                            } else
                                ToastUtil.ToastErrorMsg(PlayerActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
