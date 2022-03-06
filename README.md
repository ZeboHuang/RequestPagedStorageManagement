## 请求页式管理

OPT: 理想型置换算法，缺页率最低，向后比较，缺页时，置换掉最长时间不被访问的页面； 页面需要“最长时间不被访问字段”，初始化时计算出该字段。

FIFO:先进先出置换算法，可能产生belady异常。内存中的页面为队列式结构。 内存区 维护一个队列结构

LRU: 最近最久未使用算法。 页面维护一个访问字段，记录该页上次被访问的时间（记录页面间隔）

共同操作 1.内存不满，该页直接调入。 2.内存中含有该页，不操作 3.内存满，与 合适页 置换。 记录缺页次数

内存工作区： 维护 物理块数，

## 动画参考

    https://blog.csdn.net/weiqiang_1989/article/details/86525285 

    https://github.com/ukhanoff/AndroidSortAnimation/blob/develop/app/src/main/java/com/ukhanoff/bubblesort/fragments/SortingFragment.java

#

2022.3.5

    记录ram history list 记录每个场景下比较的两点---- 页面序列中 和 内存区 中。 某些特殊的点:OPT LRU 需要个特殊字段判断，是否进行前后比较

    需解决: 动画怎么一步步演示，而不是一下子就出来
           演示时需要将上一步的结果复制给下一步，借助上一部动画的ram history
            复制完后，在进行比较或置换动画

        今天下午再做

            
