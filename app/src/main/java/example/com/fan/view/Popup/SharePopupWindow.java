package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import example.com.fan.R;
import example.com.fan.bean.RandomModelBean;
import example.com.fan.mylistener.ShareListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.QRCodeUtils.getQrCode;
import static example.com.fan.utils.ShareUtils.getShareUrl;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.tpartyLoginUtils.getUMShareListener;

/**
 * Created by lian on 2017/6/5.
 * 分享自定义页面；
 */
public class SharePopupWindow implements ShareListener, View.OnClickListener {
    private static final String TAG = getTAG(SharePopupWindow.class);
    private Context mContext;
    private ImageView code_img;
    private String myInvitationCode;
    private TextView myInvitationCode_tv;
    private Bitmap bitmap;
    private ShareListener listener;
    private PopupWindow popupWindow;
    private LinearLayout qq_layout, wx_layout, find_layout;
    private String ShareUrl = "";

    public SharePopupWindow(Context mContext, String myInvitationCode) {
        this.myInvitationCode = myInvitationCode;
        this.mContext = mContext;
    }

    public void ScreenPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.share_popup_layout, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        init(contentView);
        setQRCode();
        click();
        backgroundAlpha(0.3f, mContext);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f, mContext);
                if (bitmap != null)
                    bitmap.recycle();
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void setQRCode() {
        myInvitationCode_tv.setText(getRouString(R.string.RichScan) + myInvitationCode);
        /**
         * 获取随机模特;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETRANDOMBYPAGE)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "1")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0) {
                                    RandomModelBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(0)), RandomModelBean.class);
                                    getShareUrl(mContext, rb.getUser_id(), rb.getId(), listener);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void click() {
        qq_layout.setOnClickListener(this);
        wx_layout.setOnClickListener(this);
        find_layout.setOnClickListener(this);
    }

    private void init(View contentView) {
        listener = this;
        code_img = (ImageView) contentView.findViewById(R.id.code_img);
        myInvitationCode_tv = (TextView) contentView.findViewById(R.id.myInvitationCode_tv);
        qq_layout = (LinearLayout) contentView.findViewById(R.id.qq_layout);
        wx_layout = (LinearLayout) contentView.findViewById(R.id.wx_layout);
        find_layout = (LinearLayout) contentView.findViewById(R.id.find_layout);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onSucceed(String url) {
        ShareUrl = url;
        bitmap = getQrCode(url);
        if (code_img != null && bitmap != null && url.length() > 0)
            code_img.setImageBitmap(bitmap);
    }

    @Override
    public void onFail() {
        if (popupWindow != null)
            popupWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (!ShareUrl.isEmpty()) {

            switch (v.getId()) {
                case R.id.qq_layout:
                    share(SHARE_MEDIA.QQ);
                    break;
                case R.id.wx_layout:
                    share(SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.find_layout:
                    share(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
            }
        }
    }

    private void share(SHARE_MEDIA md) {
        //开启自定义分享页面
        UMImage image = new UMImage(mContext, R.mipmap.logo);
        new ShareAction((Activity) mContext)
                .setPlatform(md)
                .setCallback(getUMShareListener(mContext))
                .withTitle("尤女郎")
                .withText("邀请码：" + myInvitationCode)
                .withTargetUrl(ShareUrl)
                .withMedia(image)
                .share();

        popupWindow.dismiss();
    }
}
