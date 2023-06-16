package com.zy.common.ext

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.zy.common.kt_utils.longToast
import com.zy.common.kt_utils.shortToast
import java.io.File

private const val UNKNOWN = "unKnown"
private const val DEFAULT_VALUE = -1
private const val TAG = "IMI_Log"

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc Context 扩展函数
 */
fun Context.toast(message: String) = shortToast(message)

fun Context.toast(@StringRes messageId: Int) = shortToast(messageId)

fun Context.toastLong(message: String) = longToast(message)

fun Context.toastLong(@StringRes messageId: Int) = longToast(messageId)

/**
 * Fragment中Toast扩展函数
 */
fun Fragment.toast(message: String) = shortToast(message)

fun Context.log(tag: String = TAG, message: String) = Log.d(tag, message)

fun Context.logI(tag: String = TAG, message: String) = Log.i(tag, message)

fun Context.logE(tag: String = TAG, message: String) = Log.e(tag, message)

fun Fragment.log(tag: String = TAG, message: String) = Log.d(tag, message)

fun Fragment.logI(tag: String = TAG, message: String) = Log.i(tag, message)

fun Fragment.logE(tag: String = TAG, message: String) = Log.e(tag, message)

/**
 * 获取应用版本名称，默认为本应用
 * @return 失败时返回unKnown
 */
fun Context.getAppVersionName(packageName: String = this.packageName): String {
    return try {
        if (packageName.isBlank()) {
            return UNKNOWN
        } else {
            val pi = packageManager.getPackageInfo(packageName, 0)
            pi?.versionName ?: UNKNOWN
        }
    } catch (e: PackageManager.NameNotFoundException) {
        UNKNOWN
    }
}

/**
 * 获取应用版本号，默认为本应用
 * @return 失败时返回-1
 */
fun Context.getAppVersionCode(packageName: String = this.packageName): Int {
    return try {
        if (packageName.isBlank()) {
            DEFAULT_VALUE
        } else {
            val pi = packageManager.getPackageInfo(packageName, 0)
            pi?.versionCode ?: DEFAULT_VALUE
        }
    } catch (e: PackageManager.NameNotFoundException) {
        DEFAULT_VALUE
    }
}

/**
 * 获取应用大小，单位为b，默认为本应用
 * @return 失败时返回-1
 */
fun Context.getAppSize(packageName: String = this.packageName): Long {
    return try {
        if (packageName.isBlank()) {
            DEFAULT_VALUE.toLong()
        } else {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            File(applicationInfo.sourceDir).length()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        DEFAULT_VALUE.toLong()
    }
}

/**
 * 获取应用图标，默认为本应用
 * @return 失败时返回null
 */
fun Context.getAppIcon(packageName: String = this.packageName): Drawable? {
    return try {
        if (packageName.isBlank()) {
            null
        } else {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            applicationInfo.loadIcon(packageManager)
        }
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}




