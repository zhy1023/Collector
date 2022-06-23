package com.chuangmi.base.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread

/**
 * @Author: zhy
 * @Date: 2022/2/17
 */
interface IBaseContract {
    interface BaseActivityFun {
        /** 业务初始化 */
        fun init(savedInstanceState: Bundle?)

        /** toast弹框 */
        fun toastMsg(message: String)

        /** toast弹框 */
        fun toastMsg(id: Int)

        /** 通用进度框 */
        fun showProgressDialog()

        /** 通用进度框
         * @param message 进度框提示消息
         * */
        fun showProgressDialog(message: String)

        /** 关闭进度框 */
        fun dismissProgressDialog()

        /**
         * 确认提示框
         * @param message 消息
         * */
        fun showAlertDialog(message: String)

        /**
         * 自定义弹框
         *
         * @param layout 布局ID
         * @return root view
         */
        fun showCustomAlertDialog(@LayoutRes layout: Int): View

        /**
         * 文本输入弹框
         *
         * @param title 标题
         * @param message 信息
         * @param defaultInput 默认输入内容
         * @param block 文本输入回调
         * @receiver
         */
        fun showInputDialog(
            title: String,
            message: String,
            defaultInput: String,
            block: (content: String) -> Unit
        )

        /** 关闭提示框 */
        fun dismissAlertDialog()

        /** 打开软键盘 */
        fun showSoftInput(view: View)

        /** 隐藏然键盘 */
        fun hideSoftInput(view: View)

        /** 打开软键盘 */
        fun showSoftInput()

        /** 隐藏然键盘 */
        fun hideSoftInput()

        /** 切换软键盘状态 */
        fun toggleSoftInput()

        /** 判断软键盘状态 */
        fun isSoftInputVisible(): Boolean

        /** 监听软键盘状态 */
        fun setSoftInputListener(l: OnSoftKeyBoardChangeListener)

        /**
         * Check permissions
         *
         * @param permissions 权限列表
         * @param block 授权成功后回调
         * @receiver
         */
        fun checkPermissions(permissions: List<String>, block: () -> Unit)

        /** 设置全屏 隐藏title，沉浸模式
         * @param enable 开启状态
         * */
        fun setFullScreen(enable: Boolean)

    }

    interface BaseFragmentFun {
        /** UI实例化，数据直接加载也可以放到这里加载 */
        @UiThread
        fun initView(view: View, savedInstanceState: Bundle?)

        /** 数据懒加载 */
        @UiThread
        fun lazyLoad()

        /** 设置全屏 隐藏title，沉浸模式
         * @param enable 开启状态
         * */
        fun setFullScreen(enable: Boolean)
    }

    interface OnSoftKeyBoardChangeListener {
        /**
         * 显示软键盘回调
         *
         * @param height 软键盘高度
         */
        fun keyBoardShow(height: Int)

        /**
         * 关闭软键盘回调
         *
         * @param height 软键盘高度
         */
        fun keyBoardHide(height: Int)
    }
}