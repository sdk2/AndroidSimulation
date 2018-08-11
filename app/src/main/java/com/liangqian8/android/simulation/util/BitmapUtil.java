package com.liangqian8.android.simulation.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public final class BitmapUtil {

    private static final String LOG_TAG = "BitmapUtil";
    private static final String fileName = Environment.getExternalStorageDirectory() + "/screenshot.jpg";
    private static final File file = new File(fileName);

    public static Bitmap getBitmap() {
        try {
            screenshot();
            if (!blockByFileLength()) {
                Log.e(LOG_TAG, "file.length() == 0");
                return Bitmap.createBitmap(1920,1080, Bitmap.Config.ALPHA_8);
            }
            Bitmap bitmap;
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = streamToBytes(inputStream);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            if (bitmap == null) {
                Log.e(LOG_TAG, "bitmap == null");
                return Bitmap.createBitmap(1920,1080, Bitmap.Config.ALPHA_8);
            }
            return bitmap;
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        return null;
    }

    /**
     * 阻塞直到截图文件生成完毕
     *
     * @return
     */
    private static boolean blockByFileLength() {
        int attempts = 10;
        int num = 0;
        while (num++ < attempts) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            if (file.length() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 屏幕截图
     */
    private static void screenshot() {
        //删除文件
        if (file.exists()) file.delete();
        //调用su截图
        Log.i(LOG_TAG, "start to screenshot");
        RootShellCmd.screenshot(fileName);
    }

    /**
     * 读取文件流转化为字节数组
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    private static byte[] streamToBytes(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.flush();
        return outStream.toByteArray();
    }
}
