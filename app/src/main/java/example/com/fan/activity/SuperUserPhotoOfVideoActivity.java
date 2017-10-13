package example.com.fan.activity;

import android.content.Intent;
import android.util.Log;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;

import static example.com.fan.utils.IntentUtils.goUserPhotoOfVideoLoadPage;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/10/12.
 */
public class SuperUserPhotoOfVideoActivity extends InitActivity {
    private final static String TAG = getTAG(SuperUserPhotoOfVideoActivity.class);
    private List<LocalMedia> selectList = new ArrayList<>();
    int flag;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        flag = Integer.parseInt(getIntent().getStringExtra("Photo_of_Video_flag"));
        switch (flag) {
            case 0:
                Photo();
                break;
            case 1:
                Video();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

    }

    private void Video() {
        PictureSelector.create(SuperUserPhotoOfVideoActivity.this)
                .openGallery(PictureMimeType.ofVideo())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_white_style)
                .selectionMode(PictureConfig.MULTIPLE)// 单选
                .previewVideo(true)// 是否可预览视频
                .isCamera(false)// 是否显示拍照按钮
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .enablePreviewAudio(true)//是否可播放音频
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .videoSecond(400)//显示多少秒以内的视频or音频也可适用
                .isGif(false)// 是否显示gif图片
                .forResult(7747);//结果回调onActivityResult code
    }

    private void Photo() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(SuperUserPhotoOfVideoActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_white_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(25)// 最大图片选择数量 int
                .minSelectNum(6)// 最小选择数量 int
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "原图路径===" + selectList.get(0).getPath());
                    Log.i(TAG, "压缩后路径===" + selectList.get(0).getCompressPath());
                    goUserPhotoOfVideoLoadPage(this, 0, selectList.get(0).getPath());
                    finish();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    break;
                case 7747:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "原图路径===" + selectList.get(0).getPath());
                    Log.i(TAG, "压缩后路径===" + selectList.get(0).getCompressPath());
                    goUserPhotoOfVideoLoadPage(this, 1, selectList.get(0).getPath());
                    finish();
                    break;
            }
        } else
            finish();
    }
}
