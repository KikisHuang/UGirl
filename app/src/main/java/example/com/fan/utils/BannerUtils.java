package example.com.fan.utils;

import android.content.Context;

import static example.com.fan.utils.IntentUtils.goBuyGoodsPage;
import static example.com.fan.utils.IntentUtils.goOutsidePage;
import static example.com.fan.utils.IntentUtils.goPhotoPage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;

/**
 * Created by lian on 2017/7/15.
 * Banner跳转通用方法;
 */
public class BannerUtils {

    public static void goBannerPage(Context context, int type, String path, String id) {
        if (LoginStatusQuery()) {
            if (type == -1)
                goOutsidePage(context, path, "活动");
            if (type == 0)
                goPhotoPage(context, id, 0);
            if (type == 1)
                goBuyGoodsPage(context, id);
            if (type == 2) {

            }
            if (type == 4)
                goPlayerPage(context, id, 4);
            if (type == 5)
                goPlayerPage(context, id, 5);
            if (type == -3)
                goPlayerPage(context, id, -3);
        } else
            Login(context);
    }
}
