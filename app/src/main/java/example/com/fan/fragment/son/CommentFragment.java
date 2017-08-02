package example.com.fan.fragment.son;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.adapter.CommentAdapter;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.bean.CollectBean;
import example.com.fan.bean.CommentBean;
import example.com.fan.fragment.BaseFragment;
import example.com.fan.mylistener.editeListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;
import example.com.fan.view.Popup.CommentEditPopupWindow;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.JsonUtils.getJsonAr;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/13.
 */
public class CommentFragment extends BaseFragment implements editeListener {
    private static final String TAG = getTAG(CommentFragment.class);

    private static TextView collect_num, msg_edt;
    private static LinearLayout icon_ll;
    private static ListView listView;
    private static CommentAdapter adapte;
    public String id = "";
    private editeListener elistener;
    private List<CommentBean> commentlist;
    private List<CollectBean> collectlist;
    private String Colle;

    public void setId(String id, String Colle) {
        this.id = id;
        this.Colle = Colle;
    }

    @Override
    protected int initContentView() {
        return R.layout.comment_popu_layout2;
    }

    protected void click() {
        msg_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentEditPopupWindow.ScreenPopupWindow(msg_edt, getActivity().getApplicationContext(), elistener);
            }
        });
    }

    private void getData() {

        /**
         * 收藏数据;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETCOLLECTIONUSER)
                .addParams(MzFinal.ID, id)
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "30")
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
                                for (int i = 0; i < ar.length(); i++) {
                                    CollectBean coll = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), CollectBean.class);
                                    collectlist.add(coll);
                                }
                                getcolletIcon();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });

        getComment();
    }

    private void getComment() {
        commentlist.clear();
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETMYCOMMENT)
                .addParams(MzFinal.ID, id)
                .addParams(MzFinal.TYPE, "0")
                .addParams(MzFinal.PAGE, "0")
                .addParams(MzFinal.SIZE, "50")
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
                                for (int i = 0; i < ar.length(); i++) {
                                    CommentBean com = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), CommentBean.class);
                                    commentlist.add(com);
                                }
                                Log.i(TAG, "ID======" + id);
                                if (adapte != null) {
                                    adapte.notifyDataSetChanged();
                                }
                                adapte = new CommentAdapter(getActivity(), commentlist);
                                listView.setAdapter(adapte);

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getcolletIcon() {

        for (CollectBean cb: collectlist) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.dip2px(getActivity(), 28), DeviceUtils.dip2px(getActivity(), 28));
            lp.gravity = Gravity.CENTER;
            lp.setMargins(0, 0, 15, 0);
            ImageView img = new ImageView(getActivity());
            try {

                if (cb.getHeadImgUrl().isEmpty() || cb.getHeadImgUrl() == null)
                    Glide.with(getActivity().getApplicationContext()).load(R.mipmap.test_icon).override(45, 45).bitmapTransform(new CropCircleTransformation(getActivity())).crossFade(500).into(img);
                else
                    Glide.with(getActivity().getApplicationContext()).load(cb.getHeadImgUrl()).override(45, 45).bitmapTransform(new CropCircleTransformation(getActivity())).crossFade(500).into(img);
            } catch (Exception e) {
                Log.i(TAG, "Glide You cannot start a load for a destroyed activity");
            }
            img.setLayoutParams(lp);

            icon_ll.addView(img);
        }
    }

    protected void init() {
        elistener = this;
        commentlist = new ArrayList<>();
        collectlist = new ArrayList<>();
        collect_num = (TextView) view.findViewById(R.id.collect_num);
        listView = (ListView) view.findViewById(R.id.listView);
        msg_edt = (TextView) view.findViewById(R.id.msg_edt);
        icon_ll = (LinearLayout) view.findViewById(R.id.icon_ll);
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        elistener = null;
    }

    @Override
    public void showEditePopup(String content) {
        /**
         * 发送评论;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.ADDPHOTOCOMMENT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams(MzFinal.CONTENT, content)
                .addParams(MzFinal.ID, id)
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
                                ToastUtil.toast2_bottom(getActivity(), "评论成功!");
                                getComment();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
