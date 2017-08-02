package example.com.fan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * 可以监听ScrollView的上下滑动 ，实现ScrollListener接口，调用setScrollListener(ScrollListener l)方法。
 * SCROLL_UP :ScrollView正在向上滑动
 * SCROLL_DOWN :ScrollView正在向下滑动
 *
 * @author xm
 */
public class ObservableScrollView extends ScrollView {

    private static final String TAG = getTAG(ObservableScrollView.class);
    private ScrollListener mListener;

    public interface ScrollListener {
        void scrollOritention(int oritention);
    }

    /**
     * ScrollView正在向上滑动
     */
    public static final int SCROLL_UP = 0x01;

    /**
     * ScrollView正在向下滑动
     */
    public static final int SCROLL_DOWN = 0x10;

    /**
     * 最小的滑动距离
     */
    private static final int SCROLLLIMIT = 25;


    public ObservableScrollView(Context context) {
        super(context, null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (oldt > t && oldt - t > SCROLLLIMIT) {// 向下
            if (mListener != null)
                mListener.scrollOritention(SCROLL_DOWN);
        } else if (oldt < t && t - oldt > SCROLLLIMIT) {// 向上
            if (mListener != null)
                mListener.scrollOritention(SCROLL_UP);
        }
        if (getScrollY() == 0) {
            if (mListener != null)
                mListener.scrollOritention(SCROLL_DOWN);
        }

    }
    public void setScrollListener(ScrollListener l) {
        this.mListener = l;
    }
}