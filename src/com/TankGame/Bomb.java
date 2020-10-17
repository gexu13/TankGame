package com.TankGame;

public class Bomb {
    //爆炸发生的位置
    int x;
    int y;
    boolean bombExist = true;
    //爆炸持续时间
    int time = 8;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //爆炸时间减少
    public void timeCountDown() {
        if (this.time > 0) {
            time--;
        } else {
            this.bombExist = false;
        }
    }
}
