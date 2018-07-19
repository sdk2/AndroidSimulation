package com.liangqian8.android.simulation.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liangqian8.android.simulation.R;
import com.liangqian8.android.simulation.util.RootShellCmd;
import com.liangqian8.android.simulation.util.WindowUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private Button button;
    private Button button2;
    private TextView textView1;
    private String fileName = Environment.getExternalStorageDirectory() + "/screenshot.jpg";
    private File file = new File(fileName);

    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!hasPermission(MainActivity.this)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setMovementMethod(ScrollingMovementMethod.getInstance());
        mHandler = new Handler();
        Handler startHandler = new Handler(message -> {
            textView1.append("\n" + "start");
            return false;
        });

        Handler myHandler = new Handler(message -> {
            Bundle data = message.getData();
            textView1.append("\n" + data.getString("pixel"));
            return false;
        });

        Handler errorHandler = new Handler(message -> {
            Bundle data = message.getData();
            textView1.append("\n" + data.getString("error"));
            return false;
        });

        button.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                try {
                    screenshot();
                    if (!blockByFileLength()) {
                        Log.e("screenshot", "file.length() == 0");
                        return;
                    }
                    Bitmap bitmap;
                    try (FileInputStream inputStream = new FileInputStream(file)) {
                        byte[] bytes = streamToBytes(inputStream);
                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    }
                    if (bitmap == null) {
                        Log.e("bitmap", "bitmap == null");
                        return;
                    }
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    textView1.append("\nwidth=" + width + " height=" + height);
                    int pixel = bitmap.getPixel(969, 132);
                    String color = "#" + Integer.toHexString(pixel & 0xFFFFFF).toUpperCase();
                    textView1.append("\ncolor at 969, 132 is " + color);
                } catch (Exception e) {
                    textView1.append("\n" + e.toString());
                }
            }, 2000);
        });

        button2.setOnClickListener(view -> {
            mHandler.postDelayed(() -> WindowUtil.showPopupWindow(MainActivity.this), 0);
        });
    }

    /**
     * 获取权限的回调函数
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        }
    }

    /**
     * 阻塞直到截图文件生成完毕
     * @return
     */
    private boolean blockByFileLength() {
        int attempts = 20;
        int num = 0;
        while (num++ < attempts) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("screenshot", "attempt to " + num + " times");
            if (file.length() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 屏幕截图
     */
    private void screenshot() {
        //删除文件
        if (file.exists()) file.delete();
        //调用su截图
        Log.i("screenshot", "start to screenshot");
        RootShellCmd.screenshot(fileName);
    }

    /**
     * 检查是否有读写存储设备的权限
     * @param context
     * @return
     */
    private static boolean hasPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 读取文件流转化为字节数组
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
