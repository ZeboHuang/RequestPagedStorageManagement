package com.lemondev.requestpagedstoragemanagementdemo.strategy;

import android.util.Log;

import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem;
import com.lemondev.requestpagedstoragemanagementdemo.model.Page;
import com.lemondev.requestpagedstoragemanagementdemo.model.PageTable;
import com.lemondev.requestpagedstoragemanagementdemo.model.Ram;

import java.util.List;

/**
 * 最近最少置换算法
 * <p>
 * 2022/3/2
 * Created by vibrantBobo
 */

public class LRU extends PageReplacement {
    public LRU(Ram mRam, PageTable mPageTable, List<Integer> mPageList) {
        super(mRam, mPageTable, mPageList);
    }

    @Override
    public void doReplacement() {
        super.doReplacement();

        /**
         * 置换过程中记录访问字段lastVisited
         */


        AnimationStepItem animationStep;


        for (int i = 0; i < mPageList.size(); i++) {
            int pageNumber = mPageList.get(i);
            Page p = mPageTable.getPage(pageNumber);
            List<String> oldRamList = mRam.getRamList();


            if (!mRam.contains(p)) {
                mCount++;
                p.setInRam(true);   //添加或置换的页
                if (mRam.isFull()) {
                    int index = mRam.getLastReplacedIndex();
                    mRam.get(index).setInRam(false);
                    Page old = mRam.replace(index, p);
                    stringLog.add("内存已满，且页#" + old.getNumber() + "最近最久未访问，将页#" + pageNumber + "与页#" + old.getNumber() + " 进行置换");

                    animationStep = new AnimationStepItem(oldRamList, mRam.getLastReplacedIndex(), i, p.getLastVisited(), true);
                } else {
                    mRam.add(p);
                    stringLog.add("内存未满，将页#" + pageNumber + " 添加进内存中");
                    animationStep = new AnimationStepItem(oldRamList, mRam.indexOf(p), i, p.getLastVisited(), true);
                }
            } else {
                stringLog.add("内存中含有该页#" + pageNumber);

                animationStep = new AnimationStepItem(oldRamList, mRam.getLastReplacedIndex(), i, p.getLastVisited(), false);
            }
            //每访问一个页面，访问字段都要更新
            updateLastVisited(pageNumber);

            animationSteps.add(animationStep);

            output();
        }
        stringLog.add("模拟LRU结束");
        stringLog.add("缺页: " + mCount);
        stringLog.add("缺页率: " + getMissingPagePercentage());
        Log.d(TAG, "doReplacement: 缺页：" + mCount);
        Log.d(TAG, "doReplacement: 缺页率" + getMissingPagePercentage());

    }

    /**
     * @param pageNumber
     */
    private void updateLastVisited(int pageNumber) {
        mPageTable.getPage(pageNumber).setLastVisited(0);
        for (Page p :
                mRam.getRamBlocks()) {
            if (p.getNumber() != pageNumber) {
                p.setLastVisited(p.getLastVisited() + 1);
            }
        }
    }
}
