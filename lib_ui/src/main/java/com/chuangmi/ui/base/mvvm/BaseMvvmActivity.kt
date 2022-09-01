/*
package com.chuangmi.base.mvvm

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.chuangmi.base.R
import com.chuangmi.base.ui.BaseActivity
import kotlin.reflect.KClass

*/
/**
 * MVVM架构:ViewModel+DataBinding
 * @Author: zhy
 * @Date: 2022/2/18
 *//*

abstract class BaseMvvmActivity<T : ViewDataBinding,
        K : BaseViewModel>(layoutResID: Int, viewModelClass: KClass<K>) : BaseActivity(layoutResID) {

    protected val b: T by lazy { DataBindingUtil.setContentView(this, layoutId) }
    protected open val vm: K by lazy { ViewModelProvider(this).get(viewModelClass.java) }
    private val layoutId = layoutResID

*/
/*
    constructor(layoutResID: Int, titleType: TitleType, viewModelClass: KClass<K>) : this(layoutResID, viewModelClass) {
        this.titleType = titleType
        this.layoutResID = layoutResID
    }
*//*


    override fun setupView() {
        bindModel()
        b.lifecycleOwner = this
        observe()
    }

    private fun observe() {
        //错误toast监听
        vm.toastError.observe(this) { errorId ->
            when (errorId) {
                BaseViewModel.errorNetwork -> toastMsg(R.string.no_network)
            }
        }
        //Http error code 错误toast监听
        vm.toastErrorByCode.observe(this) { code ->
            toastMsg(code)
        }
        //debug错误toast监听
        vm.debugError.observe(this) { message ->
            toastMsg(message)
        }
        //进度条监听
        vm.isLoadingState.observe(this) { isLoading ->
            if (isLoading) {
                showProgressDialog()
            } else {
                dismissProgressDialog()
            }
        }
        //toast监听
        vm.toast.observe(this) { message ->
            toastMsg(message)
        }
    }

    abstract fun bindModel()
}*/
