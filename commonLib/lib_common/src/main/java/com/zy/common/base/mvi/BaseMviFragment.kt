package com.zy.common.base.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zy.common.base.BaseFragment
import com.zy.common.widget.StatusViewOwner
import kotlin.reflect.KClass

/**
 * @Author: zhy
 * @Date: 2023/5/8
 * @Desc: BaseMviFragment
 */
abstract class BaseMviFragment<VB : ViewDataBinding, VM : ViewModel>(
    layoutId: Int,
    viewModelClass: KClass<VM>
) : BaseFragment(layoutId) {

    lateinit var b: VB
    protected val vm: VM by lazy { ViewModelProvider(this)[viewModelClass.java] }
    protected lateinit var mStatusViewUtil: StatusViewOwner

    override fun customCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = DataBindingUtil.inflate(inflater, mLayoutId, container, false)
        b.lifecycleOwner = this
        mStatusViewUtil = StatusViewOwner(baseActivity, getStatusOwnerView()) {
            retryRequest()
        }
        bindModel()
        registerEvent()
        return b.root
    }

    abstract fun bindModel()

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
