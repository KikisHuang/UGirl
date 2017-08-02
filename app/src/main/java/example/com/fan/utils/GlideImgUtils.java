package example.com.fan.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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


    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 30, 0, RoundedCornersTransformation.CornerType.ALL)).crossFade(1000)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }

}
