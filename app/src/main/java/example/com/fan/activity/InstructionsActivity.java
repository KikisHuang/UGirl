package example.com.fan.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import example.com.fan.R;
import example.com.fan.utils.MzFinal;

import static example.com.fan.utils.IntentUtils.goInstructionPhotoPage;
import static example.com.fan.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/11/1.
 */
public class InstructionsActivity extends InitActivity implements View.OnClickListener {
    public static final String TAG = "InstructionsActivity";
    private ImageView s1_img, s2_img, s3_img, s4_img, s5_img, v1_img, v2_img;

    @Override
    protected void click() {
        s1_img.setOnClickListener(this);
        s2_img.setOnClickListener(this);
        s3_img.setOnClickListener(this);
        s4_img.setOnClickListener(this);
        s5_img.setOnClickListener(this);

        v1_img.setOnClickListener(this);
        v2_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.instrution_layout);
        setTitles(this, "操作指南");
        s1_img = f(R.id.s1_img);
        s2_img = f(R.id.s2_img);
        s3_img = f(R.id.s3_img);
        s4_img = f(R.id.s4_img);
        s5_img = f(R.id.s5_img);

        v1_img = f(R.id.v1_img);
        v2_img = f(R.id.v2_img);
    }

    @Override
    protected void initData() {
        Glide.with(this).load(MzFinal.INSTRUCTION_S1).into(s1_img);
        Glide.with(this).load(MzFinal.INSTRUCTION_S2).into(s2_img);
        Glide.with(this).load(MzFinal.INSTRUCTION_S3).into(s3_img);
        Glide.with(this).load(MzFinal.INSTRUCTION_S4).into(s4_img);
        Glide.with(this).load(MzFinal.INSTRUCTION_S5).into(s5_img);

        Glide.with(this).load(MzFinal.INSTRUCTION_V1).into(v1_img);
        Glide.with(this).load(MzFinal.INSTRUCTION_V2).into(v2_img);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s1_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_S1);
                break;
            case R.id.s2_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_S2);
                break;
            case R.id.s3_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_S3);
                break;
            case R.id.s4_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_S4);
                break;
            case R.id.s5_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_S5);
                break;
            case R.id.v1_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_V1);
                break;
            case R.id.v2_img:
                goInstructionPhotoPage(this, MzFinal.INSTRUCTION_V2);
                break;

        }
    }
}
