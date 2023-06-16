package com.zy.demo.function

import kotlin.random.Random

/**
 * @Author: zhy
 * @Date: 2023/5/8
 * @Desc: FunctionScope
 */
object FunctionScope {

    @JvmStatic
    fun main(args: Array<String>) {
        takeXXTest()
//        takeIfTest()
//        letTest()
//        alsoTest()
//        applyTest()
        foo("1", "2", "hhhh")
    }

    private fun foo(vararg strings: String) {
        strings.forEach { println(it) }
    }

    /**
     * takeIf: 如果`takeIf`后面的表达式或闭包符合要求，则`takeIf`返回此对象；否则返回`null`。
     * takeUnless: 与`takeIf`用法相反，如果`takeUnless`不匹配后面的表达式或闭包，则返回该对象；否则返回`null`
     */
    private fun takeXXTest() {
        val num = Random.nextInt(100)
        println("num:$num")
        val evenOrNull = num.takeIf { it % 2 == 0 } //结果为偶数 或 null
        val oddOrNull = num.takeUnless { it % 2 == 0 } //结果为奇数 或 null
        println("evenOrNull：$evenOrNull, oddOrNull：$oddOrNull")
    }

    /*** takeIf结合let使用*/
    private fun takeIfTest() {
        val list = arrayListOf(1, 2, 3)
        list.takeIf { it.size < 4 }?.let {
            println("list$list")
        }
    }

    /**
     * `let`常用的两个作用：一是定义对象在特定的作用域范围内，
     * 另一个更常用的场景是对对象执行 `xxx ?.let{ }`，在 `lambda` 表达式中执行操作，以一种更优雅的形式来判空。
     */
    private fun letTest() {
        //let{}中可以使用it来代表intList对象
        val intList: List<Int> = arrayListOf(1, 2, 3)
        intList.let {
            println(it.size)
            println(it.lastIndex)
        }

        //通过?.let{}进行判断，如何为空，后面就不会再执行
        val list2: List<Int>? = null
        list2?.let {
            println(it.size)
        }
    }

    /**
     * 非扩展函数with(){}的使用，一般不需要用返回值，
     * 而是将上下文对象作为参数传递到Lambda表达式中，典型应用是with(RecyclerView.Holder){}
     */
    private fun withTest(person: Person = Person("xxx", 18, 1)) {
        with(person) {
            println(name)
            println(age)
            println(sex)
        }
    }

    /**
     * run和with的作用基本一致，run可以以扩展函数的方式调用，通用场景：当表达式中同时包含对象初始化及返回值计算时使用。
     */
    private fun runTest() {
        val list = arrayListOf(1, 2, 3)
        val size = list.run {
            list.add(4) //添加一条数据
            list.size  //返回最终的size
        }
        println("list.size:$size") //执行结果：list.size:4
    }

    /**
     * `also`通常执行将上下文对象作为参数的操作，返回值是上下文对象本身。
     * `lambda`表达式中用`it`来表示上下文对象，可以将`also`理解为**并且用该对象执行以下操作**
     */
    private fun alsoTest() {
        val list = arrayListOf(1, 2, 3)
        list.also {
            println("添加前：$list")
        }.add(4)
        println("添加后：$list")
    }
    /**
     * 执行结果：
     * 添加前：[1, 2, 3]
     * 添加后：[1, 2, 3, 4]
     */

    /**
     * `lambda`表达式中不返回值，且主要是对对象成员进行操作的场景使用`apply`。
     * 典型使用场景：对象的配置，可以将`apply`理解为**将以下赋值应用于对象**。
     */
    private fun applyTest() {
        val person = Person().apply {
            //给Person对象设置属性
            name = "xxx"
            age = 30
            sex = 1
        }
        println(person)
    }

}

data class Person(var name: String = "", var age: Int = 0, var sex: Int = 0)