package com.zy.common.widget

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc: IStatusView
 */
interface IStatusView {
    /*** 正常视图*/
    fun showMainView()

    /*** 空视图*/
    fun showEmptyView()

    /*** 数据错误视图*/
    fun showErrorView(errMsg: String)

    /*** 展示Loading视图*/
//    fun showLoadingView(isShow: Boolean)
}