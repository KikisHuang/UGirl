package example.com.fan.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.activity.Account_detailsActivity;
import example.com.fan.activity.AttentionActivity;
import example.com.fan.activity.AttestationActivity;
import example.com.fan.activity.AwardActivity;
import example.com.fan.activity.BuyCrowdActivity;
import example.com.fan.activity.BuygoodsActivity;
import example.com.fan.activity.ChoicenessActivity;
import example.com.fan.activity.CrowdActivity;
import example.com.fan.activity.HelpActivity;
import example.com.fan.activity.HomePage2Activity;
import example.com.fan.activity.HomePageActivity;
import example.com.fan.activity.HostModelActivity;
import example.com.fan.activity.LoginActivity;
import example.com.fan.activity.MainActivity;
import example.com.fan.activity.MyCollectActivity;
import example.com.fan.activity.MyOrderActivity;
import example.com.fan.activity.MyOrderDetailsActivity;
import example.com.fan.activity.MyPrivatePhotoActivity;
import example.com.fan.activity.MyVideoActivity;
import example.com.fan.activity.NewestActivity;
import example.com.fan.activity.OrderActivity;
import example.com.fan.activity.OutSideActivity;
import example.com.fan.activity.OverPayActivity;
import example.com.fan.activity.PayActivity;
import example.com.fan.activity.PersonalInfoActivity;
import example.com.fan.activity.PhotoActivity;
import example.com.fan.activity.PlayerActivity;
import example.com.fan.activity.PlayerVideoActivity;
import example.com.fan.activity.PrivatePhotoActivity;
import example.com.fan.activity.PrivateTypeActivity;
import example.com.fan.activity.ProjectIncomeActivity;
import example.com.fan.activity.RankingActivity;
import example.com.fan.activity.RegisterActivity;
import example.com.fan.activity.SGamerActivity;
import example.com.fan.activity.SearchActivity;
import example.com.fan.activity.SettingActivity;
import example.com.fan.activity.StoreActivity;
import example.com.fan.activity.SuperUserPhotoOfVideoActivity;
import example.com.fan.activity.TaskActivity;
import example.com.fan.activity.TimeSelectActivity;
import example.com.fan.activity.UnReadActivity;
import example.com.fan.activity.UploadPhotoActivity;
import example.com.fan.activity.UploadPrivatePhotoActivity;
import example.com.fan.activity.UploadWeChatActivity;
import example.com.fan.activity.UserPhotoOfVideoUpLoadActivity;
import example.com.fan.activity.VideoAndVrSonActivity;
import example.com.fan.activity.VipActivity;
import example.com.fan.bean.MyOrderBean;
import example.com.fan.bean.mcPublishImgUrls;

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
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("main_type", main_type);
        intent.putExtra("main_info_id", main_info_id);
        intent.setAction("main_action");
//        startPage(context, intent);
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

        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("photo_position", i + "");
        intent.putExtra("photo_id", id);
        startPage(context, intent);
    }

    /**
     * 照片查看器页面2（个人私照版,不判断vip）;
     *
     * @param context 上下文;
     * @param id      私照id;
     * @param i       从第几张查看;
     */
    public static void goPrivatePhotoPage(Context context, String id, int i) {

        Intent intent = new Intent(context, PrivatePhotoActivity.class);
        intent.putExtra("photo_position", i + "");
        intent.putExtra("photo_id", id);
        startPage(context, intent);
    }

    /**
     * 模特个人主页页面;
     *
     * @param context 上下文;
     * @param id      用户id;
     */
    public static void goHomePage(Context context, String id) {

        Intent intent = new Intent(context, HomePageActivity.class);
        intent.putExtra("user_id", id);
        startPage(context, intent);
    }

    /**
     * 模特个人主页2页面;
     *
     * @param context 上下文;
     * @param id      用户id;
     */
    public static void goHomePage2(Context context, String id) {

        Intent intent = new Intent(context, HomePage2Activity.class);
        intent.putExtra("user_id", id);
        startPage(context, intent);
    }

    /**
     * 活动、商城页面;
     *
     * @param context 上下文;
     */
    public static void goStorePage(Context context) {

        Intent intent = new Intent(context, StoreActivity.class);
        startPage(context, intent);
    }

    /**
     * 打赏价格设置页面;
     *
     * @param context    上下文;
     * @param selectList
     */
    public static void goAwardPage(Context context, int flag, List<String> selectList) {

        Intent intent = new Intent(context, AwardActivity.class);
        intent.putExtra("award_flag", flag + "");
        if (selectList != null)
            intent.putStringArrayListExtra("award_selectLists", (ArrayList<String>) selectList);
        ((Activity) context).startActivityForResult(intent, 7744);
//        startPage(context, intent);
    }

    /**
     * 超级玩家批量照片、视频上传页面;
     *
     * @param context    上下文;
     * @param selectList
     */
    public static void goUserPhotoOfVideoLoadPage(Context context, int flag, String FilePath, List<LocalMedia> selectList) {

        Intent intent = new Intent(context, UserPhotoOfVideoUpLoadActivity.class);
        intent.putExtra("photo_of_video_flag", "" + flag);
        intent.putExtra("photo_of_video_FilePath", "" + FilePath);
        intent.putExtra("selectLists", (Serializable) selectList);
        startPage(context, intent);
    }

    /**
     * 超级玩家批量照片、视频上传页面2;
     *
     * @param context      上下文;
     * @param selectList
     * @param specialId
     * @param chargeNumber
     */
    public static void goUserPhotoOfVideoLoadPage2(Context context, int flag, String FilePath, List<mcPublishImgUrls> selectList, String specialId, int chargeNumber) {

        Intent intent = new Intent(context, UserPhotoOfVideoUpLoadActivity.class);
        intent.putExtra("photo_of_video_flag", "" + flag);
        intent.putExtra("photo_of_video_FilePath", "" + FilePath);
        intent.putExtra("photo_specialId", "" + specialId);
        intent.putExtra("photo_of_chargeNumber", "" + chargeNumber);
        intent.putExtra("mcPublishImgUrls", (Serializable) selectList);
        startPage(context, intent);
    }

    /**
     * 超级玩家批量照片、视频选择页面;
     *
     * @param context 上下文;
     *                0相册
     *                1视频
     */
    public static void goSuperPhotoVideoPage(Context context, int flag) {

        Intent intent = new Intent(context, SuperUserPhotoOfVideoActivity.class);
        intent.putExtra("Photo_of_Video_flag", flag + "");
        startPage(context, intent);
    }

    /**
     * 众筹页面;
     *
     * @param context 上下文;
     * @param title   标题;
     * @param id      标题;
     */
    public static void goCrowdPage(Context context, String title, String id) {

        Intent intent = new Intent(context, CrowdActivity.class);
        intent.putExtra("crowd_name", title);
        intent.putExtra("crowd_id", id);
        Log.i(TAG, "crowd id ======" + id);
        startPage(context, intent);
    }

    /**
     * 购买页面;
     *
     * @param context 上下文;
     * @param id      商品id;
     */
    public static void goBuyGoodsPage(Context context, String id) {

        Intent intent = new Intent(context, BuygoodsActivity.class);
        intent.putExtra("buy_good_id", id);
        startPage(context, intent);
    }

    /**
     * 购买众筹页面;
     *
     * @param context 上下文;
     * @param id      id;
     */
    public static void goBuyCrowdPage(Context context, String id) {

        Intent intent = new Intent(context, BuyCrowdActivity.class);
        intent.putExtra("b_id", id);
        startPage(context, intent);
    }

    /**
     * 超级用户页面;
     *
     * @param context 上下文;
     */
    public static void goSGamerPage(Context context) {

        Intent intent = new Intent(context, SGamerActivity.class);
        startPage(context, intent);
    }
    /**
     * 超级用户页面;
     *
     * @param context 上下文;
     * @param typeId
     */
    public static void goPrivateTypePage(Context context, String typeId, String name) {

        Intent intent = new Intent(context, PrivateTypeActivity.class);
        intent.putExtra("private_type_typeId", typeId);
        intent.putExtra("private_type_name", name);
        startPage(context, intent);
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

        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("buy_num", buy_num);
        intent.putExtra("buy_money", moeny);
        intent.putExtra("buy_name", buy_name);
        intent.putExtra("buy_tag", tag);
        intent.putExtra("buy_cover", path);
        intent.putExtra("buy_info", info);
        intent.putExtra("buy_id", id);
        startPage(context, intent);
    }

    /**
     * 热门模特页面;
     *
     * @param context 上下文;
     */
    public static void goHostModelPage(Context context) {

        Intent intent = new Intent(context, HostModelActivity.class);
        startPage(context, intent);
    }

    /**
     * VIP页面;
     *
     * @param context 上下文;
     */
    public static void goVipPage(Context context) {

        Intent intent = new Intent(context, VipActivity.class);
        startPage(context, intent);
    }

    /**
     * 最新页面;
     *
     * @param context 上下文;
     */
    public static void goNewestPage(Context context) {

        Intent intent = new Intent(context, NewestActivity.class);
        startPage(context, intent);
    }

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginPage(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        startPage(context, intent);
    }

    /**
     * 认证页面;
     *
     * @param context 上下文;
     * @param tag     布局显示标识符;
     */
    public static void goRzPage(Context context, int tag) {

        Intent intent = new Intent(context, AttestationActivity.class);
        intent.putExtra("attesta_tag", tag + "");
        startPage(context, intent);
    }

    /**
     * 选择器页面;
     *
     * @param context 上下文;
     * @param tag     布局显示标识符;
     */
    public static void goSelectPage(Context context, int tag) {

        Intent intent = new Intent(context, TimeSelectActivity.class);
        intent.putExtra("select_tag", tag + "");
        startPage(context, intent);
    }

    /**
     * 精选图集页面;
     *
     * @param context 上下文;
     * @param pos
     */
    public static void goChoicenessPage(Context context, String pos) {

        Intent intent = new Intent(context, ChoicenessActivity.class);
        intent.putExtra("page_position", "" + pos);
        startPage(context, intent);
    }

    /**
     * 关注页面;
     *
     * @param context 上下文;
     */
    public static void goAttentionPage(Context context) {

        Intent intent = new Intent(context, AttentionActivity.class);
        startPage(context, intent);
    }

    /**
     * 充值页面;
     *
     * @param context 上下文;
     */
    public static void goPayPage(Context context) {

        Intent intent = new Intent(context, PayActivity.class);
        startPage(context, intent);
    }

    /**
     * 修改个人信息页面;
     *
     * @param context 上下文;
     */
    public static void goPersonInfoPage(Context context, String icon, String name, String sex, String wx) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        intent.putExtra("person_name", name);
        intent.putExtra("person_icon", icon);
        intent.putExtra("person_sex", sex);
        intent.putExtra("person_wx", wx);
        startPage(context, intent);
    }

    /**
     * 修改个人信息页面(带头像跳转动画方法);
     *
     * @param context 上下文;
     */
    public static void goPersonInfoPage2(Context context, String icon, String name, String sex, String wx, View v) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        intent.putExtra("person_name", name);
        intent.putExtra("person_icon", icon);
        intent.putExtra("person_sex", sex);
        intent.putExtra("person_wx", wx);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                    ((Activity) context, v, "transition_morph_view");
            context.startActivity(intent, options.toBundle());
        } else
            context.startActivity(intent);
    }

    /**
     * 上传照片页面;
     *
     * @param context 上下文;
     *                requestCode 186
     * @param cut
     */
    public static void goUploadPhotoPage(Context context, String tag, boolean cut, int page) {
        Intent intent = new Intent(context, UploadPhotoActivity.class);
        intent.putExtra("photo_flag", tag);
        intent.putExtra("photo_page", page + "");
        intent.putExtra("photo_cut", cut + "");
        startPage(context, intent);
    }

    /**
     * 注册页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goRegisterPage(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        startPage(context, intent);
    }

    /**
     * 已购买页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goOverPayPage(Context context) {
        Intent intent = new Intent(context, OverPayActivity.class);
        startPage(context, intent);
    }

    /**
     * 已购买页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goAccountDetailsPage(Context context, int flag) {
        Intent intent = new Intent(context, Account_detailsActivity.class);
        intent.putExtra("account_details_flag", flag + "");
        startPage(context, intent);
    }

    /**
     * 我的收藏页面;
     *
     * @param context 上下文;
     *                requestCode 186
     */
    public static void goMyCollectPage(Context context) {
        Intent intent = new Intent(context, MyCollectActivity.class);
        startPage(context, intent);
    }

    /**
     * VR of Video 播放器页面;
     *
     * @param context 上下文;
     * @param id
     * @param flag    视频vr标识符  4=video  5=vr   -3=私密video
     */
    public static void goPlayerPage(Context context, String id, int flag) {
        Intent intent = new Intent();
        switch (flag) {
            case 4:
                intent.setClass(context, PlayerVideoActivity.class);
                intent.putExtra("play_id", id);
                intent.putExtra("type_flag", String.valueOf(flag));
                startPage(context, intent);
                break;
            case -3:
                intent.setClass(context, PlayerVideoActivity.class);
                intent.putExtra("play_id", id);
                intent.putExtra("type_flag", String.valueOf(flag));
                startPage(context, intent);
                break;
            case 5:
                intent.setClass(context, PlayerActivity.class);
                intent.putExtra("play_id", id);
                startPage(context, intent);
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
        Intent intent = new Intent(context, VideoAndVrSonActivity.class);
        intent.putExtra("VideoVR_tag", tag);
        startPage(context, intent);
    }

    /**
     * 设置页面;
     *
     * @param context 上下文;
     */
    public static void goSettingPage(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的视频页面;
     *
     * @param context 上下文;
     */
    public static void goMyVideoPage(Context context) {
        Intent intent = new Intent(context, MyVideoActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的微信页面;
     *
     * @param context 上下文;
     */
    public static void goWeChatPage(Context context) {
        Intent intent = new Intent(context, UploadWeChatActivity.class);
        startPage(context, intent);
    }

    /**
     * 上传私照页面;
     *
     * @param context 上下文;
     */
    public static void goUpPrivatePhotoPage(Context context) {
        Intent intent = new Intent(context, UploadPrivatePhotoActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的私照页面;
     *
     * @param context 上下文;
     * @param MYID
     */
    public static void goMyPrivatePhotoPage(Context context, String MYID) {
        Intent intent = new Intent(context, MyPrivatePhotoActivity.class);
        intent.putExtra("MyPrivate_ID", MYID);
        startPage(context, intent);
    }

    /**
     * 我的订单页面;
     *
     * @param context 上下文;
     */
    public static void goMyOrderPage(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的订单详情页面;
     *
     * @param context 上下文;
     * @param mb      实体类
     */
    public static void goMyOrderDetailsPage(Context context, MyOrderBean mb) {
        Intent intent = new Intent(context, MyOrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MyOrderBean", mb);
        intent.putExtras(bundle);
        startPage(context, intent);
    }

    /**
     * 任务页面;
     *
     * @param context 上下文;
     */
    public static void goTaskPage(Context context) {
        Intent intent = new Intent(context, TaskActivity.class);
        startPage(context, intent);
    }

    /**
     * 未读页面;
     *
     * @param context 上下文;
     */
    public static void goUnReadPage(Context context) {
        Intent intent = new Intent(context, UnReadActivity.class);
        startPage(context, intent);
    }

    /**
     * 排行榜页面;
     *
     * @param context 上下文;
     */
    public static void goRankingPage(Context context, String tag) {
        Intent intent = new Intent(context, RankingActivity.class);
        intent.putExtra("Rangking_Tag", tag);
        startPage(context, intent);
    }

    /**
     * 搜索页面;
     *
     * @param context 上下文;
     */
    public static void goSearchPage(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        startPage(context, intent);
    }

    /**
     * 外链页面;
     *
     * @param context 上下文;
     */
    public static void goOutsidePage(Context context, String url, String title) {
        Intent intent = new Intent(context, OutSideActivity.class);
        intent.putExtra("outside_url", url);
        intent.putExtra("outside_title", title);
        startPage(context, intent);
    }

    /**
     * 众筹支持付款页面;
     *
     * @param context 上下文;
     */
    public static void goProjectIncomePage(Context context, String id) {
        Intent intent = new Intent(context, ProjectIncomeActivity.class);
        intent.putExtra("Target_id", id);
        startPage(context, intent);
    }

    /**
     * 帮助页面;
     *
     * @param context 上下文;
     */
    public static void goHelpPage(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        startPage(context, intent);
    }


    /**
     * 跳转通用方法;
     *
     * @param context
     * @param intent
     */
    private static void startPage(Context context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
//            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            context.startActivity(intent);
        else
            context.startActivity(intent);
    }

    /**
     * 跳转通用方法;
     *
     * @param context
     * @param intent
     */
    private static void startPage2(Context context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
        else
            context.startActivity(intent);
    }
}
