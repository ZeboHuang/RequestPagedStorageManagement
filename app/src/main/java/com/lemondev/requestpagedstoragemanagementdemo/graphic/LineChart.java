package com.lemondev.requestpagedstoragemanagementdemo.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.fonts.FontFamily;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lemondev.requestpagedstoragemanagementdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/2
 * Created by vibrantBobo
 */

public class LineChart extends View {
    private static final int DIRECTION_HORIZONTAL = 1;
    private static final int DIRECTION_VERTICAL = 2;

    private static final String TAG = "LineChart";
    private static final int PADDING = 100;
    private static final int SCALE_WIDTH = 15;
    private static final int POINT_RADIUS = 10;

    private static final int BASE_PERCENTILE = 100;

    //paint
    private static final float PAINT_TEXT_SIZE_SMALL = 30f;
    private static final float PAINT_TEXT_SIZE_MEDIUM = 40f;
    private static final float PAINT_TEXT_SIZE_BIG = 50f;
    private static final float LINE_WIDTH_THIN = 5f;
    private static final float LINE_WIDTH_MEDIUM = 10f;
    private static final float LINE_WIDTH_THICK = 15f;


    private int axisPointNumX = 5;       //刻度数目
    private int axisPointNumY = 5;       //刻度数目

    private String axisTitleX = "x";
    private String axisTitleY = "y";


    private float mWidth;       //屏幕宽高
    private float mHeight;

    private float axisWidth;    //坐标轴长度
    private float axisLength;

    private List<Line> lines;       //可以添加多条折线

    private Paint solidLinePaint;
    private Paint pointPaint;
    private Paint colorPaint;
    private Paint textPaint;

    public int getAxisPointNumX() {
        return axisPointNumX;
    }

    public void setAxisPointNumX(int axisPointNumX) {
        this.axisPointNumX = axisPointNumX;
    }

    public int getAxisPointNumY() {
        return axisPointNumY;
    }

    public void setAxisPointNumY(int axisPointNumY) {
        this.axisPointNumY = axisPointNumY;
    }

    public String getAxisTitleX() {
        return axisTitleX;
    }

    public void setAxisTitleX(String axisTitleX) {
        this.axisTitleX = axisTitleX;
    }

    public String getAxisTitleY() {
        return axisTitleY;
    }

    public void setAxisTitleY(String axisTitleY) {
        this.axisTitleY = axisTitleY;
    }

    public LineChart(Context context) {
        super(context);
        initView();
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        axisLength = mWidth - PADDING * 2;
        axisWidth = mHeight / 3 * 2;
    }

    private void initView() {
        lines = new ArrayList<>();

        solidLinePaint = new Paint();
        solidLinePaint.setStrokeWidth(LINE_WIDTH_THIN);

        pointPaint = new Paint();
        pointPaint.setStrokeWidth(LINE_WIDTH_MEDIUM);

        colorPaint = new Paint();
        colorPaint.setStrokeWidth(LINE_WIDTH_THIN);
        colorPaint.setColor(ContextCompat.getColor(getContext(), R.color.purple_200));
        colorPaint.setTextSize(PAINT_TEXT_SIZE_MEDIUM);

        textPaint = new Paint();
        textPaint.setTextSize(PAINT_TEXT_SIZE_MEDIUM);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (canvas != null) {
            float dx = PADDING;
            float dy = mHeight / 4 * 3;
            canvas.translate(dx, dy);

            drawAxis(canvas);

            for (Line l :
                    lines) {
                l.drawPoints(canvas);
                l.drawPointsLines(canvas);
            }
            drawChartLabel(canvas);
        }
    }

    public void reset(int axisPointNumX, int axisPointNumY) {
        lines.clear();
        this.axisPointNumX = axisPointNumX;
        this.axisPointNumY = axisPointNumY;
        invalidate();
    }

    public void addLine(float[] pts, String name, int color) {
        lines.add(new Line(pts, name, color));

    }


    private void drawAxis(Canvas canvas) {
        canvas.drawLine(0, 0, axisLength, 0, solidLinePaint);
        canvas.drawLine(0, 0, 0, -axisWidth, solidLinePaint);
        drawAxisScale(canvas, axisLength, axisWidth, axisPointNumX, axisPointNumY);
        drawAxisArrow(canvas, axisLength, axisWidth);
        canvas.drawText(axisTitleX, axisLength-PADDING/2, PADDING / 2, textPaint);
        canvas.drawText(axisTitleY, 0, -axisWidth - PADDING / 2, textPaint);
    }


    private void drawAxisScale(Canvas canvas, float dxlength, float dylength, int scaleX, int scaleY) {
        float dx = (dxlength - PADDING) / scaleX;   //增量
        for (int i = 1; i <= scaleX; i++) {
            canvas.drawLine(dx * i, 0, dx * i, SCALE_WIDTH, solidLinePaint);
            canvas.drawText(String.valueOf(i), dx * i - SCALE_WIDTH, PADDING / 4 * 3, textPaint);
        }
        float dy = (dylength - PADDING) / scaleY;   //增量
        for (int i = 1; i <= scaleY; i++) {
            canvas.drawLine(0, -dy * i, -SCALE_WIDTH, -dy * i, solidLinePaint);
            canvas.drawText(String.format("%.0f%%", (float) i / scaleY * BASE_PERCENTILE), PADDING / 4, -dy * i + SCALE_WIDTH, textPaint);
        }
    }

    private void drawAxisArrow(Canvas canvas, float dxlength, float dylength) {
        canvas.drawLine(dxlength, 0, dxlength - SCALE_WIDTH, -SCALE_WIDTH, solidLinePaint);
        canvas.drawLine(dxlength, 0, dxlength - SCALE_WIDTH, SCALE_WIDTH, solidLinePaint);
        canvas.drawLine(0, -dylength, SCALE_WIDTH, -dylength + SCALE_WIDTH, solidLinePaint);
        canvas.drawLine(0, -dylength, -SCALE_WIDTH, -dylength + SCALE_WIDTH, solidLinePaint);
    }

    private void drawChartLabel(Canvas canvas) {
        canvas.translate(axisLength - PADDING * 3, -axisWidth + PADDING);
        for (int i = 0; i < lines.size(); i++) {
            Line l = lines.get(i);
            canvas.drawLine(0, i * PADDING / 2, PADDING, i * PADDING / 2, l.paint);
            canvas.drawText(l.name, PADDING / 3 * 4, i * PADDING / 3 * 2, l.paint);
        }
    }


    private class Line {
        private float[] points = null;
        private Paint paint;
        private String name;

        public Line(float[] points, String name, int color) {
            this.points = points.clone();
            paint = new Paint();
            paint.setTextSize(PAINT_TEXT_SIZE_MEDIUM);
            paint.setStrokeWidth(LINE_WIDTH_MEDIUM);

            paint.setColor(ContextCompat.getColor(getContext(), color));

            this.name = name;
        }

        public int getPaintColor() {
            return paint.getColor();
        }


        private float[] convertPoints(int convertType) {
            float[] pts = new float[points.length];
            if (convertType == BASE_PERCENTILE) {//百分制
                for (int i = 0; i < points.length - 1; i += 2) {
                    float x = points[i] * (axisLength - PADDING) / axisPointNumX;
                    float y = -points[i + 1] * (axisWidth - PADDING);
                    pts[i] = x;
                    pts[i + 1] = y;
                }
            }
            return pts;
        }


        public void drawPoints(Canvas canvas) {
            float[] pts = convertPoints(BASE_PERCENTILE);
            for (int i = 0; i < points.length - 1; i += 2) {
                canvas.drawCircle(pts[i], pts[i + 1], POINT_RADIUS, paint);
                canvas.drawText(String.format("%.0f%%", this.points[i + 1] * BASE_PERCENTILE), pts[i] + SCALE_WIDTH, pts[i + 1] - SCALE_WIDTH, paint);
            }
        }

        public void drawPointsLines(Canvas canvas) {
            float[] pts = convertPoints(BASE_PERCENTILE);
            if (pts.length >= 4) {
                float x = pts[0];
                float y = pts[1];
                for (int i = 2; i < pts.length - 1; i += 2) {
                    canvas.drawLine(x, y, pts[i], pts[i + 1], paint);
                    x = pts[i];
                    y = pts[i + 1];
                }
            }
        }

    }
}
