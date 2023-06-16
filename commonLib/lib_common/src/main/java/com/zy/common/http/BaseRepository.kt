package com.zy.common.http

/**
 * @Author: zhy
 * @Date: 2023/5/4
 * @Desc: BaseRepository
 */
open class BaseRepository {
    suspend fun <T : Any> executeRequest(
        block: suspend () -> BaseData<T>
    ): BaseData<T> {
        val result = block()
        if (result.data == null) {
            result.state = ReqState.SUCCESS
        } else {
            result.state = ReqState.ERROR
        }
        return result
    }
}