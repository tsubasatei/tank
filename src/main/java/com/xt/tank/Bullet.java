package com.xt.tank;

import lombok.Data;

import java.awt.*;

/**
 * 子弹类
 */
@Data
public class Bullet {
    private int x, y;
    private Dir dir;
    private TankFrame frame;
    private static final int SPEED = 10;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private boolean isAlive = true;

    public Bullet(int x, int y, Dir dir, TankFrame frame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.frame = frame;
    }

    // 发射子弹
    public void paint(Graphics g) {
        if (!isAlive) {
            this.frame.bullets.remove(this);
        }
        // 获取画笔的颜色, 改变颜色后，再设置回去
        Color color = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(color);
        move();
    }

    // 设置子弹方向
    public void move() {
        switch (dir) {
            case LEFT :
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
            default:
                break;
        }
        if (x < 0 || y < 0 || x > TankFrame.WIDTH || y > TankFrame.HEIGHT) {
            isAlive = false;
        }
    }
}
