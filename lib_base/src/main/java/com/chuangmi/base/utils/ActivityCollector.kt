package com.chuangmi.base.utils

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * 管理应用程序中所有Activity。
 *
 * @Author: zhy
 * @Date: 2022/2/17
 * @Desc: ActivityCollector :Activity栈管理
 */
object ActivityCollector {

    private val activities = Stack<WeakReference<Activity>>()

    @Volatile
    private var currentActivity: WeakReference<Activity>? = null

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    fun pushTask(task: WeakReference<Activity>?) {
        activities.push(task)
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    fun removeTask(task: WeakReference<Activity>?) {
        activities.remove(task)
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    fun removeTask(taskIndex: Int) {
        if (activities.size > taskIndex) activities.removeAt(taskIndex)
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    fun removeToTop() {
        val end = activities.size
        val start = 1
        for (i in end - 1 downTo start) {
            val activity = activities[i].get()
            if (null != activity && !activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    fun removeAll() {
        for (task in activities) {
            val activity = task.get()
            if (null != activity && !activity.isFinishing) {
                activity.finish()
            }
        }
    }

    fun setCurrentActivity(task: WeakReference<Activity>?) {
        currentActivity = task
    }

    fun getCurrentActivity(): Activity? {
        return currentActivity?.get()
    }

    fun cleanLoginActivityInList() {
        val end = activities.size
        val start = 0
        for (i in end - 1 downTo start) {
            val activity = activities[i].get()
            if (null != activity && !activity.isFinishing
                && activity.localClassName.contains("LoginActivity")
            ) {
                activity.finish()
            }
        }
    }

    fun cleanActivityExceptLogin() {
        val end = activities.size
        val start = 0
        for (i in end - 1 downTo start) {
            val activity = activities[i].get()
            if (null != activity && !activity.localClassName.contains("LoginActivity")) {
                activity.finish()
            }
        }
    }

    fun isActivityInStack(containName: String): Boolean {
        val end = activities.size
        val start = 0
        for (i in end - 1 downTo start) {
            val activity = activities[i].get()
            if (null != activity && activity.localClassName.contains(containName)) {
                return true
            }
        }
        return false
    }
}
