package example.com.fan;

import android.app.Application;
import android.util.Log;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.socialize.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import example.com.fan.base.sign.CrashHandler;
import example.com.fan.base.sign.save.SPreferences;
import okhttp3.OkHttpClient;

import static example.com.fan.utils.SynUtils.JpushInit;
import static example.com.fan.utils.SynUtils.getOverID;

/**
 * Created by lian on 2017/5/2.
 */
public class MyAppcation extends Application {
    //    private static final String TAG = getTAG(MyAppcation.class);
    private static final String TAG = "MyAppcation";
    public static String CHANNEL;
    public static String myInvitationCode;
    public static String UserIcon;
    public static boolean VipFlag;
    public static CrashHandler crashHandler;
    public static String pkName = "";
    public static String QQID = "";
    public static String WECHATID = "";
    public static String ALIID = "";


    /**
     * 内存泄露检测;
     */
//    private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        MyAppcation application = (MyAppcation) context
//                .getApplicationContext();
//        return application.refWatcher;
//    }
    @Override
    public void onCreate() {
        super.onCreate();
//        refWatcher = LeakCanary.install(this);
        /**
         * 全局异常捕获方法;
         */
//      crashHandler = CrashHandler.getInstance();
//      crashHandler.init(getApplicationContext());
        pkName = getOverID(pkName, this);

        Log.i(TAG, "pk ===========" + pkName);
        OkHttpInit();
        JpushInit(this);
//        SophixInit();

        CHANNEL = AnalyticsConfig.getChannel(getApplicationContext());

        Log.i(TAG, "CHANNEL ====" + CHANNEL);
        SPreferences.setContext(getApplicationContext());
        {
            /**
             * 参数一: appID;
             * 参数二: key;
             */
            //尤女郎 key;
           PlatformConfig.setWeixin(WECHATID, "4bcde8ce9e646833395aae492d93bbc8");
           PlatformConfig.setQQZone(QQID, "63e5c4ab1c476fa9204c8504fa2338bd");

            //尤女映画 key;
//            PlatformConfig.setWeixin(WECHATID, "db426a9829e4b49a0dcac7b4162da6b6");
//            PlatformConfig.setQQZone(QQID, "5021f5bba7807a82e424d47a3190720b");

        }
    }


/*    private void SophixInit() {
        SophixManager.getInstance().setContext(this)
                .setAppVersion(getVersionName(this))
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG,"Sophix --- 补丁成功!!! CODE ==="+code);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            Log.i(TAG,"Sophix --- 新补丁生效需要重启!!! CODE ==="+code);
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            Log.i(TAG,"Sophix --- 内部引擎异常!!! CODE ==="+code);
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            // SophixManager.getInstance().cleanPatches();
                        } else {
                            Log.i(TAG,"Sophix --- 其他错误信息!!! CODE ==="+code);
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }*/

    /**
     * OkHttp初始化;
     */
    private void OkHttpInit() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

}
