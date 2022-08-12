package com.chuangmi.download.task;

import com.chuangmi.download.callback.IDownloadListener;
import com.chuangmi.download.model.ILDownloadInfo;

import java.util.List;

/**
 * @Author: zhy
 * @Date: 2022/8/11
 * @Desc: ILDownloadTask
 */
public class ILDownloadTask implements Runnable {
    private ILDownloadInfo mDownloadInfo;
    private IDownloadListener mDownloadListeners;

    public ILDownloadTask(ILDownloadInfo mDownloadInfo, IDownloadListener downloadListener) {
        this.mDownloadInfo = mDownloadInfo;
        this.mDownloadListeners = downloadListener;
    }

    @Override
    public void run() {

    }
}