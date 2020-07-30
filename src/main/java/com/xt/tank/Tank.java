package com.xt.tank;

import lombok.Data;

import java.awt.*;

/**
 * 坦克
 */
@Data
public class Tank {
    private int x = 200;
    private int y = 200; // 初始位置
    private Dir dir = Dir.DOWN;
    private TankFrame frame;
    private static final int SPEED = 10; // 速度
    private boolean moving = false; // 是否移动标志

    public Tank(int x, int y, Dir dir, TankFrame frame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.frame = frame;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 50, 50);
        g.setColor(color);

        move();
    }

    private void move() {
        if (moving) {
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
            }
        }
    }

    // 发射子弹
    public void fire() {
        frame.bullets.add(new Bullet(this.x, this.y, this.dir, this.frame));
    }
}
