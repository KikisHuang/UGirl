package example.com.fan.utils.photo;

public class GlobalVar {

    public static int screenHeight;
    public static int screenWidth;
    public static String version;
    public static String device;
    // 存储用户名的key
    public static boolean isCheckUpdate = false;
    // 项目安装文件夹
    public static String APP_FLODER = "fan";
    public static final String APP_INSTALL_FLODER = APP_FLODER + "/install";
    public static final String APP_LOG_FLODER = APP_FLODER + "/logs";
    public static final String APP_IMAGE_FLODER = APP_FLODER + "/images";

    public static void setFloderName(String name) {
        APP_FLODER = name;
    }

}
