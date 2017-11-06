package example.com.fan.view.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.RippleView;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/5.
 * 客服页面；
 */
public class WithdrawPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(WithdrawPopupWindow.class);
    private static TextView balance_tv;
    private EditText money_ed;
    private static RippleView withdraw_bt;
    private Context mContext;
    private PopupWindow popupWindow;
    private String balance = "";

    public WithdrawPopupWindow(Context mContext, String balance) {
        this.mContext = mContext;
        this.balance = balance;
    }

    public synchronized void ScreenPopupWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.withdraw_popu_layout, null);
        int w = (int) (DeviceUtils.getWindowWidth(mContext) * 8 / 10);
        popupWindow = new PopupWindow(contentView, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);

        init(contentView);
        click();
        backgroundAlpha(0.7f, mContext);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f, mContext);
                popupWindow.dismiss();
                popupWindow = null;
                withdraw_bt = null;
                money_ed = null;
                balance_tv = null;
            }
        });

        popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.buy_goods_activity_layout, null), Gravity.BOTTOM, 0, 0);
    }

    private void click() {
        withdraw_bt.setOnClickListener(this);
    }

    private void init(View contentView) {
        balance_tv = (TextView) contentView.findViewById(R.id.balance_tv);
        money_ed = (EditText) contentView.findViewById(R.id.money_ed);
        withdraw_bt = (RippleView) contentView.findViewById(R.id.withdraw_bt);
        balance_tv.setText(balance);
        money_ed.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdraw_bt:
                Withdraw();
                break;
        }
    }

    private void Withdraw() {
        /**
         * 提现;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.APPLYCASH)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("money", money_ed.getText().toString())
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
                                ToastUtil.toast2_bottom(mContext, "成功申请提现！！！");
                                popupWindow.dismiss();
                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);

                        } catch (Exception e) {

                        }
                    }
                });
    }
}
