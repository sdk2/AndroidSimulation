package com.liangqian8.android.simulation.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * 用root权限执行Linux下的Shell指令
 */
public final class RootShellCmd {

    private static OutputStream os;

    /**
     * 执行shell指令
     *
     * @param cmd 指令
     */
    private static void exec(String cmd) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 后台模拟全局按键
     */
    public static void simulateKey(int keyCode) {
        exec("input keyevent " + keyCode + "\n");
    }

    /**
     * 后台模拟屏幕触摸
     */
    public static void simulateTap(int x, int y) {
        exec("input tap " + x + " " + y + "\n");
    }

    /**
     * 后台模拟屏幕滑动
     */
    public static void simulateSwipe(int x1, int y1, int x2, int y2, int t) {
        exec(String.format(Locale.CHINA, "input swipe %d %d %d %d %d\n", x1, y1, x2, y2, t));
    }

    /**
     * 获取屏幕截图
     */
    public static void screenshot(String fileName) {
        exec("screencap -p " + fileName + "\n");
    }
}