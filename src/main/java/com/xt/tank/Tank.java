package com.xt.tank;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 坦克
 */
@AllArgsConstructor
public class Tank {
    private int x, y; // 初始位置
    private Dir dir; // 初始方向
    private Group group; // 分组
    private TankFrame tankFrame;

    public static final int SPEED = 1;
    private BufferedImage image;

    public static int WIDTH = 50;
    public static int HEIGHT = 50;

    private boolean isMoving = true;
    private boolean living = true;

    private Random random = new Random();

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getGroup() {
        return group;
    }

    // 绘制tank
    public void paint(Graphics g) {
        if (!living) this.tankFrame.getEnemyTanks().remove(this);
        switch (dir) {
            case LEFT:
                image = ResourceMgr.tankL;
                break;
            case RIGHT:
                image = ResourceMgr.tankR;
                break;
            case UP:
                image = ResourceMgr.tankU;
                break;
            case DOWN:
                image = ResourceMgr.tankD;
                break;
        }
        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        g.drawImage(image, x, y, null);

        move();
    }

    // 移动位置，改变 x、y
    private void move() {
        if (!isMoving) return;

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

        if (random.nextInt() > 8) this.fire();
    }

    // 发射子弹
    public void fire() {
        int bX = this.x + WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + HEIGHT/2 - Bullet.HEIGHT/2;
        tankFrame.getBullets().add(new Bullet(bX, bY, this.dir, this.group, this.tankFrame));
    }

    public void die() {
        living = false;
    }
}
