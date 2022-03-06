package com.lemondev.requestpagedstoragemanagementdemo.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PathDashPathEffect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lemondev.requestpagedstoragemanagementdemo.R;

/**
 * 2022/3/4
 * Created by vibrantBobo
 */

public class TextGridView extends androidx.appcompat.widget.AppCompatTextView {
    public TextGridView(Context context) {
        super(context);
        initView();
    }

    public TextGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TextGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private static final int DEFAULT_HEIGHT = 150;
    private static final int DEFAULT_WIDTH = 150;
    private static final int PADDING = 5;


    public static final int DEFAULT = 0;
    public static final int RIGHT = 1;
    public static final int WRONG = 2;
    private int compareFlag;

    private void initView() {
        compareFlag = DEFAULT;

        this.setMinHeight(DEFAULT_HEIGHT);
        this.setMinWidth(DEFAULT_WIDTH);
        this.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        this.setGravity(Gravity.CENTER);
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gridDefaultColor));
        this.setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    public int getCompareFlag() {
        return compareFlag;
    }

    public void setCompareFlag(int compareFlag) {
        this.compareFlag = compareFlag;

        if(compareFlag==RIGHT){
            this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.compareRight));
        }else if(compareFlag==WRONG){
            this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.compareWrong));
        }else{
            this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gridDefaultColor));
        }
    }
}