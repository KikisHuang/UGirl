package example.com.fan.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuView;

import example.com.fan.R;

import static example.com.fan.utils.AnimationUtil.TitleAnima;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/19.
 * 各种标题方法类;
 */
public class TitleUtils {
    private static final String TAG = getTAG(TitleUtils.class);

    /**
     * 子页面标题设置;
     *
     * @param ac
     * @param str
     */
    public static void setTitles(final Activity ac, String str) {
        TextView textView = (TextView) ac.findViewById(R.id.title_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setText(str);
        hideView(ac);
        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
    }

    /**
     * 子页面标题设置2(同时改变颜色);
     *
     * @param ac
     * @param str
     */
    public static void setTitlesAndColors(final Activity ac, String str, int color) {
        TextView textView = (TextView) ac.findViewById(R.id.title_tv);
        RelativeLayout rl = (RelativeLayout) ac.findViewById(R.id.title_rl);
        textView.setVisibility(View.VISIBLE);
        rl.setBackgroundResource(color);
        textView.setText(str);
        hideView(ac);
        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
    }

    /**
     * 子页面透明标题设置;
     *
     * @param ac
     */
    public static void setTitles2(final Activity ac) {
        TextView textView = (TextView) ac.findViewById(R.id.title_tv);
        RelativeLayout title_rl = (RelativeLayout) ac.findViewById(R.id.title_rl);
        textView.setVisibility(View.GONE);
        hideView(ac);
        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
    }

    /**
     * 首页面标题设置;
     *
     * @param ac
     */

    public static void PageImagTitle(Activity ac) {
        ImageView title_img = (ImageView) ac.findViewById(R.id.title_img);
        title_img.setVisibility(View.VISIBLE);
        TextView textView = (TextView) ac.findViewById(R.id.title_tv);
        textView.setVisibility(View.GONE);
    }

    /**
     * 主页面标题设置;
     *
     * @param ac
     * @param str
     */
    public static void hideImagTitle(Activity ac, String str) {
        ImageView title_img = (ImageView) ac.findViewById(R.id.title_img);
        MaterialMenuView silide_img = (MaterialMenuView) ac.findViewById(R.id.material_menu_button);
        ImageView search_img = (ImageView) ac.findViewById(R.id.search_img);
        if (silide_img.getVisibility() == View.GONE || search_img.getVisibility() == View.GONE) {
            search_img.setVisibility(View.VISIBLE);
            silide_img.setVisibility(View.VISIBLE);
        }
        title_img.setVisibility(View.GONE);
        TextView textView = (TextView) ac.findViewById(R.id.title_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setText(str);
    }

    /**
     * 标题隐藏;
     *
     * @param ac
     */
    public static void hideTitle(Activity ac) {
        RelativeLayout view = (RelativeLayout) ac.findViewById(R.id.main_title_layout);
        view.setVisibility(View.GONE);
    }

    /**
     * 标题显示;
     *
     * @param ac
     */
    public static void ShowTitle(Activity ac) {
        RelativeLayout view = (RelativeLayout) ac.findViewById(R.id.main_title_layout);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * View控件隐藏;
     *
     * @param ac
     */
    public static void hideView(Activity ac) {
        MaterialMenuView silide_img = (MaterialMenuView) ac.findViewById(R.id.material_menu_button);
        ImageView search_img = (ImageView) ac.findViewById(R.id.search_img);
        ImageView title_img = (ImageView) ac.findViewById(R.id.title_img);
        silide_img.setVisibility(View.GONE);
        search_img.setVisibility(View.GONE);
        title_img.setVisibility(View.GONE);
    }

    /**
     * 标题布局改变;
     * flag 是否是大标题;
     * tag 显示logo还是文字;
     * name 标题名称;
     *
     * @param ac
     */

    public static void ChangeTitleLayout(final Activity ac, boolean flag, int tag, String name) {
        RelativeLayout title = (RelativeLayout) ac.findViewById(R.id.title);
        ImageView mimg = (ImageView) ac.findViewById(R.id.title_img);
        MaterialMenuView slide = (MaterialMenuView) ac.findViewById(R.id.material_menu_button);
        ImageView search = (ImageView) ac.findViewById(R.id.search_img);
        TextView mtv = (TextView) ac.findViewById(R.id.title_tv);
        if (flag) {
            TitleAnima(ac, title, flag, mtv, slide, search);
            //文字标题;
            if (tag == 0) {
                mimg.setVisibility(View.VISIBLE);
                mtv.setVisibility(View.GONE);
            }
            //动画标题;
            if (tag == 1) {
                mtv.setVisibility(View.VISIBLE);
                mimg.setVisibility(View.GONE);
                mtv.setText(name);
            }
        } else {
            if (tag == 3) {
                ViewGroup.LayoutParams lp =  title.getLayoutParams();
                lp.height = DeviceUtils.dip2px(ac,50);
                title.setLayoutParams(lp);
                //无动画放大方法,图片标题;
                slide.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                mimg.setVisibility(View.VISIBLE);
                mtv.setVisibility(View.GONE);

            } else if (tag == 4) {
                ViewGroup.LayoutParams lp = title.getLayoutParams();
                lp.height = DeviceUtils.dip2px(ac,50);
                title.setLayoutParams(lp);
                //无动画放大方法,文字标题;
                slide.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                mtv.setVisibility(View.VISIBLE);
                mimg.setVisibility(View.GONE);
            } else {
                //动画放大方法;
                TitleAnima(ac, title, flag, mtv, slide, search);
                //图片标题;
                if (tag == 0) {
                    mimg.setVisibility(View.VISIBLE);
                    mtv.setVisibility(View.GONE);
                }
                //文字标题;
                if (tag == 1) {
                    mtv.setVisibility(View.VISIBLE);
                    mimg.setVisibility(View.GONE);
                    mtv.setText(name);
                }
            }
        }
    }
}
