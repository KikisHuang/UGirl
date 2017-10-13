package example.com.fan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.PageTopBean;
import example.com.fan.bean.UserInfoBean;
import example.com.fan.mylistener.ChangeUserInfoListener;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.ApplySuperUserPopupWindow;
import example.com.fan.view.Popup.PayPopupWindow;
import example.com.fan.view.Popup.SharePopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.BannerUtils.goBannerPage;
import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.IntentUtils.goAttentionPage;
import static example.com.fan.utils.IntentUtils.goLoginPage;
import static example.com.fan.utils.IntentUtils.goMyCollectPage;
import static example.com.fan.utils.IntentUtils.goMyOrderPage;
import static example.com.fan.utils.IntentUtils.goOverPayPage;
import static example.com.fan.utils.IntentUtils.goPayPage;
import static example.com.fan.utils.IntentUtils.goPersonInfoPage2;
import static example.com.fan.utils.IntentUtils.goRegisterPage;
import static example.com.fan.utils.IntentUtils.goSGamerPage;
import static example.com.fan.utils.IntentUtils.goSettingPage;
import static example.com.fan.utils.IntentUtils.goStorePage;
import static example.com.fan.utils.IntentUtils.goUnReadPage;
import static example.com.fan.utils.IntentUtils.goVipPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.StringUtil.cleanNull;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.SynUtils.getSex;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;
import static example.com.fan.utils.TextViewColorUtils.setTextColor1;


/**
 * Created by lian on 2017/4/22.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, ChangeUserInfoListener, OverallRefreshListener {
    private static final String TAG = getTAG(MyFragment.class);
    private TextView attention_tv, account_tv, rank_tv, login_tv, register_tv, user_name, acoount_line, super_tv;
    private ImageView user_icon, ad_img;
    private RelativeLayout rl_layout0, rl_layout1, rl_layout2, rl_layout3, rl_layout4, rl_layout5, rl_layout6, rl_layout7, rl_layout8, login_rl, rl_layout33;
    //    private RelativeLayout rl_layout10, rl_layout11, rl_layout12, rl_layout13;
    private LinearLayout unlogin_ll, account_number_layout, compile_layout;
    public static MyFragment fragment;
    private List<UserInfoBean> info;
    //    private TextView upload_private, upload_video;
    private Button apply_for_bt;
    private ImageView more_icon0;


    @Override
    protected int initContentView() {
        return R.layout.my_fragment;
    }

    private void IsLogin() {
        //获取广告;
        getBeanner();
        if (!LoginStatusQuery()) {
            account_number_layout.setVisibility(View.GONE);
            login_rl.setVisibility(View.GONE);
            unlogin_ll.setVisibility(View.VISIBLE);
            acoount_line.setVisibility(View.GONE);
        } else {
            account_number_layout.setVisibility(View.VISIBLE);
            login_rl.setVisibility(View.VISIBLE);
            unlogin_ll.setVisibility(View.GONE);
            acoount_line.setVisibility(View.VISIBLE);
            getData();
        }
    }

    private void getBeanner() {
        /**
         * 顶部Banner数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETBANNER)
                .addParams("showPosition", "4")
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
                                    final PageTopBean rb = new Gson().fromJson(String.valueOf(ar.getJSONObject(0)), PageTopBean.class);
                                    Glide.with(getActivity()).load(rb.getImgUrl()).into(ad_img);
                                    ad_img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goBannerPage(getActivity(), rb.getType(), rb.getHttpUrl(), rb.getValue());
                                        }
                                    });
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    public void click() {
        user_icon.setOnClickListener(this);
        attention_tv.setOnClickListener(this);
        account_tv.setOnClickListener(this);
        rank_tv.setOnClickListener(this);
        login_tv.setOnClickListener(this);
        register_tv.setOnClickListener(this);
        compile_layout.setOnClickListener(this);

        rl_layout0.setOnClickListener(this);
        rl_layout1.setOnClickListener(this);
        rl_layout2.setOnClickListener(this);
        rl_layout3.setOnClickListener(this);
        rl_layout4.setOnClickListener(this);
        rl_layout5.setOnClickListener(this);
        rl_layout6.setOnClickListener(this);
        rl_layout7.setOnClickListener(this);
        rl_layout8.setOnClickListener(this);
        rl_layout33.setOnClickListener(this);

//        rl_layout10.setOnClickListener(this);
//        rl_layout11.setOnClickListener(this);
//        rl_layout12.setOnClickListener(this);
//        rl_layout13.setOnClickListener(this);

//        upload_video.setOnClickListener(this);
//        upload_private.setOnClickListener(this);
    }

    private void getData() {
        /**
         * 我的关注;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYFOLLOW)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "999")
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
                                JSONObject ob = ar.optJSONObject(ar.length() - 1);
                                setTextColor1(attention_tv, ob.optString("count") + MzFinal.br, "关注", "#000000");

                                if (code == 0)
                                    setTextColor1(attention_tv, "0" + MzFinal.br, "关注", "#000000");
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });

        /**
         * 获取个人信息;
         */
        if (LoginStatusQuery()) {
            info.clear();
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETMYDETAILS)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
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
                                    JSONObject ob = getJsonOb(response);
                                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                                    info.add(ub);

                                    if (ub.getModelFlag() == 1) {
                                        MzFinal.MODELFLAG = true;
                                        more_icon0.setVisibility(View.VISIBLE);
                                        apply_for_bt.setVisibility(View.GONE);
                                        super_tv.setText(getRouString(R.string.my_production));
                                    } else {
                                        MzFinal.MODELFLAG = false;
                                        more_icon0.setVisibility(View.GONE);
                                        apply_for_bt.setVisibility(View.VISIBLE);
                                        super_tv.setText(getRouString(R.string.apply_for_super_user));
                                    }

                                    MzFinal.MYID = ub.getId();
                                    setTextColor1(account_tv, info.get(0).getBalance() + MzFinal.br, "我的账户", "#FF4D87");
                                    setTextColor1(rank_tv, info.get(0).getCommentLevel() + MzFinal.br, "等级", "#000000");
                                    MyAppcation.UserIcon = ub.getHeadImgUrl();
                                    MyAppcation.myInvitationCode = ub.getMyInvitationCode();
                                    if (info.get(0).getHeadImgUrl() == null)
                                        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.test_icon).apply(getRequestOptions(false, 0, 0,true)).into(user_icon);
                                    else
                                        Glide.with(getActivity().getApplicationContext()).load(info.get(0).getHeadImgUrl()).apply(getRequestOptions(false, 0, 0,true)).into(user_icon);
                                    if (cleanNull(info.get(0).getName()))
                                        user_name.setText("");
                                    else
                                        user_name.setText(info.get(0).getName());

                                    MyAppcation.UserIcon = ub.getHeadImgUrl();
                                    MyAppcation.myInvitationCode = ub.getMyInvitationCode();
                                } else
                                    ToastUtil.ToastErrorMsg(getActivity(), response, code);
                                /**
                                 * 更新vip状态;
                                 */
                                getUserVip();
                            } catch (Exception e) {

                            }
                        }
                    });
        }
    }

    public void init() {
        fragment = this;
        info = new ArrayList<>();
        //注册观察者监听网络;
        ListenerManager.getInstance().registerListtener(this);

        attention_tv = (TextView) view.findViewById(R.id.attention_tv);
        account_tv = (TextView) view.findViewById(R.id.account_tv);
        rank_tv = (TextView) view.findViewById(R.id.rank_tv);
        login_tv = (TextView) view.findViewById(R.id.login_tv);
        register_tv = (TextView) view.findViewById(R.id.register_tv);
        acoount_line = (TextView) view.findViewById(R.id.acoount_line);

        rl_layout33 = (RelativeLayout) view.findViewById(R.id.rl_layout33);

        account_number_layout = (LinearLayout) view.findViewById(R.id.account_number_layout);
        compile_layout = (LinearLayout) view.findViewById(R.id.compile_layout);
        apply_for_bt = (Button) view.findViewById(R.id.apply_for_bt);
        super_tv = (TextView) view.findViewById(R.id.super_tv);
        more_icon0 = (ImageView) view.findViewById(R.id.more_icon0);
        user_icon = (ImageView) view.findViewById(R.id.user_icon);
        user_name = (TextView) view.findViewById(R.id.user_name);

    /*    upload_private = (TextView) view.findViewById(R.id.upload_private);
        upload_video = (TextView) view.findViewById(R.id.upload_video);*/

        ad_img = (ImageView) view.findViewById(R.id.ad_img);

        rl_layout0 = (RelativeLayout) view.findViewById(R.id.rl_layout0);
        rl_layout1 = (RelativeLayout) view.findViewById(R.id.rl_layout1);
        rl_layout2 = (RelativeLayout) view.findViewById(R.id.rl_layout2);
        rl_layout3 = (RelativeLayout) view.findViewById(R.id.rl_layout3);
        rl_layout4 = (RelativeLayout) view.findViewById(R.id.rl_layout4);
        rl_layout5 = (RelativeLayout) view.findViewById(R.id.rl_layout5);
        rl_layout6 = (RelativeLayout) view.findViewById(R.id.rl_layout6);
        rl_layout7 = (RelativeLayout) view.findViewById(R.id.rl_layout7);
        rl_layout8 = (RelativeLayout) view.findViewById(R.id.rl_layout8);

//        rl_layout10 = (RelativeLayout) view.findViewById(R.id.rl_layout10);
//        rl_layout11 = (RelativeLayout) view.findViewById(R.id.rl_layout11);
//        rl_layout12 = (RelativeLayout) view.findViewById(R.id.rl_layout12);
//        rl_layout13 = (RelativeLayout) view.findViewById(R.id.rl_layout13);

        login_rl = (RelativeLayout) view.findViewById(R.id.login_rl);
        unlogin_ll = (LinearLayout) view.findViewById(R.id.unlogin_ll);

    }

    @Override
    public void initData() {
        IsLogin();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        ListenerManager.getInstance().unRegisterListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.user_icon:
                if (info.size() > 0)
                    goPersonInfoPage2(getActivity(), info.get(0).getHeadImgUrl(), info.get(0).getName(), String.valueOf(getSex(info.get(0).getSex())), info.get(0).getWx(), user_icon);
                break;
            case R.id.login_tv:
                goLoginPage(getActivity());
                break;
            case R.id.register_tv:
                goRegisterPage(getActivity());
                break;
            case R.id.compile_layout:
                if (info.size() > 0)
                    goPersonInfoPage2(getActivity(), info.get(0).getHeadImgUrl(), info.get(0).getName(), String.valueOf(getSex(info.get(0).getSex())), info.get(0).getWx(), user_icon);
                break;
            case R.id.attention_tv:
                goAttentionPage(getActivity());
                break;
            case R.id.account_tv:
                goPayPage(getActivity());
                break;
            case R.id.rank_tv:
                goPayPage(getActivity());
                break;
            case R.id.rl_layout1:
                if (LoginStatusQuery())
                    goPayPage(getActivity());
                else
                    Login(getActivity());
                break;
            case R.id.rl_layout2:
                if (LoginStatusQuery()) {
                    if (MyAppcation.VipFlag)
                        goVipPage(getActivity());
                    else {
                        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pay_pp_layout, null);
                        int width = DeviceUtils.getWindowWidth(getActivity()) * 8 / 10;
                        int h = (int) (DeviceUtils.getWindowHeight(getActivity()) * 6 / 10);
                        PopupWindow pay = new PopupWindow(contentView, width, h);

                        PayPopupWindow p = new PayPopupWindow(getActivity());
                        p.ScreenPopupWindow(LayoutInflater.from(getActivity()).inflate(R.layout.my_fragment, null), pay, 1, contentView);
                    }
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout3:
                if (LoginStatusQuery()) {
                    goOverPayPage(getActivity());
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout4:
                if (LoginStatusQuery()) {
                    goMyCollectPage(getActivity());
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout33:
                if (LoginStatusQuery()) {
                    goStorePage(getActivity());
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout5:
                if (LoginStatusQuery()) {
                    goUnReadPage(getActivity());
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout6:
                if (LoginStatusQuery()) {
                    goMyOrderPage(getActivity());
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout7:
                if (LoginStatusQuery()) {
                    //任务页面;
                    //goTaskPage(getActivity());
                    try {
                        //分享popup;
                        if (!cleanNull(MyAppcation.myInvitationCode)) {
                            SharePopupWindow sp = new SharePopupWindow(getActivity(), MyAppcation.myInvitationCode);
                            sp.ScreenPopupWindow(LayoutInflater.from(getActivity()).inflate(R.layout.my_fragment, null));
                        }
                    } catch (Exception e) {

                    }
                } else
                    Login(getActivity());
                break;
            case R.id.rl_layout8:
                goSettingPage(getActivity());
                break;
            case R.id.rl_layout0:
                if (MzFinal.MODELFLAG)
                    goSGamerPage(getActivity());
                else {
                    ApplySuperUserPopupWindow aps = new ApplySuperUserPopupWindow(getActivity());
                    aps.ScreenPopupWindow();
                }
                break;

         /*   //我的私照
            case R.id.rl_layout10:
                if (MzFinal.MODELFLAG) {
                    goMyPrivatePhotoPage(getActivity(),MzFinal.MYID);
                } else
                    GoRenzPage();
                break;
            //我的视频
            case R.id.rl_layout11:
                if (MzFinal.MODELFLAG) {
                    goMyVideoPage(getActivity());
                } else
                    GoRenzPage();
                break;
            //我的微信;
            case R.id.rl_layout12:

                if (MzFinal.MODELFLAG) {
                    goWeChatPage(getActivity());
                } else
                    GoRenzPage();
                break;
            case R.id.rl_layout13:

                break;
            //上传私照;
            case R.id.upload_private:
                if (MzFinal.MODELFLAG) {
                    goUpPrivatePhotoPage(getActivity());
                } else
                    GoRenzPage();
                break;
            //上传视频;
            case R.id.upload_video:
                if (MzFinal.MODELFLAG) {

                } else
                    GoRenzPage();

                break;*/

        }
    }

    @Override
    public void onUpDataUserInfo() {
        IsLogin();
    }

    @Override
    public void notifyAllActivity(boolean net) {
        if (net)
            IsLogin();
    }
}
