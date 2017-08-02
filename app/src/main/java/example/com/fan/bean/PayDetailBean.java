package example.com.fan.bean;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lian on 2017/6/10.
 */
public class PayDetailBean {
    public TextView getDetails_tv() {
        return details_tv;
    }

    public void setDetails_tv(TextView details_tv) {
        this.details_tv = details_tv;
    }

    public LinearLayout getPay_details() {
        return pay_details;
    }

    public void setPay_details(LinearLayout pay_details) {
        this.pay_details = pay_details;
    }

    private TextView details_tv;
    private LinearLayout pay_details;

}
