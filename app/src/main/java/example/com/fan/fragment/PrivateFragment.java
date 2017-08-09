package example.com.fan.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.PictureSlidePagerAdapter3;
import example.com.fan.bean.PhotoType;
import example.com.fan.mylistener.OverallRefreshListener;
import example.com.fan.utils.ListenerManager;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.CustomViewPager;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/4/22.
 */
public class PrivateFragment extends BaseFragment implements OverallRefreshListener {
    private static final String TAG = getTAG(PrivateFragment.class);
    private CustomViewPager pr_viewPager;
    private TabLayout mTab;
    private List<PhotoType> list;
    private PictureSlidePagerAdapter3 page_adapter;

    @Override
    protected int initContentView() {
        return R.layout.private_fragment;
    }

    private void setPager() {

        page_adapter = new PictureSlidePagerAdapter3(getChildFragmentManager(), list);

        pr_viewPager.setAdapter(page_adapter);
        mTab.setupWithViewPager(pr_viewPager);
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
        pr_viewPager.setCurrentItem(0);
    }

    private void getData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPHOTOTYPE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try { int code = getCode(response);
                            if (code == 1) {
                                list.clear();
                                JSONArray ar = getJsonAr(response);

                                for (int i = 0; i < ar.length(); i++) {
                                    PhotoType pt = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PhotoType.class);
                                    list.add(pt);
                                }

                                setNavi();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);

                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void setNavi() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                createView(list.get(i).getTypeName());
            }
            setPager();
        }
    }

    @Override
    protected void click() {

    }
    @Override
    public void init() {
        //初始化标题;
        onDownTouchListener(1, getResources().getString(R.string.private_photo));
        list = new ArrayList<>();
        mTab = (TabLayout) view.findViewById(R.id.tab_layout);

        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        //注册观察者监听网络;
        ListenerManager.getInstance().registerListtener(this);

        pr_viewPager = (CustomViewPager) view.findViewById(R.id.viewPager);

    }

    @Override
    public void initData() {
        getData();
    }

    private void createView(String str) {
        mTab.addTab(mTab.newTab().setText(str));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }
    @Override
    public void notifyAllActivity(boolean net) {
        if (net)
            getData();
    }
}
