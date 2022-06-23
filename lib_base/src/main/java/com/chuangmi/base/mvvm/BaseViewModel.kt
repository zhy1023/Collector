package com.chuangmi.base.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.core.exception.CRException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @Author: zhy
 * @Date: 2022/2/18
 */
abstract class BaseViewModel : ViewModel(), IContract.BaseViewModelFun {

    /** 数据刷新livedata */
    protected var requestData = MutableLiveData<Any>()

    /** 错误弹框livedata */
    var toastError = MutableLiveData<Int>()

    /** 调试弹框livedata */
    var toastErrorByCode = MutableLiveData<Int>()

    /** 调试弹框livedata */
    var debugError = MutableLiveData<String>()

    /** 加载中弹框livedata */
    var isLoadingState = MutableLiveData<Boolean>()

    /** 弹框livedata */
    var toast = MutableLiveData<String>()

    override fun loadData() {
        requestData.postValue(requestData.value)
    }

    override fun toastError(id: Int) {
        toastError.postValue(id)
    }

    override fun toastErrorByCode(id: Int) {
        toastErrorByCode.value = id
    }

    override fun toastDebugError(e: Exception) {
        if (e is CRException) {
            debugError.postValue("code[${e.errCode}]:${e.message}")
        } else
            debugError.postValue(e.toString())
    }

    override fun setLoading(isLoading: Boolean) {
        isLoadingState.postValue(isLoading)
    }

    override fun toast(msg: String) {
        toast.postValue(msg)
    }

    override suspend fun <T> Observable<T>.await(): T {
        return suspendCoroutine { continuation ->
            subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: T) {
                    if (t != null) {
                        continuation.resume(t)
                    } else {
                        continuation.resumeWithException(RuntimeException())
                    }
                }

                override fun onError(e: Throwable) {
                    continuation.resumeWithException(CRException(e))
                }

                override fun onComplete() {
                }

            })
        }
    }


    companion object {
        /** 网络错误 */
        const val errorNetwork = 1
    }
}