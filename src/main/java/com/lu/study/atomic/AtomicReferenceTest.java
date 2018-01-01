package com.lu.study.atomic;

import com.lu.study.bean.Student;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子操作--引用对象操作
 *
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/30
 */
public class AtomicReferenceTest {
    static AtomicReference<Student> studentAtomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        Student oldStudent = Student.builder().age(13).id("20171230").name("lu").build();
        studentAtomicReference.set(oldStudent);


        Student newStudent = Student.builder().age(14).id("20171230").name("lu").build();
        // studentAtomicReference.compareAndSet(oldStudent,newStudent)
        if (studentAtomicReference.compareAndSet(oldStudent, newStudent)) {
            Student lastStudent = studentAtomicReference.get();

            System.out.println(lastStudent.toString());
        }


        // 原子操作 int类型 线程安全 多线程改变对象的值时无需加锁，通过unsafe本地方法保证线程安全
        AtomicInteger atomicInteger = new AtomicInteger(12); // 用之前一定要初始化下，不然取的值是不固定的，由内存决定
        // 获取当前对象的值
        atomicInteger.get();
        // 先自减 后返回自减后的值  可以看成  --i
        System.out.println(atomicInteger.decrementAndGet());
        // 先自增 后返回自增后的值  可以看成  ++i
        atomicInteger.incrementAndGet();

        // 可以当成  i++
        atomicInteger.getAndIncrement();

        // 弱引用，可以被垃圾回收掉
        atomicInteger.weakCompareAndSet(12,23);

        //atomicInteger.accumulateAndGet(2,applyAsInt(1,2)
                //);
        // 当对象的值是11的时候 把值改成13，其他情况不做任何处理，若更新成功返回true，否则返回false
        atomicInteger.compareAndSet(11, 13);

    }
}
