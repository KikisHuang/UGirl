package example.com.fan.utils;

/**
 * Created by lian on 2017/5/3.
 */
public class GlideImgUtils {

    /**
     * 圆角;
     * .bitmapTransform(new RoundedCornersTransformation(context, 30, 0, RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000);
     *
     * 指定尺寸;
     * .override(w, h);
     *
     * 淡入加载效果;
     * .crossFade();
     *
     * 自定义动画;
     * .animate();
     *
     * 圆形;
     * .bitmapTransform(new CropCircleTransformation(context)).crossFade(200);
     *
     * 毛玻璃;
     * .bitmapTransform(new BlurTransformation(context, 25)).crossFade(1000);
     *
     * 显示模糊预加载;
     * .thumbnail(0.1f);
     *
     * 失败图片
     * .error(R.drawable.failed);
     *
     * 占位符 也就是加载中的图片，可放个gif
     * .placeholder(R.drawable.loading)
     */

}
