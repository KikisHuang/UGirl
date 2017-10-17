package example.com.fan.utils;

import example.com.fan.R;

/**
 * Created by lian on 2017/4/24.
 */
public class MzFinal {
    /**
     * 主路径
     */
//  public static final String URl = "http://192.168.1.224:8080/mcFnsInterface/";
//  public static final String URl = "http://101.37.86.15:8080/mcFnsInterface/";

    public static final String URl = "http://fns.mozu123.com:8080/mcFnsInterface/";

//    public static final String URl = "http://fnsandroid.mozu123.com/mcFnsInterface/";
//    public static final String URl = "http://fnsandroid2.mozu123.com/mcFnsInterface/";
//    public static final String URl = "http://fnsandroid3.mozu123.com/mcFnsInterface/";

    /**
     * 全局广告触摸时间变量;
     */
    public static long TouchTime = 0;
    public static final String KEY = "key";
    public static final String APPID = "appId";
    public static final String PAGE = "page";
    public static final String SIZE = "pageSize";
    public static final String TYPE = "type";
    public static final String ID = "id";
    public static final String COUNT = "count";
    public static final String CONTENT = "content";
    public static final String typeFlag = "typeFlag";

    public static String MYID = "";

    public static final String br = "<br />";
    //支付宝支付接口存放数组;
    public static String[] Alis = {MzFinal.ALIPAYCROWDFUNDING, MzFinal.ALIPAYOFFICIALSELL, MzFinal.ALIPAYPHOTO, MzFinal.ALIPAYVIP, MzFinal.ALIPAYMODELWX, MzFinal.ALIPAYRECHARGE};


    //尤女映画、支付宝APPID;
    public static final String AliAPPID2 = "2017082508371443";
    //尤女映画、微信APPID;
    public static final String WECHATAPPID2 = "wxc5790f30f6da0d43";

    //尤女郎、支付宝APPID;
    public static final String AliAPPID1 = "2017061207472579";
    //尤女郎、微信APPID;
    public static final String WECHATAPPID1 = "wx34d169cf97a82205";
    //小课堂;
    public static final String xiaoketang = "wx0703fd5e9c59b6b0";

    /**
     * <p/>
     * 尤女映画 WECHAT：wxc5790f30f6da0d43
     * 尤女映画 QQ：101409825
     */
    public static final String QQLOGIN2 = "101409825";
    /**
     * 微信支付AppID;
     * 尤女郎 WECHAT：wx34d169cf97a82205;
     * 尤女郎 QQ：101408068;
     */
    public static final String QQLOGIN1 = "101408068";
    /**
     * 模特标识符
     */
    public static boolean MODELFLAG = false;

    //客户端判断;
    public static boolean getAPPID(String pk) {
        if (pk.equals("example.com.yinhua"))
            return true;
        else return false;
    }

    //微信支付、支付宝支付方式判断;
    public static boolean AlisOfWecha(String url) {
        for (String u : Alis) {
            if (u.equals(url))
                return true;
        }
        return false;
    }

    /**
     * 专辑购买标识符;
     */
    public static boolean isPay = false;

    //关于我们外链;
    public static final String AS_WE = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/system/about.png";

    /**
     * Main界面底部菜单icon数组;
     */
    public static int[] main_bottom = {R.mipmap.page_bottom, R.mipmap.photo_bottom, R.mipmap.vr_bottom, R.mipmap.store_bottom, R.mipmap.my_bottom};

    public static int[] main_un_bottom = {R.mipmap.unpage_bottom, R.mipmap.unphoto_bottom, R.mipmap.unvr_bottom, R.mipmap.unstore_bottom, R.mipmap.unmy_bottom};

    //刷新动画
    public static int[] refreshAnimSrcs = new int[]{R.mipmap.ref6, R.mipmap.ref7, R.mipmap.ref8, R.mipmap.ref9, R.mipmap.ref10, R.mipmap.ref11, R.mipmap.ref12, R.mipmap.ref13};
    //下拉动画
    public static int[] pullAnimSrcs = new int[]{R.mipmap.ref1, R.mipmap.ref2, R.mipmap.ref3, R.mipmap.ref4, R.mipmap.ref5};
    //上滑动画
    public static int[] loadingAnimSrcs = new int[]{R.drawable.mt_loading01, R.drawable.mt_loading02};

    public static int[] privatePhotoColors = {R.color.private_random_color1, R.color.private_random_color2, R.color.private_random_color3};

    public static int[] pay_colors = {R.color.pay_color1, R.color.pay_color2, R.color.pay_color3};
    /**
     * 推送自定义常量
     */
    public static final int PRIVATEPHOTO = 0;   // 私照
    public static final int APRICE = 1;    // 一口价
    public static final int CROWDFUNDING = 2;// 众筹
    public static final int AUCTION = 3;  // 拍卖
    public static final int VIDEO = 4; // 视频
    public static final int VRVIDEO = 5;  // VR视频
    public static final int PRIVATEVIDEO = -3;  // 私密视频
    public static final int PRIVATE_PHOTO = -2;  // 私密照片

    /**
     * 收藏接口
     */
    public static final String COLLECTIONPHOTO = "photo/collectionPhoto.app";
    /**
     * 收藏视频接口
     */
    public static final String COLLECTIONVIDEO = "video/collectionVideo.app";

    /**
     * 获取所有私照的类型
     */
    public static final String GETPHOTOTYPE = "photo/getPhotoType.app";
    /**
     * 根据类型获取该类型下面的私照
     */
    public static final String GETPHOTOBYTYPE = "photo/getPhotoByType.app";
    /**
     * 默认账号验证码登录接口，如果库中不存在该账号会自动注册然后自动登陆
     */
    public static final String DEFAULT = "login/default.app";
    /**
     * 第三方qq登录接口
     */
    public static final String QQ = "login/qq.app";
    /**
     * 第三方微信登录接口
     */
    public static final String WECHAT = "login/wx.app";
    /**
     * 获取验证码接口
     */
    public static final String CREATECODE = "common/createCode.app";
    /**
     * 根据用户ID查找该用户的基础信息
     */
    public static final String GETUSERBYID = "user/getUserById.app";
    /**
     * 获取广告接口
     */
    public static final String GETBANNER = "banner/getBanner.app";
    /**
     * 获取指定榜单中排行最高的用户
     */
    public static final String GETTOPRANKING = "ranking/getTopRanking.app";
    /**
     * 首页画廊效果banner 接口, 默认每组1-6个
     */
    public static final String GETHOMEBANNERPHOTOBYTYPE = "photo/getHomeBannerPhotoByType.app";
    /**
     * 获取首页推荐的写真
     */
    public static final String GETPHOTOBYHOME = "photo/getPhotoByHome.app";
    /**
     * 写真照片查看页面数据获取接口
     */
    public static final String GETDETAILS = "photo/getDetails.app";
    /**
     * 发送评论接口
     */
    public static final String ADDPHOTOCOMMENT = "photo/addPhotoComment.app";
    /**
     * VR/视频发送评论接口
     */
    public static final String ADDVRVIDEOCOMMENT = "video/addVRVideoComment.app";
    /**
     * 获取商城商品列表数据接口
     */
    public static final String GETSHOPPINGMALL = "shoppingMall/getShoppingMall.app";
    /**
     * 获取商城活动列表数据接口
     */
    public static final String GETSHOPPINGMALLACTIVITIES = "shoppingMall/getShoppingMallActivities.app";
    /**
     * 欢迎界面图片接口
     */
    public static final String GETWELCOMEIMGURL = "common/getWelcomeImgUrl.app";
    /**
     * 获取主页视频/VR接口
     */
    public static final String GETALLVIDEO = "video/getAllVideo.app";
    /**
     * 获取视频页面接口
     */
    public static final String GETVIDEO = "video/getVideo.app";
    /**
     * 获取VR页面接口
     */
    public static final String GETVRVIDEO = "video/getVRVideo.app";
    /**
     * 获取评论接口
     * 发送参数 type=类型: 0私照 1一口价 2众筹 3拍卖 4视频 5VR
     */
    public static final String GETMYCOMMENT = "comment/getComment.app";
    /**
     * 收藏人数接口
     */
    public static final String GETCOLLECTIONUSER = "photo/getCollectionUser.app";
    /**
     * 获取我关注的人数接口
     */
    public static final String GETMYFOLLOW = "user/getMyfollow.app";
    /**
     * 获取用户个人信息接口
     */
    public static final String GETMYDETAILS = "user/getMyDetails.app";
    /**
     * 修改用户信息接口
     */
    public static final String MODIFYUSER = "user/modifyUser.app";
    /**
     * 获取用户默认地址信息接口
     */
    public static final String GETADDRESSBYUSER = "address/getAddressByUser.app";
    /**
     * 修改用户地址接口
     */
    public static final String MODIFYADDRESS = "address/modifyAddress.app";
    /**
     * 提交邀请码接口
     */
    public static final String USEINVITATIONCODE = "user/useInvitationCode.app";
    /**
     * 上传头像接口
     */
    public static final String UPLOADLOGO = "upload/uploadLogo.app";
    /**
     * 搜索接口
     */
    public static final String SEARCH = "search.app";
    /**
     * 获取搜索热门标签接口
     */
    public static final String getHotSearch = "getHotSearch.app";
    /**
     * 一口价接口
     */
    public static final String OFFGETDETAILS = "officialSell/getDetails.app";
    /**
     * 会员套餐详情接口
     */
    public static final String GETVIPBYTYPE = "vip/getVIPByType.app";
    /**
     * 虚拟币充值接口
     */
    public static final String GETRECHARGEBYPAGE = "v2_recharge/getRechargeByPage.app";
    /**
     * 获取收藏私照接口
     */
    public static final String GETMYCOLLECTIONPHOTO = "photo/getMyCollectionPhoto.app";
    /**
     * 获取收藏视频接口
     */
    public static final String GETMYCOLLECTIONVIDEO = "video/getMyCollectionVideo.app";
    /**
     * 获取收藏VR接口
     */
    public static final String GETMYCOLLECTIONVRVIDEO = "video/getMyCollectionVRVideo.app";
    /**
     * 获取该用户指定类型的已购买列表接口
     * type=类型标记:0私照;1一口价;2众筹;3拍卖,4视频,5VR视频
     * status= 支付状态 null 0未支付 1支付成功 -1支付失败/支付取消 -2校验失败 -3退款
     */
    public static final String GETPURCHASEBYPAGE = "order/getPurchaseByPage.app";
    /**
     * 点赞视频接口
     */
    public static final String LIKEVIDEO = "video/likeVideo.app";
    /**
     * 专辑点赞接口
     */
    public static final String LIKEPHOTO = "photo/likePhoto.app";
    /**
     * 全部订单接口
     */
    public static final String GETMYORDERBYPAGE = "order/getMyOrderByPage.app";
    /**
     * 获取模特信息接口
     */
    public static final String GETMODELINFO = "modle/getModelInfo.app";
    /**
     * 根据模特获取他/她所属的私照或视频接口
     */
    public static final String GETPUBLISHRECORDBYMODEL = "modle/getPublishRecordByModel.app";
    /**
     * 获取模特自己传的私密照片/私密视频
     */
    public static final String GETPRIVATERECORD = "v2_model/getPrivateRecord.app";
    /**
     * 关注接口
     */
    public static final String FOLLOWUSER = "user/followUser.app";
    /**
     * 排行榜接口
     */
    public static final String GETRANKING = "ranking/getRanking.app";
    /**
     * 获取我最近访问的模特接口
     */
    public static final String GETSELLMODEL = "see/getSellModel.app";
    /**
     * 获取我最近访问的模特接口
     */
    public static final String GETVIDEOMODELCOVER = "common/getVideoModelCover.app";

    /**
     * 发送普通视频评论接口
     */
    public static final String ADDVIDEOCOMMENT = "video/addVideoComment.app";
    /**
     * 获取视频接口
     */
    public static final String VIDEODETAILS = "video/getDetails.app";
    /**
     * 获取照片OSS接口
     */
    public static final String PHOTOAUTHENTICATION = "oss/photoAuthentication.app";
    /**
     * 获取私密照片OSS接口
     */
    public static final String PRIVATEPHOTOAUTHENTICATION = "oss/privatePhotoAuthentication.app";

    /**
     * 获取私密视频OSS接口
     */
    public static final String PRIVATEVIDEOAUTHENTICATION = "oss/privateVideoAuthentication.app";
    /**
     * 获取视频OSS接口
     */
    public static final String VIDEOAUTHENTICATION = "oss/videoAuthentication.app";
    /**
     * 随机获取视频接口
     */
    public static final String GETRANDOMVIDEOBYPAGE = "video/getRandomVideoByPage.app";
    /**
     * 随机获取模特接口
     */
    public static final String GETRANDOMBYPAGE = "photo/getRandomByPage.app";
    /**
     * 随机获取vr接口
     */
    public static final String GETRANDOMVRVIDEOBYPAGE = "video/getRandomVRVideoByPage.app";
    /**
     * 获取Vip接口
     */
    public static final String GETMYVIP = "user/getMyVip.app";
    /**
     * 获取众筹详情接口
     */
    public static final String GETCROWDDETAIL = "crowdFunding/getDetails.app";
    /**
     * 参与人数详情接口
     */
    public static final String GETCROWDFUNDINGBYSUPPORTER = "crowdFunding/getCrowdFundingBySupporter.app";
    /**
     * 获取众筹目标的价格等级接口
     */
    public static final String GETCROWDFUNDINGTARGETPRICE = "crowdFunding/getCrowdFundingTargetPrice.app";
    /**
     * 支付宝众筹支付接口
     */
    public static final String ALIPAYCROWDFUNDING = "order/aliPayCrowdFunding.app";
    /**
     * 微信众筹支付接口
     */
    public static final String WXPAYCROWDFUNDING = "order/wxPayCrowdFunding.app";
    /**
     * 微信一口价支付接口
     */

    public static final String WXPAYOFFICIALSELL = "order/wxPayOfficialSell.app";
    /**
     * 支付宝一口价支付接口
     */
    public static final String ALIPAYOFFICIALSELL = "order/aliPayOfficialSell.app";
    /**
     * 微信专辑支付接口
     */
    public static final String WXPAYPHOTO = "order/wxPayPhoto.app";
    /**
     * 支付宝专辑支付接口
     */
    public static final String ALIPAYPHOTO = "order/aliPayPhoto.app";
    /**
     * 支付宝VIP支付接口
     */
    public static final String ALIPAYVIP = "order/aliPayVIP.app";
    /**
     * 微信VIP支付接口
     */
    public static final String WXPAYVIP = "order/wxPayVIP.app";

    /**
     * 虚拟币支付宝支付接口
     */
    public static final String ALIPAYRECHARGE = "order/aliPayRecharge.app";
    /**
     * 虚拟币微信支付接口
     */
    public static final String WXPAYRECHARGE = "order/wxPayRecharge.app";

    /**
     * 购买模特微信号,支付宝接口;
     */
    public static final String ALIPAYMODELWX = "order/aliPayModelWx.app";
    /**
     * 购买模特微信号,微信接口;
     */
    public static final String WXPAYMODELWX = "order/wxPayModelWx.app";
    /**
     * 获取分享接口
     */
    public static final String GETSHAREURL = "share/getShareURL.app";
    /**
     * 获取浏览过的视频/VR接口
     */
    public static final String GETSELLRECORD = "see/getSellRecord.app";
    /**
     * 获取客服联系方式、app版本号接口
     */
    public static final String GETSETTING = "common/getSetting.app";
    /**
     * 最新apk下载接口
     */
    public static final String DOWNLOADPATH = "common/getDownloadPath.app";
    /**
     * 获取我未读的私照 0、视频4、VR 5 接口
     */
    public static final String GETNOTREADRECORD = "see/getNotReadRecord.app";
    /**
     * 获取未完成的旧的私密照片集;
     */
    public static final String GETOLDPRIVATEPHOTO = "photo/getOldPrivatePhoto.app";

    /**
     * 创建空的私照集;
     */
    public static final String CREATEPRIVATEPHOTO = "photo/createPrivatePhoto.app";
    /**
     * 新增私照图片;
     */
    public static final String ADDPHOTOIMG = "photo/addPhotoIMG.app";
    /**
     * 上传私密视频;
     */
    public static final String ADDPRIVATEVIDEO = "video/addPrivateVideo.app";
    /**
     * 发布专辑;
     */
    public static final String PUBLISHPRIVATEPHOTO = "photo/publishPrivatePhoto.app";
    /**
     * 获取模特微信号和价格;
     */
    public static final String GETWXPRICE = "v2_model/getWXPrice.app";
    /**
     * 设置模特微信号和价格;
     */
    public static final String SETWXPRICE = "v2_model/setWXPrice.app";
    /**
     * 获取私密模特列表 ;
     */
    public static final String GETMODELBYPAGE = "v2_model/getModelByPage.app";

    /**
     * 已购买微信列表接口;
     */
    public static final String GETMYMODELWXBYPAGE = "order/getMyModelWXByPage.app";
    /**
     * 获取指定模特的微信号或价格 ;
     */
    public static final String GETMODELWX = "v2_model/getModelWX.app";

    /**
     * 获取私密模特信息 ;
     */
    public static final String GETMODELINFO2 = "v2_model/getModelInfo.app";
    /**
     * 根据模特获取他/她所属的私密照片或私密视频接口 ;
     */
    public static final String GETPUBLISHRECORDBYMODEL2 = "v2_model/getPublishRecordByModel.app";
    /**
     * 申请模特认证接口 ;
     */
    public static final String MODELAPPLY = "user/modelApply.app";
    /**
     * 获取我的账号信息接口 ;
     */
    public static final String GETMYACCOUNT = "v2_model/getMyAccount.app";
    /**
     * 绑定、修改手机号的验证码接口 ;
     */
    public static final String CREATEMODIFYPHONECODE = "common/createModifyPhoneCode.app";
    /**
     * 检测用户是否绑定过手机接口 ;
     */
    public static final String CHECKPHONE = "user/checkPhone.app";
    /**
     * 修改/绑定用户手机号码 接口 ;
     */
    public static final String MODIFYPHONE = "user/modifyPhone.app";
    /**
     * 获取我的账提现明细接口 ;
     */
    public static final String GETCASHDETAILS = "v2_model/getCashDetails.app";
    /**
     * 获取我的提现明细接口 ;
     */
    public static final String GETMYCASHDETAILS = "v2_cash/getMyCashDetails.app";
    /**
     * 申请提现接口 ;
     */
    public static final String APPLYCASH = "v2_cash/applyCash.app";
    /**
     * 删除私密视频接口 ;
     */
    public static final String DELETEPRIVATEVIDEO = "video/deletePrivateVideo.app";
    /**
     * 删除私密视频接口 ;
     */
    public static final String DELETEPRIVATEPHOTO = "photo/deletePrivatePhoto.app";
    /**
     * 更新设置模特背景图接口 ;
     */
    public static final String UPDATEBACKGROUND = "v2_model/updateBackground.app";
    /**
     * 获取所有私密照片/私密视频的类型接口 ;
     */
    public static final String GETALLTYPE = "privateType/getALLType.app";
    /**
     * 检测模特申请接口 ;
     */
    public static final String CHECKAPPLY = "user/checkApply.app";
    /**
     * 分享图片接口 ;
     */
    public static final String SHAREQCIMAGE = "share/getShareQCImage.app";
    /**
     * 私密视频购买接口 ;
     */
    public static final String PAYPRIVATEVIDEO = "order/payPrivateVideo.app";
    /**
     * 私密照片购买接口 ;
     */
    public static final String PAYPRIVATEPHOTO = "order/payPrivatePhoto.app";
    /**
     * 检测指定类型有没有权限接口 ;
     */
    public static final String CHECKTYPEPERMISSION = "privateType/checkTypePermission.app";
    /**
     * 根据类型获取所属的私照或视频接口 ;
     */
    public static final String GETPUBLISHRECORDBYTYPE = "privateType/getPublishRecordByType.app";

}
