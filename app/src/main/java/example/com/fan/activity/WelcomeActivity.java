package example.com.fan.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import example.com.fan.R;
import example.com.fan.mylistener.PositionAddListener;
import example.com.fan.utils.AnimationUtil;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MyGlideModule;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.bean.ServerCode.getCodeStatusMsg;
import static example.com.fan.utils.IntentUtils.goMainPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.getIMEI;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getVersionName;


/**
 * Created by lian on 2017/5/19.
 * 欢迎页面;
 */
public class WelcomeActivity extends InitActivity implements PositionAddListener {
    private static final String TAG = getTAG(WelcomeActivity.class);
    private ImageView login_img;
    private TextView lead_tv;
    private LinearLayout login_ll;
    private ImageView welcome_img;
    private ImageView welcome_img2;
    private Handler handler;
    private int time = 3;
    private Timer tm;
    private Bitmap bitmap = null;
    public static PositionAddListener plistener;
    private Runnable mrunnable;

    private void Hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        lead_tv.setText("点击图标跳过 " + time + "秒");
                        break;
                    case 1:
                        lead_tv.setText("点击图标跳过 " + time + "秒");
                        break;
                    case 0:
                        lead_tv.setText("点击图标跳过 " + time + "秒");
                        startAnima();
                        break;
                    case 9:

                        break;
                    case 99:
                        welcome_img2.setVisibility(View.GONE);
                        bitmap = (Bitmap) msg.obj;    //在handler中接受从子线程发回来的数据
                        welcome_img.setImageBitmap(bitmap);
                        login_img.setEnabled(true);
                        startAnima2();
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
    }

    private void startAnima2() {
        welcome_img.startAnimation(AnimationUtil.WelcomeAnimathree());

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
        }, 1500, 1000);

    }

    @Override
    protected void click() {
        login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tm != null) {
                    tm.cancel();
                    tm = null;
                }
                startAnima();
            }
        });
    }

    private void startAnima() {
        login_img.setEnabled(false);
        lead_tv.setVisibility(View.INVISIBLE);
        TranslateAnimation ta = AnimationUtil.WelcomeAnima();
        login_ll.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                RotateAnimation aa = AnimationUtil.WelcomeAnimatwo();
                login_img.startAnimation(aa);
                aa.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        end();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void end() {
        try {
            goMainPage(this, getIntent().getStringExtra("wlecome_type"), getIntent().getStringExtra("wlecome_info_id"));
        } catch (Exception e) {
            goMainPage(this, "", "");
        }
    }

    private void getData() {

        OkHttpUtils
                .post()
                .url(MzFinal.URl + MzFinal.GETWELCOMEIMGURL)
                .addParams("IMEI", getIMEI(this))
                .addParams("Phone",android.os.Build.BRAND)
                .addParams("Molde",android.os.Build.MODEL)
                .addParams("Android_Version",android.os.Build.VERSION.RELEASE)
                .addParams("VersionName",getVersionName(this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(WelcomeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Glide.with(getApplicationContext()).load(new JSONObject(response).optString("data")).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        handler.post(mrunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                Message message = new Message();
                                                message.what = 99;
                                                message.obj = resource;
                                                handler.sendMessage(message);
                                            }
                                        });
                                    }
                                });
                            } else {
                                ToastUtil.toast2_bottom(WelcomeActivity.this, getCodeStatusMsg(code));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void init() {
        setContentView(R.layout.welcome_layout);
        GlideModuleConfig();
        plistener = this;
        login_img = f(R.id.login_img);
        welcome_img = f(R.id.welcome_img);
        welcome_img2 = f(R.id.welcome_img2);
        login_ll = f(R.id.login_ll);
        lead_tv = f(R.id.lead_tv);
        login_img.setEnabled(false);
    }

    /**
     * Glide图片质量全局配置(根据手机分辨率来判断显示的质量);
     */
    private void GlideModuleConfig() {
        MyGlideModule GlideConfig = new MyGlideModule();
        GlideBuilder builder = new GlideBuilder(this);
        int width = DeviceUtils.getRatio(this, true);
        int height = DeviceUtils.getRatio(this, false);
        Log.i(TAG, "Phone Ratio ===" + height + " x " + width);
        if (width >= 1080 && height >= 1920) {
            builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
            Log.i(TAG, "PREFER_ARGB_8888");
        } else {
            builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
            Log.i(TAG, "PREFER_RGB_565");
        }
        GlideConfig.applyOptions(this, builder);
    }

    @Override
    protected void initData() {
        Hand();
        getData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        plistener = null;

        if (bitmap != null && !bitmap.isRecycled()) {
            welcome_img.setImageBitmap(null);
            handler.removeCallbacks(mrunnable);
            handler.removeCallbacksAndMessages(null);
            handler = null;
            bitmap.recycle();
            bitmap = null;
            if (tm != null) {
                tm.cancel();
                tm = null;
            }
        }
        System.gc();
//        MyAppcation.getRefWatcher(this).watch(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onIncrease() {

        finish();
    }

}
