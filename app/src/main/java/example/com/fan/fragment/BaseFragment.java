package example.com.fan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.fan.mylistener.TouchHideTitleListener;
import example.com.fan.utils.TitleUtils;

import static example.com.fan.utils.SynUtils.getTAG;


/**
 * Created by lian on 2017/4/22.
 */
public abstract  class BaseFragment extends Fragment implements TouchHideTitleListener {
    private static final String TAG = getTAG(BaseFragment.class);
    public static boolean UpTouch = true;
    private long changeTime;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(initContentView(), container, false);
        init();
        click();
        initData();
        return view;
    }

    protected abstract int initContentView();

    /**
     * 添加监听事件
     */
    protected abstract void click();

    /**
     * 所有初始化在此方法完成
     */
    protected abstract void init();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onUpTouchListener(int tag,String name) {
        if (UpTouch && System.currentTimeMillis() - changeTime > 100) {
            TitleUtils.ChangeTitleLayout(getActivity(), true, tag, name);
            UpTouch = false;
            changeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onDownTouchListener(int tag, String name) {
        if (!UpTouch && System.currentTimeMillis() - changeTime > 100) {
            TitleUtils.ChangeTitleLayout(getActivity(), false, tag, name);
            changeTime = System.currentTimeMillis();
            UpTouch = true;
        }
    }
}
