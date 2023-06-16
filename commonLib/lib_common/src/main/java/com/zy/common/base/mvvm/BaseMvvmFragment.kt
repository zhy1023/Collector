package com.zy.common.base.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.zy.common.base.BaseFragment
import com.zy.common.ext.toast
import kotlin.reflect.KClass

/**
 * @Author: zhy
 * @Date: 2023/3/8*/


abstract class BaseMvvmFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    layoutId: Int,
    viewModelClass: KClass<VM>
) :
    BaseFragment(layoutId) {
    lateinit var b: VB
    protected open val vm: VM by lazy { ViewModelProvider(this)[viewModelClass.java] }

    override fun customCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = DataBindingUtil.inflate(inflater, mLayoutId, container, false)
        b.lifecycleOwner = this
        bindModel()
        return b.root
    }

    abstract fun bindModel()

    override fun customObserve() {
        //错误toast监听
        vm.toastError.observe(this) { errorId ->
            when (errorId) {
//                BaseViewModel.errorNetwork -> baseActivity.showToast(R.string.imi_network_error)
            }
        }
        //Http error code 错误toast监听
        vm.toastErrorByCode.observe(this) { code ->
            baseActivity.toast(code)
        }
        //debug错误toast监听
        vm.debugError.observe(this) { message ->
            baseActivity.toast(message)
        }
        //进度条监听
        vm.loadingState.observe(this) { isLoading ->
            if (isLoading) {
                baseActivity.showProgressDialog(true)
            } else {
                baseActivity.showProgressDialog(false)
            }
        }
        //toast监听
        vm.toast.observe(this) { message ->
            baseActivity.toast(message)
        }
    }

    open fun onActionEvent() {
    }

    open fun onBackPress() {
    }

}
