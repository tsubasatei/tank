package com.xt.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 */
public class Explosion {
    private int x, y;
    private TankFrame tankFrame;
    private BufferedImage image = ResourceMgr.explosions[0];
    private boolean living = true;

    public static int WIDTH = ResourceMgr.explosions[0].getWidth();
    public static int HEIGHT = ResourceMgr.explosions[0].getHeight();

    private int step = 0;

    public Explosion(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public boolean living() {
        return living;
    }

    // 绘制子弹
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explosions[step++], x, y, null);
        if (step >= ResourceMgr.explosions.length) {
            step = 0;
            living = false;
        }
    }
}
