package example.com.fan.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        Receiver();
    }

    private void Receiver() {
        tag = Integer.parseInt(getIntent().getStringExtra("select_tag"));
        switch (tag) {
            case 1:
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                wheelView1.setLayoutParams(lp);

                wheelView2.setVisibility(View.GONE);
                wheelView3.setVisibility(View.GONE);
                break;
            case 2:
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 200), ViewGroup.LayoutParams.WRAP_CONTENT);
                wheelView1.setLayoutParams(lp1);

                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(this, 200), ViewGroup.LayoutParams.WRAP_CONTENT);
                wheelView1.setLayoutParams(lp2);
                wheelView3.setVisibility(View.GONE);
                break;
            case 3:


                break;
        }
    }

    @Override
    protected void initData() {

        wheelView1.setOffset(1);
        wheelView1.setItems(infoList1);
        wheelView1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        wheelView2.setOffset(1);
        wheelView2.setItems(infoList1);
        wheelView2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        wheelView3.setOffset(1);
        wheelView3.setItems(infoList1);
        wheelView3.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_button:

                break;
        }
    }
}
