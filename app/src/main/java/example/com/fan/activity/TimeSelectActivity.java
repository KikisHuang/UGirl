package example.com.fan.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.fan.R;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.view.RippleView;
import example.com.fan.view.WheelView;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/10/10.
 */
public class TimeSelectActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = getTAG(TimeSelectActivity.class);
    private LinearLayout time_layout;
    private RippleView verify_button;
    private WheelView wheelView1, wheelView2, wheelView3;
    private List<String> infoList1;
    private List<String> infoList2;
    private List<String> infoList3;
    private int tag;
    private TextView title_1, title_2, title_3;
    private String B;
    private String W;
    private String H;
    private String height = "";

    @Override
    protected void click() {
        verify_button.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.time_select_layout);
        time_layout = f(R.id.time_layout);
        verify_button = f(R.id.verify_button);
        infoList1 = new ArrayList<>();
        infoList2 = new ArrayList<>();
        infoList3 = new ArrayList<>();
        wheelView1 = f(R.id.wheelView1);
        wheelView2 = f(R.id.wheelView2);
        wheelView3 = f(R.id.wheelView3);
        title_1 = f(R.id.title_1);
        title_2 = f(R.id.title_2);
        title_3 = f(R.id.title_3);
        Receiver();
    }

    private void Receiver() {
        tag = Integer.parseInt(getIntent().getStringExtra("select_tag"));
        switch (tag) {
            case 1:
                HeightInit();
                break;
            case 2:
              /*  LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 200), ViewGroup.LayoutParams.WRAP_CONTENT);
                wheelView1.setLayoutParams(lp1);

                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 200), ViewGroup.LayoutParams.WRAP_CONTENT);
                wheelView1.setLayoutParams(lp2);*/
                break;
            case 3:
                BWHInit();
                break;
        }
    }

    private void HeightInit() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 300), ViewGroup.LayoutParams.WRAP_CONTENT);
        wheelView1.setLayoutParams(lp);
        title_1.setText("身高");
        wheelView2.setVisibility(View.GONE);
        wheelView3.setVisibility(View.GONE);
        title_2.setVisibility(View.GONE);
        title_3.setVisibility(View.GONE);
        height = "145";
        for (int i = 145; i < 195; i++) {
            infoList1.add(i + "");
        }

        wheelView1.setOffset(1);
        wheelView1.setItems(infoList1);
        wheelView1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                height = item;
            }
        });
    }

    private void BWHInit() {
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
        wheelView1.setLayoutParams(lp1);
        title_1.setText("胸围");
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
        wheelView2.setLayoutParams(lp2);
        title_2.setText("腰围");
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
        wheelView3.setLayoutParams(lp3);
        title_3.setText("臀围");
        B = "77";
        for (int i = 77; i < 95; i++) {
            infoList1.add(i + " ");
        }
        W = "55";
        for (int i = 55; i < 68; i++) {
            infoList2.add(i + " ");
        }
        H = "79";
        for (int i = 79; i < 97; i++) {
            infoList3.add(i + " ");
        }

        wheelView1.setOffset(1);
        wheelView1.setItems(infoList1);
        wheelView1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                B = item;
            }
        });
        wheelView2.setOffset(1);
        wheelView2.setItems(infoList2);
        wheelView2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                W = item;
            }
        });
        wheelView3.setOffset(1);
        wheelView3.setItems(infoList3);
        wheelView3.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                H = item;
            }
        });
    }

    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_button:
                if (tag == 3) {
                    if (AttestationActivity.alistener != null) {
                        Log.d(TAG, "selectedIndex: " + B + ", item: " + W + " hhhh =" + H);
                        AttestationActivity.alistener.onBWHResult(B, W, H);
                        finish();
                    }
                }
                if (tag == 1) {
                    if (AttestationActivity.alistener != null) {
                        AttestationActivity.alistener.onHeightResult(height);
                        finish();
                    }

                }
                break;
        }
    }
}
