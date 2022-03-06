package com.lemondev.requestpagedstoragemanagementdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.lemondev.requestpagedstoragemanagementdemo.graphic.TextGridView;

public class TestActivity extends AppCompatActivity {

    private MaterialButton btn;
    private TextGridView textGridView;
    private boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn = findViewById(R.id.test_btn);
        textGridView = findViewById(R.id.text_grid_view);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textGridView.setSelected(isSelected);

                if (isSelected) {
                    isSelected = false;
                } else {
                    isSelected = true;
                }
            }
        });


    }
}