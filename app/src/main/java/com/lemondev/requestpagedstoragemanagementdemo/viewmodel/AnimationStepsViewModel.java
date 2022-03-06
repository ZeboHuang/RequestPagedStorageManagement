package com.lemondev.requestpagedstoragemanagementdemo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lemondev.requestpagedstoragemanagementdemo.animation.AnimationStepItem;

import java.util.List;

/**
 * 2022/3/4
 * Created by vibrantBobo
 */

public class AnimationStepsViewModel extends ViewModel {
    private MutableLiveData<List<AnimationStepItem>> animations = new MutableLiveData<>();


    public MutableLiveData<List<AnimationStepItem>> getAnimations() {
        return animations;
    }
}
