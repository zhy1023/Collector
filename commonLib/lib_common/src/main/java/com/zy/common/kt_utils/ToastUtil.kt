@file:JvmName("ToastUtil")

package com.zy.common.kt_utils

import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.zy.common.app.GlobalApp

/**
 * @Author: zhy
 * @Date: 2023/4/24
 * @Desc: ToastUtil Toast工具类
 */
private var toast: Toast? = null
private var firstShowTime = 0L
private var nextShowTime = 0L
private var toastTextSize = 14

private fun showToast(
    message: CharSequence,
    duration: Int,
    textSize: Int = toastTextSize
): Toast {
    fun durationTime() = if (duration == Toast.LENGTH_SHORT) 2000L else 3500L
    return when (toast == null) {
        true -> {
            firstShowTime = System.currentTimeMillis()
            toast = Toast.makeText(GlobalApp.appContext, message, duration)
            toast!!.apply {
                setText(message)
                view?.findViewById<TextView>(android.R.id.message)?.textSize = textSize.toFloat()
            }
        }
        false -> toast!!.apply {
            nextShowTime = System.currentTimeMillis()
            setText(message)
            setDuration(duration)
        }
    }.apply {
        if (nextShowTime == 0L || nextShowTime - firstShowTime > durationTime()) {
            GlobalApp.mHandler.post { show() }
            GlobalApp.mHandler.postDelayed({
                cancelToast()
            }, durationTime())
        }
    }
}

private fun showToast(
    @StringRes messageId: Int,
    duration: Int
): Toast {
    return showToast(GlobalApp.getString(messageId), duration)
}

/**
 * 取消Toast，要将toast、firstShowTime和nextShowTime都初始化
 */
private fun cancelToast() {
    toast?.cancel()
    toast = null
    firstShowTime = 0L
    nextShowTime = 0L
}

fun shortToast(message: CharSequence) = showToast(message, Toast.LENGTH_SHORT)

fun shortToast(@StringRes messageId: Int) = showToast(messageId, Toast.LENGTH_SHORT)

fun longToast(message: CharSequence) = showToast(message, Toast.LENGTH_LONG)

fun longToast(@StringRes messageId: Int) = showToast(messageId, Toast.LENGTH_LONG)