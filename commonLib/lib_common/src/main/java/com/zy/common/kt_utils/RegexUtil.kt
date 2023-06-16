@file:JvmName("RegexUtil")

package com.zy.common.kt_utils

import android.util.Patterns

/**
 * @Author: zhy
 * @Date: 2023/4/24
 * @Desc 正则表达式工具类
 */

private fun String.checkWithRegex(pattern: String): Boolean = Regex(pattern).matches(this)

/**校验内地手机号码**/
val String.isMobile: Boolean
    get() = checkWithRegex("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$")

/**校验电子邮箱**/
val String.isEmail: Boolean
    get() = checkWithRegex(Patterns.EMAIL_ADDRESS.pattern())

/**校验网络链接**/
val String.isWebUrl: Boolean
    //    get() = checkWithRegex(Patterns.WEB_URL.pattern())
    get() = checkWithRegex("[a-zA-z]+://[^\\s]*")

/**校验数字**/
val String.isNumber: Boolean
    get() = checkWithRegex("^[0-9]*$")

/**校验正整数**/
val String.isPositiveInt: Boolean
    get() = checkWithRegex("""^[1-9]\d*${'$'}""")

/**校验负整数**/
val String.isNegativeInt: Boolean
    get() = checkWithRegex("""^-[1-9]\d*${'$'}""")

/**校验字母（无关大小写）**/
val String.isLetter: Boolean
    get() = checkWithRegex("^[A-Za-z]+$")

/**校验大写字母**/
val String.isUpperCaseLetter: Boolean
    get() = checkWithRegex("^[A-Z]+$")

/**校验小写字母**/
val String.isLowerCaseLetter: Boolean
    get() = checkWithRegex("^[a-z]+$")

/**校验汉字**/
val String.isChinese: Boolean
    get() = checkWithRegex("^[\\u4e00-\\u9fa5]+$")