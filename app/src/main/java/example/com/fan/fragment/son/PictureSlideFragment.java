package example.com.fan.fragment.son;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.w3c.dom.Text;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.activity.PhotoActivity;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.PageTopBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.PayRefreshListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import okhttp3.Call;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static example.com.fan.utils.BannerUtils.goBannerPage;
import static example.com.fan.utils.IntentUtils.goOutsidePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonSring;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class PictureSlideFragment extends BaseFragment implements PayRefreshListener, View.OnClickListener {
    private static final String TAG = getTAG(PictureSlideFragment.class);
    private String url;
    private PageTopBean adv_url;
    private PhotoView imageView;
    private FrameLayout bottom_Advertisement_bar_layout;
    private ImageView bottom_Advertisement_bar;
    private TextView adver_close_tv;
    private PhotoViewAttacher photoViewAttacher;
    private boolean need;
    private String id = "";
    private ImageView load_img;
    private GestureDetector.OnDoubleTapListener gest;
    public static PayRefreshListener PayListener;

    /**
     * 动态创建Fragment,防止OOM;
     *
     * @param path      原始路径
     * @param base      OSS路径
     * @param needMoney 收费标识符
     * @param id        专辑id
     * @param isadv
     * @return
     */
    public static PictureSlideFragment newInstance(String path, String base, boolean needMoney, String id, PageTopBean isadv) {

        if (!MzFinal.isPay && needMoney && !MyAppcation.VipFlag) {
            if (PhotoActivity.tlistener != null)
                PhotoActivity.tlistener.CloseOfOpenTouch(false);
        }

        PictureSlideFragment f = new PictureSlideFragment();
        Bundle args = new Bundle();
        /**
         * 判断服务端返回的收费标识符，选择路径;
         */
        if (needMoney)
            args.putString("url", base.substring(base.lastIndexOf("/") + 1, base.length()));
        else
            args.putString("url", path);

        if (isadv != null && MzFinal.AlbumENDAdvertShow)
            args.putSerializable("adv_url", isadv);
        else
            args.putSerializable("adv_url", null);

        args.putString("id", id);
        args.putBoolean("needMoney", needMoney);

        f.setArguments(args);


        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments() != null ? getArguments().getString("url") : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494050810732&di=12648ae1cfe05df8bf586bd65c60961f&imgtype=0&src=http%3A%2F%2Fres2.esf.leju.com%2Fesf_www%2Fstatics%2Fimages%2Fdefault-img%2Fdetail.png";
        adv_url = getArguments() != null ? (PageTopBean) getArguments().getSerializable("adv_url") : null;
        need = getArguments() != null ? getArguments().getBoolean("needMoney") : false;
        id = getArguments() != null ? getArguments().getString("id") : "";
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_picture_slide;
    }

    private void getData() {

        load_img.setVisibility(View.VISIBLE);
        Glide.with(getActivity().getApplicationContext()).asGif().load(R.drawable.loading_gif).into(load_img);

        /**
         * 收费图片从oss中获取;
         */
        if (need) {
            /**
             * 获取加密照片;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.PHOTOAUTHENTICATION)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams(MzFinal.ID, id)
                    .addParams("imageName", url)
                    .tag(this)
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
                                    /**
                                     * 收费判断,如未购买并且不等于vip,ViewPager禁止滑动并且弹窗;
                                     */
                                    if (PhotoActivity.tlistener != null)
                                        PhotoActivity.tlistener.CloseOfOpenTouch(true);

                                    ReadImg(getJsonSring(response));

                                } else if (code == 0) {
                                    if (PhotoActivity.tlistener != null)
                                        PhotoActivity.tlistener.CloseOfOpenTouch(false);
                                    ReadImg(url);
                                } else
                                    ToastUtil.ToastErrorMsg(getActivity(), response, code);
                            } catch (Exception e) {

                            }
                        }
                    });

        } else {
            if (PhotoActivity.tlistener != null)
                PhotoActivity.tlistener.CloseOfOpenTouch(true);
            ReadImg(url);
        }
    }

    /**
     * 图片显示方法
     *
     * @param url 路径;
     */
    private void ReadImg(String url) {
        try {
            RequestOptions op = new RequestOptions();
            op.fitCenter().error(R.drawable.load_fail_img);
            Glide.with(getActivity().getApplicationContext())
                    .load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    load_img.setVisibility(View.GONE);
                    return false;
                }
            }).apply(op).into(imageView);

        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
    }

    @Override
    protected void click() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        gest = null;
        PayListener = null;
        photoViewAttacher.cleanup();
        photoViewAttacher = null;
    }

    @Override
    protected void init() {
        imageView = (PhotoView) view.findViewById(R.id.iv_main_pic);
        bottom_Advertisement_bar_layout = (FrameLayout) view.findViewById(R.id.bottom_Advertisement_bar_layout);
        bottom_Advertisement_bar = (ImageView) view.findViewById(R.id.bottom_Advertisement_bar);
        adver_close_tv = (TextView) view.findViewById(R.id.adver_close_tv);

        load_img = (ImageView) view.findViewById(R.id.load_img);
        photoViewAttacher = new PhotoViewAttacher(imageView);
        PayListener = this;

        if (adv_url != null) {
            url = adv_url.getImgUrl();
            getBottomBanner();
            adver_close_tv.setOnClickListener(this);
        } else bottom_Advertisement_bar_layout.setVisibility(view.GONE);


        photoViewAttacher.setOnDoubleTapListener(gest = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i(TAG, "onSingleTapConfirmed  id ==" + id);
                Log.i(TAG, "adv_url   ==" + adv_url);
                if (PhotoActivity.slistener != null && adv_url != null)
                    goBannerPage(getActivity(), adv_url.getType(), adv_url.getHttpUrl(), adv_url.getValue());

                if (PhotoActivity.slistener != null && adv_url == null)
                    PhotoActivity.slistener.onShowOfHide();

                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (photoViewAttacher == null)
                    return false;

                try {
                    float scale = photoViewAttacher.getScale();//获取当前缩放值
                    float x = e.getX();
                    float y = e.getY();
                    //PhotoView预定义了3种缩放大小，大，中，小，可以实际使用感受下，双击后先缩放到中，再双击缩放到大，再双击缩放到小。
                    if (scale < photoViewAttacher.getMediumScale()) {//当前缩放为小，变换为中
                        photoViewAttacher.setScale(photoViewAttacher.getMediumScale(), x, y, true);
                    } else if (scale >= photoViewAttacher.getMediumScale() && scale < photoViewAttacher.getMaximumScale()) {//当前缩放为中，变化为大
                        photoViewAttacher.setScale(photoViewAttacher.getMinimumScale(), x, y, true);
                    }/* else {//当前缩放为大，变换为小
                        photoViewAttacher.setScale(photoViewAttacher.getMinimumScale(), x, y, true);
                    }*/
                } catch (ArrayIndexOutOfBoundsException e1) {
                    // Can sometimes happen when getX() and getY() is called
                }

                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.i(TAG, "onDoubleTapEvent");
                return false;
            }
        });

    }

    private void getBottomBanner() {
        /**
         * 底部广告接口;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "7")
                .tag(this)
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
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() > 0) {
                                    bottom_Advertisement_bar_layout.setVisibility(view.VISIBLE);
                                    final PageTopBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(0)), PageTopBean.class);
                                    Glide.with(getActivity()).load(rb.getImgUrl()).into(bottom_Advertisement_bar);
                                    MzFinal.AdvertisementIsShow = true;
                                    bottom_Advertisement_bar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goBannerPage(getActivity(), rb.getType(), rb.getHttpUrl(), rb.getValue());
                                        }
                                    });
                                } else
                                    MzFinal.AdvertisementIsShow = false;
                            }
                        } catch (Exception e) {
                            MzFinal.AdvertisementIsShow = false;
                        }
                    }
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (!isVisibleToUser) {
//                if (photoViewAttacher != null)
//                    photoViewAttacher.setScale(photoViewAttacher.getMinimumScale(), 0, 0, true);
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void initData() {

        getData();
    }

    @Override
    public void onPayRefresh() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adver_close_tv:
                bottom_Advertisement_bar_layout.setVisibility(view.GONE);
                break;

        }
    }
}