package com.ljj.base.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

/**
 * Created by 一锅子鱼 on 2019/3/29.
 */
public class LogFileUtil {
    private static String filenameTemp = "fileLog.txt";
    private static String folderName = "LogFile";

    public static String[] permissionsREAD = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //创建文件夹及文件
    public static File createText() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), folderName);
        if (!file.exists()) {
            try {
                //按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
            }
        }
        File dir = new File(file, filenameTemp);
        if (!dir.exists()) {
            try {
                //在指定的文件夹中创建文件
                dir.createNewFile();
            } catch (Exception e) {
            }
        }
        Logger.e("文件地址--" + dir.getAbsolutePath());
        return dir;
    }

    //向已创建的文件中写入数据
    public static void print(Context context,String str) {
        if (!lacksPermissions(context)) {
            File logFile = createText();
            FileWriter fw = null;
            BufferedWriter bw = null;
            String datetime = "";
            try {
                SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " "
                        + "hh:mm:ss");
                datetime = tempDate.format(new java.util.Date());
                fw = new FileWriter(logFile, true);//
                // 创建FileWriter对象，用来写入字符流
                bw = new BufferedWriter(fw); // 将缓冲对文件的输出
                String myreadline = datetime + "-" + str;
                bw.write(myreadline + "\n"); // 写入文件
                bw.newLine();
                bw.flush(); // 刷新该流的缓冲
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fw!=null){
                        fw.close();
                    }
                } catch (Exception e1) {
                }
            }
        }
    }

    public static boolean lacksPermissions(Context context) {
        for (String permission : permissionsREAD) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
