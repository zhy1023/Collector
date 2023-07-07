package com.zy.common.databinding

import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/** 快速创建实现ViewModel和Observable */
class ObservableViewModel : ViewModel(), ObservableImpl {
    override val registry: PropertyChangeRegistry = PropertyChangeRegistry()
}