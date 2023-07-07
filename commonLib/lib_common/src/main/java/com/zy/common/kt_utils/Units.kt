@file:Suppress("unused")

package com.zy.common.kt_utils

import android.content.res.Resources

//<editor-fold desc="Dimension">

/*converts dp value into px*/
val Number.dp
    get() = (this.toFloat() * Resources.getSystem().displayMetrics.density).toInt()

/*converts sp value into px*/
val Number.sp
    get() = (this.toFloat() * Resources.getSystem().displayMetrics.scaledDensity).toInt()

//</editor-fold>


//<editor-fold desc="Memory">

val Number.KB: Long
    get() = this.toLong() * 1024L

val Number.MB: Long
    get() = this.KB * 1024

val Number.GB: Long
    get() = this.MB * 1024

//</editor-fold>


