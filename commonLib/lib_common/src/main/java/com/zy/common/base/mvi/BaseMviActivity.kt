package com.zy.common.base.mvi

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zy.common.base.BaseActivity
import com.zy.common.widget.StatusViewOwner
import kotlin.reflect.KClass

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc: BaseMviActivity
 */
abstract class BaseMviActivity<VB : ViewDataBinding, VM : ViewModel>(
    layoutId: Int,
    private val viewModelClass: KClass<VM>
) : BaseActivity(layoutId) {

    protected val b: VB by lazy { DataBindingUtil.setContentView(this, layoutId) }
    protected lateinit var vm: VM

    protected lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
    }

    override fun initContentView() {
        bindModel()
        b.lifecycleOwner = this
        registerEvent()
    }

    open fun bindModel() {
        vm = ViewModelProvider(this)[viewModelClass.java]
    }

    /*** 注册UI状态事件*/
    abstract fun registerEvent()

    /*** 重新请求*/
    open fun retryRequest() {}

    /**
     * 展示Loading、Empty、Error视图等
     *  无异常页面展示无需实现
     */
    open fun getStatusOwnerView(): View? = null
}