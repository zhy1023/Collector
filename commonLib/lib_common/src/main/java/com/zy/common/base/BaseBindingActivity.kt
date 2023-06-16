package com.zy.common.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author: zhy
 * @Date: 2023/3/8
 * @Desc: BaseBindingActivity
 */

open class BaseBindingActivity<VB : ViewDataBinding>(layoutId: Int) : BaseActivity(layoutId) {

    protected val b: VB by lazy { DataBindingUtil.setContentView(this, layoutId) }

    override fun initContentView() {
        b.lifecycleOwner = this
    }
}
