package com.zy.common.base.mvvm

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.zy.common.base.BaseActivity
import com.zy.common.ext.toast
import kotlin.reflect.KClass

/**
 * MVVM架构:ViewModel+DataBinding
 * @Author: zhy
 * @Date: 2023/3/8
 */

abstract class BaseMvvmActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    layoutId: Int,
    viewModelClass: KClass<VM>
) : BaseActivity(layoutId) {

    protected val b: VB by lazy { DataBindingUtil.setContentView(this, layoutId) }
    protected open val vm: VM by lazy { ViewModelProvider(this)[viewModelClass.java] }

    /*constructor(layoutResID: Int, titleType: TitleType, viewModelClass: KClass<K>) : this(
        layoutResID,
        viewModelClass
    ) {
        this.titleType = titleType
        this.layoutId = layoutResID
    }*/

    override fun initContentView() {
        bindModel()
        b.lifecycleOwner = this
        observe()
    }

    private fun observe() {
        //错误toast监听
        vm.toastError.observe(this) { errorId ->
            when (errorId) {
//                BaseViewModel.errorNetwork -> showToast(R.string.imi_network_error)
            }
        }
        //Http error code 错误toast监听
        vm.toastErrorByCode.observe(this) { code ->
            toast(code)
        }
        //debug错误toast监听
        vm.debugError.observe(this) { message ->
            toast(message)
        }
        //进度条监听
        vm.loadingState.observe(this) { isLoading ->
            if (isLoading) {
                showProgressDialog(true)
            } else {
                showProgressDialog(false)
            }
        }
        //toast监听
        vm.toast.observe(this) { message ->
            toast(message)
        }
    }

    abstract fun bindModel()
}
