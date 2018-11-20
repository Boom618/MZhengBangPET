package com.ty.zbpet.presenter.product;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * @author PVer on 2018/10/28.
 */
public class Test extends View {


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Test(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        Path path = new Path();
        // path 方形的四个位置 和 方向（）
        path.addRect(0, 0, 2, 10, Path.Direction.CW);
        // PathDashPathEffect : path  间距  开始预留间距 样式
        paint.setPathEffect(new PathDashPathEffect(path, 2, 0, PathDashPathEffect.Style.ROTATE));
        PathMeasure pathMeasure = new PathMeasure(path, false);

        float length = pathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();

        int height = rect.height();

        RectF rectF = new RectF(getWidth() - 90, getHeight() - 90, getWidth() + 90, getHeight() + 90);

        // drawArc ：矩形 开始角度 结束角度 是否连接圆心 画笔
        canvas.drawArc(rectF, 45, 360 - 135, false, paint);


    }
}
