package com.chuangmi.download.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chuangmi.download.constant.ILDownloadState;

import java.io.Serializable;

/**
 * @Author: zhy
 * @Date: 2022/8/10
 * @Desc: ILDownloadInfo
 */
@Entity(tableName = "download_info")
public class ILDownloadInfo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "path")
    private String path;

    @ColumnInfo(name = "savePath")
    private String savePath;

    /**
     * 下载状态
     */
    @ColumnInfo(name = "state")
    private int state = ILDownloadState.IDLE;

    @ColumnInfo(name = "size")
    private long size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


}