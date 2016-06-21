package com.jkarthus.joe.demo.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.jkarthus.joe.demo.download.DownloadUtils;

/**
 * @author shihuajian
 * @depict DownloadManager 下载完成后触发的广播
 * @since 5/17/16 5:55 PM
 */
public class DownloadCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = DownloadCompleteReceiver.class.getSimpleName();

    private DownloadManager downloadManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            Toast.makeText(context, "下载完成了....", Toast.LENGTH_LONG).show();

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            long saveId = Long.valueOf(System.getProperty(DownloadUtils.EXTRA_DOWNLOAD_ID));
            Log.e(TAG, "ID: " + id + " and SaveID: " + saveId);
            // TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = downloadManager.query(query);

            int columnCount = cursor.getColumnCount();
            String path = null;
            // TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
            while (cursor.moveToNext()) {
                for (int j = 0; j < columnCount; j++) {
                    Log.e(TAG, "SDCard Print ========================================================");
                    String columnName = cursor.getColumnName(j);
                    String string = cursor.getString(j);
                    if (columnName.equals("local_uri")) {
                        path = string;
                    }
                    if (string != null) {
                        Log.e(TAG, columnName + ": " + string);
                    } else {
                        Log.e(TAG, columnName + ": null");
                    }
                }
            }
            cursor.close();
            //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理
            if (path != null && path.startsWith("content:")) {
                Log.e(TAG, "No SDCard Print ========================================================");
                cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
                if (cursor != null) {
                    columnCount = cursor.getColumnCount();
                    while (cursor.moveToNext()) {
                        for (int j = 0; j < columnCount; j++) {
                            String columnName = cursor.getColumnName(j);
                            String string = cursor.getString(j);
                            if (string != null) {
                                Log.e(TAG, columnName + ": " + string);
                            } else {
                                Log.e(TAG, columnName + ": null");
                            }
                        }
                    }
                    cursor.close();
                }
            } else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Toast.makeText(context, "点击通知", Toast.LENGTH_LONG).show();
            }
        } else if (DownloadManager.ACTION_VIEW_DOWNLOADS.equals(action)) {
            Toast.makeText(context, "下载", Toast.LENGTH_LONG).show();
        } else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            Toast.makeText(context, "点击通知", Toast.LENGTH_LONG).show();
        }
    }
}
