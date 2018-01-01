package com.lu.study.pools;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 场景：适合大任务，会把大任务拆成多个小任务 并且当线程任务完成时，会从其他线程窃取任务去执行，最大程度的利用了线程的特性
 *
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/31
 */
public class TestForkJoinPool {

    // 工作线程队列最大值
    private static final Integer MAX = 200;

    // 实现接口 RecursiveTask<Integer>  integer是返回值
    static class MyForkJoinTask extends RecursiveTask<Integer> {
        // 子任务开始计算的值
        private Integer startValue;

        // 子任务结束计算的值
        private Integer endValue;

        public MyForkJoinTask(Integer startValue, Integer endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        protected Integer compute() {
            Random random = new Random();
            // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
            // 可以正式进行累加计算了
            if (endValue - startValue < MAX) {
                System.out.println(Thread.currentThread().getId() + "开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
                Integer totalValue = 0;
                for (int index = this.startValue; index <= this.endValue; index++) {
                    totalValue += index;
                }
                Long sleepTime = Long.valueOf(random.nextInt(10) * 1000);
                // 模拟不同线程处理时间可能不一样，这样可以测试 fork/join的工作窃取
                // 工作窃取 即当前线程没有任务后会从
                System.out.println(Thread.currentThread().getId() + "线程睡眠 " + sleepTime / 1000D);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return totalValue;
            }
            // 否则再进行任务拆分，拆分成两个任务
            else {
                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
                subTask1.fork();
                MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1, endValue);
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }
        }


    }

    public static void main(String[] args) {
        // 这是Fork/Join框架的线程池 线程池一定要设置大小，不然会一直创建线程，这样会增加内存的消耗
        // 这里设置的并发数是4
        ForkJoinPool pool = new ForkJoinPool(4);
        ForkJoinTask<Integer> taskFuture = pool.submit(new MyForkJoinTask(1, 10000));
        try {
            Integer result = taskFuture.get();
            System.out.println("result = " + result);
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);
            System.out.printf("**********************\n");

            System.out.printf("线程池的worker线程们的数量:%d\n",
                    pool.getPoolSize());
            System.out.printf("当前执行任务的线程的数量:%d\n",
                    pool.getActiveThreadCount());
            System.out.printf("没有被阻塞的正在工作的线程:%d\n",
                    pool.getRunningThreadCount());
            System.out.printf("已经提交给池还没有开始执行的任务数:%d\n",
                    pool.getQueuedSubmissionCount());
            System.out.printf("已经提交给池已经开始执行的任务数:%d\n",
                    pool.getQueuedTaskCount());
            System.out.printf("线程偷取任务数:%d\n",
                    pool.getStealCount());
            System.out.printf("池是否已经终止 :%s\n",
                    pool.isTerminated());
            System.out.printf("**********************\n");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }


    }
}
