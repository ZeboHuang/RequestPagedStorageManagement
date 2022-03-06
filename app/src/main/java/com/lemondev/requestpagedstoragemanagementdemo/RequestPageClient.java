package com.lemondev.requestpagedstoragemanagementdemo;

import android.util.Log;

import com.lemondev.requestpagedstoragemanagementdemo.model.PageTable;
import com.lemondev.requestpagedstoragemanagementdemo.model.Ram;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.FIFO;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.LRU;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.OPT;
import com.lemondev.requestpagedstoragemanagementdemo.strategy.PageReplacement;

import java.util.List;

/**
 * 2022/3/3
 * Created by vibrantBobo
 */

public class RequestPageClient {
    private static final int DEFAULT_RAM_SIZE = 3;
    private static RequestPageClient instance;

    private List<Integer> pageList;

    private FIFO fifo;
    private OPT opt;
    private LRU lru;


    private RequestPageClient(int ramSize) {
        ram = new Ram(ramSize);
        pageTable = PageTable.getInstance();
    }

    private Ram ram;
    private PageTable pageTable;

    public static RequestPageClient getInstance(List<Integer> pageList) {
        if (instance == null) {
            synchronized (RequestPageClient.class) {
                if (instance == null) {
                    instance = new RequestPageClient(DEFAULT_RAM_SIZE);
                }
            }
        }
        instance.setPageList(pageList);
        return instance;
    }


    private void doAllPageReplacement() {
        opt = new OPT(ram, pageTable, pageList);
        fifo = new FIFO(ram, pageTable, pageList);
        lru = new LRU(ram, pageTable, pageList);
        opt.doReplacement();
        fifo.doReplacement();
        lru.doReplacement();
    }


    public void setRamSize(int ramSize) {
        ram.setRamSize(ramSize);
        doAllPageReplacement();
    }


    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
        doAllPageReplacement();
    }

    public List<String> getFIFOLog() {
        return fifo.getStringLog();
    }

    public List<String> getOPTLog() {
        return opt.getStringLog();
    }

    public List<String> getLRULog() {
        return lru.getStringLog();
    }


    public float[] getFIFOPoints(int times) {
        return getPoints(new FIFO(ram, pageTable, pageList), times);
    }

    public float[] getOPTPoints(int times) {
        return getPoints(new OPT(ram, pageTable, pageList), times);
    }

    public float[] getLRUPoints(int times) {
        return getPoints(new LRU(ram, pageTable, pageList), times);
    }

    private float[] getPoints(PageReplacement pageReplacement, int times) {
        float[] pts = new float[times * 2];
        for (int i = 0; i < times; i++) {
            pageReplacement.setRamSize(i + 1);
            pageReplacement.doReplacement();
            pts[i * 2] = i + 1;
            pts[i * 2 + 1] = pageReplacement.getMissingPagePercentage();
        }
        return pts;
    }

    public Ram getRam() {
        return ram;
    }


    public List<com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem> getFIFOAnimation() {
        return fifo.getAnimationSteps();
    }

    public List<com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem> getOPTAnimation() {
        Log.d("MTAG", "getOPTAnimation: steps: " + opt.getAnimationSteps().size());
        return opt.getAnimationSteps();
    }

    public List<com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem> getLRUAnimation() {
        return lru.getAnimationSteps();
    }

}
