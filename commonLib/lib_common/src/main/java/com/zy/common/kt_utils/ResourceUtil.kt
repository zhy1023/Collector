@file:JvmName("ResUtil")

package com.zy.common.kt_utils

import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.zy.common.app.GlobalApp

/**
 * @Author: zhy
 * @Date: 2023/4/24
 * @Desc 资源相关工具类 :获取字符、图片、颜色等资源
 */

/*** 获取颜色*/
@JvmName("getColor")
fun getResColor(@ColorRes colorRes: Int) = ContextCompat.getColor(GlobalApp.appContext, colorRes)

/*** 获取图片资源*/
@JvmName("getDrawable")
fun getResDrawable(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(GlobalApp.appContext, drawableRes)

/*** 获取字符资源*/
@JvmName("getString")
fun getResString(@StringRes stringId: Int, vararg formatArgs: Any) =
    GlobalApp.appContext.getString(stringId, *formatArgs)

/*** 获取String数组*/
@JvmName("getStringArray")
fun getResStringArray(@ArrayRes arrayId: Int): Array<String> =
    GlobalApp.appContext.resources.getStringArray(arrayId)

/*** 获取Int数组*/
@JvmName("getIntArray")
fun getResIntArray(@ArrayRes arrayId: Int) = GlobalApp.appContext.resources.getIntArray(arrayId)

/*** 获取Char数组*/
@JvmName("getTextArray")
fun getResTextArray(@ArrayRes arrayId: Int): Array<CharSequence> =
    GlobalApp.appContext.resources.getTextArray(arrayId)

/**
 * 获取dimens资源
 * 单位为px
 */
@JvmName("getDimenPx")
fun getResDimenPx(@DimenRes dimenRes: Int) =
    GlobalApp.appContext.resources.getDimensionPixelSize(dimenRes)