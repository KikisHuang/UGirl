package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import example.com.fan.R;
import example.com.fan.activity.PhotoActivity;
import example.com.fan.activity.PrivatePhotoActivity;
import example.com.fan.utils.MzFinal;

import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 充值页面,popupWindows实现;
 */
public class PayPopupWindow {
    private static final String TAG = getTAG(PayPopupWindow.class);
    private static TextView pay_tv, vip_tv, collect_tv, close_tv, introduce_tv;
    private Context context;
    private View view;
    private View v;
    private String id = "";
    private String price = "";
    private AliWechatPopupWindow aw;
    private PopupWindow popupWindow;
    private int tag;

    public PayPopupWindow(Context context) {
        this.context = context;
    }

    public PayPopupWindow(Context context, String price, String id) {
        this.price = price;
        this.context = context;
        this.id = id;
        this.aw = new AliWechatPopupWindow(context, new String[]{MzFinal.ALIPAYPHOTO, MzFinal.WXPAYPHOTO});
    }

    public synchronized void ScreenPopupWindow(View vv, PopupWindow p, final int tag, View contentView) {
        if (popupWindow == null) {
            this.popupWindow = p;
            this.view = contentView;
            this.v = vv;
            this.tag = tag;
            init();
            backgroundAlpha(0.7f, context);
            click();
            popupWindow.setAnimationStyle(R.style.AnimationPreview);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f, context);
                    popupWindow.dismiss();
                    if (tag == -1)
                        ((Activity) context).finish();

                    popupWindow = null;
                    aw = null;
                    view = null;
                    v = null;
                    pay_tv = null;
                    vip_tv = null;
                    collect_tv = null;
                    introduce_tv = null;
                    close_tv = null;
                }
            });
            // 设置好参数之后再show
            popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void click() {
        pay_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vs) {
                aw.ScreenPopupWindow(v, id);
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
                if (PrivatePhotoActivity.clistener != null)
                    PrivatePhotoActivity.clistener.onCollect();
            }
        });
    }

    private void init() {

        pay_tv = (TextView) view.findViewById(R.id.pay_tv);
        vip_tv = (TextView) view.findViewById(R.id.vip_tv);
        collect_tv = (TextView) view.findViewById(R.id.collect_tv);
        introduce_tv = (TextView) view.findViewById(R.id.introduce_tv);
        close_tv = (TextView) view.findViewById(R.id.close_tv);
        if (tag == 1) {
            pay_tv.setVisibility(View.GONE);
            collect_tv.setVisibility(View.GONE);
            introduce_tv.setText(getRouString(R.string.pay_introduce2));
        } else if (tag == 2) {
            vip_tv.setVisibility(View.GONE);
            introduce_tv.setText(getRouString(R.string.pay2_introduce) + price + "元");
        } else {
            close_tv.setVisibility(View.GONE);
            collect_tv.setVisibility(View.GONE);
//            if (!price.isEmpty())
//                introduce_tv.setText(getRouString(R.string.pay_introduce) + price + "元");
//            else
//                introduce_tv.setText(getRouString(R.string.pay_introduce2));
        }
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

}
