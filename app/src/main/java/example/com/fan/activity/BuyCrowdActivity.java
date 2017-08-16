package example.com.fan.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.text.ParseException;

import example.com.fan.R;
import example.com.fan.bean.CrowDetailsBean;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.TitleUtils;
import example.com.fan.utils.ToastUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.utils.DateUtils.daysBetween;
import static example.com.fan.utils.IntentUtils.goProjectIncomePage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/7.
 * 商品众筹页面;
 */
public class BuyCrowdActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(BuyCrowdActivity.class);
    private LinearLayout supp_ll, content_img_ll;
    private ImageView cover_img;
    private TextView supp_tv, title_tv, buy_annotation, raise_per, raise_moeny, goal_moeny, surplus_day, foot_content, head_content;
    private ProgressBar progressbar;
    private String id = "";
    private String targetId = "";

    private void getData() {
        /**
         * 获取众筹详情数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETCROWDDETAIL)
                .addParams(MzFinal.ID, id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(BuyCrowdActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                CrowDetailsBean cb = new Gson().fromJson(String.valueOf(ob), CrowDetailsBean.class);
                                setData(cb);
                                setSupp(cb);
                            } else
                                ToastUtil.ToastErrorMsg(BuyCrowdActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void setData(CrowDetailsBean cb) {
        targetId = cb.getMcCrowdFundingTargets().get(0).getTargetId();
        try {
            Glide.with(getApplicationContext()).load(cb.getMcCrowdFundingImgUrls().get(0).getPath()).crossFade(200).into(cover_img);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        title_tv.setText(cb.getTitle());
        buy_annotation.setText(cb.getSubInfo());
        raise_moeny.setText("已认筹"+cb.getMcCrowdFundingTargets().get(0).getSumMoney());
        raise_per.setText(cb.getMcCrowdFundingTargets().get(0).getProgress() + "%");
        progressbar.setProgress(cb.getMcCrowdFundingTargets().get(0).getProgress());
        goal_moeny.setText("目标金额(￥)" + cb.getMcCrowdFundingTargets().get(0).getTargetMoney());
        try {
            surplus_day.setText("剩余天数：" + daysBetween(cb.getStartTime(), cb.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        head_content.setText(cb.getHeadInfo());
        foot_content.setText(cb.getFootInfo());
        addContentImg(cb, cb.getMcCrowdFundingContentImgUrls().size());
    }

    private void addContentImg(CrowDetailsBean cb, int size) {

        if (size != 0) {
            for (int i = 0; i < size; i++) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.bottomMargin = DeviceUtils.dip2px(this, 15);
                lp.gravity = Gravity.CENTER;
                ImageView im = new ImageView(this);
                im.setScaleType(ImageView.ScaleType.FIT_XY);
                try {
                    Glide.with(getApplicationContext()).load(cb.getMcCrowdFundingContentImgUrls().get(i).getPath()).crossFade(200).into(im);
                } catch (Exception e) {
                    Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
                }
                im.setLayoutParams(lp);
                content_img_ll.addView(im);

            }
        } else {
            ImageView im = new ImageView(this);
            im.setScaleType(ImageView.ScaleType.FIT_CENTER);
            try {
                Glide.with(getApplicationContext()).load(R.drawable.nodata_img).crossFade(200).into(im);
            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
            content_img_ll.addView(im);
        }
    }

    private void recevie() {
        id = getIntent().getStringExtra("b_id");
    }

    protected void click() {
        supp_tv.setOnClickListener(this);
    }

    private void setSupp(CrowDetailsBean cb) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 40), DeviceUtils.dip2px(this, 40));
        lp.gravity = Gravity.CENTER;
        ImageView im = new ImageView(this);
        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im.setLayoutParams(lp);
        try {
            Glide.with(getApplicationContext()).load(cb.getMcCrowdFundingTargets().get(0).getHeadImgUrl()).override(80, 80).bitmapTransform(new CropCircleTransformation(this)).crossFade(200).into(im);
        } catch (Exception e) {
            Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
        }
        supp_ll.addView(im);
    }
    @Override
    protected void init() {
        setContentView(R.layout.buycrowd_activity_layout);
        TitleUtils.setTitles(this, getResources().getString(R.string.project_detail));
        supp_ll = f(R.id.supp_ll);
        supp_tv = f(R.id.supp_tv);
        title_tv = f(R.id.title);
        raise_per = f(R.id.raise_per);
        raise_moeny = f(R.id.raise_moeny);
        foot_content = f(R.id.foot_content);
        head_content = f(R.id.head_content);
        content_img_ll = f(R.id.content_img_ll);
        goal_moeny = f(R.id.goal_moeny);
        surplus_day = f(R.id.surplus_day);
        cover_img = f(R.id.cover_img);
        buy_annotation = f(R.id.buy_annotation);
        progressbar = f(R.id.progressbar);
        recevie();
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supp_tv:
                if (!targetId.isEmpty())
                    goProjectIncomePage(BuyCrowdActivity.this, targetId);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
