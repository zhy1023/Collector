package com.zy.common.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @Author: zhy
 * @Date: 2023/3/9
 * @Desc: CoroutineException
 */
suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
        println("catch $throwable,threadName：${Thread.currentThread().name}")
    }

    val customScope =
        CoroutineScope(SupervisorJob() + CoroutineName("自定义协程") + Dispatchers.IO + exceptionHandler)

//    customScope.launch {
//        println("准备出错1 threadName:${Thread.currentThread().name}")
//        throw KotlinNullPointerException("============= 出错啦 1")
//    }.join()
//
//    customScope.launch {
//        println("准备出错1 threadName:${Thread.currentThread().name}")
//        throw KotlinNullPointerException("============= 出错啦 2")
//    }.join()
    val deferred = customScope.async {
        println("子协程1 start threadName:${Thread.currentThread().name}")
        delay(1000)
        throw KotlinNullPointerException("============= 出错啦 1")
        println("子协程1 end threadName:${Thread.currentThread().name}")
        "1234"
    }
    try {
        deferred.await()
    } catch (e: Exception) {
        println("抓到了异常${e.message}")
    }

}