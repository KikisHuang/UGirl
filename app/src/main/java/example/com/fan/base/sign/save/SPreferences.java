package example.com.fan.base.sign.save;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class SPreferences {

    public static Context context;
    /**
     * 用户登录token;
     */
    private static final String KEY_USER_TOKEN = "token";
    /**
     * 照片查看器弹幕开关;
     */
    private static final String KEY_BULLETFLAG = "bullet_flag";
    /**
     * 上次登录方式;
     */
    private static final String KEY_LOGIN_WAY = "login_way";
    /**
     * 邀请弹窗标识符;
     */
    private static final String KEY_INVI_CODE = "Invitation_code";

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }
    public static void saveLoginWay(String str) {
        saveString(KEY_LOGIN_WAY, str);
    }
    public static void saveInViCode(boolean f) {
        saveBoolean(KEY_INVI_CODE, f);
    }
    public static boolean getInViCode() {
        return getBoolean(KEY_INVI_CODE);
    }

    public static String getLoginWay() {
        return getString(KEY_LOGIN_WAY);
    }
    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, true);
    }

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("Fan", Context.MODE_PRIVATE);
    }

    public static void setContext(Context context) {
        SPreferences.context = context.getApplicationContext();
    }


    public static boolean getBulletFlag() {
        return getBoolean(KEY_BULLETFLAG);
    }

    public static void saveBulletFlag(boolean flag) {
        saveBoolean(KEY_BULLETFLAG, flag);
    }
}
