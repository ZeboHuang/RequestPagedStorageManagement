package com.lemondev.requestpagedstoragemanagementdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationControl;
import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem;
import com.lemondev.requestpagedstoragemanagementdemo.graphic.TableChart;
import com.lemondev.requestpagedstoragemanagementdemo.viewmodel.AnimationStepsViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnimateActivity extends BaseActivity {
    private static final String TAG = "AnimateActivity";

    public static final int PADDING = 50;

    @SuppressWarnings("Since15")
    private List<String> menu = List.of("OPT", "FIFO", "LRU");


    private AppCompatImageButton backBtn;
    private MaterialAutoCompleteTextView dropdownMenu;
    private TextInputEditText ramSizeInputView;
    private MaterialButton startBtn;


    private ExtendedFloatingActionButton nextBtn;
    private TableChart tableChart;

    private List<AnimationStepItem> stepItemList;
    private AnimationControl animationControl;

    private List<Integer> pageList;

//    int[] pages = {2, 4, 5, 1, 5, 7, 1, 8, 4, 8, 4, 2, 9, 0, 2, 1, 3, 5, 7, 3, 8, 9, 3, 2, 5};

    private RequestPageClient requestPageClient;
    private AnimationStepsViewModel viewModel;

    private boolean isAnimationRunning = false;     //标志动画是否执行
    private int stepIndex = 0;    //动画步骤的索引


    private TextView stepLogsView;
    private List<String> stepLogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);


        initView();
        observeAnyDataChanged();

        /**
         *
         */

        nextBtn = findViewById(R.id.animation_next_step_btn);
        tableChart = findViewById(R.id.my_table_layout);
//        testPageList();
        pageList = getIntent().getIntegerArrayListExtra("pageList");
        Log.d(TAG, "onCreate: pageList>size: " + pageList.size());

        requestPageClient = RequestPageClient.getInstance(pageList);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAnimationIteration();

            }
        });

        animationControl = new AnimationControl(tableChart, pageList);

//        animationControl.addListener(new AnimationListener() {
//            @Override
//            public void onAnimationEnd(int position) {
//                runAnimationIteration();
//            }
//        });

        /**
         *
         */


    }

    private void setOldRamListToGrid(List<String> list, int col) {
        //列不变，行索引
        for (int i = 0; i < list.size(); i++) {
            tableChart.getElementAt(i + 1, col).setText(list.get(i));
        }
    }

    private void showStepLogs(int currStep) {
        stepLogsView.setText(stepLogs.get(currStep));
    }

    private void runAnimationIteration() {
        isAnimationRunning = true;
        if (stepItemList != null && stepItemList.size() == stepIndex) {
//            animationControl.showFinish();
            return;
        }
        if (stepItemList != null && !stepItemList.isEmpty() && stepItemList.size() > stepIndex) {
            AnimationStepItem animationStep = stepItemList.get(stepIndex);

            /**
             * 由于页面与内存区公用一个表格，所以内存区的行索引+1
             */

            //把上一步的结果复制
            //是否要记录一个 animationStep.getCol  //获取该步骤页面的列索引
            setOldRamListToGrid(animationStep.getRamList(), stepIndex);
            showStepLogs(stepIndex);

            if (animationStep.isNeedReplaced()) {
                animationControl.showGridReplaceAnimation(0, animationStep.getIndexInPageOrder(), animationStep.getIndexInRam() + 1, animationStep.getIndexInPageOrder());
            } else {
                animationControl.showGridNonReplaceAnimation(0, animationStep.getIndexInPageOrder(), animationStep.getIndexInRam() + 1, animationStep.getIndexInPageOrder());
            }

            stepIndex++;
        }

    }

    private void initGrids() {
//        ramsize在 go click 的时候已经
        tableChart.setCols(pageList.size());
        List<List<String>> list = new ArrayList<>();
        list.add(listIntegerToString(pageList));
        tableChart.setElements(list);
        tableChart.drawElements();
    }

    private List<String> listIntegerToString(List<Integer> integerList) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < integerList.size(); i++) {
            stringList.add(String.valueOf(integerList.get(i)));
        }
        return stringList;
    }

//    private void testPageList() {
//        pageList = new ArrayList<>();
//        for (int i = 0; i < pages.length; i++) {
//            pageList.add(pages[i]);
//        }
//    }

    private void initView() {
        backBtn = findViewById(R.id.animate_back_btn);
        dropdownMenu = findViewById(R.id.type_dropdown_menu_view);
        ramSizeInputView = findViewById(R.id.animate_input_view);
        startBtn = findViewById(R.id.start_btn);


        stepLogsView = findViewById(R.id.animation_step_logs);


        dropdownMenu.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f1 = dropdownMenu.getText().toString().isEmpty();
                boolean f2 = ramSizeInputView.getText().toString().isEmpty();
                if (f1 || f2) {
                    makeToast("请输入完整");
                } else {
                    String type = dropdownMenu.getText().toString();
                    Integer ramSize = Integer.valueOf(ramSizeInputView.getText().toString());


                    tableChart.setRows(ramSize + 1);
                    requestPageClient.setRamSize(ramSize);

                    reset();

                    if (type.equals("OPT")) {
                        viewModel.getAnimations().postValue(requestPageClient.getOPTAnimation());
                        stepLogs = requestPageClient.getOPTLog();
                    } else if (type.equals("FIFO")) {
                        viewModel.getAnimations().postValue(requestPageClient.getFIFOAnimation());
                        stepLogs = requestPageClient.getFIFOLog();
                    } else {
                        viewModel.getAnimations().postValue(requestPageClient.getLRUAnimation());
                        stepLogs = requestPageClient.getLRULog();
                    }


                }


            }
        });


    }

    private void reset() {
        //reset
        stepIndex = 0;
        initGrids();
        stepLogsView.setText("动画模拟");
    }


    private void observeAnyDataChanged() {
        viewModel = new ViewModelProvider(this).get(AnimationStepsViewModel.class);

        viewModel.getAnimations().observe(this, new Observer<List<AnimationStepItem>>() {
            @Override
            public void onChanged(List<AnimationStepItem> animationStepItems) {
                stepItemList = animationStepItems;

                Log.d(TAG, "onChanged: steps: " + stepItemList.size());
            }
        });
    }

//    public void setNextGrids(int currCol) {
//        int ramCurrNum = requestPageClient.getRam().getRamBlocks().size();
//        if (currCol < cols - 1) {
//            for (int i = 0; i < ramCurrNum; i++) {
//                TextGridView textGridView = tableChart.getElementAt(i + 1, currCol + 1);
//                textGridView.setText(String.valueOf(requestPageClient.getRam().get(i).getNumber()));
//            }
//        }
//    }

}