package com.liangqian8.android.simulation.program;

import android.content.Context;

public abstract class Program implements Runnable {

    protected Context context;

    public Program(Context context) {
        this.context = context;
    }
}
