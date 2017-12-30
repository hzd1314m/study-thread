package com.lu.study.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/30
 */
public class BlockingQueueArrayTest {
    static BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);


    public static void main(String[] args) {
        for (int i = 0; i <= 12; i++) {
            // offer 不报错  只会加到队列满为止，超出就丢失了
            arrayBlockingQueue.offer("index" + i);

            // add 是offer的简单的封装，当队列满了还向队列加数据就报错了
            arrayBlockingQueue.add("index" + i);

            // put()方法往队列里插入元素,如果队列已经满,则会一直等待直到队列为空插入新元素,或者线程被中断抛出异常.
            // 与之对应的 就是take() 取，若没有就一直等待
            try {
                arrayBlockingQueue.put("index" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    /*  offer()方法往队列添加元素如果队列已满直接返回false,队列未满则直接插入并返回true;

        add()方法是对offer()方法的简单封装.如果队列已满,抛出异常new IllegalStateException("Queue full");

        put()方法往队列里插入元素,如果队列已经满,则会一直等待直到队列为空插入新元素,或者线程被中断抛出异常.

        remove()方法直接删除队头的元素:

        peek()方法直接取出队头的元素,并不删除.

        element()方法对peek方法进行简单封装,如果队头元素存在则取出并不删除,如果不存在抛出异常NoSuchElementException()

        pool()方法取出并删除队头的元素,当队列为空,返回null;

        take()方法取出并删除队头的元素,当队列为空,则会一直等待直到队列有新元素可以取出,或者线程被中断抛出异常

        offer()方法一般跟pool()方法相对应, put()方法一般跟take()方法相对应.日常开发过程中offer()与pool()方法用的相对比较频繁.
    */
        arrayBlockingQueue.remove();

        arrayBlockingQueue.forEach(str -> System.out.println(str));


        arrayBlockingQueue.forEach(str -> System.out.println(str));
    }

}
