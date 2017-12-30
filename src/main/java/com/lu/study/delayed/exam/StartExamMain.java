package com.lu.study.delayed.exam;

import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/30
 */
public class StartExamMain {

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        int studentNumber = 20;
        DelayQueue<Examinee> examineeDelayQueue = new DelayQueue<Examinee>();
        Random random = new Random();
        Examinee examinee =null;
        for (int i = 0; i < studentNumber; i++) {
            examinee=new Examinee("examinee" + (i + 1));
            System.out.println(examinee.toString());
            examineeDelayQueue.put(examinee);

        }
        examineeDelayQueue.put(new Examinee("examinee"));
        Thread examSysThread = new Thread(new ExamSys(examineeDelayQueue));
        examSysThread.start();
    }
}
