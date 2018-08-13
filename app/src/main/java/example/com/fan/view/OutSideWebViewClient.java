package example.com.fan.view;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.view.dialog.CustomProgress.Cancle;

/**
 * Created by lian on 2017/7/11.
 */
public class OutSideWebViewClient extends WebViewClient {
    private static final String TAG = getTAG(OutSideWebViewClient.class);

    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.loadUrl(request.getUrl().toString());
        } else {
            view.loadUrl(request.toString());

        }
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        Log.e(TAG, "Cookies = " + CookieStr);
        Cancle();
        super.onPageFinished(view, url);
    }
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
        // super.onReceivedSslError(view, handler, error);
        // 接受所有网站的证书，忽略SSL错误，执行访问网页
        handler.proceed();
    }
}
