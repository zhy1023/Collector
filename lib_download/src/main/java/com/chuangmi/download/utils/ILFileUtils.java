package com.chuangmi.download.utils;

import java.io.File;

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
}