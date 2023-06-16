package com.zy.common.http

/**
 * @Author: zhy
 * @Date: 2023/5/4
 * @Desc: BaseData NetKit请求结果分装类
 */
class BaseData<T> {
    var data: T? = null
    var msg: String? = null
    var code: Int = -1
    var e: Exception? = null
    var state: ReqState = ReqState.ERROR
}

enum class ReqState {
    SUCCESS, ERROR
}