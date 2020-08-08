package com.xt.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹类
 */
public class Bullet {
    private int x, y;  // 初始位置
    private Dir dir;   // 方向
    private Group group;  // 分组
    private TankFrame tankFrame;  // tank war 窗口

    private boolean living = true;  // 子弹撞到tank就死了

    // 子弹的速度
    public static final int SPEED = PropertyMgr.getInt("bulletSpeed") != null ? PropertyMgr.getInt("bulletSpeed") : 10;
    // 子弹图片的宽和高
    public static int WIDTH = 30;
    public static int HEIGHT = 30;
    // 子弹图片
    private BufferedImage image;

    // 防注判断子弹是否还存活
    Rectangle rectangle = new Rectangle();

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
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

    // 绘制子弹
    public void paint(Graphics g) {
        // 子弹不存活，从tank窗口移除
        if (!living) {
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
        // 若子弹飞出窗口，则不存活了
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            living = false;
        }

        // 更新 rectangle
        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
    }

    // 判断 子弹 和 坦克 的碰撞
    public void collideWith(Tank tank) {
        if(this.group == tank.getGroup()) return;

        Rectangle tankR = tank.rectangle;
        if (rectangle.intersects(tankR)) { // 两矩形相交
            this.die();
            tank.die();
            int bX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int bY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            this.tankFrame.getExplosions().add(new Explode(bX, bY, this.tankFrame));
        }
    }

    // 死亡
    private void die() {
        living = false;
    }
}
