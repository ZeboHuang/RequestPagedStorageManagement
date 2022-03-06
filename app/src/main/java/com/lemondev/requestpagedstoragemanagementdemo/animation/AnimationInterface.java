package com.lemondev.requestpagedstoragemanagementdemo.animation;

/**
 * 2022/3/4
 * Created by vibrantBobo
 */

public interface AnimationInterface {
    /**
     * 展示第row行和第col列的网格动画
     */

    void showGridNonReplaceAnimation(int row1, int col1, int row2, int col2);

    /**
     * 置换动画
     *
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     */
    void showGridReplaceAnimation(int row1, int col1, int row2, int col2);


    /**
     * @param row1     当前快的行索引
     * @param col1     当前快的列索引
     * @param distance 与目标块的距离  >0   |  <0
     */
    void showGridCompareAnimation(int row1, int col1, int distance);
}
