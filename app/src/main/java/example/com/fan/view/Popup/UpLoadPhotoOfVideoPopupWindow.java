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

import static example.com.fan.utils.IntentUtils.goSuperPhotoVideoPage;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/5.
 * 上传图片、视频页面；
 */
public class UpLoadPhotoOfVideoPopupWindow implements View.OnClickListener {
    private static final String TAG = getTAG(UpLoadPhotoOfVideoPopupWindow.class);

    private ImageView close_img, photo_img, video_img;
    private Context mContext;
    private PopupWindow popupWindow;

    public UpLoadPhotoOfVideoPopupWindow(Context mContext) {
        this.mContext = mContext;
    }

    public void ScreenPopupWindow() {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.upload_photo_video_popu_layout, null);
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
                    close_img = null;
                    photo_img = null;
                    video_img = null;
                }
            });

            popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.buy_goods_activity_layout, null), Gravity.BOTTOM, 0, 0);
        }
    }

    private void click() {
        close_img.setOnClickListener(this);
        photo_img.setOnClickListener(this);
        video_img.setOnClickListener(this);
    }

    private void init(View contentView) {
        close_img = (ImageView) contentView.findViewById(R.id.close_img);
        photo_img = (ImageView) contentView.findViewById(R.id.photo_img);
        video_img = (ImageView) contentView.findViewById(R.id.video_img);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                popupWindow.dismiss();
                break;
            case R.id.photo_img:
                goSuperPhotoVideoPage(mContext,0);
                popupWindow.dismiss();
                break;
            case R.id.video_img:
                goSuperPhotoVideoPage(mContext,1);
                popupWindow.dismiss();
                break;
        }
    }

}
