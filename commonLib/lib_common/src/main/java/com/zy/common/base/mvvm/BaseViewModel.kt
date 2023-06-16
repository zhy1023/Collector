package com.zy.common.base.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.common.exception.IMIException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @Author: zhy
 * @Date: 2023/3/8
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
    var loadingState = MutableLiveData<Boolean>()

    /** 弹框livedata */
    var toast = MutableLiveData<String>()

    override fun loadData() = requestData.postValue(requestData.value)

    override fun toastError(id: Int) = toastError.postValue(id)

    override fun toastErrorByCode(id: Int) {
        toastErrorByCode.value = id
    }

    override fun toastDebugError(e: Exception) {
        if (e is IMIException) {
            debugError.postValue("code[${e.errCode}]:${e.message}")
        } else
            debugError.postValue(e.toString())
    }

    override fun setLoading(isLoading: Boolean) = loadingState.postValue(isLoading)

    override fun toast(msg: String) = toast.postValue(msg)

    override suspend fun <T : Any> Observable<T>.await(): T {
        return suspendCoroutine { continuation ->
            subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(@NonNull t: T) {
                    continuation.resume(t)
                }

                override fun onError(e: Throwable) {
                    continuation.resumeWithException(IMIException(e))
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