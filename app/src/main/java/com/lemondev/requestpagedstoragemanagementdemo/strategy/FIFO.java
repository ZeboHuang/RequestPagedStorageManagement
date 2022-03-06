package com.lemondev.requestpagedstoragemanagementdemo.strategy;

import android.util.Log;

import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem;
import com.lemondev.requestpagedstoragemanagementdemo.model.Page;
import com.lemondev.requestpagedstoragemanagementdemo.model.PageTable;
import com.lemondev.requestpagedstoragemanagementdemo.model.Ram;

import java.util.List;

/**
 * 2022/3/2
 * Created by vibrantBobo
 */

public class FIFO extends PageReplacement {
    private int headIndex;


    public FIFO(Ram mRam, PageTable mPageTable, List<Integer> mPageList) {
        super(mRam, mPageTable, mPageList);
    }

    @Override
    public void doReplacement() {
        super.doReplacement();
        //模拟队列

        headIndex = 0;

        AnimationStepItem animationStep;

        for (int i = 0; i < mPageList.size(); i++) {
            int pageNumber = mPageList.get(i);
            Page p = mPageTable.getPage(pageNumber);

            List<String> oldRamList = mRam.getRamList();
            //animation

            if (!mRam.contains(p)) {
                mCount++;
                if (mRam.isFull()) {
//                    Page old = mRam.poll();        //先进先出，将队头删去
                    Page old = mRam.replace(headIndex, p);
                    stringLog.add("内存已满，将页#" + pageNumber + "与内存中页#" + old.getNumber() + "置换");


                } else {
                    stringLog.add("内存未满，将页#" + pageNumber + " 添加进内存中");
                    mRam.add(p);    //往队尾插入

                    //添加动画
                }

                //记录旧的ramList 以便动画还没开始时赋值
                animationStep = new AnimationStepItem(oldRamList, headIndex, i, true);
                headIndex = (headIndex + 1) % mRam.getRamSize();
            } else {
                stringLog.add("内存中含有该页#" + pageNumber);

                int containIndex = mRam.indexOf(p);
                //如果已包含，则显示包含的位置
                animationStep = new AnimationStepItem(oldRamList, containIndex, i, false);
            }
            //都是一样的
            //置换动画
            animationSteps.add(animationStep);

            output();
        }
        stringLog.add("模拟FIFO结束");
        stringLog.add("缺页: " + mCount);
        stringLog.add("缺页率: " + getMissingPagePercentage());
        Log.d(TAG, "doReplacement: 缺页：" + mCount);
        Log.d(TAG, "doReplacement: 缺页率" + getMissingPagePercentage());
    }
}
