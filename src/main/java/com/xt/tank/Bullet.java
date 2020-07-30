package com.xt.tank;

import java.awt.*;

/**
 * 子弹
 */
public class Bullet {
    private int x, y;
    private Dir dir;
    private TankFrame tankFrame;

    private boolean isAlive = true;

    public static final int SPEED = 10;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

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
        Color color = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT); // 画圆形
        g.setColor(color);

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
