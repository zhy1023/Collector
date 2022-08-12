package com.chuangmi.download;

import android.content.Context;
import android.util.Log;

import com.chuangmi.download.callback.IDownloadListener;
import com.chuangmi.download.callback.IDownloadManager;
import com.chuangmi.download.database.ILDownloadDataBase;
import com.chuangmi.download.exception.ILDownLoadException;
import com.chuangmi.download.model.ILDownloadInfo;
import com.chuangmi.download.constant.ILDownloadState;
import com.chuangmi.download.task.ILDownloadTask;
import com.chuangmi.download.utils.ILCheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhy
 * @Date: 2022/8/10
 * @Desc: ILDownloadManager
 */
public class ILDownloadManager implements IDownloadManager {
    private static final String TAG = ILDownloadManager.class.getSimpleName();

    private static volatile ILDownloadManager mInstance;
    private final ILDownloadDataBase mDataBase;
    //当前正在下载的任务
    private ILDownloadInfo mCurrentDownloadInfo;

    /**
     * 下载状态监听
     */
    private final List<IDownloadListener> mDownloadListenerList;
    private final ConcurrentLinkedQueue<ILDownloadInfo> mDownloadLinkedQueue;
    private final ExecutorService mExecutorService;

    private ILDownloadManager(Context context) {
        mDataBase = ILDownloadDataBase.getInstance(context);
        mDownloadListenerList = new ArrayList<>();
        mDownloadLinkedQueue = new ConcurrentLinkedQueue<>();
        mExecutorService = Executors.newSingleThreadExecutor();
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
        prepareDownload(downloadInfo);
    }

    @Override
    public synchronized void startDownload(List<ILDownloadInfo> downloadList) {
        if (ILCheckUtils.isEmpty(downloadList)) {
            Log.e(TAG, "addDownload downloadList is empty.");
            return;
        }
        for (ILDownloadInfo downloadInfo : downloadList) {
            prepareDownload(downloadInfo);
        }
    }

    private synchronized void prepareDownload(ILDownloadInfo downloadInfo) {
        mDownloadLinkedQueue.add(downloadInfo);
        downloadInfo.setState(ILDownloadState.PREPARE);
        mDownloadListener.onPrepared(downloadInfo);
        mDataBase.getDownloadInfoDao().insert(downloadInfo);
    }

    @Override
    public synchronized void stopDownload(ILDownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            Log.e(TAG, "stopDownload downloadInfo is null.");
            return;
        }
        if (mCurrentDownloadInfo.getId() == downloadInfo.getId() && !mExecutorService.isShutdown()) {
            mExecutorService.shutdownNow();
        }
        if (downloadInfo.getState() == ILDownloadState.STOP) {
            mDownloadListener.onStop(downloadInfo);
        } else {
            mDownloadListener.onDelete(downloadInfo);
        }
        mDataBase.getDownloadInfoDao().insert(downloadInfo);
    }

    public synchronized void stopAll() {
        if (!mExecutorService.isShutdown()) {
            mExecutorService.shutdownNow();
        }
        mCurrentDownloadInfo.setState(ILDownloadState.STOP);
        mDataBase.getDownloadInfoDao().update(mCurrentDownloadInfo);
        for (ILDownloadInfo downloadInfo : mDownloadLinkedQueue) {
            downloadInfo.setState(ILDownloadState.STOP);
            mDataBase.getDownloadInfoDao().update(downloadInfo);
        }
    }

    public synchronized void deleteAll() {
        if (!mExecutorService.isShutdown()) {
            mExecutorService.shutdownNow();
        }
        mCurrentDownloadInfo.setState(ILDownloadState.DELETE);
        mDataBase.getDownloadInfoDao().update(mCurrentDownloadInfo);
        for (ILDownloadInfo downloadInfo : mDownloadLinkedQueue) {
            downloadInfo.setState(ILDownloadState.DELETE);
            mDataBase.getDownloadInfoDao().update(downloadInfo);
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
            if (mExecutorService != null && mExecutorService.isTerminated()) {
                mCurrentDownloadInfo = mDownloadLinkedQueue.peek();
                mExecutorService.submit(new ILDownloadTask(mCurrentDownloadInfo, mDownloadListener));
            }
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
        }

        @Override
        public void onCompletion(ILDownloadInfo info) {
            Log.d(TAG, "onCompletion: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onCompletion(info);
                }
            }
            if (!mDownloadLinkedQueue.isEmpty()) {
                mCurrentDownloadInfo = mDownloadLinkedQueue.peek();
                mExecutorService.submit(new ILDownloadTask(mCurrentDownloadInfo, mDownloadListener));
            }
        }

        @Override
        public void onError(ILDownloadInfo info, ILDownLoadException e) {
            Log.d(TAG, "onError: " + info + ", exception: " + e);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onError(info, e);
                }
            }
        }

        @Override
        public void onDelete(ILDownloadInfo info) {
            Log.d(TAG, "onDelete: " + info);
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onDelete(info);
                }
            }
        }

        @Override
        public void onDeleteAll() {
            Log.d(TAG, "onDeleteAll.");
            for (IDownloadListener listener : mDownloadListenerList) {
                if (listener != null) {
                    listener.onDeleteAll();
                }
            }
        }
    };


}