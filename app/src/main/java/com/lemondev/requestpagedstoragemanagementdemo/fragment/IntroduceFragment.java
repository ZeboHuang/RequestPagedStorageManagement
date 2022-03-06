package com.lemondev.requestpagedstoragemanagementdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;
import com.lemondev.requestpagedstoragemanagementdemo.R;

/**
 * 2022/3/5
 * Created by vibrantBobo
 */

public class IntroduceFragment extends Fragment {

    private MaterialToolbar toolbar;

    MaterialTextView fifoView, optView, lruView;

    private String fifoString = "先进先出（First In first Out,FIFO) 页面置换算法的基本思想：每次置换最先调入内存的页面，即将内存中等待时间" +
            "最长的页面进行置换。此算法的适用范围是顺序结构程序。FIFO页面置换算法, 也就是先进先出的意思。这和我们现实生活中的排队方式很相似, 先进队" +
            "伍的人会先买到票, 然后先从队伍中离开。如果使用FIFO算法作为页面置换算法, 缓存空间大小是三个页面时, 一次进入Page1, Page2, Page3。当Pag" +
            "e4要进入缓存时, 操作系统将会把Page1清除出缓存, 将Page4加入至缓存中。如果再有Page5要进入缓存时, 操作系统会将Page2清除出缓存空间, 以此类推。";

    private String optString = "1966年，Belady提出最佳页面替换算法(OPTimal replacement，OPT)。是操作系统存储管理中的一种全局页面替" +
            "换策略 。当要调入一页而必须淘汰旧页时，应该淘汰以后不再访问的页，或距最长时间后要访问的页面。它所产生的缺页数最少，然而，却需" +
            "要预测程序的页面引用串，这是无法预知的，不可能对程序的运行过程做出精确的断言，不过此理论算法可用作衡量各种具体算法的标准。";

    private String lruString = "LRU是Least Recently Used的缩写，即最近最少使用，是一种常用的页面置换算法，选择最近最久未使用的页面予以淘汰。" +
            "该算法赋予每个页面一个访问字段，用来记录一个页面自上次被访问以来所经历的时间 t，当须淘汰一个页面时，选择现有页面中其 t 值最大的，即最近" +
            "最少使用的页面予以淘汰。";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_introduce, container, false);
        optView = view.findViewById(R.id.opt_text_view);
        fifoView = view.findViewById(R.id.fifo_text_view);
        lruView = view.findViewById(R.id.lru_text_view);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("页面置换算法模拟");


        optView.setText(optString);
        fifoView.setText(fifoString);
        lruView.setText(lruString);

        return view;
    }
}
