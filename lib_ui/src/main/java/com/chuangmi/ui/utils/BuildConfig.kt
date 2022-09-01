package com.chuangmi.ui.utils

import android.os.Build

/**
 * Android版本
 */
object BuildConfig {

    /**Android 6.0*/
    fun hasM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**Android 7.0*/
    fun hasN(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    /**Android 8.0*/
    fun hasOreo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    /**Android 10.0*/
    @JvmStatic
    fun hasQ(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }
}