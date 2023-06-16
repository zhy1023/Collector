package com.zy.common.ext

import android.app.Activity
import android.app.Dialog
import android.graphics.Bitmap
import android.view.View
import com.zy.common.base.IRootView
import com.zy.common.kt_utils.viewToBitmap
import java.util.concurrent.TimeUnit

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc: View扩展方法
 */
fun <T : View> Activity.bind(id: Int): T {
    return this.findViewById(id) as T
}

fun <T : View> View.bind(id: Int): T {
    return this.findViewById(id) as T
}

fun <T : View> Dialog.bind(id: Int): T {
    return this.findViewById(id) as T
}


fun <T : View> Activity.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> View.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> Dialog.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> IRootView.id(id: Int) = lazy {
    this.rootView().findViewById<T>(id)
}

/**
 * 当前View是否可见
 */
val View.isVisible
    get() = visibility == View.VISIBLE

/**
 * 当前View是否不可见
 */
val View.isInvisible
    get() = visibility == View.INVISIBLE

/**
 * 当前View是否隐藏
 */
val View.isGone
    get() = visibility == View.GONE

/**
 * 将View设置为隐藏
 */
fun View.setGone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/**
 * 将View设置为可见
 */
fun View.setVisible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

/**
 * 将View设置为不可见
 */
fun View.setInvisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

/**
 * 设置View的宽度
 * @param width: 宽度值，单位为px
 */
fun View.setWidth(width: Int) {
    layoutParams = layoutParams.apply {
        this.width = width
    }
}

/**
 * 设置View的高度
 * @param height: 高度值，单位为px
 */
fun View.setHeight(height: Int) {
    layoutParams = layoutParams.apply {
        this.height = height
    }
}

/**
 * 设置View的宽度和高度
 * @param width: 宽度值，单位为px
 * @param height: 高度值，单位为px
 */
fun View.setWidthAndHeight(width: Int, height: Int) {
    layoutParams = layoutParams.apply {
        this.width = width
        this.height = height
    }
}

/**
 * 设置View的padding
 */
fun View.setPaddingEx(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) =
    setPadding(left, top, right, bottom)

/**
 * 将View转换为Bitmap
 * @param  scale: 生成的Bitmap相对于原View的大小比例，范围为0~1.0
 */
fun View.toBitmap(scale: Float = 1.0F): Bitmap? = viewToBitmap(this, scale)


const val clickEffectiveTime = 500L

/*** 防止暴力点击*/
fun View.setOnThrottleClick(
    interval: Long = clickEffectiveTime,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
    block: View.() -> Unit
) {
    setOnClickListener(ThrottleClickListener(interval, unit, block))
}

class ThrottleClickListener(
    private val interval: Long = clickEffectiveTime,
    private val unit: TimeUnit = TimeUnit.MILLISECONDS,
    private var block: View.() -> Unit
) : View.OnClickListener {
    private var lastTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > unit.toMillis(interval)) {
            lastTime = currentTime
            block(v)
        }
    }
}
