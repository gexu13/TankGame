package com.TankGame;

import java.util.Vector;

class Tank {
    int x;
    int y;
    //坦克的颜色
    int color;
    //方向：0表示上，1表示下，2表示左，3表示右。
    int direction;
    //坦克的速度
    int speed = 10;
    //能发射的炮弹数目
    int shellNum = 5;
    boolean tankExist = true;
    //引用类型，坦克的炮弹
    Vector<Shell> shellVector = new Vector<>();
    Shell shell;

    //Shell shell;
    //传入x,y坐标
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //坦克开炮的方法
    public void shot() {
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}













