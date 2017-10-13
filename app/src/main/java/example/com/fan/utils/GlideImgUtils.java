package example.com.fan.utils;

import com.bumptech.glide.request.RequestOptions;

import example.com.fan.base.sign.save.SPreferences;

/**
 * Created by lian on 2017/5/3.
 */
public class GlideImgUtils {

    /**
     * 圆角;
     * .bitmapTransform(new RoundedCornersTransformation(context, 30, 0, RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000);
     * <p/>
     * 指定尺寸;
     * .override(w, h);
     * <p/>
     * 淡入加载效果;
     * .crossFade();
     * <p/>
     * 自定义动画;
     * .animate();
     * <p/>
     * 圆形;
     * .bitmapTransform(new CropCircleTransformation(context)).crossFade(200);
     * <p/>
     * 毛玻璃;
     * .bitmapTransform(new BlurTransformation(context, 25)).crossFade(1000);
     * <p/>
     * 显示模糊预加载;
     * .thumbnail(0.1f);
     * <p/>
     * 失败图片
     * .error(R.drawable.failed);
     * <p/>
     * 占位符 也就是加载中的图片，可放个gif
     * .placeholder(R.drawable.loading)
     */

    public static RequestOptions getRequestOptions(boolean centerCrop, int w, int h, boolean CropCircle) {
        RequestOptions options = new RequestOptions();
        if (centerCrop)
            options.centerCrop();
        if (w > 0 && h > 0)
            options.override(w, h);
        if (CropCircle)
            options.transform(new GlideCircleTransform(SPreferences.context));
        return options;
    }
}
