package com.lemondev.requestpagedstoragemanagementdemo.noticeview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * 2022/3/3
 * Created by vibrantBobo
 */

public class NoticeViewModel extends ViewModel {
    private MutableLiveData<List<String>> notices = new MutableLiveData<>();

    public MutableLiveData<List<String>> getNotices() {
        return notices;
    }
}
