package com.xt.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸类
 */
public class Explode {
    private int x, y;  // 初始位置
    private TankFrame tankFrame;
    private static BufferedImage image = ResourceMgr.explosions[0]; // 爆炸图片

    public static int WIDTH = ResourceMgr.explosions[0].getWidth();
    public static int HEIGHT = ResourceMgr.explosions[0].getHeight();

    private int step = 0; //

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    // 绘制爆炸
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explosions[step++], x, y, null);
//        image = ResourceMgr.explosions[step];
//        WIDTH = image.getWidth();
//        HEIGHT = image.getHeight();
        if (step >= ResourceMgr.explosions.length) {
            this.tankFrame.getExplosions().remove(this);
        }
    }
}
