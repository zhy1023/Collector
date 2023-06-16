package com.zy.common.base

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

/**
 * @Author: zhy
 * @Date: 2023/3/7
 */
abstract class BaseActivity(layoutId: Int) : AppCompatActivity(), IContract.IBaseActivityFun,
    CoroutineScope by MainScope() {
    /*** 横竖屏监听  */
    protected var isPort = false
    private var mLayoutId by Delegates.notNull<Int>()
    protected val mHandler by lazy { BaseWeakHandler(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            finish()
        }
        initActivityNeed()
        handleIntent(intent)
        initContentView()
        checkOrientationStatus()
        initView()
        initData()
        initListener()
    }

    init {
        mLayoutId = layoutId
    }

    override fun initActivityNeed() {
    }

    override fun initContentView() = setContentView(mLayoutId)

    override fun initView() {}

    override fun initData() {}

    override fun initListener() {}

    override fun handleIntent(intent: Intent?) {}


    override fun showProgressDialog(isShow: Boolean) {
        launch {

        }
    }

    override fun showCommonDialog(message: String?, callback: DialogInterface.OnClickListener) {
        launch {

        }
    }

    override fun showProgressDialog(message: String?) = showXQProgressDialog(message)

    override fun showProgressDialog(message: Int) = showProgressDialog(getString(message))

    private fun showXQProgressDialog(message: String?) {
        launch {

        }
    }

    class BaseWeakHandler<T : BaseActivity>(t: T) : Handler(Looper.getMainLooper()) {
        private val mReference: WeakReference<T> = WeakReference(t)

        override fun handleMessage(msg: Message) {
            val t = mReference.get() ?: return
            t.handleMessage(msg)
        }

    }

    override fun handleMessage(msg: Message?) {}

    /**
     * 是否深色状态栏颜色 默认深色
     *
     * @return
     */
    protected open fun isTitleDark(): Boolean = true

    /**
     * 是否调用沉浸状态栏
     *
     * @return
     */
    protected open fun isTitleEnable(): Boolean = true

    protected fun <V : View?> findView(id: Int): V = findViewById(id)

    private fun checkOrientationStatus() {
        isPort = requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected fun activity(): Activity = this

    override fun onConfigurationChanged(configuration: Configuration) {
        super.onConfigurationChanged(configuration)
//        Log.d(TAG, "onConfigurationChanged: $isPort")
        checkOrientationStatus()
    }

    override fun attachBaseContext(newBase: Context) =
        super.attachBaseContext(newBase)

    companion object {
        val TAG: String = BaseActivity::class.java.simpleName
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}