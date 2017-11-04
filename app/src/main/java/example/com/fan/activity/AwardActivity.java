package example.com.fan.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.AwardPagerAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.AwardBean;
import example.com.fan.bean.PrivatePhotoUpBean;
import example.com.fan.mylistener.AwardListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.ZoomOutPageTransformer;
import example.com.fan.view.WrapContentHeightViewPager;
import okhttp3.Call;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonOb;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/10/14.
 */
public class AwardActivity extends InitActivity implements AwardListener, View.OnClickListener {
    private final static String TAG = getTAG(AwardActivity.class);
    private WrapContentHeightViewPager viewPager;
    private int flag;
    private List<String> selectList;
    private List<AwardBean> list;
    private AwardListener listener;
    private int chargeNumber = 0;
    private LinearLayout page_layout;
    private TextView len_tv, tag_one_tv;
    private SeekBar price_sb;
    private TextView price_tv;
    private FrameLayout add_layout, reduce_layout;
    private int Pro = 0;
    private Button commit_bt;
    private String SpecialId;
    private int Size = 0;
    private int UpSize = 0;
    private ImageView close_img;

    @Override
    protected void click() {
        reduce_layout.setOnClickListener(this);
        add_layout.setOnClickListener(this);
        commit_bt.setOnClickListener(this);
        close_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.award_layout);
        flag = Integer.parseInt(getIntent().getStringExtra("award_flag"));
        price_sb = f(R.id.price_sb);
        price_tv = f(R.id.price_tv);
        reduce_layout = f(R.id.reduce_layout);
        add_layout = f(R.id.add_layout);
        commit_bt = f(R.id.commit_bt);
        tag_one_tv = f(R.id.tag_one_tv);
        len_tv = f(R.id.len_tv);
        close_img = f(R.id.close_img);
        viewPager = f(R.id.viewPager);
        page_layout = f(R.id.page_layout);

        if (flag == 0) {
            listener = this;
            list = new ArrayList<>();
            selectList = getIntent().getStringArrayListExtra("award_selectLists");
            Size = selectList.size();
//            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.getWindowWidth(this) * 6 / 10, (int) (getWindowHeight(this) / 2.1));
            PagerDataInit();
            PageInit();
            setPageListener();
        }
        if (flag == 1) {
            Pro = Integer.parseInt(getIntent().getStringExtra("award_price"));
            price_sb.setProgress(Pro);
            price_tv.setText("￥" + Pro);

            viewPager.setVisibility(View.GONE);
            tag_one_tv.setVisibility(View.GONE);
            len_tv.setVisibility(View.GONE);
        }

        SeekBarsetListener();
    }

    private void SeekBarsetListener() {

        price_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//设置滑动监听
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Pro = progress;
                price_tv.setText("￥" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setPageListener() {

        //页面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                len_tv.setText(position + 1 + "/" + selectList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    private void PagerDataInit() {
        for (int i = 0; i < selectList.size(); i++) {
            AwardBean ab = new AwardBean();
            ab.setPath(selectList.get(i));
            View view = getLayoutInflater().inflate(R.layout.award_page_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.item_image);
            Glide.with(getApplicationContext()).load(selectList.get(i)).apply(getRequestOptions(true, 0, 0, false)).into(img);
            ab.setView(view);
            list.add(ab);
        }
    }

    @Override
    protected void initData() {

    }

    private void PageInit() {

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER;
        lp1.setMargins(0, 80, 0, 70);
        viewPager.setLayoutParams(lp1);
        page_layout.setClipChildren(false);
        viewPager.setClipChildren(false);

        //设置ViewPager切换效果，即实现画廊效果
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        AwardPagerAdapter adapter = new AwardPagerAdapter(list, this, listener);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(selectList.size());

    }


    @Override
    public void onSelect(int pos, List<View> views) {

        if (pos != 0 && pos == chargeNumber) {
            for (int i = 0; i < views.size(); i++) {
                views.get(i).findViewById(R.id.dim_img).setVisibility(View.GONE);
                ((ImageView) views.get(i).findViewById(R.id.select_img)).setImageResource(R.mipmap.off_icon);
            }
            chargeNumber = 0;
        } else {
            for (int i = 0; i < views.size(); i++) {
                views.get(i).findViewById(R.id.dim_img).setVisibility(View.GONE);
                ((ImageView) views.get(i).findViewById(R.id.select_img)).setImageResource(R.mipmap.off_icon);
            }
            for (int i = 0; i < views.size(); i++) {
                if (pos <= i) {
                    views.get(i).findViewById(R.id.dim_img).setVisibility(View.VISIBLE);
                    ((ImageView) views.get(i).findViewById(R.id.select_img)).setImageResource(R.mipmap.on_icon);
                }
            }
            chargeNumber = pos;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reduce_layout:
                if (Pro != 0) {
                    Pro--;
                    price_sb.setProgress(Pro);
                }

                break;
            case R.id.add_layout:
                if (Pro < 99) {
                    Pro++;
                    price_sb.setProgress(Pro);
                }
                break;
            case R.id.close_img:
                finish();
                break;
            case R.id.commit_bt:
                if (flag == 0) {
                    if (chargeNumber != 0 && Pro != 0)
                        SettingImgPostion();

                    else if (chargeNumber == 0)
                        ToastUtil.toast2_bottom(this, "请选择从第几张开始收费!!");
                    else if (Pro == 0)
                        ToastUtil.toast2_bottom(this, "请设置价格!!");

                }
                if (flag == 1) {
                    if (Pro == 0)
                        ToastUtil.toast2_bottom(this, "请设置价格!!");
                    else {
                        Intent intent = new Intent();
                        Log.i(TAG, "AW price  ===" + Pro);
                        intent.putExtra("price", String.valueOf(Pro));
                        setResult(100, intent);
                        ToastUtil.toast2_bottom(AwardActivity.this, "设置成功!");
                        finish();
                    }
                }
                break;
        }
    }

    private void SettingImgPostion() {
        Show(AwardActivity.this, "上传图片中..", false, null);
        /**
         * 设置专辑收费照片(创建专辑);
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CREATEPRIVATEPHOTO)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("hidePosition", String.valueOf(chargeNumber))
                .addParams("price", String.valueOf(Pro))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AwardActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                PrivatePhotoUpBean pp = new Gson().fromJson(String.valueOf(getJsonOb(response)), PrivatePhotoUpBean.class);
                                SpecialId = pp.getId();
                                Log.i(TAG, "Size ==" + Size);
                                for (int i = 0; i < selectList.size(); i++) {
                                    NewPrivatePhoto(new File(selectList.get(i)), i);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(AwardActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void NewPrivatePhoto(final File file, int pos) {
        int price;
        if (chargeNumber <= pos)
            price = 1;
        else
            price = 0;

        Log.i(TAG, "num == " + pos + "  needMoney == " + price + "id == " + SpecialId + "  chargeNumber==" + chargeNumber);
        /**
         * 新增私照;
         */
        OkHttpUtils.post()
                .url(MzFinal.URl + MzFinal.ADDPHOTOIMG)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", SpecialId)
                .addParams("needMoney", String.valueOf(price))
                .addParams("num", String.valueOf(pos))
                .addFile("file", file.getName(), file)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AwardActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            Log.i(TAG, "ADDPHOTOIMG" + response);
                            if (code == 1) {

                                Log.i(TAG, "UpSize ==" + UpSize);
                                if (UpSize + 1 == Size) {
                                    ToastUtil.toast2_bottom(AwardActivity.this, "私照专辑创建成功！！");
                                    Intent intent = new Intent();
                                    intent.putExtra("SpecialId", SpecialId);
                                    intent.putExtra("chargeNumber", chargeNumber + "");
                                    intent.putExtra("price", String.valueOf(Pro));
                                    setResult(111, intent);
                                    finish();
                                } else
                                    UpSize++;

                            } else {
                                ToastUtil.ToastErrorMsg(AwardActivity.this, response, code);
                                Cancle();
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }
}
