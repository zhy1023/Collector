package com.chuangmi.download.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @Author: zhy
 * @Date: 2022/8/12
 * @Desc: ILFileUtils
 */
public class ILFileUtils {
    public static boolean deleteAllFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return false;
            }

            if (file.isFile()) {
                return deleteFile(path);
            } else {
                return deleteDirectory(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }


    public static boolean deleteDirectory(String dir) {
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);

        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }

        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                flag = deleteFile(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else if (file.isDirectory()) {
                flag = deleteDirectory(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            return false;
        }
        return dirFile.delete();
    }

    /**
     * android Q 版本默认路径
     * /storage/emulated/0/Android/data/包名/files/Media/
     * android Q 以下版本默认"/sdcard/DCIM/Camera/"
     */
    public static String getDir(Context context) {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dir = context.getExternalFilesDir("") + File.separator + "Media" + File.separator;
        } else {
            dir = Environment.getExternalStorageDirectory() + File.separator + "DCIM"
                    + File.separator + "Camera" + File.separator;
        }
        File file = new File(dir);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return dir;
    }

    /**
     * 换算文件的大小
     */
    public String FormatFileSize(long fileSize) {
        if (fileSize <= 0) {
            return "0M";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

}