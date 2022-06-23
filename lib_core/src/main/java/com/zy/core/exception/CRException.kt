package com.zy.core.exception

/**
 * @Author: zhy
 * @Date: 2022/6/23
 * @Desc: CRException
 */
data class CRException(var errCode: Int, var errMsg: String) : Exception() {
    constructor(e: Exception) : this(-1, e.message.toString())
    constructor(e: Throwable) : this(-1, e.message.toString())
}
