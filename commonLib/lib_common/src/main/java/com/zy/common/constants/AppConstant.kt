package com.zy.common.constants

/**
 * @Author: zhy
 * @Date: 2022/9/1
 * @Desc: AppConstant 常量类
 */
object AppConstant {
    const val CODE = "code"
    const val MSG = "msg"
    const val RESULT = "result"
    const val MESSAGE = "message"

    enum class MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    enum class TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }
}