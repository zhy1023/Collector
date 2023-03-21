package com.zy.common.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @Author: zhy
 * @Date: 2023/3/7
 * @Desc: Dispatcher
 */
fun main(): Unit = runBlocking<Unit> {
//    println("main")
//    launch(Dispatchers.IO) {
//        println("launch")
//    }

    launch(context = CoroutineName("CoroutineName1")) {
        println("main: " + Thread.currentThread().name)
        launch (Dispatchers.IO){
            println("launch2: " + Thread.currentThread().name)
            launch(context = CoroutineName("CoroutineName2")) {
                println("launch3: " + Thread.currentThread().name)
            }
        }
    }
}
