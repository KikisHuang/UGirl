package example.com.fan.utils.photo;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonUtil {

    private static final CommonLog log = LogFactory.createLog();

    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
        }
    }


    public static boolean checkNetState(Context context) {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            //方法一：
            NetworkInfo[] info = connectivity.getAllNetworkInfo();//获取手机所有网络状态
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        netstate = true;
                        break;
                    }
                }
            }

            //方法二：
//            NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
//            if (networkinfo == null || !networkinfo.isAvailable()) {
//                netstate = false;
//                return false;
//            }else {
//                netstate = true;
//            }
//
//            if ("WIFI".equals(networkinfo.getTypeName())) {//网络类型判断
//                // 当前网络为WIFI
//            } else {
//                //
//            }

        }
        return netstate;
    }

    public static void showToask(Context context, String tip) {
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

//	DisplayMetrics dm = new DisplayMetrics();
//	manager.getDefaultDisplay().getMetrics(dm)
// 		SCREEN_WIDTH = dm.widthPixels;
//		SCREEN_HEIGHT = dm.heightPixels;

    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
//		SCREEN_WIDTH = dm.widthPixels;
//		SCREEN_HEIGHT = dm.heightPixels;

//		Display display = manager.getDefaultDisplay();
//		return display.getHeight();
        return dm.heightPixels;
    }

}
