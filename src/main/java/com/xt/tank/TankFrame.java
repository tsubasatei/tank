package com.xt.tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * 坦克窗口
 */
public class TankFrame extends Frame {

    Tank tank = new Tank(200, 200, Dir.DOWN, this);
    List<Bullet> bullets = new ArrayList<>();
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setBackground(Color.BLACK);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            // 关闭窗口
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new MyKeyListener());
    }

    /**
     * 用双缓冲解决闪烁问题
     *  1. repaint - update
     *  2. 截获 update
     *  3. 首先把该画出来的东西（坦克， 子弹）先画在内存的图片中，图片大小和游戏画面一致
     *  4. 把内存中图片一次性画到屏幕上（内存的内容复制到显存）
     */
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    /**
     * 窗口需要重新回值的时候会自动调用paint方法
     * @param g ： 画笔
     */
    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量: " + this.bullets.size(), 20, 60);
        g.setColor(color);

        // 绘制坦克
        tank.paint(g);
        // 绘制子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
    }

    /**
     * 键盘监听事件
     */
    class MyKeyListener implements KeyListener {
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        // 键盘按下触发
        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println("keyPressed" + e);

            // 按键
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT: // 左键
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT: // 右键
                    bR = true;
                    break;
                case KeyEvent.VK_UP:  // 上键
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN: // 下键
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        // 键盘释放触发
        @Override
        public void keyReleased(KeyEvent e) {
//            System.out.println("keyReleased" + e);
//            x += 5;
//            repaint();
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL: // ctrl键 发射子弹
                    tank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        /**
         * 根据四个boolean值，确定移动方向
         */
        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                tank.setMoving(false);
            } else {
                tank.setMoving(true);
                if (bL) {
                    tank.setDir(Dir.LEFT);
                }
                if(bU) {
                    tank.setDir(Dir.UP);
                }
                if(bR) {
                    tank.setDir(Dir.RIGHT);
                }
                if(bD) {
                    tank.setDir(Dir.DOWN);
                }
            }

        }

    }
}
