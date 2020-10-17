package com.TankGame;

import java.io.*;
import java.util.Vector;

public class Recorder {

    //类变量
    private static int myLives = 5 ;
    private static int enemyLives = 20;
    private static int myTotalScore = 0;


    private static FileWriter fileWriter = null;
    private static BufferedWriter bufferedWriter = null;
    private static FileReader fileReader = null;
    private static BufferedReader bufferedReader = null;
    //把MyPanel中的EnemyTanksVector传入
    static Vector<EnemyTank> enemyTanks = new Vector<>();
    //把文件中读取到的点放入EnemyCoordinate的Vector中
    static  Vector<TankCoordinate> EnemyTankCoordinateVector = new Vector<>();
    //把我的坦克坐标也传入
    static  MyTank myTank ;
    static  TankCoordinate myTankCoordinate ;
    //类方法
    //保存我的成绩和敌人的坐标
    public static void saveMyScoresAndTankCoordinates(){
        try {
            fileWriter = new FileWriter("/src/com/TankGame/Document/MyScoresAndTankCoordinates");
            bufferedWriter = new BufferedWriter(fileWriter);

            //第一行：把我的总分数写入指定的文件中
            bufferedWriter.write(Recorder.getMyTotalScore()+"\n");
            //第二行：把我的生命值写入指定文件中
            bufferedWriter.write(Recorder.getMyLives()+"\n");
            //第三行：把剩余敌人坦克数也写入指定文件中
            bufferedWriter.write(Recorder.getEnemyLives()+"\n");
            //第四行：把我的坦克坐标也写入文件
            if(myTank.tankExist==true){
                bufferedWriter.write(myTank.getX()+" "+myTank.getY()+" "+myTank.getDirection()+" "+"\n");
            }

            //将每一台敌人坦克的坐标取出并写入文件
            for (int i = 0; i<enemyTanks.size(); i++){
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.tankExist==true){

                    //把内容写入指定的文件中
                    bufferedWriter.write(enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirection()+" "+"\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭文件流
        finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    //读取我的成绩
    public static void recoverMyScores(){
        try {
            fileReader = new FileReader("/src/com/TankGame/Document/MyScoresAndTankCoordinates");
            bufferedReader = new BufferedReader(fileReader);

            //读第一行MyScores文件到String中
            String readMyScores = bufferedReader.readLine();
            //将String转换成Integer并把值传给MyScores
            setMyTotalScore(Integer.parseInt(readMyScores));



        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //读取我的成绩和敌人坦克的坐标
    public static void recoverMyScoresAndTankCoordinates(){
        EnemyTankCoordinateVector.clear();
        try {

            fileReader = new FileReader("/src/com/TankGame/Document/MyScoresAndTankCoordinates");
            bufferedReader = new BufferedReader(fileReader);

            //读第一行：MyScores文件到String中
            String readMyScoresAndEnemyCoordinates = bufferedReader.readLine();
            //将String转换成Integer并把值传给MyScores
            setMyTotalScore(Integer.parseInt(readMyScoresAndEnemyCoordinates));

            //读第二行MyLives文件到String中
            String readMyLives = bufferedReader.readLine();
            //将String转换成Integer并把值传给MyScores
            setMyLives(Integer.parseInt(readMyLives));

            //读第三行EnemyLives文件到String中
            String readEnemyLives = bufferedReader.readLine();
            //将String转换成Integer并把值传给MyScores
            setEnemyLives(Integer.parseInt(readEnemyLives));

            //读第四行我的坦克读坐标
            readMyScoresAndEnemyCoordinates = bufferedReader.readLine();
            String []myXYD = readMyScoresAndEnemyCoordinates.split(" ");
            myTankCoordinate= new TankCoordinate(Integer.parseInt(myXYD[0]) , Integer.parseInt(myXYD[1]),
                    Integer.parseInt(myXYD[2]));
            //继续读取坐标
            while((readMyScoresAndEnemyCoordinates= bufferedReader.readLine()) != null){
            String [] enemyXYD = readMyScoresAndEnemyCoordinates.split(" ");

            TankCoordinate enemyTankCoordinate = new TankCoordinate( Integer.parseInt(enemyXYD[0]), Integer.parseInt(enemyXYD[1]),
                                                                       Integer.parseInt(enemyXYD[2]));
            EnemyTankCoordinateVector.add(enemyTankCoordinate);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //重制我的生命和敌人数量
    public static void resetRecord(){
        myLives=5;
        enemyLives=20;
    }






    public static int getMyTotalScore() {
        return myTotalScore;
    }

    public static void setMyTotalScore(int myTotalScore) {
        Recorder.myTotalScore = myTotalScore;
    }

    public static int getEnemyLives() {
        return enemyLives;
    }

    public static void setEnemyLives(int enemyLives) {
        Recorder.enemyLives = enemyLives;
    }

    public static int getMyLives() {
        return myLives;
    }

    public static void setMyLives(int myLives) {
        Recorder.myLives = myLives;
    }

    public static void myLivesDown(){
        myLives--;
    }

    public static void enemyLivesDown(){
        enemyLives--;
    }

    public static void gainMyScore(){
        myTotalScore++;
    }


}
