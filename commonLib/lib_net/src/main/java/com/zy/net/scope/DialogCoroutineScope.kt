
package com.zy.net.scope

import android.app.Dialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zy.net.NetConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * 自动加载对话框网络请求
 *
 *
 * 开始: 显示对话框
 * 错误: 提示错误信息, 关闭对话框
 * 完全: 关闭对话框
 *
 * @param activity 对话框跟随生命周期的FragmentActivity
 * @param dialog 不使用默认的加载对话框而指定对话框
 * @param cancelable 是否允许用户取消对话框
 */
@Suppress("DEPRECATION")
class DialogCoroutineScope(
    val activity: FragmentActivity,
    var dialog: Dialog? = null,
    val cancelable: Boolean = true,
    dispatcher: CoroutineDispatcher = Dispatchers.Main
) : NetCoroutineScope(dispatcher = dispatcher), LifecycleObserver {

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun start() {
        activity.runOnUiThread {
            dialog = when {
                dialog != null -> dialog
                else -> NetConfig.dialogFactory.onCreate(activity)
            }
            dialog?.setOnDismissListener { cancel() }
            dialog?.setCancelable(cancelable)
            if (!activity.isFinishing) dialog?.show()
        }
    }

    override fun previewFinish(succeed: Boolean) {
        if (succeed) {
            dismiss()
        }
    }

    override fun finally(e: Throwable?) {
        super.finally(e)
        dismiss()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dismiss() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }

}