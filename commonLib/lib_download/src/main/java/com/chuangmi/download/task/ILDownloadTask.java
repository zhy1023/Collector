package com.chuangmi.download.task;

import android.util.Log;

import com.chuangmi.download.callback.IDownloadListener;
import com.chuangmi.download.constant.ILDownloadState;
import com.chuangmi.download.database.ILDataBaseUtils;
import com.chuangmi.download.exception.ILDownLoadException;
import com.chuangmi.download.model.ILDownloadInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: zhy
 * @Date: 2022/8/11
 * @Desc: ILDownloadTask
 */
public class ILDownloadTask extends Thread {
    private static final String TAG = ILDownloadTask.class.getSimpleName();
    private final ILDownloadInfo mDownloadInfo;
    private final IDownloadListener mDownloadListener;


    public ILDownloadTask(ILDownloadInfo mDownloadInfo, IDownloadListener downloadListener) {
        this.mDownloadInfo = mDownloadInfo;
        this.mDownloadListener = downloadListener;
    }

    @Override
    public void run() {
        Log.d(TAG, "download start :" + mDownloadInfo);
        mDownloadInfo.setState(ILDownloadState.START);
        mDownloadListener.onStart(mDownloadInfo);
        long currentSize = 0;
        File file = new File(mDownloadInfo.getSavePath() + mDownloadInfo.getName());
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "file mkdir exception: " + e.getMessage());
                mDownloadListener.onError(mDownloadInfo, new ILDownLoadException(-1, e.getMessage()));
            }
        } else {
            currentSize = file.length();
        }
        Log.d(TAG, "download info init  currentSize :" + currentSize);
        try {
            URL url = new URL(mDownloadInfo.getDownloadUrl());
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestMethod("GET");
//            long totalSize = 17499966;
//            long totalSize = getContentSize(connection);
            long totalSize = connection.getContentLength();
            connection.setRequestProperty("Range", "bytes=" + currentSize
                    + "-" + totalSize);
            connection.connect();
            int code = connection.getResponseCode();
            Log.d(TAG, "http connection code: " + code);
            // 判断是否能够断点下载
            if (code == 206) {
                OutputStream outputStream = new FileOutputStream(file, true);
                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int length;
                mDownloadInfo.setState(ILDownloadState.PROGRESS);
                ILDataBaseUtils.update(mDownloadInfo);
                if (totalSize == currentSize) {
                    mDownloadListener.onError(mDownloadInfo, new ILDownLoadException(-1, "文件已下载完成"));
                    return;
                }
                mDownloadInfo.setSize(totalSize);
                int currentPercent = -1;
                while ((length = is.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                    currentSize += length;
                    mDownloadInfo.setCurrentSize(currentSize);
                    int percent = (int) (currentSize * 100 / totalSize);
                    if (percent > 0 && currentPercent != percent) {
                        currentPercent = percent;
                        mDownloadListener.onProgress(mDownloadInfo, currentPercent);
                    }
                }
                if (mDownloadInfo.getSize() == mDownloadInfo.getCurrentSize()) {
                    mDownloadInfo.setState(ILDownloadState.COMPLETE);
                    ILDataBaseUtils.update(mDownloadInfo);
                    mDownloadListener.onCompletion(mDownloadInfo);
                } else {
                    Log.e(TAG, "下载文件长度异常.");
                    mDownloadInfo.setState(ILDownloadState.ERROR);
                    ILDataBaseUtils.update(mDownloadInfo);
                    mDownloadListener.onCompletion(mDownloadInfo);
                    mDownloadInfo.setCurrentSize(0);
                    // 错误状态需要删除文件
                    file.delete();
                }
                is.close();
                outputStream.flush();
                outputStream.close();
                connection.disconnect();
            } else {
                Log.e(TAG, "HTTP exception :" + mDownloadInfo);
                mDownloadListener.onError(mDownloadInfo, new ILDownLoadException(code, "HTTP exception."));
            }
        } catch (Exception e) {
            Log.e(TAG, "下载异常 :" + e.getMessage());
            if ("thread interrupted".equals(e.getMessage())) {
                mDownloadListener.onStop(mDownloadInfo);
                return;
            }
            mDownloadInfo.setState(ILDownloadState.ERROR);
            mDownloadInfo.setCurrentSize(0);
            ILDataBaseUtils.update(mDownloadInfo);
            mDownloadListener.onError(mDownloadInfo, new ILDownLoadException(-1, e.getMessage()));
            // 错误状态需要删除文件
            file.delete();
        }

    }

    /**
     * 获取下载文件长度
     */
    private long getContentSize(HttpURLConnection conn) {
        long contentSize = 0;
        for (int i = 1; ; i++) {
            String mine = conn.getHeaderFieldKey(i);
            if (mine == null) {
                break;
            } else if (mine.equals("Content-Length")) {
                contentSize = Long.parseLong(conn.getHeaderField(i));
                break;
            }
        }
        Log.d(TAG, "download file total size :" + contentSize);
        return contentSize;
    }
}