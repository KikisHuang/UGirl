package example.com.fan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PictureSlidePagerAdapter2;
import example.com.fan.bean.PhotoType;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.CustomViewPager;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/6/9.
 * 精选图集页面;
 */
public class ChoicenessActivity extends BaseActivity {

    private CustomViewPager pr_viewPager;
    private TabLayout mTab;
    private List<PhotoType> list;
    private PictureSlidePagerAdapter2 page_adapter;
    private int pagePosition = 0;

    private void getPagePosition() {
        String name = getIntent().getStringExtra("page_position");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (name.equals(list.get(i).getTypeName()))
                    pagePosition = i;
            }
            setNavi();
            setPager();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;

    }

    private void setNavi() {
        mTab.setupWithViewPager(pr_viewPager);
        if (list.size() > 0) {
            for (PhotoType type : list) {
                createView(type.getTypeName());
            }
        }
    }

    @Override
    public void initData() {
        getData();
    }

    private void setPager() {

        page_adapter = new PictureSlidePagerAdapter2(getSupportFragmentManager(), list);
        pr_viewPager.setAdapter(page_adapter);
        pr_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pr_viewPager.setCurrentItem(pagePosition);
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPHOTOTYPE)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ChoicenessActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    PhotoType pt = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PhotoType.class);
                                    list.add(pt);
                                }
                                getPagePosition();
                            } else

                                ToastUtil.ToastErrorMsg(ChoicenessActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
    @Override
    protected void init() {
        setContentView(R.layout.choiceness_activity_layout);
        setTitles(this, getResources().getString(R.string.choiceness));
        list = new ArrayList<>();
        mTab = (TabLayout) findViewById(R.id.tab_layout);
        //2.MODE_FIXED模式
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        pr_viewPager = (CustomViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private void createView(String str) {
        mTab.addTab(mTab.newTab().setText(str));
    }
}
