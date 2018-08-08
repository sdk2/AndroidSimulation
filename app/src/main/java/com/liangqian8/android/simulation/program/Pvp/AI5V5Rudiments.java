package com.liangqian8.android.simulation.program.Pvp;

import android.content.Context;
import android.graphics.Bitmap;
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
            Toast.makeText(context, "开始运行...", Toast.LENGTH_SHORT).show();
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
            // 6.点击开始匹配
            RootShellCmd.simulateTap(1060, 900);
            Thread.sleep(1500);

            // 尝试3次匹配进入游戏
            int num = 3;
            while (num-- > 0) {
                // 7.循环检测是否匹配成功
                while (true) {
                    if (BitmapUtil.getBitmap().getPixel(700, 850) == -14473158) {
                        break;
                    }
                }
                // 8.点击进入游戏
                RootShellCmd.simulateTap(950, 850);
                Thread.sleep(1500);
                // 判断是否进入游戏成功，因为可能其他人拒绝游戏
                boolean enterSuccess = false;
                while (true) {
                    // 此时有两种情况
                    // (1)选择英雄界面为进入成功
                    // (2)返回匹配队列，这种情况只需检测匹配成功的界面即可
                    Bitmap bitmap = BitmapUtil.getBitmap();
                    // 9.选择英雄界面
                    if (bitmap.getPixel(1900, 10) == -14464100) {
                        enterSuccess = true;
                        break;
                    }
                    // 正在匹配的页面
                    if (bitmap.getPixel(20, 980) == -15260100) {
                        break;
                    }
                }
                if (enterSuccess) {
                    break;
                }
            }
            // 10.选择英雄
            //TODO 450 550 展开
            //确定 1750 1020

            // 11.进入游戏成功
            while (true) {
                Bitmap bitmap = BitmapUtil.getBitmap();
                if (bitmap.getPixel(950, 400) == -10694630 ||
                        bitmap.getPixel(955, 400) == -10694630 ||
                        bitmap.getPixel(960, 400) == -10694630) {
                    break;
                }
            }

            // 12.游戏结束，继续
            while (true) {
                if (BitmapUtil.getBitmap().getPixel(1000, 1000) == -13136196) {
                    break;
                }
            }
            // 继续
            // 返回房间
            // 开始匹配
        } catch (InterruptedException e) {

        }
    }

    private boolean stop = false;

    public void runTemp() {
        try {
            int temp1 = 2;
            while (temp1-- > 0) {
                if (stop) break;
                // 装备 + 1,2技能
                RootShellCmd.simulateTap(200, 430);
                Thread.sleep(1000);
                RootShellCmd.simulateTap(200, 430);
                Thread.sleep(1000);
                RootShellCmd.simulateTap(1320, 840);
                Thread.sleep(1000);
                RootShellCmd.simulateTap(1440, 650);
                Thread.sleep(1000);
                // 出门（下路）
                RootShellCmd.simulateSwipe(300, 850, 530, 895, 16000 + 3000);
                // 普攻
                int temp = 20;
                while (temp-- > 0) {
                    RootShellCmd.simulateTap(1420, 950);
                    RootShellCmd.simulateTap(1740, 920);
                    Thread.sleep(2000);
                }
                // 回城
                RootShellCmd.simulateTap(960, 990);
                Thread.sleep(10000);
                RootShellCmd.simulateTap(960, 990);
                Thread.sleep(10000);
                Thread.sleep(5000);
            }
            while (true) {
                if (stop) break;
                RootShellCmd.simulateTap(200, 430);
                RootShellCmd.simulateTap(960, 990);
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopTemp() {
        stop = true;
    }

    private void method() throws InterruptedException {
        // 购买装备
        RootShellCmd.simulateTap(200, 430);
        Thread.sleep(1000);
        // 加点1技能
        RootShellCmd.simulateTap(1320, 840);
        Thread.sleep(1000);
        // 加点2技能
        RootShellCmd.simulateTap(1440, 650);
    }

}
