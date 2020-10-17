package com.TankGame;
/*
1：画出坦克
2：实现坦克上下左右移动
3：实现坦克连发开炮
4：消灭敌人，产生爆炸效果
5：敌人坦克移动并可以连发开炮
6：敌人坦克物理碰撞
7：游戏初始化面板
8：显示我的生命值与敌人的剩余数
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class TankGame extends JFrame implements ActionListener , KeyListener {
    MyPanel mp;
    StageOnePanel stageOnePanel;
    JMenuBar jMenuBar;
    JMenu jMenu1;
    JMenu jMenu2;
    JMenuItem jMenuItem1;
    JMenuItem jMenuItem2;
    JMenuItem jMenuItem3;
    JMenuItem jMenuItem4;
    JMenuItem jMenuItem5;

    //构造方法
    public TankGame() {
        //初始化stage1面板
        stageOnePanel= new StageOnePanel();
        //初始化菜单栏
        jMenuBar = new JMenuBar();
        jMenu1 = new JMenu("坦克游戏/TankGame");
        jMenu1.setMnemonic('n');
        jMenu2 = new JMenu("保存/Save");
        jMenu2.setMnemonic('s');
        jMenuItem1 = new JMenuItem("开始游戏/Start");
        jMenuItem2 = new JMenuItem("重新开始/Restart");
        jMenuItem3 = new JMenuItem("退出游戏/Exit");
        jMenuItem4 = new JMenuItem(("保存/Save"));
        jMenuItem5 = new JMenuItem("恢复/Reload");

        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu2.add(jMenuItem4);
        jMenu2.add(jMenuItem5);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);

        //将jMenuItem设置为事件源
        jMenuItem1.setActionCommand("start the game");
        jMenuItem2.setActionCommand("restart");
        jMenuItem3.setActionCommand("exit");
        jMenuItem4.setActionCommand(("save"));
        jMenuItem5.setActionCommand("reload");

        //注册监听
        this.addKeyListener(this);
        jMenuItem1.addActionListener(this);
        jMenuItem2.addActionListener(this);
        jMenuItem3.addActionListener(this);
        jMenuItem4.addActionListener(this);
        jMenuItem5.addActionListener(this);
        //设置JFrame
        this.add(stageOnePanel);
        this.setJMenuBar(jMenuBar);
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //Start stage1Panel闪烁的线程
        Thread thread = new Thread(stageOnePanel);
        thread.start();
    }
    public static void main(String[] args) {
        TankGame tankGame = new TankGame();
    }

    //开始游戏方法
    public void startTheGame(){
        Recorder.recoverMyScores();
        //注册监听
        this.addKeyListener(mp);
        //启动myPanel进程
        Thread threadPanel = new Thread(mp);
        threadPanel.start();
        //启动MyPanel之前要把之前的StageOnePanel给remove掉
        this.remove(stageOnePanel);
        //用户点击一次开始游戏后，就不能再一次点开始游戏了
        jMenuItem1.removeActionListener(this);
        //用户按空格一次开始游戏后，就不能再按空格开始游戏了
        this.removeKeyListener(this);
        //设置JFrame
        this.add(mp);
        this.setJMenuBar(jMenuBar);
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    //点击开始游戏，重新游戏，退出游戏
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果用户点击Start，则进入游戏（进入MyPanel）
        if(e.getActionCommand().equals("start the game")){
            mp = new MyPanel("new game");
            this.startTheGame();
        }
        //重新开始游戏
        else if(e.getActionCommand().equals("restart")){
            this.remove(mp);
            mp = new MyPanel("new game");
            this.startTheGame();
            Recorder.resetRecord();
            }

        //退出游戏
        else if(e.getActionCommand().equals("exit")){
            //将MyPanel上的敌人坦克Vector传给recorder
            Recorder.enemyTanks = mp.enemyTankVector;
            //将MyPanel上的MyTank传入Recorder
            Recorder.myTank=mp.myTank;
            //保存我的分数,我的坐标和敌人的坐标
            Recorder.saveMyScoresAndTankCoordinates();
            System.exit(0);
        }

        //保存游戏
        else if (e.getActionCommand().equals("save")){
            //将MyPanel上的敌人坦克Vector传给Recorder
            Recorder.enemyTanks = mp.enemyTankVector;
            //将MyPanel上的MyTank传入Recorder
            Recorder.myTank=mp.myTank;
            //保存我的分数,我的坐标和敌人的坐标
            Recorder.saveMyScoresAndTankCoordinates();

        }
        //重载游戏/恢复游戏
        else if (e.getActionCommand().equals("reload")){
            this.remove(mp);
            //恢复已保存的我的总分数和敌人坦克的坐标
            Recorder.recoverMyScoresAndTankCoordinates();
            mp = new MyPanel("recover game");
            this.startTheGame();

        }
    }

    //按空格开始游戏
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            mp = new MyPanel("new game");
            this.startTheGame();
        }
    }
    //没用到
    @Override
    public void keyReleased(KeyEvent e) {

    }
    //没用到
    @Override
    public void keyTyped(KeyEvent e) {
    }
}




