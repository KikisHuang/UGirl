package example.com.fan.utils;

import java.util.Random;

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

//  public static final String URl = "http://fnsandroid.mozu123.com/mcFnsInterface/";
//  public static final String URl = "http://fnsandroid2.mozu123.com/mcFnsInterface/";
//  public static final String URl = "http://fnsandroid3.mozu123.com/mcFnsInterface/";
//  public static final String URl = "http://fnsandroid4.mozu123.com/mcFnsInterface/";
//  public static final String URl = "http://fnsandroid5.mozu123.com/mcFnsInterface/";


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
    public static boolean AdvertisementIsShow = true;

    public static final String br = "<br />";
    //支付宝支付接口存放数组;
    public static String[] Alis = {MzFinal.ALIPAYCROWDFUNDING, MzFinal.ALIPAYOFFICIALSELL, MzFinal.ALIPAYPHOTO, MzFinal.ALIPAYVIP, MzFinal.ALIPAYMODELWX, MzFinal.ALIPAYRECHARGE};

    public static String INSTRUCTION_S1 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/s1.png";
    public static String INSTRUCTION_S2 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/s2.png";
    public static String INSTRUCTION_S3 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/s3.png";
    public static String INSTRUCTION_S4 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/s4.png";
    public static String INSTRUCTION_S5 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/s5.png";

    public static String INSTRUCTION_V1 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/v1.png";
    public static String INSTRUCTION_V2 = "http://fns-system.oss-cn-hangzhou.aliyuncs.com/ViDeoAndPrivatePhotoURL/v2.png";

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
        else
            return false;
    }

    //微信支付、支付宝支付方式判断;
    public static boolean AlisOfWecha(String url) {
        for (String u : Alis) {
            if (u.equals(url))
                return true;
        }
        return false;
    }

    public static String[] FAKECITY = {"北京", "上海", "深圳", "广州", "广东", "广西", "沈阳"};
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
     * 专辑收藏接口
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
     * Video Vr封面图接口
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
     * 获取我未读的0私照 、4视频、 5VR 、-2私密照片、-3私密视频接口
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
    /**
     * 私密视频购买检测接口 ;
     */
    public static final String CHECKPAY = "order/checkPay.app";
    /**
     * 分享二维码图片;
     */
    public static final String SHAREQCIMAGEJPG = "share/shareQCImage.jpg";
    /**
     * 发送安装信息;
     */
    public static final String INSTALL = "common/install.app";


    public static final String totleGName = "梦琪,忆柳,之桃,慕青,问兰,尔岚,元香,初夏,沛菡,傲珊,曼文,乐菱,痴珊,恨玉,惜文,香寒,新柔,语蓉,海安,夜蓉,涵柏,水桃,醉蓝,春儿,语琴,从彤,傲晴,语兰,又菱,碧彤,元霜,怜梦,紫寒,妙彤,曼易,南莲,紫翠,雨寒,易烟,如萱,若南,寻真,晓亦,向珊,慕灵,以蕊,寻雁,映易,雪柳,孤岚,笑霜,海云,凝天,沛珊,寒云,冰旋,宛儿,绿真,盼儿,晓霜,碧凡,夏菡,曼香,若烟,半梦,雅绿,冰蓝,灵槐,平安,书翠,翠风,香巧,代云,梦曼,幼翠,友巧,听寒,梦柏,醉易,访旋,亦玉,凌萱,访卉,怀亦,笑蓝,春翠,靖柏,夜蕾,冰夏,梦松,书雪,乐枫,念薇,靖雁,寻春,恨山,从寒,忆香,觅波,静曼,凡旋,以亦,念露,芷蕾,千兰,新波,代真,新蕾,雁玉,冷卉,紫山,千琴,恨天,傲芙,盼山,怀蝶,冰兰,山柏,翠萱,恨松,问旋,从南,白易,问筠,如霜,半芹,丹珍,冰彤,亦寒,寒雁,怜云,寻文,乐丹,翠柔,谷山,之瑶,冰露,尔珍,谷雪,乐萱,涵菡,海莲,傲蕾,青槐,冬儿,易梦,惜雪,宛海,之柔,夏青,亦瑶,妙菡,春竹,痴梦,紫蓝,晓巧,幻柏,元风,冰枫,访蕊,南春,芷蕊,凡蕾,凡柔,安蕾,天荷,含玉,书兰,雅琴,书瑶,春雁,从安,夏槐,念芹,怀萍,代曼,幻珊,谷丝,秋翠,白晴,海露,代荷,含玉,书蕾,听白,访琴,灵雁,秋春,雪青,乐瑶,含烟,涵双,平蝶,雅蕊,傲之,灵薇,绿春,含蕾,从梦,从蓉,初丹,听兰,听蓉,语芙,夏彤,凌瑶,忆翠,幻灵,怜菡,紫南,依珊,妙竹,访烟,怜蕾,映寒,友绿,冰萍,惜霜,凌香,芷蕾,雁卉,迎梦,元柏,代萱,紫真,千青,凌寒,紫安,寒安,怀蕊,秋荷,涵雁,以山,凡梅,盼曼,翠彤,谷冬,新巧,冷安,千萍,冰烟,雅阳,友绿,南松,诗云,飞风,寄灵,书芹,幼蓉,以蓝,笑寒,忆寒,秋烟,芷巧,水香,映之,醉波,幻莲,夜山,芷卉,向彤,小玉,幼南,凡梦,尔曼,念波,迎松,青寒,笑天,涵蕾,碧菡,映秋,盼烟,忆山,以寒,寒香,小凡,代亦,梦露,映波,友蕊,寄凡,怜蕾,雁枫,水绿,曼荷,笑珊,寒珊,谷南,慕儿,夏岚,友儿,小萱,紫青,妙菱,冬寒,曼柔,语蝶,青筠,夜安,觅海,问安,晓槐,雅山,访云,翠容,寒凡,晓绿,以菱,冬云,含玉,访枫,含卉,夜白,冷安,灵竹,醉薇,元珊,幻波,盼夏,元瑶,迎曼,水云,访琴,谷波,乐之,笑白,之山,妙海,紫霜,平夏,凌旋,孤丝,怜寒,向萍,凡松,青丝,翠安,如天,凌雪,绮菱,代云,南莲,寻南,春文,香薇,冬灵,凌珍,采绿,天春,沛文,紫槐,幻柏,采文,春梅,雪旋,盼海,映梦,安雁,映容,凝阳,访风,天亦,平绿,盼香,觅风,小霜,雪萍,半雪,山柳,谷雪,靖易,白薇,梦菡,飞绿,如波,又晴,友易,香菱,冬亦,问雁,妙春,海冬,半安,平春,幼柏,秋灵,凝芙,念烟,白山,从灵,尔芙,迎蓉,念寒,翠绿,翠芙,靖儿,妙柏,千凝,小珍,天巧,妙旋,雪枫,夏菡,元绿,痴灵,绮琴,雨双,听枫,觅荷,凡之,晓凡,雅彤,香薇,孤风,从安,绮彤,之玉,雨珍,幻丝,代梅,香波,青亦,元菱,海瑶,飞槐,听露,梦岚,幻竹,新冬,盼翠,谷云,忆霜,水瑶,慕晴,秋双,雨真,觅珍,丹雪,从阳,元枫,痴香,思天,如松,妙晴,谷秋,妙松,晓夏,香柏,巧绿,宛筠,碧琴,盼兰,小夏,安容,青曼,千儿,香春,寻双,涵瑶,冷梅,秋柔,思菱,醉波,醉柳,以寒,迎夏,向雪,香莲,以丹,依凝,如柏,雁菱,凝竹,宛白,初柔,南蕾,书萱,梦槐,香芹,南琴,绿海,沛儿,晓瑶,听春,凝蝶,紫雪,念双,念真,曼寒,凡霜,飞雪,雪兰,雅霜,从蓉,冷雪,靖巧,翠丝,觅翠,凡白,乐蓉,迎波,丹烟,梦旋,书双,念桃,夜天,海桃,青香,恨风,安筠,觅柔,初南,秋蝶,千易,安露,诗蕊,山雁,友菱,香露,晓兰,白卉,语山,冷珍,秋翠,夏柳,如之,忆南,书易,翠桃,寄瑶,如曼,问柳,香梅,幻桃,又菡,春绿,醉蝶,亦绿,诗珊,听芹,新之,易巧,念云,晓灵,静枫,夏蓉,如南,幼丝,秋白,冰安,秋白,南风,醉山,初彤,凝海,紫文,凌晴,香卉,雅琴,傲安,傲之,初蝶,寻桃,代芹,诗霜,春柏,绿夏,碧灵,诗柳,夏柳,采白,慕梅,乐安,冬菱,紫安,宛凝,雨雪,易真,安荷,静竹,代柔,丹秋,绮梅,依白,凝荷,幼珊,忆彤,凌青,之桃,芷荷,听荷,代玉,念珍,梦菲,夜春,千秋,白秋,谷菱,飞松,初瑶,惜灵,恨瑶,梦易,新瑶,曼梅,碧曼,友瑶,雨兰,夜柳,香蝶,盼巧,芷珍,香卉,含芙,夜云,依萱,凝雁,以莲,易容,元柳,安南,幼晴,尔琴,飞阳,白凡,沛萍,雪瑶,向卉,采文,乐珍,寒荷,觅双,白桃,安卉,迎曼,盼雁,乐松,涵山,恨寒,问枫,以柳,含海,秋春,翠曼,忆梅,涵柳,梦香,海蓝,晓曼,代珊,春冬,恨荷,忆丹,静芙,绮兰,梦安,紫丝,千雁,凝珍,香萱,梦容,冷雁,飞柏,天真,翠琴,寄真,秋荷,代珊,初雪,雅柏,怜容,如风,南露,紫易,冰凡,海雪,语蓉,碧玉,翠岚,语风,盼丹,痴旋,凝梦,从雪,白枫,傲云,白梅,念露,慕凝,雅柔,盼柳,半青,从霜,怀柔,怜晴,夜蓉,代双,以南,若菱,芷文,寄春,南晴,恨之,梦寒,初翠,灵波,巧春,问夏,凌春,惜海,亦旋,沛芹,幼萱,白凝,初露,迎海,绮玉,凌香,寻芹,秋柳,尔白,映真,含雁,寒松,友珊,寻雪,忆柏,秋柏,巧风,恨蝶,青烟,问蕊,灵阳,春枫,又儿,雪巧,丹萱,凡双,孤萍,紫菱,寻凝,傲柏,傲儿,友容,灵枫,尔丝,曼凝,若蕊,问丝,思枫,水卉,问梅,念寒,诗双,翠霜,夜香,寒蕾,凡阳,冷玉,平彤,语薇,幻珊,紫夏,凌波,芷蝶,丹南,之双,凡波,思雁,白莲,从菡,如容,采柳,沛岚,惜儿,夜玉,水儿,半凡,语海,听莲,幻枫,念柏,冰珍,思山,凝蕊,天玉,问香,思萱,向梦,笑南,夏旋,之槐,元灵,以彤,采萱,巧曼,绿兰,平蓝,问萍,绿蓉,靖柏,迎蕾,碧曼,思卉,白柏,妙菡,怜阳,雨柏,雁菡,梦之,又莲,乐荷,寒天,凝琴,书南,映天,白梦,初瑶,恨竹,平露,含巧,慕蕊,半莲,醉卉,天菱,青雪,雅旋,巧荷,飞丹,恨云,若灵,尔云,幻天,诗兰,青梦,海菡,灵槐,忆秋,寒凝,凝芙,绮山,静白,尔蓉,尔冬,映萱,白筠,冰双,访彤,绿柏,夏云,笑翠,晓灵,含双,盼波,以云,怜翠,雁风,之卉,平松,问儿,绿柳,如蓉,曼容,天晴,丹琴,惜天,寻琴,痴春,依瑶,涵易,忆灵,从波,依柔,问兰,山晴,怜珊,之云,飞双,傲白,沛春,雨南,梦之,笑阳,代容,友琴,雁梅,友桃,从露,语柔,傲玉,觅夏,晓蓝,新晴,雨莲,凝旋,绿旋,幻香,觅双,冷亦,忆雪,友卉,幻翠,靖柔,寻菱,丹翠,安阳,雅寒,惜筠,尔安,雁易,飞瑶,夏兰,沛蓝,静丹,山芙,笑晴,新烟,笑旋,雁兰,凌翠,秋莲,书桃,傲松,语儿,映菡,初曼,听云,孤松,初夏,雅香,语雪,初珍,白安,冰薇,诗槐,冷玉,冰巧,之槐,香柳,问春,夏寒,半香,诗筠,新梅,白曼,安波,从阳,含桃,曼卉,笑萍,碧巧,晓露,寻菡,沛白,平灵,水彤,安彤,涵易,乐巧,依风,紫南,亦丝,易蓉,紫萍,惜萱,诗蕾,寻绿,诗双,寻云,孤丹,谷蓝,惜香,谷枫,山灵,幻丝,友梅,从云,雁丝,盼旋,幼旋,尔蓝,沛山,代丝,痴梅,觅松,冰香,依玉,冰之,妙梦,以冬,碧春,曼青,冷菱,雪曼,安白,香桃,安春,千亦,凌蝶,又夏,南烟,靖易,沛凝,翠梅,书文,雪卉,乐儿,傲丝,安青,初蝶,寄灵,惜寒,雨竹,冬莲,绮南,翠柏,平凡,亦玉,孤兰,秋珊,新筠,半芹,夏瑶,念文,晓丝,涵蕾,雁凡,谷兰,灵凡,凝云,曼云,丹彤,南霜,夜梦,从筠,雁芙,语蝶,依波,晓旋,念之,盼芙,曼安,采珊,盼夏,初柳,迎天,曼安,南珍,妙芙,语柳,含莲,晓筠,夏山,尔容,采春,念梦,傲南,问薇,雨灵,凝安,冰海,初珍,宛菡,冬卉,盼晴,冷荷,寄翠,幻梅,如凡,语梦,易梦,千柔,向露,梦玉,傲霜,依霜,灵松,诗桃,书蝶,恨真,冰蝶,山槐,以晴,友易,梦桃,香菱,孤云,水蓉,雅容,飞烟,雁荷,代芙,醉易,夏烟,山梅,若南,恨桃,依秋,依波,香巧,紫萱,涵易,忆之,幻巧,水风,安寒,白亦,惜玉,碧春,怜雪,听南,念蕾,梦竹,千凡,寄琴,采波,元冬,思菱,平卉,笑柳,雪卉,南蓉,谷梦,巧兰,绿蝶,飞荷,平安,孤晴,芷荷,曼冬,寻巧,寄波,尔槐,以旋,绿蕊,初夏,依丝,怜南,千山,雨安,水风,寄柔,念巧,幼枫,凡桃,新儿,春翠,夏波,正雅,雨琴,静槐,元槐,映阳,飞薇,小凝,映寒,傲菡,谷蕊,笑槐,飞兰,笑卉,迎荷,元冬,书竹,半烟,绮波,小之,觅露,夜雪,春柔,寒梦,尔风,白梅,雨旋,芷珊,山彤,尔柳,沛柔,灵萱,沛凝,白容,乐蓉,映安,依云,映冬,凡雁,梦秋,醉柳,梦凡,秋巧,若云,元容,怀蕾,灵寒,天薇,白风,访波,亦凝,易绿,夜南,曼凡,亦巧,青易,冰真,白萱,友安,诗翠,雪珍,海之,小蕊,又琴,香彤,语梦,惜蕊,迎彤,沛白,雁山,易蓉,雪晴,诗珊,春冬,又绿,冰绿,半梅,笑容,沛凝,念瑶,天真,含巧,如冬,向真,从蓉,春柔,亦云,向雁,尔蝶,冬易,丹亦,夏山,醉香,盼夏,孤菱,安莲,问凝,冬萱,晓山,雁蓉,梦蕊,山菡,南莲,飞双,凝丝,思萱,怀梦,雨梅,冷霜,向松,迎丝,迎梅,听双,山蝶,夜梅,醉冬,巧云,雨筠,平文,青文,半蕾,幼菱,寻梅,含之,香之,含蕊,亦玉,靖荷,碧萱,寒云,向南,书雁,怀薇,思菱,忆文,翠巧,怀山,若山,向秋,凡白,绮烟,从蕾,天曼,又亦,依琴,曼彤,沛槐,又槐,元绿,安珊,夏之,易槐,宛亦,白翠,丹云,问寒,易文,傲易,青旋,思真,妙之,半双,若翠,初兰,怀曼,惜萍,初之,宛丝,寄南,小萍,幻儿,千风,天蓉,雅青,寄文,代天,春海,惜珊,向薇,冬灵,惜芹,凌青,谷芹,香巧,雁桃,映雁,书兰,盼香,向山,寄风,访烟,绮晴,傲柔,寄容,以珊,紫雪,芷容,书琴,寻桃,涵阳,怀寒,易云,采蓝,代秋,惜梦,尔烟,谷槐,怀莲,涵菱,水蓝,访冬,半兰,又柔,冬卉,安双,冰岚,香薇,语芹,静珊,幻露,访天,静柏,凌丝,小翠,雁卉,访文,凌文,芷云,思柔,巧凡,慕山,依云,千柳,从凝,安梦,香旋,凡巧,映天,安柏,平萱,以筠,忆曼,新竹,绮露,觅儿,碧蓉,白竹,飞兰,曼雁,雁露,凝冬,含灵,初阳,海秋,香天,夏容,傲冬,谷翠,冰双,绿兰,盼易,思松,梦山,友灵,绿竹,灵安,凌柏,秋柔,又蓝,尔竹,香天,天蓝,青枫,问芙,语海,灵珊,凝丹,小蕾,迎夏,水之,飞珍,冰夏,亦竹,飞莲,海白,元蝶,春蕾,芷天,怀绿,尔容,元芹,若云,寒烟,听筠,采梦,凝莲,元彤,觅山,痴瑶,代桃,冷之,盼秋,秋寒,慕蕊,巧夏,海亦,初晴,巧蕊,听安,芷雪,以松,梦槐,寒梅,香岚,寄柔,映冬,孤容,晓蕾,安萱,听枫,夜绿,雪莲,从丹,碧蓉,绮琴,雨文,幼荷,青柏,痴凝,初蓝,忆安,盼晴,寻冬,雪珊,梦寒,迎南,巧香,采南,如彤,春竹,采枫,若雁,翠阳,沛容,幻翠,山兰,芷波,雪瑶,代巧,寄云,慕卉,冷松,涵梅,书白,乐天,雁卉,宛秋,傲旋,新之,凡儿,夏真,静枫,痴柏,恨蕊,乐双,白玉,问玉,寄松,丹蝶,元瑶,冰蝶,访曼,代灵,芷烟,白易,尔阳,怜烟,平卉,丹寒,访梦,绿凝,冰菱,语蕊,痴梅,思烟,忆枫,映菱,访儿,凌兰,曼岚,若枫,傲薇,凡灵,乐蕊,秋灵,谷槐,觅云,以寒,寒香,小凡,代亦,梦露,映波,友蕊,寄凡,怜蕾,雁枫,水绿,曼荷,笑珊,寒珊,谷南,慕儿,夏岚,友儿,小萱,紫青,妙菱,冬寒,曼柔,语蝶,青筠,夜安,觅海,问安,晓槐,雅山,访云,翠容,寒凡,晓绿,以菱,冬云,含玉,访枫,含卉,夜白,冷安,灵竹,醉薇,元珊,幻波,盼夏,元瑶,迎曼,水云,访琴,谷波,乐之,笑白,之山,妙海,紫霜,平夏,凌旋,孤丝,怜寒,向萍,凡松,青丝,翠安,如天,凌雪,绮菱,代云,南莲,寻南,春文,香薇,冬灵,凌珍,采绿,天春,沛文,紫槐,幻柏,采文,春梅,雪旋,盼海,映梦,安雁,映容,凝阳,访风,天亦,平绿,盼香,觅风,小霜,雪萍,半雪,山柳,谷雪,靖易,白薇,梦菡,飞绿,如波,又晴,友易,香菱,冬亦,问雁,妙春,海冬,半安,平春,幼柏,秋灵,凝芙,念烟,白山,从灵,尔芙,迎蓉,念寒,翠绿,翠芙,靖儿,妙柏,千凝,小珍,天巧,妙旋,雪枫,夏菡,元绿,痴灵,绮琴,雨双,听枫,觅荷,凡之,晓凡,雅彤,香薇,孤风,从安,绮彤,之玉,雨珍,幻丝,代梅,香波,青亦,元菱,海瑶,飞槐,听露,梦岚,幻竹,新冬,盼翠,谷云,忆霜,水瑶,慕晴,秋双,雨真,觅珍,丹雪,从阳,元枫,痴香,思天,如松,妙晴,谷秋,妙松,晓夏,香柏,巧绿,宛筠,碧琴,盼兰,小夏,安容,青曼,千儿,香春,寻双,涵瑶,冷梅,秋柔,思菱,醉波,醉柳,以寒,迎夏,向雪,香莲,以丹,依凝,如柏,雁菱,凝竹,宛白,初柔,南蕾,书萱,梦槐,香芹,南琴,绿海,沛儿,晓瑶,听春,凝蝶,紫雪,念双,念真,曼寒,凡霜,飞雪,雪兰,雅霜,从蓉,冷雪,靖巧,翠丝,觅翠,凡白,乐蓉,迎波,丹烟,梦旋,书双,念桃,夜天,海桃,青香,恨风,安筠,觅柔,初南,秋蝶,千易,安露,诗蕊,山雁,友菱,香露,晓兰,白卉,语山,冷珍,秋翠,夏柳,如之,忆南,书易,翠桃,寄瑶,如曼,问柳,香梅,幻桃,又菡,春绿,醉蝶,亦绿,诗珊,听芹,新之,易巧,念云,晓灵,静枫,夏蓉,如南,幼丝,秋白,冰安,秋白,南风,醉山,初彤,凝海,紫文,凌晴,香卉,雅琴,傲安,傲之,初蝶,寻桃,代芹,诗霜,春柏,绿夏,碧灵,诗柳,夏柳,采白,慕梅,乐安,冬菱,紫安,宛凝,雨雪,易真,安荷,静竹,代柔,丹秋,绮梅,依白,凝荷,冰巧,之槐,香柳,问春,夏寒,半香,诗筠,新梅,白曼,安波,从阳,含桃,曼卉,笑萍,碧巧,晓露,寻菡,沛白,平灵,水彤,安彤,涵易,乐巧,依风,紫南,亦丝,易蓉,紫萍,惜萱,诗蕾,寻绿,诗双,寻云,孤丹,谷蓝,惜香,谷枫,山灵,幻丝,友梅,从云,雁丝,盼旋,幼旋,尔蓝,沛山,代丝,痴梅,觅松,冰香,依玉,冰之,妙梦,以冬,碧春,曼青,冷菱,雪曼,安白,香桃,安春,千亦,凌蝶,又夏,南烟,靖易,沛凝,翠梅,书文,雪卉,乐儿,傲丝,安青,初蝶,寄灵,惜寒,雨竹,冬莲,绮南,翠柏,平凡,亦玉,孤兰,秋珊,新筠,半芹,夏瑶,念文,晓丝,涵蕾,雁凡,谷兰,灵凡,凝云,曼云,丹彤,南霜,夜梦,从筠,雁芙,语蝶,依波,晓旋,念之,盼芙,曼安,采珊,盼夏,初柳,迎天,曼安,南珍,妙芙,语柳,含莲,晓筠,夏山,尔容,采春,念梦,傲南,问薇,雨灵,凝安,冰海,初珍,宛菡,冬卉,盼晴,冷荷,寄翠,幻梅,如凡,语梦,易梦,千柔,向露,梦玉,傲霜,依霜,灵松,诗桃,书蝶,恨真,冰蝶,山槐,以晴,友易,梦桃,香菱,孤云,水蓉,雅容,飞烟,雁荷,代芙,醉易,夏烟,山梅,若南,恨桃,依秋,依波,香巧,紫萱,涵易,忆之,幻巧,水风,安寒,白亦,惜玉,碧春,怜雪,听南,念蕾,梦竹,千凡,寄琴,采波,元冬,思菱,平卉,笑柳,雪卉,南蓉,谷梦,巧兰,绿蝶,飞荷,平安,孤晴,芷荷,曼冬,寻巧,寄波,尔槐,以旋,绿蕊,初夏,依丝,怜南,千山,雨安,水风,寄柔,念巧,幼枫,凡桃,新儿,春翠,夏波,雨琴,静槐,元槐,映阳,飞薇,小凝,映寒,傲菡,谷蕊,笑槐,飞兰,笑卉,迎荷,元冬,书竹,半烟,绮波,小之,觅露,夜雪,春柔,寒梦,尔风,白梅,雨旋,芷珊,山彤,尔柳,沛柔,灵萱,沛凝,白容,乐蓉,映安,依云,映冬,凡雁,梦秋,醉柳,梦凡,秋巧,若云,元容,怀蕾,灵寒,天薇,白风,访波,亦凝,易绿,夜南,曼凡,亦巧,青易,冰真,白萱,友安,诗翠,雪珍,海之,小蕊,又琴,香彤,语梦,惜蕊,迎彤,沛白,雁山,易蓉,雪晴,诗珊,春冬,又绿,冰绿,半梅,笑容,沛凝,念瑶,天真,含巧,如冬,向真,从蓉,春柔,亦云,向雁,尔蝶,冬易,丹亦,夏山,醉香,盼夏,孤菱,安莲,问凝,冬萱,晓山,雁蓉,梦蕊,山菡,南莲,飞双,凝丝,思萱,怀梦,雨梅,冷霜,向松,迎丝,迎梅,听双,山蝶,夜梅,醉冬,巧云,雨筠,平文,青文,半蕾,碧萱,寒云,向南,书雁,怀薇,思菱,忆文,翠巧,怀山,若山,向秋,凡白,绮烟,从蕾,天曼,又亦,依琴,曼彤,沛槐,又槐,元绿,安珊,夏之,易槐,宛亦,白翠,丹云,问寒,易文,傲易,青旋,思真,妙之,半双,若翠,初兰,怀曼,惜萍,初之,宛丝,寄南,小萍,幻儿,千风,天蓉,雅青,寄文,代天,春海,惜珊,向薇,冬灵,惜芹,凌青,谷芹,香巧,雁桃,映雁,书兰,盼香,向山,寄风,访烟,绮晴,傲柔,寄容,以珊,紫雪,芷容,书琴,寻桃,涵阳,怀寒,易云,采蓝,代秋,惜梦,尔烟,谷槐,怀莲,涵菱,水蓝,访冬,半兰,又柔,冬卉,安双,冰岚,香薇,语芹,静珊,幻露,访天,静柏,凌丝,小翠,雁卉,访文,凌文,芷云,思柔,巧凡,慕山,依云,千柳,从凝,安梦,香旋,凡巧,映天,安柏,平萱,以筠,忆曼,新竹,绮露,觅儿,碧蓉,白竹,飞兰,曼雁,雁露,凝冬,含灵,初阳,海秋,香天,夏容,傲冬,谷翠,冰双,绿兰,盼易,思松,梦山,友灵,绿竹,灵安,凌柏,秋柔,又蓝,尔竹,香天,天蓝,青枫,问芙,语海,灵珊,凝丹,小蕾,迎夏,水之,飞珍,冰夏,亦竹,飞莲,海白,元蝶,春蕾,芷天,怀绿,尔容,元芹,若云,寒烟,听筠,采梦,凝莲,元彤,觅山,痴瑶,代桃,冷之,盼秋,秋寒,慕蕊,巧夏,海亦,初晴,巧蕊,听安,芷雪,以松,梦槐,寒梅,香岚,寄柔,映冬,孤容,晓蕾,安萱,听枫,夜绿,雪莲,从丹,碧蓉,绮琴,雨文,幼荷,青柏,痴凝,初蓝,忆安,盼晴,寻冬,雪珊,梦寒,迎南,巧香,采南,如彤,春竹,采枫,若雁,翠阳,沛容,幻翠,山兰,芷波,雪瑶,代巧,寄云,慕卉,冷松,涵梅,书白,乐天,雁卉,宛秋,傲旋,新之,凡儿,夏真,静枫,痴柏,恨蕊,乐双,白玉,问玉,寄松,丹蝶,元瑶,冰蝶,访曼,代灵,芷烟,白易,尔阳,怜烟,平卉,丹寒,访梦,绿凝,冰菱,语蕊,痴梅,思烟,忆枫,映菱,访儿,凌兰,曼岚,若枫,傲薇,凡灵,乐蕊,秋灵,谷槐,觅云,幼珊,忆彤,凌青,之桃,芷荷,听荷,代玉,念珍,梦菲,夜春,千秋,白秋,谷菱,飞松,初瑶,惜灵,恨瑶,梦易,新瑶,曼梅,碧曼,友瑶,雨兰,夜柳,香蝶,盼巧,芷珍,香卉,含芙,夜云,依萱,凝雁,以莲,易容,元柳,安南,幼晴,尔琴,飞阳,白凡,沛萍,雪瑶,向卉,采文,乐珍,寒荷,觅双,白桃,安卉,迎曼,盼雁,乐松,涵山,恨寒,问枫,以柳,含海,秋春,翠曼,忆梅,涵柳,梦香,海蓝,晓曼,代珊,春冬,恨荷,忆丹,静芙,绮兰,梦安,紫丝,千雁,凝珍,香萱,梦容,冷雁,飞柏,天真,翠琴,寄真,秋荷,代珊,初雪,雅柏,怜容,如风,南露,紫易,冰凡,海雪,语蓉,碧玉,翠岚,语风,盼丹,痴旋,凝梦,从雪,白枫,傲云,白梅,念露,慕凝,雅柔,盼柳,半青,从霜,怀柔,怜晴,夜蓉,代双,以南,若菱,芷文,寄春,南晴,恨之,梦寒,初翠,灵波,巧春,问夏,凌春,惜海,亦旋,沛芹,幼萱,白凝,初露,迎海,绮玉,凌香,寻芹,秋柳,尔白,映真,含雁,寒松,友珊,寻雪,忆柏,秋柏,巧风,恨蝶,青烟,问蕊,灵阳,春枫,又儿,雪巧,丹萱,凡双,孤萍,紫菱,寻凝,傲柏,傲儿,友容,灵枫,尔丝,曼凝,若蕊,问丝,思枫,水卉,问梅,念寒,诗双,翠霜,夜香,寒蕾,凡阳,冷玉,平彤,语薇,幻珊,紫夏,凌波,芷蝶,丹南,之双,凡波,思雁,白莲,从菡,如容,采柳,沛岚,惜儿,夜玉,水儿,半凡,语海,听莲,幻枫,念柏,冰珍,思山,凝蕊,天玉,问香,思萱,向梦,笑南,夏旋,之槐,元灵,以彤,采萱,巧曼,绿兰,平蓝,问萍,绿蓉,靖柏,迎蕾,碧曼,思卉,白柏,妙菡,怜阳,雨柏,雁菡,梦之,又莲,乐荷,寒天,凝琴,书南,映天,白梦,初瑶,恨竹,平露,含巧,慕蕊,半莲,醉卉,天菱,青雪,雅旋,巧荷,飞丹,恨云,若灵,尔云,幻天,诗兰,青梦,海菡,灵槐,忆秋,寒凝,凝芙,绮山,静白,尔蓉,尔冬,映萱,白筠,冰双,访彤,绿柏,夏云,笑翠,晓灵,含双,盼波,以云,怜翠,雁风,之卉,平松,问儿,绿柳,如蓉,曼容,天晴,丹琴,惜天,寻琴,痴春,依瑶,涵易,忆灵,从波,依柔,问兰,山晴,怜珊,之云,飞双,傲白,沛春,雨南,梦之,笑阳,代容,友琴,雁梅,友桃,从露,语柔,傲玉,觅夏,晓蓝,新晴,雨莲,凝旋,绿旋,幻香,觅双,冷亦,忆雪,友卉,幻翠,靖柔,寻菱,丹翠,安阳,雅寒,惜筠,尔安,雁易,飞瑶,夏兰,沛蓝,静丹,山芙,笑晴,新烟,笑旋,雁兰,凌翠,秋莲,书桃,傲松,语儿,映菡,初曼,听云,孤松,初夏,雅香,语雪,初珍,白安,冰薇,诗槐,冷玉梦琪,忆柳,之桃,慕青,问兰,尔岚,元香,初夏,沛菡,傲珊,曼文,乐菱,痴珊,恨玉,惜文,香寒,新柔,语蓉,海安,夜蓉,涵柏,水桃,醉蓝,春儿,语琴,从彤,傲晴,语兰,又菱,碧彤,元霜,怜梦,紫寒,妙彤,曼易,南莲,紫翠,雨寒,易烟,如萱,若南,寻真,晓亦,向珊,慕灵,以蕊,寻雁,映易,雪柳,孤岚,笑霜,海云,凝天,沛珊,寒云,冰旋,宛儿,绿真,盼儿,晓霜,碧凡,夏菡,曼香,若烟,半梦,雅绿,冰蓝,灵槐,平安,书翠,翠风,香巧,代云,梦曼,幼翠,友巧,听寒,梦柏,醉易,访旋,亦玉,凌萱,访卉,怀亦,笑蓝,春翠,靖柏,夜蕾,冰夏,梦松,书雪,乐枫,念薇,靖雁,寻春,恨山,从寒,忆香,觅波,静曼,凡旋,以亦,念露,芷蕾,千兰,新波,代真,新蕾,雁玉,冷卉,紫山,千琴,恨天,傲芙,盼山,怀蝶,冰兰,山柏,翠萱,恨松,问旋,从南,白易,问筠,如霜,半芹,丹珍,冰彤,亦寒,寒雁,怜云,寻文,乐丹,翠柔,谷山,之瑶,冰露,尔珍,谷雪,乐萱,涵菡,海莲,傲蕾,青槐,冬儿,易梦,惜雪,宛海,之柔,夏青,亦瑶,妙菡,春竹,痴梦,紫蓝,晓巧,幻柏,元风,冰枫,访蕊,南春,芷蕊,凡蕾,凡柔,安蕾,天荷,含玉,书兰,雅琴,书瑶,春雁,从安,夏槐,念芹,怀萍,代曼,幻珊,谷丝,秋翠,白晴,海露,代荷,含玉,书蕾,听白,访琴,灵雁,秋春,雪青,乐瑶,含烟,涵双,平蝶,雅蕊,傲之,灵薇,绿春,含蕾,从梦,从蓉,初丹,听兰,听蓉,语芙,夏彤,凌瑶,忆翠,幻灵,怜菡,紫南,依珊,妙竹,访烟,怜蕾,映寒,友绿,冰萍,惜霜,凌香,芷蕾,雁卉,迎梦,元柏,代萱,紫真,千青,凌寒,紫安,寒安,怀蕊,秋荷,涵雁,以山,凡梅,盼曼,翠彤,谷冬,新巧,冷安,千萍,冰烟,雅阳,友绿,南松,诗云,飞风,寄灵,书芹,幼蓉,以蓝,笑寒,忆寒,秋烟,芷巧,水香,映之,醉波,幻莲,夜山,芷卉,向彤,小玉,幼南,凡梦,尔曼,念波,迎松,青寒,笑天,涵蕾,碧菡,映秋,盼烟,忆山";
    public static final String totleBName = "安邦,安福,安歌,安国,安和,安康,安澜,安民,安宁,安平,安然,安顺,安翔,安晏,安宜,安怡,安易,安志,昂然,昂雄,宾白,宾鸿,宾实,彬彬,彬炳,彬郁,斌斌,斌蔚,滨海,波光,波鸿,波峻,波涛,博瀚,博超,博达,博厚,博简,博明,博容,博赡,博涉,博实,博涛,博文,博学,博雅,博延,博艺,博易,博裕,博远,才捷,才良,才艺,才英,才哲,才俊,成和,成弘,成化,成济,成礼,成龙,成仁,成双,成天,成文,成业,成益,成荫,成周,承安,承弼,承德,承恩,承福,承基,承教,承平,承嗣,承天,承望,承宣,承颜,承业,承悦,承允,承运,承载,承泽,承志,德本,德海,德厚,德华,德辉,德惠,德容,德润,德寿,德水,德馨,德曜,德业,德义,德庸,德佑,德宇,德元,德运,德泽,德明,飞昂,飞白,飞飙,飞掣,飞尘,飞沉,飞驰,飞光,飞翰,飞航,飞翮,飞鸿,飞虎,飞捷,飞龙,飞鸾,飞鸣,飞鹏,飞扬,飞文,飞翔,飞星,飞翼,飞英,飞宇,飞羽,飞雨,飞语,飞跃,飞章,飞舟,风华,丰茂,丰羽,刚豪,刚洁,刚捷,刚毅,高昂,高岑,高畅,高超,高驰,高达,高澹,高飞,高芬,高峯,高峰,高歌,高格,高寒,高翰,高杰,高洁,高峻,高朗,高丽,高邈,高旻,高明,高爽,高兴,高轩,高雅,高扬,高阳,高义,高谊,高逸,高懿,高原,高远,高韵,高卓,光赫,光华,光辉,光济,光霁,光亮,光临,光明,光启,光熙,光耀,光誉,光远,国安,国兴,国源,冠宇,冠玉,晗昱,晗日,涵畅,涵涤,涵亮,涵忍,涵容,涵润,涵涵,涵煦,涵蓄,涵衍,涵意,涵映,涵育,翰采,翰池,翰飞,翰海,翰翮,翰林,翰墨,翰学,翰音,瀚玥,翰藻,瀚海,瀚漠,昊苍,昊昊,昊空,昊乾,昊穹,昊然,昊然,昊天,昊焱,昊英,浩波,浩博,浩初,浩大,浩宕,浩荡,浩歌,浩广,浩涆,浩瀚,浩浩,浩慨,浩旷,浩阔,浩漫,浩淼,浩渺,浩邈,浩气,浩然,浩穰,浩壤,浩思,浩言,皓轩,和蔼,和安,和璧,和昶,和畅,和风,和歌,和光,和平,和洽,和惬,和顺,和硕,和颂,和泰,和悌,和通,和同,和煦,和雅,和宜,和怡,和玉,和裕,和豫,和悦,和韵,和泽,和正,和志,鹤轩,弘博,弘大,弘方,弘光,弘和,弘厚,弘化,弘济,弘阔,弘亮,弘量,弘深,弘盛,弘图,弘伟,弘文,弘新,弘雅,弘扬,弘业,弘义,弘益,弘毅,弘懿,弘致,弘壮,宏伯,宏博,宏才,宏畅,宏达,宏大,宏放,宏富,宏峻,宏浚,宏恺,宏旷,宏阔,宏朗,宏茂,宏邈,宏儒,宏深,宏胜,宏盛,宏爽,宏硕,宏伟,宏扬,宏义,宏逸,宏毅,宏远,宏壮,鸿宝,鸿波,鸿博,鸿才,鸿彩,鸿畅,鸿畴,鸿达,鸿德,鸿飞,鸿风,鸿福,鸿光,鸿晖,鸿朗,鸿文,鸿熙,鸿羲,鸿禧,鸿信,鸿轩,鸿煊,鸿煊,鸿雪,鸿羽,鸿远,鸿云,鸿运,鸿哲,鸿祯,鸿振,鸿志,鸿卓,华奥,华采,华彩,华灿,华藏,华池,华翰,华皓,华晖,华辉,华茂,华美,华清,华荣,华容,嘉赐,嘉德,嘉福,嘉良,嘉茂,嘉木,嘉慕,嘉纳,嘉年,嘉平,嘉庆,嘉荣,嘉容,嘉瑞,嘉胜,嘉石,嘉实,嘉树,嘉澍,嘉熙,嘉禧,嘉祥,嘉歆,嘉许,嘉勋,嘉言,嘉谊,嘉懿,嘉颖,嘉佑,嘉玉,嘉誉,嘉悦,嘉运,嘉泽,嘉珍,嘉祯,嘉志,嘉致,坚白,坚壁,坚秉,坚成,坚诚,建安,建白,建柏,建本,建弼,建德,建华,建明,建茗,建木,建树,建同,建修,建业,建义,建元,建章,建中,健柏,金鑫,锦程,瑾瑜,晋鹏,经赋,经亘,经国,经略,经纶,经纬,经武,经业,经义,经艺,景澄,景福,景焕,景辉,景辉,景龙,景明,景山,景胜,景铄,景天,景同,景曜,靖琪,君昊,君浩,俊艾,俊拔,俊弼,俊才,俊材,俊驰,俊楚,俊达,俊德,俊发,俊风,俊豪,俊健,俊杰,俊捷,俊郎,俊力,俊良,俊迈,俊茂,俊美,俊民,俊名,俊明,俊楠,俊能,俊人,俊爽,俊悟,俊晤,俊侠,俊贤,俊雄,俊雅,俊彦,俊逸,俊英,俊友,俊语,俊誉,俊远,俊哲,俊喆,俊智,峻熙,季萌,季同,开畅,开诚,开宇,开济,开霁,开朗,凯安,凯唱,凯定,凯风,凯复,凯歌,凯捷,凯凯,凯康,凯乐,凯旋,凯泽,恺歌,恺乐,康安,康伯,康成,康德,康复,康健,康乐,康宁,康平,康胜,康盛,康时,康适,康顺,康泰,康裕,乐安,乐邦,乐成,乐池,乐和,乐家,乐康,乐人,乐容,乐山,乐生,乐圣,乐水,乐天,乐童,乐贤,乐心,乐欣,乐逸,乐意,乐音,乐咏,乐游,乐语,乐悦,乐湛,乐章,乐正,乐志,黎昕,黎明,力夫,力强,力勤,力行,力学,力言,立诚,立果,立人,立辉,立轩,立群,良奥,良弼,良才,良材,良策,良畴,良工,良翰,良吉,良骥,良俊,良骏,良朋,良平,良哲,理群,理全,茂才,茂材,茂德,茂典,茂实,茂学,茂勋,茂彦,敏博,敏才,敏达,敏叡,敏学,敏智,明诚,明达,明德,明辉,明杰,明俊,明朗,明亮,明旭,明煦,明轩,明远,明哲,明喆,明知,明志,明智,明珠,朋兴,朋义,彭勃,彭薄,彭湃,彭彭,彭魄,彭越,彭泽,彭祖,鹏程,鹏池,鹏飞,鹏赋,鹏海,鹏鲸,鹏举,鹏鹍,鹏鲲,鹏涛,鹏天,鹏翼,鹏云,鹏运,濮存,溥心,璞玉,璞瑜,浦和,浦泽,奇略,奇迈,奇胜,奇水,奇思,奇邃,奇伟,奇玮,奇文,奇希,奇逸,奇正,奇志,奇致,祺福,祺然,祺祥,祺瑞,琪睿,庆生,荣轩,锐达,锐锋,锐翰,锐进,锐精,锐立,锐利,锐思,锐逸,锐意,锐藻,锐泽,锐阵,锐志,锐智,睿博,睿才,睿诚,睿慈,睿聪,睿达,睿德,睿范,睿广,睿好,睿明,睿识,睿思,绍辉,绍钧,绍祺,绍元,升荣,圣杰,晟睿,思聪,思淼,思源,思远,思博,斯年,斯伯,泰初,泰和,泰河,泰鸿,泰华,泰宁,泰平,泰清,泰然,天材,天成,天赋,天干,天罡,天工,天翰,天和,天华,天骄,天空,天禄,天路,天瑞,天睿,天逸,天佑,天宇,天元,天韵,天泽,天纵,同方,同甫,同光,同和,同化,同济,巍昂,巍然,巍奕,伟博,伟毅,伟才,伟诚,伟茂,伟懋,伟祺,伟彦,伟晔,伟泽,伟兆,伟志,温纶,温茂,温书,温韦,温文,温瑜,文柏,文昌,文成,文德,文栋,文赋,文光,文翰,文虹,文华,文康,文乐,文林,文敏,文瑞,文山,文石,文星,文轩,文宣,文彦,文曜,文耀,文斌,文彬,文滨,向晨,向笛,向文,向明,向荣,向阳,翔宇,翔飞,项禹,项明,晓博,心水,心思,心远,欣德,欣嘉,欣可,欣然,欣荣,欣怡,欣怿,欣悦,新翰,新霁,新觉,新立,新荣,新知,信鸿,信厚,信鸥,信然,信瑞,兴安,兴邦,兴昌,兴朝,兴德,兴发,兴国,兴怀,兴平,兴庆,兴生,兴思,兴腾,兴旺,兴为,兴文,兴贤,兴修,兴学,兴言,兴业,兴运,星波,星辰,星驰,星光,星海,星汉,星河,星华,星晖,星火,星剑,星津,星阑,星纬,星文,星宇,星雨,星渊,星洲,修诚,修德,修杰,修洁,修谨,修筠,修明,修能,修平,修齐,修然,修为,修伟,修文,修雅,修永,修远,修真,修竹,修贤,旭尧,炫明,学博,学海,学林,学民,学名,学文,学义,学真,雪松,雪峰,雪风,雅昶,雅畅,雅达,雅惠,雅健,雅珺,雅逸,雅懿,雅志,炎彬,阳飙,阳飇,阳冰,阳波,阳伯,阳成,阳德,阳华,阳晖,阳辉,阳嘉,阳平,阳秋,阳荣,阳舒,阳朔,阳文,阳曦,阳夏,阳旭,阳煦,阳炎,阳焱,阳曜,阳羽,阳云,阳泽,阳州,烨赫,烨华,烨磊,烨霖,烨然,烨烁,烨伟,烨烨,烨熠,烨煜,毅然,逸仙,逸明,逸春,宜春,宜民,宜年,宜然,宜人,宜修,意远,意蕴,意致,意智,熠彤,懿轩,英飙,英博,英才,英达,英发,英范,英光,英豪,英华,英杰,英朗,英锐,英睿,英叡,英韶,英卫,英武,英悟,英勋,英彦,英耀,英奕,英逸,英毅,英哲,英喆,英卓,英资,英纵,永怡,永春,永安,永昌,永长,永丰,永福,永嘉,永康,永年,永宁,永寿,永思,永望,永新,永言,永逸,永元,永贞,咏德,咏歌,咏思,咏志,勇男,勇军,勇捷,勇锐,勇毅,宇达,宇航,宇寰,宇文,宇荫,雨伯,雨华,雨石,雨信,雨星,雨泽,玉宸,玉成,玉龙,玉泉,玉山,玉石,玉书,玉树,玉堂,玉轩,玉宇,玉韵,玉泽,煜祺,元白,元德,元化,元基,元嘉,元甲,元驹,元凯,元恺,元魁,元良,元亮,元龙,元明,元青,元思,元纬,元武,元勋,元正,元忠,元洲,远航,苑博,苑杰,越彬,蕴涵,蕴和,蕴藉,展鹏,哲瀚,哲茂,哲圣,哲彦,振海,振国,正诚,正初,正德,正浩,正豪,正平,正奇,正青,正卿,正文,正祥,正信,正阳,正业,正谊,正真,正志,志诚,志新,志勇,志明,志国,志强,志尚,志专,志文,志行,志学,志业,志义,志用,志泽,致远,智明,智鑫,智勇,智敏,智志,智渊,子安,子晋,子民,子明,子默,子墨,子平,子琪,子石,子实,子真,子濯,子昂,子轩,子瑜,自明,自强,作人,自怡,自珍,曾琪,泽宇,泽语";

    public static final String[] VipLevel = {"充值了奢华钻石VIP，并获得了神秘礼品！！", "充值了尊贵铂金VIP，并获得神秘礼品！！", "充值了古朴青铜VIP，并获得了神秘礼品！！", "开通了双期包月无限制观赏特权！！", "开通了单月包期无限制观赏特权！！"};

    /*随机生成一个名*/
    public static String generateFirstName(int s) {


        int sex = (int) (Math.random() * 100);
        String[] firstName = null;
        //优先判断s的状态
        if (s == 1) {
            firstName = totleGName.split(",");
        } else if (s == 0) {
            firstName = totleBName.split(",");
        } else {
            //如果是男性（整除）
            if ((sex / 2) == 0) {
                //generate girlFirstName
                firstName = totleGName.split(",");
            } else {
                //generate boyFirstName
                firstName = totleBName.split(",");
            }
        }
        int temp = (int) (Math.random() * (firstName.length));
        return firstName[temp];
    }

    /**
     * 获取随机名称集合;
     *
     * @return
     */
    public static String getRandownName() {
        return generateFirstName(0) + "***" + getVipName();
    }

    private static String getVipName() {
        Random ra = new Random();
        int num = ra.nextInt(VipLevel.length);
        return VipLevel[num];
    }
}
