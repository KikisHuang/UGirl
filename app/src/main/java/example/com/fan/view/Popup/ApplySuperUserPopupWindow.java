package example.com.fan.view.Popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import example.com.fan.R;
import example.com.fan.view.RippleView;

import static example.com.fan.utils.IntentUtils.goRzPage;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/5.
 * 申请超级玩家页面；
 */
public class ApplySuperUserPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(ApplySuperUserPopupWindow.class);
    private RippleView apply_for_button;
    private ImageView close_img;
    private Context mContext;
    private PopupWindow popupWindow;

    public ApplySuperUserPopupWindow(Context mContext) {
        this.mContext = mContext;
    }

    public void ScreenPopupWindow() {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.super_popu_layout, null);
            popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);

            init(contentView);
            click();
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow.dismiss();
                    popupWindow = null;
                    apply_for_button = null;
                    close_img = null;
                }
            });

            popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.buy_goods_activity_layout, null), Gravity.BOTTOM, 0, 0);
        }
    }

    private void click() {
        close_img.setOnClickListener(this);
        apply_for_button.setOnClickListener(this);
    }

    private void init(View contentView) {
        apply_for_button = (RippleView) contentView.findViewById(R.id.apply_for_button);
        close_img = (ImageView) contentView.findViewById(R.id.close_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                popupWindow.dismiss();
                break;
            case R.id.apply_for_button:
                goRzPage(mContext, 0);
                popupWindow.dismiss();
                break;
        }
    }

}
