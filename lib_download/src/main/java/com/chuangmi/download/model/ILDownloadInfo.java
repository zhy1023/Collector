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

    /** 文件名称*/
    @ColumnInfo(name = "name")
    private String name;

    /** 文件下载链接*/
    @ColumnInfo(name = "downloadUrl")
    private String downloadUrl;

    /** 文件存储路径*/
    @ColumnInfo(name = "savePath")
    private String savePath;

    /** 下载状态*/
    @ColumnInfo(name = "state")
    private int state = ILDownloadState.IDLE;

    /** 文件已下载长度*/
    @ColumnInfo(name = "currentSize")
    private long currentSize;

    /** 文件总长度*/
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    @Override
    public String toString() {
        return "ILDownloadInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", savePath='" + savePath + '\'' +
                ", state=" + state +
                ", currentSize=" + currentSize +
                ", size=" + size +
                '}';
    }
}