package com.chuangmi.download.callback;

import com.chuangmi.download.model.ILDownloadInfo;

import java.util.List;

/**
 * @Author: zhy
 * @Date: 2022/8/12
 * @Desc: IDownloadManager
 */
public interface IDownloadManager {
    /**
     * 开启单个下载任务信息
     */
    void startDownload(ILDownloadInfo downloadInfo);

    /**
     * 开启下载任务集合
     */
    void startDownload(List<ILDownloadInfo> downloadList);

    /**
     * 暂停、删除任务
     */
    void stopDownload(ILDownloadInfo downloadInfo);

    /**
     * 暂停全部任务
     */
    void stopAll();

    /**
     * 删除全部任务
     */
    void deleteAll();

    /**
     * 设置下载监听
     */
    void setDownloadListener(IDownloadListener listener);

    /**
     * 移除具体的下载监听
     */
    boolean removeDownloadListener(IDownloadListener listener);

    /**
     * 清空所有下载监听
     */
    void clearDownloadListener();
}