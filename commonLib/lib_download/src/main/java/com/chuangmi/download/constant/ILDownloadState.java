package com.chuangmi.download.constant;

/**
 * @Author: zhy
 * @Date: 2022/8/12
 * @Desc: DownloadState 下载状态
 */
public interface ILDownloadState {
    /** 空闲 */
    int IDLE = 0;
    /** 准备 */
    int PREPARE = 1;
    /** 开始 */
    int START = 2;
    /** 进行中 */
    int PROGRESS = 3;
    /** 暂停 */
    int STOP = 4;
    /** 完成 */
    int COMPLETE = 5;
    /** 异常 */
    int ERROR = 6;
    /** 删除 */
    int DELETE = 7;
}
