package com.xt.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹
 */
public class Bullet {
    private int x, y;
    private Dir dir;
    private Group group;
    private TankFrame tankFrame;

    private boolean living = true;

    public static final int SPEED = 10;
    public static int WIDTH = 30;
    public static int HEIGHT = 30;

    private BufferedImage image;

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

    public boolean living() {
        return living;
    }

    // 绘制子弹
    public void paint(Graphics g) {
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
            int bX = tank.getX() + Tank.WIDTH / 2 - Explosion.WIDTH / 2;
            int bY = tank.getY() + Tank.HEIGHT / 2 - Explosion.HEIGHT / 2;
            this.tankFrame.getExplosions().add(new Explosion(bX, bY, this.tankFrame));
        }
    }

    // 死亡
    private void die() {
        living = false;
    }
}
