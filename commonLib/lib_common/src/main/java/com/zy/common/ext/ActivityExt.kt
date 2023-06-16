package com.zy.common.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

/**
 * @Author: zhy
 * @Date: 2023/4/24
 * @Desc Activity工具类
 */

/**
 * 启动一个Activity
 */
inline fun <reified T : Activity> Context.launchActivity(
    intent: Intent? = null
) {
    if (intent == null) {
        startActivity(Intent(this, T::class.java))
    } else {
        intent.setClass(this, T::class.java)
        startActivity(intent)
    }
}

/**
 * 启动一个Activity，并传递参数
 */
inline fun <reified T : Activity> Context.launchActivity(vararg extras: Pair<String, Any?>) =
    startActivity(Intent(this, T::class.java).apply {
        putParams(*extras)
    })

/**
 * 启动一个Activity，并获得回调
 * @receiver:FragmentActivity
 */
inline fun <reified T : Activity> FragmentActivity.launchActivityForResult(noinline callback: (resultCode: Int, data: Intent?) -> Unit) =
    EasyResult(this).startActForResult(T::class.java, callback)


/**
 * 启动一个Activity，传入参数并获得回调
 * @receiver:FragmentActivity
 */
inline fun <reified T : Activity> FragmentActivity.launchActivityForResult(
    vararg extras: Pair<String, Any?>,
    noinline callback: (resultCode: Int, data: Intent?) -> Unit
) = EasyResult(this).startActForResult(
    Intent(this, T::class.java).apply { putParams(*extras) },
    callback
)

inline fun <reified T : Activity> FragmentActivity.easyResult(noinline callback: (resultCode: Int, data: Intent?) -> Unit) {
    val easyResult = EasyResult(this)
    easyResult.startActForResult(T::class.java) { resultCode, data ->
        callback(resultCode, data)
        // 这里可以根据需要做一些额外的处理，比如处理异常情况等等
    }
}

/**
 * 启动一个Activity，并获得回调
 * @receiver:Fragment
 */
inline fun <reified T : Activity> Fragment.launchActivityForResult(noinline callback: (resultCode: Int, data: Intent?) -> Unit) =
    EasyResult(this).startActForResult(T::class.java, callback)

/**
 * 启动一个Activity，传入参数并获得回调
 * @receiver:Fragment
 */
inline fun <reified T : Activity> Fragment.launchActivityForResult(
    vararg extras: Pair<String, Any?>,
    noinline callback: (resultCode: Int, data: Intent?) -> Unit
) = EasyResult(this).startActForResult(
    Intent(this.context, T::class.java).apply { putParams(*extras) },
    callback
)


/**
 * 启动一个Activity，传递Intent并获得回调
 * @receiver:FragmentActivity
 */
fun FragmentActivity.launchActWithIntentForResult(
    intent: Intent,
    callback: (resultCode: Int, data: Intent?) -> Unit
) = EasyResult(this).startActForResult(intent, callback)

/**
 * 启动一个Activity，传递Intent并获得回调
 * @receiver:Fragment
 */
fun Fragment.launchActWithIntentForResult(
    intent: Intent,
    callback: (resultCode: Int, data: Intent?) -> Unit
) = EasyResult(this).startActForResult(intent, callback)


class EasyResult(resultCaller: ActivityResultCaller) {
    fun <T> startActForResult(
        clazz: Class<T>,
        callback: (resultCode: Int, data: Intent?) -> Unit
    ) = Unit

    fun startActForResult(intent: Intent, callback: (resultCode: Int, data: Intent?) -> Unit) {}
}

/**
 * 获取String类型参数
 */
fun FragmentActivity.extraString(key: String, default: String = ""): Lazy<String> = lazy {
    intent?.extras?.getString(key) ?: default
}

/**
 * 获取Boolean类型参数
 */
fun FragmentActivity.extraBoolean(key: String, default: Boolean = false): Lazy<Boolean> = lazy {
    intent?.extras?.getBoolean(key) ?: default
}

/**
 * 获取Int类型参数
 */
fun FragmentActivity.extraInt(key: String, default: Int = 0): Lazy<Int> = lazy {
    intent?.extras?.getInt(key) ?: default
}

/**
 * 获取Long类型参数
 */
fun FragmentActivity.extraLong(key: String, default: Long = 0L): Lazy<Long> = lazy {
    intent?.extras?.getLong(key) ?: default
}

/**
 * 获取Double类型参数
 */
fun FragmentActivity.extraDouble(key: String, default: Double = 0.0): Lazy<Double> = lazy {
    intent?.extras?.getDouble(key) ?: default
}

/**
 * 获取Float类型参数
 */
fun FragmentActivity.extraFloat(key: String, default: Float = 0.0F): Lazy<Float> = lazy {
    intent?.extras?.getFloat(key) ?: default
}

/**
 * 获取Parcelable类型参数
 */
inline fun <reified T : Parcelable> FragmentActivity.extraParcelable(
    key: String,
    default: T
): Lazy<T> = lazy {
    (intent?.extras?.getParcelable(key) ?: default)
}

/**
 * 获取Serializable类型参数
 */
inline fun <reified T : Serializable> FragmentActivity.extraSerializable(
    key: String,
    default: T
): Lazy<T> =
    lazy {
        (intent?.extras?.getSerializable(key) ?: default) as T
    }

/*** 将参数传入Intent*/
fun Intent.putParams(
    vararg extras: Pair<String, Any?>
): Intent {
    if (extras.isEmpty()) return this
    extras.forEach { (key, value) ->
        value ?: let {
            it.putExtra(key, it.toString())
            return@forEach
        }
        when (value) {
            is Bundle -> this.putExtra(key, value)
            is Boolean -> this.putExtra(key, value)
            is BooleanArray -> this.putExtra(key, value)
            is Byte -> this.putExtra(key, value)
            is ByteArray -> this.putExtra(key, value)
            is Char -> this.putExtra(key, value)
            is CharArray -> this.putExtra(key, value)
            is String -> this.putExtra(key, value)
            is CharSequence -> this.putExtra(key, value)
            is Double -> this.putExtra(key, value)
            is DoubleArray -> this.putExtra(key, value)
            is Float -> this.putExtra(key, value)
            is FloatArray -> this.putExtra(key, value)
            is Int -> this.putExtra(key, value)
            is IntArray -> this.putExtra(key, value)
            is Long -> this.putExtra(key, value)
            is LongArray -> this.putExtra(key, value)
            is Short -> this.putExtra(key, value)
            is ShortArray -> this.putExtra(key, value)
            is Parcelable -> this.putExtra(key, value)
            is Serializable -> this.putExtra(key, value)
            else -> throw IllegalArgumentException("Not support $value type ${value.javaClass}..")
        }
    }
    return this
}

/*** 从Intent中获取参数*/
inline fun <reified T : Any> FragmentActivity.extraParam(key: String, default: T) = lazy {
    val param = intent?.extras?.get(key)
    require(param is T || param == null) { "Invalid type of the param: ${param?.javaClass?.canonicalName}" }
    (param as? T) ?: default
}
