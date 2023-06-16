package com.zy.common.base.mvi

import androidx.annotation.Keep

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc: MviInterface UI状态管理封装类
 */

@Keep
/*** 重复性事件 可以多次消费*/
interface IUiState

@Keep
/*** 一次性事件，不支持多次消费*/
interface ISingleUiState

/**
 *  UI 状态密封类 确保所有可能的状态都在同一文件中定义，而且所有状态都是不可变的
 *  一次性事件，不支持多次消费
 */
sealed class LoadUiState {
    data class Loading(var isShow: Boolean) : LoadUiState()

    object ShowMainView : LoadUiState()

    data class Error(val msg: String) : LoadUiState()
}