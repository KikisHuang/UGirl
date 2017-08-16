package example.com.fan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import example.com.fan.activity.PayActivity;
import example.com.fan.activity.PhotoActivity;
import example.com.fan.fragment.son.PictureSlideFragment;
import example.com.fan.utils.MzFinal;
import example.com.fan.utils.ToastUtil;

import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.getUserVip;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = getTAG(WXPayEntryActivity.class);

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, MzFinal.WECHATPAY);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case 0:
                ToastUtil.toast2_bottom(this, "支付成功");
                try {
                    if (PictureSlideFragment.PayListener != null && PhotoActivity.tlistener != null) {
                        MzFinal.isPay = true;
                        PictureSlideFragment.PayListener.onPayRefresh();
                    }
                    if(PayActivity.paylistener!=null)
                        PayActivity.paylistener.onPayRefresh();

                    getUserVip(getApplicationContext());
                } catch (Exception e) {
                    Log.i(TAG, "Error ==== " + e);
                }
                break;
            case -1:
                ToastUtil.toast2_bottom(this, "支付失败");
                break;
            case -2:
                ToastUtil.toast2_bottom(this, "支付失败!");
                break;
        }
        finish();
    }

    @Override
    public void onReq(BaseReq req) {
        Log.i(TAG, "支付失败");
    }

}