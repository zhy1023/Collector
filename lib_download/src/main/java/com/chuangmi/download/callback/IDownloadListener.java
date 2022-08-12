package com.chuangmi.download.callback;

import com.chuangmi.download.exception.ILDownLoadException;
import com.chuangmi.download.model.ILDownloadInfo;

/**
 * @Author: zhy
 * @Date: 2022/8/11
 * @Desc: IDownloadListener
 */
public interface IDownloadListener {
    /**
     * 准备下载
     */
    void onPrepared(ILDownloadInfo info);

    /**
     * 开始下载
     */
    void onStart(ILDownloadInfo info);

    /**
     * 下载中
     *
     * @param percent : 下载百分比(0~100)
     */
    void onProgress(ILDownloadInfo info, int percent);

    /**
     * 暂停
     */
    void onStop(ILDownloadInfo info);

    /**
     * 下载完成
     */
    void onCompletion(ILDownloadInfo info);

    /**
     * 下载失败
     *
     * @param e : 下载异常信息
     */
    void onError(ILDownloadInfo info, ILDownLoadException e);

    /**
     * 删除成功
     */
    void onDelete(ILDownloadInfo info);

    /**
     * 全部删除
     */
    void onDeleteAll();
}
