package com.zy.common.base.mvvm

import io.reactivex.Observable

/**
 * @Author: zhy
 * @Date: 2023/3/8
 */
interface IContract {

    interface BaseViewModelFun {
        /** 加载数据 */
        fun loadData()

        /** 加载数据 */
        fun setLoading(isLoading: Boolean)

        /** toast错误 */
        fun toastError(id: Int)

        /** toast错误 */
        fun toastErrorByCode(id: Int)

        /** toast调试错误 */
        fun toastDebugError(e: Exception)

        /** toast */
        fun toast(msg: String)

        /**
         * Await RX 转suspend
         *
         * @param T
         * @return
         */
        suspend fun <T : Any> Observable<T>.await(): T
    }
}