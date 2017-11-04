package example.com.fan.activity;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.MirrorBean;
import example.com.fan.bean.mcPublishImgUrls;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goUserPhotoOfVideoLoadPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/10/12.
 */
public class SuperUserPhotoOfVideoActivity extends InitActivity {
    private final static String TAG = getTAG(SuperUserPhotoOfVideoActivity.class);
    private List<LocalMedia> selectList = new ArrayList<>();
    private int flag;
    private String SpecialId = "";
    private int chargeNumber = 0;
    private List<mcPublishImgUrls> list;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        flag = Integer.parseInt(getIntent().getStringExtra("Photo_of_Video_flag"));
        switch (flag) {
            case 0:
                getData();
//                Photo();
                break;
            case 1:
                Video();
                break;
            default:
                finish();
                break;
        }
    }

    private void getData() {
        /**
         * 获取未完成私密照片;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETOLDPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(SuperUserPhotoOfVideoActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONObject ar = getJsonOb(response);
                            if (code == 1) {
                                list = new ArrayList<>();
                                Log.i(TAG, "GETOLDPRIVATEPHOTO" + response);
                                MirrorBean mb = new Gson().fromJson(String.valueOf(getJsonOb(response)), MirrorBean.class);
                           /*     if (ar.optJSONArray("mcPublishImgUrls").length() > 0) {

                                    SpecialId = mb.getId();
                                    chargeNumber = ar.optInt("hidePosition");

                                    for (int i = 0; i < mb.getMcPublishImgUrls().size(); i++) {
                                        mcPublishImgUrls mpiu = mb.getMcPublishImgUrls().get(i);
                                        list.add(mpiu);
                                    }
                                    goUserPhotoOfVideoLoadPage2(SuperUserPhotoOfVideoActivity.this, 3, list.get(0).getPath(), list, SpecialId, chargeNumber);
                                    finish();
                                } else*/
                                    Delete(mb.getId());

                            } else if (code == 0)
                                Photo();
                            else
                                ToastUtil.ToastErrorMsg(SuperUserPhotoOfVideoActivity.this, response, code);

                        } catch (Exception e) {
                            Log.i(TAG, "Error ===" + e);
                        }
                    }
                });
    }

    private void Delete(String id) {
        /**
         * 防止报错，空照片集直接删除;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.DELETEPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(SuperUserPhotoOfVideoActivity.this, "网络不顺畅...");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Log.i(TAG,"删除空照片集成功.. ==="+response);
                                Photo();
                            } else
                                ToastUtil.ToastErrorMsg(SuperUserPhotoOfVideoActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
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
                    goUserPhotoOfVideoLoadPage(this, 0, selectList.get(0).getPath(), selectList);
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
                    goUserPhotoOfVideoLoadPage(this, 1, selectList.get(0).getPath(), selectList);
                    finish();
                    break;
            }
        } else
            finish();
    }
}
