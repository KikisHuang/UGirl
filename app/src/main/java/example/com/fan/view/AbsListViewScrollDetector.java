package example.com.fan.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by lian on 2017/10/21.
 * 精确监听listView上下滑动;
 */
abstract class AbsListViewScrollDetector implements AbsListView.OnScrollListener {
    //第一个可视的item的顶部坐标
    private int mLastScrollY;
    //上一次滑动的第一个可视item的索引值
    private int mPreviousFirstVisibleItem;
    //列表控件，如ListView
    private AbsListView mListView;
    /**
     * 滑动距离响应的临界值，这个值可根据需要自己指定
     * 只有只有滑动距离大于mScrollThreshold，才会响应滑动动作
     */
    private int mScrollThreshold;

    //ListView向上滑动时会被调用，由子类去实现。
    abstract void onScrollUp();

    //ListView下滑动时会被调用，由子类去实现。
    abstract void onScrollDown();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    //核心方法，该方法封装了滑动方向判断的逻辑，当ListView产生滑动之后，该方法会被调用。
    //1.首先，判断滑动后第一个可视的item和滑动前是否同一个，如果是同一个，进入第2步，否则进入第3步
    //2.这次滑动距离小于一个Item的高度，比较第一个可视的item的顶部坐标在滑动前后的差值，就知道了滑动的距离
    //3.直接比较滑动前后firstVisibleItem的值就可以判断滑动方向了。
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == 0) return;
        if (isSameRow(firstVisibleItem)) {
            int newScrollY = getTopItemScrollY();
            //判断滑动距离是否大于mScrollThreshold
            boolean isSignificantDelta = Math.abs(mLastScrollY - newScrollY) > mScrollThreshold;
            if (isSignificantDelta) {
                //对于第一个可视的item，根据其前后两次的顶部坐标判断滑动方向
                if (mLastScrollY > newScrollY) {
                    onScrollUp();
                } else {
                    onScrollDown();
                }
            }
            mLastScrollY = newScrollY;
        } else {
            if (firstVisibleItem > mPreviousFirstVisibleItem) {
                onScrollUp();
            } else {
                onScrollDown();
            }
            mLastScrollY = getTopItemScrollY();
            mPreviousFirstVisibleItem = firstVisibleItem;
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }

    public void setListView(@NonNull AbsListView listView) {
        mListView = listView;
    }

    //滑动距离：不超过一个item的高度
    private boolean isSameRow(int firstVisibleItem) {
        return firstVisibleItem == mPreviousFirstVisibleItem;
    }

    //获取第一个item的纵坐标高度
    private int getTopItemScrollY() {
        if (mListView == null || mListView.getChildAt(0) == null) return 0;
        View topChild = mListView.getChildAt(0);
        return topChild.getTop();
    }
}