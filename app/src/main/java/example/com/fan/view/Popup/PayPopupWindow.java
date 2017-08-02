package example.com.fan.view.Popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.activity.PhotoActivity;
import example.com.fan.utils.MzFinal;

import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 余额不足充值页面,popupWindows实现;
 */
public class PayPopupWindow {
    private static final String TAG = getTAG(PayPopupWindow.class);
    private static TextView pay_tv, vip_tv, collect_tv, close_tv, introduce_tv;
    private Context context;
    private View view;
    private String id = "";
    private String price = "";
    public PayPopupWindow(Context context) {
        this.context = context;
    }
    public PayPopupWindow(Context context, String price,String id) {
        this.price = price;
        this.context = context;
        this.id =id;
    }

    public void ScreenPopupWindow(View view, PopupWindow popupWindow, int tag, View contentView) {
        this.view = view;
        init(contentView, tag);
        click(popupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void click(final PopupWindow popupWindow) {
        pay_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AliWechatPopupWindow aw = new AliWechatPopupWindow(context, new String[]{MzFinal.ALIPAYPHOTO, MzFinal.WXPAYPHOTO});
                aw.ScreenPopupWindow(view, context, id);
                popupWindow.dismiss();
            }
        });
        close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        vip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPayPage(context);
                popupWindow.dismiss();
            }
        });
        collect_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PhotoActivity.clistener != null)
                    PhotoActivity.clistener.onCollect();
            }
        });
    }

    private void init(View f, int tag) {

        pay_tv = (TextView) f.findViewById(R.id.pay_tv);
        vip_tv = (TextView) f.findViewById(R.id.vip_tv);
        collect_tv = (TextView) f.findViewById(R.id.collect_tv);
        introduce_tv = (TextView) f.findViewById(R.id.introduce_tv);
        close_tv = (TextView) f.findViewById(R.id.close_tv);
        if (tag == 1) {
            pay_tv.setVisibility(View.GONE);
            collect_tv.setVisibility(View.GONE);
            introduce_tv.setText(getRouString(R.string.pay_introduce2));
        } else {
            if (!price.isEmpty())
                introduce_tv.setText(getRouString(R.string.pay_introduce) + price + "元");
            else
                introduce_tv.setText(getRouString(R.string.pay_introduce2));
        }

    }

}
