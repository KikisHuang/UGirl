package example.com.fan.activity;

import android.content.Context;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.OutSideWebViewClient;

import static example.com.fan.utils.SynUtils.Finish;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/7/8.
 */
public class OutSideActivity extends InitActivity {
    private String url = "";
    private WebView webView;
    private TextView statement_tv;
    private ScrollView statement_scroll;

    @Override
    protected void click() {

    }

    private void webinit() {
        webView.setInitialScale(50);//这里一定要设置，数值可以根据各人的需求而定，我这里设置的是50%的缩放

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setBuiltInZoomControls(true);// support zoom
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        webView.goBack();

    }


    @Override
    protected void init() {
        setContentView(R.layout.ouside_activity_layout);
        setTitles(this, getIntent().getStringExtra("outside_title"));
        webView = f(R.id.webView);
        statement_tv = f(R.id.statement_tv);
        statement_scroll = f(R.id.statement_scroll);
//        if (Build.VERSION.SDK_INT >= 19) // KITKAT
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void initData() {
        receiver();
    }

    private void receiver() {
        try {
//            url = getIntent().getStringExtra("outside_url");
            url = "http://news.online.sh.cn/news/gb/content/2017-09/05/content_8603221.htm";
            if (url.isEmpty()) {
                webView.setVisibility(View.GONE);
                statement_scroll.setVisibility(View.VISIBLE);
                statement_tv.setText(getRouString(R.string.statement));
            } else {

                synCookies(this, url);
                Show(this, "加载中", true, null);
                webView.loadUrl(url);

                webView.setVisibility(View.VISIBLE);
                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
                webView.setWebViewClient(new OutSideWebViewClient());
                webinit();

            }
        } catch (Exception e) {
            ToastUtil.toast2_bottom(OutSideActivity.this, "非常抱歉,该页面发生了异常!");
            Cancle();
            Finish(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    public void destroyWebView() {

        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webView.freeMemory();
            webView.pauseTimers();
            webView.removeAllViews();
            webView.destroy();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
    }

    /**
     * 同步cookie
     */
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "Cookie Test...");//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

}
