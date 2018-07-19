package com.liangqian8.android.simulation.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liangqian8.android.simulation.R;

public final class WindowUtil {
    private static final String LOG_TAG = "WindowUtil";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Boolean isShown = false;

    /**
     * 显示弹出框
     *
     * @param context
     */
    public static void showPopupWindow(final Context context) {
        if (isShown) {
            Log.i(LOG_TAG, "return cause already shown");
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
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL; //the key of back is not working
        //int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM; // cannot call input method
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
        Log.i(LOG_TAG, "add view");
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        Log.i(LOG_TAG, "hide " + isShown + ", " + mView);
        if (isShown && null != mView) {
            Log.i(LOG_TAG, "hidePopupWindow");
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private static View setUpView(final Context context) {
        Log.i(LOG_TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow,
                null);
        EditText editText1 = (EditText) view.findViewById(R.id.editText1);
        EditText editText2 = (EditText) view.findViewById(R.id.editText2);
        TextView textView = (TextView) view.findViewById(R.id.content);
        Button positiveBtn = (Button) view.findViewById(R.id.positiveBtn);
        Button negativeBtn = (Button) view.findViewById(R.id.negativeBtn);
        positiveBtn.setOnClickListener(v -> {
            Log.i(LOG_TAG, "you clicked!");
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
        negativeBtn.setOnClickListener(v -> {
            Log.i(LOG_TAG, "close the window");
            WindowUtil.hidePopupWindow();
        });
        return view;
    }

}
