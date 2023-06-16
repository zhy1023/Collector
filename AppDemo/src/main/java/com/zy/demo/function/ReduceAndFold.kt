package com.zy.demo.function

/**
 * @Author: zhy
 * @Date: 2023/5/8
 * @Desc: ReduceAndFold 高阶函数：高阶函数reduce()、fold()详解
 */
object ReduceAndFold {

    @JvmStatic
    fun main(args: Array<String>) {
//        reduceAdd()
        reduceAppend()
//        foldAdd()
//        foldAppend()
    }

    /**
     * 执行结果：
     * acc:1, i:2
     * acc:3, i:3
     * acc:6, i:4
     * acc:10, i:5
     * sum is 15
     */
    private fun reduceAdd() {
        val list = listOf(1, 2, 3, 4, 5)
        val sum = list.reduce { acc, i ->
            println("acc:$acc, i:$i")
            acc + i
        }
        println("sum is $sum")
    }

    /**
     * result---> apple, banana, orange, pear
     */
    private fun reduceAppend() {
        val strings = listOf("apple", "banana", "orange", "pear")
        val result = strings.reduce { acc, s -> "$acc, $s" }
        println(result)
    }

    /**
     * 执行结果：
     * acc:10, i:1
     * acc:11, i:2
     * acc:13, i:3
     * acc:16, i:4
     * acc:20, i:5
     * sum is 25
     */
    private fun foldAdd() {
        val numbers = listOf(1, 2, 3, 4, 5)
        val sum = numbers.fold(10) { acc, i -> acc + i }
        println(sum)
    }

    /**
     * result---> Fruits: apple banana orange pear
     */
    private fun foldAppend() {
        val strings = listOf("apple", "banana", "orange", "pear")
        val result = strings.fold("Fruits:") { acc, s -> "$acc $s" }
        println(result)
    }
}