package example.com.fan.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.List;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.bean.GalleryBean;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.SynUtils;
import example.com.fan.view.Popup.PayPopupWindow;

import static example.com.fan.utils.IntentUtils.goChoicenessPage;
import static example.com.fan.utils.IntentUtils.goHostModelPage;
import static example.com.fan.utils.IntentUtils.goNewestPage;
import static example.com.fan.utils.IntentUtils.goVideoOfVrPage;
import static example.com.fan.utils.IntentUtils.goVipPage;
import static example.com.fan.utils.SynUtils.Login;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by kk on 2016/7/26.
 */
public class MyPagerButtomAdapter extends PagerAdapter {
    private static final String TAG = getTAG(MyPagerButtomAdapter.class);
    private List<GalleryBean> list;
    private Context context;
    private PopupWindow pay;

    public MyPagerButtomAdapter(List<GalleryBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = list.get(position).getView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (list.get(position).getId()) {
                    //最新
                    case 1:
                        goNewestPage(context);
                        break;
                    //热门模特;
                    case 2:
                        goHostModelPage(context);
                        break;
                    //Vip页面;
                    case 3:
                        if (SynUtils.LoginStatusQuery()) {
                            if (MyAppcation.VipFlag)
                                goVipPage(context);
                            else {
                                PayPopupWindow p = new PayPopupWindow(context);
                                View contentView = LayoutInflater.from(context).inflate(R.layout.pay_pp_layout, null);
                                int width = DeviceUtils.getWindowWidth(context) * 8 / 10;
                                int h = (int) (DeviceUtils.getWindowHeight(context) * 6 / 10);
                                pay = new PopupWindow(contentView, width, h);
                                p.ScreenPopupWindow(LayoutInflater.from(context).inflate(R.layout.my_fragment, null), pay, 1, contentView);
                            }
                        } else
                            Login(context);
                        break;
                    //VR
                    case -2:
                        goVideoOfVrPage(context, "1");
                        break;
                    //Video
                    case -3:
                        goVideoOfVrPage(context, "0");
                        break;
                    //精选图集页面
                    default:
                        goChoicenessPage(context, list.get(position).getTypeName());
                        break;
                }
            }
        });

        container.addView(view);
        return list.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position).getView());
    }
}

