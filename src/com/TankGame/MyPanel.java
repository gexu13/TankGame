package com.TankGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

//炮弹是不会按键盘飞行的，所以需要不断刷新myPanel来让炮弹产生飞行效果。
class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    MyTank myTank;
    //给敌人的坦克创建一个Vector集合
    Vector<EnemyTank> enemyTankVector = new Vector<EnemyTank>();
    int enemySize = 5;
    //定义爆炸集合
    Vector<Bomb> bombs;
    //定义四张爆炸图片
    Image image1;
    Image image2;
    Image image3;
    Image image4;

    public MyPanel(String flag) {

        //创建bombs集合
        bombs = new Vector<>();
        //创建四张爆炸图片
        //image4=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("src/com/TankGame4/bomb4.jpg"));
        //image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("\bomb3.jpg"));
        //image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("\bomb2.jpg"));
        //image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("\bomb1.jpg"));
        try {
            image4 = ImageIO.read(new File("resources/pics/bomb4.jpg"));
            image3 = ImageIO.read(new File("resources/pics/bomb3.jpg"));
            image2 = ImageIO.read(new File("resources/pics/bomb2.jpg"));
            image1 = ImageIO.read(new File("resources/pics/bomb1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //开始新游戏
        if(flag.equals("new game")){
        //创建我的坦克
            myTank = new MyTank(400, 550);

            //创建敌人坦克并放入Vector集合中
            //创建敌人坦克的同时，也创建敌人的炮弹
            for (int i = 0; i < enemySize; i++) {
                EnemyTank enemyTank = new EnemyTank((i + 1) * 120, i + 30);
                enemyTank.setColor(1);
                enemyTank.setDirection(1);
                this.enemyTankVector.add(enemyTank);
                //把创建的敌人坦克传入判断敌人坦克是否碰撞的方法中
                //将创建的敌人坦克Vector传回enemyTank类中的Vector
                enemyTank.getEnemyTanks(enemyTankVector);
                //把创建的敌人坦克启动线程
                Thread thread = new Thread(enemyTank);
                //每创建一台敌人坦克，start一次，这样每一台敌人坦克都会自己移动。
                thread.start();
                //创建敌人炮弹
                Shell enemyShell = new Shell(enemyTank.getX() - 2, enemyTank.getY() + 31, enemyTank.direction);
                //将敌人炮弹加入到enemyTank.shellVector中。
                enemyTank.shellVector.add(enemyShell);
                //将每一个炮弹start一次
                Thread thread1 = new Thread(enemyShell);
                thread1.start();
            }
        }
        //恢复游戏
        else if(flag.equals("recover game")){
            //创建我的坦克
            myTank = new MyTank(Recorder.myTankCoordinate.x, Recorder.myTankCoordinate.y);
            myTank.setDirection(Recorder.myTankCoordinate.direction);

            //创建敌人坦克并放入Vector集合中
            //创建敌人坦克的同时，也创建敌人的炮弹
            for (int i = 0; i < Recorder.EnemyTankCoordinateVector.size(); i++) {
                EnemyTank enemyTank = new EnemyTank(Recorder.EnemyTankCoordinateVector.get(i).x,
                        Recorder.EnemyTankCoordinateVector.get(i).y);
                enemyTank.setColor(1);
                enemyTank.setDirection(Recorder.EnemyTankCoordinateVector.get(i).direction);
                this.enemyTankVector.add(enemyTank);
                //把创建的敌人坦克传入判断敌人坦克是否碰撞的方法中
                //将创建的敌人坦克Vector传回enemyTank类中的Vector
                enemyTank.getEnemyTanks(enemyTankVector);
                //把创建的敌人坦克启动线程
                Thread thread = new Thread(enemyTank);
                //每创建一台敌人坦克，start一次，这样每一台敌人坦克都会自己移动。
                thread.start();
                //创建敌人炮弹
                Shell enemyShell = new Shell(enemyTank.getX() - 2, enemyTank.getY() + 31, enemyTank.direction);
                //将敌人炮弹加入到enemyTank.shellVector中。
                enemyTank.shellVector.add(enemyShell);
                //将每一个炮弹start一次
                Thread thread1 = new Thread(enemyShell);
                thread1.start();
            }
        }
        //播放音频
        Audio audioStart = new Audio("resources/audio/start.wav");
        audioStart.start();

    }
    //方法覆盖override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 600);
        //画bomb爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.bombExist) {
                //开始画
                if (bomb.time > 6) {
                    g.drawImage(image4, bomb.x, bomb.y, 30, 30, this);
                } else if (bomb.time > 4) {
                    g.drawImage(image3, bomb.x, bomb.y, 30, 30, this);
                } else if (bomb.time > 2) {
                    g.drawImage(image2, bomb.x, bomb.y, 30, 30, this);
                } else if (bomb.time > 0) {
                    g.drawImage(image1, bomb.x, bomb.y, 30, 30, this);
                }
                bomb.timeCountDown();
                //当time<0时，爆炸就结束了，不再绘制爆炸图片
                if (!bomb.bombExist) {
                    bombs.remove(bomb);
                }


            }
        }

        //画游戏信息
        this.GameInfo(g);

        //画我的坦克
        if (myTank.tankExist) {
            this.drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }
        //每一台敌人坦克，都有一个独立的enemyTank.shellVector
        //画敌人的坦克和炮弹
        for (int i = 0; i < enemyTankVector.size(); i++) {
            EnemyTank enemyTank = enemyTankVector.get(i);
            if (enemyTank.tankExist) {
                //画敌人坦克
                this.drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                //画敌人炮弹
                for (int j = 0; j < enemyTank.shellVector.size(); j++) {
                    Shell enemyShell = enemyTank.shellVector.get(j);
                    //System.out.println("enemyTank.shellVector.size()= 第" + i + "台" + enemyTank.shellVector.size());
                    //如果炮弹存在，就画出来
                    if (enemyShell != null || enemyShell.shellExist) {
                        g.setColor(Color.white);
                        g.fillOval(enemyShell.getX(), enemyShell.getY(), 4, 4);
                    }
                    //如果不存在，就把它从敌人炮弹集合中删除
                    if (!enemyShell.shellExist) {
                        enemyTank.shellVector.remove(j);
                    }
                }
            }
        }
        //画我的坦克的炮弹
        for (int i = 0; i < myTank.shellVector.size(); i++) {
            Shell tankShell = myTank.shellVector.get(i);
            if (tankShell != null || tankShell.shellExist) {
                g.setColor(Color.white);
                g.fillOval(tankShell.getX(), tankShell.getY(), 4, 4);
            }
            //碰墙后或击中敌人坦克后将炮弹从集合中删除
            if (!tankShell.shellExist) {
                myTank.shellVector.remove(tankShell);


            }
        }
    }
    //画游戏信息
    public void GameInfo(Graphics g){
        //画显示的坦克(不参与战斗）
        //我的坦克
        this.drawTank(850,50,g,0,0);
        //敌人坦克
        this.drawTank(850, 150 , g, 0,1);
        //画我的剩余生命数
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.BOLD,13));
        g.drawString("我的剩余生命/Remaining Lives",880,45);
        //画剩余敌人数
        g.drawString("剩余敌人数量/Enemies Left",880,145);
        //写我的生命数值
        g.drawString("x "+Recorder.getMyLives(),900,65);
        //写剩余的敌人数
        g.drawString("x "+Recorder.getEnemyLives(),900,165);
        //画我击杀了多少敌人坦克
        this.drawTank(870, 540 , g, 0,1);
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("x "+ Recorder.getMyTotalScore(),900,550);
        g.drawString("我的总分数/My Total Scores",810,500);
    }

    //画坦克
    //写一个成员方法来画坦克
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        //判断方向direction=0，坦克向上
        if (direction == 0) {
            //敌我坦克六个轮子都是灰色的
            g.setColor(Color.GRAY);
            g.fillOval(x - 19, y - 14, 8, 8);
            g.fillOval(x - 19, y - 4, 8, 8);
            g.fillOval(x - 19, y + 6, 8, 8);
            g.fillOval(x + 11, y - 14, 8, 8);
            g.fillOval(x + 11, y - 4, 8, 8);
            g.fillOval(x + 11, y + 6, 8, 8);

            //type0是玩家坦克myTank
            if (type == 0) {
                g.setColor(Color.orange);
            }
            //type1是敌人坦克enemyTank
            if (type == 1) {
                g.setColor(Color.green);
            }
            //坦克机身
            g.fill3DRect(x - 15, y - 15, 30, 30, true);
            //画坦克上身
            g.fill3DRect(x - 7, y - 7, 15, 15, false);
            //画炮管
            g.fill3DRect(x - 3, y - 32, 6, 25, false);
        }
        //坦克向下
        else if (direction == 1) {
            //敌我坦克六个轮子都是灰色的
            g.setColor(Color.GRAY);
            g.fillOval(x - 19, y - 14, 8, 8);
            g.fillOval(x - 19, y - 4, 8, 8);
            g.fillOval(x - 19, y + 6, 8, 8);
            g.fillOval(x + 11, y - 14, 8, 8);
            g.fillOval(x + 11, y - 4, 8, 8);
            g.fillOval(x + 11, y + 6, 8, 8);
            //type0是玩家坦克myTank
            if (type == 0) {
                g.setColor(Color.orange);
            }
            //type1是敌人坦克enemyTank
            if (type == 1) {
                g.setColor(Color.green);
            }
            //坦克机身
            g.fill3DRect(x - 15, y - 15, 30, 30, true);
            //画坦克上身
            g.fill3DRect(x - 7, y - 7, 15, 15, false);
            //画炮管
            g.fill3DRect(x - 3, y + 7, 6, 25, false);

        }
        //坦克向左
        else if (direction == 2) {
            //敌我坦克六个轮子都是灰色的
            g.setColor(Color.GRAY);
            g.fillOval(x - 14, y - 19, 8, 8);
            g.fillOval(x - 4, y - 19, 8, 8);
            g.fillOval(x + 6, y - 19, 8, 8);
            g.fillOval(x - 14, y + 11, 8, 8);
            g.fillOval(x - 4, y + 11, 8, 8);
            g.fillOval(x + 6, y + 11, 8, 8);
            //type0是玩家坦克myTank
            if (type == 0) {
                g.setColor(Color.orange);
            }
            //type1是敌人坦克enemyTank
            if (type == 1) {
                g.setColor(Color.green);
            }
            //坦克机身
            g.fill3DRect(x - 15, y - 15, 30, 30, true);
            //画坦克上身
            g.fill3DRect(x - 7, y - 7, 15, 15, false);
            //画炮管
            g.fill3DRect(x - 32, y - 3, 25, 6, false);
        }
        //坦克向右
        else if (direction == 3) {
            //敌我坦克六个轮子都是灰色的
            g.setColor(Color.GRAY);
            g.fillOval(x - 14, y - 19, 8, 8);
            g.fillOval(x - 4, y - 19, 8, 8);
            g.fillOval(x + 6, y - 19, 8, 8);
            g.fillOval(x - 14, y + 11, 8, 8);
            g.fillOval(x - 4, y + 11, 8, 8);
            g.fillOval(x + 6, y + 11, 8, 8);
            //type0是玩家坦克myTank
            if (type == 0) {
                g.setColor(Color.orange);
            }
            //type1是敌人坦克enemyTank
            if (type == 1) {
                g.setColor(Color.green);
            }
            //坦克机身
            g.fill3DRect(x - 15, y - 15, 30, 30, true);
            //画坦克上身
            g.fill3DRect(x - 7, y - 7, 15, 15, false);
            //画炮管
            g.fill3DRect(x + 7, y - 3, 25, 6, false);
        }
    }


    //我的炮弹击中敌人坦克
    public void hitEnemyTank(Tank tank, Shell shell) {
        if (shell.getX() > tank.getX() - 15 && shell.getX() < tank.getX() + 15
                && shell.getY() > tank.getY() - 15 && shell.getY() < tank.getY() + 15) {
            //击中了坦克没了，炮弹也没了
            tank.tankExist = false;
            shell.shellExist = false;
            //坦克被击中就会产生爆炸效果
            Bomb bomb = new Bomb(tank.getX() - 15, tank.getY() - 15);
            bombs.add(bomb);
            //敌人的坦克被击中了，剩余敌人减1
            Recorder.enemyLivesDown();
            //敌人的坦克被击中了，MyScores加1
            Recorder.gainMyScore();
            Audio audioEnemyTankBoom = new Audio("resources/audio/boom.wav");
            audioEnemyTankBoom.start();

        }


    }
    //敌人炮弹击中我的坦克
    public void hitMyTank(Tank tank, Shell shell) {
        if (shell.getX() > tank.getX() - 15 && shell.getX() < tank.getX() + 15
                && shell.getY() > tank.getY() - 15 && shell.getY() < tank.getY() + 15) {
            //击中了坦克没了，炮弹也没了
            tank.tankExist = false;
            shell.shellExist = false;
            //坦克被击中就会产生爆炸效果
            Bomb bomb = new Bomb(tank.getX() - 15, tank.getY() - 15);
            bombs.add(bomb);
            //我的坦克被击中了，我的生命剩余值减1
            Recorder.myLivesDown();
            Audio audioMyTankBoom = new Audio("resources/audio/boom.wav");
            audioMyTankBoom.start();

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    //上下左右键控制坦克移动,发射炮弹
    public void keyPressed(KeyEvent e) {
        //向上走
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.myTank.setDirection(0);
            //向上走
            this.myTank.moveUp();
            Audio audioMyTankMove = new Audio("resources/audio/move.wav");
            audioMyTankMove.start();

        }
        //向下走
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.myTank.setDirection(1);
            //向下走
            this.myTank.moveDown();
            Audio audioMyTankMove = new Audio("resources/audio/move.wav");
            audioMyTankMove.start();

        }
        //向左走
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.myTank.setDirection(2);
            //向左走
            this.myTank.moveLeft();
            Audio audioMyTankMove = new Audio("resources/audio/move.wav");
            audioMyTankMove.start();

        }
        //向右走
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.myTank.setDirection(3);
            //向右走
            this.myTank.moveRight();
            Audio audioMyTankMove = new Audio("resources/audio/move.wav");
            audioMyTankMove.start();
        }
        //开炮
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //炮弹发射枚数
            if (myTank.shellVector.size() < myTank.shellNum) {
                //如果我的坦克还活着，就能开炮
                if (myTank.tankExist) {
                    this.myTank.shot();
                    Audio audioMyTankShot = new Audio("resources/audio/biu.wav");
                    audioMyTankShot.start();
                }
            }
        }
        this.repaint();
    }



    @Override
    //myPanel线程 每隔100ms刷新一次myPanel
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断敌人坦克是否被击中
            //遍历每一颗炮弹
            for (int i = 0; i < this.myTank.shellVector.size(); i++) {
                Shell shell = this.myTank.shellVector.get(i);
                if (shell.shellExist) {
                    //遍历每一台敌人坦克
                    for (EnemyTank enemyTank : this.enemyTankVector) {
                        //判断敌人坦克是否还存活
                        if (enemyTank.tankExist) {
                            this.hitEnemyTank(enemyTank, shell);
                        }
                    }
                }
            }
            //判断我的坦克是否被击中
            //遍历每一台敌人坦克
            for (int i = 0; i < enemyTankVector.size(); i++) {
                EnemyTank enemyTank = enemyTankVector.get(i);
                if (enemyTank.tankExist) {
                    //遍历敌人坦克的每一颗炮弹
                    for (int j = 0; j < enemyTank.shellVector.size(); j++) {
                        Shell enemyShell = enemyTank.shellVector.get(j);
                        if (enemyShell.shellExist) {
                            //判断我的坦克是否还存活
                            if (myTank.tankExist) {
                                this.hitMyTank(myTank, enemyShell);

                            }
                        }
                    }
                }
            }
            this.repaint();
            //System.out.println("还有多少敌人坦克"+enemyTankVector.size());
        }
    }
}
