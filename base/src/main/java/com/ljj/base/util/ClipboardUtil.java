package com.ljj.base.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by 一锅子鱼 on 2019/1/28.
 */
public enum ClipboardUtil {
    text,
    intent,
    rawUrl;

    /**
     * 复制
     *
     * @param content
     * @param context
     */
    public static boolean copyContent(ClipboardUtil type, Object content, Context context) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = null;
            switch (type) {
                case text:
                    clipData = ClipData.newPlainText("text", content.toString());
                    break;
                case intent:
                    clipData = ClipData.newIntent("intent", (Intent) content);
                    break;
                case rawUrl:
                    clipData = ClipData.newRawUri("url", (Uri) content);
                    break;
            }
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(context,"复制微信号成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"复制微信号失败",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 粘贴
     */
    public static Object getContent(ClipboardUtil type, Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        Object result = null;
        try {
            if (clipboardManager.hasPrimaryClip()) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                switch (type) {
                    case text:
                        result = clipData.getItemAt(0).getText().toString();
                        break;
                    case intent:
                        result = clipData.getItemAt(0).getIntent();
                        break;
                    case rawUrl:
                        result = clipData.getItemAt(0).getUri();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
