package com.lemondev.requestpagedstoragemanagementdemo.model;

import java.util.List;

/**
 * 2022/3/1
 * Created by vibrantBobo
 */

public class Page {
    private final int number;    //页号
    private boolean inRam; //状态位
    //    private boolean isEdit; //修改位
    private int lastVisited;    //距离上次被访问的时间间隔
    private int nextVisited;    //距离下次被访问的时间间隔

    /**
     * private List<Object> data;  //数据域
     * <p>
     * private int size;   //页大小
     */


    public Page(int number) {
        this.number = number;
        inRam = false;
        lastVisited = 0;
        nextVisited = 0;
    }

    public int getNumber() {
        return number;
    }

    public boolean isInRam() {
        return inRam;
    }

    public void setInRam(boolean inRam) {
        this.inRam = inRam;
    }

    public int getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(int lastVisited) {
        this.lastVisited = lastVisited;
    }

    public int getNextVisited() {
        return nextVisited;
    }

    public void setNextVisited(int nextVisited) {
        this.nextVisited = nextVisited;
    }
}
