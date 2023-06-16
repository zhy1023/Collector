package com.zy.common.kt_utils

import android.view.View
import android.view.ViewGroup.MarginLayoutParams


/**
 * @Author: zhy
 * @Date: 2023/5/24
 * @Desc: UIUtil
 */
object UIUtil {

    /*** 设置View margin*/
    fun setMargin(
        view: View,
        leftMargin: Int = 0,
        topMargin: Int = 0,
        rightMargin: Int = 0,
        bottomMargin: Int = 0
    ) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            view.requestLayout()
        }
    }
}