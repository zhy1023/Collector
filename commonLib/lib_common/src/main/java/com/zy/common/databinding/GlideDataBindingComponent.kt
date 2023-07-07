package com.zy.common.databinding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.zy.common.app.GlobalApp

object GlideDataBindingComponent {

    /**
     * 加载圆形图片
     * @param url 图片来源
     * @param holder 占位图
     */
    @BindingAdapter(value = ["imgCircle", "holder"], requireAll = false)
    @JvmStatic
    fun loadImageCircle(v: ImageView, url: Any?, holder: Drawable?) {
        if (url == null && holder == null) v.setImageDrawable(null)
        Glide.with(v.context).load(url).circleCrop().placeholder(holder).into(v)
    }

    /**
     * 加载图片
     * @param url 图片来源
     * @param holder 占位图
     * @param corner 图片四周圆角半径值
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = ["img", "holder", "corner"], requireAll = false)
    @JvmStatic
    fun loadImageWithHolder(v: ImageView, url: Any?, holder: Drawable?, corner: Int?) {
        if (url == null && holder == null) v.setImageDrawable(null)
        val requestBuilder = Glide.with(v.context).load(url).placeholder(holder)
        if (corner != null) {
            requestBuilder.transform(
                CenterCrop(),
                RoundedCorners((corner * GlobalApp.appContext.resources.displayMetrics.density).toInt())
            )
        }
        requestBuilder.into(v)
    }
}