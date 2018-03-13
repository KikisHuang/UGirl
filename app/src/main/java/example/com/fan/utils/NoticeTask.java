package example.com.fan.utils;

import java.util.TimerTask;

import example.com.fan.mylistener.NoticeListener;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/12/19.
 * 公告栏定时器;
 */
public class NoticeTask extends TimerTask {
    private static final String TAG = getTAG(NoticeTask.class);
    private NoticeListener listener;
    private int VPT = 0;
    public static boolean open = false;
    public static boolean OneOpen = true;

    public NoticeTask(NoticeListener listener) {
        this.listener = listener;
        VPT = 0;
    }

    @Override
    public void run() {

        if (listener != null) {
            if (open) {
                if (VPT >= 7) {
                    listener.onNoticeMsg(false);
                    VPT = 0;
                } else
                    VPT++;
            } else if (OneOpen) {
                if (VPT >= 10) {
                    OneOpen = false;
                    listener.onNoticeMsg(true);
                    VPT = 0;
                } else
                    VPT++;
            } else {
                if (VPT >= 40) {
                    listener.onNoticeMsg(true);
                    VPT = 0;
                } else
                    VPT++;
            }

//          Log.i(TAG, "Notice run ...  VPT === " + VPT + " open ==" + open);

        } else
            cancel();
    }
}
