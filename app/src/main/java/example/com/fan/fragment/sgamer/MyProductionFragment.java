package example.com.fan.fragment.sgamer;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.MyProductionAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModeInfoBean;
import example.com.fan.bean.ModelBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.ChangeUserInfoListener;
import example.com.fan.mylistener.ItemClickListener;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.SuperUseDeleteListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.SpringUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.dialog.AlertDialog;
import okhttp3.Call;

import static example.com.fan.utils.FileSizeUtil.getFileOrFilesSize;
import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.ShareUtils.getSystemShare;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TextViewColorUtils.setTextColor2;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;


/**
 * Created by lian on 2017/10/10.
 */
public class MyProductionFragment extends BaseFragment implements SpringListener, ItemClickListener, SuperUseDeleteListener, View.OnClickListener, ChangeUserInfoListener {

    private static final String TAG = getTAG(MyProductionFragment.class);
    private TextView photo_tv, video_tv, income_tv;
    private SpringView springview;
    private ListView listView;
    private int page = 0;
    private ImageView cover_img;
    private LinearLayout top;
    private List<ModelBean> rlist;
    private ItemClickListener hlistener;
    private SuperUseDeleteListener delete;
    private MyProductionAdapter adapter;
    private LinearLayout null_layout;
    private LinearLayout bottom_null_layout;
    public static ChangeUserInfoListener listener;

    @Override
    protected int initContentView() {
        return R.layout.myproduction_fragment;
    }

    @Override
    protected void click() {
        null_layout.setOnClickListener(this);
        cover_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        top = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.myproduction_head_include, null);
        rlist = new ArrayList<>();
        hlistener = this;
        listener = this;
        delete = this;
        springview = (SpringView) view.findViewById(R.id.springview);
        SpringUtils.SpringViewInit(springview, getActivity(), this);
        listView = (ListView) view.findViewById(R.id.listView);
        photo_tv = (TextView) top.findViewById(R.id.photo_tv);
        cover_img = (ImageView) top.findViewById(R.id.cover_img);
        video_tv = (TextView) top.findViewById(R.id.video_tv);
        income_tv = (TextView) top.findViewById(R.id.income_tv);
        null_layout = (LinearLayout) top.findViewById(R.id.null_layout);
        bottom_null_layout = (LinearLayout) top.findViewById(R.id.bottom_null_layout);

    }


    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {
        /**
         * 获取私密模特信息 ;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMODELINFO2)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, MzFinal.MYID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {

                                JSONObject ob = getJsonOb(response);

                                ModeInfoBean mb = new Gson().fromJson(String.valueOf(ob), ModeInfoBean.class);
                                photo_tv.setText(getRouString(R.string.photo) + "\n" + mb.getPhotoCount());
                                video_tv.setText(getRouString(R.string.Video) + "\n" + mb.getVideoCount());
                                setTextColor2(income_tv, getRouString(R.string.income) + MzFinal.br, String.valueOf(mb.getSumOrder()), "#FF4D87");
                                if (cleanNull(mb.getCoverPath()))
                                    null_layout.setVisibility(View.VISIBLE);
                                else {
                                    null_layout.setVisibility(View.GONE);
                                    Glide.with(getActivity()).load(mb.getCoverPath()).into(cover_img);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });
        /**
         * 获取模特自己传的私密照片/私密视频
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPRIVATERECORD)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 20))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (b)
                                    rlist.clear();
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0 && b) {
                                    bottom_null_layout.setVisibility(View.GONE);
                                } else if (b && ar.length() <= 0)
                                    bottom_null_layout.setVisibility(View.VISIBLE);

                                for (int i = 0; i < ar.length(); i++) {
                                    ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                    rlist.add(mb);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new MyProductionAdapter(getActivity(), rlist, hlistener, delete);
                                    listView.setAdapter(adapter);
                                }
                                if (listView.getHeaderViewsCount() == 0) {
                                    listView.addHeaderView(top);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });

    }

    @Override
    public void IsonRefresh(int i) {
        page += i;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    @Override
    public void onItemClickListener(int position, String id) {
        switch (position) {
            case 0:
                goPrivatePhotoPage(getActivity(), id, 0);
                break;
            case -3:
                goPlayerPage(getActivity(), id, -3);
                break;
            case 1002:
                goHomePage(getActivity(), id);
                break;
            case 12322:
                getSystemShare(getActivity(), id);
                break;
        }
    }

    @Override
    public void onDelete(int type, final String id) {
        switch (type) {
            //私密照;
            case -2:
                new AlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("确定删除吗？").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteVideoOfPhoto(id, MzFinal.DELETEPRIVATEPHOTO);
                    }
                }).show();
                break;
            //私密视频;
            case -3:
                new AlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("确定删除吗？").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteVideoOfPhoto(id, MzFinal.DELETEPRIVATEVIDEO);
                    }
                }).show();
                break;
        }
    }


    private void DeleteVideoOfPhoto(String id, String url) {
        Show(getActivity(), "删除中..", true, null);
        /**
         * 删除视频、私照;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + url)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(getActivity(), "删除成功!!");
                                getData(true);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                            Cancle();
                        } catch (Exception e) {
                            Cancle();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_img:
                SelectPhoto();
                break;
            case R.id.null_layout:
                SelectPhoto();
                break;
        }
    }

    private void SelectPhoto() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(MyProductionFragment.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_white_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
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
                .forResult(7894);//结果回调onActivityResult code
    }

    private void UpDataBack(File file) {
        Show(getActivity(), "上传图片中..", false, null);
        /**
         * 更新设置模特背景；
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URl + MzFinal.UPDATEBACKGROUND)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addFile("file", file.getName(), file)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(getActivity(), "背景图片修改成功!!");
                                getData(true);
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                        Cancle();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case 7894:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    //压缩判断;
                    if (getFileOrFilesSize(selectList.get(0).getPath(), 3) > 1.5) {

                        Log.i(TAG, "背景大于 1.5M 使用压缩路径, url===" + selectList.get(0).getCompressPath());
                        UpDataBack(new File(selectList.get(0).getCompressPath()));
                    } else {
                        Log.i(TAG, "背景小于 1.5M 使用原图路径, url===" + selectList.get(0).getPath());
                        UpDataBack(new File(selectList.get(0).getPath()));

                    }
                    break;
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Cancle();
        listener = null;
    }

    @Override
    public void onUpDataUserInfo() {
        getData(true);
    }
}
