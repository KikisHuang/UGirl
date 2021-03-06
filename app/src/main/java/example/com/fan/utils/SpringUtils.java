package example.com.fan.utils;

import android.app.Activity;
import android.os.Handler;
import android.widget.ListView;

import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import example.com.fan.R;
import example.com.fan.mylistener.SpringListener;

import static example.com.fan.utils.SynUtils.getRouColors;

/**
 * Created by lian on 2017/5/26.
 * 刷新控件初始化;
 */
public class SpringUtils {

    public static void SpringViewInit(final SpringView springView, final Activity ac, final SpringListener springListener) {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new MeituanHeader(ac, MzFinal.pullAnimSrcs, MzFinal.refreshAnimSrcs));
        springView.setFooter(new MeituanFooter(ac, MzFinal.refreshAnimSrcs));
        springView.setBackgroundColor(getRouColors(R.color.white));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新动画;
                        springView.onFinishFreshAndLoad();
                        springListener.IsonRefresh(0);
                    }
                }, 500);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新动画;
                        springView.onFinishFreshAndLoad();
                        springListener.IsonLoadmore(20);
                    }
                }, 500);
            }
        });
    }

    public static void BottomListAutoRefresh(ListView listview){
      /*  listview.setOnScrollListener(new OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                    }
                }
            }
        });*/
    }
}
