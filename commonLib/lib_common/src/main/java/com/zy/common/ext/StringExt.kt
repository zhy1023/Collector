package com.zy.common.ext

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc: String扩展方法
 */

const val HASH_MD5 = "MD5";

/**
 * 将字符串按给定进制解析为byte型数
 *
 * @param radix    进制
 * @param defValue 默认值
 * @return 解析结果，如果格式错误，默认返回给定的默认值
 */
fun String?.toByte(radix: Int = 10, defValue: Byte = 0.toByte()): Byte =
    if (isNullOrEmpty()) defValue else toByteOrNull(radix) ?: defValue

/**
 * 将字符串按给定进制解析为int型数
 *
 * @param radix    进制
 * @param defValue 默认值
 * @return 解析结果，如果格式错误，默认返回给定的默认值
 */
fun String?.toInt(radix: Int = 10, defValue: Int = 0): Int =
    if (isNullOrEmpty()) defValue else toIntOrNull(radix) ?: defValue

/**
 * 将字符串按给定进制解析为long型数
 *
 * @param radix    进制
 * @param defValue 默认值
 * @return 解析结果，如果格式错误，默认返回给定的默认值
 */
fun String?.toLong(radix: Int = 10, defValue: Long = 0): Long =
    if (isNullOrEmpty()) defValue else toLongOrNull(radix) ?: defValue

/**
 * 将字符串解析为float型浮点数
 *
 * @param defValue 默认值
 * @return 解析结果，如果格式错误，默认返回给定的默认值
 */
fun String?.toFloat(defValue: Float = 0f): Float =
    if (isNullOrEmpty()) defValue else toFloatOrNull() ?: defValue

/**
 * 将字符串解析为double型浮点数
 *
 * @param defValue 默认值
 * @return 解析结果，如果格式错误，默认返回给定的默认值
 */
fun String?.toDouble(defValue: Double = 0.0): Double =
    if (isNullOrEmpty()) defValue else toDoubleOrNull() ?: defValue

/**
 * 对字符串md5加密
 *
 * @return md5加密后的string
 */
fun String.md5(): String {
    if (isNullOrEmpty()) return ""
    var messageDigest: MessageDigest? = null
    try {
        //设置哪种算法
        messageDigest = MessageDigest.getInstance(HASH_MD5)
        //对算法进行重置以免影响下次计算
        messageDigest.reset()
        //使用指定的字段进行摘要
        messageDigest.update(this.toByteArray(StandardCharsets.UTF_8))
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    //完成Hash值的计算
    if (messageDigest == null) {
        return ""
    }
    val byteArray = messageDigest.digest()
    val md5StrBuff = StringBuilder()
    for (b in byteArray) {
        if (Integer.toHexString(0xFF and b.toInt()).length == 1) {
            md5StrBuff.append("0").append(Integer.toHexString(0xFF and b.toInt()))
        } else {
            md5StrBuff.append(Integer.toHexString(0xFF and b.toInt()))
        }
    }
    return md5StrBuff.toString()
}