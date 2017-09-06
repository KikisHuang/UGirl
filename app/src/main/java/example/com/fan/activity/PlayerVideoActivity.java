package example.com.fan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.adapter.PlayerCommentAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.CommentBean;
import example.com.fan.bean.RandomBean;
import example.com.fan.bean.VideoPlayBean;
import example.com.fan.mylistener.editeListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ShareUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.CommentEditPopupWindow;
import example.com.fan.view.dialog.AlertDialog;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonInt;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.JsonUtils.getKeyMap;
import static example.com.fan.utils.SynUtils.Finish;
import static example.com.fan.utils.SynUtils.KswitchWay;
import static example.com.fan.utils.SynUtils.ParseK;
import static example.com.fan.utils.SynUtils.getRouColors;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/7/1.
 */
public class PlayerVideoActivity extends AppCompatActivity implements View.OnClickListener, editeListener {
    private static final String TAG = getTAG(PlayerVideoActivity.class);
    private JCVideoPlayerStandard mJcVideoPlayerStandard;
    private ListView listView;
    //顶部评论;
    private TextView comment_ed, share_num;
    private FrameLayout collect_fl, admire_fl, share_fl;
    private String id;
    private PlayerCommentAdapter adapter;
    private View top;
    private TextView video_title, player_number, video_name, attention_tv, collect_num, admire_num;
    private ImageView video_icon, activity_imgBack;
    private LinearLayout more_content_ll;
    private List<CommentBean> commentlist;
    private String Path = "";
    private String userId = "";
    private VideoPlayBean vb;
    private editeListener elistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity_layout);
        receive();
        init();
        click();
        getData();
    }

    private void click() {
        comment_ed.setOnClickListener(this);
        collect_fl.setOnClickListener(this);
        admire_fl.setOnClickListener(this);
        share_fl.setOnClickListener(this);
        activity_imgBack.setOnClickListener(this);
        video_icon.setOnClickListener(this);
        attention_tv.setOnClickListener(this);
    }

    private void getData() {
        getVideoData(MzFinal.GETRANDOMVIDEOBYPAGE);
    }

    private void init() {
        elistener = this;
        collect_fl = (FrameLayout) findViewById(R.id.collect_fl);
        admire_fl = (FrameLayout) findViewById(R.id.admire_fl);
        share_fl = (FrameLayout) findViewById(R.id.share_fl);
        listView = (ListView) findViewById(R.id.listView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowWidth(this) * 9 / 16);
        mJcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.jc_video);
        mJcVideoPlayerStandard.setLayoutParams(lp);
        comment_ed = (TextView) findViewById(R.id.comment_ed);
        commentlist = new ArrayList<>();
        collect_num = (TextView) findViewById(R.id.collect_num);
        share_num = (TextView) findViewById(R.id.share_num);
        activity_imgBack = (ImageView) findViewById(R.id.activity_imgBack);
        admire_num = (TextView) findViewById(R.id.admire_num);
        top = LayoutInflater.from(this).inflate(R.layout.player_top_layout, null);

        more_content_ll = (LinearLayout) top.findViewById(R.id.more_content_ll);
        video_title = (TextView) top.findViewById(R.id.video_title);
        player_number = (TextView) top.findViewById(R.id.player_number);
        video_name = (TextView) top.findViewById(R.id.video_name);
        video_icon = (ImageView) top.findViewById(R.id.video_icon);

        attention_tv = (TextView) top.findViewById(R.id.attention_tv);

    }

    private void receive() {
        id = getIntent().getStringExtra("play_id");
    }

    private void getComment(String type) {

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
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
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
                                    adapter = new PlayerCommentAdapter(commentlist, PlayerVideoActivity.this);
                                    listView.setAdapter(adapter);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getVideoData(String url) {
        Show(PlayerVideoActivity.this, "加载中", false, null);
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
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
                        Cancle();
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
                                    Glide.with(PlayerVideoActivity.this).load(vb.getJoinUser().getHeadImgUrl()).centerCrop().override(150, 150).bitmapTransform(new CropCircleTransformation(PlayerVideoActivity.this)).crossFade(200).into(video_icon);
                                } catch (Exception e) {
                                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                }
                                video_name.setText(vb.getJoinUser().getName());
                                if (vb.getMcPublishVideoUrls().size() > 0) {
                                    /**
                                     * 判断视频是否是收费;
                                     */
                                    if (vb.getMcPublishVideoUrls().get(0).getNeedMoney()) {
                                        if (MyAppcation.VipFlag)
                                            getAccredit(vb.getMcPublishVideoUrls().get(0).getPath(), vb);
                                        else {

                                            new AlertDialog(PlayerVideoActivity.this).builder().setTitle("提示").setCancelable(false).setMsg("成为会员才能看哦，更多精彩细节等着你!\n\n").setNegativeButton("下次再说", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Finish(PlayerVideoActivity.this);
                                                }
                                            }).setPositiveButton("成为会员", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    goPayPage(PlayerVideoActivity.this);
                                                    Finish(PlayerVideoActivity.this);
                                                }
                                            }).show();
                                        }

                                    } else {
                                        //设置标题;
                                        mJcVideoPlayerStandard.setUp(vb.getMcPublishVideoUrls().get(0).getPath()
                                                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                                        Path = vb.getMcPublishVideoUrls().get(0).getPath();
                                    }

                                    collect_num.setText(KswitchWay(vb.getCollectionCount()));
                                    admire_num.setText(KswitchWay(vb.getLikesCount()));
                                    share_num.setText(KswitchWay(vb.getShareCount()));
                                    try {
                                        Glide.with(getApplicationContext()).load(vb.getCoverPath()).override(1920, 1080).crossFade(200).into(mJcVideoPlayerStandard.thumbImageView);
                                    } catch (Exception e) {
                                        Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                                    }
                                } else {
                                    ToastUtil.toast2_bottom(PlayerVideoActivity.this, "异常，没有获取到视频地址！");
                                    Finish(PlayerVideoActivity.this);
                                }

                                if (listView.getHeaderViewsCount() == 0)
                                    listView.addHeaderView(top);
                                //先addHead再setAdapter否则4.4以下会报错;
                                getComment(String.valueOf(4));
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);

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
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
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
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    /**
     * 获取授权加密;
     *
     * @param path
     * @param vb
     */
    private void getAccredit(final String path, final VideoPlayBean vb) {
        final String url = path.substring(path.lastIndexOf("/") + 1, path.length());
        Map<String, String> map = getKeyMap();
        map.put("videoName", url);
        map.put("id", id);

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
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Path = getJsonSring(response);
                                //设置标题;
                                mJcVideoPlayerStandard.setUp(getJsonSring(response)
                                        , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
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

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "goPlayerPage");
                id = rb.getId();
                getData();
//                goPlayerPage(PlayerVideoActivity.this, rb.getId(), 4);
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


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        elistener = null;
        JCVideoPlayer.clearSavedProgress(this, Path);
        if (mJcVideoPlayerStandard != null) {
            mJcVideoPlayerStandard.removeAllViews();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_ed:
                CommentEditPopupWindow.ScreenPopupWindow(comment_ed, getApplicationContext(), elistener);
                break;
            case R.id.collect_fl:
                CollectVideo();
                break;
            case R.id.admire_fl:
                Admire();
                break;
            case R.id.activity_imgBack:
                Finish(PlayerVideoActivity.this);
                break;
            case R.id.attention_tv:
                if (!Path.isEmpty() && !userId.isEmpty())
                    Attention(userId);
                break;
            case R.id.video_icon:
                if (!userId.isEmpty())
                    goHomePage(this, userId);
                break;
            case R.id.share_fl:
                if (!userId.isEmpty())
                    ShareUtils.ShareApp(this, userId, vb.getName(), vb.getInfo(), vb.getId());
                break;
        }
    }

    /**
     * 关注;
     *
     * @param user_id
     */
    private void Attention(String user_id) {

        Map<String, String> map = getKeyMap();
        map.put("id", user_id);
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
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonInt(response)) {
                                    case 0:
                                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "成功取消关注");
                                        attention_tv.setText(getRouString(R.string.attention_she));
                                        break;
                                    case 1:
                                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "关注成功!");
                                        attention_tv.setText("取消关注");
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
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
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonSring(response)) {
                                    case "1":
                                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "收藏成功!");
                                        collect_num.setText(ParseK(collect_num.getText().toString(), true));
                                        break;
                                    case "0":
                                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "取消收藏!");
                                        collect_num.setText(ParseK(collect_num.getText().toString(), false));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
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
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                switch (getJsonSring(response)) {
                                    case "1":
                                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "点赞成功!");
                                        admire_num.setText(ParseK(admire_num.getText().toString(), true));
                                        break;
                                    case "0":
                                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "取消点赞!");
                                        admire_num.setText(ParseK(admire_num.getText().toString(), false));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    @Override
    public void showEditePopup(String content) {
        /**
         * 发送评论;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.ADDVIDEOCOMMENT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.CONTENT, content)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PlayerVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(PlayerVideoActivity.this, "评论成功!");
                                getComment(String.valueOf(4));
                            } else
                                ToastUtil.ToastErrorMsg(PlayerVideoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
