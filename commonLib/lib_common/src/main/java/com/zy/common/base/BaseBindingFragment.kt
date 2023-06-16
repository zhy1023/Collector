package com.zy.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author: zhy
 * @Date: 2023/5/8
 * @Desc: BaseBindingFragment
 */
open class BaseBindingFragment<VB : ViewDataBinding>(layoutId: Int) : BaseFragment(layoutId) {
    lateinit var b: VB

    override fun customCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = DataBindingUtil.inflate(inflater, mLayoutId, container, false)
        b.lifecycleOwner = this
        return b.root
    }
}