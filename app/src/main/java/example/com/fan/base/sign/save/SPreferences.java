package example.com.fan.base.sign.save;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by on 2017/5/13
 * Sp存储类;
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
    /**
     * 用户UUID(启动次数统计);
     */
    private static final String KEY_UUID = "User_UUID";

    /**
     * 保存UUID;
     */
    public static void saveUserUUID(String uuid) {
        saveString(KEY_UUID, uuid);
    }

    /**
     * 获取UUID;
     */
    public static String getUserUUID() {
        return getString(KEY_UUID);
    }

    /**
     * 保存登录Token;
     */
    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    /**
     * 获取登录Token;
     */
    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    /**
     * 保存登录方式;
     */
    public static void saveLoginWay(String str) {
        saveString(KEY_LOGIN_WAY, str);
    }

    /**
     * 保存推广码弹出状态;
     */
    public static void saveInViCode(boolean f) {
        saveBoolean(KEY_INVI_CODE, f);
    }

    /**
     * 获取推广码弹出状态;
     */
    public static boolean getInViCode() {
        return getBoolean(KEY_INVI_CODE);
    }

    /**
     * 获取登录状态;
     */
    public static String getLoginWay() {
        return getString(KEY_LOGIN_WAY);
    }

    /**
     * 读取String方法;
     *
     * @param key
     * @return String
     */
    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    /**
     * 读取boolean方法;
     *
     * @param key
     * @return boolean
     */
    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, true);
    }

    /**
     * 保存boolean方法;
     *
     * @param key
     * @param value
     */
    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存String方法;
     *
     * @param key
     * @param value
     */
    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 通用SharedPreferences方法;
     */
    static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("Fan", Context.MODE_PRIVATE);
    }

    /**
     * 获取全局Context方法;
     *
     * @param context
     */
    public static void setContext(Context context) {
        SPreferences.context = context.getApplicationContext();
    }

    /**
     * 获取评论开关状态;
     */
    public static boolean getBulletFlag() {
        return getBoolean(KEY_BULLETFLAG);
    }

    /**
     * 保存开关状态;
     *
     * @param flag
     */
    public static void saveBulletFlag(boolean flag) {
        saveBoolean(KEY_BULLETFLAG, flag);
    }
}
