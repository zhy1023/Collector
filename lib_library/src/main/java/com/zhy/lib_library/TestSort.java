package com.zhy.lib_library;

import com.zhy.lib_library.arithmetic.SortAlgorithm;

/**
 * @Author: zhy
 * @Date: 2023/3/23
 * @Desc: TestSort
 */
public class TestSort {

    public static void main(String[] args) {
        int[] arrays = new int[]{20, 14, 3, 18, 9, 22, 5};
//        SortUtils.sortByBubble(arrays);
//        SortUtils.sortBySelect(arrays);
//        SortUtils.sortByHeap(arrays);
//        SortUtils.sortByQuick(arrays);
        SortAlgorithm.sortByMerge(arrays);
//        SortUtils.sortByInsert(arrays);
        for (int i : arrays) {
            System.out.println("i :" + i);
        }
    }
}