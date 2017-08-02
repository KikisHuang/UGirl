package example.com.fan.utils;

import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import example.com.fan.mylistener.OverallRefreshListener;

/**
 * Created by lian on 2017/7/8.
 * <p/>
 * 全局广播刷新管理类;
 */
public class ListenerManager {
    private static final String TAG = "ListenerManager";
    /**
     * 单例模式
     */
    public static ListenerManager listenerManager;

    /**
     * 注册的接口集合，发送广播的时候都能收到
     */
    private List<OverallRefreshListener> iListenerList = new CopyOnWriteArrayList<OverallRefreshListener>();

    /**
     * 获得单例对象
     */
    public static ListenerManager getInstance() {
        if (listenerManager == null) {
            listenerManager = new ListenerManager();
        }
        return listenerManager;
    }

    /**
     * 注册监听
     */
    public void registerListtener(OverallRefreshListener iListener) {
        iListenerList.add(iListener);
        Log.i(TAG,"注册观察者");
    }

    /**
     * 注销监听
     */
    public void unRegisterListener(OverallRefreshListener iListener) {
        if (iListenerList.contains(iListener)) {
            iListenerList.remove(iListener);
            Log.i(TAG,"移除观察者");
        }
    }

    /**
     * 发送广播
     * @param net
     */
    public void sendBroadCast(boolean net) {
        for (OverallRefreshListener iListener : iListenerList) {
            iListener.notifyAllActivity(net);
            Log.i(TAG,"通知被观察者");
        }
    }

}
