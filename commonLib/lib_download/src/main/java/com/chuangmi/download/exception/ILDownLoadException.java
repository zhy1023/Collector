package com.chuangmi.download.exception;

/**
 * @Author: zhy
 * @Date: 2022/8/11
 * @Desc: ILDownLoadException
 */
public class ILDownLoadException {
    private int code;
    private String msg;

    public ILDownLoadException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ILDownLoadException(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ILDownLoadException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}