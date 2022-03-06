package com.lemondev.requestpagedstoragemanagementdemo.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsetsAnimation;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lemondev.requestpagedstoragemanagementdemo.R;

/**
 * 2022/3/3
 * Created by vibrantBobo
 */

public class GridImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final String TAG = "MTAG";

    public static final float TEXT_SIZE = 80f;
    public static final float PADDING = 50f;
    public static final float ROUND_RADIUS = 50f;


    public GridImageView(Context context) {
        super(context);

        initView();
    }

    public GridImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    public GridImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

    }

    private boolean isSelected;     //是否被选中
    private boolean isOnFinalPlace; //


    private float fixedGridSideLength;      //固定的网格边长


    private Paint paint;
    private Integer valueToDraw = 0;  //
    private Rect bounds;


    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setOnFinalPlace(boolean onFinalPlace) {
        isOnFinalPlace = onFinalPlace;
    }

    private void initView() {
        paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setStrokeWidth(5f);
        paint.setTextSize(TEXT_SIZE);
        paint.setAntiAlias(true);

        bounds = new Rect();
        String text = "8";
        paint.getTextBounds(text, 0, text.length(), bounds);  //默认初始化bounds;
        float endX = PADDING * 2 + (bounds.right - bounds.left);
        float endY = PADDING * 2 + (bounds.bottom - bounds.top);
        fixedGridSideLength = endX > endY ? endX : endY;


        //设置网格大小
        setMinimumHeight((int) fixedGridSideLength);
        setMinimumWidth((int) fixedGridSideLength);
    }

    public void setNumber(int number) {
        this.valueToDraw = number;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            if (valueToDraw != null) {

                drawRamGrid(canvas);
            }
        }
    }

    private void drawRamGrid(Canvas canvas) {
        String text = valueToDraw.toString();
        bounds = new Rect();
        paint.setAntiAlias(true);
        paint.setTextSize(TEXT_SIZE);
        paint.getTextBounds(text, 0, text.length(), bounds);
        if (isSelected) {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.teal_200));
        } else {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.purple_200));
        }

        Log.d(TAG, "drawRamGrid: bounds left:" + bounds.left + " top:" + bounds.top + " right:" + bounds.right + " bottom:" + bounds.right);

        canvas.drawRoundRect(0, 0, fixedGridSideLength, fixedGridSideLength, ROUND_RADIUS, ROUND_RADIUS, paint);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.black));
        canvas.drawText(text, PADDING, PADDING - bounds.top, paint);
//        Log.d("MTAG", "drawRamGrid: ");

    }

}
