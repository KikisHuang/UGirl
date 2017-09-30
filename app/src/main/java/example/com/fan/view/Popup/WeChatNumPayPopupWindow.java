package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import example.com.fan.R;
import example.com.fan.utils.MzFinal;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 微信支付宝弹窗,popupWindows实现;
 */
public class WeChatNumPayPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(WeChatNumPayPopupWindow.class);
    private TextView model_name, price_tv, pay_tv, cancel_tv;
    private ImageView head_img;
    private PopupWindow popupWindow;
    private Context context;
    private String id = "";
    private AliWechatPopupWindow aw;
    private View view;
    private View layout;

    public WeChatNumPayPopupWindow(Context context) {
        this.context = context;
        this.aw = new AliWechatPopupWindow(context, new String[]{MzFinal.ALIPAYMODELWX, MzFinal.WXPAYMODELWX});
    }

    public void ScreenPopupWindow(View v, String id, String iconUrl, String price, String name) {
        if (popupWindow == null) {
            this.id = id;
            // 一个自定义的布局，作为显示的内容
            view = LayoutInflater.from(context).inflate(R.layout.wechatnum_pay_popu_layout, null);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);
            init(iconUrl, price, name);
            click();
            backgroundAlpha(0.4f, context);
            layout = v;
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
                    popupWindow = null;
                    model_name = null;
                    price_tv = null;
                    pay_tv = null;
                    cancel_tv = null;
                    head_img = null;
                    aw = null;
                    view = null;

                }
            });
            // 设置好参数之后再show
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    private void click() {
        pay_tv.setOnClickListener(this);
        head_img.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
    }

    private void init(String iconUrl, String price, String name) {
        model_name = (TextView) view.findViewById(R.id.model_name);
        price_tv = (TextView) view.findViewById(R.id.price_tv);
        pay_tv = (TextView) view.findViewById(R.id.pay_tv);
        cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
        head_img = (ImageView) view.findViewById(R.id.head_img);
        price_tv.setText("她的微信号价格为" + price + "元哦！！");

        model_name.setText(name);
        Glide.with(context).load(iconUrl).bitmapTransform(new CropCircleTransformation(context)).into(head_img);
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
            case R.id.pay_tv:
                aw.ScreenPopupWindow(layout, id);
                popupWindow.dismiss();
                break;
            case R.id.cancel_tv:
                popupWindow.dismiss();
                break;
        }
    }

}
