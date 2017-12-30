package com.lu.study.delayed.exam;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;

/**
 * 考试系统
 *
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/30
 */
public class ExamSys implements Runnable {
    private DelayQueue<Examinee> examineeDelayQueue;

    private int examineeNum = 20;

    public ExamSys(DelayQueue<Examinee> examineeDelayQueue) {
        this.examineeDelayQueue = examineeDelayQueue;
    }

    @Override
    public void run() {
        System.out.println(" 系统开启考试，请在规定时间范围内完成考试并且交卷");
        while (examineeNum > 0) {
            Examinee examinee = examineeDelayQueue.poll();
            if (examinee.getTestTime() < 90) {
                examinee.run();
                if (examineeNum > 0) {
                    examineeNum--;
                }
            } else {
                System.out.println(" 考试时间到，全部交卷！");
                Examinee tmpExaminee;
                for (Iterator<Examinee> iterator2 = examineeDelayQueue.iterator(); iterator2.hasNext(); ) {
                    tmpExaminee = iterator2.next();
                    tmpExaminee.setTimeOut(true);
                    tmpExaminee.run();
                    if (examineeNum > 0) {
                        examineeNum--;
                    }
                }
            }
        }
    }
}
