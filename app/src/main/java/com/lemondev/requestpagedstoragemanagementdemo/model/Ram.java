package com.lemondev.requestpagedstoragemanagementdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/2
 * Created by vibrantBobo
 */

public class Ram {
    private List<Page> ramBlocks;

    private int ramSize;

    public Ram(int ramSize) {
        this.ramBlocks = new ArrayList<>();
        this.ramSize = ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }


    public void clear() {
        ramBlocks.clear();
    }

    public List<String> getRamList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ramBlocks.size(); i++) {
            list.add(String.valueOf(ramBlocks.get(i).getNumber()));
        }
        return list;
    }

    /**
     * 获取 FIFO 的头指针
     *
     * @return
     */


    /**
     * 访问字段最大说明，最久没被访问过
     *
     * @return
     */
    public int getLastReplacedIndex() {
        int lastReplacedIndex = 0;

        if (ramBlocks.size() < ramSize) return ramBlocks.size() - 1;

        //内存已满
        for (int i = 0; i < ramBlocks.size(); i++) {
            if (ramBlocks.get(i).getLastVisited() > ramBlocks.get(lastReplacedIndex).getLastVisited()) {
                lastReplacedIndex = i;
            }
        }
        return lastReplacedIndex;
    }

    /**
     * 访问字段最大说明，最远最久的将来才会被访问到
     * 访问字段为-1，说明之后都不会被访问到
     *
     * @return
     */
    public int getNextReplacedIndex() {
        int nextReplacedIndex = 0;
        for (int i = 0; i < ramBlocks.size(); i++) {
            if (ramBlocks.get(i).getNextVisited() == PageTable.WONT_APPEAR_AGAIN) {
                nextReplacedIndex = i;
                break;
            }
            if (ramBlocks.get(i).getNextVisited() > ramBlocks.get(nextReplacedIndex).getNextVisited()) {
                nextReplacedIndex = i;
            }
        }
        return nextReplacedIndex;
    }

    public boolean isFull() {
        return ramBlocks.size() == ramSize;
    }

    /**
     * ram中是否含有该page
     *
     * @param page 比较页
     * @return 含有返回true, 否则返回false
     */
    public boolean contains(Page page) {
//        for (Page p :
//                ram) {
//            if (p.getPageNumber() == page.getPageNumber()) return true;
//        }

        //使用享元模式，内存中只有一份，所以可以比较内存地址
        return ramBlocks.contains(page);
    }

    public int indexOf(Page p) {
//        for (int i = 0; i < ram.size(); i++) {
//            if (ram.get(i).getPageNumber() == p.getPageNumber()) return i;
//        }
//        return -1;

        return ramBlocks.indexOf(p);
    }

    public Page get(int i) {
        return ramBlocks.get(i);
    }

    public void add(Page p) {
        if (ramBlocks.size() < ramSize) {

            ramBlocks.add(p);
            //FIFO 添加进队列, LRU, OPT 没有使用，不影响
        }
    }

//    public int poll() {
////        if (ramBlocks.size() > 0) {
////            Page tmp = ramBlocks.get(0);
////            ramBlocks.remove(0);
////            return tmp;
////        }
//
//        headIndex = (headIndex + 1 + ramSize) % ramSize;
//
//        return null;
//    }

    public Page replace(int index, Page p) {
        if (index < ramBlocks.size()) {
            Page tmp = ramBlocks.get(index);


            ramBlocks.set(index, p);

            return tmp;
        }
        return null;
    }

    public List<Page> getRamBlocks() {
        return ramBlocks;
    }

    public void setRamBlocks(List<Page> ramBlocks) {
        this.ramBlocks = ramBlocks;
    }

    public int getRamSize() {
        return ramSize;
    }

}
