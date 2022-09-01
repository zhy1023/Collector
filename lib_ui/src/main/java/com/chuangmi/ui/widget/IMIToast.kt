package com.chuangmi.ui.widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chuangmi.base.R


/**
 * @Author: zhy
 * @Date: 2022/2/17
 * @Desc: IMIToast
 */
class IMIToast(context: Context) {
    var toast: Toast = Toast(context)
    var textView: TextView
    val mContext: Context = context

    init {
        val toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null)
        textView = toastRoot.findViewById<View>(R.id.toast_text) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        toast.view = toastRoot
        toast.setGravity(Gravity.CENTER, 0, 0)
    }

    fun show(message: String) {
        textView.text = message
        toast.show()
    }
}