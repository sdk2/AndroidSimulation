package com.liangqian8.android.simulation.program.Pvp;

import android.content.Context;
import android.widget.Toast;

import com.liangqian8.android.simulation.program.Program;
import com.liangqian8.android.simulation.util.BitmapUtil;
import com.liangqian8.android.simulation.util.RootShellCmd;

public class AI5V5Rudiments extends Program {

    public AI5V5Rudiments(Context context) {
        super(context);
    }

    //15 1065

    @Override
    public void run() {
        try {
            // 1.判断是否在游戏首页
            if (BitmapUtil.getBitmap().getPixel(10, 1060) != -15320747) {
                Toast.makeText(context, "不在游戏首页", Toast.LENGTH_SHORT).show();
                return;
            }
            // 2.对战模式
            RootShellCmd.simulateTap(400, 250);
            Thread.sleep(1500);
            // 3.人机练习
            RootShellCmd.simulateTap(1600, 350);
            Thread.sleep(1500);
            // 4.王者峡谷
            RootShellCmd.simulateTap(420, 380);
            Thread.sleep(1000);
            // 5.入门
            RootShellCmd.simulateTap(420, 380);
            Thread.sleep(3000);
            // 6.开始匹配
            RootShellCmd.simulateTap(1060, 900);
            Thread.sleep(1500);
            // 7.循环检测是否匹配成功
            while (true) {
                if (BitmapUtil.getBitmap().getPixel(700, 850) == -14473158) {
                    break;
                }
            }
            // 8.进入游戏
            RootShellCmd.simulateTap(950, 850);
            Thread.sleep(1500);
            // 9.循环检测是否进入游戏，等待其它玩家
            while (true) {
                if (BitmapUtil.getBitmap().getPixel(700, 850) != -14473158) {
                    break;
                }
            }
            //TODO 其他玩家没有进入，重新匹配
            //955 400血条
        } catch (InterruptedException e) {

        }
    }

}
