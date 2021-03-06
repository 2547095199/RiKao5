package fragment.bwie.com.rikao5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by CZ on 2017/11/4.
 */
public class CustomProgrssView extends View {
    private boolean runing = true;
    private int progress = 0;
    //定义一个画笔
    private Paint paint;


    public CustomProgrssView(Context context) {
        super(context);
    }


    public CustomProgrssView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //创建一个画笔
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //设置画笔的颜色
        paint.setColor(Color.RED);
        //设置画笔，填充是空心的
        paint.setStyle(Paint.Style.STROKE);

    }

    float sweep;

    public CustomProgrssView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取当前view的宽度
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int radius = 50;
        //设置画笔的粗细
        paint.setStrokeWidth(10);
        //定义一个区域
        RectF rectF = new RectF(x - radius, y - radius, x + radius, y + radius);
        //true从中心点开始画  false 中心点不现实    。画弧
        canvas.drawArc(rectF, -90, progress, false, paint);
        int text = (int) ((float) progress / 360 * 100);
        //测量字符串的宽度
        float textWidth = paint.measureText(text + "%");
        Rect recttext = new Rect();
        //获取字符串的高度
        paint.getTextBounds(text + "%", 0, (text + "%").length(), recttext);
        //文字大小
        paint.setTextSize(10);
        paint.setStrokeWidth(1);
        //控制文字位置
        canvas.drawText(text + "%", x - textWidth / 2, y + recttext.height() / 4, paint);


    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runing) {
                    if (progress >= 360) {
                        runing = false;
                        return;
                    }
                    System.out.println("progress" + progress);
                    progress += 10;
                    //子线程刷新，系统调用onDraw方法
                    postInvalidate();
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

