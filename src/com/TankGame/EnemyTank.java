package com.TankGame;

import java.util.Vector;

//创建敌人坦克并继承Tank类
class EnemyTank extends Tank implements Runnable {
    //创建一个敌人坦克的集合，将MyPanel里创建的敌人坦克传入(每一台敌人坦克都有enemyTanks这个集合）
    Vector<EnemyTank> enemyTanks = new Vector<>();

    //使用super(x,y)继承父类的构造方法，给enemyTank的成员变量初始化
    public EnemyTank(int x, int y) {
        super(x, y);
    }
    //将MyPanel中的坦克传入Vector中
    //（使enemyTank类中的Vector<enemyTank>与MyPanel中的enemyTankVector在内存中指向同一个位置）
    public void getEnemyTanks(Vector<EnemyTank> enemyTankVector){
        this.enemyTanks = enemyTankVector;
    }

    //敌人坦克也要开炮
    @Override
    //敌人坦克运动，坐标变化，产生炮弹
    public void run() {
        //System.out.println("碰撞时enemyTanks size =" + enemyTanks.size());
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (this.direction) {
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if (y - 15 > 0 && !this.isContact()) {
                            y = y - 1;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (y + 15 < 600 && !this.isContact()) {
                            y = y + 1;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (x - 15 > 0 && !this.isContact()) {
                            x = x - 1;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (x + 15 < 800 && !this.isContact()) {
                            x = x + 1;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            //让坦克产生一个新方向
            this.direction = (int) (Math.random() * 4);
            //如果敌人坦克被炮弹击中 敌人坦克就停止运动。
            if (this.tankExist == false) {
                break;
            }

            //产生敌人的炮弹
            //如果敌人坦克存在就添加子弹
            if (this.tankExist) {
                if (this.shellVector.size() < 5) {
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
                    //创建完后，start新添加的敌人炮弹
                    Thread thread = new Thread(shell);
                    thread.start();
                }
            }
        }
    }

    //敌人坦克碰撞
    public boolean isContact() {
        boolean result = false;
        //"我"的方向
        switch (this.direction) {
            //"我"朝上
            case 0:
                //取出enemyTank集合中传入的MyPanel中创建的敌人坦克
                for (int i = 0; i < this.enemyTanks.size(); i++) {
                    EnemyTank enemyTank = this.enemyTanks.get(i);
                    //如果坦克不等于它本身
                    if (enemyTank != this) {
                        //判断是否发生碰撞
                        if (this.getX() - 15 >= enemyTank.getX() - 15 && this.getX() - 15 <= enemyTank.getX() + 15 &&
                                this.getY() - 15 <= enemyTank.getY() + 15 && this.getY() - 15 >= enemyTank.getY() - 15) {
                            result = true;
                        }
                        if (this.getX() + 15 >= enemyTank.getX() - 15 && this.getX() + 15 <= enemyTank.getX() + 15 &&
                                this.getY() - 15 <= enemyTank.getY() + 15 && this.getY() - 15 >= enemyTank.getY() - 15) {
                            result = true;
                        }
                    }
                }
                break;
            //"我"朝下
            case 1:
                //取出enemyTank集合中传入的MyPanel中创建的敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //如果坦克不等于它本身
                    if (enemyTank != this) {
                        //判断是否发生碰撞
                        if (this.getX() - 15 >= enemyTank.getX() - 15 && this.getX() - 15 <= enemyTank.getX() + 15 &&
                                this.getY() + 15 >= enemyTank.getY() - 15 && this.getY() + 15 <= enemyTank.getY() + 15) {
                            result = true;
                        }
                        if (this.getX() + 15 >= enemyTank.getX() - 15 && this.getX() + 15 <= enemyTank.getX() + 15 &&
                                this.getY() + 15 >= enemyTank.getY() - 15 && this.getY() + 15 <= enemyTank.getY() + 15) {
                            result = true;
                        }
                    }
                }
                break;
            //"我"朝左
            case 2:
                //取出enemyTank集合中传入的MyPanel中创建的敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //如果坦克不等于它本身
                    if (enemyTank != this) {
                        //判断是否发生碰撞
                        if (this.getX() - 15 <= enemyTank.getX() + 15 && this.getX() - 15 >= enemyTank.getX() - 15
                                && this.getY() - 15 >= enemyTank.getY() - 15 && this.getY() - 15 <= enemyTank.getY() + 15) {
                            result = true;
                        }
                        if (this.getX() - 15 <= enemyTank.getX() + 15 && this.getX() - 15 >= enemyTank.getX() - 15
                                && this.getY() + 15 >= enemyTank.getY() - 15 && this.getY() + 15 <= enemyTank.getY() + 15) {
                            result = true;
                        }
                    }
                }
                break;
            //"我"朝右
            case 3:
                //取出enemyTank集合中传入的MyPanel中创建的敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //如果坦克不等于它本身
                    if (enemyTank != this) {
                        //判断是否发生碰撞
                        if (this.getX() + 15 >= enemyTank.getX() - 15 && this.getX() + 15 <= enemyTank.getX() + 15
                                && this.getY() - 15 >= enemyTank.getY() - 15 && this.getY() - 15 <= enemyTank.getY() + 15) {
                            result = true;
                        }
                        if (this.getX() + 15 >= enemyTank.getX() - 15 && this.getX() + 15 <= enemyTank.getX() + 15
                                && this.getY() + 15 >= enemyTank.getY() - 15 && this.getY() + 15 <= enemyTank.getY() + 15) {
                            result = true;
                        }
                    }
                }
                break;
        }
        return result;
    }
}