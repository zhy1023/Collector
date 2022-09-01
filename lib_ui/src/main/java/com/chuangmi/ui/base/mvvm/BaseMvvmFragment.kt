package com.chuangmi.ui.base.mvvm

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.chuangmi.ui.R
import com.chuangmi.ui.base.BaseFragment
import kotlin.reflect.KClass

/**
 * @Author: zhy
 * @Date: 2022/2/18*/


abstract class BaseMvvmFragment<T : ViewDataBinding,
        K : BaseViewModel>(layoutResID: Int, viewModelClass: KClass<K>) :
    BaseFragment(layoutResID) {
    lateinit var b: T
    protected open val vm: K by lazy { ViewModelProvider(this).get(viewModelClass.java) }

    override fun customCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = DataBindingUtil.inflate(inflater, layoutResID, container, false)
//        b.lifecycleOwner = this
        bindModel()
        return b.root
    }

    abstract fun bindModel()

    override fun customObserve() {
        //错误toast监听
        vm.toastError.observe(this) { errorId ->
            when (errorId) {
                BaseViewModel.errorNetwork -> baseActivity.toastMsg(R.string.no_network)
            }
        }
        //Http error code 错误toast监听
        vm.toastErrorByCode.observe(this) { code ->
            baseActivity.toastMsg(code)
        }
        //debug错误toast监听
        vm.debugError.observe(this) { message ->
            baseActivity.toastMsg(message)
        }
        //进度条监听
        vm.isLoadingState.observe(this) { isLoading ->
            if (isLoading) {
                baseActivity.showProgressDialog()
            } else {
                baseActivity.dismissProgressDialog()
            }
        }
        //toast监听
        vm.toast.observe(this) { message ->
            baseActivity.toastMsg(message)
        }
    }

    open fun onActionEvent() {
    }

    open fun onBackPress() {
    }

}
