package com.ljj.base.util;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by 一锅子鱼 on 2018/11/8.
 */
public class CommonUtil {

    /**
     * 返回正确的UserAgent
     *
     * @return
     */
    public static String getUserAgent(Context context) {
        StringBuffer userAgent = new StringBuffer();
        userAgent.append(AppUtil.getAppProcessName(context) + "/");
        userAgent.append(AppUtil.getVersionName(context));
        userAgent.append("(android;" + SystemUtil.getSystemVersion() + ";");
        userAgent.append(SystemUtil.getDeviceBrand() + "@" + getChannelName(context) + ")");
        return userAgent.toString();
    }



    /**
     * 获取渠道名
     *
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context context) {
        String channelName = "anzhi";
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("UMENG_CHANNEL"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (WalleChannelReader.getChannel(App.getApp().getApplicationContext())!=null&&!WalleChannelReader.getChannel(App.getApp().getApplicationContext()).equals("")){
//            channelName=WalleChannelReader.getChannel(App.getApp().getApplicationContext());
//        }
        return channelName;
    }
    public static String getUniqueId(Context context){
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        if (androidID==null||id==null){
            return getBuildInfo();
        }
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
    public static String getBuildInfo() {
        //这里选用了几个不会随系统更新而改变的值
        StringBuffer buildSB = new StringBuffer();
        buildSB.append(Build.BRAND).append("/");
        buildSB.append(Build.PRODUCT).append("/");
        buildSB.append(Build.DEVICE).append("/");
        buildSB.append(Build.ID).append("/");
        buildSB.append(Build.VERSION.INCREMENTAL);
        return buildSB.toString();
        //        return Build.FINGERPRINT;
    }
}
