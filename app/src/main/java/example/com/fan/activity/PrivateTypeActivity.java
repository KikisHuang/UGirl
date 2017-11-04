package example.com.fan.activity;

import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.ModelAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.ModelBean;
import example.com.fan.mylistener.SpringListener;
import example.com.fan.mylistener.StoreItemClickListener;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ShareUtils;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.PaytwoPopupWindow;
import okhttp3.Call;

import static example.com.fan.utils.IntentUtils.goHomePage;
import static example.com.fan.utils.IntentUtils.goPlayerPage;
import static example.com.fan.utils.IntentUtils.goPrivatePhotoPage;
import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SpringUtils.SpringViewInit;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.LoginStatusQuery;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/17.
 */
public class PrivateTypeActivity extends InitActivity implements SpringListener, StoreItemClickListener {

    private static final String TAG = getTAG(PrivateTypeActivity.class);
    private List<ModelBean> rlist;
    private ListView listView;
    private SpringView springview1;
    private int page = 0;
    private String typeId = "";
    private ModelAdapter adapter;
    private StoreItemClickListener hlistener;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.private_type_layout);
        listView = f(R.id.listView);
        springview1 = f(R.id.springview1);
        hlistener = this;
        SpringViewInit(springview1, PrivateTypeActivity.this, this);
        rlist = new ArrayList<>();
        try {
            typeId = getIntent().getStringExtra("private_type_typeId");
            setTitles(this, getIntent().getStringExtra("private_type_name"));
        } catch (Exception e) {
            finish();
        }
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {
        /**
         * 获取指定类型私密照片、视频;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETPUBLISHRECORDBYTYPE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("typeId", typeId)
                .addParams(MzFinal.PAGE, String.valueOf(page))
                .addParams(MzFinal.SIZE, String.valueOf(page + 20))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivateTypeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (b)
                                    rlist.clear();
                                JSONArray ar = getJsonAr(response);

                                for (int i = 0; i < ar.length(); i++) {
                                    ModelBean mb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), ModelBean.class);
                                    rlist.add(mb);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new ModelAdapter(PrivateTypeActivity.this, rlist, hlistener);
                                    listView.setAdapter(adapter);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(PrivateTypeActivity.this, response, code);

                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });
    }

    @Override
    public void IsonRefresh(int i) {
        page = i;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int a) {
        page += a;
        getData(false);
    }

    @Override
    public void onItemClickListener(int position, String id, int pos) {
        switch (position) {
            case 1002:
                goHomePage(PrivateTypeActivity.this, id);
                break;
            case -2:
                if (LoginStatusQuery()) {
                    goPrivatePhotoPage(PrivateTypeActivity.this, id, 0);
                } else
                    Login(PrivateTypeActivity.this);
                break;
            case -3:
                if (LoginStatusQuery()) {
                    CheckPay(id,pos);
                } else
                    Login(PrivateTypeActivity.this);
                break;
            case 112:
                if (LoginStatusQuery()) {
                    ShareUtils.getSystemShare(PrivateTypeActivity.this, id);
                } else
                    Login(PrivateTypeActivity.this);

                break;
            case 113:
                if (LoginStatusQuery()) {
                    ShareUtils.getSystemShare(PrivateTypeActivity.this, id);
                } else
                    Login(PrivateTypeActivity.this);
                break;
        }
    }
    private void CheckPay(final String ids, final int pos) {
        /**
         * 获取所有类型私密视频、私照;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.CHECKPAY)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.ID, ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(PrivateTypeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                goPlayerPage(PrivateTypeActivity.this, ids, -3);
                            } else if (code == -1){
                                PaytwoPopupWindow pyp = new PaytwoPopupWindow(PrivateTypeActivity.this, String.valueOf(rlist.get(pos).getPrice()), ids, 1, null);
                                pyp.ScreenPopupWindow(listView);
                            }
                            else
                                ToastUtil.ToastErrorMsg(PrivateTypeActivity.this, response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                        }
                    }
                });
    }
}
