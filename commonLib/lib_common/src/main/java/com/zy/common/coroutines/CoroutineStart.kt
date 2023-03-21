package com.zy.common.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @Author: zhy
 * @Date: 2023/3/7
 * @Desc: CoroutineStart
 */
fun main(): Unit = runBlocking {
//    launch(context = CoroutineName("CoroutineName1")) {
//        println("main: " + Thread.currentThread().name)
//        launch(start = CoroutineStart.LAZY) {
//            println("launch2: " + Thread.currentThread().name)
//            launch(context = CoroutineName("CoroutineName2")) {
//                println("launch3: " + Thread.currentThread().name)
//            }
//        }
//    }

    launch {
        println("main: " + Thread.currentThread().name)
    }
    val job = launch(start = CoroutineStart.LAZY) {
        println("launch2: " + Thread.currentThread().name)
    }

    launch {
        println("launch3: " + Thread.currentThread().name)
    }

    job.start()
}