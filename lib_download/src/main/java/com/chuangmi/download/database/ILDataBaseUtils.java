package com.chuangmi.download.database;

import android.content.Context;

import com.chuangmi.download.model.ILDownloadInfo;

import java.util.List;

/**
 * @Author: zhy
 * @Date: 2022/8/19
 * @Desc: ILDataBaseUtils
 */
public class ILDataBaseUtils {
    private static ILDownloadInfoDao mDownloadInfoDao;


    public static void init(Context context) {
        mDownloadInfoDao = ILDownloadDataBase.getInstance(context).getDownloadInfoDao();
    }


    public static ILDownloadInfo getDownloadInfoByUrl(String downloadUrl) {
        checkDataBaseDao();
        return mDownloadInfoDao.getDownloadInfoByUrl(downloadUrl);
    }

    public static List<ILDownloadInfo> getAll() {
        checkDataBaseDao();
        return mDownloadInfoDao.getAll();
    }

    public static void insert(ILDownloadInfo downloadInfo) {
        checkDataBaseDao();
        mDownloadInfoDao.insert(downloadInfo);
    }

    public static void insertAll(List<ILDownloadInfo> downloadInfoList) {
        checkDataBaseDao();
        mDownloadInfoDao.insertAll(downloadInfoList);
    }

    public static void delete(ILDownloadInfo downloadInfo) {
        checkDataBaseDao();
        mDownloadInfoDao.delete(downloadInfo);
    }


    public static void deleteAll(List<ILDownloadInfo> downloadInfoList) {
        checkDataBaseDao();
        mDownloadInfoDao.deleteAll(downloadInfoList);
    }


    public static void update(ILDownloadInfo downloadInfo) {
        checkDataBaseDao();
        mDownloadInfoDao.update(downloadInfo);
    }

    private static void checkDataBaseDao() {
        if (mDownloadInfoDao == null) {
            throw new IllegalArgumentException("mDownloadInfoDao must be initialized .");
        }
    }

}