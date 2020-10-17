package com.TankGame;

public class Shell implements Runnable {
    //炮弹的初始坐标
    int x;
    int y;
    //炮弹飞行速度
    int speed = 5;
    //炮弹的飞行方向
    int direction = 0;
    //炮弹是否还存在
    boolean shellExist = true;

    //构造炮弹
    public Shell(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    //炮弹飞行线程
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (this.direction) {
                case 0:
                    y = y - speed;
                    break;
                case 1:
                    y = y + speed;
                    break;
                case 2:
                    x = x - speed;
                    break;
                case 3:
                    x = x + speed;
                    break;
            }
            //System.out.println("x= "+x+" y="+y);
            //炮弹超出边界，就停止运动
            if (x < 0 || x > 800 || y < 0 || y > 600) {
                this.shellExist = false;
                //System.out.println("子弹头"+this.shellExist);
                break;
            }
        }
    }
}
