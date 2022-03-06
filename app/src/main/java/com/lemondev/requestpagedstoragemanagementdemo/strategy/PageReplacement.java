package com.lemondev.requestpagedstoragemanagementdemo.strategy;

import android.util.Log;

import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem;
import com.lemondev.requestpagedstoragemanagementdemo.model.Page;
import com.lemondev.requestpagedstoragemanagementdemo.model.PageTable;
import com.lemondev.requestpagedstoragemanagementdemo.model.Ram;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/1
 * Created by vibrantBobo
 */

public abstract class PageReplacement {
    protected static final String TAG = "MTAG";

    protected int mCount;
    protected Ram mRam;
    protected PageTable mPageTable;
    protected List<Integer> mPageList;


    //记录文字信息
    protected List<String> stringLog;
    //记录动画步骤
    protected List<AnimationStepItem> animationSteps;

    public PageReplacement(Ram mRam, PageTable mPageTable, List<Integer> mPageList) {
        this.mRam = mRam;
        this.mPageTable = mPageTable;
        this.mPageList = mPageList;
        stringLog = new ArrayList<>();
        animationSteps = new ArrayList<>();
    }

    public Ram getRam() {
        return mRam;
    }

    private void reset() {
        mRam.clear();
        stringLog.clear();
        animationSteps.clear();
        mCount = 0;
    }

    public void doReplacement() {
        // TODO: 2022/3/2
        reset();
    }

    public int getMissingPageCount() {
        return mCount;
    }

    public float getMissingPagePercentage() {
        return (float) mCount / mPageList.size();
    }

    void output() {
        StringBuilder sb = new StringBuilder();
        for (Page p :
                mRam.getRamBlocks()) {
            sb.append(p.getNumber() + ", ");
        }
        Log.d(TAG, "output: " + sb);
    }

    public void setRamSize(int size) {
        mRam.setRamSize(size);
    }

    public List<String> getStringLog() {
        doReplacement();
        return stringLog;
    }

    public List<AnimationStepItem> getAnimationSteps() {
        return animationSteps;
    }
}
