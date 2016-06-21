package com.jkarthus.joe.demo.download;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * @author shihuajian
 * @depict 系统下载工具
 * @since 5/17/16 5:43 PM
 */
public class DownloadUtils {

    public static final String EXTRA_DOWNLOAD_ID = "extra_download_id";

    public static void executeDownload(Context context, String fileUrl) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(fileUrl);
        Request request = new Request(uri);

        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);

        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //不显示下载界面
        request.setVisibleInDownloadsUi(false);

        /* 设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，
        下载后的文件在/mnt/sdcard/Android/data/packageName/files目录下面，
        如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个目录下面 */
        String fileName = "QIANLU";
        File folder = Environment.getExternalStoragePublicDirectory(fileName);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }

        request.setDestinationInExternalPublicDir(fileName, uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1));
        // TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
        long id = downloadManager.enqueue(request);
        System.setProperty(EXTRA_DOWNLOAD_ID, String.valueOf(id));
    }

}
