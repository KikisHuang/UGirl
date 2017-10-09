package example.com.fan.activity;

import android.view.View;
import android.widget.LinearLayout;

import example.com.fan.R;

import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/9/28.
 */
public class AttestationActivity extends InitActivity implements View.OnClickListener {
//    private RippleView renz_btn;
    private LinearLayout hint_layout,phone_verify_layout,phone_code_layout,fill_in_layout;

    @Override
    protected void click() {
//        renz_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.attestation_activity);
        setTitles(this, "");
        hint_layout = f(R.id.hint_layout);
        phone_verify_layout = f(R.id.phone_verify_layout);
        phone_code_layout = f(R.id.phone_code_layout);
        fill_in_layout = f(R.id.fill_in_layout);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.renz_btn:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
