package com.zy.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlin.properties.Delegates

/**
 * @Desc:
 * @Author: zhy
 * @Date: 2023/3/7
 */
abstract class BaseFragment(layoutId: Int) : Fragment(), IContract.IBaseFragmentFun, IRootView,
    CoroutineScope by MainScope() {

    protected var mRootView: View? = null
    protected var mLayoutId by Delegates.notNull<Int>()

    open val baseActivity: BaseActivity by lazy { activity as BaseActivity }

    init {
        mLayoutId = layoutId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = customCreateView(inflater, container, savedInstanceState)
            initFragmentNeed()
//            val mTitleBar = mRootView?.findViewById<View>(R.id.title_bar)
//            TitleBarUtil.setTitleBar(activity, mTitleBar)
            arguments?.let { handleBundle(it) }
            initView()
            initData()
            initListener()
            customObserve()
        }
        return mRootView
    }

    open fun customCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(mLayoutId, container, false)
    }

    override fun rootView(): View = mRootView!!

    open fun customObserve() {
        //用于子类客制化监听
    }

    override fun initFragmentNeed() {}

    override fun handleBundle(bundle: Bundle?) {}

    override fun initContentView() {}

    override fun initView() {}

    override fun initData() {}

    override fun initListener() {}

    override fun onDestroyView() {
        super.onDestroyView()
        cancel()
    }


}