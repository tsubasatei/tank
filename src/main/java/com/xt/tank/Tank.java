package com.xt.tank;

import lombok.AllArgsConstructor;

import java.awt.*;

/**
 * 坦克
 */
@AllArgsConstructor
public class Tank {
    private int x, y; // 初始位置
    private Dir dir; // 初始方向
    private TankFrame tankFrame;

    public static final int SPEED = 5;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    private boolean isMoving = true;

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    // 绘制tank
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(x, y, WIDTH, HEIGHT); // 画矩形
        g.setColor(color);

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
    }

    // 发射子弹
    public void fire() {
        tankFrame.getBullets().add(new Bullet(this.x, this.y, this.dir, this.tankFrame));
    }
}
