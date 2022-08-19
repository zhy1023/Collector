package com.zy.collector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.chuangmi.download.ILDownloadManager
import com.chuangmi.download.model.ILDownloadInfo
import com.chuangmi.download.utils.ILFileUtils
import java.io.File

/**
 * @Author: zhy
 * @Date: 2022/8/19
 * @Desc: DownloadActivity
 */
class DownloadActivity : AppCompatActivity() {
    private val savePath: String by lazy { ILFileUtils.getDir(this) + "imiLab/download" + File.separator }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        initView()
    }

    private fun initView() {
        findViewById<View>(R.id.start).setOnClickListener {
            handleStartDownload()
        }

        findViewById<View>(R.id.stop).setOnClickListener {
            ILDownloadManager.getInstance(this).stopAll()
        }
    }

    private fun handleStartDownload() {
        val info1 = ILDownloadInfo()
        info1.name = "向日葵.apk"
        info1.savePath = savePath
        info1.downloadUrl =
            "https://dl-cdn.oray.com/hsk/android/hskTunnel_2.0.apk"

        val info2 = ILDownloadInfo()
        info2.name = "蒲公英游戏版.apk"
        info2.savePath = savePath
        info2.downloadUrl =
            "https://dl-cdn.oray.com/hsk/android/hskTunnel_2.0.apk"

        val info3 = ILDownloadInfo()
        info3.name = "花生壳.apk"
        info3.savePath = savePath
        info3.downloadUrl =
            "https://dl-cdn.oray.com/hsk/android/hskTunnel_2.0.apk"


        val info4 = ILDownloadInfo()
        info4.name = "开心消消乐.apk"
        info4.savePath = savePath
        info4.downloadUrl =
            "https://dl-cdn.oray.com/hsk/android/hskTunnel_2.0.apk"

        val downloadList = ArrayList<ILDownloadInfo>()
        downloadList.add(info1)
        downloadList.add(info2)
        downloadList.add(info3)
        downloadList.add(info4)
        ILDownloadManager.getInstance(this).startDownload(downloadList)

    }

    companion object {
        val TAG = DownloadActivity::class.java.simpleName
    }
}