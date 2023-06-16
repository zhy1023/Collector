package com.zy.common.app

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * @Author: zhy
 * @Date: 2023/5/25
 * @Desc: GlobalCoroutineExceptionHandler  协程全局异常捕获
 */
const val TAG = "IMIGlobalException"

class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.e(TAG, "caught exception->coroutineContext:$context, exception:$exception")
    }
}