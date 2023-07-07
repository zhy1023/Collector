package com.zy.collector

import android.view.View
import com.chuangmi.download.ILDownloadManager
import com.chuangmi.download.model.ILDownloadInfo
import com.chuangmi.download.utils.ILFileUtils
import com.zy.common.base.BaseActivity
import java.io.File

/**
 * @Author: zhy
 * @Date: 2022/8/19
 * @Desc: DownloadActivity
 */
class DownloadActivity : BaseActivity(R.layout.activity_download) {
    private val savePath: String by lazy { ILFileUtils.getDir(this) + "download" + File.separator }


    override fun initView() {
        findViewById<View>(R.id.start).setOnClickListener {
            handleStartDownload()
        }

        findViewById<View>(R.id.stop).setOnClickListener {
            ILDownloadManager.getInstance(this).stopAll()
        }
    }

    private fun handleStartDownload() {
        val info1 = ILDownloadInfo()
        info1.name = "sunflower.apk"
        info1.savePath = savePath
        info1.downloadUrl =
            "https://pgy.oray.com/softwares/156/download/1664/PgyVisitorEnt_1.2.0.apk"

        val info2 = ILDownloadInfo()
        info2.name = "Pgy.apk"
        info2.savePath = savePath
        info2.downloadUrl =
            "https://pgy.oray.com/softwares/156/download/1664/PgyVisitorEnt_1.2.0.apk"

        val info3 = ILDownloadInfo()
        info3.name = "peanut.apk"
        info3.savePath = savePath
        info3.downloadUrl =
            "https://pgy.oray.com/softwares/156/download/1664/PgyVisitorEnt_1.2.0.apk"


        val info4 = ILDownloadInfo()
        info4.name = "happy.apk"
        info4.savePath = savePath
        info4.downloadUrl =
            "https://pgy.oray.com/softwares/156/download/1664/PgyVisitorEnt_1.2.0.apk"

        val downloadList = ArrayList<ILDownloadInfo>()
        downloadList.add(info1)
        downloadList.add(info2)
        downloadList.add(info3)
        downloadList.add(info4)
        ILDownloadManager.getInstance(this).startDownload(downloadList)

    }

    companion object {
        const val TAG = "DownloadActivity"
    }

    override fun onDestroy() {
        super.onDestroy()
        ILDownloadManager.getInstance(this).release()
    }
}