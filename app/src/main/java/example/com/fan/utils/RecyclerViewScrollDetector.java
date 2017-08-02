package example.com.fan.utils;

import android.support.v7.widget.RecyclerView;

import example.com.fan.fragment.BaseFragment;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/6.
 */
public class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {

    private static final String TAG = getTAG(RecyclerViewScrollDetector.class);
    private int mScrollThreshold;
    private BaseFragment f;
    private String title;

    public RecyclerViewScrollDetector(BaseFragment f, String title) {
        this.f = f;
        this.title = title;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                f.onUpTouchListener(1, title);
            } else {
                f.onDownTouchListener(1, title);
            }
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}
