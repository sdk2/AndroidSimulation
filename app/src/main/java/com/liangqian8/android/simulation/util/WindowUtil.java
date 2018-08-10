package com.liangqian8.android.simulation.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liangqian8.android.simulation.R;
import com.liangqian8.android.simulation.program.Pvp.AI5V5Rudiments;

public final class WindowUtil {
    private static final String LOG_TAG = "WindowUtil";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static WindowManager.LayoutParams params = null;
    private static Boolean isShown = false;
    private static Thread thread = null;
    private static Runnable runnable = new AI5V5Rudiments();

    /**
     * 显示弹出框
     *
     * @param context
     */
    public static void showPopupWindow(final Context context) {
        if (isShown) {
            return;
        }
        isShown = true;
        Log.i(LOG_TAG, "showPopupWindow");
        // 获取应用的Context
        Context mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(context);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL; //the key of back is not working
        //int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM; // cannot call input method
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    static AI5V5Rudiments ai5V5Rudiments = null;

    @SuppressLint("ClickableViewAccessibility")
    private static View setUpView(final Context context) {
        Log.i(LOG_TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow,
                null);
        EditText editText1 = (EditText) view.findViewById(R.id.editText1);
        EditText editText2 = (EditText) view.findViewById(R.id.editText2);
        TextView textView = (TextView) view.findViewById(R.id.content);
        Button positiveBtn = (Button) view.findViewById(R.id.positiveBtn);
        Button guajiBtn = (Button) view.findViewById(R.id.guajiBtn);
        Button guaji2Btn = (Button) view.findViewById(R.id.guaji2Btn);
        Button stopguajiBtn = (Button) view.findViewById(R.id.stopguajiBtn);
        Button negativeBtn = (Button) view.findViewById(R.id.negativeBtn);
        positiveBtn.setOnClickListener(v -> {
            String x = editText1.getText().toString();
            String y = editText2.getText().toString();
            textView.setText(x + " " + y);
            try {
                int pixel = BitmapUtil.getBitmap().getPixel(Integer.valueOf(x), Integer.valueOf(y));
                String color = ColorUtil.pixedToColor(pixel);
                textView.append("\n" + pixel + "  " + color);
            } catch (NumberFormatException e) {
            }
        });

        guajiBtn.setOnClickListener(v -> {
            if (thread == null) {
                thread = new Thread(runnable);
                thread.start();
                guajiBtn.setText("停止");
            } else {
                thread.interrupt();
                thread = null;
                guajiBtn.setText("挂机");
            }
        });
        guaji2Btn.setOnClickListener(v -> {
        });
        stopguajiBtn.setOnClickListener(v -> {
        });
        negativeBtn.setOnClickListener(v -> {
            WindowUtil.hidePopupWindow();
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastX = rawX;
                    lastY = rawY;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    params.x += rawX - lastX;
                    params.y += rawY - lastY;
                    mWindowManager.updateViewLayout(view, params);
                    lastX = rawX;
                    lastY = rawY;
                    return true;
                }
                return false;
            }
        });
        return view;
    }

}
