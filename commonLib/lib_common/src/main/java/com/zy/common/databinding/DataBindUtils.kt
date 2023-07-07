package com.zy.common.databinding

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <A : ViewDataBinding> Activity.setContent(@LayoutRes layout: Int): A {
    return DataBindingUtil.setContentView(this, layout)
}

fun <A : ViewDataBinding> ViewGroup.inflate(@LayoutRes layout: Int): A {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layout, this, false)
}

fun <A : ViewDataBinding> Context.inflate(@LayoutRes layout: Int): A {
    return DataBindingUtil.inflate(LayoutInflater.from(this), layout, null, false)
}

fun <A : ViewDataBinding> View.bind(): A {
    return DataBindingUtil.bind(this)!!
}