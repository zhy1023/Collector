package com.chuangmi.download;

import android.content.Context;
import android.util.Log;

import com.chuangmi.download.callback.IDownloadListener;
import com.chuangmi.download.callback.IDownloadManager;
import com.chuangmi.download.constant.ILDownloadState;
import com.chuangmi.download.database.ILDataBaseUtils;
import com.chuangmi.download.database.ILDownloadDataBase;
import com.chuangmi.download.exception.ILDownLoadException;
import com.chuangmi.download.model.ILDownloadInfo;
import com.chuangmi.download.task.ILDownloadTask;
import com.chuangmi.download.utils.ILCheckUtils;
import com.zy.common.utils.CheckUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: zhy
 * @Date: 2022/8/10
 * @Desc: ILDownloadManager
 */
public class ILDownloadManager implements IDownloadManager {
    private static final String TAG = ILDownloadManager.class.getSimpleName();

    private static volatile ILDownloadManager mInstance;

    //最大下载数
    private int MAX_NUM = 1;

    /**
     * 下载状态监听
     */
    private final List<IDownloadListener> mDownloadListenerList;
    /**
     * 正在下载的数据集合
     */
    private final ConcurrentLinkedQueue<ILDownloadInfo> mDownloadList;
    /**
     * 准备下载的数据集合
     */
    private final ConcurrentLinkedQueue<ILDownloadInfo> mPreparedList;
    /**
     * 下载数据与下载任务--对应
     */
    private final LinkedHashMap<ILDownloadInfo, ILDownloadTask> mDownloadMap;

    private ILDownloadManager(Context context) {
        ILDataBaseUtils.init(context);
        mDownloadList = new ConcurrentLinkedQueue<>();
        mPreparedList = new ConcurrentLinkedQueue<>();
        mDownloadMap = new LinkedHashMap<>();
        mDownloadListenerList = new ArrayList<>();
    }

    public static ILDownloadManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ILDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new ILDownloadManager(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void setDownloadListener(IDownloadListener listener) {
        if (!mDownloadListenerList.contains(listener)) {
            mDownloadListenerList.add(listener);
        }
    }

    @Override
    public boolean removeDownloadListener(IDownloadListener listener) {
        if (mDownloadListenerList.contains(listener)) {
            return mDownloadListenerList.remove(listener);
        }
        return false;
    }

    public void clearDownloadListener() {
        if (!mDownloadListenerList.isEmpty()) {
            mDownloadListenerList.clear();
        }
    }

    @Override
    public synchronized void startDownload(ILDownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            Log.e(TAG, "addDownload downloadInfo is null.");
            return;
        }
        if (mPreparedList.contains(downloadInfo) || mDownloadList.contains(downloadInfo)) {
            Log.i(TAG, "download info already exist.");
            return;
        }
        prepareDownload(downloadInfo);
    }

    @Override
    public synchronized void startDownload(List<ILDownloadInfo> downloadList) {
        if (ILCheckUtils.isEmpty(downloadList)) {
            Log.e(TAG, "addDownload downloadList is empty.");
            return;
        }
        for (ILDownloadInfo downloadInfo : downloadList) {
            if (mPreparedList.contains(downloadInfo) || mDownloadList.contains(downloadInfo)) {
                Log.i(TAG, "download info already exist.");
                continue;
            }
            prepareDownload(downloadInfo);
        }
    }

    private synchronized void prepareDownload(ILDownloadInfo downloadInfo) {
        downloadInfo.setState(ILDownloadState.PREPARE);
        mDownloadMap.put(downloadInfo, new ILDownloadTask(downloadInfo, mDownloadListener));
        mDownloadListener.onPrepared(downloadInfo);
        ILDataBaseUtils.insert(downloadInfo);
    }

    @Override
    public synchronized void stopDownload(ILDownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            Log.e(TAG, "stopDownload downloadInfo is null.");
            return;
        }
        ILDownloadTask downloadTask = mDownloadMap.get(downloadInfo);
        if (downloadTask != null && !downloadTask.isInterrupted()) {
            downloadTask.interrupt();
        }
        if (downloadInfo.getState() == ILDownloadState.STOP) {
            mDownloadListener.onStop(downloadInfo);
        } else {
            mDownloadListener.onDelete(downloadInfo);
        }
        ILDataBaseUtils.insert(downloadInfo);
    }

    public synchronized void stopAll() {
        for (ILDownloadInfo downloadInfo : mDownloadList) {
            ILDownloadTask downloadTask = mDownloadMap.get(downloadInfo);
            if (downloadTask != null && !downloadTask.isInterrupted()) {
                downloadTask.interrupt();
            }
            downloadInfo.setState(ILDownloadState.STOP);
            ILDataBaseUtils.update(downloadInfo);
            mDownloadList.remove(downloadInfo);
        }
        for (ILDownloadInfo downloadInfo : mPreparedList) {
            downloadInfo.setState(ILDownloadState.STOP);
            ILDataBaseUtils.update(downloadInfo);
            mPreparedList.remove(downloadInfo);
        }
    }

    public synchronized void deleteAll() {
        for (ILDownloadInfo downloadInfo : mDownloadList) {
            ILDownloadTask downloadTask = mDownloadMap.get(downloadInfo);
            if (downloadTask != null && !downloadTask.isInterrupted()) {
                downloadTask.interrupt();
            }
            downloadInfo.setState(ILDownloadState.DELETE);
            ILDataBaseUtils.update(downloadInfo);
            mDownloadList.remove(downloadInfo);
        }
        for (ILDownloadInfo downloadInfo : mPreparedList) {
            downloadInfo.setState(ILDownloadState.DELETE);
            ILDataBaseUtils.update(downloadInfo);
            mPreparedList.remove(downloadInfo);
        }
    }

    /**
     * 自动下载下一个
     */
    private synchronized void autoDownload() {
        if (!mPreparedList.isEmpty() && mDownloadList.size() < MAX_NUM) {
            ILDownloadInfo downloadInfo = mPreparedList.poll();
            Log.d(TAG, "autoDownload info: " + downloadInfo);
            mDownloadList.add(downloadInfo);
            ILDownloadTask downloadTask = mDownloadMap.get(downloadInfo);
            if (downloadTask != null) {
                try {
                    downloadTask.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    mDownloadListener.onError(downloadInfo, new ILDownLoadException(-1, e.getMessage()));
                }
            }
        }
    }

    private final IDownloadListener mDownloadListener = new IDownloadListener() {
        @Override
        public void onPrepared(ILDownloadInfo info) {
            Log.d(TAG, "onPrepared: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onPrepared(info);
                }
            }
            mPreparedList.add(info);
            autoDownload();
        }

        @Override
        public void onStart(ILDownloadInfo info) {
            Log.d(TAG, "onStart: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onStart(info);
                }
            }
        }

        @Override
        public void onProgress(ILDownloadInfo info, int percent) {
            Log.d(TAG, "onProgress: " + info + ", percent: " + percent);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onProgress(info, percent);
                }
            }
        }

        @Override
        public void onStop(ILDownloadInfo info) {
            Log.d(TAG, "onStop: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onStop(info);
                }
            }
            mDownloadList.remove(info);
            autoDownload();
        }

        @Override
        public void onCompletion(ILDownloadInfo info) {
            Log.d(TAG, "onCompletion: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onCompletion(info);
                }
            }
            mDownloadList.remove(info);
            autoDownload();
        }

        @Override
        public void onError(ILDownloadInfo info, ILDownLoadException e) {
            Log.d(TAG, "onError: " + info + ", exception: " + e);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onError(info, e);
                }
            }
            mDownloadList.remove(info);
            autoDownload();
        }

        @Override
        public void onDelete(ILDownloadInfo info) {
            Log.d(TAG, "onDelete: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onDelete(info);
                }
            }
            mDownloadList.remove(info);
            autoDownload();
        }
    };

    @Override
    public int getMaxNum() {
        return MAX_NUM;
    }

    @Override
    public void setMaxNum(int maxNum) {
        this.MAX_NUM = maxNum;
    }

    /**
     * 清空所有下载队列
     */
    public void clearDownloadList() {
        if (!CheckUtils.isEmpty(mDownloadMap)) {
            mDownloadMap.clear();
        }
        if (!CheckUtils.isEmpty(mPreparedList)) {
            mPreparedList.clear();
        }
        if (!CheckUtils.isEmpty(mDownloadList)) {
            mDownloadList.clear();
        }
    }

    public void release() {
        clearDownloadList();
        ILDownloadDataBase.closeDataBase();
        ILDownloadManager.mInstance = null;
    }
}