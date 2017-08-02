package example.com.fan.server;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import static cn.jpush.android.api.JPushInterface.isPushStopped;
import static cn.jpush.android.api.JPushInterface.resumePush;
import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/7/5.
 * 进程拉起服务;
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    private static final String TAG = getTAG(MyJobService.class);
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            Toast.makeText(MyJobService.this, "MyJobService", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "MyJobService");
            JobParameters param = (JobParameters) msg.obj;
            jobFinished(param, true);
            if (isPushStopped(getApplicationContext())) {
                resumePush(getApplicationContext());
                Log.i(TAG, "JPush Stopped true");
            } else
                Log.i(TAG, "JPush Stopped false");

//            JpushInit(getApplicationContext());
            return true;
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Message m = Message.obtain();
        m.obj = params;
        handler.sendMessage(m);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        handler.removeCallbacksAndMessages(null);
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Job Server onDestroy");
    }
}
