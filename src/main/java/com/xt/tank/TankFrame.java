package com.xt.tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克大战窗口
 */
public class TankFrame extends Frame {

    // 窗口大小，宽和高
    public static final int GAME_WIDTH = PropertyMgr.getInt("gameWidth") != null ? PropertyMgr.getInt("gameWidth") : 1080;
    public static final int GAME_HEIGHT = PropertyMgr.getInt("gameHeight") != null? PropertyMgr.getInt("gameHeight") : 960;

    // 我方坦克初始位置
    private int x = 200;
    private int y = 400;

    // 己方坦克
    private Tank tank = new Tank(x, y, Dir.DOWN, Group.GOOD, this); // 初始 Tank
    // 子弹列表
    private List<Bullet> bullets = new ArrayList<>();
    // 敌方坦克列表
    private List<Tank> enemyTanks = new ArrayList<>();
    // 爆炸组
    private List<Explode> explosions = new ArrayList<>();


    public TankFrame() throws HeadlessException {
        setBackground(Color.BLACK);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        /**
         * 在构造器中添加 窗口监听器 和 事件监听器
         */
        addWindowListener(new WindowAdapter() {
            // 关闭窗口
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new MyKeyListener());
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public List<Explode> getExplosions() {
        return explosions;
    }

    // 绘制
    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量: " + this.bullets.size(), 20, 60);
        g.drawString("敌人的数量: " + this.enemyTanks.size(), 20, 80);
        g.drawString("爆炸的数量: " + this.enemyTanks.size(), 20, 100);
        g.setColor(color);

        // 分别绘制tank 和 子弹
        tank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        // 绘制敌军坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(g);
        }

        // 绘制爆炸
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(g);
        }

        // 判断子弹和坦克是否相交
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemyTanks.size(); j++) {
                bullets.get(i).collideWith(enemyTanks.get(j));
            }
        }
    }

    /**
     * 用双缓冲解决闪烁问题
     * 1. repaint - update
     * 2. 截获 update
     * 3. 首先把该画出来的东西（坦克， 子弹）先画在内存的图片中，图片大小和游戏画面一致
     * 4. 把内存中图片一次性画到屏幕上（内存的内容复制到显存）
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

    // 键盘事件监听器
    class MyKeyListener implements KeyListener {
        // 方向键是否被按下
        private boolean bL = false;
        private boolean bR = false;
        private boolean bU = false;
        private boolean bD = false;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        // 键盘按下触发
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            // 控制tank方向
            setMainTankDir();
        }

        // 键盘弹起触发
        @Override
        public void keyReleased(KeyEvent e) {
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
                case KeyEvent.VK_CONTROL:  // ctrl 键弹起 发射子弹
                    tank.fire();
                    break;
                default:
                    break;
            }
            // 设置坦克方向
            setMainTankDir();
        }

        // 根据方向键，设置tank移动方向
        private void setMainTankDir() {
            if (!bL && !bR && !bU && !bD) tank.setMoving(false);
            else {
                tank.setMoving(true);
                if (bL) tank.setDir(Dir.LEFT);
                if (bR) tank.setDir(Dir.RIGHT);
                if (bU) tank.setDir(Dir.UP);
                if (bD) tank.setDir(Dir.DOWN);
            }
        }
    }
}
