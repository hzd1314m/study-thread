package com.lu.study.customandproduct.newjdk;

import com.lu.study.bean.Order;
import com.lu.study.customandproduct.interfaces.IOrderSys;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OrderSysImplByNewWay implements IOrderSys {

    private List<Order> orderQueue;

    private ReentrantLock lock = new ReentrantLock();

    private Condition productCondition = lock.newCondition();

    private Condition customCondition = lock.newCondition();

    private static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    // 目前收到的钱
    static double totalMoneny = 0;

    public OrderSysImplByNewWay(List<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    public void addOrder(Order addOrder) throws InterruptedException {
        lock.lock();
        lock.tryLock();
        try {
            while (currentOrderSize.get() >= MAX_ORDER_NUMBER) {
                System.out.println("本店订单已经达到上限,暂时停止接单");
                // 让订单系统停止接单
                productCondition.await();

            }

            addOrder.setAccountName("张三" + threadLocalRandom.nextInt(100));
            addOrder.setAccountPhone("1381231234" + threadLocalRandom.nextInt(9));
            addOrder.setAccountTableID("TABLE_NUM_" + threadLocalRandom.nextInt(100));
            addOrder.setTotalMoney(threadLocalRandom.nextDouble() * 10000);
            addOrder.setRange(threadLocalRandom.nextDouble());
            orderQueue.add(addOrder);
            //Thread.sleep(1000);
            String log = String.format("本店接到:顾客%s 餐桌号为%s 订单金额为%s 优惠折扣为%s的订单",
                    addOrder.getAccountName(), addOrder.getAccountTableID(), addOrder.getTotalMoney(), addOrder.getRange());
            System.out.println(log);
            System.out.println("本店正在火热接单,目前接单数量为:" + currentOrderSize.incrementAndGet());
            // 让员工完成订单
            customCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeOrder(Order removeOrder) throws InterruptedException {
        lock.lock();
        try {
            while (currentOrderSize.get() <= 0) {
                System.out.println("本店暂无订单。请速订购。先点先得");
                // 让订单系统停开始接单
                customCondition.await();
            }

            removeOrder = orderQueue.get(0);
            String log = String.format("本店完成顾客%s 餐桌号为%s 订单金额为%s 优惠折扣为%s 的订单" ,
                    removeOrder.getAccountName() , removeOrder.getAccountTableID() , removeOrder.getTotalMoney() , removeOrder.getRange());
            System.out.println(log);
            currentOrderSize.decrementAndGet();
            System.out.println("本店正在火热接单,目前完成订单数量为:" + currentFinishOrderSize.incrementAndGet());

            totalMoneny = totalMoneny + removeOrder.getTotalMoney() * removeOrder.getRange();
            System.out.println("本店已经赚了:" + totalMoneny + "元!!!!!");
            orderQueue.remove(0);
            // 让订单系统停开始接单
            productCondition.signal();
            Thread.sleep(5000);
        }finally {
            lock.unlock();
        }
    }
}
