package example.com.fan.mylistener;

/**
 * Created by lian on 2017/7/17.
 */
public interface PayListener {
    void onPay(String num, String title, String price, String tag, String cover, String info, String id);
}
