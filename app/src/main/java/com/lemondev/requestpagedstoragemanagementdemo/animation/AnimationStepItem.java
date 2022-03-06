package com.lemondev.requestpagedstoragemanagementdemo.animation;

import java.util.List;

/**
 * 2022/3/5
 * Created by vibrantBobo
 */

public class AnimationStepItem {

    //记录内存区历史
    private List<String> ramList;

    private int indexInRam;     //记录目标比较对象的 行索引
    private int indexInPageOrder;   //记录页面序列的 列索引

    private int compareDistance;    //LRU, OPT 横向比较距离 =0 FIFO, <0 LRU 向后比较 >0 向前比较

    private boolean isNeedReplaced;


    public AnimationStepItem(List<String> numberInRam, int indexInRam, int indexInPageOrder) {
        this.ramList = numberInRam;
        this.indexInRam = indexInRam;
        this.indexInPageOrder = indexInPageOrder;
        compareDistance = 0;
        isNeedReplaced = false;
    }

    public AnimationStepItem(List<String> ramList, int indexInRam, int indexInPageOrder, boolean isNeedReplaced) {
        this.ramList = ramList;
        this.indexInRam = indexInRam;
        this.indexInPageOrder = indexInPageOrder;
        this.isNeedReplaced = isNeedReplaced;
    }

    public AnimationStepItem(List<String> ramList, int indexInRam, int indexInPageOrder, int compareDistance, boolean isNeedReplaced) {
        this.ramList = ramList;
        this.indexInRam = indexInRam;
        this.indexInPageOrder = indexInPageOrder;
        this.compareDistance = compareDistance;
        this.isNeedReplaced = isNeedReplaced;
    }

    public List<String> getRamList() {
        return ramList;
    }

    public void setRamList(List<String> ramList) {
        this.ramList = ramList;
    }

    public int getIndexInRam() {
        return indexInRam;
    }

    public void setIndexInRam(int indexInRam) {
        this.indexInRam = indexInRam;
    }

    public int getIndexInPageOrder() {
        return indexInPageOrder;
    }

    public void setIndexInPageOrder(int indexInPageOrder) {
        this.indexInPageOrder = indexInPageOrder;
    }

    public int getCompareDistance() {
        return compareDistance;
    }

    public void setCompareDistance(int compareDistance) {
        this.compareDistance = compareDistance;
    }

    public boolean isNeedReplaced() {
        return isNeedReplaced;
    }

    public void setNeedReplaced(boolean needReplaced) {
        isNeedReplaced = needReplaced;
    }
}
