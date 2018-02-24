package com.lu.study.delayed.exam;

import lombok.*;

import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 考生
 *
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/30
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Examinee implements Runnable, Delayed {

    // 线程安全
    private static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    // 考生名字
    private String name;

    // 考生考试耗时
    private long testTime;
    // 交卷时间
    private long endExamTime;
    // 考试时间是否超时。超时就强制交卷
    private boolean timeOut = false;

    public Examinee(String name) {
        this.name = name;
        // 随机模拟考生考试卷耗时
        this.testTime = threadLocalRandom.nextInt(160);
        // 这里模拟考生提交试卷
        this.endExamTime = TimeUnit.NANOSECONDS.convert(testTime, TimeUnit.NANOSECONDS) + System.nanoTime();// 纳秒级别
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(endExamTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * 队列排序，按照考试耗时由小到大排序，所以可以知道delayed队列是有序的
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        if (o == null || !(o instanceof Examinee))
            return 1;
        if (o == this)
            return 0;
        Examinee examinee = (Examinee) o;
        if (this.testTime > examinee.getTestTime()) {
            return 1;
        } else if (this.testTime == examinee.getTestTime()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public void run() {
        if (timeOut) {
            System.out.println(name + " 交卷，实际用时 120分钟");
        } else {
            System.out.println(name + " 交卷," + "实际用时 " + testTime + " 分钟");
        }
    }
}
