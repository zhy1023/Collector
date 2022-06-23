package com.zy.core.application

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication

/**
 * @Author: zhy
 * @Date: 2022/2/18
 * @Desc: BaseApp
 */
open class BaseApp : MultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initSDK()
    }

    /**
     * 初始化SDK
     */
    open fun initSDK() {}


}