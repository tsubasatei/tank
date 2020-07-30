package com.xt.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹
 */
public class Bullet {
    private int x, y;
    private Dir dir;
    private TankFrame tankFrame;

    private boolean isAlive = true;

    public static final int SPEED = 10;
    public static int WIDTH = 30;
    public static int HEIGHT = 30;

    private BufferedImage image;

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public boolean isAlive() {
        return isAlive;
    }

    // 绘制子弹
    public void paint(Graphics g) {
        if (!isAlive) {
            this.tankFrame.getBullets().remove(this);
            return;
        }
        switch (dir) {
            case LEFT:
                image = ResourceMgr.bulletL;
                break;
            case RIGHT:
                image = ResourceMgr.bulletR;
                break;
            case UP:
                image = ResourceMgr.bulletU;
                break;
            case DOWN:
                image = ResourceMgr.bulletD;
                break;
        }
        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        g.drawImage(image, x, y, null);

        move();
    }

    // 设置子弹移动方向, 并判断是否还活着
    private void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            isAlive = false;
        }
    }

}
