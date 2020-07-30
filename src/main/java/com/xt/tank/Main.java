package com.xt.tank;

import java.util.concurrent.TimeUnit;

/**
 * 测试主程序
 */
public class Main {
    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();
        while (true) {
            // 暂停一会儿线程
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            // repaint()方法会自动调用paint()
            tankFrame.repaint();
        }
    }
}
