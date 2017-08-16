package example.com.fan.utils;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuView;

import example.com.fan.activity.WelcomeActivity;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/18.
 * 动画工具类;
 */
public class AnimationUtil {
    private static final String TAG = getTAG(AnimationUtil.class);

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }


    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 从控件所在位置移动到控件的顶部
     *
     * @return
     */
    public static TranslateAnimation moveToViewTop() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 从控件的顶部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation1() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 欢迎界面平移动画;
     *
     * @return
     */
    public static TranslateAnimation WelcomeAnima() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -2.15f);
        mHiddenAction.setDuration(1000);
        mHiddenAction.setFillAfter(true);
        return mHiddenAction;
    }

    /**
     * 欢迎界面旋转动画;
     *
     * @return
     */

    public static RotateAnimation WelcomeAnimatwo() {
        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(1300);
        animation.setFillAfter(true);
        return animation;
    }

    /**
     * 欢迎界面缩放动画;
     * 从view中心放大;
     *
     * @return
     */

    public static ScaleAnimation WelcomeAnimathree() {

        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(15000);
        return animation;
    }

    /**
     * 标题缩放动画;
     * 从view中心缩小;
     *
     * @return
     */
    public static ScaleAnimation TitleZoomAnima(boolean tag) {

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //3秒完成动画
        scaleAnimation.setDuration(200);
        return scaleAnimation;
    }

    /**
     * 闪烁动画
     *
     * @param view
     */
    public static void TwinkleAnima(View view) {
        //闪烁
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation1.setDuration(200);
        alphaAnimation1.setRepeatCount(1);
        view.setAnimation(alphaAnimation1);
        view.startAnimation(alphaAnimation1);
//        alphaAnimation1.setRepeatMode(Animation.REVERSE);
    }

    /**
     * 抖动动画
     *
     * @param view
     */
    public static ObjectAnimator ShakeAnima(View view) {
        return tada(view, 1f);
    }

    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(350);
    }

    /**
     * 圆形转场动画;
     * 从view中心放大;
     *
     * @param layoutView
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void cuttoAnima(RelativeLayout layoutView) {

        // 圆形动画的x坐标  位于View的中心
        int cx = (layoutView.getLeft() + layoutView.getRight()) / 2;

        //圆形动画的y坐标  位于View的中心
        int cy = (layoutView.getTop() + layoutView.getBottom()) / 2;

        //起始大小半径
        float startX = 0f;

        //结束大小半径 大小为图片对角线的一半
        float startY = (float) Math.sqrt(cx * cx + cy * cy);
        Animator animator = null;
        animator = ViewAnimationUtils.createCircularReveal(layoutView, cx, cy, startX, startY);
        //在动画开始的地方速率改变比较慢,然后开始加速
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (WelcomeActivity.plistener != null)
                    WelcomeActivity.plistener.onIncrease();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    /**
     * 页面标题缩放、放大动画;
     */
    public static int size = 0;

    public static void TitleAnima(final Context context, final RelativeLayout layout, boolean flag, final TextView mtv, MaterialMenuView slide, ImageView search) {
        ValueAnimator va = null;
        Log.i(TAG, "flag ===" + layout.getLayoutParams().height);
        if (flag && layout.getLayoutParams().height == 150) {
            size = 4;
            //隐藏view，高度从50变为25
            va = ValueAnimator.ofInt(DeviceUtils.dip2px(context, 50), DeviceUtils.dip2px(context, 25));
            slide.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取当前的height值
                    int h = (Integer) valueAnimator.getAnimatedValue();
                    //动态更新view的高度
                    layout.getLayoutParams().height = h;
                    layout.requestLayout();
                    switch (size) {
                        case 4:
//                            mtv.setTextSize(DeviceUtils.dip2px(context,18));
                            size--;
                            break;
                        case 3:
                            mtv.setTextSize(17);
                            size--;
                            break;
                        case 2:
                            mtv.setTextSize(16);
                            size--;
                            break;
                        case 1:
                            mtv.setTextSize(15);
                            size--;
                            break;
                        case 0:
                            mtv.setTextSize(14);
                            break;
                    }
                }
            });

            va.setDuration(200);
            //开始动画
            va.start();
        }
        if (!flag && layout.getLayoutParams().height == 75) {
            size = 0;
            va = ValueAnimator.ofInt(DeviceUtils.dip2px(context, 25), DeviceUtils.dip2px(context, 50));
            slide.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);

            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取当前的height值
                    int h = (Integer) valueAnimator.getAnimatedValue();
                    //动态更新view的高度
                    layout.getLayoutParams().height = h;
                    layout.requestLayout();
                    switch (size) {
                        case 4:
                            mtv.setTextSize(18);
                            break;
                        case 3:
                            mtv.setTextSize(17);
                            size++;
                            break;
                        case 2:
                            mtv.setTextSize(16);
                            size++;
                            break;
                        case 1:
                            mtv.setTextSize(15);
                            size++;
                            break;
                        case 0:
//                            mtv.setTextSize(DeviceUtils.dip2px(context,14));
                            size++;
                            break;
                    }
                }
            });
            va.setDuration(200);
            //开始动画
            va.start();
        }

    }
}
