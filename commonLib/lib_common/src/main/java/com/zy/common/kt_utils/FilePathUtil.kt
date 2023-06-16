@file:JvmName("FilePathUtil")

package com.zy.common.kt_utils

import java.io.File

/**
 * @Author: zhy
 * @Date: 2023/5/5
 * @Desc: FilePathUtil
 */

/*** 文件是否存在*/
fun isExist(filePath: String): Boolean = File(filePath).exists()

/**
 * 获取文件名（不包含扩展名）
 */
fun getFileNameWithoutExtension(filePath: String): String {
    val file = File(filePath)
    if (!file.exists()) return ""
    val fileName = file.name
    val dotIndex = fileName.lastIndexOf('.')
    if (dotIndex == -1) {
        return fileName
    }
    return fileName.substring(0, dotIndex)
}

/**
 * 获取文件扩展名
 */
fun getFileExtension(filePath: String): String {
    val file = File(filePath)
    if (!file.exists()) return ""
    val fileName = file.name
    val dotIndex = fileName.lastIndexOf('.')
    if (dotIndex == -1) {
        return ""
    }
    return fileName.substring(dotIndex + 1)
}

/**
 * 获取文件所在目录路径
 */
fun getFileDirectory(filePath: String): String? {
    val file = File(filePath)
    return if (!file.exists()) "" else file.parent
}

/**
 * 合并路径
 */
fun combinePath(vararg paths: String): String {
    val separator = File.separator
    return paths.joinToString(separator)
}