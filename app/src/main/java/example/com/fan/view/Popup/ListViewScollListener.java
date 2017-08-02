package example.com.fan.view.Popup;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

import example.com.fan.fragment.BaseFragment;

/**
 * Created by lian on 2017/6/14.
 */
public class ListViewScollListener implements AbsListView.OnScrollListener {
    private Context context;
    private ListView listView;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    private BaseFragment f;
    private String title;

    public ListViewScollListener(Context context, ListView listView, BaseFragment f, String title) {
        this.context = context;
        this.listView = listView;
        this.f = f;
        this.title = title;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                scrollFlag = false;
                // 判断滚动到底部
                if (listView.getLastVisiblePosition() == (listView
                        .getCount() - 1)) {
                }
                // 判断滚动到顶部
                if (listView.getFirstVisiblePosition() == 0) {
                }

                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                scrollFlag = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                scrollFlag = false;
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
        if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
            f.onUpTouchListener(1, title);
        } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
            f.onDownTouchListener(1, title);
        } else {
            return;
        }
        lastVisibleItemPosition = firstVisibleItem;
    }
}
