package com.lu.study.pools;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/31
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(2);
        Thread thread = new Thread(new Work2(downLatch));
        Thread thread2 = new Thread(new Work2(downLatch));
        thread.start();
        thread2.start();
        System.out.println("finish work1");
        downLatch.await();

        System.out.println("finish work2");
    }

    static class Work2 implements Runnable {
        CountDownLatch downLatch;

        public Work2(CountDownLatch downLatch) {
            this.downLatch = downLatch;
        }

        @Override
        public void run() {
            System.out.println("线程开始执行");
            Random random = new Random();
            Long sleepTime = Long.valueOf(random.nextInt(10) * 10);
            // 模拟不同线程处理时间可能不一样
            System.out.println(Thread.currentThread().getId() + "线程睡眠 " + sleepTime / 1000D);
            try {
                Thread.sleep(sleepTime);
                downLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
