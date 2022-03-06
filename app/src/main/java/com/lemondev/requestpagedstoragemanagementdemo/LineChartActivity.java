package com.lemondev.requestpagedstoragemanagementdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lemondev.requestpagedstoragemanagementdemo.graphic.LineChart;
import com.lemondev.requestpagedstoragemanagementdemo.model.Page;
import com.lemondev.requestpagedstoragemanagementdemo.model.PageTable;
import com.lemondev.requestpagedstoragemanagementdemo.model.Ram;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.FIFO;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.LRU;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.OPT;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.PageReplacement;

import java.util.List;

public class LineChartActivity extends BaseActivity {
    private static final String TAG = "LineChartActivity";


    private static final int DEFAULT_PAGE_RANGE = 10;
    private static final int DEFAULT_PAGELIST_LENGTH = 20;
    private static final int DEFAULT_AXIS_SCALE_Y = 10;

    private MaterialButton startBtn;
    private TextInputEditText searchView;
    private AppCompatImageButton backBtn;

    private LineChart lineChart;

    private RequestPageClient requestPageClient;
    private List<Integer> mPageList;

    private float[] mOPTpts;
    private float[] mFIFOpts;
    private float[] mLRUpts;

    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        lineChart = findViewById(R.id.line_chart);
        lineChart.setAxisTitleX("内存块");
        lineChart.setAxisTitleY("缺页率");

        startBtn = findViewById(R.id.start_btn);
        searchView = findViewById(R.id.linechart_input_view);
        backBtn = findViewById(R.id.linechart_back_btn);


        mIntent = getIntent();
        mPageList = mIntent.getIntegerArrayListExtra("pageList");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean f = searchView.getText().toString().isEmpty();
                if (f) {
                    makeToast("请输入ram大小...");
                } else {
                    int times = Integer.parseInt(searchView.getText().toString());
                    setMaxRamSize(times);
                }
            }
        });
    }


    private void setMaxRamSize(int times) {
        lineChart.reset(times, 10);
        if (mPageList != null) {
            requestPageClient = RequestPageClient.getInstance(mPageList);

            mOPTpts = requestPageClient.getOPTPoints(times);
            lineChart.addLine(mOPTpts, "OPT", R.color.teal_200);

            mFIFOpts = requestPageClient.getFIFOPoints(times);
            lineChart.addLine(mFIFOpts, "FIFO", R.color.purple_200);

            mLRUpts = requestPageClient.getLRUPoints(times);
            lineChart.addLine(mLRUpts, "LRU", R.color.chocolate);


        } else {
            makeToast("请先产生随机页面序列.");
        }

    }


}