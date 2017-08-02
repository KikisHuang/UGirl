package example.com.fan.view.Popup;

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
import example.com.fan.mylistener.editeListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/3/29.
 * 评论Editext,Popupwindows实现;
 */
public class CommentEditPopupWindow {
    private static final String TAG = getTAG(CommentEditPopupWindow.class);
    private static TextView send_tv;
    private static EditText msg_edt;
    private static PopupWindow popupWindow;
    private static  editeListener elistener;

    public static void ScreenPopupWindow(View view, final Context mContext, editeListener listener) {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            elistener = listener;
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.comment_popu_layout, null);
            init(contentView);
            int width = (int) (DeviceUtils.getWindowWidth(mContext) * 3.4 / 10);

            popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, width);
            SendMsg(mContext);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
//防止PopupWindow被软件盘挡住
            popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        //打开软键盘
//        ShowofHideSoftKeyboard(msg_edt, mContext);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
//                //关闭软键盘
//                if (((Activity) mContext).getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
//                    //隐藏软键盘
//                    ShowofHideSoftKeyboard(msg_edt, mContext);
//                }
                    popupWindow.dismiss();
                    popupWindow = null;
                    elistener = null;
                }
            });
            //始终在软键盘上方;
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            // 设置好参数之后再show
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        popupWindow.showAsDropDown(LayoutInflater.from(mContext).inflate(R.layout.photo_activity_layout, null));
        }
    }

    private static void SendMsg(final Context context) {
        send_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = msg_edt.getText().toString().length();
                if (size >= 5 && size < 50) {
                    elistener.showEditePopup(msg_edt.getText().toString());
                    msg_edt.setText("");
                    popupWindow.dismiss();

                } else {
                    if (size > 50)
                        ToastUtil.toast2_bottom(context, "评论内容长度不能超过50字哦.");
                    if (size < 5)
                        ToastUtil.toast2_bottom(context, "评论内容长度不能小于5个字哦.");
                }

            }
        });
    }


    private static void init(View contentView) {
        msg_edt = (EditText) contentView.findViewById(R.id.msg_edt);
        send_tv = (TextView) contentView.findViewById(R.id.send_tv);
    }
}
