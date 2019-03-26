package com.dverybest.promiserynote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by BEN on 19/10/2018.
 */

public class LinedEditText extends android.support.v7.widget.AppCompatEditText {

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
    }

    private Rect mRect;
    private Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        //int count = getLineCount();

        int height = getHeight();
        int line_height = getLineHeight();

        int count = height / line_height;

        if (getLineCount() > count)
            count = getLineCount();//for long text with scrolling

        Rect r = mRect;

        Paint paint = mPaint;
       // paint.setStrokeWidth();
        int baseline = getLineBounds(0, r);//first line

        for (int i = 0; i < count; i++) {

            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            baseline += getLineHeight();//next line
        }

        super.onDraw(canvas);
    }
/*
*  <view
    xmlns:android="http://schemas.android.com/apk/res/android"
    class="com.dverybest.promiserynote.LinedEditText"
    android:id="@+id/body"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
       android:text="b\nc\na\n"
       android:layout_centerInParent="true"
    android:background="@android:color/transparent"
    android:capitalize="sentences"
    android:fadingEdge="vertical"
    android:gravity="top"
    android:padding="5dp"
    android:scrollbars="vertical"
    android:textSize="22sp" />
*
* */

}
