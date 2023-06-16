package com.zy.common.base

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Message

/**
 * @Desc: View契约
 * @Author: zhangyuan
 * @Date: 2021/12/23
 */
interface IContract {
    interface IBaseActivityFun : IBaseView {
        /**
         * 加载布局之前事件处理
         */
        fun initActivityNeed()

        /**
         * 处理Intent
         */
        fun handleIntent(intent: Intent?)

        /**
         * 通用进度框
         *
         * @param message 进度框提示消息
         */
        fun showProgressDialog(message: String?)

        /**
         * 通用进度框
         *
         * @param message 进度框提示消息
         */
        fun showProgressDialog(message: Int)

        /**
         * 通用进度框
         */
        fun showProgressDialog(isShow: Boolean)

        /**
         * 公共对话框（ps:取消、确定）
         */
        fun showCommonDialog(message: String?, callback: DialogInterface.OnClickListener)

        /**
         * 处理handleMsg
         *
         * @param msg
         */
        fun handleMessage(msg: Message?)
    }

    interface IBaseFragmentFun : IBaseView {
        /**
         * 加载布局之前事件处理
         */
        fun initFragmentNeed()

        /**
         * 处理Bundle
         */
        fun handleBundle(bundle: Bundle?)
    }

    interface IBaseView {
        fun initContentView()

        /**
         * 初始化View空间
         */
        fun initView()

        /**
         * 业务数据初始化
         */
        fun initData()

        /**
         * 监听初始化
         */
        fun initListener()
    }
}