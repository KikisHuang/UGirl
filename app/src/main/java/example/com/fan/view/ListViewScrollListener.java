package example.com.fan.view;

import example.com.fan.fragment.BaseFragment;

/**
 * Created by lian on 2017/10/21.
 */
public class ListViewScrollListener extends AbsListViewScrollDetector {
    private BaseFragment fragment;
    private String title;
    public ListViewScrollListener(BaseFragment fragment,String str) {
        this.fragment = fragment;
        this.title = str;
    }

    @Override
    void onScrollUp() {
        fragment.onUpTouchListener(1, title);
    }

    @Override
    void onScrollDown() {
        fragment.onDownTouchListener(1, title);
    }
}
