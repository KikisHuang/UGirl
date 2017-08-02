package example.com.fan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static example.com.fan.utils.SynUtils.JpushInit;

/**
 * Created by lian on 2017/7/5.
 */
public class BootupReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT.equals(intent.getAction())){
            JpushInit(context.getApplicationContext());
        }
    }
}
