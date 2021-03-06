package com.liangqian8.android.simulation.program.Pvp;

import android.graphics.Bitmap;
import android.util.Log;

import com.liangqian8.android.simulation.program.Program;
import com.liangqian8.android.simulation.util.BitmapUtil;
import com.liangqian8.android.simulation.util.RootShellCmd;

public class AI5V5Rudiments extends Program {

    private static final String LOG_TAG = "AI5V5";
    private static final int heroListX = 105;
    private static final int heroListY = 235;
    private static final int heroListOffsetX = 195;
    private static final int heroListOffsetY = 210;
    private int goTime = 0;
    private int[][] hero;
    private boolean scroll;

    public AI5V5Rudiments() {
    }

    @Override
    public void run() {
        try {
            // 1.判断是否在游戏首页
            // 2.对战模式
            RootShellCmd.simulateTap(400, 250);
            Thread.sleep(1500);
            // 3.人机练习
            RootShellCmd.simulateTap(1600, 350);
            Thread.sleep(1500);
            // 4.王者峡谷
            RootShellCmd.simulateTap(420, 380);
            Thread.sleep(500);
            // 5.入门
            RootShellCmd.simulateTap(420, 380);
            Thread.sleep(3000);

            // 一直挂机直到终止运行
            while (true) {
                // 6.点击开始匹配
                RootShellCmd.simulateTap(1060, 900);
                Log.i(LOG_TAG, "start match");
                Thread.sleep(1500);

                while (true) {
                    // 7.点击进入游戏
                    RootShellCmd.simulateTap(950, 850);
                    Thread.sleep(4000);
                    // 8.选择英雄界面
                    if (BitmapUtil.getBitmap().getPixel(1900, 10) == -14464100) {
                        Log.i(LOG_TAG, "enter success");
                        break;
                    }
                }
                // 9.选择英雄
                chooseHero();

                // 10.确定
                RootShellCmd.simulateTap(1750, 1020);

                // 11.进入游戏画面
                while (true) {
                    Bitmap bitmap = BitmapUtil.getBitmap();
                    // 血条绿色
                    if (bitmap.getPixel(950, 400) == -10694630 ||
                            bitmap.getPixel(955, 400) == -10694630 ||
                            bitmap.getPixel(960, 400) == -10694630) {
                        break;
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                }

                // 12.游戏内部操作
                runInGame();
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }

        } catch (InterruptedException e) {

        }
    }

    private void scroll() throws InterruptedException {
        RootShellCmd.simulateSwipe(heroListX + 3 * heroListOffsetX, heroListY + 3 * heroListOffsetY, heroListX, heroListY, 1000);
        Thread.sleep(1000);
    }

    private void chooseHero() throws InterruptedException {
        for (int i = 0, heroLength = hero.length; i < heroLength; i++) {
            int[] ints = hero[i];
            RootShellCmd.simulateTap(450, 550);
            Thread.sleep(2000);
            if (i == 0 && scroll) scroll();
            RootShellCmd.simulateTap(heroListX + ints[0] * heroListOffsetX, heroListY + ints[1] * heroListOffsetY);
            Thread.sleep(2000);
        }
    }

    public void runInGame() {
        if (goTime == 0) goTime = 19000;
        try {
            int temp1 = 2;
            while (temp1-- > 0) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                // 装备 + 1,2技能 + 装备
                RootShellCmd.simulateTap(200, 430);
                Thread.sleep(1);
                RootShellCmd.simulateTap(1320, 840);
                Thread.sleep(1);
                RootShellCmd.simulateTap(1440, 650);
                Thread.sleep(1);
                RootShellCmd.simulateTap(200, 430);
                Thread.sleep(1);
                // 出门（下路）
                RootShellCmd.simulateSwipe(300, 850, 530, 895, goTime);
                Thread.sleep(goTime);
                RootShellCmd.simulateSwipe(300, 850, 300, 700, 1500);
                Thread.sleep(1500);
                // 普攻
                int temp = 8;
                while (temp-- > 0) {
                    RootShellCmd.simulateTap(1740, 920, 200);
                    Thread.sleep(3000);
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                }
                // 回城
                RootShellCmd.simulateTap(960, 990);
                Thread.sleep(10000);
                RootShellCmd.simulateTap(960, 990);
                Thread.sleep(10000);
            }
            while (true) {
                RootShellCmd.simulateTap(200, 430);
                RootShellCmd.simulateTap(960, 990);
                Thread.sleep(10000);

                int bitmap = BitmapUtil.getBitmap().getPixel(1910, 1070);
                if (bitmap == -14474451) { // 开始匹配
                    break;
                } else if (bitmap == -16379875) { // 继续 + 继续 + 返回房间
                    int temp = 3;
                    while (temp-- > 0) {
                        RootShellCmd.simulateTap(960, 990);
                        Thread.sleep(2000);
                    }
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setGoTime(int goTime) {
        this.goTime = goTime;
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

    public void setHero(int[][] hero) {
        this.hero = hero;
    }

}
