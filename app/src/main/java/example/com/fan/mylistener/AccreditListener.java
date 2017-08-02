package example.com.fan.mylistener;

/**
 * Created by lian on 2017/6/13.
 */
public interface AccreditListener {
    void onSucceed(String accessToken, String openid, String url);

    void onFail();
}
