package com.chuangmi.base.ui

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.chuangmi.base.R
import com.chuangmi.base.utils.ActivityCollector
import com.chuangmi.base.utils.ActivityCollector.setCurrentActivity
import com.chuangmi.base.utils.LogUtils
import com.chuangmi.base.widget.IMIToast
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

/**
 * @Author: zhy
 * @Date: 2022/2/18
 * @Desc: BaseActivity
 */
abstract class BaseActivity(layoutResID: Int) : AppCompatActivity(), IBaseContract.BaseActivityFun,
    CoroutineScope {

    /**
     * 判断当前Activity是否在前台。
     */
    protected var isActive: Boolean = false

    /**
     * 当前Activity的实例。
     */
    protected var activity: Activity? = null

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null

    protected var layoutResID by Delegates.notNull<Int>()
    protected lateinit var rootView: ViewGroup
    private var rootViewVisibleHeight: Int = 0
    val weakHandler by lazy { BaseWeakHandler(this) }

    /**
     * 创建当前activity使用的协程。
     */
    private val job by lazy { Job() }

    private val imm: InputMethodManager by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val wm: WindowManager by lazy { getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    var isActivityToastShow = false

    protected val toast by lazy { IMIToast(this) }

    init {
        this.layoutResID = layoutResID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        activityWR = WeakReference(activity!!)
        ActivityCollector.pushTask(activityWR)
        setupView()
        init(savedInstanceState)
    }

    /** 提供给子类做初始化扩展 */
    open fun setupView() {
        setContentView(layoutResID)
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onResume() {
        super.onResume()
        isActive = true
        setCurrentActivity(activityWR)
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        dismissProgressDialog()
    }

    override fun onDestroy() {
        activity = null
        ActivityCollector.removeTask(activityWR)
        cancel()
        super.onDestroy()
    }

    override fun setContentView(layoutResID: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.setContentView(R.layout.base_kt_layout_base_activity)
        rootView = findViewById(R.id.content_layout)
        LayoutInflater.from(this).inflate(layoutResID, rootView, true)

        //替换rootView的ID，适配dataBinding
        //清掉所有的android.R.id.content ID
        var rootContent: ViewGroup? = findViewById(android.R.id.content)
        while (rootContent != null) {
            rootContent.id = View.NO_ID
            rootContent = findViewById(android.R.id.content)
        }
        rootView.id = android.R.id.content

    }


    override fun toastMsg(message: String) {
        toast.show(message)
    }

    override fun toastMsg(id: Int) {
        toastMsg(getString(id))
    }

    override fun showSoftInput(view: View) {
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    override fun hideSoftInput(view: View) {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showSoftInput() {
        if (currentFocus != null)
            showSoftInput(currentFocus!!)
    }

    override fun hideSoftInput() {
        if (currentFocus != null)
            hideSoftInput(currentFocus!!)
    }

    override fun toggleSoftInput() {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun isSoftInputVisible(): Boolean {
        val outRect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(outRect)
        return window.decorView.bottom - outRect.bottom > heightThreshold
    }

    override fun setSoftInputListener(l: IBaseContract.OnSoftKeyBoardChangeListener) {
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            //获取当前根视图在屏幕上显示的大小
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)
            val visibleHeight = r.height()
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight
                return@addOnGlobalLayoutListener
            }

            //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
            if (rootViewVisibleHeight == visibleHeight) {
                return@addOnGlobalLayoutListener
            }

            //根视图显示高度变小超过200，可以看作软键盘显示了
            if (rootViewVisibleHeight - visibleHeight > heightThreshold) {
                l.keyBoardShow(rootViewVisibleHeight - visibleHeight)
                rootViewVisibleHeight = visibleHeight
                return@addOnGlobalLayoutListener
            }

            //根视图显示高度变大超过200，可以看作软键盘隐藏了
            if (visibleHeight - rootViewVisibleHeight > heightThreshold) {
                l.keyBoardHide(rootViewVisibleHeight - visibleHeight)
                rootViewVisibleHeight = visibleHeight
            }
        }
    }


    open fun performMessage(msg: Message) {}


    open fun isTransition(): Boolean {
        return true
    }


    private fun cancelActivityToast() {
        if (isActivityToastShow) {
            try {
                wm.removeView(toast.toast.view)
                isActivityToastShow = false
            } catch (e: Exception) {
                LogUtils.e(TAG, "cancelActivityToast error:$e")
            }
        }
    }


    class BaseWeakHandler<T : BaseActivity>(t: T) :
        Handler(Looper.getMainLooper()) {
        private val mReference: WeakReference<T> = WeakReference(t)
        override fun handleMessage(msg: Message) {
            val t = mReference.get() ?: return
            t.performMessage(msg)
        }

    }

    override fun showAlertDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun showCustomAlertDialog(layout: Int): View {
        TODO("Not yet implemented")
    }

    override fun showInputDialog(
        title: String,
        message: String,
        defaultInput: String,
        block: (content: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun showProgressDialog() {
        TODO("Not yet implemented")
    }

    override fun showProgressDialog(message: String) {
        TODO("Not yet implemented")
    }

    override fun dismissAlertDialog() {
        TODO("Not yet implemented")
    }

    override fun dismissProgressDialog() {
        TODO("Not yet implemented")
    }

    override fun checkPermissions(permissions: List<String>, block: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun setFullScreen(enable: Boolean) {
        TODO("Not yet implemented")
    }



    companion object {
        /** 短吐司显示的时长 */
        const val SHORT_DURATION_TIMEOUT: Long = 2000

        /** 长吐司显示的时长 */
        const val LONG_DURATION_TIMEOUT: Long = 3500

        /** 软键盘判断预置 */
        const val heightThreshold = 200

        /** 弹框队列触发器 */
        const val DIALOG = "dialogTrigger"

        /**日志输出标志*/
        const val TAG: String = "BaseActivity"
    }
}