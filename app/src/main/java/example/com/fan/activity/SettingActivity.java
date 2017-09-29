package example.com.fan.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.VersionBean;
import example.com.fan.fragment.MyFragment;
import example.com.fan.mylistener.VersionCheckListener;
import example.com.fan.utils.DownLoadUtils;
import example.com.fan.utils.GlideCacheUtil;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SynUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.dialog.AlertDialog;
import okhttp3.Call;

import static com.sina.weibo.sdk.utils.ImageUtils.isWifi;
import static example.com.fan.utils.IntentUtils.goHelpPage;
import static example.com.fan.utils.IntentUtils.goOutsidePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.Finish;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getVersionCode;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.utils.getVersionUtils.getVersionInfo;
import static example.com.fan.view.dialog.PhotoProgress.LoadingCancle;
import static example.com.fan.view.dialog.PhotoProgress.LoadingShow;

/**
 * Created by lian on 2017/6/26.
 */
public class SettingActivity extends InitActivity implements View.OnClickListener, VersionCheckListener {
    private static final String TAG = getTAG(SettingActivity.class);
    private TextView logout, clear_tv, up_tv, about_tv, disclaimer_tv, help_tv;
    private LinearLayout clear_cache, up_load, about_us, disclaimer, help;


    @Override
    protected void click() {
        logout.setOnClickListener(this);
        clear_cache.setOnClickListener(this);
        help.setOnClickListener(this);
        up_load.setOnClickListener(this);
        about_us.setOnClickListener(this);
        disclaimer.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.setting_activity_layout);
        setTitles(this, getRouString(R.string.system_setting));
        logout = f(R.id.logout);
        clear_cache = f(R.id.clear_cache);
        up_load = f(R.id.up_load);
        about_us = f(R.id.about_us);
        disclaimer = f(R.id.disclaimer);
        help = f(R.id.help);

        clear_tv = (TextView) clear_cache.findViewById(R.id.name_tv);
        clear_tv.setText(getRouString(R.string.clear));
        up_tv = (TextView) up_load.findViewById(R.id.name_tv);
        up_tv.setText(getRouString(R.string.upload));
        about_tv = (TextView) about_us.findViewById(R.id.name_tv);
        about_tv.setText(getRouString(R.string.about));
        disclaimer_tv = (TextView) disclaimer.findViewById(R.id.name_tv);
        disclaimer_tv.setText(getRouString(R.string.disclaimer));
        help_tv = (TextView) help.findViewById(R.id.name_tv);
        help_tv.setText(getRouString(R.string.help));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                if (SynUtils.LoginStatusQuery()) {
                    SPreferences.saveUserToken("");
                    if (MyFragment.fragment != null)
                        MyFragment.fragment.onUpDataUserInfo();
                    ToastUtil.toast2_bottom(this, "成功清除登录信息....");
                    finish();
                } else
                    ToastUtil.toast2_bottom(this, "您未登录!");
                break;
            case R.id.clear_cache:


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlideCacheUtil.clearImageAllCache(SettingActivity.this);
                    }
                }).start();
                ToastUtil.toast2_bottom(this, "成功清除缓存....");

                break;
            case R.id.help:
                goHelpPage(this);
                break;
            case R.id.up_load:
                getVersionInfo(getApplicationContext(), this);
                break;
            case R.id.about_us:
                goOutsidePage(SettingActivity.this, MzFinal.AS_WE, "关于我们");
                break;
            case R.id.disclaimer:
                goOutsidePage(SettingActivity.this, "", "免责声明");
                break;
        }
    }

    @Override
    public void onVersion(VersionBean vb) {
        int old = SynUtils.getVersionCode(getApplicationContext());

        if (vb.getAndroidVersion() > old) {
            LoadingShow(SettingActivity.this, false, getRouString(R.string.VersonUp));
            /**
             * 获取apk数据;
             */

            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.DOWNLOADPATH)
                    .addParams("version", String.valueOf(getVersionCode(SettingActivity.this)))
                    .addParams("channelCode", MyAppcation.CHANNEL)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(SettingActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    final String url = getJsonSring(response);
                                    if (!url.isEmpty()) {
                                        /**
                                         * 判断是否Wifi环境;
                                         */
                                        if (isWifi(SettingActivity.this)) {
                                            DownLoadUtils du = new DownLoadUtils(SettingActivity.this);
                                            du.download(url);
                                        } else {
                                            new AlertDialog(SettingActivity.this).builder().setTitle("提示").setCancelable(true).setMsg("检测到您不是Wifi环境,是否还继续下载?").setNegativeButton("下次再说", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    LoadingCancle();
                                                }
                                            }).setPositiveButton("下载", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    DownLoadUtils du = new DownLoadUtils(SettingActivity.this);
                                                    du.download(url);
                                                }
                                            }).show();
                                        }
                                    } else
                                        ToastUtil.toast2_bottom(SettingActivity.this, "没有获取到新版本下载路径");

                                } else {
                                    ToastUtil.ToastErrorMsg(SettingActivity.this, response, code);
                                    LoadingCancle();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
        } else {
            ToastUtil.toast2_bottom(this, "您已经是最新版本，无需更新!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onFail() {
        LoadingCancle();
    }
}
