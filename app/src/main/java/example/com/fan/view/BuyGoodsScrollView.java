package example.com.fan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lian on 2017/6/23.
 */
public class BuyGoodsScrollView extends ScrollView {
    private static String TAG = BuyGoodsScrollView.class.getName();

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public BuyGoodsScrollView(Context context) {
        super(context);
    }

    public BuyGoodsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuyGoodsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:

                if (mScrollListener != null) {
                    int contentHeight = getChildAt(0).getHeight();
                    int scrollHeight = getHeight();

                    int scrollY = getScrollY();
                    mScrollListener.onScroll(scrollY);

                    if (scrollY + scrollHeight >= contentHeight || contentHeight <= scrollHeight) {
                        mScrollListener.onScrollToBottom();
                    } else {
                        mScrollListener.notBottom();
                    }

                    if (scrollY == 0) {
                        mScrollListener.onScrollToTop();
                    }

                }

                break;
        }
        boolean result = super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);

        return result;

    }

    public interface ScrollListener{
        void onScrollToBottom();
        void onScrollToTop();
        void onScroll(int scrollY);
        void notBottom();
    }  }