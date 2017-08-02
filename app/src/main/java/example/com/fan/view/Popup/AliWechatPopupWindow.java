package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import example.com.fan.R;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.PayUtils.PayNow;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 微信支付宝弹窗,popupWindows实现;
 */
public class AliWechatPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(AliWechatPopupWindow.class);
    private static LinearLayout ali_ll, wechat_ll;
    private PopupWindow popupWindow;
    private Context context;
    private String id = "";
    private String[] url;

    public AliWechatPopupWindow(Context context, String[] url) {
        this.context = context;
        this.url = url;
    }

    //附近的人地区筛选;
    public void ScreenPopupWindow(View view, final Context mContext, String id) {
        this.id = id;
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.aliwechat_popu_layout, null);
        int width = DeviceUtils.getWindowWidth(mContext) * 8 / 10;
        int h = (int) (DeviceUtils.getWindowWidth(mContext) * 6.7 / 10);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        init(contentView);
        click();
        backgroundAlpha(0.4f, mContext);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f, mContext);
                popupWindow.dismiss();
            }
        });
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void click() {
        ali_ll.setOnClickListener(this);
        wechat_ll.setOnClickListener(this);
    }

    private static void init(View contentView) {
        ali_ll = (LinearLayout) contentView.findViewById(R.id.ali_ll);
        wechat_ll = (LinearLayout) contentView.findViewById(R.id.wechat_ll);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public static void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ali_ll:
                PayNow(context, id, url[0], 0, 1);
                break;
            case R.id.wechat_ll:
                PayNow(context, id, url[1], 1, 1);
                break;
        }
    }

}
