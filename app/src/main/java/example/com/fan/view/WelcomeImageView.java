package example.com.fan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lian on 2017/7/20.
 */
public class WelcomeImageView extends ImageView {
    public WelcomeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            System.out.println("WelcomeImageView  -> onDraw() Canvas: trying to use a recycled bitmap");
        }
    }
}
