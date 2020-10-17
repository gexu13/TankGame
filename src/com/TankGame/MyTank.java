package com.TankGame;

//创建自己操作的坦克并继承Tank类
class MyTank extends Tank {

    //使用super(x,y)继承父类的构造方法，给myTank的成员变量初始化
    public MyTank(int x, int y) {
        super(x, y);
    }

    //重写父类方法 开炮
    public void shot() {
        switch (this.direction) {
            case 0:
                shell = new Shell(x - 2, y - 35, 0);
                shellVector.add(shell);
                break;
            case 1:
                shell = new Shell(x - 2, y + 31, 1);
                shellVector.add(shell);
                break;
            case 2:
                shell = new Shell(x - 35, y - 2, 2);
                shellVector.add(shell);
                break;
            case 3:
                shell = new Shell(x + 31, y - 2, 3);
                shellVector.add(shell);
                break;

        }
        //启动线程
        //炮弹在shot里创建，所以炮弹的线程在这里启动。创建就启动运行。
        Thread threadShell = new Thread(shell);
        //每一颗炮弹都被start了一次
        threadShell.start();

    }

    //我的坦克向上移动
    public void moveUp() {
        y = y - speed;
    }

    //我的坦克向下移动
    public void moveDown() {
        y = y + speed;
    }

    //我的坦克向左移动
    public void moveLeft() {
        x = x - speed;
    }

    //我的坦克向右移动
    public void moveRight() {
        x = x + speed;
    }
}

