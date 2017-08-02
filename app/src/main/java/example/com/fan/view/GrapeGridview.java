package example.com.fan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by lian on 2017/5/25.
 * GridView自定义（禁止滑动）;
 */
public class GrapeGridview extends GridView {

    public GrapeGridview(Context context) {
        super(context);
// TODO Auto-generated constructor stub
    }

    public GrapeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }

    public GrapeGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
// TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

    //通过重新dispatchTouchEvent方法来禁止滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
// TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//禁止Gridview进行滑动
        }

        return super.dispatchTouchEvent(ev);
    }
}