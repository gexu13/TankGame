package com.TankGame;

import javax.swing.*;
import java.awt.*;

public class StageOnePanel extends JPanel implements Runnable {
    int times=0;
    public void paint(Graphics graphics){
        super.paint(graphics);
        graphics.fillRect(0,0,800,600);
        if(times%2==0) {
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Arial", Font.BOLD, 50));
            graphics.drawString("STAGE:1", 300, 300);
            graphics.setFont(new Font("Arial", Font.BOLD, 25));
            graphics.drawString("请按空格开始游戏", 310, 330);
            graphics.drawString("Please press SPACE to start the game", 200, 360);
        }
    }

    @Override
    public void run() {
        while(true){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        times++;
        repaint();
        }
    }
}
