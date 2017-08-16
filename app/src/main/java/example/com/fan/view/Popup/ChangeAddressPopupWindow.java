package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.mylistener.AddressListener;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/5.
 * 收货地址填写弹窗;
 */
public class ChangeAddressPopupWindow {
    private static final String TAG = getTAG(ChangeAddressPopupWindow.class);
    private static EditText address_tv, wx_tv,phone_tv;
    private static TextView submit_info;
    private static PopupWindow popupWindow;

    //附近的人地区筛选;
    public static void ScreenPopupWindow(final Context mContext, String wx, String phone, String address, AddressListener listener) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.address_popu_layout, null);
        int h = (int) (DeviceUtils.getWindowHeight(mContext) * 4.5 / 10);
        int w = (int) (DeviceUtils.getWindowWidth(mContext) * 9 / 10);
         popupWindow = new PopupWindow(contentView, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.4f, mContext);
        init(contentView);
        setInfo(wx, phone, address);
        click(listener, popupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f, mContext);
                popupWindow.dismiss();
                popupWindow = null;
                address_tv = null;
                wx_tv = null;
                phone_tv = null;
                submit_info = null;
            }
        });
        popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.order_activity_layout, null), Gravity.CENTER, 0, 0);
    }

    private static void setInfo(String wx, String phone, String address) {
        address_tv.setText(address);
        wx_tv.setText(wx);
        phone_tv.setText(phone);
    }

    private static void click(final AddressListener listener, final PopupWindow popupWindow) {

        submit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChange(address_tv.getText().toString(), wx_tv.getText().toString(), phone_tv.getText().toString());
                popupWindow.dismiss();
            }
        });
    }

    private static void init(View contentView) {
        address_tv = (EditText) contentView.findViewById(R.id.address_tv);
        wx_tv = (EditText) contentView.findViewById(R.id.wx_tv);
        phone_tv = (EditText) contentView.findViewById(R.id.phone_tv);
        submit_info = (TextView) contentView.findViewById(R.id.submit_info);

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
}
