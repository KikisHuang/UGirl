package example.com.fan.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.GalleryBean;
import example.com.fan.bean.McOfficialSellImgUrls;
import example.com.fan.bean.PageTopBannerBean;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/3.
 */
public class homeViewPageUtils {

    private static final String TAG = getTAG(homeViewPageUtils.class);

    /**
     * ViewPager底部小圆点初始化;
     *
     * @param size              数据长度;
     * @param context           上下文;
     * @param mImageViewDotList 底部圆点数据集合;
     * @param mLinearLayoutDot  底部圆点布局;
     * @param dotPosition       圆点起始的位置;
     */
    public static void setDot(int size, Context context, List<ImageView> mImageViewDotList, LinearLayout mLinearLayoutDot, int dotPosition) {
        mLinearLayoutDot.removeAllViews();
        mImageViewDotList.clear();
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        params.rightMargin = 20;
        //  for循环创建images.length个ImageView（小圆点）
        for (int i = 0; i < size; i++) {
            ImageView imageViewDot = new ImageView(context);
            imageViewDot.setLayoutParams(params);
            //  设置小圆点的背景为暗红图片
            imageViewDot.setBackgroundResource(R.drawable.dot_corners_false);
            mLinearLayoutDot.addView(imageViewDot);
            mImageViewDotList.add(imageViewDot);
        }
        //设置第一个小圆点图片背景为红色
        mImageViewDotList.get(dotPosition).setBackgroundResource(R.drawable.dot_corners_true);
    }

    /**
     * 主页顶部viewPager;
     * ViewPager中图片初始化,根据flag判断需要显示的图片样式;
     *
     * @param images         图片数据源;
     * @param context        上下文;
     * @param mImageViewList 返回的结果集;
     * @return
     */
    public static List<PageTopBannerBean> getTopImg(final List<PageTopBannerBean> images, final Context context, List<PageTopBannerBean> mImageViewList, int flag,LayoutInflater inflater) {

        for (int i = 0; i < images.size() + 2; i++) {
            PageTopBannerBean pb = new PageTopBannerBean();
            ImageView imageView = null;
            View view = null;
            if (flag == 0) {
                view = inflater.inflate(R.layout.top_img_item, null);
                imageView = (ImageView) view.findViewById(R.id.top_img);
            }

            if (flag == 1) {
                view = inflater.inflate(R.layout.store_viewpager_item, null);
                imageView = (ImageView) view.findViewById(R.id.top_img1);
            }

            if (imageView != null)
                imageView.setVisibility(View.VISIBLE);

            if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景

                Glide.with(context).load(images.get(images.size() - 1).getImgUrl()).crossFade(200).into(imageView);

                pb.setType(images.get(images.size() - 1).getType());
                pb.setId(images.get(images.size() - 1).getId());
                pb.setView(view);
                pb.setUid(images.get(images.size() - 1).getUid());
                pb.setHttpUrl(images.get(images.size() - 1).getHttpUrl());
                pb.setValue(images.get(images.size() - 1).getValue());

                mImageViewList.add(pb);
            } else if (i == images.size() + 1) {   //判断当i=images.length+1时为该处的ImageView设置第一张图片作为背景
//                imageView = setImg(context);
                Glide.with(context).load(images.get(0).getImgUrl()).crossFade(200).into(imageView);

                pb.setType(images.get(0).getType());
                pb.setId(images.get(0).getId());
                pb.setView(view);
                pb.setUid(images.get(0).getUid());
                pb.setHttpUrl(images.get(0).getHttpUrl());
                pb.setValue(images.get(0).getValue());

                mImageViewList.add(pb);
            } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
//                imageView = setImg(context);
                Glide.with(context).load(images.get(i - 1).getImgUrl()).crossFade(200).into(imageView);

                pb.setType(images.get(i - 1).getType());
                pb.setId(images.get(i - 1).getId());
                pb.setView(view);
                pb.setUid(images.get(i - 1).getUid());
                pb.setHttpUrl(images.get(i - 1).getHttpUrl());
                pb.setValue(images.get(i - 1).getValue());

                mImageViewList.add(pb);
            }
        }

        return mImageViewList;
    }

    /**
     * 一口价页面;
     * ViewPager中图片初始化,根据flag判断需要显示的图片样式;
     *
     * @param images  图片数据源;
     * @param context 上下文;
     * @return
     */
    public static List<McOfficialSellImgUrls> getBuyTopImg(final List<McOfficialSellImgUrls> images, final Context context,LayoutInflater inflater) {
        // @param    mImageViewList 返回的结果集;
        List<McOfficialSellImgUrls> mImageViewList = new ArrayList<>();

        for (int i = 0; i < images.size() + 2; i++) {
            McOfficialSellImgUrls pb = new McOfficialSellImgUrls();
            ImageView imageView = null;
            View view = null;
            view = inflater.inflate(R.layout.buy_top_img_item, null);
            imageView = (ImageView) view.findViewById(R.id.top_img);

            if (imageView != null)
                imageView.setVisibility(View.VISIBLE);

            if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景

                Glide.with(context.getApplicationContext()).load(images.get(images.size() - 1).getPath()).override(1920, 1080).crossFade(300).into(imageView);

                pb.setPath(images.get(images.size() - 1).getPath());
                pb.setId(images.get(images.size() - 1).getId());
                pb.setUid(images.get(images.size() - 1).getUid());
                pb.setView(view);

                mImageViewList.add(pb);
            } else if (i == images.size() + 1) {

                Glide.with(context.getApplicationContext()).load(images.get(0).getPath()).crossFade(300).override(1920, 1080).into(imageView);

                pb.setPath(images.get(0).getPath());
                pb.setId(images.get(0).getId());
                pb.setView(view);
                pb.setUid(images.get(0).getUid());

                mImageViewList.add(pb);
            } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
//                imageView = setImg(context);
                Glide.with(context.getApplicationContext()).load(images.get(i - 1).getPath()).crossFade(300).override(1920, 1080).into(imageView);

                pb.setPath(images.get(i - 1).getPath());
                pb.setId(images.get(i - 1).getId());
                pb.setView(view);
                pb.setUid(images.get(i - 1).getUid());

                mImageViewList.add(pb);
            }
        }

        return mImageViewList;
    }

    /**
     * 测试用;
     *
     * @param images
     * @param context
     * @param mImageViewList
     * @param flag
     * @return
     */
    public static List<View> getImg(final String[] images, final Context context, List<View> mImageViewList, int flag) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        for (int i = 0; i < images.length + 2; i++) {
            ImageView imageView = null;
            View view = null;
            if (flag == 0) {
                view = inflater.inflate(R.layout.top_img_item, null);
                imageView = (ImageView) view.findViewById(R.id.top_img);
            }

            if (flag == 1) {
                view = inflater.inflate(R.layout.store_viewpager_item, null);
                imageView = (ImageView) view.findViewById(R.id.top_img1);
            }

            if (imageView != null)
                imageView.setVisibility(View.VISIBLE);

            if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景
//                imageView = setImg(context);
                Glide.with(context).load(images[images.length - 1]).fitCenter().crossFade(300).into(imageView);
//                GlideImgUtils.loadIntoUseFitWidth(context, images[images.length - 1], 0, imageView);
                mImageViewList.add(view);
            } else if (i == images.length + 1) {   //判断当i=images.length+1时为该处的ImageView设置第一张图片作为背景
//                imageView = setImg(context);
                Glide.with(context).load(images[0]).fitCenter().crossFade(300).into(imageView);
//                GlideImgUtils.loadIntoUseFitWidth(context, images[0], 0, imageView);
                mImageViewList.add(view);
            } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
//                imageView = setImg(context);
                Glide.with(context).load(images[i - 1]).fitCenter().crossFade(300).into(imageView);
//                GlideImgUtils.loadIntoUseFitWidth(context, images[i - 1], 0, imageView);
                mImageViewList.add(view);
            }
        }

        return mImageViewList;
    }

    /**
     * 主页画廊ViewPager实现无限循环方法;
     *
     * @param images  图片数据源;
     * @param context 上下文;
     * @return
     */
    public static List<GalleryBean> getbottomImg(final List<GalleryBean> images, final Context context,LayoutInflater inflater) {

        List<GalleryBean> mImageViewList = new ArrayList<>();
        for (int i = 0; i < images.size() + 2; i++) {
            GalleryBean gb = bottompageLayout(images, context, inflater, i);
            mImageViewList.add(gb);
        }

        return mImageViewList;
    }

    private static GalleryBean bottompageLayout(List<GalleryBean> blist, Context context, LayoutInflater inflater, int i) {
        GalleryBean gb = new GalleryBean();
        LinearLayout ll;
        View view = inflater.inflate(R.layout.bottom_page_item, null);
        view.findViewById(R.id.six_layout).setVisibility(View.GONE);
        view.findViewById(R.id.three_layout).setVisibility(View.GONE);
        view.findViewById(R.id.four_layout).setVisibility(View.GONE);
        view.findViewById(R.id.five_layout).setVisibility(View.GONE);
        /**
         * 实现ViewPager的无限循环;
         */
        if (i == 0)
            i = blist.size() - 1;
        else if (i == blist.size() + 1)
            i = 0;
        else
            i -= 1;
        /**
         * 根据数组中的子数组的size判断显示的布局：
         */
        switch (blist.get(i).getMcPublishRecords().size()) {
            case 6:
                ll = (LinearLayout) view.findViewById(R.id.six_layout);
                ll.setVisibility(View.VISIBLE);
                sixParams(ll, context, blist, view, i);
                break;
            case 3:
                ll = (LinearLayout) view.findViewById(R.id.three_layout);
                ll.setVisibility(View.VISIBLE);
                threeParams(ll, context, blist, view, i);
                break;
            case 4:
                ll = (LinearLayout) view.findViewById(R.id.four_layout);
                ll.setVisibility(View.VISIBLE);
                fourParams(ll, context, blist, view, i);
                break;
            case 5:
                ll = (LinearLayout) view.findViewById(R.id.five_layout);
                ll.setVisibility(View.VISIBLE);
                fiveParams(ll, context, blist, view, i);
                break;
        }
        gb.setId(blist.get(i).getId());
        gb.setTypeName(blist.get(i).getTypeName());
        gb.setMcPublishRecords(blist.get(i).getMcPublishRecords());
        gb.setView(view);
        return gb;
    }

    /**
     * 6张图片布局;
     *
     * @param ll
     * @param context
     * @param blist
     * @param view
     * @param i
     */
    private static void sixParams(LinearLayout ll, Context context, List<GalleryBean> blist, View view, int i) {
        TextView tv = (TextView) view.findViewById(R.id.title_tv);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.item_image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.item_image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.item_image3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.item_image4);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.item_image5);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.item_image6);
        boolean flag;
        if (blist.get(i).getId() == 3)
            flag = true;
        else
            flag = false;


        int width = (int) (DeviceUtils.getWindowWidth(context) * 2.3 / 10);
        int height = (int) (width * 2.7);
        Log.i(TAG, "width======" + width);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(width * 2, height);
        ll.setLayoutParams(lp1);

        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(width - 20, width - 20, 1.0f);
        for (int j = 0; j < blist.get(i).getMcPublishRecords().size(); j++) {

            if (j == 0 || j % 2 == 0) {
                params.rightMargin = DeviceUtils.dip2px(context, 3);
            } else
                params.leftMargin = DeviceUtils.dip2px(context, 3);

            if (j > 1)
                params.topMargin = DeviceUtils.dip2px(context, 5);

            switch (j) {
                case 0:
                    imageView1.setLayoutParams(params);
                    setBitmap2(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView1, context, flag);
                    break;
                case 1:
                    imageView2.setLayoutParams(params);
                    setBitmap2(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView2, context, flag);
                    break;
                case 2:

                    imageView3.setLayoutParams(params);
                    setBitmap2(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView3, context, flag);

                    break;
                case 3:

                    imageView4.setLayoutParams(params);
                    setBitmap2(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView4, context, flag);
                    break;
                case 4:
                    imageView5.setLayoutParams(params);
                    setBitmap2(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView5, context, flag);

                    break;
                case 5:
                    imageView6.setLayoutParams(params);
                    setBitmap2(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView6, context, flag);
                    break;
            }
        }

        String title = "";
        title = blist.get(i).getTypeName() + ">>";
        tv.setText(title);
    }

    /**
     * 3张图片布局;
     *
     * @param ll
     * @param context
     * @param blist
     * @param view
     * @param i
     */
    private static void threeParams(LinearLayout ll, Context context, List<GalleryBean> blist, View view, int i) {
        TextView tv = (TextView) view.findViewById(R.id.title_tv);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.three_item_image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.three_item_image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.three_item_image3);

        int width = (int) (DeviceUtils.getWindowWidth(context) * 2.3 / 10);
        int height = (int) (width * 2.7);
        Log.i(TAG, "width======" + width);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(width * 2, height);
        ll.setLayoutParams(lp1);

        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(width - 20, width - 20, 1.0f);
        for (int j = 0; j < blist.get(i).getMcPublishRecords().size(); j++) {

            if (j > 1)
                params.topMargin = DeviceUtils.dip2px(context, 5);

            switch (j) {
                case 0:
                    imageView1.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView1, context);
                    break;
                case 1:
                    imageView2.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView2, context);
                    break;
                case 2:
                    imageView3.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView3, context);
                    break;
                case 3:
            }
        }
        String title = "";
        title = blist.get(i).getTypeName() + ">>";

        tv.setText(title);

    }

    /**
     * 4张图片布局;
     *
     * @param ll
     * @param context
     * @param blist
     * @param view
     * @param i
     */
    private static void fourParams(LinearLayout ll, Context context, List<GalleryBean> blist, View view, int i) {
        TextView tv = (TextView) view.findViewById(R.id.title_tv);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.four_item_image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.four_item_image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.four_item_image3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.four_item_image4);

        int width = (int) (DeviceUtils.getWindowWidth(context) * 2.3 / 10);
        int height = (int) (width * 2.7);
        Log.i(TAG, "width======" + width);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(width * 2, height);
        ll.setLayoutParams(lp1);

        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(width - 20, width - 20, 1.0f);
        for (int j = 0; j < blist.get(i).getMcPublishRecords().size(); j++) {

            if (j == 0) {
                params.rightMargin = DeviceUtils.dip2px(context, 3);
            }
            if (j == 1)
                params.leftMargin = DeviceUtils.dip2px(context, 3);

            if (j > 1)
                params.topMargin = DeviceUtils.dip2px(context, 5);

            switch (j) {
                case 0:
                    imageView1.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView1, context);
                    break;
                case 1:
                    imageView2.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView2, context);
                    break;
                case 2:

                    imageView3.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView3, context);

                    break;
                case 3:

                    imageView4.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView4, context);
                    break;

            }
        }

        String title = "";
        title = blist.get(i).getTypeName() + ">>";
        tv.setText(title);
    }

    /**
     * 5张图片布局;
     *
     * @param ll
     * @param context
     * @param blist
     * @param view
     * @param i
     */
    private static void fiveParams(LinearLayout ll, Context context, List<GalleryBean> blist, View view, int i) {
        TextView tv = (TextView) view.findViewById(R.id.title_tv);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.five_item_image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.five_item_image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.five_item_image3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.five_item_image4);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.five_item_image5);

        int width = (int) (DeviceUtils.getWindowWidth(context) * 2.3 / 10);
        int height = (int) (width * 2.7);
        Log.i(TAG, "width======" + width);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(width * 2, height);
        ll.setLayoutParams(lp1);

        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(width - 20, width - 20, 1.0f);
        for (int j = 0; j < blist.get(i).getMcPublishRecords().size(); j++) {

            if (j == 0) {
                params.rightMargin = DeviceUtils.dip2px(context, 3);
            }
            if (j == 1)
                params.leftMargin = DeviceUtils.dip2px(context, 3);

            if (j > 1)
                params.topMargin = DeviceUtils.dip2px(context, 5);

            switch (j) {
                case 0:
                    imageView1.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView1, context);
                    break;
                case 1:
                    imageView2.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView2, context);
                    break;
                case 2:

                    imageView3.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView3, context);

                    break;
                case 3:

                    imageView4.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView4, context);
                    break;
                case 4:
                    imageView5.setLayoutParams(params);
                    setBitmap(blist.get(i).getMcPublishRecords().get(j).getCoverPath(), imageView5, context);
                    break;

            }
        }

        String title = "";
        title = blist.get(i).getTypeName() + ">>";
        tv.setText(title);
    }

    private static void setBitmap(String str, ImageView img, Context context) {
        Glide.with(context)
                .load(str)
                .override(320,320)
                .into(img);
    }

    /**
     * vip 模糊设置;
     *
     * @param str
     * @param img
     * @param context
     * @param flag
     */
    private static void setBitmap2(String str, ImageView img, Context context, boolean flag) {
        if (flag) {
            Glide.with(context)
                    .load(str)
                    .centerCrop()
                    .bitmapTransform(new BlurTransformation(context, 35))
                    .into(img);
        } else {
            Glide.with(context)
                    .load(str)
                    .override(300,300)
                    .into(img);
        }
    }
}
