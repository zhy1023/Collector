/*
 *  Copyright (c) 2020 Oray Inc. All rights reserverd.
 *  No Part of this file may be reproduced, stored
 *  in a retrieval system, or transmitted, in any form, or by any means,
 *  electronic, mechanical, photocopying, recording, or otherwise,
 *  without the prior consent of Oray Inc.
 */

package com.chuangmi.download.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chuangmi.download.model.ILDownloadInfo;

/**
 * 本地数据库
 */
@Database(entities = {ILDownloadInfo.class}, version = 1, exportSchema = false)
public abstract class ILDownloadDataBase extends RoomDatabase {
    private static final String TAG = ILDownloadDataBase.class.getSimpleName();

    private static final String ROOM_NAME = "imi_database";
    private static volatile ILDownloadDataBase mInstance;

    public static ILDownloadDataBase getInstance(Context context) {
        if (null == mInstance) {
            synchronized (ILDownloadDataBase.class) {
                if (null == mInstance)
                    mInstance = Room
                            .databaseBuilder(context.getApplicationContext(), ILDownloadDataBase.class, ROOM_NAME)
//                            .addMigrations(MIGRATION_1_2)
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    Log.d(TAG, "imi_database onOpen.");
                                }

                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d(TAG, "imi_database onCreate.");
                                }
                            })
                            .build();
            }
        }
        return mInstance;
    }

    /**
     * 关闭数据库
     */
    public static void closeDataBase() {
        if (null != mInstance && mInstance.isOpen()) {
            mInstance.close();
        }
    }


    public abstract ILDownloadInfoDao getDownloadInfoDao();

    /**
     * 数据库版本 1->2 新增book表格
     */
//    private final static Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS `userinfo` (`id` INTEGER PRIMARY KEY autoincrement,`uid` INTEGER ,`account` TEXT , `password` TEXT , `isChecked` INTEGER)");
//        }
//    };
}
