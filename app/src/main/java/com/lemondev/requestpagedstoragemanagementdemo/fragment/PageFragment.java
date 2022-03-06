package com.lemondev.requestpagedstoragemanagementdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lemondev.requestpagedstoragemanagementdemo.AnimateActivity;
import com.lemondev.requestpagedstoragemanagementdemo.LineChartActivity;
import com.lemondev.requestpagedstoragemanagementdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/5
 * Created by vibrantBobo
 */

public class PageFragment extends Fragment {

    private StringBuilder randomPageListString;
    private MaterialTextView randomPageListView;
    private MaterialButton randomNumBtn, showLineChartBtn, showAnimateBtn;

    private int maxPage, pageLength;
    private TextInputEditText maxPageView, pageLengthView, ramSizeView;

    private List<Integer> pageList;

    private boolean hasRandomList = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page, container, false);


        randomNumBtn = view.findViewById(R.id.random_num_btn);
        showLineChartBtn = view.findViewById(R.id.show_line_chart_btn);
        showAnimateBtn = view.findViewById(R.id.show_animate_btn);

        maxPageView = view.findViewById(R.id.page_max_num_field);
        pageLengthView = view.findViewById(R.id.request_page_length);

        randomPageListView = view.findViewById(R.id.random_page_list_view);

        randomNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean f1 = maxPageView.getText().toString().isEmpty();
                boolean f2 = pageLengthView.getText().toString().isEmpty();
                if (f1 | f2) {
                    makeToast("请输入完整...");
                } else {
                    maxPage = Integer.parseInt(maxPageView.getText().toString());
                    pageLength = Integer.parseInt(pageLengthView.getText().toString());

                    if (maxPage > 0 && pageLength > 0) {
                        randomPageListString = new StringBuilder();

                        pageList = getRandomPageList(maxPage, pageLength);
                        hasRandomList = true;
                        randomPageListView.setText(randomPageListString);
                    } else {
                        makeToast("请正确输入...");
                    }

                }

            }
        });

        showLineChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasRandomList) {
                    Intent intent = new Intent(getActivity(), LineChartActivity.class);
                    intent.putIntegerArrayListExtra("pageList", (ArrayList<Integer>) pageList);
                    startActivity(intent);
                } else {
                    makeToast("请先产生随机页面序列...");
                }
            }
        });

        showAnimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasRandomList) {
                    Intent intent = new Intent(getActivity(), AnimateActivity.class);
                    intent.putIntegerArrayListExtra("pageList", (ArrayList<Integer>) pageList);
                    startActivity(intent);
                } else {
                    makeToast("请先产生随机页面序列...");
                }
            }
        });


        return view;
    }

    private List<Integer> getRandomPageList(int range, int length) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int pN = (int) (Math.random() * range);
            list.add(pN);
            randomPageListString.append(pN + ", ");
        }
        randomPageListString.deleteCharAt(randomPageListString.length() - 1);
        Log.d("MTAG", "getRandomPageList: " + randomPageListString.toString());
        return list;
    }

    void makeToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
