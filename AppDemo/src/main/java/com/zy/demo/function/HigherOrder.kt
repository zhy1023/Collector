package com.zy.demo.function

/**
 * @Author: zhy
 * @Date: 2023/5/29
 * @Desc: HigherOrder
 */
object HigherOrder {
    @JvmStatic
    fun main(args: Array<String>) {
        testLogin()
    }

    private fun testLogin() {
        loginEngin("zhy", "123") { success ->
            if (success) {
                println("login success!")
            } else {
                println("login failed!")
            }
        }
    }

    private fun loginEngin(userName: String, pwd: String, response: (Boolean) -> Unit) =
        response(userName == "zhy" && pwd == "123")
}