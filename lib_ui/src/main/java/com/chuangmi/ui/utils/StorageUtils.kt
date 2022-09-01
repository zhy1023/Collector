package com.chuangmi.ui.utils

import com.chuangmi.ui.utils.BuildConfig.hasQ
import com.chuangmi.ui.utils.LogUtils.e
import com.chuangmi.ui.utils.LogUtils.i
import android.text.TextUtils
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.os.Build
import com.chuangmi.ui.application.BaseApp
import java.io.*
import java.lang.Exception

/**
 * @author ； ZY
 * @date : 2020/9/9
 * @describe :AndroidQ存储路径适配
 */
object StorageUtils {
    private const val parentFileName = "chuangmi"
    val UPLOAD_PATH: Any = "upload"

    //获取外部Download路径
    /**
     * @param context 下载路劲
     * context.getExternalFilesDir(Environment.Environment.DIRECTORY_DOWNLOADS)
     *
     *
     * 视频文件
     * context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
     *
     *
     * 音频文件
     * context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
     *
     *
     * 图片文件
     * context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
     * @return
     */
    fun getExternalStorageDownloadPath(context: Context, path: String?): String {
        return context.getExternalFilesDir(if (TextUtils.isEmpty(path)) Environment.DIRECTORY_DOWNLOADS else path)
            .toString()
    }

    /**
     * AndroidQ以下版本存储路劲
     *
     * @return
     */
    private val oldStoragePath: String
        get() = Environment.getExternalStorageDirectory().absolutePath + File.separator + "oray" + File.separator + "pgy"
    private val oldSmbDownloadPath: String
        get() = (Environment.getExternalStorageDirectory().absolutePath + File.separator + "Download"
                + File.separator + "dandelion" + File.separator + "download")

    /**
     * AndroidQ存储路劲
     *
     * @return
     */
    val storagePath: String
        get() = if (hasQ()) getExternalStorageDownloadPath(
            BaseApp.context,
            ""
        ) else oldStoragePath

    /**
     * SMB文件下载路径
     *
     * @return
     */
    val smbDownloadStoragePath: String
        get() = if (hasQ()) getExternalStorageDownloadPath(
            BaseApp.context,
            "smb/download"
        ) else oldSmbDownloadPath

    /**
     * 存储图片文件
     *
     * @param context
     * @param file
     * @return
     */
    fun SavePictureFile(context: Context, file: File?): Boolean {
        if (file == null) {
            return false
        }
        val uri = insertFileIntoMediaStore(context, file, true)
        return SaveFile(context, file, uri)
    }

    /**
     * 存储视频文件
     *
     * @param context
     * @param file
     * @return
     */
    fun SaveVideoFile(context: Context, file: File?): Boolean {
        if (file == null) {
            return false
        }
        val uri = insertFileIntoMediaStore(context, file, false)
        return SaveFile(context, file, uri)
    }

    /**
     * 存储文件
     *
     * @param context
     * @param file
     * @param uri
     * @return
     */
    private fun SaveFile(context: Context, file: File, uri: Uri?): Boolean {
        if (uri == null) {
            e("url is null")
            return false
        }
        i("SaveFile: " + file.name)
        val contentResolver = context.contentResolver
        var parcelFileDescriptor: ParcelFileDescriptor? = null
        try {
            parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "w")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        if (parcelFileDescriptor == null) {
            e("parcelFileDescriptor is null")
            return false
        }
        val outputStream = FileOutputStream(parcelFileDescriptor.fileDescriptor)
        val inputStream: FileInputStream
        inputStream = try {
            FileInputStream(file)
        } catch (e: FileNotFoundException) {
            e(e.toString())
            try {
                outputStream.close()
            } catch (ex: IOException) {
                e(ex.toString())
            }
            return false
        }
        try {
            copy(inputStream, outputStream)
        } catch (e: IOException) {
            e(e.toString())
            return false
        } finally {
            try {
                outputStream.close()
            } catch (e: IOException) {
                e(e.toString())
            }
            try {
                inputStream.close()
            } catch (e: IOException) {
                e(e.toString())
            }
        }
        return true
    }

    //注意当文件比较大时该方法耗时会比较大
    @Throws(IOException::class)
    private fun copy(ist: InputStream, ost: OutputStream) {
        val buffer = ByteArray(4096)
        var byteCount: Int
        while (ist.read(buffer).also { byteCount = it } != -1) {
            ost.write(buffer, 0, byteCount)
        }
        ost.flush()
    }

    //创建视频或图片的URI
    private fun insertFileIntoMediaStore(context: Context, file: File, isPicture: Boolean): Uri? {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, file.name)
        contentValues.put(
            MediaStore.Video.Media.MIME_TYPE,
            if (isPicture) "image/jpeg" else "video/mp4"
        )
        if (Build.VERSION.SDK_INT >= 29) {
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, file.lastModified())
        }
        var uri: Uri? = null
        try {
            uri = context.contentResolver.insert(
                if (isPicture) MediaStore.Images.Media.EXTERNAL_CONTENT_URI else MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return uri
    }
}