package example.com.fan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import example.com.fan.activity.AttentionActivity;
import example.com.fan.activity.BuyCrowdActivity;
import example.com.fan.activity.BuygoodsActivity;
import example.com.fan.activity.ChoicenessActivity;
import example.com.fan.activity.CrowdActivity;
import example.com.fan.activity.HelpActivity;
import example.com.fan.activity.HomePageActivity;
import example.com.fan.activity.HostModelActivity;
import example.com.fan.activity.LoginActivity;
import example.com.fan.activity.MainActivity;
import example.com.fan.activity.MyCollectActivity;
import example.com.fan.activity.MyOrderActivity;
import example.com.fan.activity.MyOrderDetailsActivity;
import example.com.fan.activity.NewestActivity;
import example.com.fan.activity.OrderActivity;
import example.com.fan.activity.OutSideActivity;
import example.com.fan.activity.OverPayActivity;
import example.com.fan.activity.PayActivity;
import example.com.fan.activity.PersonalInfoActivity;
import example.com.fan.activity.PhotoActivity;
import example.com.fan.activity.PlayerActivity;
import example.com.fan.activity.PlayerVideoActivity;
import example.com.fan.activity.ProjectIncomeActivity;
import example.com.fan.activity.RegisterActivity;
import example.com.fan.activity.SettingActivity;
import example.com.fan.activity.TaskActivity;
import example.com.fan.activity.UnReadActivity;
import example.com.fan.activity.UploadPhotoActivity;
import example.com.fan.activity.VideoAndVrSonActivity;
import example.com.fan.activity.VipActivity;
import example.com.fan.bean.MyOrderBean;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/26.
 */
public class IntentUtils {
    private static final String TAG = getTAG(IntentUtils.class);
    /**
     * 主页面;
     *
     * @param context      上下文;
     * @param main_type
     * @param main_info_id
     */
    public static void goMainPage(Context context, String main_type, String main_info_id) {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.putExtra("main_type", main_type);
        intent.putExtra("main_info_id", main_info_id);
        intent.setAction("main_action");
        context.startActivity(intent);
    }

    /**
     * 照片查看器页面;
     *
     * @param context 上下文;
     * @param id      私照id;
     * @param i       从第几张查看;
     */
    public static void goPhotoPage(Context context, String id, int i) {

        Intent intent = new Intent(context.getApplicationContext(), PhotoActivity.class);
        intent.putExtra("photo_position", i + "");
        intent.putExtra("photo_id", id);
        context.startActivity(intent);
    }

    /**
     * 模特个人主页页面;
     *
     * @param context 上下文;
     * @param id      用户id;
     */
    public static void goHomePage(Context context, String id) {

        Intent intent = new Intent(context.getApplicationContext(), HomePageActivity.class);
        intent.putExtra("user_id", id);
        context.startActivity(intent);
    }

    /**
     * 众筹页面;
     *
     * @param context 上下文;
     * @param title   标题;
     * @param id      标题;
     */
    public static void goCrowdPage(Context context, String title, String id) {

        Intent intent = new Intent(context.getApplicationContext(), CrowdActivity.class);
        intent.putExtra("crowd_name", title);
        intent.putExtra("crowd_id", id);
        Log.i(TAG, "crowd id ======" + id);
        context.startActivity(intent);
    }

    /**
     * 购买页面;
     *
     * @param context 上下文;
     * @param id      商品id;
     */
    public static void goBuyGoodsPage(Context context, String id) {

        Intent intent = new Intent(context.getApplicationContext(), BuygoodsActivity.class);
        intent.putExtra("buy_good_id", id);
        context.startActivity(intent);
    }

    /**
     * 购买众筹页面;
     *
     * @param context 上下文;
     * @param id      id;
     */
    public static void goBuyCrowdPage(Context context, String id) {

        Intent intent = new Intent(context.getApplicationContext(), BuyCrowdActivity.class);
        intent.putExtra("b_id", id);
        context.startActivity(intent);
    }

    /**
     * 付款页面;
     *
     * @param context  上下文;
     * @param buy_num  商品数量;
     * @param moeny    商品价钱;
     * @param buy_name 商品名称;
     * @param tag      0为商品,1为众筹;
     * @param path     封面url;
     * @param info     商品信息;
     * @param id       商品id;
     */
    public static void goOrderPage(Context context, String buy_num, String buy_name, String moeny, String tag, String path, String info, String id) {

        Intent intent = new Intent(context.getApplicationContext(), OrderActivity.class);
        intent.putExtra("buy_num", buy_num);
        intent.putExtra("buy_money", moeny);
        intent.putExtra("buy_name", buy_name);
        intent.putExtra("buy_tag", tag);
        intent.putExtra("buy_cover", path);
        intent.putExtra("buy_info", info);
        intent.putExtra("buy_id", id);
        context.startActivity(intent);
    }

    /**
     * 热门模特页面;
     *
     * @param context 上下文;
     */
    public static void goHostModelPage(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), HostModelActivity.class);
        context.startActivity(intent);
    }

    /**
     * VIP页面;
     *
     * @param context 上下文;
     */
    public static void goVipPage(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), VipActivity.class);
        context.startActivity(intent);
    }

    /**
     * 最新页面;
     *
     * @param context 上下文;
     */
    public static void goNewestPage(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), NewestActivity.class);
        context.startActivity(intent);
    }

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginPage(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 精选图集页面;
     *
     * @param context 上下文;
     * @param pos
     */
    public static void goChoicenessPage(Context context, String pos) {

        Intent intent = new Intent(context.getApplicationContext(), ChoicenessActivity.class);
        intent.putExtra("page_position", "" + pos);
        context.startActivity(intent);
    }

    /**
     * 关注页面;
     *
     * @param context 上下文;
     */
    public static void goAttentionPage(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), AttentionActivity.class);
        context.startActivity(intent);
    }

    /**
     * 充值页面;
     *
     * @param context 上下文;
     */
    public static void goPayPage(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), PayActivity.class);
        context.startActivity(intent);
    }

    /**
     * 修改个人信息页面;
     *
     * @param context 上下文;
     */
    public static void goPersonInfoPage(Context context, String icon, String name, String sex, String wx) {
        Intent intent = new Intent(context.getApplicationContext(), PersonalInfoActivity.class);
        intent.putExtra("person_name", name);
        intent.putExtra("person_icon", icon);
        intent.putExtra("person_sex", sex);
        intent.putExtra("person_wx", wx);
        context.startActivity(intent);
    }

    /**
     * 上传照片页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goUploadPhotoPage(Context context, String tag) {
        Intent intent = new Intent(context.getApplicationContext(), UploadPhotoActivity.class);
        intent.putExtra("photo_flag", tag);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 注册页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goRegisterPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), RegisterActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 已购买页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goOverPayPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), OverPayActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 我的收藏页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goMyCollectPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), MyCollectActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * VR of Video 播放器页面;
     *
     * @param context 上下文;
     * @param id
     * @param flag    视频vr标识符  4=video  5=vr
     */
    public static void goPlayerPage(Context context, String id, int flag) {
        Intent intent = new Intent();
        switch (flag) {
            case 4:
                intent.setClass(context.getApplicationContext(), PlayerVideoActivity.class);
                intent.putExtra("play_id", id);
                ((Activity) context).startActivity(intent);
                break;
            case 5:
                intent.setClass(context.getApplicationContext(), PlayerActivity.class);
                intent.putExtra("play_id", id);
                ((Activity) context).startActivity(intent);
                break;

        }
    }

    /**
     * VR of Video 单独列表子页面;
     *
     * @param context 上下文;
     * @param tag
     */
    public static void goVideoOfVrPage(Context context, String tag) {
        Intent intent = new Intent(context.getApplicationContext(), VideoAndVrSonActivity.class);
        intent.putExtra("VideoVR_tag", tag);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 设置页面;
     *
     * @param context 上下文;
     */
    public static void goSettingPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), SettingActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 我的订单页面;
     *
     * @param context 上下文;
     */
    public static void goMyOrderPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), MyOrderActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 我的订单详情页面;
     *
     * @param context 上下文;
     * @param mb      实体类
     */
    public static void goMyOrderDetailsPage(Context context, MyOrderBean mb) {
        Intent intent = new Intent(context.getApplicationContext(), MyOrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MyOrderBean", mb);
        intent.putExtras(bundle);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 任务页面;
     *
     * @param context 上下文;
     */
    public static void goTaskPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), TaskActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 未读页面;
     *
     * @param context 上下文;
     */
    public static void goUnReadPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), UnReadActivity.class);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 外链页面;
     *
     * @param context 上下文;
     */
    public static void goOutsidePage(Context context, String url, String title) {
        Intent intent = new Intent(context.getApplicationContext(), OutSideActivity.class);
        intent.putExtra("outside_url", url);
        intent.putExtra("outside_title", title);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 众筹支持付款页面;
     *
     * @param context 上下文;
     */
    public static void goProjectIncomePage(Context context, String id) {
        Intent intent = new Intent(context.getApplicationContext(), ProjectIncomeActivity.class);
        intent.putExtra("Target_id", id);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 帮助页面;
     *
     * @param context 上下文;
     */
    public static void goHelpPage(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), HelpActivity.class);
        ((Activity) context).startActivity(intent);
    }
}
