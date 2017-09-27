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

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.base.sign.save.SPreferences.saveInViCode;
import static example.com.fan.utils.IntentUtils.goPersonInfoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getSex;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/5.
 * 邀请码弹窗;
 */
public class CodePopupWindow {
    private static final String TAG = getTAG(CodePopupWindow.class);
    private static ImageView code_img;
    private Context mContext;
    private List<UserInfoBean> info;
    private PopupWindow popupWindow;

    public CodePopupWindow(Context mContext) {
        this.mContext = mContext;
    }

    public void ScreenPopupWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.code_popup_layout, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.AnimationPreview);
        init(contentView);
        getData();
        click();
        backgroundAlpha(0.3f, mContext);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f, mContext);
                popupWindow.dismiss();
                popupWindow = null;
                code_img = null;
                info = null;
                saveInViCode(false);
            }
        });

        popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.activity_main, null), Gravity.CENTER, 0, 0);
    }

    private void getData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
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
                                JSONObject ob = getJsonOb(response);
                                UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                info.add(ub);
                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
        }

    private void click() {
        code_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (info.size() > 0)
                    goPersonInfoPage(mContext, info.get(0).getHeadImgUrl(), info.get(0).getName(), String.valueOf(getSex(info.get(0).getSex())), info.get(0).getWx());
//                  popupWindow.dismiss();

            }
        });
    }

    private void init(View contentView) {
        code_img = (ImageView) contentView.findViewById(R.id.code_img);
        info = new ArrayList<>();
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
