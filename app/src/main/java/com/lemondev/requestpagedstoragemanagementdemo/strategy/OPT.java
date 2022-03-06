package com.lemondev.requestpagedstoragemanagementdemo.strategy;

import android.util.Log;

import androidx.annotation.NonNull;

import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem;
import com.lemondev.requestpagedstoragemanagementdemo.model.Page;
import com.lemondev.requestpagedstoragemanagementdemo.model.PageTable;
import com.lemondev.requestpagedstoragemanagementdemo.model.Ram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 2022/3/2
 * Created by vibrantBobo
 */

public class OPT extends PageReplacement {

    //初始化时记录的数据
    private int[] mNextVisitedArray;
    private static Map<Integer, Integer> pageCache = new HashMap<>();

    public OPT(Ram mRam, PageTable mPageTable, List<Integer> mPageList) {
        super(mRam, mPageTable, mPageList);
        mNextVisitedArray = new int[mPageList.size()];

        initPages();
    }

    @Override
    public void doReplacement() {
        super.doReplacement();


        AnimationStepItem animationStep;


        for (int i = 0; i < mPageList.size(); i++) {
            int pageNumber = mPageList.get(i);
            Page p = mPageTable.getPage(pageNumber);
            List<String> oldRamList = mRam.getRamList();


            if (!mRam.contains(p)) {
                mCount++;
                p.setInRam(true);
                if (mRam.isFull()) {
                    //置换
                    int index = mRam.getNextReplacedIndex();
                    mRam.get(index).setInRam(false);
                    Page old = mRam.replace(index, p);
                    stringLog.add("内存已满，且内存中页#" + old.getNumber() + "将来最远才会被访问" + " ，将页#" + pageNumber + "与页#" + old.getNumber() + " 进行置换");
                } else {
                    //直接加入
                    mRam.add(p);
                    stringLog.add("内存未满，将页#" + pageNumber + " 添加进内存");

                }
                animationStep = new AnimationStepItem(oldRamList, mRam.getNextReplacedIndex(), i, p.getNextVisited(), true);
            } else {
                stringLog.add("内存中含有该页#" + pageNumber);

                animationStep = new AnimationStepItem(oldRamList, mRam.getNextReplacedIndex(), i, p.getNextVisited(), false);
            }
            updateNextVisited(pageNumber, mNextVisitedArray[i]);

            animationSteps.add(animationStep);

            output();
        }
        stringLog.add("模拟OPT结束");
        stringLog.add("缺页: " + mCount);
        stringLog.add("缺页率: " + getMissingPagePercentage());
        Log.d(TAG, "doReplacement: 缺页：" + mCount);
        Log.d(TAG, "doReplacement: 缺页率" + getMissingPagePercentage());

    }


    /**
     * 每步进一次，间隔距离就少1
     * 置换，添加，再次访问时，需要更新固定初始化记录的距离。
     *
     * @param pageNumber
     * @param value
     */
    private void updateNextVisited(int pageNumber, int value) {
        mPageTable.getPage(pageNumber).setNextVisited(value);
        //除了pageNumber 新加入或者新置换的页，其余页访问字段均需-1
        for (Page p :
                mRam.getRamBlocks()) {
            if (p.getNumber() != pageNumber
                    && p.getNextVisited() != PageTable.WONT_APPEAR_AGAIN
            )
                p.setNextVisited(p.getNextVisited() - 1);
        }
    }

    /**
     * 记录页面与之后相同页面间隔的距离
     */
    private void initPages() {
        pageCache.clear();
        int pageNumber;
        for (int i = mPageList.size() - 1; i >= 0; i--) {
            pageNumber = mPageList.get(i);
            if (pageCache.containsKey(pageNumber)) {
                mNextVisitedArray[i] = pageCache.get(pageNumber) - i;
            } else {
                //倒序第一次出现
                mNextVisitedArray[i] = PageTable.WONT_APPEAR_AGAIN;
            }
            pageCache.put(pageNumber, i);
        }
    }
}
