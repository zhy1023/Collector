package com.chuangmi.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

/**
 * @Author: zhy
 * @Date: 2022/2/17
 * @Desc: BaseFragment
 */
abstract class BaseFragment(layoutResID: Int) : Fragment(), IBaseContract.BaseFragmentFun,
    CoroutineScope {

    /**
     * 日志输出标志
     */
    protected var layoutResID by Delegates.notNull<Int>()
    protected var isLazyLoaded: Boolean = false
    open val baseActivity: BaseActivity by lazy { activity as BaseActivity }
    lateinit var rootView: View

    /**
     * 创建当前fragment使用的协程。
     */
    private val job by lazy { Job() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.layoutResID = layoutResID
    }

    override fun onDestroyView() {
        cancel()
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!this::rootView.isInitialized) {
            rootView = customCreateView(inflater, container, savedInstanceState)
            initView(rootView, savedInstanceState)
            customObserve()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        //执行懒加载
        if (this::rootView.isInitialized && !isLazyLoaded) {
            lazyLoad()
            isLazyLoaded = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //执行懒加载
        if (this::rootView.isInitialized && !isLazyLoaded) {
            lazyLoad()
            isLazyLoaded = true
        }
    }

    open fun customCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutResID, container, false)
    }

    open fun customObserve() {
        //用于子类客制化监听
    }

    @UiThread
    abstract override fun initView(view: View, savedInstanceState: Bundle?)

    @UiThread
    abstract override fun lazyLoad()


    companion object {
        const val TAG = "BaseFragment"
    }
}