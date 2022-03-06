package com.lemondev.requestpagedstoragemanagementdemo.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;

import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.lemondev.requestpagedstoragemanagementdemo.RequestPageClient;
import com.lemondev.requestpagedstoragemanagementdemo.graphic.TableChart;
import com.lemondev.requestpagedstoragemanagementdemo.graphic.TextGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/4
 * Created by vibrantBobo
 */

public class AnimationControl implements AnimationInterface {
    private static final String TAG = "AnimationControl";


    private TableChart tableChart;

    private ValueAnimator valueAnimator;        //用于控制动画的

    private List<AnimationListener> listeners;


    private List<Integer> pageList;

    private int rows, cols;

    public AnimationControl(TableChart tableChart, List<Integer> pageList) {
        this.tableChart = tableChart;
        rows = this.tableChart.getRows();
        cols = this.tableChart.getCols();
        this.pageList = pageList;
        listeners = new ArrayList<>();

        Log.d(TAG, "AnimationControl: rows&cols: " + rows + ", " + cols);
    }

    public void addListener(AnimationListener listener) {
        listeners.add(listener);
    }


//    public void showFIFOAnimationSteps() {
//        if (pageList == null) {
//            Log.e(TAG, "showFIFOAnimationSteps: pageList is null");
//            return;
//        }
//
//        List<AnimationStepsItem> mFIFOAnimationSteps = requestPageClient.getFIFOAnimations(pageList);
//
//        Log.d(TAG, "showFIFOAnimationSteps: size: " + mFIFOAnimationSteps.size());
//
//        for (AnimationStepsItem step : mFIFOAnimationSteps) {
//            int indexInRam = step.getIndexInRam();
//            int indexInPageList = step.getIndexInPageList();
//
//
//            if (step.isReplaced()) {
//
//                showGridReplaceAnimation(0, indexInPageList, indexInRam + 1, indexInPageList);
//            } else {
//                Log.d(TAG, "showFIFOAnimationSteps: no replace");
//                showGridCompareAnimation(0, indexInPageList, indexInRam + 1, indexInPageList);
//            }
//            setNextGrids(indexInPageList);
//        }
//    }


    /**
     * compare right
     *
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     */

    @Override
    public void showGridReplaceAnimation(int row1, int col1, int row2, int col2) {

        TextGridView grid1 = tableChart.getElementAt(row1, col1);       //页面序列中的
        TextGridView grid2 = tableChart.getElementAt(row2, col2);       //内存区中的

        valueAnimator = ValueAnimator.ofInt(0, 7);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                if (value % 2 == 0) {
                    grid1.setCompareFlag(TextGridView.WRONG);
                    grid2.setCompareFlag(TextGridView.WRONG);
                } else {
                    grid1.setCompareFlag(TextGridView.DEFAULT);
                    grid2.setCompareFlag(TextGridView.DEFAULT);
                }
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


                grid1.setCompareFlag(TextGridView.DEFAULT);
                grid2.setCompareFlag(TextGridView.DEFAULT);

                //置换
                grid2.setText(grid1.getText());
            }
        });

        valueAnimator.start();

    }

    /**
     * comare wrong
     *
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     */

    @Override
    public void showGridNonReplaceAnimation(int row1, int col1, int row2, int col2) {

        Log.d(TAG, "showGridCompareAnimation: ");

        TextGridView grid1 = tableChart.getElementAt(row1, col1);
        TextGridView grid2 = tableChart.getElementAt(row2, col2);

        valueAnimator = ValueAnimator.ofInt(0, 5);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                if (value % 2 == 0) {
                    grid1.setCompareFlag(TextGridView.RIGHT);
                    grid2.setCompareFlag(TextGridView.RIGHT);
                } else {
                    grid1.setCompareFlag(TextGridView.DEFAULT);
                    grid2.setCompareFlag(TextGridView.DEFAULT);
                }
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                grid1.setCompareFlag(TextGridView.DEFAULT);
                grid2.setCompareFlag(TextGridView.DEFAULT);
            }
        });

        valueAnimator.start();
    }

    @Override
    public void showGridCompareAnimation(int row1, int col1, int distance) {



    }

    private void notifyAnimationEnd(int position) {
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onAnimationEnd(position);
            }
        }
    }
}
