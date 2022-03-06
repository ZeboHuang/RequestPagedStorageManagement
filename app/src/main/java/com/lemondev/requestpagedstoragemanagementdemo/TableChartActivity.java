package com.lemondev.requestpagedstoragemanagementdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.lemondev.requestpagedstoragemanagementdemo.graphic.TableChart;

import java.util.ArrayList;
import java.util.List;

public class TableChartActivity extends AppCompatActivity {

    private static final String TAG = "TableChartActivity";


    private ValueAnimator valueAnimator;
    TableChart TableChart;

    private MaterialButton testBtn;

    int[] pages = {2, 4, 5, 1, 5, 7, 1, 8, 4, 8, 4, 2, 9, 0, 2, 1, 3, 5, 7, 3, 8, 9, 3, 2, 5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_chart);
        initView();


        drawChilds();
    }

    void drawChilds() {


        List<List<String>> list = new ArrayList<>();
        List<String> rowList = new ArrayList<>();
        for (int i = 0; i < pages.length; i++) {
            rowList.add(String.valueOf(pages[i]));
        }
        list.add(rowList);

        int row = 4;
        int col = 40;

        TableChart.setElements(list);
        TableChart.setCols(col);
        TableChart.setRows(row);
        TableChart.drawElements();
    }

    void initView() {
        testBtn = findViewById(R.id.test_btn);
        TableChart = findViewById(R.id.tableChartLayout);


        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGridAnimation(2, 3);
            }
        });
    }

    void showGridAnimation(int row, int col) {

        TextView t = TableChart.getElementAt(row, col);

        valueAnimator = ValueAnimator.ofInt(0, 5);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                if (value % 2 == 0) {
                    t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.chocolate));
                } else {
                    t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_200));
                }

                Log.d(TAG, "onAnimationUpdate: " + value);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.teal_200));

                Log.d(TAG, "onAnimationEnd: Animitaion END.");
            }
        });

        valueAnimator.start();
    }


}