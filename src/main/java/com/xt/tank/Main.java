package com.xt.tank;

import java.util.concurrent.TimeUnit;

/**
 * 测试主程序
 */
public class Main {
    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();

        // 初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            tankFrame.getEnemyTanks().add(new Tank(50 + i * 50, 200, Dir.DOWN, Group.BAD, tankFrame));
        }

        while (true) {
            // 暂停一会儿线程
            try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            // repaint()方法会自动调用paint()
            tankFrame.repaint();
        }
    }
}
