package com.TankGame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import java.text.NumberFormat;

public class Audio extends Thread{
    String fileName;

    public Audio(String fileName){
        this.fileName = fileName;
    }

    public void run() {
        try {
            File audioFile = new File(fileName);
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(audioFile);

           // System.out.println("此音频的格式为：" + audioInputStream.getFormat()
                   // + "\n此音频的流长度为：" + audioInputStream.getFrameLength()
                  //  + "帧\n此文件共有 " + fileName.length() + " 字节");

            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open();
            line.start();
            int length = 0;
            byte[] buffer = new byte[512];
            while ((length = audioInputStream.read(buffer)) != -1) {
                // if (audioInputStream.available() > 8459032) {
                // audioInputStream.skip(length);
                // continue;
                // }
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
//                System.out
//                        .println(numberFormat.format(((float) file.length() - (float) audioInputStream
//                                .available()) / (float) file.length() * 100)
//                                + "%");

                line.write(buffer, 0, length);
            }
            line.drain();
            line.close();
            audioInputStream.close();

        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}



//public class Audio extends Thread{
//    String fileName ;
//
//    public Audio(String file){
//        this.fileName=file;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("播放了马？");
//        File audioFile = new File(fileName);
//        //音频字节流
//        AudioInputStream audioInputStream = null;
//        try {
//            //输入
//            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//            return;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        AudioFormat format = audioInputStream.getFormat();
//        SourceDataLine audioLine = null;
//
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format ,AudioSystem.NOT_SPECIFIED );
//
//        try {
//            audioLine = (SourceDataLine) AudioSystem.getLine(info);
//            audioLine.open(format);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        audioLine.start();
//        int read = 0;
//        byte[] audioSource = new byte[1024];
//
//        try {
//            while(read != -1){
//                 read = audioInputStream.read(audioSource,0,audioSource.length);}
//                if(read>=0){
//                    audioLine.write(audioSource,0,read);
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//            finally {
//                audioLine.drain();
//                audioLine.close();
//            }
//    }
//}
