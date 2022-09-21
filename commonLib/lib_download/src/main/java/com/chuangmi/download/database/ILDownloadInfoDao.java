/*
 *  Copyright (c) 2020 Oray Inc. All rights reserverd.
 *  No Part of this file may be reproduced, stored
 *  in a retrieval system, or transmitted, in any form, or by any means,
 *  electronic, mechanical, photocopying, recording, or otherwise,
 *  without the prior consent of Oray Inc.
 */

package com.chuangmi.download.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chuangmi.download.model.ILDownloadInfo;

import java.util.List;

/**
 * @author ； ZY
 * @date : 2020/11/9
 * @describe :帐号信息数据库操作类
 */
@Dao
public interface ILDownloadInfoDao {

    /**
     * 根据downloadUrl查询下载任务
     */
    @Query("select * from download_info where downloadUrl = :url")
    ILDownloadInfo getDownloadInfoByUrl(String url);

    /**
     * 查询所有帐号信息
     * asc升序（默认）
     * desc降序
     */
    @Query("select * from download_info order by id")
    List<ILDownloadInfo> getAll();

    /**
     * 插入一条帐号信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ILDownloadInfo downloadInfo);

    /**
     * 插入帐号信息集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ILDownloadInfo> downloadInfoList);

    /**
     * 删除一条帐号信息
     */
    @Delete
    void delete(ILDownloadInfo downloadInfo);

    /**
     * 删除帐号信息集合
     */
    @Delete
    void deleteAll(List<ILDownloadInfo> downloadInfoList);

    @Update
    void update(ILDownloadInfo downloadInfo);
}
