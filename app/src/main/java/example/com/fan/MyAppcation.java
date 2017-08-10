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

import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.utils.MzFinal;
import okhttp3.OkHttpClient;

import static example.com.fan.utils.SynUtils.JpushInit;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/2.
 */
public class MyAppcation extends Application {
    private static final String TAG = getTAG(MyAppcation.class);
    public static String CHANNEL;
    public static String myInvitationCode;
    public static String UserIcon;
    public static boolean VipFlag;

    /**
     * 内存泄露检测;
     */
 /*   private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyAppcation application = (MyAppcation) context
                .getApplicationContext();
        return application.refWatcher;
    }*/
    @Override
    public void onCreate() {
        super.onCreate();
//        refWatcher = LeakCanary.install(this);
        /**
         * 全局异常捕获方法;
         */
//      CrashHandler crashHandler = CrashHandler.getInstance();
//      crashHandler.init(getApplicationContext());

        OkHttpInit();
        CHANNEL = AnalyticsConfig.getChannel(getApplicationContext());
        JpushInit(this);
        Log.i(TAG, "CHANNEL ====" + CHANNEL);
        SPreferences.setContext(getApplicationContext());
        {
            /**
             * 参数一: appID;
             * 参数二: key;
             */
            PlatformConfig.setWeixin(MzFinal.WECHATPAY, "4bcde8ce9e646833395aae492d93bbc8");
            PlatformConfig.setQQZone(MzFinal.QQLOGIN, "63e5c4ab1c476fa9204c8504fa2338bd");
            /*PlatformConfig.setSinaWeibo("172922517", "048166c79d54b2c4ce3e76ba5d30097a");*/

        }
    }

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
