package com.lemondev.requestpagedstoragemanagementdemo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2022/3/2
 * Created by vibrantBobo
 */

public class PageTable {
    public static final int WONT_APPEAR_AGAIN = -1;

    private static PageTable instance = null;

    private  Map<Integer, Page> pages;

    private PageTable() {
        this.pages = new HashMap<>();
    }

    public static PageTable getInstance() {
        if (instance == null) {
            synchronized (PageTable.class) {
                if (instance == null) {
                    instance = new PageTable();
                }
            }
        }
        return instance;
    }

    public Page getPage(Integer pageNumber) {
        if (!pages.containsKey(pageNumber)) pages.put(pageNumber, new Page(pageNumber));
        return pages.get(pageNumber);
    }


}
