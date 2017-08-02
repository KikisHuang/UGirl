package example.com.fan.activity;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.VersionBean;
import example.com.fan.mylistener.VersionCheckListener;
import example.com.fan.utils.SynUtils;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.SynUtils.getRouString;
import static example.com.fan.utils.TitleUtils.setTitles;
import static example.com.fan.utils.getVersionUtils.getVersionInfo;

/**
 * Created by lian on 2017/7/7.
 */
public class HelpActivity extends InitActivity implements View.OnClickListener, VersionCheckListener {
    private LinearLayout download_why, msg_why, content_why, search_why, collect_why, comment_why, pay_why, updata_why, call_layout;
    private List<LinearLayout> list;
    private FrameLayout qq_server_layout, skill_server_layout, wx_server_layout;
    private String qq, skill, phone, wx = "";


    private void getData() {
        getVersionInfo(getApplicationContext(), this);
    }

    @Override
    protected void click() {
        skill_server_layout.setOnClickListener(this);
        qq_server_layout.setOnClickListener(this);
        wx_server_layout.setOnClickListener(this);
        call_layout.setOnClickListener(this);

        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            list.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(finalI).findViewById(R.id.details_layout).getVisibility() == View.GONE) {
                        list.get(finalI).findViewById(R.id.details_layout).setVisibility(View.VISIBLE);
                        ImageView im = (ImageView) list.get(finalI).findViewById(R.id.boult_img);
                        im.setRotation(90f);
                    } else {
                        list.get(finalI).findViewById(R.id.details_layout).setVisibility(View.GONE);
                        ImageView im = (ImageView) list.get(finalI).findViewById(R.id.boult_img);
                        im.setRotation(0f);
                    }
                }
            });
        }
    }
    @Override
    protected void init() {
        setContentView(R.layout.help_activity_layout);
        setTitles(this, getRouString(R.string.help));
        list = new ArrayList<>();
        list.add(download_why = f(R.id.download_why));
        list.add(msg_why = f(R.id.msg_why));
        list.add(content_why = f(R.id.content_why));
        list.add(search_why = f(R.id.search_why));
        list.add(collect_why = f(R.id.collect_why));
        list.add(comment_why = f(R.id.comment_why));
        list.add(pay_why = f(R.id.pay_why));
        list.add(updata_why = f(R.id.updata_why));
        qq_server_layout = f(R.id.qq_server_layout);
        call_layout = f(R.id.call_ll);
        skill_server_layout = f(R.id.skill_server_layout);
        wx_server_layout = f(R.id.wx_server_layout);

        TextView tv2 = (TextView) download_why.findViewById(R.id.name_tv);
        tv2.setText(getRouString(R.string.download_intro));
        TextView tv22 = (TextView) download_why.findViewById(R.id.detais_tv);
        tv22.setText(getRouString(R.string.download_details));

        TextView tv3 = (TextView) msg_why.findViewById(R.id.name_tv);
        tv3.setText(getRouString(R.string.why_receiver_msg));
        TextView tv33 = (TextView) msg_why.findViewById(R.id.detais_tv);
        tv33.setText(getRouString(R.string.msg_details));

        TextView tv4 = (TextView) content_why.findViewById(R.id.name_tv);
        tv4.setText(getRouString(R.string.dont_cotent));
        TextView tv44 = (TextView) content_why.findViewById(R.id.detais_tv);
        tv44.setText(getRouString(R.string.content_details));


        TextView tv5 = (TextView) search_why.findViewById(R.id.name_tv);
        tv5.setText(getRouString(R.string.use_search));
        TextView tv55 = (TextView) search_why.findViewById(R.id.detais_tv);
        tv55.setText(getRouString(R.string.use_search_details));


        TextView tv6 = (TextView) collect_why.findViewById(R.id.name_tv);
        tv6.setText(getRouString(R.string.use_collect));
        TextView tv66 = (TextView) collect_why.findViewById(R.id.detais_tv);
        tv66.setText(getRouString(R.string.use_collect_details));

        TextView tv7 = (TextView) comment_why.findViewById(R.id.name_tv);
        tv7.setText(getRouString(R.string.use_comment));
        TextView tv77 = (TextView) comment_why.findViewById(R.id.detais_tv);
        tv77.setText(getRouString(R.string.comment_details));

        TextView tv8 = (TextView) pay_why.findViewById(R.id.name_tv);
        tv8.setText(getRouString(R.string.pay_fail));
        TextView tv88 = (TextView) pay_why.findViewById(R.id.detais_tv);
        tv88.setText(getRouString(R.string.pay_details));

        TextView tv9 = (TextView) updata_why.findViewById(R.id.name_tv);
        tv9.setText(getRouString(R.string.user_upload_why));
        TextView tv99 = (TextView) updata_why.findViewById(R.id.detais_tv);
        tv99.setText(getRouString(R.string.updata_details));


    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skill_server_layout:
                if (SynUtils.isQQClientAvailable(getApplicationContext()) && !skill.isEmpty())
                    RelationQQ(skill);
                else
                    ToastUtil.toast2_bottom(this, "请先安装QQ!");
                break;
            case R.id.qq_server_layout:
                if (SynUtils.isQQClientAvailable(getApplicationContext()) && !qq.isEmpty())
                    RelationQQ(qq);
                else
                    ToastUtil.toast2_bottom(this, "请先安装QQ!");
                break;
            case R.id.wx_server_layout:
                RelationWx();
                break;
        }
    }

    /**
     * 添加微信,微信号复制到剪贴板;
     */
    private void RelationWx() {
        try {
            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
            ClipboardManager cm = (ClipboardManager) HelpActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(wx);
            ToastUtil.toast2_bottom(this, "已复制客服微信号");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtil.toast2_bottom(this, "检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    /**
     * 发送QQ临时消息方法;
     * params s  qq号
     */
    private void RelationQQ(String s) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + s;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void onVersion(VersionBean vb) {
        qq = vb.getKfQq();
        skill = vb.getKfQq();
        phone = vb.getKfPhone();
        wx = vb.getKfWx();
    }

    @Override
    public void onFail() {
        finish();
    }
}
