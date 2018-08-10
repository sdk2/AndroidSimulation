package com.liangqian8.android.simulation.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liangqian8.android.simulation.R;
import com.liangqian8.android.simulation.program.Pvp.AI5V5Rudiments;
import com.liangqian8.android.simulation.util.WindowUtil;

public class MainActivity extends Activity {

    private Button button;
    private Button button2;
    private TextView textView1;

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


        button.setOnClickListener(view -> {
        });

        button2.setOnClickListener(view -> {
            new Handler().postDelayed(() -> WindowUtil.showPopupWindow(MainActivity.this), 3000);
        });
    }

    /**
     * 获取权限的回调函数
     *
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
     * 检查是否有读写存储设备的权限
     *
     * @param context
     * @return
     */
    private static boolean hasPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

}
