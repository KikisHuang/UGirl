package example.com.fan.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.utils.DeviceUtils;

import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 充值页面2,popupWindows实现;
 */
public class FakePopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(FakePopupWindow.class);
    private static TextView info_tv, pay_tv, cancle_tv;
    private Context context;
    private View v;
    private View view;
    private PopupWindow popupWindow;
    //1私密视频,0私照;
    private int tag;
    private String title = "";
    public FakePopupWindow(Context context,String title) {
        this.context = context;
        this.title = title;
    }

    public void ScreenPopupWindow(View vv) {
        if (popupWindow == null) {
            this.v = vv;
            view = LayoutInflater.from(context).inflate(R.layout.pay_two_pp_layout, null);
            init();
            click();
            int width = DeviceUtils.getWindowWidth(context) * 7 / 10;
            popupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow.dismiss();
                    popupWindow = null;
                    v = null;
                    pay_tv = null;
                    info_tv = null;
                    cancle_tv = null;
                }
            });
            // 设置好参数之后再show
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    private void click() {
        pay_tv.setOnClickListener(this);
        cancle_tv.setOnClickListener(this);
    }

    private void init() {
        info_tv = (TextView) view.findViewById(R.id.info_tv);
        pay_tv = (TextView) view.findViewById(R.id.pay_tv);
        cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);
        info_tv.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_tv:
                    goPayPage(context);
                    popupWindow.dismiss();
                break;
            case R.id.cancle_tv:
                popupWindow.dismiss();
                break;
        }
    }
}
