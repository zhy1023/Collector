@file:JvmName("FileUtil")

package com.zy.common.kt_utils

import java.io.File
import java.io.IOException

/**
 * @Author: zhy
 * @Date: 2023/5/5
 * @Desc: FileUtil 文件工具类
 */

/**
 * 读取文件内容
 * @param filePath 文件路径
 * @return String 文件内容
 */
fun readFile(filePath: String): String {
    val file = File(filePath)
    if (!file.exists()) {
        throw IOException("File not found: $filePath")
    }
    return file.readText()
}

/**
 * 创建文件并写入内容
 * @param filePath 文件路径
 * @param content 写入内容
 */
fun writeFile(filePath: String, content: String) {
    val file = File(filePath)
    file.createNewFile()
    file.writeText(content)
}

/*** copy文件*/
fun copyFile(sourceFilePath: String, destinationFilePath: String) {
    val sourceFile = File(sourceFilePath)
    val destinationFile = File(destinationFilePath)
    sourceFile.copyTo(destinationFile, true)
}

/*** 删除文件*/
fun deleteFile(filePath: String) {
    val file = File(filePath)
    if (file.exists()) {
        file.delete()
    }
}

/**
 * 根据文件路径 创建文件
 */
fun createDirectory(directoryPath: String) {
    val directory = File(directoryPath)
    if (!directory.exists()) {
        directory.mkdirs()
    }
}

/**
 * 获取指定路劲下所有文件
 * @param directoryPath 目标路径
 * @return List<String>
 */
fun listFiles(directoryPath: String): List<String> {
    val directory = File(directoryPath)
    if (!directory.exists()) {
        throw IOException("Directory not found: $directoryPath")
    }
    return directory.listFiles()?.map { it.absolutePath } ?: emptyList()
}

/**
 * 获取分拣大小
 * @param filePath 文件路径
 * @return Long 文件大小
 */
fun getFileSize(filePath: String): Long {
    val file = File(filePath)
    if (!file.exists()) {
        throw IOException("File not found: $filePath")
    }
    return file.length()
}