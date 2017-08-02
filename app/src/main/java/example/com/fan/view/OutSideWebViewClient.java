package example.com.fan.view;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/7/11.
 */
public class OutSideWebViewClient extends WebViewClient {
    private static final String TAG = getTAG(OutSideWebViewClient.class);

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        Log.e(TAG, "Cookies = " + CookieStr);
        super.onPageFinished(view, url);
    }

}
