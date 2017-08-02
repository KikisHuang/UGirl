package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import example.com.fan.R;
import example.com.fan.bean.VersionBean;
import example.com.fan.mylistener.VersionCheckListener;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.getVersionUtils.getVersionInfo;

/**
 * Created by lian on 2017/6/5.
 * 客服页面；
 */
public class ServerPopupWindow implements VersionCheckListener {
    private static final String TAG = getTAG(ServerPopupWindow.class);
    private static TextView wx_bt;
    private static TextView wx_number;
    private Context mContext;
    private String wx = "";

    public ServerPopupWindow(Context mContext) {
        this.mContext = mContext;
    }

    public void ScreenPopupWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.server_popu_layout, null);
        int h = (int) (DeviceUtils.getWindowHeight(mContext) * 4.7 / 10);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, h);
        getVersionInfo(mContext.getApplicationContext(), this);
        init(contentView);
        click(mContext);
        backgroundAlpha(0.7f, mContext);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f, mContext);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.buy_goods_activity_layout, null), Gravity.BOTTOM, 0, 0);
    }

    private void click(final Context mContext) {
        if (!wx.isEmpty()) {
            wx_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(wx);

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setComponent(cmp);
                        mContext.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // TODO: handle exception
                        Toast.makeText(mContext, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void init(View contentView) {
        wx_bt = (TextView) contentView.findViewById(R.id.wx_bt);
        wx_number = (TextView) contentView.findViewById(R.id.wx_number);
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
    public void onVersion(VersionBean vb) {
        wx = vb.getKfWx();
        wx_number.setText("客服微信号：" + wx);
    }

    @Override
    public void onFail() {

    }
}
