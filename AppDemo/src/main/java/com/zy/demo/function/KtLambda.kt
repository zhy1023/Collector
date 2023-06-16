package com.zy.demo.function

/**
 * @Author: zhy
 * @Date: 2023/5/29
 * @Desc: KtLambda
 */
fun main() {
//    println(method02(2, 6))
    testFold()
    println("sum:${sum(1, 2)}")
}

var method01 = { num1: Int, num2: Int -> num1.toString() + num2.toString() }

var method02: (Int, Int) -> String = fun(num1, num2) = num1.toString() + num2.toString()

val sum = { num1: Int, num2: Int -> num1 + num2 }

fun testFold() {
    listOf(1, 2, 3, 4, 5).fold2(0) { acc, i ->
        println("acc:$acc,i:$i")
        val result = (acc + i) * i
        println("result:$result")
        result
    }
}

fun <T, R> Collection<T>.fold2(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

inline fun <R> run2(block: () -> R): R {
    return block()
}

inline fun <T, R> T.run2(block: T.() -> R): R {
    return block()
}

inline fun <T, R> with2(receiver: T, block: T.() -> R): R {
    return receiver.block()
}

inline fun <T> T.apply2(block: T.() -> Unit): T {
    block()
    return this
}

inline fun <T> T.also2(block: (T) -> Unit): T {
    block(this)
    return this
}

inline fun <T, R> T.let2(block: (T) -> R): R = block(this)

inline fun <T> T.takeIf2(predicate: (T) -> Boolean): T? {
    return if (predicate(this)) this else null
}

inline fun <T> T.takeUnless(predicate: (T) -> Boolean): T? {
    return if (!predicate(this)) this else null
}

inline fun repeat2(times: Int, action: (Int) -> Unit) {
    for (index in 0..times) {
        action(index)
    }
}