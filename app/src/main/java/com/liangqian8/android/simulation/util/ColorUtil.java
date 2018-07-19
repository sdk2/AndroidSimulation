package com.liangqian8.android.simulation.util;

public final class ColorUtil {

    public static String pixedToColor(int pixel) {
        return "#" + Integer.toHexString(pixel & 0xFFFFFF).toUpperCase();
    }

}
