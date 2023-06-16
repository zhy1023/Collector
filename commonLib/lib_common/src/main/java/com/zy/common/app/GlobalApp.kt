package com.zy.common.app

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zy.common.kt_utils.getResColor
import com.zy.common.kt_utils.getResDrawable
import com.zy.common.kt_utils.getResString

/**
 * @Author: zhy
 * @Date: 2023/4/24
 * @Desc: GlobeApp 全局App Context工具类
 */
object GlobalApp {
    lateinit var mApplication: Application
    val mHandler by lazy { Handler(Looper.getMainLooper()) }

    @JvmStatic
    fun init(application: Application) {
        mApplication = application
    }

    @JvmStatic
    val appContext: Context
        get() = mApplication.applicationContext

    @JvmStatic
    fun getString(@StringRes strId: Int): String = getResString(strId)

    @JvmStatic
    fun getColor(@ColorRes colorId: Int): Int = getResColor(colorId)

    @JvmStatic
    fun getDrawable(@DrawableRes drawableId: Int): Drawable? = getResDrawable(drawableId)
}