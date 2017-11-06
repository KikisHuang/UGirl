package example.com.fan.utils;

import android.app.Activity;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import example.com.fan.R;

/**
 * Created by lian on 2017/11/6.
 */
public class PhotoUtils {
    public static void onMultiplePhoto(Activity ac, int min, int max) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(ac)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_white_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(max)// 最大图片选择数量 int
                .minSelectNum(min)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .enableCrop(false)//是否裁剪
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .compressMaxKB(Luban.THIRD_GEAR)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 头像裁剪
     * 单选相册;
     *
     * @param ac
     * @param flag 相册或拍照;
     */
    public static void onSinglePhoto(Activity ac, boolean flag) {
        PictureSelector ps = PictureSelector.create(ac);
        PictureSelectionModel model;
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
        if (flag)
            model = ps.openGallery(PictureMimeType.ofImage());
        else
            model = ps.openCamera(PictureMimeType.ofImage());

                model
                .theme(R.style.picture_white_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .enableCrop(true)//是否裁剪
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .withAspectRatio(1, 1)
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .compress(true)// 是否压缩 true or false
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片 true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    /**
     * 头像不裁剪
     * 单选相册;
     *
     * @param ac
     * @param flag 相册或拍照;
     */
    public static void onSinglePhotoNoCut(Activity ac, boolean flag) {
        PictureSelector ps = PictureSelector.create(ac);
        PictureSelectionModel model;
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
        if (flag)
            model = ps.openGallery(PictureMimeType.ofImage());
        else
            model = ps.openCamera(PictureMimeType.ofImage());

                model
                .theme(R.style.picture_white_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .enableCrop(false)//是否裁剪
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片 true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
