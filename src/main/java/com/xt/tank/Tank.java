package com.xt.tank;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 坦克类
 */
@AllArgsConstructor
public class Tank {
    private int x, y; // 初始位置
    private Dir dir; // 初始方向
    private Group group; // 分组
    private TankFrame tankFrame; // 坦克窗口

    // 坦克的移动速度
    public static final int SPEED = PropertyMgr.getInt("tankSpeed") != null ? PropertyMgr.getInt("tankSpeed") : 5;
    // 坦克图片
    private BufferedImage image;
    // 坦克图片的宽
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    // 坦克图片的高
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    // 是否在移动
    private boolean isMoving = true;
    // 敌方坦克是否还活着
    private boolean living = true;

    private Random random = new Random();

    // 用于判断乙方是否碰到子弹
    Rectangle rectangle = new Rectangle();

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
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
        //
        if (this.group == Group.BAD && !living) this.tankFrame.getEnemyTanks().remove(this);

        switch (dir) {
            case LEFT:
                image = group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL;
                break;
            case RIGHT:
                image = group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR;
                break;
            case UP:
                image = group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU;
                break;
            case DOWN:
                image = group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD;
                break;
        }
        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        g.drawImage(image, x, y, null);

        move();
    }

    // 移动tank位置，改变 x、y
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

        // 敌方tank 随机发射子弹
        if (this.group == Group.BAD && random.nextInt(100) > 95) this.fire();
        // 敌方tank 随机移动方向
        if (this.group == Group.BAD && random.nextInt(100) > 95) randomDir();

        // 边界检测
        boundCheck();

        // 更新 rectangle
        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
    }

    // 边界检测，不让tank移出tank窗口
    private void boundCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH -2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2 ) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT -2;
    }

    // 随机方向移动
    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    // 发射子弹
    public void fire() {
        int bX = this.x + WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + HEIGHT/2 - Bullet.HEIGHT/2;
        tankFrame.getBullets().add(new Bullet(bX, bY, this.dir, this.group, this.tankFrame));
    }

    // tank 被子弹打死
    public void die() {
        living = false;
    }
}
