package com.chuangmi.base.utils

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Log工具，类似android.util.Log。
 */
object LogUtils {
    private const val customTagPrefix = "ImiHome"
    private const val isSaveLog = true // 是否把保存日志到SD卡中
    private val ROOT = StorageUtils.storagePath + "/chuangmi/imihome" // SD卡中的根目录
    private val PATH_LOG_INFO = ROOT + "log/"
    private const val SPIT = " | "
    private const val allowV = false
    private const val allowD = false
    private const val allowI = true
    private const val allowW = true
    private const val allowE = true

    @SuppressLint("DefaultLocale")
    private fun generateTag(caller: StackTraceElement): String {
//        String tag = "%s.%s(Line:%d)";
        var callerClazzName = caller.className
        callerClazzName = callerClazzName.substring(
            callerClazzName
                .lastIndexOf(".") + 1
        )
        val lineNumber = caller.lineNumber
        var tag = if (lineNumber < 100) "(Line: %d)" else "(Line:%d)"
        tag = String.format(
            tag,  /*callerClazzName,caller.getMethodName(),*/
            lineNumber
        )
        tag = "$customTagPrefix:$tag"
        return tag
    }

    /**
     * 返回的数据格式是 1. edqwiuyhe 1. diuqwh
     * 需要转换成edqwiuyhe/ndiuqwh
     *
     * @param logs
     * @return
     */
    fun formatLogs(logs: String?): String {
        var logs = logs
        return if (null != logs && logs.isNotEmpty()) {
            val buffer = StringBuilder()
            logs = logs.replace("<ol>".toRegex(), "").replace("</ol>".toRegex(), "")
                .replace("</li>".toRegex(), "")
            val log = logs.split("<li>").toTypedArray()
            for (s in log) {
                buffer.append(s).append("\n")
            }
            buffer.subSequence(2, buffer.length - 2)
            buffer.toString()
        } else {
            ""
        }
    }

    /**
     * 自定义的logger
     */
    private val customLogger: CustomLogger? = null
    fun d(content: String?) {
        if (!allowD) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.d(tag, content)
        } else {
            Log.d(tag, content!!)
        }
    }

    fun d(TAG: String, content: String?) {
        if (!allowI) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.d(tag + SPIT + TAG, content)
        } else {
            Log.d(tag + SPIT + TAG, content!!)
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag + SPIT + TAG, content)
        }
    }

    fun d(content: String?, tr: Throwable?) {
        if (!allowD) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.d(tag, content, tr)
        } else {
            Log.d(tag, content, tr)
        }
    }

    fun e(content: String?) {
        if (!allowE) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.e(tag, content)
        } else {
            Log.e(tag, content!!)
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, content)
        }
    }

    fun e(TAG: String, content: String?) {
        if (!allowE) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.e(tag + SPIT + TAG, content)
        } else {
            Log.e(tag + SPIT + TAG, content!!)
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag + SPIT + TAG, content)
        }
    }

    fun e(content: String?, tr: Throwable) {
        if (!allowE) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.e(tag, content, tr)
        } else {
            Log.e(tag, content, tr)
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, tr.message)
        }
    }

    fun i(content: String?) {
        if (!allowI) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.i(tag, content)
        } else {
            Log.i(tag, content!!)
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, content)
        }
    }

    fun i(TAG: String, content: String?) {
        if (!allowI) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.i(tag + SPIT + TAG, content)
        } else {
            Log.i(tag + SPIT + TAG, content!!)
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag + SPIT + TAG, content)
        }
    }

    fun i(content: String?, tr: Throwable?) {
        if (!allowI) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.i(tag, content, tr)
        } else {
            Log.i(tag, content, tr)
        }
    }

    fun v(content: String?) {
        if (!allowV) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.v(tag, content)
        } else {
            Log.v(tag, content!!)
        }
    }

    fun v(content: String?, tr: Throwable?) {
        if (!allowV) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.v(tag, content, tr)
        } else {
            Log.v(tag, content, tr)
        }
    }

    fun w(content: String?) {
        if (!allowW) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.w(tag, content)
        } else {
            Log.w(tag, content!!)
        }
    }

    fun w(content: String?, tr: Throwable?) {
        if (!allowW) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.w(tag, content, tr)
        } else {
            Log.w(tag, content, tr)
        }
    }

    fun w(tr: Throwable?) {
        if (!allowW) return
        val caller = callerStackTraceElement
        val tag = generateTag(caller)
        if (customLogger != null) {
            customLogger.w(tag, tr)
        } else {
            Log.w(tag, tr)
        }
    }

    private val callerStackTraceElement: StackTraceElement
        get() = Thread.currentThread().stackTrace[4]

    private fun point(path: String, tag: String, msg: String?) {
        var path: String = path
        if (isSDAva) {
            val date = Date()
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.SIMPLIFIED_CHINESE
            )
            path += "log.txt"
            val time = dateFormat.format(date)
            val file = File(path)
            if (file.exists() && file.length() > 10 * 1024 * 1024) //Log日志大于10m 删除重新创建
                file.delete()
            if (!file.exists()) createDipPath(path)
            var out: BufferedWriter? = null
            try {
                out = BufferedWriter(
                    OutputStreamWriter(
                        FileOutputStream(file, true)
                    )
                )
                out.write("$time $tag $msg\r\n")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    out?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 根据文件路径 递归创建文件
     *
     * @param file
     */
    private fun createDipPath(file: String) {
        val parentFile = file.substring(0, file.lastIndexOf("/"))
        val file1 = File(file)
        val parent = File(parentFile)
        if (!file1.exists()) {
            parent.mkdirs()
            try {
                file1.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private val thread_local_formatter: ThreadLocal<ReusableFormatter> =
        object : ThreadLocal<ReusableFormatter>() {
            override fun initialValue(): ReusableFormatter {
                return ReusableFormatter()
            }
        }

    fun format(msg: String?, vararg args: Any?): String? {
        val formatter = thread_local_formatter.get() ?: return null
        return formatter.format(msg, *args)
    }

    private val isSDAva: Boolean
        get() = Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED || Environment.getExternalStorageDirectory().exists()

    interface CustomLogger {
        fun d(tag: String?, content: String?)
        fun d(tag: String?, content: String?, tr: Throwable?)
        fun e(tag: String?, content: String?)
        fun e(tag: String?, content: String?, tr: Throwable?)
        fun i(tag: String?, content: String?)
        fun i(tag: String?, content: String?, tr: Throwable?)
        fun v(tag: String?, content: String?)
        fun v(tag: String?, content: String?, tr: Throwable?)
        fun w(tag: String?, content: String?)
        fun w(tag: String?, content: String?, tr: Throwable?)
        fun w(tag: String?, tr: Throwable?)
    }

    /**
     * A little trick to reuse a formatter in the same thread
     */
    private class ReusableFormatter internal constructor() {
        private val formatter: Formatter
        private val builder: StringBuilder = StringBuilder()
        fun format(msg: String?, vararg args: Any?): String {
            formatter.format(msg, *args)
            val s = builder.toString()
            builder.setLength(0)
            return s
        }

        init {
            formatter = Formatter(builder)
        }
    }


}