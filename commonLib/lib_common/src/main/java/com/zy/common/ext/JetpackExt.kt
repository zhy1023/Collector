package com.zy.common.ext

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

/**
 * @Author: zhy
 * @Date: 2023/4/24
 * @Desc: JetpackExt jetpack相关扩展方法
 */

/**
 * 封装Flow.flowWithLifecycle，用于UI层单个Flow去订阅数据时使用
 */
inline fun <T> Flow<T>.flowWithLifecycle2(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.(T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    //前后台切换时可以重复订阅数据.如：Lifecycle.State是STARTED，那么在生命周期进入 STARTED 状态时开始任务，在 DESTROYED 状态时停止订阅
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { block(it) }
}

/**
 * MVI模式中使用
 */
inline fun <T, A> Flow<T>.flowWithLifecycle2(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    kProperty1: KProperty1<T, A>,
    crossinline block: suspend CoroutineScope.(A) -> Unit,
) = lifecycleOwner.lifecycleScope.launch {
    //前后台切换时可以重复订阅数据。如：Lifecycle.State是STARTED，那么在生命周期进入 STARTED 状态时开始任务，在 DESTROYED 状态时停止订阅
    flowOnSingleLifecycle(lifecycleOwner.lifecycle, minActiveState)
        .map { kProperty1.get(it) }
        .collect { block(it) }
}


/**
 * 效果类似于Channel，不过Channel是一对一的，而这里是一对多的
 * ps: 如果不想对UI层的Lifecycle.repeatOnLifecycle/Flow.flowWithLifecycle在前后台切换时重复订阅，可以使用此方法；
 */
fun <T> Flow<T>.flowOnSingleLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isFirstCollect: Boolean = true,
): Flow<T> = callbackFlow {
    var lastValue: T? = null
    lifecycle.repeatOnLifecycle(minActiveState) {
        this@flowOnSingleLifecycle.collect {
            if ((lastValue != null || isFirstCollect) && (lastValue != it)) {
                send(it)
            }
            lastValue = it
        }
    }
    lastValue = null
    close()
}